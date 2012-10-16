package org.sd.common.implementation;

import org.sd.common.structure.IConfig;
import org.sd.common.structure.IConfiguration;

public class ClientConfigProxy implements IConfiguration {
	
	IConfig clientConfig;
	
	public IConfig getConfig() {
		if (this.clientConfig == null) {
			//initialize new clientConfig
			clientConfig = new ClientConfig();
			clientConfig.initConfig();
		}
		return this.clientConfig;
	}

}
