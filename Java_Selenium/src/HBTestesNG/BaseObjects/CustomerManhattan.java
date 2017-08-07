package HBTestesNG.BaseObjects;

import org.sikuli.script.*;
import org.testng.TestNG;

import Utility.Utilities;



public class CustomerManhattan  extends TestObject {
	public String username, password, extension, did, agentType, state;
	public String imgFolder, autoitFolder;
	public Screen screen;
   
	public CustomerManhattan() throws Exception{
		super();
		screen = new Screen();
		autoitFolder = ".\\Resources\\autoit\\";
		imgFolder = ".\\Resources\\imgs\\";
		
		
	}
	
	public void startManhattan(String x, String y) throws Exception {
		log.info("\n@ Manhattan : " +  username + " #### Starts Manhattan client: Location : " + x + "/" + y + " ####");
		Utilities.executeCommand("ShoreTel.exe");
		wait(5);
		String strAutoItCommand = "AutoIt3.exe " + autoitFolder + "manhattan_relocate.exe " + x + " " + y;
    	Utilities.executeCommand(strAutoItCommand);
		currentTime();
	}
	
	public void logIn(String username, String password) throws Exception {
		log.info("\n@ Manhattan : " +  username + " #### Login Manhattan client ####");

    	Pattern patternUsername = new Pattern(imgFolder +"mh_username_left.PNG").targetOffset(100, 0);
    	Pattern patternPassword = new Pattern(imgFolder +"mh_password_left.PNG").targetOffset(100, 0);

    	String loginBtn = imgFolder +"ic_login_loginButton.PNG";
    	String connectionNotSecure = imgFolder + "mh_connectionNotSecure.PNG";
    	String proceedBtn = imgFolder + "mh_proceedBtn.PNG";
    	
    	clickAppear(screen, patternUsername);
    	screen.write(username);
    	clickAppear(screen, patternPassword);
    	screen.write(password);
    	clickAppear(screen, loginBtn);
    	
    	while (null != screen.exists(connectionNotSecure)) {
    		log.info("@ Handling connection not secure");
    		screen.click(connectionNotSecure);
    		screen.click(proceedBtn);
    		wait(2);
    	}
    
		log.info("\n@ Manhattan : " +  username + " #### Login Completed ####");
		currentTime();
	}
	
	public void logIn() throws Exception {
		log.info("\n@ Manhattan : " +  username + " #### Login Manhattan client ####");

    	Pattern patternUsername = new Pattern(imgFolder +"mh_username_left.PNG").targetOffset(100, 0);
    	Pattern patternPassword = new Pattern(imgFolder +"mh_password_left.PNG").targetOffset(100, 0);

    	String loginBtn = imgFolder +"ic_login_loginButton.PNG";
    	String connectionNotSecure = imgFolder + "mh_connectionNotSecure.PNG";
    	String proceedBtn = imgFolder + "mh_proceedBtn.PNG";
    	
    	clickAppear(screen, patternUsername);
    	screen.write(username);
    	clickAppear(screen, patternPassword);
    	screen.write(password);
    	clickAppear(screen, loginBtn);
    	
    	while (null != screen.exists(connectionNotSecure)) {
    		log.info("@ Handling connection not secure");
    		screen.click(connectionNotSecure);
    		screen.click(proceedBtn);
    		wait(2);
    	}
    
		log.info("\n@ Manhattan : " +  username + " #### Login Completed ####");
		currentTime();
	}
	
	public void logOut(int waitSec) throws Exception {
		log.info("\n@ Manhattan : " +  username + " #### LogOut Manhattan client ####");

    	Pattern patternStateBtn = new Pattern(imgFolder +"mh_stateBtn.PNG").targetOffset(100, 0);
    	//String stateBtn = imgFolder + "mh_stateBtn.PNG";
    	String logoutBtn = imgFolder +"mh_logoutBtn.PNG";
    	

    	wait(waitSec);
    	clickAppear(screen, patternStateBtn);
    	wait(1);
    	clickAppear(screen, logoutBtn);
		currentTime();
	}
	
	public void closeManhattan(int waitSec) throws Exception {
		log.info("\n@ Manhattan : " +  username + " #### Close Manhattan client by pressing X ####");

    	String closeIcon = imgFolder +"mh_closeManhattan";
    	
    	wait(waitSec);
    	clickAppear(screen, closeIcon);
		currentTime();
	}
	
	  public void makeACDCall_Continually(String number) throws Exception {
		  
		int j;
	    
	    for (int i = 0; i < 1000; i++) {
	    	j = i+1;
	    	log.info("\n@ Manhattan : " +  username + " @@@@@@@@@@@@@@@ " + j + "th call starts @@@@@@@@@@@@@@@");
	    	makeACDCall(number);
	        //maxmizeFireFox();
	        //wait(10,"Talking");
	    	wait(2);

		}
	    state = "busy";
	    
	    //signoutWebAgent(); 
	    //tearDownAll();
	  }
	  
	  public void makeACDCall(String number) throws Exception{
		  log.info("\n@ Manhattan : " +  username + " #### Before making ACD call ####");
		  int j;
		  String numTextBox= imgFolder + "mh_numTextBox.PNG";
		  clickAppear(screen, numTextBox);
		  screen.type(number);
		  screen.type(Key.ENTER);

	  }
	  
	  public void dropCall() throws Exception {
		  
		  log.info("\n@ Manhattan : " +  username + " #### Dropping a call ####");
		 
	  }
	  
	  public void answerCall() throws Exception {
		  
		  log.info("\n@ Manhattan : " +  username + " #### Answering a call ####");
		 

	  }
	  
	  public void dropCall_reset() throws Exception {
		  if (state.contains("idle")) return;
		  
		  log.info("\n@ Manhattan : " +  username + " #### Dropping a call for a reset ####");
		 

	  }
	  
	  
	  public void tearDownAll() throws Exception {
		log.info("\n@ Manhattan : " +  username + " #### Logout and Close Manhattan client ####");
		logOut(2);
		closeManhattan(2);
	    currentTime();	    
	  }
	  
	  /**
	   * This waits until "img" appear and click.
	   * @param s  : This is screen object 
	   * @param img : This is image to click
	   * @throws Exception
	   */
	  public void clickAppear(Screen s, String img) throws Exception {
		int i = 0;
		while (true) {
			if (s.exists(img) != null) {
			    s.click(img);
			    break;
			} 
			i++;
			wait(2);
			if (i > 10) {
				log.info("\n@ Manhattan : " +  username + " : image doesn't appear so exception => " + img);
				throw new Exception();
			}
		}
	  }
	  
	  
	  /**
	   * This waits until "img" appear and click: This img is a pattern, so you can use "offset" from image.
	   * @param s  : This is screen object 
	   * @param img : This is image to click
	   * @throws Exception
	   */
	  public void clickAppear(Screen s, Pattern img) throws Exception {
			int i = 0;
			while (true) {
				if (s.exists(img) != null) {
				    s.click(img);
				    break;
				} 
			
				i++;
				wait(2);
				if (i > 10) {
					log.info("\n@ Manhattan : " +  username + " : image doesn't appear so exception => " + img);
					throw new Exception();
				}
			}
		  }
	  
	  public static void main(String[] args) throws Exception {
			 
		 for(int i=0 ; i < 2; i++) {
			CustomerManhattan manhattan=new CustomerManhattan();
			manhattan.startManhattan("10", "200");
			manhattan.logIn("young6@yc2.com", "changeme");
		    manhattan.makeACDCall("4024");
			manhattan.logOut(5);
			manhattan.closeManhattan(2);
			sleep(1000);
		 }
			
	  }

}
