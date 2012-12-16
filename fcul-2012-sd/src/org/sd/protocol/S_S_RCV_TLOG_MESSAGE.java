package org.sd.protocol;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import org.sd.common.messages.IMessage;
import org.sd.common.messages.Message;

public class S_S_RCV_TLOG_MESSAGE extends Message implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1948194365812438073L;
	private String text;
	private LinkedList<IMessage> actionSet;
	
	public S_S_RCV_TLOG_MESSAGE(List<IMessage> l) {
		messageProtocol = Protocol.C_REQ_AG;
		actionSet.addAll(l);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getContent() {
		
		return (T) this.actionSet;
	}
	
	@SuppressWarnings("unchecked")
	public Protocol getHeader() {
		return messageProtocol;
	}
}
