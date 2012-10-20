package org.sd.common.structure;

import java.io.Serializable;

public interface IMessage extends Serializable {
	
	public <T> T getContent();
	public <T> void setContent(T content);
	
	public IAgent getReceiver();
	
	public IAgent getSender();
	
}
