package org.sd.client;

import org.sd.common.IConfig;

public class ClientConfigProxy {
	
	private static ClientConfig clientConfig;
	
	public static synchronized IConfig getConfig() {
		if (clientConfig == null) {
			//initialize new clientConfig
			clientConfig = new ClientConfig();
			clientConfig.loadConfig();
		}
		return clientConfig;
	}

	public static void saveConfig(ClientConfig config) {
		// TODO Auto-generated method stub
		
	}

}
