/**
 * 
 */
package svrEngine;


import java.net.ServerSocket;
import java.net.Socket;
import protocol.ServerConfigData;

/**
 * Componente modulo de sockets do servidor 
 * @author tpires
 */
public class NetworkServer extends Thread
{
	
	private ServerSocket sasServerSocket;
	private ServerMenu parentFrame;
	private boolean  stayOnline=true;
	private Socket ligacao;
	ServerConfigData currentConfig;
	

	/************************************************************************
	 * Constructor do socket de servidor. 
	 * Start() call.
	 * @param parentFrame Frame que chama este thread. necessario para as mensagens de status
	 */
	public NetworkServer(ServerMenu parentFrame, boolean lock)
	{
		this.parentFrame=parentFrame;
		
		currentConfig = IOperations.loadIOServerConfigData("cfgs/serverconfig.dat");
		
		try {
			sasServerSocket = new ServerSocket (currentConfig.getServerPortAddress());
			parentFrame.updateStatusConns("Servidor em linha!,\nA aguardar ligações na porta: "+
					currentConfig.getServerPortAddress());
			this.start();
		}
		catch (Exception e)
		{
			parentFrame.updateStatusConns("erro: a criar ServerSocket : "+e.toString());
		}
	}
	
	/*********************************************************************************
	 * Overload run() devido á extensão do thread
	 * Inicia o loop a aguardar connecções.
	 */
	public void run()
	{
		parentFrame.updateStatusConns("A aguardar ligações...");
		
		while(stayOnline)
		{
			try	{
				ligacao = sasServerSocket.accept();
				parentFrame.updateStatusConns("A chegar ligação de: "+ligacao.getInetAddress());
				//TODO:Distinguir clientes de estações de deploy.
				new NetworkClientHandle (ligacao, parentFrame);
			}
			catch (Exception e)	{
				parentFrame.updateStatusConns("ServerSocket : "+e.toString());
			}
		}
		parentFrame.updateStatusConns("Recepção de Ligações Terminada!");
	}
	
	/**********************************************************************
	 * Metodo para terminar o a actividade de servidor.
	 * Servidor não aceita mais ligações
	 */
	public void disconnect ()
	{
		try {
			this.stayOnline=false;
			sasServerSocket.close();
		}
		catch (Exception e)	{
			parentFrame.updateStatusConns("A finalizar servidor.");
		}
	}
	
}

