package sistema;

import controlocliente.ClientConfigData;
import controlocliente.ClientProtocol;
import controloservidor.NetworkServer;
import controloservidor.ServerConfigData;

public class EntryPoint {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		ServerConfigData serverConfigData = new ServerConfigData();
		
		
		//NetworkServer networkServer = new NetworkServer();
		
		//Server data required by the client to connect
		ClientConfigData clientConfigData = new ClientConfigData();
		clientConfigData.setSIPaddress("localhost");
		clientConfigData.setSPort(serverConfigData.getServerPortAddress());
		
		
		
		ClientProtocol clientProtocol = new ClientProtocol(ClientProtocol.HANDSHAKE);

	}

}
