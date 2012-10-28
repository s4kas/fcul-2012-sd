package org.sd.common.messages;

import org.sd.common.Protocol;

public abstract class Message implements IMessage {

	private static final long serialVersionUID = -6045379790328162267L;
	protected org.sd.common.Protocol messageProtocol;

	@SuppressWarnings({ "unchecked" })
	public abstract Protocol getHeader();
}
