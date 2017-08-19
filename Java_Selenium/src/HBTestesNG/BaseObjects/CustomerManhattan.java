package HBTestesNG.BaseObjects;

import org.sikuli.script.*;

import Utility.Utilities;



public class CustomerManhattan  extends TestObject {
	public String username, password, extension, did, agentType;
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
		wait(2);
		String strAutoItCommand = "AutoIt3.exe " + autoitFolder + "manhattan_relocate.exe " + x + " " + y;
    	Utilities.executeCommand(strAutoItCommand);
    	currentState ="started";
		currentTime();
	}
	
	public void logIn(String username, String password) throws Exception {
		log.info("\n@ Manhattan : " +  username + " #### Login Manhattan client ####");

    	Pattern patternUsername = new Pattern(imgFolder +"mh_username_left.PNG").targetOffset(100, 0);
    	Pattern patternPassword = new Pattern(imgFolder +"mh_password_left.PNG").targetOffset(100, 0);

    	String loginBtn = imgFolder +"ic_login_loginButton.PNG";
    	String connectionNotSecure = imgFolder + "mh_connectionNotSecure.PNG";
    	String proceedBtn = imgFolder + "mh_proceedBtn.PNG";
    	
    	activateManhattan();
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
		currentState ="loggedin_idle";
		currentTime();
	}
	
	public void logIn() throws Exception {
		log.info("\n@ Manhattan : " +  username + " #### Login Manhattan client ####");

    	Pattern patternUsername = new Pattern(imgFolder +"mh_username_left.PNG").targetOffset(100, 0);
    	Pattern patternPassword = new Pattern(imgFolder +"mh_password_left.PNG").targetOffset(100, 0);

    	String loginBtn = imgFolder +"ic_login_loginButton.PNG";
    	String connectionNotSecure = imgFolder + "mh_connectionNotSecure.PNG";
    	String proceedBtn = imgFolder + "mh_proceedBtn.PNG";
    	
    	activateManhattan();
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
		currentState = "loggedin_idle";
		currentTime();
	}
	
	public void logOut(int waitSec) throws Exception {
		log.info("\n@ Manhattan : " +  username + " #### LogOut Manhattan client ####");

    	Pattern patternStateBtn = new Pattern(imgFolder +"mh_stateBtn.PNG").targetOffset(100, 0);
    	//String stateBtn = imgFolder + "mh_stateBtn.PNG";
    	String logoutBtn = imgFolder +"mh_logoutBtn.PNG";

    	wait(waitSec);
    	activateManhattan();
    	clickAppear(screen, patternStateBtn);
    	wait(1);
    	clickAppear(screen, logoutBtn);
    	currentState ="loggedout";
		currentTime();
	}
	
	public void closeManhattan(int waitSec) throws Exception {
		log.info("\n@ Manhattan : " +  username + " #### Close Manhattan client by pressing X ####");

    	String closeIcon = imgFolder +"mh_closeManhattan";
    	activateManhattan();
    	wait(waitSec);
    	clickAppear(screen, closeIcon);
    	currentState ="closed";
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
	    currentState ="loggedin_busy";
	    
	    //signoutWebAgent(); 
	    //tearDownAll();
	  }
	  
	  public void activateManhattan() {
		  log.info("\n@ Manhattan : " +  username + " #### Activating Manhattan client ####");
		  String strAutoItCommand = "AutoIt3.exe " + autoitFolder + "manhattan_activateMH.exe";
	      Utilities.executeCommand(strAutoItCommand);
	  }
	  
	  public void makeACDCall(String number) throws Exception{
		  log.info("\n@ Manhattan : " +  username + " #### Before making ACD call ####");
		  int j;
		  Pattern patternoffhookBtn = new Pattern(imgFolder +"mh_offhookBtn.PNG").targetOffset(100, 0);

		  activateManhattan();
		  clickAppear(screen, patternoffhookBtn);
		  screen.type(number);
		  screen.type(Key.ENTER);
		  currentState ="loggedin_busy";

	  }
	  
	  public void dropCall() throws Exception {
		  
		  log.info("\n@ Manhattan : " +  username + " #### Dropping a call ####");
		  String callBox = imgFolder +"mh_callBox";
		  String dropBtn = imgFolder +"mh_dropBtn";
	    	
		  activateManhattan();
    	  clickAppear(screen, callBox);
    	  clickAppear(screen, dropBtn);
    	  
    	  currentState ="loggedin_idle";
		  currentTime();
		 
	  }
	  
	  public void dropCall(int waitSec) throws Exception {
		  
		  log.info("\n@ Manhattan : " +  username + " #### Dropping a call ####");
		  String callBox = imgFolder +"mh_callBox";
		  String dropBtn = imgFolder +"mh_dropBtn";
	    	
		  activateManhattan();
		  wait(waitSec, "# Customer waits before drop the call");
    	  clickAppear(screen, callBox);
    	  clickAppear(screen, dropBtn);
    	  
    	  currentState ="loggedin_idle";
		  currentTime();
		 
	  }
	  
	  public void answerCall(int waitSec) throws Exception {
		  log.info("\n@ Manhattan : " +  username + " #### Answering a call ####");
		  String answerBtn = imgFolder +"mh_answerBtn";
	    	
		  activateManhattan();
    	  wait(waitSec);
    	  clickAppear(screen, answerBtn);
    	  currentState ="loggedin_busy";
		  currentTime();
		 

	  }
	  
	  public void abandonCall() throws Exception {
		  
		  log.info("\n@ Manhattan : " +  username + " #### Abandoning a call ####");
		  String abandonBtn = imgFolder +"mh_abandonBtn";
	    	
		  activateManhattan();
	      clickAppear(screen, abandonBtn);
	      currentState ="loggedin_idle";
		  currentTime();
		 

	  }
	  
	  public void dropCall_reset() throws Exception {
		  //if (state.contains("idle")) return;
		  
		  log.info("\n@ Manhattan : " +  username + " #### Dropping a call for a reset ####");
		  String callBox = imgFolder +"mh_callBox";
		  String dropBtn = imgFolder +"mh_dropBtn";
	    	
		  activateManhattan();
    	  if (clickAppear(screen, callBox, 5)) {
    		  clickAppear(screen, dropBtn, 5);
    	  }
    	  
		  currentTime();

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
	   * This waits until "img" appear and click.
	   * @param s  : This is screen object 
	   * @param img : This is image to click
	   * @param numTry: This is to try how many time to check if an image appears.
	   */
	  public boolean clickAppear(Screen s, String img, int numTry) {
		int i = 0;
		boolean returnValue = true;
		try {
		while (true) {
				if (s.exists(img) != null) {
				    s.click(img);
				    break;
				} 
				i++;
				wait(2);
				if (i > numTry) {
					log.info("\n@ Manhattan : " +  username + " : image doesn't appear after " + numTry + " Tries  => " + img);
					throw new Exception();
				}
			}
		}catch(Exception e) {
			returnValue = false;
			log.error("Fail to click: " + img + " => " + e.toString());
		}
		
		return returnValue;
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
			manhattan.logIn("agent6yc1@c1yc1.com", "Shoreadmin1");
		    manhattan.makeACDCall("4021");
			manhattan.logOut(5);
			manhattan.closeManhattan(2);
			sleep(1000);
		 }
			
	  }

}
