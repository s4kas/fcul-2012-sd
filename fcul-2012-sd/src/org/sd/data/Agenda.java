package org.sd.data;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import org.sd.io.IoOperations;
import org.sd.io.Writable;
import org.sd.server.ServerAgenda;


public class Agenda implements Serializable , Writable, IAgenda{

	private static final long serialVersionUID = -2260478952930826599L;
	ArrayList <Evento> agenda = new ArrayList <Evento>();

	
	/*************************************************************
	 * Initialize agenda.
	 */
	public Agenda(){
		//loads agenda into memory
		load();
	}
	
	
	public void agendaDump (){
		for (Evento e: agenda){
			ServerAgenda.addToInfoConsole(e.toString());
		}
	}
	
	/**********************************************************************
	 * Removes eventos from the agenda.
	 * @param newEvento new evento
	 * @return true if removed.
	 */
	public boolean removesEvento (Evento newEvento){
		//goes find new element in agenda.
		//returns false if not removed/exists.
		return 	agenda.remove(newEvento);
	}
	
	
	/**********************************************************************
	 * Adds Eventos to the agenda.
	 * @param newEvento new evento
	 * @return true if added.
	 */
	public boolean addEvento (Evento newEvento){
		boolean exists = false;
		
		//goes find new element in agenda.
		for (Evento ev: agenda){
			if (ev.equals(newEvento)){
				exists=true;
				break;
			}
		}
		if (!exists) agenda.add(newEvento);
		//returns false if not added.
		//return true if added
		return !exists;
	}
	

	/**********************************************************************
	 * replaces Eventos  the agenda.
	 * @param newEvento new evento
	 * @param existing Evento
	 * @return true if added.
	 */
	public boolean alterEvento(Evento existingEvento, Evento newEvento) {
		//return true if remove and added
		return agenda.remove(existingEvento) && agenda.add(newEvento); 
	}

	
	/***********************************************************************
	 * Save agenda to file.
	 */
	public void save() {
		
		try{
			ServerAgenda.addToInfoConsole("Saving agenda file on disk");
			IoOperations.saveAgendaToFile(agenda, new File("agenda.obj"));	
		} catch (IOException io){
			ServerAgenda.addToInfoConsole("IO OOPS! AGENDA NOT SAVED!");
		}
	}

	
	/***********************************************************************
	 * Loads agenda stored on file to memory
	 */

	public void load() {
		ServerAgenda.addToInfoConsole("Loading agenda file from disk");
		//this.agenda.addAll(IoOperations.loadAgendaFromFile(new File("agenda.obj")));
	}




	

	
}
