package org.sd.server.message;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;

import org.sd.common.connection.Connection;
import org.sd.common.connection.IConnection;
import org.sd.common.messages.IMessage;
import org.sd.server.ServerConfigProxy;
import org.sd.server.dispatcher.Dispatchable;
import org.sd.server.dispatcher.ServerDispatcher;
import org.sd.server.dispatcher.ConnectionDispatcher;

public class MessagePool extends Dispatchable {
	
	private final LinkedList<IConnection> incomingQueue;
	private final LinkedList<IConnection> outgoingQueue;
	
	public MessagePool() {
		incomingQueue = new LinkedList<IConnection>();
		outgoingQueue = new LinkedList<IConnection>();
	}
	
	public void addDispatchers(ServerDispatcher serverDispatcher, ConnectionDispatcher connectionDispatcher) {
		addIncomingObserver(serverDispatcher);
		addOutgoingObserver(connectionDispatcher);
	}

	@Override
	public synchronized boolean postOutgoingConnection(IConnection outgoingConnection) {
		//add a message to the queue
		outgoingQueue.addLast(outgoingConnection);
		
		//notify observers
		notifyConnectionObservers();
		
		// TODO Auto-generated method stub
		return true;
	}
	
	public synchronized void postMultipleOutgoingConnection(IMessage message, List<String> outList) {
		int port = ServerConfigProxy.getConfig().getServerPort();
		
		for (String out : outList) {
			try {
				incomingQueue.add(new Connection(message, new Socket(out,port)));
				
				//notify observers
				notifyConnectionObservers();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public synchronized boolean postIncomingConnection(IConnection incomingConnection) {
		//add a message to the queue
		incomingQueue.addLast(incomingConnection);
		
		//warn the observers
		notifyServerSideObservers();
		
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public IConnection takeOutgoingConnection() {
		return outgoingQueue.removeFirst();
	}

	@Override
	public IConnection takeIncomingConnection() {
		return incomingQueue.removeFirst();
	}
	
	
}
	
