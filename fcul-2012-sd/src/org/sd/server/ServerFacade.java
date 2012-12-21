package org.sd.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.sd.common.IAgentFacade;
import org.sd.common.IConfig;
import org.sd.common.connection.Connection;
import org.sd.data.Agenda;
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
	private ServerDispatcher serverDispatcher;
	private ConnectionDispatcher connectionDispatcher;
	
	private ServerSocket serverSocket;
	private Socket clientSocket;
	
	private boolean isListening = true;
	
	public ServerDispatcher getServerDispatcher() {
		return serverDispatcher;
	}

	public void initialize(IConfig serverConfiguration) {
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
		serverDispatcher = new ServerDispatcher(new Agenda());
		connectionDispatcher = new ConnectionDispatcher();
		messagePool.addDispatchers(serverDispatcher, connectionDispatcher);
	}
	
	public void start() {
		(new Thread() {
			public void run() {
				//start the server socket
				try {
					serverSocket = new ServerSocket(serverConfig.getServerPort());
					isListening = true;
				} catch (IOException e1) {
					terminate();
				}
				
				while (isListening) {
					try {		
						//start the client connection
						clientSocket = serverSocket.accept();
						
						//add the connection to the queue
						Connection connection = new Connection(clientSocket);
						connectionPool.execute(new ConnectionWorker(connection, WorkType.RECEIVE));
					} catch (IOException e) {
						terminate();
					}
				}	
			}
		}).start();
	}

	public void terminate() {
		//close the client socket
		try {
			clientSocket.close();
		} catch (Exception e) {
		}
		
		//close the server socket
		try {
			serverSocket.close();
		} catch (IOException e) {
		}
		
		//server stops listening for connections
		isListening = false;
	}

	public void setIsPrimaryServer() {
		serverDispatcher.getCurrentServerInfo().setThisAsPrimary();
	}

	public void addPrimaryServer(String server) {
		serverDispatcher.getCurrentServerInfo().addLast(server);
	}
}
