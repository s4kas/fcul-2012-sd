package org.sd.common.implementation;

import org.sd.common.structure.IAgent;

public class Agent implements IAgent {
	
	public static enum Type {
		SERVER, CLIENT
	};
	
	private int port;
	private String address;
	private Type agentType;
	
	public Agent() {
		
	}
	
	public Agent(Agent oldAgent) {
		copyFrom(oldAgent); 
	}
	
	public void setPort(int port) {
		this.port = port;
	}

	public int getPort() {
		return this.port;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAgentType(Type agentType) {
		this.agentType = agentType;
	}

	@SuppressWarnings("unchecked")
	public Type getAgentType() {
		return this.agentType;
	}
	
	private void copyFrom(Agent oldAgent) {
		try {
			this.port = oldAgent.getPort();
			this.address = oldAgent.getAddress();
			this.agentType = oldAgent.getAgentType();
		} catch (Exception e) {
			//TODO BM to implement
		}
	}
}