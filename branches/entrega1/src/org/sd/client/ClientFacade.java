package org.sd.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import org.sd.common.IAgentFacade;
import org.sd.common.ICommunicator;
import org.sd.common.IConfig;
import org.sd.common.IMessage;
import org.sd.common.connection.ConnectionQueue;
import org.sd.common.connection.ConnectionQueueSingleton;
import org.sd.common.connection.ConnectionWorker;

public class ClientFacade implements IAgentFacade, ICommunicator {
	
	private Socket clientSocket;
	private ClientConfig clientConfig;
	private boolean isConnected;
	
	private ConnectionQueue workQueue;

	public void initialize(IConfig clientConfiguration) {
		//do the validations
		if (!(clientConfiguration instanceof ClientConfig)) {
			//TODO BM tratar erros
		}
		
		//get the client config
		clientConfig = (ClientConfig) clientConfiguration;
		
		//start the WorkQueue
		ConnectionQueueSingleton.setNThread(clientConfig.getNThreads());
		workQueue = ConnectionQueueSingleton.getInstance();
	}
	
	public void sendMessage(IMessage message) {
		
		try {
			//start listening
			clientSocket = new Socket(clientConfig.getClientAddress(), clientConfig.getClientPort());
			
			//set timeout
			clientSocket.setSoTimeout(clientConfig.getConnectionTimeout());
			
			//send message
			workQueue.execute(new ConnectionWorker(clientSocket, message));
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
		//try to close the socket
	    try {
			clientSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    //stop listening
	    isConnected = false;
	}
}
