package org.sd.server.dispatcher;

import java.io.IOException;
import java.net.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

import org.sd.common.connection.Connection;
import org.sd.common.connection.IConnection;
import org.sd.common.messages.IMessage;

import org.sd.data.ActionLog;
import org.sd.data.Agenda;
import org.sd.data.ClientList;
import org.sd.data.Evento;
import org.sd.data.ServerInfo;
import org.sd.protocol.A_RCV_AG_MESSAGE;
import org.sd.protocol.A_RCV_RDT_MESSAGE;
import org.sd.protocol.C_RCV_SL_MESSAGE;
import org.sd.protocol.PromotionMessage;
import org.sd.protocol.Protocol;
import org.sd.protocol.S_C_RCV_AAD_MESSAGE;
import org.sd.protocol.S_C_RCV_HS_MESSAGE;
import org.sd.protocol.S_RCV_PROMO_MESSAGE;
import org.sd.protocol.S_RCV_RDT_MESSAGE;
import org.sd.protocol.S_RCV_SL_MESSAGE;
import org.sd.protocol.S_S_RCV_ALOG_MESSAGE;
import org.sd.protocol.S_S_RCV_HS_MESSAGE;
import org.sd.protocol.S_S_REQ_HS_MESSAGE;
import org.sd.protocol.S_S_REQ_PROMO_MESSAGE;
import org.sd.server.message.MessagePool;
import org.sd.server.message.MessagePoolProxy;

public class DispatcherProcess extends Observable implements Runnable {

	
	private final int DEFINED_TIMEOUT = 15; //in seconds
	private boolean processing;
	private boolean validContent= false;
	private Protocol protocol; 
	private Agenda thisAgenda;
	private MessagePool messagePool = MessagePoolProxy.getInstance();
	private IConnection currentConnection ;

	private ServerInfo currentServerInfo;
	private ClientList currentClientList;
	private ActionLog currentActionLog;
	

	
	
	/*************************************************************
	 * Tests Connection
	 * @param serverIp
	 * @return
	 */
	private boolean testConnection (String serverIp){
		boolean sucess=false;
			try {
				Socket s = new Socket(serverIp, 1500);
				sucess=true;
				s.close();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				sucess=false;
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				sucess=false;
				e.printStackTrace();
			}
			return sucess;
	}
	
	
	/*************************************************************
	 * Validates Payload
	 * @param Protocol
	 * @return true if payloadValid
	 */
	private boolean isProtocolValid (Protocol protocol, Object content){

		//TODO: VALIDATE MESSAGE CONTENT OR DISCARD INVALID CONTENT
		boolean isValid=true;
		
		switch (protocol){
		
		
	
		case C_S_REQ_ADD:
		case C_S_REQ_ALT:
		case C_S_REQ_DEL:if (currentConnection.getMessage().getContent() instanceof Evento) isValid=true;break;
		case S_RCV_RDT:if (currentConnection.getMessage().getContent() instanceof String) isValid=true;break;		
		
		case S_RCV_SL:if (currentConnection.getMessage().getContent() instanceof List) isValid=true;break;
		case S_S_RCV_ALOG:if (currentConnection.getMessage().getContent() instanceof LinkedList) isValid=true;break;
		case A_RCV_AG:
		case S_S_RCV_PROMO:if (currentConnection.getMessage().getContent() instanceof PromotionMessage) isValid=true;break;
		
		//no payload required. Ignore payload
		case C_S_REQ_HS:
		case S_S_REQ_PROMO:
		case S_S_REQ_HS:
		case S_S_REQ_ALOG:
		case A_REQ_AG:
		case S_S_RCV_HS: isValid=true; break;
		default:
			break;
		}
		return isValid;
	}
	
	
	/**********************************************
	 * Initialize this thread
	 * @param message
	 * @throws myContentException
	 */
	public DispatcherProcess (IConnection aConnection, 
								Agenda aAgenda, 
								ServerInfo sl,
								ClientList cl,
								ActionLog al) throws Exception {
		
		this.thisAgenda = aAgenda;
		this.currentConnection = aConnection;
		this.currentActionLog = al;
		this.currentServerInfo = sl;
		this.currentClientList = cl;
		
		protocol = currentConnection.getMessage().getHeader();
		
		//Validate protocol content.
		if (isProtocolValid(protocol,currentConnection.getMessage().getContent())){
			this.validContent=true;
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
	@SuppressWarnings("unchecked")
	public void run() {
		String reply;
		processing=true;
		
		//INVALID MESSAGE CONTENT EXITS!!
		if (validContent) {

		switch ( protocol ){
			//REQUEST FULL AGENDA UPDATE FROM CLIENT
			case A_REQ_AG:
				messagePool.postOutgoingConnection(new Connection(new A_RCV_AG_MESSAGE(thisAgenda),currentConnection));
			break;

			//REQUEST ADD EVENTO FROM CLIENT
			case C_S_REQ_ADD:
				if (thisAgenda.addEvento( (Evento) currentConnection.getMessage().getContent())){
					reply = "Sucessfuly added, ..so says the master..!";
					//ADD TO ALOG
					currentActionLog.addMessage(currentConnection.getMessage());
					//SEND S_S_RCV_ALOG TO ALL SERVERS.
					//send a subset of Alog from this message position on log to the end of the log.  
						messagePool.postToAllServers(new S_S_RCV_ALOG_MESSAGE(
								currentActionLog.SubSetAfter(currentConnection.getMessage())),
								currentServerInfo.listOfServers());
				} else {
					reply = "Couldnt add, already exists or overlaps. you tring to add or alter?";
				}
				//output ok to add
				messagePool.postOutgoingConnection(new Connection(new S_C_RCV_AAD_MESSAGE(reply),currentConnection));
				//output list of events
				messagePool.postOutgoingConnection(new Connection(new A_RCV_AG_MESSAGE(thisAgenda),currentConnection));
			break;
			
			//REQUEST_ALOG
			case S_S_REQ_ALOG:
				//SEND FULL ALOG BACK TO REQUESTING SERVER.
				messagePool.postOutgoingConnection(
						new Connection(
								new S_S_RCV_ALOG_MESSAGE(currentActionLog.fullList()),
								currentConnection));
				break;
				
			case C_S_REQ_ALT:
				//ALTER = REMOVE AND ADD SAME DATE&TIME
				if (thisAgenda.alterEvento((Evento) currentConnection.getMessage().getContent())){
					reply = "Sucessfuly Altered, ...so says the master..!";
					//ADD TO ALOG
					currentActionLog.addMessage(currentConnection.getMessage());
					//SEND S_S_RCV_ALOG TO ALL SERVERS.
					//send a subset of Alog from this message position on log to the end of the log.  
						messagePool.postToAllServers(new S_S_RCV_ALOG_MESSAGE(
								currentActionLog.SubSetAfter(currentConnection.getMessage())),
								currentServerInfo.listOfServers());
				} else {
					reply = "Couldnt alter, not realy a substitution!";
				}
				//output ok to modidy
				messagePool.postOutgoingConnection(new Connection(new S_C_RCV_AAD_MESSAGE(reply),currentConnection));
				//output list of events
				messagePool.postOutgoingConnection(new Connection(new A_RCV_AG_MESSAGE(thisAgenda),currentConnection));
			break;

			case S_S_REQ_HS:

				//m i primary = first of the serverlist?
				if (currentServerInfo.givePrimary().equals(currentServerInfo.getMyAddress())){
					//REDIRECT TO LAST OF THE LIST.
					messagePool.postOutgoingConnection(
							new Connection(
									new S_RCV_RDT_MESSAGE(currentServerInfo.giveLastSecondary()),
									currentConnection));
				} else if (currentServerInfo.giveLastSecondary().equals(currentServerInfo.getMyAddress())){
					//SENDS ALOG TO SERVER
					messagePool.postOutgoingConnection(
							new Connection(
									new A_RCV_AG_MESSAGE(this.thisAgenda),
									currentConnection));
					//SENDS SERVER LIST
					messagePool.postOutgoingConnection(
							new Connection(
									new S_RCV_SL_MESSAGE(currentServerInfo.listOfServers()),
									currentConnection));
					//SENDS HANDSHAKE CONFIRMATION.
					messagePool.postOutgoingConnection(
							new Connection(
									new S_S_RCV_HS_MESSAGE(),
									currentConnection));
				} else {
					messagePool.postOutgoingConnection(
							new Connection(
									new S_RCV_RDT_MESSAGE(currentServerInfo.giveNextInFront()),
									currentConnection));
				}
				break;
			
			case C_S_REQ_HS:
				//m i primary = first of the serverlist?
				if (currentServerInfo.givePrimary().equals(currentServerInfo.getMyAddress())){

					//SEND HANDSHAKE
					messagePool.postOutgoingConnection(new Connection(new S_C_RCV_HS_MESSAGE(),currentConnection));
					//ADD THIS TO CLIENT LIST.
					currentClientList.addToList(currentConnection.getSocket().getInetAddress().getHostAddress());
					//SEND AGENDA TO CLIENT.
					messagePool.postOutgoingConnection(new Connection(new A_RCV_AG_MESSAGE(thisAgenda),currentConnection));
					//SENDS SERVER LIST
					messagePool.postOutgoingConnection(new Connection(
							new C_RCV_SL_MESSAGE(currentServerInfo.listOfServers()),
									currentConnection));
				} else {
					// IS PRIMARY ONLINE?
					if (testConnection(currentServerInfo.givePrimary())){
						messagePool.postOutgoingConnection(
								new Connection(
										new S_RCV_RDT_MESSAGE(currentServerInfo.givePrimary()),
										currentConnection));
					} else {
						//CONNECTED TO A SECONDARY SERVER
						if (!currentServerInfo.giveNextInFront().equals(currentServerInfo.givePrimary())){
							messagePool.postOutgoingConnection(
									new Connection(
											new S_RCV_RDT_MESSAGE(currentServerInfo.giveNextInFront()),
											currentConnection));
						} else {
							//TRY TO PROMOTE MYSELF AND ANOUNCE TO OTHER SERVERS.
							IMessage message= new S_S_REQ_PROMO_MESSAGE();
							//SET PROMOTING TIMESTAMP
							currentServerInfo.setTimeStamp(message.getTimeStamp());
							messagePool.postToAllServers(message,
									currentServerInfo.listOfServers());
						}
					}
				}
				break;
				
			case C_S_REQ_DEL:
				//ALTER = REMOVE AND ADD SAME DATE&TIME
				if (thisAgenda.removesEvento((Evento) currentConnection.getMessage().getContent())){
					reply = "Sucessfuly Removed, ...so says the master..!";
					//ADD TO ALOG
					currentActionLog.addMessage(currentConnection.getMessage());
					//SEND S_S_RCV_ALOG TO ALL SERVERS.
					//send a subset of Alog from this message position on log to the end of the log.  
						messagePool.postToAllServers(new S_S_RCV_ALOG_MESSAGE(
								currentActionLog.SubSetAfter(currentConnection.getMessage())),
								currentServerInfo.listOfServers());
				} else {
					reply = "Couldnt delete, cant find this evento! to delete.";
				}
				//output ok to delete
				messagePool.postOutgoingConnection(new Connection(new S_C_RCV_AAD_MESSAGE(reply),currentConnection));
				//output list of events
				messagePool.postOutgoingConnection(new Connection(new A_RCV_AG_MESSAGE(thisAgenda),currentConnection));
			break;
		
			case S_S_RCV_HS:
				//ADD MY IP ADDRES TO LAST OF THE SERVERLIST
				currentServerInfo.addLast(currentServerInfo.getMyAddress());
				messagePool.postToAllServers(new S_RCV_SL_MESSAGE(currentServerInfo.listOfServers()),
								currentServerInfo.listOfServers());
			break;

			case S_RCV_SL:
				currentServerInfo.overrideList((List<String>)currentConnection.getMessage().getContent());
				//IF IM A PRIMARY SERVER
				if (currentServerInfo.givePrimary().equals(currentServerInfo.getMyAddress())){
					messagePool.postToAllServers(new C_RCV_SL_MESSAGE(currentServerInfo.listOfServers()),
							currentClientList.listOfClients());
				}
			break;
				
			case S_S_REQ_PROMO:
				//	IF IM A PRIMARY SERVER
				if (currentServerInfo.givePrimary().equals(currentServerInfo.getMyAddress())){
					//Abort other's attempt to promote himself.
					messagePool.postOutgoingConnection(new Connection(new S_RCV_PROMO_MESSAGE(PromotionMessage.ABORT),currentConnection));
				} else if (currentServerInfo.isPromoting()){
					if (currentServerInfo.getTimeStamp()<currentConnection.getMessage().getTimeStamp()){
						messagePool.postOutgoingConnection(new Connection(new S_RCV_PROMO_MESSAGE(PromotionMessage.ABORT),currentConnection));
					} else{
						currentServerInfo.setTimeStamp(0); //aborts promotion attempt.
						messagePool.postOutgoingConnection(new Connection(new S_RCV_PROMO_MESSAGE(PromotionMessage.GOAHEAD),currentConnection));
					}
				} else {
					messagePool.postOutgoingConnection(new Connection(new S_RCV_PROMO_MESSAGE(PromotionMessage.GOAHEAD),currentConnection));
				}
				break;
				
			case S_S_RCV_PROMO:
				//Atempting promotion?
				if (currentServerInfo.isPromoting()){
					long result = currentServerInfo.getTimeStamp()-(new Date().getTime());
					if (result>DEFINED_TIMEOUT){
						//Stop promotion no answer in time. 
						//RETRY TO PROMOTE MYSELF AND ANOUNCE TO OTHER SERVERS.
						IMessage message= new S_S_REQ_PROMO_MESSAGE();
						//SET PROMOTING TIMESTAMP
						currentServerInfo.setTimeStamp(message.getTimeStamp());
						messagePool.postToAllServers(message,
								currentServerInfo.listOfServers());
						break;
				} else { // STILL IN TIME FRAME
					currentServerInfo.addOkToQuorum();
					switch ((PromotionMessage)currentConnection.getMessage().getContent()){
						case GOAHEAD:
							if (!currentServerInfo.hasQuorum()){
								break;
							} else{
								//PROMOTE MYSELF
								currentServerInfo.setPrimaryServer(currentServerInfo.getMyAddress());
								//RDT TO SERVERS
								messagePool.postToAllServers(new A_RCV_RDT_MESSAGE(currentServerInfo.getMyAddress()),currentServerInfo.listOfServers());
							}
							
						break;
						case ABORT: 
							currentServerInfo.clearQuorum();
							messagePool.postOutgoingConnection(new Connection(new S_S_REQ_HS_MESSAGE(),	currentConnection));
						break;
						}
					}
				}
				break;
			
			case S_RCV_RDT:
				//SERVER TRYS TO CONNECT TO PRIMARY AND ITS REDIRECTED TO THE LAST ONE.
				LinkedList<String> temp1 = new LinkedList<String>();
				temp1.add((String) currentConnection.getMessage().getContent());
				//I TRY TO CONNECT TO THE REDIRECTED SERVER
				messagePool.postToAllServers(new S_S_REQ_HS_MESSAGE(),temp1);
				break;
		default:
			break;
			}
		}
		processing=false;
	}

}
