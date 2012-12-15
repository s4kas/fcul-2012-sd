package org.sd.server.dispatcher;

import java.util.HashSet;
import java.util.Observable;
import java.util.Set;

import org.sd.common.connection.IConnection;

public abstract class Dispatchable extends Observable {
	
	private Set<ServerDispatcher> serverSideObservers = new HashSet<ServerDispatcher>();
	private Set<ConnectionDispatcher> connectionObservers = new HashSet<ConnectionDispatcher>();
	
	protected void addIncomingObserver(ServerDispatcher serverDispatcher) {
		serverSideObservers.add(serverDispatcher);
	}
	
	protected void addOutgoingObserver(ConnectionDispatcher connectionDispatcher) {
		connectionObservers.add(connectionDispatcher);
	}
	
	protected void notifyServerSideObservers() {
		for (ServerDispatcher serverDispatcher : serverSideObservers) {
			serverDispatcher.update();
		}
	}
	
	protected void notifyConnectionObservers() {
		for (ConnectionDispatcher connectionDispatcher : connectionObservers) {
			connectionDispatcher.update();
		}
	}
	
	public abstract boolean postOutgoingConnection(IConnection outgoingConnection);
	
	public abstract boolean postIncomingConnection(IConnection incomingConnection);
	
	public abstract IConnection takeOutgoingConnection();
	
	public abstract IConnection takeIncomingConnection();
}
