package org.sd.common.connection;

public class ConnectionPoolProxy {
	
	private static ConnectionPool connectionPool;
	private static int nThreads;
	
	public static synchronized ConnectionPool getInstance() {
		if (nThreads <= 0) {
			//TODO BM lanar excepcao de config
			return null;
		}
		
		if (connectionPool == null) {
			connectionPool = new ConnectionPool(nThreads);
		}
		
		return connectionPool;
	}
	
	public static void setNThread(int numberThreads) {
		nThreads = numberThreads;
	}
}
