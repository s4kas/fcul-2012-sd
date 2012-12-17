package org.sd.protocol;

import java.util.Date;

import org.sd.common.messages.Message;
import org.sd.data.Evento;

public class C_S_REQ_ADD_MESSAGE extends Message {

	private static final long serialVersionUID = 1206263065834076604L;
	private Evento event;
	
	public C_S_REQ_ADD_MESSAGE(Evento event) {
		this.timeStamp = new Date().getTime();
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

	public long getTimeStamp() {

		return this.timeStamp;
	}
}
