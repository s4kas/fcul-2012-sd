package org.sd.common;

public interface ICommunicator {
	
	public void sendMessage(IMessage message);
	public IMessage receiveMessage();
	
}
