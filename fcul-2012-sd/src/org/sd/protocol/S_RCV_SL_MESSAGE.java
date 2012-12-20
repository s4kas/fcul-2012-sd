package org.sd.protocol;

import java.io.Serializable;
import java.util.List;
import org.sd.common.messages.Message;

public class S_RCV_SL_MESSAGE extends Message implements Serializable {
	
	private static final long serialVersionUID = 1481124312438073L;
	private List<String> serverList;
	
	public S_RCV_SL_MESSAGE(List <String> serverList) {
		messageProtocol = Protocol.C_RCV_SL;
		this.serverList=serverList;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getContent() {
		return (T) this.serverList;
	}
	
	@SuppressWarnings("unchecked")
	public Protocol getHeader() {
		return messageProtocol;
	}
}
