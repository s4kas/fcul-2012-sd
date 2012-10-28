package org.sd.common.messages;

import org.sd.common.Protocol;

public class HandShakeMessage extends Message {

	private static final long serialVersionUID = -8646404718005846857L;
	
	public HandShakeMessage() {
		messageProtocol = Protocol.CLIENT_REQUEST_HANDSHAKE;
	}
	
	public <T> T getContent() {
		return null;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Protocol getHeader() {
		return messageProtocol;
	}

}
