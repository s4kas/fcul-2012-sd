package sistema;

import java.util.Calendar;
import java.util.Date;

public class Event {
	
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
	
	
	public Event (User u){
		EventTest(u);
	}
	
	
}
