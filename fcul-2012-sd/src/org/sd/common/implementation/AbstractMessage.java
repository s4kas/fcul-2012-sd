package org.sd.common.implementation;

import org.sd.common.structure.IAgent;
import org.sd.common.structure.IMessage;

public abstract class AbstractMessage implements IMessage {

	private static final long serialVersionUID = 4099680272503840160L;
	private Agent sender, receiver;
	
	public IAgent getReceiver() {
		return this.receiver;
	}

	public void setReceiver(IAgent receiver) {
		try {
			//cast the received IAgent to Agent
			this.receiver = (Agent) receiver;
		} catch (ClassCastException cce) {
			//TODO BM to implement
		}
	}

	public IAgent getSender() {
		return this.sender;
	}

	public void setSender(IAgent sender) {
		try {
			//cast the received IAgent to Agent
			this.sender = (Agent) sender;
		} catch (ClassCastException cce) {
			//TODO BM to implement
		}
	}
}
