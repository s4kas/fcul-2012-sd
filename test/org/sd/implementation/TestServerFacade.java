package org.sd.implementation;

import org.sd.server.ServerConfigProxy;
import org.sd.server.ServerFacade;


public class TestServerFacade {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//init server facade
		ServerFacade serverFacade = new ServerFacade();
		serverFacade.initialize(ServerConfigProxy.getConfig());
		
	}

}
