package org.sd.protocol;

import java.io.Serializable;
import java.util.List;
import org.sd.common.messages.Message;

public class S_S_RCV_HS_MESSAGE extends Message implements Serializable {
	
	private static final long serialVersionUID = 148194312438073L;
	
	public S_S_RCV_HS_MESSAGE() {
		messageProtocol = Protocol.S_S_RCV_HS;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getContent() {
		return (T) null;
	}
	
	@SuppressWarnings("unchecked")
	public Protocol getHeader() {
		return messageProtocol;
	}
}
