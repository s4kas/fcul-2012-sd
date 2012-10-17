package org.sd.common.implementation;

import org.sd.common.structure.IConfig;

public class ServerConfig implements IConfig {
	
	private Agent server;
	
	public Boolean initConfig() {
		//FIXME BM implement loadProperties
		
		//set config for the server
		this.server = new Agent();
		this.server.setAgentType(Agent.Type.SERVER);
		this.server.setPort(1500);
		
		//initialization went smoothly
		return Boolean.TRUE;
	}

	public int getServerPort() {
		return this.server.getPort();
	}

}
