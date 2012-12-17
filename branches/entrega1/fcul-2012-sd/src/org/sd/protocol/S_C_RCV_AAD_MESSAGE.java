package org.sd.protocol;

import java.io.Serializable;
import java.util.Date;

import org.sd.common.messages.Message;
import org.sd.data.Agenda;

public class S_C_RCV_AAD_MESSAGE extends Message implements Serializable{

	private static final long serialVersionUID = -86464047223846857L;
	private String text;
	
	public S_C_RCV_AAD_MESSAGE(String s) {
		this.timeStamp = new Date().getTime();
		messageProtocol = Protocol.S_C_RCV_AAD;
		text = s; 
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getContent() {
		return (T) text;
	}
	
	@SuppressWarnings("unchecked")
	public Protocol getHeader() {
		return messageProtocol;
	}

	public long getTimeStamp() {
		// TODO Auto-generated method stub
		return this.timeStamp;
	}
}
