package org.sd.protocol;

import java.io.Serializable;

import org.sd.common.messages.Message;
import org.sd.data.Agenda;

public class S_RCV_RDT_MESSAGE extends Message implements Serializable{

	private static final long serialVersionUID = -86464047223846857L;
	private Agenda agenda;
	private String ip;
	
	public S_RCV_RDT_MESSAGE(String ip) {
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
}
