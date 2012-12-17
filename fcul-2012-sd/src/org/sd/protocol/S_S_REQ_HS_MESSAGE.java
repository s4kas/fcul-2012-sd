package org.sd.protocol;

import java.io.Serializable;
import java.util.Date;

import org.sd.common.messages.Message;
import org.sd.data.Agenda;

public class S_S_REQ_HS_MESSAGE extends Message implements Serializable{

	private static final long serialVersionUID = -26464047223846857L;
	
	public S_S_REQ_HS_MESSAGE() {
		this.timeStamp = new Date().getTime();
		messageProtocol = Protocol.S_S_REQ_PROMO;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getContent() {
		return (T) null;
	}
	
	@SuppressWarnings("unchecked")
	public Protocol getHeader() {
		return messageProtocol;
	}

	public long getTimeStamp() {
		return this.timeStamp;
	}
}
