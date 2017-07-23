package Actors;

import java.io.PrintWriter;

public class CCSupervisor {
	public String supName;
	public String supUserName;
	public String supCOS;  //This is second
	public String supAgentName;
	public String supPermission;
	
	
	CCSupervisor(){
		supName ="none";
		supUserName ="none";
		supCOS = "none";
		supAgentName = "no";  //default is not logged in yet.
		supPermission = "none";
	}

}
