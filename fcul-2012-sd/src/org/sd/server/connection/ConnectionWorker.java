package org.sd.server.connection;

import java.io.IOException;

import org.sd.common.connection.Connection;
import org.sd.common.connection.IConnection;
import org.sd.common.messages.IMessage;
import org.sd.server.message.MessagePool;
import org.sd.server.message.MessagePoolProxy;

public class ConnectionWorker implements Runnable {
	
	public enum WorkType { SEND, RECEIVE };
	
	private WorkType workType;
	private IConnection workingConnection;
	
	public ConnectionWorker(IConnection connection, WorkType workType) {
		this.workType = workType;
		this.workingConnection = connection;
	}

	public void run() {
		switch(workType) {
		case SEND:
			sendMessage();
			break;
		case RECEIVE:
			receiveMessage();
			break;
		}
    }

	public void sendMessage() {			
		try {
			
			//get the message
			IMessage messageToSend = workingConnection.getMessage();
		
			//try to send the message
			workingConnection.getOutputStream().writeObject(messageToSend);
			
			workingConnection.getOutputStream().flush();
			
		} catch (IOException e) {
			//problems reading message
		}
	}

	public void receiveMessage() {
		
		IMessage receivedMessage = null;
		
		try {
			while (workingConnection.getSocket().isConnected()) {
				//message received
				receivedMessage = (IMessage) workingConnection.getInputStream().readObject();
					
				//post a new incoming message to the message pool
				IConnection incomingConnection = new Connection(receivedMessage, workingConnection);
				MessagePool messagePool = MessagePoolProxy.getInstance();
				messagePool.postIncomingConnection(incomingConnection);
			}
		} catch (IOException e) {
			//client disconnected
		} catch (ClassNotFoundException e) {
			//problems reading message
		}
	}
}
