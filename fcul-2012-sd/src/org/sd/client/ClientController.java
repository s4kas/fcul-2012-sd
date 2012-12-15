package org.sd.client;

import javax.swing.SwingUtilities;

import org.sd.client.ui.ClientUI;
import org.sd.common.messages.AddEventMessage;
import org.sd.common.messages.Event;
import org.sd.common.messages.IMessage;

public class ClientController {
	
	private static ClientFacade clientFacade; 
	
	public static void main(String[] args) {
		clientFacade = new ClientFacade();
		clientFacade.initialize(ClientConfigProxy.getConfig());
		SwingUtilities.invokeLater(new Runnable() {
	    	public void run(){  
	    		ClientUI.buildUI();
	    	}  
	    });
	}
	
	public static void addEvent(int day, int month, int year, 
			String title, String content) {
		Event ev = new Event(day,month,year,title,content);
		AddEventMessage message = new AddEventMessage(ev);
		clientFacade.sendMessage(message);
		IMessage receivedMessage = clientFacade.receiveMessage();
		if (receivedMessage == null) {
			//INFORMAR O UI que n foi possivel
			return;
		}
		
	}
	
	public static void deleteEvent(String id) {
		
	}
	
	public static void receiveMessage(IMessage message) {
		
	}
}
