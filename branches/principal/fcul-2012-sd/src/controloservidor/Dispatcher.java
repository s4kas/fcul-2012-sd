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

		
		switch (((Integer) arg)){
			case Protocol.CLIENT_ADD:
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
		
			case Protocol.CLIENT_ALTER:
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

			case Protocol.CLIENT_DEL:
				
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

			case Protocol.CLIENT_REQUEST_UPDATE:
				//valida(evento).
				//SE: valido
					//propaga o evento para o cliente que o solicitou.
				//SE:invalido
					//responde ao cliente "evento invalido".
				//((MessagePool) o).postMessage(new Message());
				break;

			case Protocol.SERVER_CLIENT_HANDSHAKE:
				//valida(evento).
				//SE: valido
					//regista novo cliente online.
					//Propaga o registo do novo cliente para os secundarios.
				//SE:invalido
					//responde ao cliente "ligação invalida".
				//((MessagePool) o).postMessage(new Message());
				break;

			case Protocol.SERVER_CLIENT_REDIRECT:
				//ou Despoletado localmente / manualmente.
				//ou em resposta a uma eleiçao para primário de um outro servidor.
				//Redireciona os clientes para um primário eleito.
				break;
				
			case Protocol.SERVER_SERVER_HANDSHAKE:
				//valida(evento).
				//SE: valido
					//regista novo servidor secundario online.
					//Propaga o registo do novo secundario para os clientes.
				//SE:invalido
					//responde ao novo secundario "ligação invalida".
				//((MessagePool) o).postMessage(new Message());
				
				break;
			case Protocol.SERVER_SERVER_REQUEST_FULL_UPDATE:
				//valida(evento).
				//SE: valido() && servidor secundario online 
					//Envia o historico transaccional para o solicitador..
				//SE:invalido
					//responde ao novo secundario "Solicitação invalida".
				//((MessagePool) o).postMessage(new Message());
				
			case Protocol.SERVER_SERVER_REQUEST_SNAP_UPDATE:
				//valida(evento).
				//SE: valido() && servidor secundario online 
					//Envia o ultimo registo no historico transaccional 
					//para o solicitador..
				//SE:invalido
					//responde ao novo secundario "Solicitação invalida".
				//((MessagePool) o).postMessage(new Message());
				break;

			}
	}

}
	