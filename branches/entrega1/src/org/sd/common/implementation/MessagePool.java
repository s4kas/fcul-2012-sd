package org.sd.common.implementation;

import org.sd.common.messages.Message;

import controloservidor.Dispatcher;

public class MessagePool extends Dispatchable {

	public MessagePool (Dispatcher d){
		super(d);
	}


	@Override
	public boolean postMessage(Message m) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Message takeTheMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isPrimaryServer() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
	
