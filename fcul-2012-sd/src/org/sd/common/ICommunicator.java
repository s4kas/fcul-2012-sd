package org.sd.common;

import org.sd.common.messages.IMessage;

public interface ICommunicator {
	
	public void sendMessage(IMessage message);
	public IMessage receiveMessage();
	
}
