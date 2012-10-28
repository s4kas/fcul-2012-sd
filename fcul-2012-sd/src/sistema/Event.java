package sistema;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class Event implements Serializable {
	
	private static final long serialVersionUID = -6550013594153572430L;
	private Calendar EventStartDateTime;
	private Calendar EventEndDateTime;
	private String Description;
	private User u;
	
	private void EventTest(User u){
		EventStartDateTime = Calendar.getInstance();
		EventEndDateTime = Calendar.getInstance();
		EventEndDateTime.add(Calendar.HOUR_OF_DAY,EventEndDateTime.get(Calendar.HOUR_OF_DAY)+1);
		Description = "Test";
		this.u = u;
	}
	
	public Event() {
		EventStartDateTime = Calendar.getInstance();
		EventEndDateTime = Calendar.getInstance();
		EventEndDateTime.add(Calendar.HOUR_OF_DAY,EventEndDateTime.get(Calendar.HOUR_OF_DAY)+1);
		Description = "Test";
	}
	
	public Event (User u){
		EventTest(u);
	}
	
	
}
