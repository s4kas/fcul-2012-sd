package org.sd.common.implementation;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import org.sd.common.structure.IAgentFacade;
import org.sd.common.structure.IConfiguration;
import org.sd.common.structure.IMessage;

public class ClientFacade implements IAgentFacade {
	
	private Socket clientSocket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	
	public IMessage receiveMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	public void sendMessage(IMessage message) {
		// TODO Auto-generated method stub

	}

	public void initialize(IConfiguration clientConfiguration) {
		if (clientConfiguration == null) {
			//TODO BM tratar erros
		}
		
		if (clientConfiguration.getConfig() == null) {
			//TODO BM tratar erros
		}
		
		if (!(clientConfiguration.getConfig() instanceof ClientConfig)) {
			//TODO BM tratar erros
		}
		
		//try to start the server socket
		ClientConfig clientConfig = (ClientConfig) clientConfiguration.getConfig();
		
		//try to initialize the socket
		try {
			clientSocket = new Socket(clientConfig.getClientAddress(), clientConfig.getClientPort());
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		try {
			clientSocket.setSoTimeout(15000);
			ois = new ObjectInputStream(clientSocket.getInputStream());
			oos = new ObjectOutputStream(clientSocket.getOutputStream());
			//FIXME BM thread.start();
		}
		catch (SocketTimeoutException e)
		{
			try{
			    clientSocket.close();
			    //TODO BM implement this
			}
			catch (Exception ex){
				//TODO BM implement this
			}
		}
		catch (Exception ex)
		{
			try{
				clientSocket.close();
				//TODO BM implement this
			}
			catch (Exception exc){
				//TODO BM implement this
			}
		}
	}

	public void terminate() {
		try{
			ois.close();
			oos.close();
		    clientSocket.close();
		}
		catch (IOException ex){
			//TODO BM implement this
		}
	}

}
