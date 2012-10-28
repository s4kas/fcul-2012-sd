package org.sd.common.messages;

import java.io.Serializable;
import java.util.Calendar;

public class Event implements Serializable {
	
	private static final long serialVersionUID = -6550013594153572430L;
	private Calendar EventStartDateTime;
	private Calendar EventEndDateTime;
	private String Description;
	
	public Event() {
		EventStartDateTime = Calendar.getInstance();
		EventEndDateTime = Calendar.getInstance();
		EventEndDateTime.add(Calendar.HOUR_OF_DAY,EventEndDateTime.get(Calendar.HOUR_OF_DAY)+1);
		Description = "Test";
	}	
}
