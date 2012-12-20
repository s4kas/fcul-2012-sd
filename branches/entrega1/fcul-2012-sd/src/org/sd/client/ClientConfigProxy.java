package org.sd.client;

public class ClientConfigProxy {
	
	private static ClientConfig clientConfig;
	
	public static ClientConfig getConfig(boolean loadFromFile) {
		if (clientConfig == null || loadFromFile) {
			//initialize new clientConfig
			clientConfig = new ClientConfig();
			clientConfig.loadConfig();
		}
		return clientConfig;
	}
}
