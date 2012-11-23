package org.sd.server.dispatcher;

import org.sd.common.messages.IMessage;
import org.sd.common.messages.Message;

public class DispatcherProcess implements Runnable{

	private boolean processing;
	private IMessage message;
	
	
	/**********************************************
	 * Inicializa o processo de dispatching
	 * @param message
	 * @throws myContentException
	 */
	public DispatcherProcess (IMessage message) throws Exception {
		this.message= message;
	
		//processa conteudo.
		
		//SEGUE FLUXOGRAMA.
		
		//Actualiza estados.
		
	}

	
	/******************************************
	 * Ainda está a processar
	 * @return
	 */
	public boolean hasEnded(){
		return !processing;
	}
	
	public void run() {
		processing=true;
	
		
		//qual o protocolo associado
//		Protocol protocol = message.getHeader();
	//	System.out.println(protocol + " - " + message.getContent());
		
		/*
		IMessage outgoingMessage = new AddEventMessage(new Event());
		IConnection outgoingConnection = new Connection(
				outgoingMessage, incomingConnection.getSocket());
		messagePool.postOutgoingConnection(outgoingConnection);
		*/
		
		/*		switch ( protocol ){
		case S_S_REQ_TLOG:break;
		case S_S_REQ_PROMO:break;
		case S_S_REQ_HS:break;
		case S_S_RCV_TLOG:break;
		case S_S_RCV_PROMO:break;
		case S_S_RCV_HS:break;
		case S_REQ_AG:break;
		case S_RCV_SL:break;
		case S_C_RCV_AAD:break;
		case C_S_REQ_HS:break;
		case C_S_REQ_AAD:break;
		case C_REQ_AG:break;
		case A_RCV_RDT:break;
		default:
			break;
			}*/


	}

}
