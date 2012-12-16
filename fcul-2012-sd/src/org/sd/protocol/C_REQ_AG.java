package org.sd.protocol;

import org.sd.common.messages.Message;
import org.sd.data.Agenda;

public class C_REQ_AG extends Message{

	private static final long serialVersionUID = -86464047223846857L;
	private Agenda agenda;
	
	public C_REQ_AG(Agenda a) {
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
