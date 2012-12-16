package org.sd.data;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;

public class ClientList implements Serializable{

	private static final long serialVersionUID = -7883586399486239715L;

	private LinkedList <String> clientList; 
	
	public ClientList(){
		clientList = new LinkedList <String>();
	}
	
	public synchronized boolean deleteFromList (String ip){
		return clientList.remove(ip);
	}
		
	public synchronized Iterator<String> listOfClients (){
		return clientList.iterator();
	}
	
	public synchronized void addClientToClientList (String ip){
		clientList.addFirst(ip); //primary Server head of the list. 
	}
}
