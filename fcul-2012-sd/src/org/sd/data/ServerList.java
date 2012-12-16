package org.sd.data;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;

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

	public synchronized Iterator<String> listOfServers (){
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
