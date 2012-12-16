package org.sd.client;

import java.util.LinkedList;
import java.util.Observable;
import org.sd.common.messages.IMessage;

public class ClientDispatchable extends Observable {
	
	private final LinkedList<IMessage> messageQueue;
	
	public ClientDispatchable() {
		messageQueue = new LinkedList<IMessage>();
	}
	
	public void postMessage(IMessage message) {
		this.messageQueue.add(message);
		setChanged();
		notifyObservers();
	}
	
	public IMessage getMessage() {
		if (!this.messageQueue.isEmpty()) {
			return messageQueue.remove();
		}
		return null;
	}
}