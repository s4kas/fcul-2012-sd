package org.sd.client;

import java.util.List;
import java.util.Properties;

import org.sd.common.IConfig;
import org.sd.common.properties.PropertiesManager;

public class ClientConfig implements IConfig {
	
	private Properties clientProperties;
	private final static String CLIENT_ADDRESS = "clientAddress";
	private final static String CLIENT_PORT = "clientPort";
	private final static String CONNECTION_TIMEOUT = "connectionTimeout";
	
	public boolean loadConfig() {
		if (clientProperties == null) {
			clientProperties = PropertiesManager.getClientProps();
		}
		//initialization went smoothly
		return true;
	}
	
	public void setServerList(List<String> servers) {
		if (clientProperties != null) {
			StringBuffer out = new StringBuffer(); 
			for (String server : servers) {
				out.append(server).append(",");
			}
			if (out.length() > 0) {
				out.replace(out.length()-1, out.length(), "");
			}
			clientProperties.setProperty(CLIENT_ADDRESS, out.toString());
		}
	}
	
	public void setServer(String server) {
		if (clientProperties != null) {
			StringBuffer out = new StringBuffer(server).append(",");
			out.append(String.valueOf(clientProperties.get(CLIENT_ADDRESS)));
			clientProperties.setProperty(CLIENT_ADDRESS, out.toString());
		}
	}

	public String[] getClientAddress() {
		if (clientProperties != null) {
			return String.valueOf(clientProperties.get(CLIENT_ADDRESS)).split(",");
		}
		return null;
	}

	public int getClientPort() {
		if (clientProperties != null) {
			return Integer.parseInt(String.valueOf(clientProperties.get(CLIENT_PORT)));
		}
		return -1;
	}
	
	public int getConnectionTimeout() {
		if (clientProperties != null) {
			return Integer.parseInt(String.valueOf(clientProperties.get(CONNECTION_TIMEOUT)));
		}
		return -1;
	}

	public boolean saveConfig() {
		return PropertiesManager.saveClientProps(clientProperties);
	}

}
