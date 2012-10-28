package org.sd.common.connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import org.sd.common.ICommunicator;
import org.sd.common.IMessage;

public class ConnectionWorker implements Runnable {
	
	private enum WorkType { SEND, RECEIVE };
	
	private WorkType workType;
	private Socket clientSocket;
	private IMessage messageToSend;
	
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	
	public ConnectionWorker(Socket clientSocket) {
		this.workType = WorkType.RECEIVE;
		this.clientSocket = clientSocket;
	}
	
	public ConnectionWorker(Socket clientSocket, IMessage message) {
		this.workType = WorkType.SEND;
		this.messageToSend = message;
		this.clientSocket = clientSocket;
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
			//try to start the ouput stream
			oos = new ObjectOutputStream(clientSocket.getOutputStream());
			
			//try to send the message
			oos.writeObject(messageToSend);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void receiveMessage() {
		
		IMessage receivedMessage = null;
		
		try {
			//try to start the input streams
			ois = new ObjectInputStream(clientSocket.getInputStream());
			
			//try to cast the object to a message
			receivedMessage = (IMessage) ois.readObject();
			
			//store the message in the MessagePool
			System.out.println(receivedMessage.getContent());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
