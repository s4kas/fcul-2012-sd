package org.sd.protocol;

import java.io.Serializable;

import org.sd.common.messages.Message;
import org.sd.data.Agenda;

public class A_RCV_AG_MESSAGE extends Message implements Serializable{

	private static final long serialVersionUID = -86464047223846857L;
	private Agenda agenda;
	
	public A_RCV_AG_MESSAGE(Agenda a) {
		messageProtocol = Protocol.C_REQ_AG;
	}
	
	public <T> T getContent() {
		return (T) agenda;
	}
	
	@SuppressWarnings("unchecked")
	public Protocol getHeader() {
		return messageProtocol;
	}
}
