package org.sd.protocol;

import org.sd.common.messages.Message;
import org.sd.data.Agenda;

public class S_C_RCV_AAD_MESSAGE extends Message{

	private static final long serialVersionUID = -86464047223846857L;
	private String text;
	
	public S_C_RCV_AAD_MESSAGE(String s) {
		messageProtocol = Protocol.C_REQ_AG;
		text = s; 
	}
	
	public <T> T getContent() {
		
		return (T) text;
	}
	
	@SuppressWarnings("unchecked")
	public Protocol getHeader() {
		return messageProtocol;
	}
}
