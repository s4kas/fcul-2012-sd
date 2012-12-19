package org.sd.server;

import org.sd.common.IConfig;

public class ServerConfig implements IConfig {
	
	private int serverPort ;
	private int connectionTimeout;
	private int nThreads;
	
	public boolean loadConfig() {
		//FIXME BM implement loadProperties
		
		this.serverPort = 1500;
		this.connectionTimeout = 15000;
		this.nThreads = 10;
		
		//initialization went smoothly
		return true;
	}

	public int getServerPort() {
		return serverPort;
	}
	
	public int getConnectionTimeout() {
		return connectionTimeout;
	}
	
	public int getNThreads() {
		return this.nThreads;
	}

	public boolean saveConfig() {

		return false;
	}

}
