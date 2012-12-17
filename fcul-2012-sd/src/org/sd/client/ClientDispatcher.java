package org.sd.client;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.sd.common.messages.IMessage;
import org.sd.data.Agenda;
import org.sd.protocol.Protocol;

public class ClientDispatcher implements Observer {

	//C_RCV_SL - Processa a recepcao da lista de servidores
	//A_RCV_RDT - Processa a ordem de redirecionamento.
	//S_C_RCV_AAD - Processa a resposta a um pedido AAD previo.
	//A_RCV_AG - Processa a recepção de uma AGenda
	//S_C_RCV_HS - Processa a aceitação de HS dum servidor.

	@SuppressWarnings("unchecked")
	public void update(Observable obs, Object obj) {
		IMessage message = ((ClientDispatchable) obs).getMessage();
		
		if (message == null) {
			return;
		}
		
		Protocol protocol = message.getHeader();
		
		switch (protocol) {
		case C_RCV_SL:
			ClientController.receiveServerList((List<String>) message.getContent());
			ClientController.updateRecentEvents("Server - Sent Server List");
			break;
		case A_RCV_RDT:
			//redirect to another server
			break;
		case S_C_RCV_AAD:
			ClientController.updateRecentEvents("Server - " + String.valueOf(message.getContent()));
			break;
		case A_RCV_AG:
			ClientController.updateRecentEvents("Server - Sent Agenda");
			ClientController.receiveAgenda((Agenda) message.getContent());
			break;
		case S_C_RCV_HS:
			ClientController.updateHandShakeStatus(true);
			ClientController.updateRecentEvents("Server - Sent HandShake Ok");
			break;
		default:
			System.out.println("Não entendo o protocolo: " + protocol);
			break;
		}
	}
	
	
	
}
