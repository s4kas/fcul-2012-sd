package org.sd.common.implementation;

import java.util.List;
import org.sd.common.structure.IConfig;

public class ClientConfig implements IConfig {
	
	private Agent client;
	private List<Agent> servers;
	
	public Boolean initConfig() {
		//FIXME BM implement loadProperties
		
		//set config for the client
		this.client = new Agent();
		this.client.setAddress("localhost");
		this.client.setPort(1500);
		
		//initialization went smoothly
		return Boolean.TRUE;
	}

	public String getClientAddress() {
		return this.client.getAddress();
	}

	public int getClientPort() {
		return this.client.getPort();
	}

}
