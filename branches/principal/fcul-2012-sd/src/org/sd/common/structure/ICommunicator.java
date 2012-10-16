package org.sd.common.structure;

public interface ICommunicator {
	
	public void sendMessage(IMessage message);
	public IMessage receiveMessage();
	
}
