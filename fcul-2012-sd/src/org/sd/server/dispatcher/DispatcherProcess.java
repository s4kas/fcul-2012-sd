package org.sd.server.dispatcher;

import java.util.Observable;
import java.util.Observer;

import org.sd.common.messages.IMessage;
import org.sd.common.messages.Message;
import org.sd.data.Agenda;
import org.sd.protocol.C_REQ_AG;
import org.sd.protocol.Protocol;
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
	 * 
	 * @param P
	 * @return
	 */
	private boolean isValid (Protocol P){
		//CHECK STATE FLAGS
		return true;
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
		
		if (isValid(protocol)){
			this.protocol =protocol;  
			auth=true;
		}
		//TODO TP: STATE CHANGES?
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
				//Evia 
				messagePool.takeOutgoingConnection().setMessage(new C_REQ_AG(agenda));
			break;
			case C_S_REQ_AAD:
				
			break;
			
			
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
			
			case A_RCV_RDT:break;
		default:
			break;
			}


	}


	

}
