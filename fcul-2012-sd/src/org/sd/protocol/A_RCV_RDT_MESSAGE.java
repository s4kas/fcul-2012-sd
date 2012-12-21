package org.sd.protocol;

import java.io.Serializable;
import java.util.Date;

import org.sd.common.messages.Message;

public class A_RCV_RDT_MESSAGE extends Message implements Serializable{

	private static final long serialVersionUID = -86464047223846857L;
	private String ip;
	
	public A_RCV_RDT_MESSAGE(String ip) {
		this.timeStamp = new Date().getTime();
		messageProtocol = Protocol.S_RCV_RDT;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getContent() {
		return (T) ip;
	}
	
	@SuppressWarnings("unchecked")
	public Protocol getHeader() {
		return messageProtocol;
	}

	public long getTimeStamp() {
		return this.timeStamp;
	}
}
