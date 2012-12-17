package org.sd.server.dispatcher;


import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import org.sd.common.connection.IConnection;
import org.sd.common.messages.IMessage;
import org.sd.data.ActionLog;
import org.sd.data.Agenda;
import org.sd.data.ClientList;
import org.sd.data.ServerList;
import org.sd.protocol.Protocol;
import org.sd.server.message.MessagePool;
import org.sd.server.message.MessagePoolProxy;

/**************************************************************************++
 * Controls message Dispatching . 
 */
public class ServerDispatcher implements IDispatcher {
	
	private ArrayList <DispatcherProcess> dpList 					= new ArrayList<DispatcherProcess>();
	private MessagePool messagePool 								= MessagePoolProxy.getInstance();
	private Agenda agenda;
	private ServerList currentServerList;
	private ClientList currentClientList;
	private ActionLog currentActionLog;
		
	public ServerDispatcher (Agenda a){
		this.agenda=a;
		currentServerList = new ServerList("localhost");
		currentActionLog = new ActionLog();
		currentClientList = new ClientList();
	}

	/*****************************************************
	 * Removes dead or timeout dispatcher process from memory 
	 */
	private void cleanUp(){
		
		ArrayList <DispatcherProcess> cleanUpDeadOnes = new ArrayList<DispatcherProcess>();
		
		for (DispatcherProcess dp : dpList){
			if (dp.hasEnded()){
				cleanUpDeadOnes.add(dp);
			}
		}
		dpList.removeAll(cleanUpDeadOnes);
	}
	
	
	/*******************************************************
	 * Message ready for processing.
	 */
	public void update() {
		
		//CLEAN UP REFERENCE TO ENDED THREADS ?? 
		cleanUp();
		//Message to process
		
		IConnection  thisconnection = messagePool.takeIncomingConnection();
		//Get protocol
				 
		//Goes for it.
		try{
			DispatcherProcess dp =  new DispatcherProcess(thisconnection,
															agenda,
															currentServerList,
															currentClientList,
															currentActionLog);		
			dp.run();
			dpList.add(dp);
		} catch (Exception e){
			System.out.println(thisconnection.toString()+ " - couldnt Swalow this one!");
		}
	}
}
	