package org.sd.protocol;

import org.sd.common.messages.Message;

public class A_REQ_AG_MESSAGE extends Message {

	private static final long serialVersionUID = 8559206170393566703L;
	
	public A_REQ_AG_MESSAGE() {
		this.messageProtocol = Protocol.A_REQ_AG;
	}
	
	@SuppressWarnings("unchecked")
	public String getContent() {
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Protocol getHeader() {
		return messageProtocol;
	}
}
