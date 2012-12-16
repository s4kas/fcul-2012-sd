package org.sd.server.dispatcher;

import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

import org.sd.common.connection.Connection;
import org.sd.common.connection.IConnection;
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
	private boolean auth= false;
	private Protocol protocol; 
	private Agenda thisAgenda;
	private IConnection thisConnection;
	
	private boolean stateFlags_Promoting;
	private boolean stateFlags_Primary;
	private boolean stateFlags_Secondary;
	private MessagePool messagePool = MessagePoolProxy.getInstance();
	
	
	
	/*************************************************************
	 * Validates Payload
	 * @param Protocol
	 * @return true if payloadValid
	 */
	private boolean isProtocolValid (Protocol p, Object content){
		//CHECK STATE FLAGS
		boolean isValid=false;
		
		switch (p){
		case C_S_REQ_CRT:
		case C_S_REQ_ALT:
		case C_S_REQ_DEL:if (thisConnection.getMessage().getContent() instanceof Evento) isValid=true;break;
			
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
	public DispatcherProcess (IConnection aConnection, Agenda aAgenda) throws Exception {
		
		this.thisAgenda = aAgenda;
		this.thisConnection = aConnection;
		Protocol protocol = thisConnection.getMessage().getHeader();
		System.out.println(protocol + " - " + protocol);
		
		//Validate protocol content.
		if (isProtocolValid(protocol,thisConnection.getMessage().getContent())){
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
	
	/*************************************************************
	 * 
	 */
	public void run() {
		String reply;
		processing=true;
		if (!auth) return;
		
		switch ( protocol ){
			//REQUEST FULL AGENDA UPDATE FROM CLIENT
			case C_REQ_AG:
				messagePool.postOutgoingConnection(new Connection(new A_RCV_AG_MESSAGE(thisAgenda),thisConnection.getSocket()));
			break;
			//REQUEST ADD EVENTO FROM CLIENT
			case C_S_REQ_CRT:
				if (thisAgenda.addEvento( (Evento) thisConnection.getMessage().getContent())){
					reply = "Sucessfuly added, ...so says the server..!";
					
				} else {
					reply = "Cannot Comply! i got Orders, you know?";
				}
				messagePool.postOutgoingConnection(new Connection(new S_C_RCV_AAD_MESSAGE(reply),thisConnection.getSocket()));
			
			break;
			
			//REQUEST 
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
