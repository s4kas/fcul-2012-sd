package org.sd.common.messages;

import org.sd.data.Evento;
import org.sd.protocol.Protocol;

public class AddEventMessage extends Message {

	private static final long serialVersionUID = 1206263065834076604L;
	private Evento event;
	
	public AddEventMessage(Evento event) {
		this.event = event;
		this.messageProtocol = Protocol.C_S_REQ_ADD;
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
