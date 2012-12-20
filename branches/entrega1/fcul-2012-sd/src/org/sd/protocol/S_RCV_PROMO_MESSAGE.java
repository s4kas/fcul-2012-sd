package org.sd.protocol;

import java.io.Serializable;
import java.util.Date;

import org.sd.common.messages.Message;
import org.sd.data.Agenda;

public class S_RCV_PROMO_MESSAGE extends Message implements Serializable{

	private static final long serialVersionUID = -8646404723846857L;
	private PromotionMessage pmsg;
	
	public S_RCV_PROMO_MESSAGE(PromotionMessage pmsg) {
		this.timeStamp = new Date().getTime();
		messageProtocol = Protocol.S_C_RCV_AAD;
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

	public long getTimeStamp() {
		// TODO Auto-generated method stub
		return this.timeStamp;
	}
}
