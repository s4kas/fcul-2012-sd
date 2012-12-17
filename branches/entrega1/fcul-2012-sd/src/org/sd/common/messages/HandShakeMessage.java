package org.sd.common.messages;

import java.util.Date;

import org.sd.protocol.Protocol;

public class HandShakeMessage extends Message {

	private static final long serialVersionUID = -8646404718005846857L;
	
	public HandShakeMessage() {
		this.timeStamp = new Date().getTime();
	//	messageProtocol = Protocol.CLIENT_REQUEST_HANDSHAKE;
	}
	
	public <T> T getContent() {
		return null;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Protocol getHeader() {
		return messageProtocol;
	}

	public long getTimeStamp() {
		return this.timeStamp;
	}

}
