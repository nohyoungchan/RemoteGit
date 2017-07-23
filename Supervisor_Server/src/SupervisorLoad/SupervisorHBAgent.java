package SupervisorLoad;

import java.io.PrintWriter;

public class SupervisorHBAgent {
	public String name;
	public String password;
	public String hgAgentURL;  //This is second
	public String delaySec;

	
	
	SupervisorHBAgent(){
		name ="none";
		password ="none";
		hgAgentURL = "none";
		delaySec = "none";
	}
	
	SupervisorHBAgent(String name, String password, String hgAgentURL, String delaySec){
		this.name = name;
		this.password = password;	
		this.hgAgentURL = hgAgentURL;
		this.delaySec = delaySec;
		
	}
	
	public String getName(){
		return name;
	}
	
	public String getPassword(){
		return password;
	}
	
	public String getHbAgentURL(){
		return hgAgentURL;
	}
	
	public String getHbDelaySec(){
		return delaySec;
	}
	
	
	

}
