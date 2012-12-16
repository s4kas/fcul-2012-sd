package org.sd.client;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import org.sd.client.ui.ClientUI;
import org.sd.common.messages.AddEventMessage;
import org.sd.common.messages.IMessage;
import org.sd.data.Evento;

public class ClientController {
	
	//C_RCV_SL - Processa a recepcao da lista de servidores
	//A_RCV_RDT - Processa a ordem de redirecionamento.
	//S_C_RCV_AAD - Processa a resposta a um pedido AAD previo.
	//A_RCV_AG - Processa a recepção de uma AGenda
	//S_C_RCV_HS - Processa a aceitação de HS dum servidor.
	
	private static ClientFacade clientFacade; 
	
	public static void main(String[] args) {
		//start ui
		SwingUtilities.invokeLater(new Runnable() {
	    	public void run(){  
	    		ClientUI.buildUI();
	    		updateStatus();
	    	}
	    });
	}
	
	public static void connect() {
		//start connection to server
		if (clientFacade == null) {
			clientFacade = new ClientFacade();
		}
		
		clientFacade.initialize(ClientConfigProxy.getConfig());
		updateStatus();
	}
	
	private void sendHandShake() {
		
	}
	
	private void sendAgendaRequest() {
		
	}
	
	public static void disconnect() {
		if (clientFacade != null) {
			clientFacade.terminate();
		}
		updateStatus();
	}
	
	public static void updateStatus() {
		if (clientFacade == null) {
			ClientUI.updateStatus(false,false);
		} else {
			ClientUI.updateStatus(clientFacade.isConnected, clientFacade.isHandShaked);
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
		AddEventMessage message = new AddEventMessage(ev);
		
		//try to send the message
		clientFacade.sendMessage(message);
		
		return true;
	}
	
	public static void deleteEvent(String id) {
		
	}
	
	public static void receiveMessage(IMessage message) {
		
	}
	
	public static List getEventsForDayMonthYear(int day, int month, int year) {
		//System.out.println(day+" "+ month+" "+year);
		return new ArrayList();
	}
	
	public static boolean eventsForDayMonthYear(int day, int month, int year) {
		return !getEventsForDayMonthYear(day,month,year).isEmpty();
	}
}
