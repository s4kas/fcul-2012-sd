package org.sd.common.structure;

public interface IAgent {
	
	public static enum AgentType {
		SERVER, CLIENT
	};

	public AgentType getAgentType();
}
