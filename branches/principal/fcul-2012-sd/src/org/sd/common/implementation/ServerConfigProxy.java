package org.sd.common.implementation;

import org.sd.common.structure.IConfig;
import org.sd.common.structure.IConfiguration;

public class ServerConfigProxy implements IConfiguration {

	IConfig serverConfig;
	
	public IConfig getConfig() {
		if (this.serverConfig == null) {
			//initialize new serverConfig
			serverConfig = new ServerConfig();
			serverConfig.initConfig();
		}
		return this.serverConfig;
	}

}
