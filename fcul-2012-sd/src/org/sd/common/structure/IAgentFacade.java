package org.sd.common.structure;

public interface IAgentFacade extends ICommunicator {
	
	public void initialize(IConfiguration config);
	public void terminate();
	
}
