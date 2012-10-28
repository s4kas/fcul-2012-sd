package org.sd.common.connection;

import java.net.Socket;

import org.sd.common.messages.IMessage;

public interface IConnection {
	
	public IMessage getMessage();
	public Socket getSocket();
	
}
