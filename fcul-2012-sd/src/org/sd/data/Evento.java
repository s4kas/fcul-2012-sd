package org.sd.data;

import java.io.Serializable;
import java.util.Calendar;

import org.sd.server.ServerAgenda;

public final class Evento implements Serializable ,Cloneable{

	private static final long serialVersionUID = 5789258183400060095L;
	private Calendar starts;
	private Calendar ends;
	private String descript;
	private String owner;

	/***************************************************************************************************
	 * Evento Imutable constructor 
	 * @param start_year
	 * @param start_month
	 * @param start_date
	 * @param start_hourOfDay
	 * @param start_minute
	 * @param ends_year
	 * @param ends_month
	 * @param ends_date
	 * @param ends_hourOfDay
	 * @param ends_minute
	 * @param description
	 * @param owner
	 */
	public Evento(int start_year, int start_month, int start_date, int start_hourOfDay, int start_minute,
					int ends_year, int ends_month, int ends_date, int ends_hourOfDay, int ends_minute,
					String description, String owner){
	
		this.starts.set(start_year, start_month, start_date, start_hourOfDay, start_minute);
		this.ends.set(ends_year, ends_month, ends_date, ends_hourOfDay, ends_minute);
		this.descript = description;
		this.owner = owner;
	}
	
	/********************************************************************************************
	 * 
	 */
	public Evento clone () {
		 Evento newEvento = null;
		 try {
			 newEvento = (Evento) super.clone();
			 newEvento.starts =(Calendar) starts.clone();
			 newEvento.ends = (Calendar) ends.clone();
			 newEvento.descript = this.descript;
			 newEvento.owner= this.owner;

		} catch (CloneNotSupportedException e) {
			ServerAgenda.addToInfoConsole("Evento não é clonavel! oops!!!");
			e.printStackTrace();
		}
		 
		return newEvento; 
	}
	
	/****************************************************************************
	 * Getters
	 */
	public String getStarts (int i){	return starts.getTime().toString();}
	public String getEnds (int i){		return ends.getTime().toString(); }
	public String getDescript(){		return descript;}
	public String getOwner(){			return owner;}
	

	/****************************************************************************
	 * Equals 
	 * @param other Evento.
	 * @return true if is equal
	 */
	public boolean equals (Evento other){
		
		return this.starts.equals(other.starts) &&
				this.ends.equals(other.ends) &&
				this.descript==other.descript &&
				this.owner==other.owner;
	}
	
	
	/***************************************************************************
	 * eventos overlaps 
	 * @param other Evento.
	 * @return true if one overlaps the other.
	 */
	public boolean overlaps (Evento other){
		return this.starts.before(other.ends) && this.ends.after(other.starts);
	}
	
	/***************************************************************************
	 * Evento date content
	 */
	public String toString(){
		return "\n"+starts.toString()+" : "+ends.toString();
	}
}
