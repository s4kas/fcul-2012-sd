package org.sd.common.connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import org.sd.common.messages.IMessage;
import org.sd.server.message.MessagePool;
import org.sd.server.message.MessagePoolProxy;

public class ConnectionWorker implements Runnable {
	
	private enum WorkType { SEND, RECEIVE };
	
	private WorkType workType;
	private Socket incomingSocket;
	private Connection outgoingConnection;
	
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	
	public ConnectionWorker(Socket clientSocket) {
		this.workType = WorkType.RECEIVE;
		this.incomingSocket = clientSocket;
	}
	
	public ConnectionWorker(IConnection connection) {
		this.workType = WorkType.SEND;
		this.outgoingConnection = (Connection) connection;
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
			//parse the connection
			IMessage messageToSend = outgoingConnection.getMessage();
			
			//try to start the ouput stream
			oos = new ObjectOutputStream(outgoingConnection.getSocket().getOutputStream());
			
			//try to send the message
			oos.writeObject(messageToSend);
			
			//close the streams
			oos.flush();
			oos.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void receiveMessage() {
		
		IMessage receivedMessage = null;
		
		try {
			//try to start the input streams
			ois = new ObjectInputStream(incomingSocket.getInputStream());
			
			//try to cast the object to a message
			receivedMessage = (IMessage) ois.readObject();
			
			//store the message in the MessagePool
			System.out.println(receivedMessage.getContent());
			
			//post a new incoming message to the message pool
			IConnection incomingConnection = new Connection(receivedMessage, incomingSocket);
			MessagePool messagePool = MessagePoolProxy.getInstance();
			messagePool.postIncomingConnection(incomingConnection);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
