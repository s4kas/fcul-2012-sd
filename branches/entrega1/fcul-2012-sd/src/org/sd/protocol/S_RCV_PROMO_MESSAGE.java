package org.sd.protocol;

import java.io.Serializable;

import org.sd.common.messages.Message;

public class S_RCV_PROMO_MESSAGE extends Message implements Serializable{

	private static final long serialVersionUID = -8646404723846857L;
	private PromotionMessage pmsg;
	
	public S_RCV_PROMO_MESSAGE(PromotionMessage pmsg) {
		messageProtocol = Protocol.S_S_RCV_PROMO;
		this.pmsg = pmsg; 
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getContent() {
		return (T) pmsg;
	}
	
	@SuppressWarnings("unchecked")
	public Protocol getHeader() {
		return messageProtocol;
	}
}
