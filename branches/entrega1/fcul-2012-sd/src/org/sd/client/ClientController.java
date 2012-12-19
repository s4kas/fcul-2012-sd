package org.sd.client;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;
import org.sd.client.ui.ClientUI;
import org.sd.common.messages.IMessage;
import org.sd.data.Agenda;
import org.sd.data.Evento;
import org.sd.data.ServerList;
import org.sd.protocol.C_S_REQ_ADD_MESSAGE;
import org.sd.protocol.C_S_REQ_ALT_MESSAGE;
import org.sd.protocol.C_S_REQ_HS_MESSAGE;

public class ClientController {

	private static ClientFacade clientFacade;
	private static Agenda agenda = new Agenda();
	private static ServerList serverList = new ServerList();
	
	public static void main(String[] args) {
		//start ui
		SwingUtilities.invokeLater(new Runnable() {
	    	public void run(){  
	    		ClientUI.buildUI();
	    		updateStatus();
	    	}
	    });
	}
	
	public static void start() {
		if (clientFacade == null) {
			clientFacade = new ClientFacade();
			clientFacade.initialize(ClientConfigProxy.getConfig(true));
		}
		//start connection to server
		updateRecentEvents("Client - Trying to connect...");
		clientFacade.start();
		updateStatus();
		
		
		if (ClientFacade.isConnected) {
			updateRecentEvents("Client - Connected!");
			waiting(5000);
			sendHandShake();
		} else {
			updateStatus();
			updateRecentEvents("Client - Can't connect!");
			updateRecentEvents("Client - Will reconnect in "+ClientFacade.RECONNECT_SECONDS+" seg...");
		}
	}
	
	private static boolean sendHandShake() {
		//not connected
		if (clientFacade == null) {
			return false;
		}
		
		//init and send a new message
		IMessage handShake = new C_S_REQ_HS_MESSAGE();
		clientFacade.sendMessage(handShake);
		
		updateRecentEvents("Client - Handshake sent.");
		
		return true;

	}
	
	private static void sendAgendaRequest() {
		
	}
	
	public static void stop() {
		if (clientFacade != null) {
			clientFacade.terminate();
		}
		updateRecentEvents("Client - Disconnected!");
		updateStatus();
	}
	
	public static void updateStatus() {
		if (clientFacade == null) {
			ClientUI.updateStatus(false, false);
		} else {
			ClientUI.updateStatus(ClientFacade.isConnected, ClientFacade.isReconnecting);
		}
	}
	
	public static boolean addEvent(int day, int month, int year, int startHour,
			int startMinutes, int endHour, int endMinutes, String title, String content) {
		//not connected
		if (clientFacade == null) {
			return false;
		}
		
		//create new event message
		Evento ev = new Evento(year,month,day,startHour, startMinutes,
				year,month,day,endHour,endMinutes, 
				(title + " - " + content),"");
		C_S_REQ_ADD_MESSAGE message = new C_S_REQ_ADD_MESSAGE(ev);
		agenda.addEvento(ev);
		
		//try to send the message
		clientFacade.sendMessage(message);
		
		updateRecentEvents("Client - Sent Add Event: " + message.getContent().getDescript());
		
		return true;
	}
	
	public static void receiveAgenda(Agenda newAgenda) {
		agenda = new Agenda();
		agenda.ListEventos().addAll(newAgenda.ListEventos());
	}
	
	public static void deleteEvent(String id) {
		
	}
	
	public static void updateRecentEvents(String message) {
		ClientUI.updateRecentEvents(message);
	}
	
	public static List<Evento> getEventsForDayMonthYear(int day, int month, int year) {
		List<Evento> aux = new ArrayList<Evento>();

		for (Evento ev : agenda.ListEventos()) {
			if (ev.equalsByDayMonthYear(day, month, year)) {
				aux.add(ev);
			}
		}
		
		return aux;
	}
	
	public static boolean eventsForDayMonthYear(int day, int month, int year) {
		return !getEventsForDayMonthYear(day,month,year).isEmpty();
	}
	
	private static void waiting(int mili) {
		try {
			Thread.sleep(mili);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void updateHandShakeStatus(boolean status) {
		if (clientFacade == null) {
			return;
		}
		clientFacade.isHandShaked = status;
	}

	public static void receiveServerList(List<String> content) {
		//save locally
		serverList = new ServerList();
		serverList.listOfServers().addAll(content);
		
		//save in properties
		ClientConfig cf = (ClientConfig) ClientConfigProxy.getConfig(false);
		cf.setServerList(content);
		cf.saveConfig();
	}

	public static boolean modifyEvent(int day, int month, int year,
			int startHour, int startMinute, int endHour, int endMinute,
			String title, String contentText) {
		//not connected
		if (clientFacade == null) {
			return false;
		}
				
		//create new event message
		Evento ev = new Evento(year,month,day,startHour, startMinute,
				year,month,day,endHour,endMinute, 
				(title + " - " + contentText),"");
		C_S_REQ_ALT_MESSAGE message = new C_S_REQ_ALT_MESSAGE(ev);
		agenda.alterEvento(ev);
				
		//try to send the message
		clientFacade.sendMessage(message);
				
		updateRecentEvents("Client - Sent Modify Event: " + message.getContent().getDescript());
				
		return true;
	}
}
