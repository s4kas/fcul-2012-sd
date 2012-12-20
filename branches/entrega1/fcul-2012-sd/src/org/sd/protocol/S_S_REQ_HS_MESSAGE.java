package org.sd.protocol;

import java.io.Serializable;
import org.sd.common.messages.Message;

public class S_S_REQ_HS_MESSAGE extends Message implements Serializable{

	private static final long serialVersionUID = -26464047223846857L;
	
	public S_S_REQ_HS_MESSAGE() {
		messageProtocol = Protocol.S_S_REQ_PROMO;
	}
	
	public <T> T getContent() {
		return (T) null;
	}
	
	@SuppressWarnings("unchecked")
	public Protocol getHeader() {
		return messageProtocol;
	}
}
