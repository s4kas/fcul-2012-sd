package org.sd.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.sd.common.IAgentFacade;
import org.sd.common.ICommunicator;
import org.sd.common.IConfig;
import org.sd.common.connection.Connection;
import org.sd.common.connection.ConnectionPool;
import org.sd.common.connection.ConnectionPoolProxy;
import org.sd.common.connection.ConnectionWorker;
import org.sd.common.connection.ConnectionWorker.WorkType;
import org.sd.common.connection.IConnection;
import org.sd.common.messages.HandShakeMessage;
import org.sd.common.messages.IMessage;

public class ClientFacade implements IAgentFacade, ICommunicator {
	
	private Socket clientSocket;
	private ClientConfig clientConfig;
	private boolean isConnected = false;
	
	private Connection connection;
	private ConnectionPool connectionPool;

	public void initialize(IConfig clientConfiguration) {
		//do the validations
		if (!(clientConfiguration instanceof ClientConfig)) {
			//TODO BM tratar erros
		}
		
		//get the client config
		clientConfig = (ClientConfig) clientConfiguration;
		
		//start the WorkQueue
		ConnectionPoolProxy.setNThread(clientConfig.getNThreads());
		connectionPool = ConnectionPoolProxy.getInstance();
		
		try {
			//start remote socket
			clientSocket = new Socket(clientConfig.getClientAddress(), clientConfig.getClientPort());
			
			//set timeout
			//clientSocket.setSoTimeout(clientConfig.getConnectionTimeout());
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//init the streams
		connection = new Connection(clientSocket);
		
		//send the handShake to the server
		sendMessage(new HandShakeMessage());
		
		//start listening for server messages
		receiveMessage();
	}
	
	public void sendMessage(IMessage message) {
		//send message
		connection.setMessage(message);
		connectionPool.execute(new ConnectionWorker(connection, WorkType.SEND));
		
	}

	public IMessage receiveMessage() {
		//add the connection to the queue
		connectionPool.execute(new ConnectionWorker(connection, WorkType.RECEIVE));
		
		return null;
	}

	public void terminate() {
		
	    try {
	    	//send the end protocol to the server
	    	//workQueue.execute(new ConnectionWorker(clientSocket, new EndConnectionMessage()));
	    	
	    	//close the streams
	    	clientSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    //stop listening
	    isConnected = false;
	}
}
