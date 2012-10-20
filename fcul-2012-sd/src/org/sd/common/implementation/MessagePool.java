package org.sd.common.implementation;

import java.util.Observable;

import controloservidor.Dispatchable;
import controloservidor.Dispatcher;

public class MessagePool extends Dispatchable {

	
	public MessagePool (Dispatcher d){
		super(d);
	}

	@Override
	public Message getMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean postMessage(Message m) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
	
