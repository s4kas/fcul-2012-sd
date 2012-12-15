package org.sd.io;

public interface Writable {
	
	
	/****************************
	 * guarda os dados em ficheiro.
	 * 
	 */
	void save();
	
	/****************************
	 * carrega os dados de ficheiro.
	 * 
	 */

	void load();
	
}
