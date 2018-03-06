package SupervisorLoad;

/**
 * This is an object contains information on Supervisor like username, password, etc.
 * @author Admin
 *
 */
public class SupervisorLoad_Client {
	public String name;
	public String password;
	public String delaySec;  //This is second
	public String alreadyLoggedInNLA;
	public String printerWriterName;
	
	
	public SupervisorLoad_Client(){
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
