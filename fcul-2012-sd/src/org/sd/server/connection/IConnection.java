package org.sd.server.connection;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import org.sd.common.messages.IMessage;

public interface IConnection {
	
	public IMessage getMessage();
	public void setMessage(IMessage message);
	public Socket getSocket();
	public ObjectOutputStream getOutputStream();
	public ObjectInputStream getInputStream();
}
