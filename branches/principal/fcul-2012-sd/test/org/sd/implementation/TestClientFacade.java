package org.sd.implementation;

import org.sd.common.implementation.ClientConfigProxy;
import org.sd.common.implementation.ClientFacade;

public class TestClientFacade {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//init server facade
		ClientFacade clientFacade = new ClientFacade();
		clientFacade.initialize(new ClientConfigProxy());

		
	}
}
