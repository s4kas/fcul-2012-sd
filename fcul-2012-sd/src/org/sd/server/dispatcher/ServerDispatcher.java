package org.sd.server.dispatcher;


import java.util.ArrayList;

import org.sd.common.IDispatcher;
import org.sd.common.Protocol;
import org.sd.common.messages.AddEventMessage;
import org.sd.common.messages.Event;
import org.sd.common.messages.IMessage;
import org.sd.server.connection.Connection;
import org.sd.server.connection.IConnection;
import org.sd.server.message.MessagePool;
import org.sd.server.message.MessagePoolProxy;

public class ServerDispatcher implements IDispatcher {
	
	ArrayList <DispatcherProcess> dpList = new ArrayList<DispatcherProcess>();
	
	MessagePool messagePool = MessagePoolProxy.getInstance();
	
	public ServerDispatcher (){
	}	
	
	
	/*****************************************************
	 * Remove da lista os processos terminados.
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
	 * Nova mensagem para processamento.
	 */
	public void update() {
		
		//limpa lista de threads
		cleanUp();
		

		//mensagem a processar
		IMessage message = (messagePool.takeIncomingConnection()).getMessage();
		//protocolo associado a esta mensagem
		Protocol protocol = message.getHeader();
		System.out.println(protocol + " - " + message.getContent());

		//tenta despachar.
		try{
			DispatcherProcess dp =  new DispatcherProcess(message);		
			dp.run();
			dpList.add(dp);
		} catch (Exception e){
			System.out.println(protocol + " - cuspiu a mensagem a mensagem!");
		}
	}
}
	