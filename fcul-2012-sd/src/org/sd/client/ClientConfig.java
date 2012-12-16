package org.sd.client;

import org.sd.common.IConfig;
import org.sd.common.properties.PropertiesManager;

public class ClientConfig implements IConfig {
	
	private String[] clientAddress;
	private int[] clientPort;
	private int connectionTimeout;
	
	public boolean loadConfig() {
		try {
			clientAddress = PropertiesManager.getClientProps(false).getProperty("clientAddress").split(","); 
			String[] ports = PropertiesManager.getClientProps(false).getProperty("clientPort").split(",");
			clientPort = new int[ports.length];
			for (int i = 0 ; i < ports.length; i ++) {
				clientPort[i] = Integer.parseInt(ports[i]);
			}
			connectionTimeout = Integer.parseInt(PropertiesManager.getClientProps(false).getProperty("connectionTimeout"));
		} catch (Exception e) {
			//TODO BM do something
			return false;
		}
		//initialization went smoothly
		return true;
	}

	public String[] getClientAddress() {
		return clientAddress;
	}

	public int[] getClientPort() {
		return clientPort;
	}
	
	public int getConnectionTimeout() {
		return connectionTimeout;
	}

	public boolean saveConfig() {
		// TODO Auto-generated method stub
		return false;
	}

}
