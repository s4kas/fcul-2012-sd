package org.sd.server;

public class ServerConfigProxy {

	private static ServerConfig serverConfig;
	
	public static ServerConfig getConfig() {
		if (serverConfig == null) {
			//initialize new serverConfig
			serverConfig = new ServerConfig();
			serverConfig.loadConfig();
		}
		return serverConfig;
	}

}
