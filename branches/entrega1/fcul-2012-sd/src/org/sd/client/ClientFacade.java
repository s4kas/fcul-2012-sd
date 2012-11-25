package org.sd.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.sd.common.IAgentFacade;
import org.sd.common.ICommunicator;
import org.sd.common.IConfig;
import org.sd.common.messages.HandShakeMessage;
import org.sd.common.messages.IMessage;
import org.sd.server.connection.Connection;

public class ClientFacade implements IAgentFacade, ICommunicator {
	
	private Socket clientSocket;
	private ClientConfig clientConfig;
	private boolean isConnected = false;
	
	private Connection connection;

	public void initialize(IConfig clientConfiguration) {
		//do the validations
		if (!(clientConfiguration instanceof ClientConfig)) {
			//TODO BM tratar erros
		}
		
		//get the client config
		clientConfig = (ClientConfig) clientConfiguration;
		
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
		
		
	}

	public IMessage receiveMessage() {
		//add the connection to the queue
		
		return null;
	}

	public void terminate() {
		
	    try {
	    	
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
