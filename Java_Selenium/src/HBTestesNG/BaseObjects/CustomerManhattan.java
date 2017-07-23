package HBTestesNG.BaseObjects;

import org.sikuli.script.*;

public class CustomerManhattan  extends Agent {
   
	public CustomerManhattan() throws Exception{
		super();
		
	}
	
	
/*	public CustomerCMWin(String username, String password, String extension) throws Exception {
		super(username, password, extension);
		log.info("\n@(" + agentType + ") " +  username + " #### Initializing ####");
	}*/
	
	
	public void logIn() throws Exception {
		log.info("\n@(" + agentType + ") " +  username + " #### Opening firefox ####");
		Screen s = new Screen();
    	Pattern patternUsername = new Pattern(".\\imgs\\ic_login_username.PNG");
    	Pattern patternPassword = new Pattern(".\\imgs\\ic_login_password.PNG");
    	Pattern patternLoginButton = new Pattern(".\\imgs\\ic_login_loginButton.PNG");
            //s.click(".\\imgs\\ic_login_username.PNG");
            //s.write("hello world");
    	s.click(patternUsername);
    	s.write("young6");
    	s.click(patternPassword);
    	s.write("changeme");
    	s.click(patternLoginButton);
    
		log.info("\n@(" + agentType + ") " +  username + " #### Go to Login Page ####");
		currentTime();
	}
	
	  public void makeACDCall_Continually(String number) throws Exception {
		  
		int j;
	    
	    for (int i = 0; i < 1000; i++) {
	    	j = i+1;
	    	log.info("\n@(" + agentType + ") " +  username + " @@@@@@@@@@@@@@@ " + j + "th call starts @@@@@@@@@@@@@@@");
	    	makeACDCall(number);
	        //maxmizeFireFox();
	        //wait(10,"Talking");
	    	wait(2);

		}
	    state = "busy";
	    
	    //signoutWebAgent(); 
	    //tearDownAll();
	  }
	  
	  public boolean makeACDCall(String number) throws Exception{
		  log.info("\n@(" + agentType + ") " +  username + " #### Before making ACD call ####");
		  int j;
		  boolean working;
		  working = true;
		  
		
		  return working;

	  }
	  
	  public void dropCall() throws Exception {
		  
		  log.info("\n@(" + agentType + ") " +  username + " #### Dropping a call ####");
		 
	  }
	  
	  public void answerCall() throws Exception {
		  
		  log.info("\n@(" + agentType + ") " +  username + " #### Answering a call ####");
		 

	  }
	  
	  public void dropCall_reset() throws Exception {
		  if (state.contains("idle")) return;
		  
		  log.info("\n@(" + agentType + ") " +  username + " #### Dropping a call for a reset ####");
		 

	  }
	  
	  
	  public void tearDownAll() throws Exception {
		log.info("\n@(" + agentType + ") " +  username + " #### Close FireFox ####");
	    currentTime();	    
	  }

}
