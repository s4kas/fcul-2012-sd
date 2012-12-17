package org.sd.data;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.net.*;

public class ServerList implements Serializable {

	private static final long serialVersionUID = -8235350151785732582L;
	private LinkedList <String> serverIpList = new LinkedList <String>();
	private String cachedLocalIpAddress; 
	
	public ServerList(){

		try {
			cachedLocalIpAddress = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			System.exit(1);
			e.printStackTrace();
		}
		this.serverIpList.add (cachedLocalIpAddress);
	}
	

	public void addLast(String s){
		serverIpList.addLast(s);
	}
	
	public void overrideList(List<String> newList){
		serverIpList = (LinkedList<String>) newList;
	}
	
	public String getMyAddress(){
		return cachedLocalIpAddress;
	}
	
	public String givePrimary (){
		//usualy the Primary.
		return serverIpList.getFirst();
	}

	public String giveNextInFront (){
		if (serverIpList.indexOf(cachedLocalIpAddress)>0)
			return  serverIpList.get(serverIpList.indexOf(cachedLocalIpAddress)-1);
		else
			return serverIpList.getFirst();
	}
	
	
	public synchronized List <String> listOfServers(){
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
