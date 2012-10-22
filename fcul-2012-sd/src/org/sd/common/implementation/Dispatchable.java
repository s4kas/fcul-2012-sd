package org.sd.common.implementation;

import java.util.Observable;

import controloservidor.Dispatcher;

public abstract class Dispatchable extends Observable{

	protected Dispatchable (Dispatcher d){
		this.addObserver(d);		
	}

	/*************************************************************************
	 * Sempre que ouver uma alteração digna ser propragada para o dispatcher.
	 * @param protocol protocolo relacionado com a alteração a este objecto
	 */
	private void notifyDispatcher (Protocol p){
		this.setChanged();
		this.notifyObservers(p);
	}
	
	/**********************************************************
	 * Nao sei se isto ainda é necessario....
	 * Se for tem de ir no fim do 
	 */
	private void endNotification (){
		this.clearChanged();
	}
	
	public void dispatcherYouHaveNewMessage(){
		notifyDispatcher(Protocol.CLIENT_REQUEST_ADD);
	}
	
	
	// Ainda á aqui duvidas...
	// rever a implementação do observer/observable.
	
	public Message getMessage(){
		Message m = takeTheMessage();
		endNotification();
		return m;
	}
	
	public abstract Message takeTheMessage();
	
	//envio de mensagem
	public abstract boolean postMessage(Message m);
	
	//Se eu sou servidor servidor
	public abstract boolean isPrimaryServer();
	
	
	
	
}
