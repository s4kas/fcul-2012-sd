package org.sd.data;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import org.sd.io.IoOperations;
import org.sd.io.Writable;


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
	
	public synchronized ArrayList<Evento> ListEventos (){
		return agenda;
	}
	
	
	/**********************************************************************
	 * Removes eventos from the agenda.
	 * @param newEvento new evento
	 * @return true if removed.
	 */
	public synchronized boolean removesEvento (Evento newEvento){
		//goes find new element in agenda.
		//returns false if not removed/exists.
		return 	agenda.remove(newEvento);
	}
	
	
	/**********************************************************************
	 * Adds Eventos to the agenda.
	 * @param newEvento new evento
	 * @return true if added.
	 */
	public synchronized boolean addEvento (Evento newEvento){
		boolean exists = false;
		
		//goes find new element in agenda.
		for (Evento ev: agenda){
			if (ev.equals(newEvento)){
				exists=true;
				break;
			}
		}
		if (!exists) {
			agenda.add(newEvento);
			save();
		}
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
	public synchronized boolean alterEvento(Evento newEvento) {
		Evento temp = null;
		
		for (Evento e : agenda) {
			if (e.equalsByDateTime(newEvento)){
				temp=e;
				break;
			}
		}
		
		if (temp!=null){
			int index =agenda.indexOf(temp);
			agenda.remove(index);
			agenda.add(index, newEvento);
			save();
		}
		 
		return temp!=null;
	}

	
	/***********************************************************************
	 * Save agenda to file.
	 */
	public void save() {
		
		try{
			IoOperations.saveAgendaToFile(agenda, new File("agenda.obj"));
		} catch (IOException io){
		}
	}

	
	/***********************************************************************
	 * Loads agenda to memory
	 */

	public void load() {
		this.agenda.addAll(IoOperations.loadAgendaFromFile(new File("agenda.obj")));
	}

}
