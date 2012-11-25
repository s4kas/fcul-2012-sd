package org.sd.server.dispatcher;

import org.sd.common.IDispatcher;
import org.sd.common.connection.ConnectionPool;
import org.sd.common.connection.ConnectionPoolProxy;
import org.sd.common.connection.ConnectionWorker;
import org.sd.common.connection.IConnection;
import org.sd.common.connection.ConnectionWorker.WorkType;
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
