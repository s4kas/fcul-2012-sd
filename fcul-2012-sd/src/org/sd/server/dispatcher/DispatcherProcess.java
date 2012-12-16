package org.sd.server.dispatcher;

import java.util.Observable;
import java.util.Observer;

import org.sd.common.messages.IMessage;
import org.sd.common.messages.Message;
import org.sd.data.Agenda;
import org.sd.data.Evento;
import org.sd.protocol.A_RCV_AG_MESSAGE;
import org.sd.protocol.Protocol;
import org.sd.protocol.S_C_RCV_AAD_MESSAGE;
import org.sd.server.message.MessagePool;
import org.sd.server.message.MessagePoolProxy;

public class DispatcherProcess extends Observable implements Runnable {

	private boolean processing;
	private IMessage message;
	private boolean auth= false;
	private Protocol protocol; 
	private Agenda agenda;
	private boolean stateFlags_Promoting;
	private boolean stateFlags_Primary;
	private boolean stateFlags_Secondary;
	private MessagePool messagePool = MessagePoolProxy.getInstance();
	private IMessage send;
	
	
	
	/*************************************************************
	 * Validates Payload
	 * @param Protocol
	 * @return true if payloadValid
	 */
	private boolean isProtocolValid (Protocol p, Object content){
		//CHECK STATE FLAGS
		boolean isValid=false;
		
		switch (p){
		case C_S_REQ_AAD: if (message.getContent() instanceof Evento) isValid=true;break;
		case S_S_RCV_TLOG:break;
		case S_S_RCV_PROMO:break;
		case S_S_RCV_HS:break;
		case S_RCV_SL:break;
		case S_C_RCV_AAD:break;
		
		//no payload required. Ignore payload
		case C_S_REQ_HS:
		case S_S_REQ_PROMO:
		case S_S_REQ_HS:
		case S_S_REQ_TLOG:
		case S_REQ_AG:
		case C_REQ_AG:
		case A_RCV_RDT: isValid=true; break;
		}
		return isValid;
	}
	
	
	/**********************************************
	 * Initialize this thread
	 * @param message
	 * @throws myContentException
	 */
	public DispatcherProcess (IMessage message, Agenda a) throws Exception {
		
		//Get protocol
		Protocol protocol = message.getHeader();
		System.out.println(protocol + " - " + message.getContent());
		this.message= message;
		this.agenda = a;
		//Validate protocol content.
		if (isProtocolValid(protocol,message.getContent())){
			this.auth=true;
		}

	}

	
	/******************************************
	 * Check if is processing.
	 * @return
	 */
	public boolean hasEnded(){
		return !processing;
	}
	
	public void run() {
		processing=true;
		if (!auth) return;
		
		System.out.println(protocol + " - " + message.getContent());
		
		switch ( protocol ){
		
			case C_REQ_AG:
				messagePool.takeOutgoingConnection().setMessage(new A_RCV_AG_MESSAGE(agenda));
			break;

			case C_S_REQ_CRT:
				if (agenda.addEvento( (Evento) message.getContent() )){
					messagePool.takeOutgoingConnection().setMessage(new S_C_RCV_AAD_MESSAGE("Sucessfuly added, ...so says the server..!"));
				} else {
					messagePool.takeOutgoingConnection().setMessage(new S_C_RCV_AAD_MESSAGE("Cannot Comply! i got Orders, you know?"));
				}
			break;
			
			case S_S_REQ_TLOG:break;
			case C_S_REQ_ALT:break;
			case C_S_REQ_DEL:break;
			case S_S_REQ_PROMO:break;
			case S_S_REQ_HS:break;
			case S_S_RCV_TLOG:break;
			case S_S_RCV_PROMO:break;
			case S_S_RCV_HS:break;
			case S_REQ_AG:break;
			case S_RCV_SL:break;
			case S_C_RCV_AAD:break;
			case C_S_REQ_HS:break;
			case A_RCV_RDT:break;
		default:
			break;
			}


	}


	

}
