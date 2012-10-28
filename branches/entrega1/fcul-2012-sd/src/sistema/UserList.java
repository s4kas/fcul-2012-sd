package sistema;

import java.io.Serializable;
import java.util.ArrayList;

public class UserList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -475709268113782278L;
	
	private ArrayList <User> userList = new ArrayList<User> ();
	

	public UserList (){
		//LoadsUserlist From file;
		userList.add(new User ("tiago","pass"));

		
	}
	
}
