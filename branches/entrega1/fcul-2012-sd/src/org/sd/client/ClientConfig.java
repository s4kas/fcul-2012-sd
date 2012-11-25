package org.sd.client;

import org.sd.common.IConfig;

public class ClientConfig implements IConfig {
	
	private String clientAddress;
	private int clientPort;
	private int connectionTimeout;
	
	public boolean loadConfig() {
		//FIXME BM implement loadProperties
		clientAddress = "localhost";
		clientPort = 1500;
		connectionTimeout = 15000;
		
		//initialization went smoothly
		return true;
	}

	public String getClientAddress() {
		return clientAddress;
	}

	public int getClientPort() {
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
