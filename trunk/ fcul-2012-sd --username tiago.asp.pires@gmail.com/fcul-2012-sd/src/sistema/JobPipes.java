/**
 * 
 */
package svrEngine;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Arrays;

import protocol.AgentProtocol;
import protocol.ServerConfigData;
import protocol.PkgJobData;

import javax.swing.JOptionPane;

/*************************************************************
 * @author tpires
 */
public class JobPipes extends Thread{
	
	private static final int ELAPSED_SECONDS =0; 
	
	private ServerConfigData currentConfig;
	private ArrayList <PkgJobData> filteredJobs = new ArrayList <PkgJobData> ();
	private ArrayList <PkgJobData> fromSource;
	private int pipeID;
	private Calendar agenda [];
	private Calendar triggerOn = Calendar.getInstance();
	private volatile boolean online=true;	
	private ServerMenu infoFrame;
		
	/*****************************************************************
	 * 
	 * @param currentConfig
	 * @param pipeID
	 */
	public JobPipes(ServerMenu infoFrame,ServerConfigData currentConfig, int pipeID, ArrayList <PkgJobData> listaGeral)
	{
		this.currentConfig=currentConfig;
		this.pipeID = pipeID;
		this.fromSource = listaGeral;
		this.infoFrame = infoFrame;
		
		infoFrame.updateStatusTasks("Rede : "+currentConfig.getDeployNetworkName(pipeID));
		//Data e hora da proxima execução!!!!
		triggerOn = setNextTrigger ();
											
		infoFrame.updateStatusTasks("Proxima exec.: "+
						triggerOn.get(Calendar.YEAR)+"-"
							+triggerOn.get(Calendar.MONTH)+"-"
								+triggerOn.get(Calendar.DAY_OF_MONTH)+" "
									+triggerOn.get(Calendar.HOUR_OF_DAY)+":"
										+triggerOn.get(Calendar.MINUTE)+":");
		this.start();
	}
	
	/*************************************************************************
	 * Metodo que filtra os jobs selecionando só os que pertencem a este pipe
	 * @param arrayList <PkgJobData> Lista de todos os jobs em queue 
	 * @param pipeID int Indentificação da rede alvo a instalar. 
	 */
	private void filterJobs (ArrayList <PkgJobData> estaLista,int pipeID)
	{
		Calendar now = Calendar.getInstance();
		for (int i=0;i<estaLista.size();i++)
		{
			//todos os jobs que forem para esta rede. neste pipe
			//todos os jobs que tiverem o agendamento para esta hora e minuto.
			try {sleep(3000);}
				catch (Exception e) {}
			
			if (estaLista.get(i).getPkgData().getPkgTargetNework()==pipeID && 
					estaLista.get(i).getJobStatus()==PkgJobData.STATUS_PENDING &&
						estaLista.get(i).getDueTo().get(Calendar.HOUR_OF_DAY)==
							now.get(Calendar.HOUR_OF_DAY) && estaLista.get(i).getDueTo()
							.get(Calendar.MINUTE)== now.get(Calendar.MINUTE))	{
				filteredJobs.add(estaLista.get(i));
				
				estaLista.get(i).setJobStatus(PkgJobData.STATUS_PROCESSING);
			}
		}
	}
	
	/*************************************************************************
	 * SIMULAÇÃO!!
	 * @param arrayList <PkgJobData> Lista de todos os jobs em queue 
	 * @param pipeID int Indentificação da rede alvo a instalar. 
	 */
	private void endfilterJobs (ArrayList <PkgJobData> estaLista,int pipeID)
	{
		Calendar now = Calendar.getInstance();
		for (int i=0;i<estaLista.size();i++)
		{
			//todos os jobs que forem para esta rede. neste pipe
			//todos os jobs que tiverem o agendamento para esta hora e minuto.
			
			if (estaLista.get(i).getPkgData().getPkgTargetNework()==pipeID && 
					estaLista.get(i).getJobStatus()==PkgJobData.STATUS_PENDING &&
						estaLista.get(i).getDueTo().get(Calendar.HOUR_OF_DAY)==
							now.get(Calendar.HOUR_OF_DAY) && estaLista.get(i).getDueTo()
							.get(Calendar.MINUTE)== now.get(Calendar.MINUTE))	{
				filteredJobs.add(estaLista.get(i));
				estaLista.get(i).setJobStatus(PkgJobData.STATUS_PROCESSED);
			}
		}
	}
	
	/*************************************************************************
	 * Metodo para o agendamento da proxima execução de deplyment
	 * @param latesDueTime
	 * @return+"-fd.dat"
	 */
	private Calendar setNextTrigger ()
	{
		agenda = currentConfig.getDeployNetworkCalendar(pipeID);
		Calendar tempCal = Calendar.getInstance();
		int iterador=0;
		
		int esteMomento =  ( (Calendar.getInstance().get(Calendar.HOUR_OF_DAY)*100)
			+ Calendar.getInstance().get(Calendar.MINUTE) )*100+Calendar.getInstance().get(Calendar.SECOND);
		
		while (agenda.length>iterador)
		{
			//se este Pacote chegar antes do ultimo agendamento
			if ( (agenda[iterador].get(Calendar.HOUR_OF_DAY)*100
						+agenda[iterador].get(Calendar.MINUTE))*100<esteMomento)
			{	iterador++; 	}
			else
			{
				//	este job fica agendado para a proxima passagem
				tempCal.set(Calendar.HOUR_OF_DAY,agenda[iterador].get(Calendar.HOUR_OF_DAY));					
				tempCal.set(Calendar.MINUTE,agenda[iterador].get(Calendar.MINUTE));
				tempCal.set(Calendar.SECOND,ELAPSED_SECONDS);
				break;
			}
			//	se todos os agendamentos ja passaram
			if (iterador==agenda.length)
			{
				//	este job fica agendado para a primeira passagem do dia
				tempCal.add(Calendar.DAY_OF_MONTH,1);
				tempCal.set(Calendar.HOUR_OF_DAY,agenda[0].get(Calendar.HOUR_OF_DAY));					
				tempCal.set(Calendar.MINUTE,agenda[0].get(Calendar.MINUTE));
				tempCal.set(Calendar.SECOND,ELAPSED_SECONDS);
			}
		}
		return tempCal;
	}

	/************************************************************************
	 * metodo de execução do thread
	 */
	public void run ()
	{
		while (online)
		{
			try {
				//Verifica a hora actual
				while (triggerOn.compareTo(Calendar.getInstance())>0)
				{
					sleep(10000); //10 segundos
					if (!online) break;
				}
				if (online)
				{
					//seleciona todos os jobs para este pipe
					filterJobs (fromSource,pipeID);
					endfilterJobs (fromSource,pipeID);//SIMULAÇÃO!!!! 
					//Processa os jobs definidos neste ambiente
					processJobs(fromSource,pipeID);
					//Actualiza o agendamento para a proxima data de passagem 
					//definida nas configurações deste ambiente.
					triggerOn = setNextTrigger ();
					
					infoFrame.updateStatusTasks("Proxima exec.: "+
							triggerOn.get(Calendar.YEAR)+"-"
								+triggerOn.get(Calendar.MONTH)+"-"
									+triggerOn.get(Calendar.DAY_OF_MONTH)+" "
										+triggerOn.get(Calendar.HOUR_OF_DAY)+":"
											+triggerOn.get(Calendar.MINUTE)+":");
				}
			} catch (Exception e){
				JOptionPane.showMessageDialog(null, "erro:"+e.toString());
			}
		}	
		infoFrame.updateStatusTasks("Agend. para: "+currentConfig.getDeployNetworkName(pipeID)+" terminado");
	}
	
	/************************************************************************
	 * Metodo para terminar a actividade o motor de passagem de pacotes
	 */
	public void stopJobs () 					{ 	this.online=false;	}

	/************************************************************************
	 * Metodo que avalia o conteudo deste pacote, e define as acções a realizar
	 * pendente peso.
	 * @param esteJob PkgJobData com os dados do pacote
	 * @return action int[][] array bidimensional com os pacotes a processar,
	 * ordenados pelas suas prioridades.
	 */
	private int[][] evaluateContent(PkgJobData esteJob)
	{
		int groupsFound=0;
		int grupos=0;
		//pos [i][0]=indice dos conteudos.
		//pos [i][1]=Peso dos conteudos.
		int [][] action; 

		//conteudos e prioridades encontrados no zip (descritivo)
		boolean [] conteudoPkg = esteJob.getPkgData().getPkgContents();

		//Quantos grupos tem este pacote.
		for (int index=0;index<conteudoPkg.length;index++)	{
			if (conteudoPkg[index])
				groupsFound++;	
		}
		action = new int [groupsFound][2];
		
		//array bidim, com as refs dos pacotes encontrados,
		//e prioridades
		for (int index=0;index<conteudoPkg.length;index++) {
				if (conteudoPkg[index])
				{
					action[grupos][0]=index;
					action[grupos][1]=currentConfig.
						getDeployNetworkContentPriorityValue (pipeID,action[index][0]);
					grupos++;
				}
		}
		//TODO:O array dos conteudos com o pacote é estatico, e tem de estar associado  
		//TODO:á configuração do servidor.

		//bubble sort maior primeiro
		//ordenar os indices no array, consuante o peso
		int tempRef,tempPri;
		for (int oi=0;oi<groupsFound-1;oi++){
			for (int ii=0;ii<groupsFound-1-oi;ii++)	{
				
				if (action[ii][1]<action[ii+1][1])
				{
					tempRef=action[ii][0];
					tempPri=action[ii][1];
					action[ii][0]=action[ii+1][0];
					action[ii][1]=action[ii+1][1];
					action[ii+1][0]=tempRef;
					action[ii+1][1]=tempPri;
				}
			}
		}
		//Devolver lista de acções para este pacote
		return action;	
	}
	
	/*************************************************************************
	 * Inicia o processamento dos jobs em lista.
	 * @param arrayList <PkgJobData> Lista de todos os jobs em queue 
	 * @param pipeID int Indentificação da rede alvo a instalar. 
	 */
	private void processJobs (ArrayList <PkgJobData> estaLista,int pipeID)
	{
		boolean processedOK=true;
		int [][] actionOnPackage;
		String [] agents;
		String error="";
		int iterador=0;
		
		//para cada job na lista dos  filtrados
		for (int index=0;index<filteredJobs.size();index++)
		{
			//avalia o conteudo e descrimina ordenando a passagem
			//para acada pacote com base no conteudo, ordena por prioridade.
			// cria um array com as acções a efectuar para este pacote
			actionOnPackage = evaluateContent(filteredJobs.get(index));
			try{	
				while (iterador<actionOnPackage.length)
				{
					switch (actionOnPackage[iterador][0])
					{
						//asps
						case ServerConfigData.PROCESS00:
							//descomprime o zip para a temp e recomprime so a directoria +ASP
							makeP00package(filteredJobs.get(index).getSSI());
							agents = currentConfig.
								getDeployNetworkContentAgentAddresses
										(pipeID,currentConfig.PROCESS00);
							//abre ligação para cada agente definido nas configs		
							for (int agentIndex=0;agentIndex<agents.length;agentIndex++)							{
								error+=SendContentToAgent(filteredJobs.get(index).
															getSSI(),agents[agentIndex]);
							}
							//termina processo de asp's	
							if (!error.isEmpty())
								processedOK=false;
							break;
						//TODO:passar Coms
						case ServerConfigData.PROCESS01:processedOK=true;break;
						//TODO: passar scritps
						case ServerConfigData.PROCESS02:processedOK=true;break;
						//TODO:passar outros.
						case ServerConfigData.PROCESS03:processedOK=true;break;
					}
					if (!processedOK)
						break;
				}
			} catch (Exception e)	{
				//erro com o zip
			}
			if (processedOK)
			//marca o job como processado.
				estaLista.get(index).setJobStatus(PkgJobData.STATUS_PROCESSED);
			else
				estaLista.get(index).setJobStatus(PkgJobData.STATUS_FAILED);
		}
	}
	
	/************************************************************************
	 * 
	 * @param ssi
	 */
	private void makeP00package(long ssi)
	{
		//descomprime o zip para a temp e recomprime so a directoria +ASP
		ZipFileHandle.unZipContent(pipeID,ServerConfigData.PROCESS00,currentConfig,ssi);
		ZipFileHandle.zipSelectedContent(pipeID,ServerConfigData.PROCESS00,currentConfig,ssi);
	}
	
	private String SendContentToAgent (long ssi,String agentAddr)
	{
		String result="";
		
		AgentProtocol sendProto = new AgentProtocol ();
		sendProto.setOpCode(AgentProtocol.PROCESS00);
		sendProto.setZipFileName(ssi+"-asp-fz.dat");
		sendProto.setTargetDir(currentConfig.getDeployNetworkContentInstallDir(pipeID, ServerConfigData.PROCESS00));
		AgentConn ligaAgente = new AgentConn (ssi,agentAddr,sendProto);
		if (!ligaAgente.goOnline(currentConfig))		{
			result=ligaAgente.getError();
		}
		return result;
	}
}
