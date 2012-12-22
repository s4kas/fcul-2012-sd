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
import org.sd.server.ServerController;
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
		
		try {
			ServerController.addToInfoConsole("Server - Sending message to: "+outgoingConnection.getSocket().getInetAddress());
			ServerController.addToInfoConsole("Server - Message contains protocol: "+outgoingConnection.getMessage().getHeader());
		} catch (Exception e) {	}
		
		//notify observers
		notifyConnectionObservers();
		
		return true;
	}
	
	public synchronized void postToAllServers(IMessage message, List<String> outList) {
		int port = ServerConfigProxy.getConfig(false).getServerPort();
System.out.println("postToAllServers:"+outList);
		for (String out : outList) {
			try {
				outgoingQueue.addLast(new Connection(message, new Socket(out,port)));
				
				try {
					ServerController.addToInfoConsole("Server - Sending message to: "+out);
					ServerController.addToInfoConsole("Server - Message contains protocol: "+message.getHeader());
				} catch (Exception e) {	}
				
				//notify observers
				notifyConnectionObservers();
			} catch (UnknownHostException e) {
				ServerController.addToInfoConsole("MessagePool - Whois "+out+":"+port+" ? Dunno!!!");
			} catch (IOException e) {
				ServerController.addToInfoConsole("MessagePool - Couldn't connect to: "+out+":"+port);
			}
		}
	}
	
	public synchronized void postToServer(IMessage message, String server) {
		int port = ServerConfigProxy.getConfig(false).getServerPort();

		try {
			outgoingQueue.addLast(new Connection(message, new Socket(server,port)));
			
			try {
				ServerController.addToInfoConsole("Server - Sending message to: "+server);
				ServerController.addToInfoConsole("Server - Message contains protocol: "+message.getHeader());
			} catch (Exception e) {	}
	
			//notify observers
			notifyConnectionObservers();
		} catch (UnknownHostException e) {
			ServerController.addToInfoConsole("MessagePool - Whois "+server+":"+port+" ? Dunno!!!");
		} catch (IOException e) {
			ServerController.addToInfoConsole("MessagePool - Couldn't connect to: "+server+":"+port);
		}
	}

	@Override
	public synchronized boolean postIncomingConnection(IConnection incomingConnection) {
		//add a message to the queue
		incomingQueue.addLast(incomingConnection);
		
		try {
			ServerController.addToInfoConsole("Server - Received message from: "+incomingConnection.getSocket().getInetAddress());
			ServerController.addToInfoConsole("Server - Message contains protocol: "+incomingConnection.getMessage().getHeader());
		} catch (Exception e) {	}
		
		//warn the observers
		notifyServerSideObservers();
		
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
	
