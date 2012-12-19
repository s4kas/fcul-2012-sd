package org.sd.client;

import org.sd.common.IConfig;

public class ClientConfigProxy {
	
	private static ClientConfig clientConfig;
	
	public static IConfig getConfig(boolean loadFromFile) {
		if (clientConfig == null || loadFromFile) {
			//initialize new clientConfig
			clientConfig = new ClientConfig();
			clientConfig.loadConfig();
		}
		return clientConfig;
	}
}
