package org.sd.common.connection;

import java.io.IOException;
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
			
			//FIXME BM
			System.out.println("Sent: " + messageToSend.getContent());
			System.out.println("To: " + workingConnection.getSocket().getInetAddress());
			
			workingConnection.getOutputStream().flush();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void receiveMessage() {
		
		IMessage receivedMessage = null;
		
		try {
			while (workingConnection.getSocket().isConnected()) {
				//message received
				receivedMessage = (IMessage) workingConnection.getInputStream().readObject();
					
				//FIXME BM
				System.out.println("Received: " + receivedMessage.getContent());
				System.out.println("From: " + workingConnection.getSocket().getInetAddress());
					
				//post a new incoming message to the message pool
				IConnection incomingConnection = new Connection(receivedMessage, workingConnection);
				MessagePool messagePool = MessagePoolProxy.getInstance();
				messagePool.postIncomingConnection(incomingConnection);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
