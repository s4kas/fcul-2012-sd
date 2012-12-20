package org.sd.common.messages;

import java.util.Calendar;

import org.sd.protocol.Protocol;

public abstract class Message implements IMessage {

	private static final long serialVersionUID = -6045379790328162267L;
	protected org.sd.protocol.Protocol messageProtocol;
	protected long timeStamp = Calendar.getInstance().getTimeInMillis();

	@SuppressWarnings({ "unchecked" })
	public abstract Protocol getHeader();

	public long getTimeStamp() {
		return this.timeStamp;
	}
}
