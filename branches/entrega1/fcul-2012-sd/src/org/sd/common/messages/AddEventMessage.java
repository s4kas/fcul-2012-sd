package org.sd.common.messages;

import org.sd.common.implementation.Protocol;
import sistema.Event;

public class AddEventMessage extends Message {

	private static final long serialVersionUID = 1206263065834076604L;
	private sistema.Event event;
	
	public AddEventMessage(Event event) {
		this.event = event;
		this.messageProtocol = Protocol.CLIENT_REQUEST_ADD;
	}
	
	@SuppressWarnings("unchecked")
	public Event getContent() {
		return event;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Protocol getHeader() {
		return messageProtocol;
	}
}
