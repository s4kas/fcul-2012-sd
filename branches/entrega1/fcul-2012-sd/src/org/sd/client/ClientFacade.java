package org.sd.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.sd.common.IAgentFacade;
import org.sd.common.ICommunicator;
import org.sd.common.IConfig;
import org.sd.common.IMessage;
import org.sd.common.connection.ConnectionPool;
import org.sd.common.connection.ConnectionPoolProxy;
import org.sd.common.connection.ConnectionWorker;
import org.sd.common.messages.HandShakeMessage;

public class ClientFacade implements IAgentFacade, ICommunicator {
	
	private Socket clientSocket;
	private ClientConfig clientConfig;
	private boolean isConnected;
	
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
		
		//send the handShake to the server
		sendMessage(new HandShakeMessage());
	}
	
	public void sendMessage(IMessage message) {
		
		try {
			//start listening
			clientSocket = new Socket(clientConfig.getClientAddress(), clientConfig.getClientPort());
			
			//set timeout
			clientSocket.setSoTimeout(clientConfig.getConnectionTimeout());
			
			//send message
			connectionPool.execute(new ConnectionWorker(clientSocket, message));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public IMessage receiveMessage() {
		//FIXME BM
		return null;
	}

	public void terminate() {
		
	    try {
	    	//send the end protocol to the server
	    	//workQueue.execute(new ConnectionWorker(clientSocket, new EndConnectionMessage()));
	    	
	    	//close the socket
			clientSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    //stop listening
	    isConnected = false;
	}
}
