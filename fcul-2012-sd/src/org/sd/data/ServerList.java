package org.sd.data;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ServerList implements Serializable {

	private static final long serialVersionUID = -8235350151785732582L;
	private LinkedList <String> serverIpList = new LinkedList <String>();
	
	public ServerList(String myIp){
		this.serverIpList.add(myIp);
	}
	
	public String givePrimary (){
		//usualy the Primary.
		return serverIpList.getFirst();
	}

	public synchronized List <String> listOfServers(){
		LinkedList <String> temp = new LinkedList<String>();
		temp.addAll(serverIpList);
		temp.remove("localhost");
		return serverIpList;
	}
	
	public synchronized Iterator<String> iteratorOfListOfServers (){
		return serverIpList.iterator();
	}
	
	public String giveLastSecondary(){
		//LastServer accepts other server.
		return serverIpList.getLast();
	}
	
	public synchronized boolean setPrimaryServer(String ip){
		serverIpList.remove(ip); //remove or not dont care.
		this.serverIpList.addFirst(ip); //primary Server head of the list.	
		return false;
	}
	
}
