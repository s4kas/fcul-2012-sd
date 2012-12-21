package org.sd.data;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.net.*;

public class ServerInfo implements Serializable {

	private static final long serialVersionUID = -8235350151785732582L;
	private LinkedList <String> serverIpList = new LinkedList <String>();
	private String cachedLocalIpAddress; //local ip address 
	private long promotingTimeStamp; // used for promotion
	private int quorum;			 //used for promotion
	
	public ServerInfo(){
		promotingTimeStamp = 0;
		quorum=0;
	}
	
	public void setThisAsPrimary() {
		try {
			cachedLocalIpAddress = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			System.exit(1);
			e.printStackTrace();
		}
		this.serverIpList.remove(cachedLocalIpAddress);
		this.serverIpList.addFirst (cachedLocalIpAddress);
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
	

	public boolean isPromoting(){
		return promotingTimeStamp>0;
		
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
		//remove if exists.
		serverIpList.remove(ip);
		//primary Server head of the list.
		this.serverIpList.addFirst(ip); 	
		return false;
	}

	public long getTimeStamp(){return promotingTimeStamp;}
	
	public void setTimeStamp(long timeStamp) {promotingTimeStamp = timeStamp;}
	
	public boolean hasQuorum(){
		return quorum==0; 
	}
	
	public void setQuorum(){
		//ALL but myself from list.
		this.quorum=serverIpList.size()-1;  
	}
	
	public void addOkToQuorum(){
		quorum--;
	}

	public void clearQuorum() {
		quorum=0;
	}
	
}
