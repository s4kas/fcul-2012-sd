package org.sd.data;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.sd.common.messages.IMessage;

public class ActionLog implements Serializable{

	private static final long serialVersionUID = 6032208664842000147L;
	private LinkedList <IMessage> actionLog;
	
	public ActionLog(){
		actionLog = new LinkedList <IMessage>();
	}
	
	public IMessage last (){
		//The most recent one
		return actionLog.getLast();
	}

	public synchronized Iterator<IMessage> replayFrom(IMessage e){
		LinkedList <IMessage> subsetof = new LinkedList <IMessage>();
		subsetof.addAll(actionLog.indexOf(e), actionLog);
		return subsetof.iterator();
	}
	
	public synchronized List<IMessage> SubSetAfter (IMessage e){
		return actionLog.subList(actionLog.indexOf(e), actionLog.size());
	}
	
	public synchronized LinkedList<IMessage> fullList (){
		LinkedList <IMessage> subsetof = new LinkedList <IMessage>();
		subsetof.addAll(actionLog);
		return subsetof;
	}

	
	public synchronized boolean addMessage(IMessage e){
		return actionLog.add(e);
	}

}
