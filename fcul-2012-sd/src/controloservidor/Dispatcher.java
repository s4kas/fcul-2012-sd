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
	
		switch (((Integer) arg)){
			case Protocol.CLIENT_ADD:
	
				//Lida com a mensagem.				
				//Se aceite adiciona ao historico
				//resposta ao cliente
				((MessagePool) o).postMessage(new Message());
				break;
		
			case Protocol.CLIENT_ALTER:
				//Lida com a mensagem.
				//Se aceite adiciona ao historico
				//resposta ao cliente
				((MessagePool) o).postMessage(new Message());
				break;

			case Protocol.CLIENT_DEL:
				//Lida com a mensagem.
				//Se aceite adiciona ao historico
				//resposta ao cliente
				((MessagePool) o).postMessage(new Message());
				break;

			case Protocol.CLIENT_REQUEST_UPDATE:
				//Lida com a mensagem.
				//Se aceite adiciona ao historico
				//resposta ao cliente
				((MessagePool) o).postMessage(new Message());
				break;

			case Protocol.SERVER_CLIENT_HANDSHAKE:
				//Lida com a mensagem.
				//Se aceite adiciona ao historico
				//resposta ao cliente
				((MessagePool) o).postMessage(new Message());
				break;
			case Protocol.SERVER_CLIENT_REDIRECT:
				//Lida com a mensagem.
				//Se aceite adiciona ao historico
				//resposta ao cliente
				((MessagePool) o).postMessage(new Message());
				break;
			case Protocol.SERVER_SERVER_HANDSHAKE:
				//Lida com a mensagem.
				//Se aceite adiciona ao historico
				//resposta ao cliente
				((MessagePool) o).postMessage(new Message());
				break;
			case Protocol.SERVER_SERVER_REQUEST_FULL_UPDATE:
				//Lida com a mensagem.
				//Se aceite adiciona ao historico
				//resposta ao cliente
				((MessagePool) o).postMessage(new Message());
				break;
			case Protocol.SERVER_SERVER_REQUEST_SNAP_UPDATE:
				//Lida com a mensagem.
				//Se aceite adiciona ao historico
				//resposta ao cliente
				((MessagePool) o).postMessage(new Message());
				break;

			}
	}

}
	