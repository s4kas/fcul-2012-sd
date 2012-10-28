package org.sd.implementation;

import org.sd.client.ClientConfigProxy;
import org.sd.client.ClientFacade;
import org.sd.common.messages.AddEventMessage;
import org.sd.common.messages.Event;
import org.sd.common.messages.IMessage;


public class TestClientFacade {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//init client facade
		ClientFacade clientFacade = new ClientFacade();
		clientFacade.initialize(ClientConfigProxy.getConfig());
		
		//init and send a new message
		IMessage message = new AddEventMessage(new Event());
		clientFacade.sendMessage(message);
	}
}
