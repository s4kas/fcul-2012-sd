package org.sd.common.connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import org.sd.common.messages.IMessage;

public class Connection implements IConnection {
	
	private IMessage messageToSend;
	private Socket socket;
	private ObjectOutputStream outputStream;
	private ObjectInputStream inputStream;
	
	public Connection(IMessage messageToSend, IConnection connection) {
		this.messageToSend = messageToSend;
		this.socket = connection.getSocket();
		this.inputStream = connection.getInputStream();
		this.outputStream = connection.getOutputStream();
	}
	
	public Connection(IMessage messageToSend, Socket socket) {
		this.messageToSend = messageToSend;
		this.socket = socket;
		initStreams();
	}
	
	public Connection(Socket socket) {
		this.socket = socket;
		initStreams();
	}
	
	private void initStreams() {
		//start the streams for this client
		try {
			outputStream = new ObjectOutputStream(socket.getOutputStream());
			inputStream = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public IMessage getMessage() {
		return messageToSend;
	}
	
	public void setMessage(IMessage message) {
		this.messageToSend = message;
	}

	public Socket getSocket() {
		return socket;
	}
	
	public ObjectOutputStream getOutputStream() {
		return outputStream;
	}
	
	public ObjectInputStream getInputStream() {
		return inputStream;
	}
}
