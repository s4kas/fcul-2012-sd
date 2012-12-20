package org.sd.protocol;

import org.sd.common.messages.Message;
import org.sd.data.Evento;

public class C_S_REQ_ALT_MESSAGE extends Message {

	private static final long serialVersionUID = 3780796414005043153L;
	private Evento event;
	
	public C_S_REQ_ALT_MESSAGE(Evento event) {
		this.event = event;
		this.messageProtocol = Protocol.C_S_REQ_ALT;
	}
	
	@SuppressWarnings("unchecked")
	public Evento getContent() {
		return event;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Protocol getHeader() {
		return messageProtocol;
	}
}

