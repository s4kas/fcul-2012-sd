package org.sd.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

import org.sd.common.IAgentFacade;
import org.sd.common.ICommunicator;
import org.sd.common.IConfig;
import org.sd.common.connection.Connection;
import org.sd.common.messages.IMessage;

public class ClientFacade implements IAgentFacade, ICommunicator {
	
	public final static int MAX_HS_TRIES = 5;
	public final static int RECONNECT_SECONDS = 10;
	
	private Socket clientSocket;
	private ClientConfig clientConfig;
	
	private MessageQueue messageQueue;
	private static Timer timer;
	
	public static boolean isConnected = false;
	public static boolean isReconnecting = false;
	public static boolean isConnecting = false;
	
	private Connection connection;

	public void initialize(IConfig clientConfiguration) {
		//do the validations
		if (!(clientConfiguration instanceof ClientConfig)) {
			return;
		}
		
		//start the dispatching process
		messageQueue = new MessageQueue();
		messageQueue.addObserver(new ClientDispatcher());
		
		//get the client config
		clientConfig = (ClientConfig) clientConfiguration;
	}
	
	public void start() {
		String[] servers = clientConfig.getClientAddress();
		int port = clientConfig.getClientPort();
		int tries = 0;
				
		while (!isConnected && tries < servers.length) {
			String server = servers[tries];
			ClientController.updateRecentEvents("Client - Trying server: " + server);
			
			try {
				//start remote socket
				isConnecting = true;
				clientSocket = new Socket(server,port);
						
				//start new connection
				connection = new Connection(clientSocket);
						
				//set connected
				isConnected = true;
				isReconnecting = false;
				isConnecting = false;
				ClientController.updateRecentEvents("Client - Connected to server: "+server);
						
			} catch (UnknownHostException e) {
				//set disconnected
				ClientController.updateRecentEvents("Client - Unknown Server: " + server);
				isConnected = false;
			} catch (IOException e) {
				//set disconnected
				ClientController.updateRecentEvents("Client - Problems on server: " + server);
				isConnected = false;
			}
			ClientController.updateStatus(); 
					
			tries++;
		}
				
		//nao consegui ligar ou desliguei-me
		if (!isConnected) {
			terminate();
			reconnect();
			return;
		}
			
		(new Thread() {
			public void run() {
				while (isConnected) {
					IMessage receivedMessage = receiveMessage();
					messageQueue.postMessage(receivedMessage);
				}
			}
		}).start();
	}
	
	public void sendMessage(final IMessage message) {
		
		(new Thread() {

			public void run() {
				try {
					//write msg
					connection.getOutputStream().writeObject(message);
				} catch (IOException e) {
					//falhou o envio da msg
					ClientController.updateRecentEvents("Client - Couldn't send: " + message);
				}	
			}
					
		}).start();
	}

	public IMessage receiveMessage() {
		try {
			return (IMessage) connection.getInputStream().readObject();
		} catch (IOException e) {
			ClientController.updateRecentEvents("Client - Server disconnected");
			terminate();
			reconnect();
		} catch (ClassNotFoundException e) {
			ClientController.updateRecentEvents("Client - Reading failed");
		}
		return null;
	}

	public void terminate() {
		ClientController.updateRecentEvents("Client - Disconnected");
	    try {
	    	//close the streams
	    	clientSocket.close();
		} catch (Exception e) {
		}
	    
	    //stop listening
	    isConnected = false;
	    ClientController.updateStatus();
	}
	
	public void reconnect() {
		if (timer != null) {
			timer.cancel();
			timer.purge();
		}
		isReconnecting = true;
		timer = new Timer();
		ClientController.updateRecentEvents("Client - Reconnecting in "+RECONNECT_SECONDS + " seconds.");
		timer.schedule(new ReconnectTask(), RECONNECT_SECONDS*1000);
	}
	
	class ReconnectTask extends TimerTask {

        public void run() {
        	ClientController.start();
        }
    }

}
