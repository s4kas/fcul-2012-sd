package org.sd.data;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class ClientList implements Serializable{

	private static final long serialVersionUID = -7883586399486239715L;

	private LinkedList <String> clientList; 
	
	public ClientList(){
		clientList = new LinkedList <String>();
	}
	
	public synchronized boolean deleteFromList (String ip){
		return clientList.remove(ip);
	}
		
	public synchronized List<String> listOfClients (){
		return clientList;
	}
	
	public synchronized void addToList (String ip){
		clientList.add(ip); //Unordered clientlist. 
	}
}
