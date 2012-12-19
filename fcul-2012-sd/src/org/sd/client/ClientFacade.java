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
	
	private Socket clientSocket;
	private ClientConfig clientConfig;
	public static boolean isConnected = false;
	public static int handShakeTries = 0;
	public final static int MAX_HS_TRIES = 5;
	public final static int RECONNECT_SECONDS = 10;
	public boolean isHandShaked = false;
	private ClientDispatchable clientDispatchable;
	private static Timer timer;
	public static boolean isReconnecting = false;
	
	private Connection connection;

	public void initialize(IConfig clientConfiguration) {
		//do the validations
		if (!(clientConfiguration instanceof ClientConfig)) {
			//TODO BM tratar erros
		}
		
		//start the dispatching process
		clientDispatchable = new ClientDispatchable();
		clientDispatchable.addObserver(new ClientDispatcher());
		
		//get the client config
		clientConfig = (ClientConfig) clientConfiguration;
	}
	
	public void start() {
		String[] servers = clientConfig.getClientAddress();
		int port = clientConfig.getClientPort();
		int tries = 0;
				
		while (!isConnected && tries < servers.length) {
			String server = servers[tries];
				
			try {
				//start remote socket
				clientSocket = new Socket(server,port);
						
				//start new connection
				connection = new Connection(clientSocket);
						
				//set connected
				isConnected = true;
				isReconnecting = false;
						
			} catch (UnknownHostException e) {
				//set disconnected
				isConnected = false;
			} catch (IOException e) {
				//set disconnected
				isConnected = false;
			}
					
			tries++;
		}
				
		//nao consegui ligar
		if (!isConnected) {
			terminate();
			reconnect();
			return;
		}
			
		(new Thread() {
			public void run() {
				while (isConnected) {
					IMessage receivedMessage = receiveMessage();
					clientDispatchable.postMessage(receivedMessage);
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
				}	
			}
					
		}).start();
	}

	public IMessage receiveMessage() {
		try {
			return (IMessage) connection.getInputStream().readObject();
		} catch (IOException e) {
		} catch (ClassNotFoundException e) {
		}
		return null;
	}

	public void terminate() {
		
	    try {
	    	//close the streams
	    	clientSocket.close();
		} catch (Exception e) {
		}
	    
	    //stop listening
	    isConnected = false;
	    isHandShaked = false;
	}
	
	public void reconnect() {
		isReconnecting = true;
		timer = new Timer();
		timer.schedule(new ReconnectTask(), RECONNECT_SECONDS*1000);
	}
	
	class ReconnectTask extends TimerTask {

        public void run() {
        	ClientController.start();
        }
    }

}
