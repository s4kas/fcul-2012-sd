package org.sd.client;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.SwingUtilities;
import org.sd.client.ui.ClientUI;
import org.sd.common.messages.IMessage;
import org.sd.data.Agenda;
import org.sd.data.Evento;
import org.sd.protocol.A_REQ_AG_MESSAGE;
import org.sd.protocol.C_S_REQ_ADD_MESSAGE;
import org.sd.protocol.C_S_REQ_ALT_MESSAGE;
import org.sd.protocol.C_S_REQ_DEL_MESSAGE;
import org.sd.protocol.C_S_REQ_HS_MESSAGE;

public class ClientController {

	private static ClientFacade clientFacade;
	private static Agenda agenda = new Agenda();
	private static LinkedList<String> serverList = new LinkedList<String>();
	
	private static boolean isHandShaked = false;
	
	public static void main(String[] args) {
		//start ui
		SwingUtilities.invokeLater(new Runnable() {
	    	public void run(){  
	    		ClientUI.buildUI();
	    		start();
	    	}
	    });
	}
	
	public static void start() {
		if (clientFacade == null) {
			clientFacade = new ClientFacade();
			clientFacade.initialize(ClientConfigProxy.getConfig(true));
		}
		
		(new Thread() {
			public void run(){
				//start connection to server
				ClientController.updateStatus();
				clientFacade.start();
		
				if (ClientFacade.isConnected) {
					waiting(5000);
					sendHandShake();
				}
			}
		}).start();
	}
	
	private static boolean sendHandShake() {
		//not connected
		if (clientFacade == null || !ClientFacade.isConnected) {
			return false;
		}
		
		//init and send a new message
		IMessage handShake = new C_S_REQ_HS_MESSAGE();
		clientFacade.sendMessage(handShake);
		
		updateRecentEvents("Client - Handshake sent.");
		return true;
	}
	
	public static void stop() {
		if (clientFacade != null) {
			clientFacade.terminate();
		}
		updateStatus();
	}

	public static void updateHandShakeStatus(boolean status) {
		if (clientFacade == null) {
			isHandShaked = false;
			return;
		}
		isHandShaked = status;
	}
	
	public static void updateStatus() {
		if (clientFacade == null) {
			ClientUI.updateStatus(false);
		} else {
			ClientUI.updateStatus(ClientFacade.isConnected);
		}
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
	
	public static void receiveAgenda(Agenda newAgenda) {
System.out.println("newReceiveAgenda:"+newAgenda.ListEventos());
		agenda.ListEventos().addAll(newAgenda.ListEventos());
System.out.println("agendaDoCliente:"+agenda.ListEventos());
		ClientUI.updateCalendarFrame();
	}

	public static void receiveServerList(List<String> content) {
		if (serverList == null) {
			serverList = new LinkedList<String>();
		}
		serverList.clear();
		serverList.addAll(content);
		
		//save in properties
		ClientConfig cf = (ClientConfig) ClientConfigProxy.getConfig(false);
		cf.setServerList(content);
		cf.saveConfig();
	}
	
	public static void redirectToServer(String content) {
		if (serverList == null) {
			serverList = new LinkedList<String>();
		}
		serverList.addFirst(content);
		
		//save in properties
		ClientConfig cf = (ClientConfig) ClientConfigProxy.getConfig(false);
		cf.setServer(content);
		cf.saveConfig();
		
		//stop the client
		ClientController.stop();
	}
	
	public static boolean sendAgendaRequest() {
		//not connected
		if (clientFacade == null || !ClientFacade.isConnected || !isHandShaked) {
			return false;
		}
		
		A_REQ_AG_MESSAGE message = new A_REQ_AG_MESSAGE();
		
		//try to send the message
		clientFacade.sendMessage(message);
						
		updateRecentEvents("Client - Sent Request for Agenda");
		
		return true;
	}

	public static boolean modifyEvent(int day, int month, int year,
			int startHour, int startMinute, int endHour, int endMinute,
			String title, String contentText) {
		//not connected
		if (clientFacade == null || !ClientFacade.isConnected || !isHandShaked) {
			return false;
		}
		
		//create new event message
		Evento ev = new Evento(year,month,day,startHour, startMinute,
				year,month,day,endHour,endMinute, 
				(title + " - " + contentText),"");
		C_S_REQ_ALT_MESSAGE message = new C_S_REQ_ALT_MESSAGE(ev);
		agenda.alterEvento(ev);
		ClientUI.updateCalendarFrame();
				
		//try to send the message
		clientFacade.sendMessage(message);
				
		updateRecentEvents("Client - Sent Modify Event: " + message.getContent().getDescript());
				
		return true;
	}
	
	public static boolean addEvent(int day, int month, int year, int startHour,
			int startMinutes, int endHour, int endMinutes, String title, String content) {
		//not connected
		if (clientFacade == null || !ClientFacade.isConnected || !isHandShaked) {
			return false;
		}
		
		//create new event message
		Evento ev = new Evento(year,month,day,startHour, startMinutes,
				year,month,day,endHour,endMinutes, 
				(title + " - " + content),"");
		C_S_REQ_ADD_MESSAGE message = new C_S_REQ_ADD_MESSAGE(ev);
		agenda.addEvento(ev);
		ClientUI.updateCalendarFrame();
		
		//try to send the message
		clientFacade.sendMessage(message);
		
		updateRecentEvents("Client - Sent Add Event: " + message.getContent().getDescript());
		
		return true;
	}
	
	public static boolean deleteEvent(Evento ev) {
		if (clientFacade == null || !ClientFacade.isConnected || !isHandShaked) {
			return false;
		}
		
		C_S_REQ_DEL_MESSAGE message = new C_S_REQ_DEL_MESSAGE(ev);
		agenda.removesEvento(ev);
		ClientUI.updateCalendarFrame();
		
		//try to send message
		clientFacade.sendMessage(message);
		
		updateRecentEvents("Client - Sent Del Event: " + message.getContent().getDescript());
		
		return true;
	}
	
	private static void waiting(int mili) {
		try {
			Thread.sleep(mili);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
