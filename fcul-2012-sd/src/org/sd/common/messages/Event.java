package org.sd.common.messages;

import java.io.Serializable;
import java.util.Calendar;

public class Event implements Serializable {
	
	private static final long serialVersionUID = -6550013594153572430L;
	private Calendar eventDate;
	private String title;
	private String content;
	
	public Event() {
		
	}
	
	public Event(int day, int month, int year, String title, String content) {
		this.eventDate = Calendar.getInstance();
		this.eventDate.set(Calendar.DAY_OF_MONTH, day);
		this.eventDate.set(Calendar.MONTH, month);
		this.eventDate.set(Calendar.YEAR,year);
		this.title = title;
		this.content = content;
	}

	public Calendar getEventDate() {
		return eventDate;
	}

	public void setEventDate(Calendar eventDate) {
		this.eventDate = eventDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
