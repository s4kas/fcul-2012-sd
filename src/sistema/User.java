package sistema;

public class User {

	private String username;
	private String pass;
	
	
	public User (String u, String p) {
		this.pass = p;
		this.username = u;
		
	}
	
	public String getUserName (){
		return username;	
	}
	
	public String getPass(){
		return pass;
	}
	
}
