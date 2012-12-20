package org.sd.protocol;

import java.io.Serializable;
import org.sd.common.messages.Message;

public class S_C_RCV_AAD_MESSAGE extends Message implements Serializable{

	private static final long serialVersionUID = -86464047223846857L;
	private String text;
	
	public S_C_RCV_AAD_MESSAGE(String s) {
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
}
