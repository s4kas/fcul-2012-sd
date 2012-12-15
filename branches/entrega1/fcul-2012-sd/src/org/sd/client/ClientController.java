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
	
	public static boolean addEvent(int day, int month, int year, int startHour,
			int startMinutes, int endHour, int endMinutes, String title, String content) {
		Event ev = new Event(day,month,year,startHour, startMinutes, endHour,
				endMinutes, title,content);
		AddEventMessage message = new AddEventMessage(ev);
		clientFacade.sendMessage(message);
		IMessage receivedMessage = clientFacade.receiveMessage();
		if (receivedMessage == null) {
			return false;
		}
		return true;
	}
	
	public static void deleteEvent(String id) {
		
	}
	
	public static void receiveMessage(IMessage message) {
		
	}
}
