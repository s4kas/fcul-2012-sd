
package svrEngine;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JOptionPane;

import protocol.ClientConfigData;
import protocol.NewPkgData;
import protocol.ServerConfigData;
import protocol.PkgJobData;

/**
 * Classe de interface IO e sockets.
 * Esta classe de metodos estáticos trabalha com objectos serializados.
 * @author tpires
 *
 */
public class IOperations {

	/**
	* Metodo estatico que escreve um qualquer objecto serializado em ficheiro no disco 
	* @param obj Objecto a serializado a escrever em IO
	* @param fileName Nome do ficheiro a conter o objecto.
	*/
	public static void saveIOObjData (Object obj,String fileName)
	{
		
		try {
			FileOutputStream fOut = new FileOutputStream(fileName);
			ObjectOutputStream oOut = new ObjectOutputStream (fOut);
			
			oOut.writeObject(obj);
			//JOptionPane.showMessageDialog (null,"Ficheiro escrito com sucesso!!");
			oOut.close();

		}
		catch (ClassCastException e)
		{
			JOptionPane.showMessageDialog (null,"Oops! "+ e.toString());
		}
		catch (InvalidClassException e)	{
			JOptionPane.showMessageDialog (null,"Oops! "+ e.toString());
		}
		catch (NotSerializableException e){
			JOptionPane.showMessageDialog (null,"Oops! "+ e.toString());
		}
		catch (IOException e){
			//JOptionPane.showMessageDialog (null,"Oops! "+ e.toString());
		}
	}
	/**
	 * Método estático que carrega de IO um ficheiro contendo um objecto do tipo PKG
	 * @param fileName String com o nome do ficheiro que contem os dados de objecto
	 * @return NewPkgData Devolve um objecto do tipo NewPkgData carregado de ficheiro IO  
	 */
	public static NewPkgData loadIOObjPkgData (String fileName)
	{
		
		try {
			FileInputStream fIn = new FileInputStream(fileName);
			ObjectInputStream oIn = new ObjectInputStream (fIn);

			NewPkgData recievedData = (NewPkgData) oIn.readObject();
			//JOptionPane.showMessageDialog (null,"Ficheiro carregado com sucesso!!");
			oIn.close();
			
			return recievedData;
		}
		catch (ClassCastException e)
		{
			JOptionPane.showMessageDialog (null,"Oops! "+ e.toString());
		}
		catch (ClassNotFoundException e)
		{
			JOptionPane.showMessageDialog (null,"Oops! "+ e.toString());
		}
		catch (InvalidClassException e)
		{
			JOptionPane.showMessageDialog (null,"Oops! "+ e.toString());
		}
		catch (NotSerializableException e)
		{
			JOptionPane.showMessageDialog (null,"Oops! "+ e.toString());
		}
		catch (IOException e)
		{
		//	JOptionPane.showMessageDialog (null,"Oops! "+ e.toString());
		}
		
		return null;
	}
	/**
	 * Método estático que carrega de IO um ficheiro contendo um objecto do tipo ClientConfigData
	 * @param fileName String com o nome do ficheiro que contem os dados de objecto
	 * @return clientConfigData Devolve um objecto do tipo ClientConfigData carregado de ficheiro IO  
	 */
	public static ClientConfigData loadIOObjConfigData (String fileName)
	{
		
		try {
			FileInputStream fIn = new FileInputStream(fileName);
			ObjectInputStream oIn = new ObjectInputStream (fIn);

			ClientConfigData recievedData = (ClientConfigData) oIn.readObject();
		//	JOptionPane.showMessageDialog (null,"Ficheiro carregado com sucesso!!");
			oIn.close();
			
			return recievedData;
		}
		catch (ClassCastException e)
		{
			JOptionPane.showMessageDialog (null,"Oops! "+ e.toString());
		}
		catch (InvalidClassException e)
		{
			JOptionPane.showMessageDialog (null,"Oops! "+ e.toString());
		}
		catch (ClassNotFoundException e)
		{
			JOptionPane.showMessageDialog (null,"Oops! "+ e.toString());
		}
		catch (IOException e)
		{
			//JOptionPane.showMessageDialog (null,"Oops! "+ e.toString());
			
		}
		
		return null;
	}
	/**
	 * Método estático que carrega de IO um ficheiro contendo um objecto do tipo ServerConfigData
	 * @param fileName String com o nome do ficheiro que contem os dados de objecto
	 * @return ServerConfigData Devolve um objecto do tipo ServerConfigData carregado de ficheiro IO  
	 */
	public static ServerConfigData loadIOServerConfigData (String fileName)
	{
		try {
			FileInputStream fIn = new FileInputStream(fileName);
			ObjectInputStream oIn = new ObjectInputStream (fIn);

			ServerConfigData recievedData = (ServerConfigData) oIn.readObject();
		//	JOptionPane.showMessageDialog (null,"Ficheiro carregado com sucesso!!");
			oIn.close();
			
			return recievedData;
		}
		catch (ClassCastException e)
		{
			JOptionPane.showMessageDialog (null,"Oops! "+ e.toString());
		}
		catch (InvalidClassException e)
		{
			JOptionPane.showMessageDialog (null,"Oops! "+ e.toString());
		}
		catch (ClassNotFoundException e)
		{
			JOptionPane.showMessageDialog (null,"Oops! "+ e.toString());
		}
		catch (IOException e)
		{
			//JOptionPane.showMessageDialog (null,"Oops! "+ e.toString());
			
		}
		
		return null;
	}
	
	/**
	 * Método estático que carrega de IO um ficheiro contendo um objecto do tipo ServerConfigData
	 * @param fileName String com o nome do ficheiro que contem os dados de objecto
	 * @return ServerConfigData Devolve um objecto do tipo ServerConfigData carregado de ficheiro IO  
	 */
	public static PkgJobData loadIOObjJobData (String fileName)
	{
		try {
			
			FileInputStream fIn = new FileInputStream(fileName);
			ObjectInputStream oIn = new ObjectInputStream (fIn);

			PkgJobData recievedData = (PkgJobData) oIn.readObject();
		//	JOptionPane.showMessageDialog (null,"Ficheiro carregado com sucesso!!");
			oIn.close();
			
			return recievedData;
		}
		catch (ClassCastException e)
		{
			JOptionPane.showMessageDialog (null,"Oops! "+ e.toString());
		}
		catch (InvalidClassException e)
		{
			JOptionPane.showMessageDialog (null,"Oops! "+ e.toString());
		}
		catch (ClassNotFoundException e)
		{
			JOptionPane.showMessageDialog (null,"Oops! "+ e.toString());
		}
		catch (IOException e)
		{
			//JOptionPane.showMessageDialog (null,"Oops! "+ e.toString());
			
		}
		
		return null;
	}

}
