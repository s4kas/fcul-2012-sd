package org.sd.protocol;

import java.util.Date;

import org.sd.common.messages.Message;

public class C_S_REQ_HS_MESSAGE extends Message {

	private static final long serialVersionUID = -8646404718005846857L;
	
	
	public C_S_REQ_HS_MESSAGE() {
		this.timeStamp = new Date().getTime();
		messageProtocol = Protocol.C_S_REQ_HS;
	}
	
	public <T> T getContent() {
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public Protocol getHeader() {
		return messageProtocol;
	}
	
	public long getTimeStamp() {
		return this.timeStamp;
	}

}
