package org.sd.client;

import javax.swing.SwingUtilities;

import org.sd.client.ui.ClientUI;
import org.sd.common.messages.AddEventMessage;
import org.sd.common.messages.Event;
import org.sd.common.messages.IMessage;

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
		clientFacade = new ClientFacade();
		clientFacade.initialize(ClientConfigProxy.getConfig());
		updateStatus();
	}
	
	public static void disconnect() {
		clientFacade.terminate();
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
