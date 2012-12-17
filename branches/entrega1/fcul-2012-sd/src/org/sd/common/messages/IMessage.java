package org.sd.common.messages;

import java.io.Serializable;


public interface IMessage extends Serializable {
	
	public <T> T getContent();
	public <T> T getHeader();
	public long getTimeStamp();
}
