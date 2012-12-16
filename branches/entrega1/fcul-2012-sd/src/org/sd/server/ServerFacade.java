package org.sd.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.sd.common.IAgentFacade;
import org.sd.common.IConfig;
import org.sd.common.connection.Connection;
import org.sd.server.connection.ConnectionPool;
import org.sd.server.connection.ConnectionPoolProxy;
import org.sd.server.connection.ConnectionWorker;
import org.sd.server.connection.ConnectionWorker.WorkType;
import org.sd.server.dispatcher.ServerDispatcher;
import org.sd.server.dispatcher.ConnectionDispatcher;
import org.sd.server.message.MessagePool;
import org.sd.server.message.MessagePoolProxy;

public class ServerFacade implements IAgentFacade {
	
	private ServerConfig serverConfig;
	private ConnectionPool connectionPool;
	private MessagePool messagePool;
	
	private ServerSocket serverSocket;
	private Socket clientSocket;
	
	private boolean isListening = true;

	public void initialize(IConfig serverConfiguration, ServerDispatcher runningServerDispatcher) {
		if (!(serverConfiguration instanceof ServerConfig)) {
			//TODO BM tratar erros
		}
		
		//get the server config
		serverConfig = (ServerConfig) serverConfiguration;
		
		//start the ConnectionPool
		ConnectionPoolProxy.setNThread(serverConfig.getNThreads());
		connectionPool = ConnectionPoolProxy.getInstance();
		
		//start the MessagePool
		messagePool = MessagePoolProxy.getInstance();
		messagePool.addDispatchers(runningServerDispatcher, new ConnectionDispatcher());
		
		try {
			//start the server socket
			serverSocket = new ServerSocket(serverConfig.getServerPort());
			
			while (isListening) {
				//start the client connection
				clientSocket = serverSocket.accept();
				
				//define a timeout
				//clientSocket.setSoTimeout(serverConfig.getConnectionTimeout());
				
				//add the connection to the queue
				Connection connection = new Connection(clientSocket);
				connectionPool.execute(new ConnectionWorker(connection, WorkType.RECEIVE));
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