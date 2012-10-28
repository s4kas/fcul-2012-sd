package org.sd.server.dispatcher;

import java.util.HashSet;
import java.util.Observable;
import java.util.Set;

import org.sd.common.connection.IConnection;

public abstract class Dispatchable extends Observable {
	
	private Set<IncomingDispatcher> incomingObservers = new HashSet<IncomingDispatcher>();
	private Set<OutgoingDispatcher> outgoingObservers = new HashSet<OutgoingDispatcher>();
	
	protected void addIncomingObserver(IncomingDispatcher incomingDispatcher) {
		incomingObservers.add(incomingDispatcher);
	}
	
	protected void addOutgoingObserver(OutgoingDispatcher outgoingDispatcher) {
		outgoingObservers.add(outgoingDispatcher);
	}
	
	protected void notifyIncomingObservers() {
		for (IncomingDispatcher incomingDispatcher : incomingObservers) {
			incomingDispatcher.update();
		}
	}
	
	protected void notifyOutgoingObservers() {
		for (OutgoingDispatcher outgoingDispatcher : outgoingObservers) {
			outgoingDispatcher.update();
		}
	}
	
	public abstract boolean postOutgoingConnection(IConnection outgoingConnection);
	
	public abstract boolean postIncomingConnection(IConnection incomingConnection);
	
	public abstract IConnection takeOutgoingConnection();
	
	public abstract IConnection takeIncomingConnection();
}
