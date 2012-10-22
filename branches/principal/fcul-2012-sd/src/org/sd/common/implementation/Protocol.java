package org.sd.common.implementation;

public class Protocol {

	//Accoes do cliente
	public final static int CLIENT_REQUEST_UPDATE=100;
	public final static int CLIENT_ADD=101;
	public final static int CLIENT_DEL=102;
	public final static int CLIENT_ALTER=103;
	public final static int SERVER_SEND_CLIENT_UPDATE=104;
	
	//Controlo do servidor

	public final static int SERVER_CLIENT_SEND_UPDATE=200;
	public final static int SERVER_CLIENT_HANDSHAKE=200;
	public final static int SERVER_CLIENT_REDIRECT=201;
	
	public final static int SERVER_SERVER_HANDSHAKE=300;
	public final static int SERVER_SERVER_DEMOTE=201;
	public final static int SERVER_SERVER_REQUEST_SNAP_UPDATE=202;
	public final static int SERVER_SERVER_SEND_SNAP_UPDATE=203;
	public final static int SERVER_SERVER_REQUEST_FULL_UPDATE=204;
	public final static int SERVER_SERVER_SEND_FULL_UPDATE=205;
	
	private Protocol(){
		
	}
	
}
