/**
 * 
 */
package controlocliente;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.swing.JOptionPane;


/**
 * VERSAO 1.005
 * @author tpires
 *
 */
public class ClientProtocol implements Serializable {

	final static long serialVersionUID=999922;
	
	public static final int END=0;
	public static final int HANDSHAKE=1;
    public static final int UPLOAD_PACKAGE=2;
    public static final int DOWNLOAD_STATS=3;
    
	private String ipAddress;
	private long serialNumber;
	private String hostName;
	private int opCode;
	
	
	/**
	 * Constructor do protocolo,
	 * @param ip String com o ip deste cliente 
	 * @param hn String com o nome deste cliente
	 */
	public ClientProtocol (int opCode)
	{
			this.opCode=opCode;
		try {
			InetAddress localMachine = java.net.InetAddress.getLocalHost();
			hostName = localMachine.getHostName();
			ipAddress = localMachine.getHostAddress();
			//JOptionPane.showMessageDialog(null,"DEBUG:::"+hostName+" "+ipAddress);
			}
			catch (UnknownHostException e)
			{
				JOptionPane.showMessageDialog(null,"Ésta aplicação necessita de pelo menos \n uma placa de rede!\n configurada");
				System.exit(1);
			}
	}
	/**
	 * Devolve o Hostname
	 * @return String com o hostname
	 */
	public String getHostname ()
	{
		return hostName;
	}

	/**
	 * Devolve o IP address desta maquina
	 * @return String com o IP address
	 */
	
	public String getIpAdress()
	{
		return ipAddress;
	}

	/**
	 * Devolve o OPcode do protocolo
	 * @return int com o valor de opCode
	 */
	public int getOpcode()
	{
		return opCode;
	}
	/**
	 * Altera o opCode do protocolo
	 * @param opCode int valor de opCode
	 */
	public void setOpCode (int opCode)
	{
		this.opCode=opCode;
	}
	/**
	 * Altera o valor serial do protocolo
	 * @return long valor serial do protocolo
	 */
	public long getSerial()
	{
		return serialNumber;
	}
	/**
	 * Devolve o valor Serial do protocolo
	 * @param serial long valor serial do protocolo
	 */
	public void getSerial(long serial)
	{
		this.serialNumber=serial;
	}
	
	

}
