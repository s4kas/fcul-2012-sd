/**
 * 
 */
package controloservidor;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Date;
import java.util.Calendar;
import javax.swing.JOptionPane;

import controlocliente.ClientProtocol;
import controloservidor.ServerConfigData;

import sistema.IOperations;
import ui.ServerMenu;


/**
 * @author tpires
 *
 */
public class NetworkClientHandle extends Thread
{
	/**
	 *Subclasse Connect, o objecto que lida com a ligação establecida
	 *Controlo da ligação establecida, em thread proprio
	 */

		private ServerMenu parentFrame;
		private Socket cliente=null;
		private ObjectInputStream ois =null;
		private ObjectOutputStream oos = null;
		BufferedOutputStream fsioOS;
		BufferedInputStream netIS;

		ServerConfigData serverCurrentConfig;
		
		/**
		 * Este constructor é inválido.
		 * 
		 */
		public NetworkClientHandle() {}
		
		 /**
		 * Criação de Objectstreams e start() de thread 
		 * @param esteCliente Handling do socket com a ligação do cliente
		 */
		public NetworkClientHandle (Socket ligacao, ServerMenu parentFrame)
		{
			cliente=ligacao;
			this.parentFrame=parentFrame;
			
			serverCurrentConfig = (ServerConfigData) IOperations.loadIOServerConfigData("cfgs/serverconfig.dat");
			
			try {
				cliente.setSoTimeout(15000);
				ois = new ObjectInputStream(cliente.getInputStream());
				oos = new ObjectOutputStream(cliente.getOutputStream());
				this.start();
			}
			catch (SocketTimeoutException e)
			{
				try{
					parentFrame.updateStatusConns("Esta connexão expirou!");
				    cliente.close();
					parentFrame.updateStatusConns("Esta connexão expirou, fechada!");
				}
				catch (Exception ex){
					parentFrame.updateStatusConns("nao fechou graciosamente!isto é mau!!");
				}
			}
			catch (Exception ex)
			{
				parentFrame.updateStatusConns("Dados corrumpidos, não é objecto!");
				try{
					parentFrame.updateStatusConns("a despejar cliente...");
					cliente.close();
					parentFrame.updateStatusConns("Cliente despejado!");
				}
				catch (Exception exc){
					parentFrame.updateStatusConns("CaBuum!! outra vez, não despejou o cliente: "
																				+exc.toString());
					return;
				}
			}
		}

		/*********************************************************************************
		 * Handling da ligação establecida
		 */
		public void run()
		{
			parentFrame.updateStatusConns("Ligação passada para processo independente...");
			try {
				ClientProtocol protoRecebido = (ClientProtocol) ois.readObject();
				while (protoRecebido.getOpcode()!= ClientProtocol.END)
				{
					switch (protoRecebido.getOpcode())	
					{
						case ClientProtocol.HANDSHAKE:
								sendClientConfigData(protoRecebido);
								break;
						case ClientProtocol.UPLOAD_PACKAGE:
								RecievePackage(protoRecebido);
								break;
						case ClientProtocol.DOWNLOAD_STATS:
								//TODO: send status to clients
								break;
					}
					protoRecebido = (ClientProtocol) ois.readObject();
				}
				ois.close();
				oos.close();
				parentFrame.updateStatusConns("Ligação terminada com "+cliente.getInetAddress());
				cliente.close();
				
			}
			catch (Exception e){
				JOptionPane.showMessageDialog(null,"Erro nas operaçoes de rede"+e.toString());
			}
		}
		
		/**
		 * Metodo para enviar os dados de configuração ao cliente.
		 * @param proto ClientProtocol, protocolo solicitado pelo cliente.
		 * @throws IOException Se não conseguir ler 
		 * do file system stream ou escrever no OutputSocketStream.
		 */
		private void sendClientConfigData(ClientProtocol proto) throws IOException
		{
			parentFrame.updateStatusConns(" a reconfigurar cliente"+proto.getHostname().toString());
			oos.writeObject(IOperations.loadIOObjConfigData(serverCurrentConfig.getConfigFilesDir()+"clientconfig.dat"));
			oos.flush();
		}
		
		/**
		 * Metodo de recepção de pacoto de código e respectivo objecto descritor.
		 * @throws IOException Se não conseguir escrever no stream
		 * @throws ClassNotFoundException Se o objecto não for da classe esperada.
		 */
		private void RecievePackage(ClientProtocol proto) throws IOException, ClassNotFoundException
		{
			int bite;
			long ssi;
			String serverSerialName; 

			
			//informa inicio de receção de pacote
			parentFrame.updateStatusConns(proto.getHostname().toString()+", está a enviar novo pacote...");
			ssi= (new Date().getTime());			//Constroi numero de série
			serverSerialName = ((Long) ssi).toString();			//conversão para strinng
			NewPkgData dados = (NewPkgData) ois.readObject();	//Recebe dados via sockets

			fsioOS = new BufferedOutputStream (
						new FileOutputStream (serverCurrentConfig.getConfigPkgDir()+serverSerialName+"-fz.dat"));	//abre um stram para IO  para receber pacote zip anexo.

			//le zip byte a byte do stream socket, e escreve em disco.
			while ((bite=ois.read())!=-1) {
				fsioOS.write(bite);
			}
			if (fsioOS!=null)
				fsioOS.close();
			
			//Informa do fim de processamento do novo pacote.
			parentFrame.updateStatusConns(" Pacote de "+cliente.getInetAddress().toString()+
																" recebido com Sucesso!");
			parentFrame.updateStatusConns("a processar pacote...");
			
			//Converter o pacote num job e grava-lo em disco
			PkgJobData esteJob = processNewPackageData(dados,ssi);
			IOperations.saveIOObjData(esteJob,
					serverCurrentConfig.getConfigPkgDir()+serverSerialName+"-fd.dat");
			
			//adicionar este pacote á estrutura de jobs
			if (parentFrame.tarefas!=null){
				parentFrame.tarefas.updateJobsList(esteJob);
				parentFrame.updateStatusConns("Pacote nº "+serverSerialName+" enviado por: "+proto.getHostname().toString()+
									" foi agendado com Sucesso!");
			} else
			{
				parentFrame.updateStatusConns(proto.getHostname().toString()+"ERRO: Agendamento indisponivel!");
			}
		}
		
		/***********************************************************************
		 * Metodo que processa o novo pacote e converte-o num Job.
		 * @param dados NewPkgData objecto de dados do pacote a submeter
		 * @param ssi long server serial number.
		 * @return PkgJobData um job pronto a sumbeter em queue.
		 */
		private PkgJobData processNewPackageData (NewPkgData dados,long ssi)
		{
			Calendar latestDueTime = Calendar.getInstance(); //data actua
			int iterador=0;

			//carregar dados de configuração do servidor
			ServerConfigData currentConfig = 
				(ServerConfigData)	IOperations.loadIOServerConfigData("cfgs/serverconfig.dat");
			
			//obter o agendamento
			Calendar estaAgenda [] =
					currentConfig.getDeployNetworkCalendar(
									dados.getPkgTargetNework());

			//Novo job, com todos os dados do pacote.
			PkgJobData novoJobData = 
					new PkgJobData (ssi,PkgJobData.STATUS_PENDING,Calendar.getInstance(),dados);
			
			
			//Atribuir agendamento a este pacote.
			int esteMomento = (latestDueTime.get(Calendar.HOUR_OF_DAY)*100)
									+latestDueTime.get(Calendar.MINUTE);
			while (estaAgenda.length>iterador)
			{
				//se este Pacote chegar antes do ultimo agendamento
				if ( (estaAgenda[iterador].get(Calendar.HOUR_OF_DAY))*100
							+(estaAgenda[iterador].get(Calendar.MINUTE))<esteMomento)
				{
					iterador++;
				}
				else
				{
					//este job fica agendado para a proxima passagem
					latestDueTime.set(Calendar.HOUR_OF_DAY,estaAgenda[iterador].get(Calendar.HOUR_OF_DAY));					
					latestDueTime.set(Calendar.MINUTE,estaAgenda[iterador].get(Calendar.MINUTE));
					novoJobData.setDueTo(latestDueTime);
					break;
				}
				//se todos os agendamentos ja passaram
				if (iterador==estaAgenda.length)
				{
					//este job fica agendado para a primeira passagem do dia
					latestDueTime.add(Calendar.DAY_OF_MONTH,1);
					latestDueTime.set(Calendar.HOUR_OF_DAY,estaAgenda[0].get(Calendar.HOUR_OF_DAY));					
					latestDueTime.set(Calendar.MINUTE,estaAgenda[0].get(Calendar.MINUTE));
					novoJobData.setDueTo(latestDueTime);
				}
			}
			return novoJobData;
		}
}	
