package org.sd.server;

public class ServerConfigProxy {

	private static ServerConfig serverConfig;
	
	public static ServerConfig getConfig(boolean loadFromFile) {
		if (serverConfig == null || loadFromFile) {
			//initialize new serverConfig
			serverConfig = new ServerConfig();
			serverConfig.loadConfig();
		}
		return serverConfig;
	}

}
