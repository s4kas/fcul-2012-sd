package controloservidor;

import java.util.Observable;

import org.sd.common.implementation.Message;
import org.sd.common.implementation.Protocol;

public abstract class Dispatchable extends Observable{

	protected Dispatchable (Dispatcher d){
		this.addObserver(d);		
	}
	
	public void notifyDispatcher (int protocol){
		this.setChanged();
		this.notifyObservers(protocol);
	}
	
	public void endNotification (){
		this.clearChanged();
	}
	
	
	public abstract Message getMessage();
	
	public abstract boolean postMessage(Message m);
	
	
	
}