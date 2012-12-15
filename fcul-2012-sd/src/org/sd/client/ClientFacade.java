package org.sd.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.sd.client.messages.HandShakeMessage;
import org.sd.common.IAgentFacade;
import org.sd.common.ICommunicator;
import org.sd.common.IConfig;
import org.sd.common.connection.Connection;
import org.sd.common.messages.IMessage;

public class ClientFacade implements IAgentFacade, ICommunicator {
	
	private Socket clientSocket;
	private ClientConfig clientConfig;
	private boolean isConnected = false;
	private boolean isHandShaked = false;
	
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
			clientSocket.setSoTimeout(clientConfig.getConnectionTimeout());
			
			this.isConnected = true;
			
		} catch (UnknownHostException e) {
			terminate();
			e.printStackTrace();
		} catch (IOException e) {
			terminate();
			e.printStackTrace();
		}
		
		//start new connection
		connection = new Connection(clientSocket);
		
		//init and send a new message
		IMessage handShake = new HandShakeMessage();
		sendMessage(handShake);
		//receive the result
		IMessage receivedMessage = receiveMessage();
		//send to controller to process
		ClientController.receiveMessage(receivedMessage);
	}
	
	public void sendMessage(IMessage message) {
		if (connection == null || !isConnected) {
			initialize(ClientConfigProxy.getConfig());
		}
		
		try {
			//write msg
			connection.getOutputStream().writeObject(message);
			//receive msg
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public IMessage receiveMessage() {
		try {
			return (IMessage) connection.getInputStream().readObject();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	    isHandShaked = false;
	}
}
