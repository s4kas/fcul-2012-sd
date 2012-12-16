package org.sd.common;

import org.sd.server.dispatcher.ServerDispatcher;

public interface IAgentFacade {
	
	public void initialize(IConfig config, ServerDispatcher runningServerDispatcher);
	public void terminate();
	
}
