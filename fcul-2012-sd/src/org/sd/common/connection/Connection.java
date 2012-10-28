package org.sd.common.connection;

import java.io.ObjectOutputStream;
import java.net.Socket;

import org.sd.common.messages.IMessage;

public class Connection implements IConnection {
	
	private IMessage messageToSend;
	private Socket socket;

	public Connection(IMessage messageToSend, Socket socket) {
		this.messageToSend = messageToSend;
		this.socket = socket;
	}
	
	public IMessage getMessage() {
		return messageToSend;
	}

	public Socket getSocket() {
		return socket;
	}
}
