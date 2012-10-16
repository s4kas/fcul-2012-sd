package org.sd.implementation;

import org.sd.common.implementation.ServerConfigProxy;
import org.sd.common.implementation.ServerFacade;


public class TestServerFacade {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//init server facade
		ServerFacade serverFacade = new ServerFacade();
		serverFacade.initialize(new ServerConfigProxy());

		
	}

}
