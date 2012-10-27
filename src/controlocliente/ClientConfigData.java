/**
 * VERSÃO 1.001s
 */
package controlocliente;

import java.io.Serializable;

/**
 * 
 * Objecto com dados de configuração do cliente
 * @author tpires
 */
public class ClientConfigData implements Serializable {
	
	static final long serialVersionUID=99902833;

	private String serverIPaddress;
	private int serverPort;
	private long version=0;
	
	public ClientConfigData (){	}
	/**
	 * Método para alterar a versão de configuração
	 * @param ver long numero de série 
	 */
	public void updateConfigVersion() {	version++; }
	/**
	 * Método que devolve a versão actual da configuração
	 * @return long numero de série
	 */
	public long getConfigVersion() { 	return version;	}

	public void setSIPaddress(String ip){	this.serverIPaddress=ip;}
	
	public String getSIPaddress() { 	return serverIPaddress; 	}
	
	public void setSPort (int sp) 	{ 		this.serverPort=sp; 	}
	
	public int getSPort ()	{		return serverPort;	}
}