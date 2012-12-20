package org.sd.server;

import java.util.Properties;

import org.sd.common.IConfig;
import org.sd.common.properties.PropertiesManager;

public class ServerConfig implements IConfig {
	
	private Properties serverProperties;
	private final static String NUMBER_THREADS = "nThreads";
	private final static String SERVER_PORT = "serverPort";
	private final static String CONNECTION_TIMEOUT = "connectionTimeout";
	
	public boolean loadConfig() {
		if (serverProperties == null) {
			serverProperties = PropertiesManager.getServerProps();
		}
		//initialization went smoothly
		return true;
	}

	public int getServerPort() {
		if (serverProperties != null) {
			return Integer.parseInt(String.valueOf(serverProperties.get(SERVER_PORT)));
		}
		return -1;
	}
	
	public int getConnectionTimeout() {
		if (serverProperties != null) {
			return Integer.parseInt(String.valueOf(serverProperties.get(CONNECTION_TIMEOUT)));
		}
		return -1;
	}
	
	public int getNThreads() {
		if (serverProperties != null) {
			return Integer.parseInt(String.valueOf(serverProperties.get(NUMBER_THREADS)));
		}
		return -1;
	}

	public boolean saveConfig() {

		return false;
	}

}
