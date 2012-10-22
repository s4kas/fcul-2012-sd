package controloservidor;

import java.util.Observable;
import java.util.Observer;

import org.sd.common.implementation.Message;
import org.sd.common.implementation.MessagePool;
import org.sd.common.implementation.Protocol;

public class Dispatcher implements Observer{

	
	public Dispatcher (){
		//initDispatcher
	}	
	
	
	public void update(Observable o, Object arg) {
	

		//Recebe Message,
		//Abre m:Message, e retira o evento ( e:Event = Message.getContent().getEvento().
		
		switch ( (Protocol) arg ){

		/*************************************************
		 * CLIENT PROTOCOLS	
		 */
		
			case CLIENT_REQUEST_ADD:
				//valida(evento).
				//SE: valido
					//Regista o evento na agenda.
					//regista m:Message no historico
					//propaga o evento para os secundários.
						//Protocol.SERVER_SERVER_SEND_SNAP_UPDATE;
					//responde ao cliente "evento submetido".
					//propaga update para os clientes
						//Protocol.SERVER_SEND_CLIENT_UPDATE;.
				//SE:invalido
					//responde ao cliente "evento invalido".
				//((MessagePool) o).postMessage(new Message());
				break;
		
			case CLIENT_REQUEST_ALTER:
				//valida(evento).
				//SE: valido
					//Regista o evento na agenda.
					//regista m:Message no historico
					//propaga o evento para os secundários.
						//Protocol.SERVER_SERVER_SEND_SNAP_UPDATE;
					//responde ao cliente "evento submetido.
					//propaga update para os clientes
						//Protocol.SERVER_SEND_CLIENT_UPDATE;.
				//SE:invalido
					//responde ao cliente "evento invalido".
				//((MessagePool) o).postMessage(new Message());
				break;

			case CLIENT_REQUEST_DEL:
				//valida(evento).
				//SE: valido
					//Regista o evento na agenda.
					//regista m:Message no historico
					//propaga o evento para os secundários.
						//Protocol.SERVER_SERVER_SEND_SNAP_UPDATE;
					//responde ao cliente "evento submetido.
					//propaga update para os clientes
						//Protocol.SERVER_SEND_CLIENT_UPDATE;.
				//SE:invalido
					//responde ao cliente "evento invalido".
				//((MessagePool) o).postMessage(new Message());
				break;				
				
				
			case CLIENT_REQUEST_UPDATE: //TEM UTILIDADE??
				//valida(evento).
				//SE: valido
					//propaga o evento para o cliente que o solicitou.
				//SE:invalido
					//responde ao cliente "evento invalido".
				//((MessagePool) o).postMessage(new Message());
				break;

			case CLIENT_REQUEST_HANDSHAKE:
				//valida(evento).
					//SE Secundario:
						//SEND Protocol.SERVER_SERVER_REQUEST_PROMOTION
						//ao primario e todos os outros secundarios que poderam 
						//terem-se promovido.
							//SE reply SIM de todos os secundarios && no reply do primario.
								//Promote();
								//envia FULL UPDATE A TODOS OS SECUNDARIOS.
							//Se reply não 
								//Return invalido
					//SE primario: Return valido
				//SE: valido
					//regista novo cliente online.
					//valida utilizador.
					//envia copia da agenda para o cliente
					//Propaga o registo do novo cliente para os secundarios.
				//SE:invalido
					//responde ao cliente "ligação invalida".
					//disconect client.
				//((MessagePool) o).postMessage(new Message());
				break;
		

				
/*************************************************
 * SERVER PROTOCOLS	
 */
				
			case SERVER_CLIENT_REDIRECT: 
				//Sou secundario
				//Redireciona os clientes para um primário eleito da lista.
				break;

				
			case SERVER_SERVER_REQUEST_HANDSHAKE:
				//TODO: a definir
				break;
			
			case SERVER_SERVER_RECIEVE_HANDSHAKE:				
				//valida(evento).
				//SE: Primário  
					//return valido
				//SE:Secundario
					//Return invalido.
			//SE: valido
				//regista novo servidor secundario online.
				//Propaga o registo do novo secundario para os clientes.
				//Propaga o registo do novo secundario para os outros secundarios.
			//SE:invalido
				//responde ao novo secundario "ligação invalida".
			((MessagePool) o).postMessage(new Message());
				break;
				
			case SERVER_SERVER_REQUEST_LOG_UPDATE:
				//TODO: a definir
				break;
				
			case SERVER_SERVER_RECIEVE_LOG_UPDATE:
				//TODO: a definir
				break;
				
			case SERVER_SERVER_REQUEST_USERLIST_UPDATE:
				//TODO: a definir
				break;
				
			case SERVER_SERVER_RECIEVE_USERLIST_UPDATE:
				//TODO: a definir
				break;
				
			case SERVER_SERVER_REQUEST_SERVERLIST_UPDATE:
				//TODO: a definir
				break;
				
			case SERVER_SERVER_RECIEVE_SERVERLIST_UPDATE:
				//TODO: a definir
				break;			
				
				
			case SERVER_SERVER_REQUEST_FULL_UPDATE:
				//valida(evento).
				//SE: valido() && servidor secundario HANDSHAKED
					//Envia o historico transaccional para o solicitador..
				//SE:invalido
					//responde ao novo secundario "Solicitação invalida".
				//((MessagePool) o).postMessage(new Message());
				break;

			case SERVER_SERVER_RECIEVE_FULL_UPDATE:
				//valida(evento).
				//SE: valido() && servidor secundario HANDSHAKED
					//guarda os dados 
				//SE:invalido
					//responde ao novo secundario "Solicitação invalida".
				//((MessagePool) o).postMessage(new Message());
				break;

				
			case SERVER_SERVER_REQUEST_PROMOTION:
				//valida(evento).
					//SE primario:
						//return invalido.
					//SE secundario:
						//return PROTOCOL.SERVER_KNOCK_YOURSELF_OUT
				break;

			case SERVER_SERVER_RECIEVE_PROMOTION:
				//valida(evento).
					//SE valido()
					//return PROTOCOL.SERVER_KNOCK_YOURSELF_OUT!!
					//TODO: Definir
				break;

			}
	}

}
	