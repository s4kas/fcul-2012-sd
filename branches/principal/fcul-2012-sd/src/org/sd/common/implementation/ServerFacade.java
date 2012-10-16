package org.sd.common.implementation;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.sd.common.structure.IAgentFacade;
import org.sd.common.structure.IConfiguration;
import org.sd.common.structure.IMessage;

import controloservidor.NetworkClientHandle;

public class ServerFacade implements IAgentFacade {
	
	private ServerSocket serverSocket;
	private Socket clientSocket;
	
	public void sendMessage(IMessage message) {
		// TODO Auto-generated method stub

	}

	public IMessage receiveMessage() {
		
		try {
			//FIXME BM chamar implementacao do thread pool
			clientSocket = serverSocket.accept();
			
			//TODO BM remove this println
			System.out.println("ServerFacade ClientSocket: " + clientSocket);
		} catch (Exception e) {
			//TODO BM tratar erros
		}
		return null;//return receivedMessage;
	}

	public void initialize(IConfiguration serverConfiguration) {
		if (serverConfiguration == null) {
			//TODO BM tratar erros
		}
		
		if (serverConfiguration.getConfig() == null) {
			//TODO BM tratar erros
		}
		
		if (!(serverConfiguration.getConfig() instanceof ServerConfig)) {
			//TODO BM tratar erros
		}
		
		//try to start the server socket
		ServerConfig serverConfig = (ServerConfig) serverConfiguration.getConfig();
		try {
			//start the socket
			serverSocket = new ServerSocket (serverConfig.getServerPort());
			
			//receive messages
			IMessage message = receiveMessage();
			
			//FIXME BM remove println
			System.out.println("ServerFacade ServerSocket: " + serverSocket);
		} catch (IOException e) {
			//TODO BM tratar erros
		}
	}

	public void terminate() {
		// TODO Auto-generated method stub

	}

}
