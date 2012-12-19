package org.sd.implementation;

import org.sd.client.ClientConfigProxy;
import org.sd.client.ClientFacade;


public class TestClientFacade {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//init client facade
		ClientFacade clientFacade = new ClientFacade();
		clientFacade.initialize(ClientConfigProxy.getConfig(true));
		clientFacade.start();
		
	}
}
