package org.sd.client.messages;

import org.sd.common.Protocol;
import org.sd.common.messages.Message;

public class HandShakeMessage extends Message {

	private static final long serialVersionUID = -8646404718005846857L;
	
	public HandShakeMessage() {
		messageProtocol = Protocol.C_S_REQ_HS;
	}
	
	public <T> T getContent() {
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public Protocol getHeader() {
		return messageProtocol;
	}

}
