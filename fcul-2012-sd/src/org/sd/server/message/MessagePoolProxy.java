package org.sd.server.message;

public class MessagePoolProxy {
	
	private static MessagePool messagePool;
	
	public static synchronized MessagePool getInstance() {
		
		if (messagePool == null) {
			messagePool = new MessagePool();
		}
		
		return messagePool;
	}
}
