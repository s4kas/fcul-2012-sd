package org.sd.common.messages;

import java.io.Serializable;
import java.util.Calendar;

public class Event implements Serializable {
	
	private static final long serialVersionUID = -6550013594153572430L;
	private Calendar eventStartDate;
	private Calendar eventEndDate;
	private String title;
	private String content;
	
	public Event() {
		
	}
	
	public Event(int day, int month, int year, int startHour, int startMinutes, int endHour, int endMinutes,
			String title, String content) {
		this.eventStartDate = Calendar.getInstance();
		this.eventStartDate.set(Calendar.DAY_OF_MONTH, day);
		this.eventStartDate.set(Calendar.MONTH, month);
		this.eventStartDate.set(Calendar.YEAR,year);
		this.eventStartDate.set(Calendar.HOUR_OF_DAY, startHour);
		this.eventStartDate.set(Calendar.MINUTE, startMinutes);
		
		this.eventEndDate = Calendar.getInstance();
		this.eventEndDate.set(Calendar.DAY_OF_MONTH, day);
		this.eventEndDate.set(Calendar.MONTH, month);
		this.eventEndDate.set(Calendar.YEAR,year);
		this.eventEndDate.set(Calendar.HOUR_OF_DAY, endHour);
		this.eventEndDate.set(Calendar.MINUTE, endMinutes);
		
		this.title = title;
		this.content = content;
	}

	public Calendar getEventStartDate() {
		return eventStartDate;
	}

	public void setEventStartDate(Calendar eventStartDate) {
		this.eventStartDate = eventStartDate;
	}

	public Calendar getEventEndDate() {
		return eventEndDate;
	}

	public void setEventEndDate(Calendar eventEndDate) {
		this.eventEndDate = eventEndDate;
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
