package org.sd.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.sd.common.IAgentFacade;
import org.sd.common.IConfig;
import org.sd.common.connection.ConnectionQueue;
import org.sd.common.connection.ConnectionQueueSingleton;
import org.sd.common.connection.ConnectionWorker;

public class ServerFacade implements IAgentFacade {
	
	private ServerConfig serverConfig;
	private ConnectionQueue workQueue;
	
	private ServerSocket serverSocket;
	private Socket clientSocket;
	
	private boolean isListening = true;

	public void initialize(IConfig serverConfiguration) {
		if (!(serverConfiguration instanceof ServerConfig)) {
			//TODO BM tratar erros
		}
		
		//get the server config
		serverConfig = (ServerConfig) serverConfiguration;
		
		//start the WorkQueue
		ConnectionQueueSingleton.setNThread(serverConfig.getNThreads());
		workQueue = ConnectionQueueSingleton.getInstance();
		
		try {
			//start the server socket
			serverSocket = new ServerSocket(serverConfig.getServerPort());
			
			while (isListening) {
				//start the client connection
				clientSocket = serverSocket.accept();
				
				//define a timeout
				clientSocket.setSoTimeout(serverConfig.getConnectionTimeout());
				
				//add the connection to the queue
				workQueue.execute(new ConnectionWorker(clientSocket));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			terminate();
		}
	}

	public void terminate() {
		//close the client socket
		try {
			clientSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//close the server socket
		try {
			serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//server stops listening for connections
		isListening = false;
	}
}
