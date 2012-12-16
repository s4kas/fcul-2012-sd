package org.sd.server.dispatcher;

import org.sd.server.connection.ConnectionPool;
import org.sd.server.connection.ConnectionPoolProxy;
import org.sd.server.connection.ConnectionWorker;
import org.sd.common.connection.IConnection;
import org.sd.server.connection.ConnectionWorker.WorkType;
import org.sd.server.message.MessagePool;
import org.sd.server.message.MessagePoolProxy;

public class ConnectionDispatcher implements IDispatcher {
	
	MessagePool messagePool = MessagePoolProxy.getInstance();
	ConnectionPool connectionPool = ConnectionPoolProxy.getInstance();
	
	public void update() {
		IConnection outgoingConnection = messagePool.takeOutgoingConnection();
		connectionPool.execute(new ConnectionWorker(outgoingConnection, WorkType.SEND));
	}

}
