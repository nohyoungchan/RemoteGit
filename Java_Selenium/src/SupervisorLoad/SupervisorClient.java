package SupervisorLoad;

/**
 * This is an object contains information on Supervisor like username, password, etc.
 * @author Admin
 *
 */
public class SupervisorClient {
	public String name;
	public String password;
	public String delaySec;  //This is second
	public String alreadyLoggedInNLA;
	public String printerWriterName;
	
	
	public SupervisorClient(){
		name ="none";
		password ="none";
		delaySec = "none";
		alreadyLoggedInNLA = "no";  //default is not logged in yet.
		printerWriterName = "none";
	}
		
	public String getName(){
		return name;
	}
	
	public String getPassword(){
		return password;
	}
	
	public String getDelaySec(){
		return delaySec;
	}
	
	
	

}
