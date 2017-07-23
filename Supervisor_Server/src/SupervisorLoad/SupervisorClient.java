package SupervisorLoad;

import java.io.PrintWriter;

public class SupervisorClient {
	public String name;
	public String password;
	public String delaySec;  //This is second
	public String alreadyLoggedInNLA;
	public String printerWriterName;
	
	
	SupervisorClient(){
		name ="none";
		password ="none";
		delaySec = "none";
		alreadyLoggedInNLA = "no";  //default is not logged in yet.
		printerWriterName = "none";
	}
	
	SupervisorClient(String name, String password, String delaySec, String alreadyLoggedInNLA){
		this.name = name;
		this.password = password;	
		this.delaySec = delaySec;
		this.alreadyLoggedInNLA = alreadyLoggedInNLA;
		
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
