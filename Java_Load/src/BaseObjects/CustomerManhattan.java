package BaseObjects;

import org.sikuli.script.*;




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
		Thread.sleep(4000);  //somehow wait(4) causes a problem here.
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
    	String voiceMailBtn = imgFolder + "mh_voiceMailBtn.PNG";
    	
    	activateManhattan();
    	clickAppear(screen, patternUsername, 5);
    	screen.write(username);
    	clickAppear(screen, patternPassword, 5);
    	screen.write(password);
    	clickAppear(screen, loginBtn, 5);
    	
    	//This is needed to expand manhattan client
    	clickAppear(screen, voiceMailBtn, 5);
    	clickAppear(screen, voiceMailBtn, 5);
    	
    	
    	
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

    	String loginBtn = imgFolder +"mh_loginBtn.PNG";
    	String connectionNotSecure = imgFolder + "mh_connectionNotSecure.PNG";
    	String proceedBtn = imgFolder + "mh_proceedBtn.PNG";
    	String voiceMailBtn = imgFolder + "mh_voiceMailBtn.PNG";
    	
    	activateManhattan();
    	clickAppear(screen, patternUsername, 5);
    	screen.write(username);
    	clickAppear(screen, patternPassword, 5);
    	screen.type(password);
    	clickAppear(screen, loginBtn, 5);
    	
    	
    	while (null != screen.exists(connectionNotSecure)) {
    		log.info("@ Handling connection not secure");
    		screen.click(connectionNotSecure);
    		screen.click(proceedBtn);
    		wait(2);
    	}
    	
    	//This is needed to expand manhattan client.  It means login successful.
    	if(clickAppear(screen, voiceMailBtn, 5)) {
    		clickAppear(screen, voiceMailBtn, 5);
    	}else {
    		throw new Exception("@ Manhattan : " +  username + " #### Login Failed ####");
    	}
    
		log.info("\n@ Manhattan : " +  username + " #### Login Completed ####");
		currentState = "loggedin_idle";
		currentTime();
	}
	
	public void logOut(int waitSec) throws Exception {
		log.info("\n@ Manhattan : " +  username + " #### LogOut Manhattan client ####");
		if(activateManhattan() == 0) {
    		log.info("## Manhattan is already closed");
    		return;
    	}else {

	    	Pattern patternStateBtn = new Pattern(imgFolder +"mh_stateBtn.PNG").targetOffset(100, 0);
	    	//String stateBtn = imgFolder + "mh_stateBtn.PNG";
	    	String logoutBtn = imgFolder +"mh_logoutBtn.PNG";
	
	    	wait(waitSec);
	    	clickAppear(screen, patternStateBtn, 5);
	    	wait(1);
	    	clickAppear(screen, logoutBtn, 5);
	    	currentState ="loggedout";
			currentTime();
    	}
	}
	
	public void closeManhattan(int waitSec) throws Exception {
		log.info("\n@ Manhattan : " +  username + " #### Close Manhattan client by pressing X ####");

    	String closeIcon = imgFolder +"mh_closeManhattan";
    	if(activateManhattan() == 0) {
    		log.info("## Manhattan is already closed");
    		return;
    	}else {
    		wait(waitSec);
	    	clickAppear(screen, closeIcon, 5);
	    	currentState ="closed";
			currentTime();
    	}
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
	  
	  /**
	   * 
	   * @return  1 is successfully activate, 0 is that manhattan client is closed
	   * @throws Exception
	   */
	  public int  activateManhattan() throws Exception{
		  log.info("\n@ Manhattan : " +  username + " #### Activating Manhattan client ####");
		  String strAutoItCommand = "AutoIt3.exe " + autoitFolder + "manhattan_activateMH.exe";
		  Process result = Runtime.getRuntime().exec(strAutoItCommand);
		  result.waitFor();
		  int res = result.exitValue();
		  log.info("manhattan exit value:" + res);
		  
		  return res;
	      
	  }
	  
	  public void makeACDCallOld(String number) throws Exception{
		  log.info("\n@ Manhattan : " +  username + " #### Before making ACD call ####");
		  int j;
		  Pattern patternoffhookBtn = new Pattern(imgFolder +"mh_offhookBtn.PNG").targetOffset(100, 0);

		  activateManhattan();
		  clickAppear(screen, patternoffhookBtn, 5);
		  screen.type(number);
		  screen.type(Key.ENTER);
		  currentState ="loggedin_busy";

	  }
	  
  public void makeACDCall(String number) throws Exception {
		  
		  log.info("\n@ Manhattan : " +  username + " #### clearing calls if they exist ####");
		  Pattern patternoffhookBtn = new Pattern(imgFolder +"mh_offhookBtn.PNG").targetOffset(100, 0);
		  Pattern activeName= new Pattern(imgFolder +"mh_active.PNG").targetOffset(0, 10);
		  Pattern dropBtn_big = new Pattern(imgFolder +"mh_dropBtn_big.PNG").targetOffset(0, 0);

		  //String eventBtn =imgFolder + "mh_eventBtn";
		  
		  
		  activateManhattan();	
		  //This is to check if there is any call left
		  //screen.click(eventBtn);
		  //screen.click(eventBtn);
		  while (null != screen.exists(activeName)) {
	    		log.info("@ cleaning a remaining call");
	    		clickAppear(screen, activeName, 5);
	    		clickAppear(screen, dropBtn_big, 5);
	    		wait(2);
	    	}

		  log.info("\n@ Manhattan : " +  username + " #### Making ACD call to : "+ number);

		  clickAppear(screen, patternoffhookBtn, 5);
		  screen.type(number);
		  wait(2);
		  screen.type(Key.ENTER);
		  currentState ="loggedin_busy";
		  currentTime();
		 
	  }
	  
	  public void dropCall() throws Exception {
		  
		  log.info("\n@ Manhattan : " +  username + " #### Dropping a call ####");
		  //String callBox = imgFolder +"mh_callBox";
		  String dropBtn = imgFolder +"mh_dropBtn";
	    	
		  activateManhattan();
		  /*if(clickAppear(screen, callBox, 5))
			  clickAppear(screen, dropBtn, 5);*/
		  clickAppear(screen, dropBtn, 5);
    	  currentState ="loggedin_idle";
		  currentTime();
		 
	  }
	  
	  public void dropCall(int waitSec) throws Exception {
		  
		  log.info("\n@ Manhattan : " +  username + " #### Dropping a call ####");
		  //String callBox = imgFolder +"mh_callBox";
		  String dropBtn = imgFolder +"mh_dropBtn";
	    	
		  activateManhattan();
		  wait(waitSec, "# Customer waits before drop the call");
		  /*if(clickAppear(screen, callBox, 5))
		       clickAppear(screen, dropBtn, 5);*/
	      clickAppear(screen, dropBtn, 5);
    	  
    	  currentState ="loggedin_idle";
		  currentTime();
		 
	  }
	  
	
	 /**
	  *  
	  * @param waitSec wait before click answerBtn
	  * @throws Exception
	  */
	  public void answerCall(int waitSec) throws Exception {
		  log.info("\n@ Manhattan : " +  username + " #### Answering a call ####");
		  String answerBtn = imgFolder +"mh_answerBtn";
	    	
		  activateManhattan();
    	  wait(waitSec);
    	  clickAppear(screen, answerBtn, 5);
    	  currentState ="loggedin_busy";
		  currentTime();
		 

	  }
	  
	  public void abandonCall() throws Exception {
		  
		  log.info("\n@ Manhattan : " +  username + " #### Abandoning a call ####");
		  String abandonBtn = imgFolder +"mh_abandonCallBtn";
	    	
		  activateManhattan();
	      clickAppear(screen, abandonBtn, 5);
	      currentState ="loggedin_idle";
		  currentTime();
		 

	  }
	  
	  public void abandonWhileRingCall() throws Exception {
		  
		  log.info("\n@ Manhattan : " +  username + " #### Abandoning a call ####");
		  String abandonBtn = imgFolder +"mh_abandonWhileRingBtn";
	    	
		  activateManhattan();
	      clickAppear(screen, abandonBtn, 5);
	      currentState ="loggedin_idle";
		  currentTime();
		 

	  }
	  
	  public void abandonWhileInQCall() throws Exception {
		  
		  log.info("\n@ Manhattan : " +  username + " #### Abandoning a call ####");
		  String abandonBtn = imgFolder +"mh_abandonWhileInQBtn";
	    	
		  activateManhattan();
	      clickAppear(screen, abandonBtn, 5);
	      currentState ="loggedin_idle";
		  currentTime();
		 

	  }
	  
	  public void dropCall_reset() throws Exception {
		  //if (state.contains("idle")) return;
		  
		  log.info("\n@ Manhattan : " +  username + " #### Dropping a call for a reset ####");
		  
		  //Pattern callBox = new Pattern(imgFolder +"mh_callBox.PNG").targetOffset(0, 10);
		  //Pattern dropBtn = new Pattern(imgFolder +"mh_dropBtn.PNG").targetOffset(20, 0);
		  Pattern activeName= new Pattern(imgFolder +"mh_active.PNG").targetOffset(0, 10);
		  Pattern dropBtn_big = new Pattern(imgFolder +"mh_dropBtn_big.PNG").targetOffset(0, 0);
	    	
		  if(activateManhattan()==0) {
			  log.info("Manhattan is already closed");
			  return;
		  }else {
			  while (null != screen.exists(activeName)) {
		    		log.info("@ cleaning a remaining call");
		    		clickAppear(screen, activeName, 5);
		    		clickAppear(screen, dropBtn_big, 5);
		    		wait(2);
		    	}
		  }
    	  
		  currentTime();

	  }
	  
	  /**
	   * Close Manhattan if it is open
	   * @throws Exception
	   */
	  public void tearDownAll() throws Exception {
		log.info("\n@ Manhattan : " +  username + " #### Logout and Close Manhattan client ####");
		if(activateManhattan() == 0) {
    		log.info("## Manhattan is already closed");
    		return;
    	}else {
			logOut(2);
			closeManhattan(1);
		    currentTime();	  
    	}

	  }
	  
	  /**
	   * This waits until "img" appear and click.
	   * @param s  : This is screen object 
	   * @param img : This is image to click
	   * @throws Exception
	   */
	  public void clickAppear(Screen s, String img) throws Exception {
		log.info("\n@ Manhattan : " +  username + " : clicking => " + img);
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
		log.info("\n@ Manhattan : " +  username + " : clicking => " + img);
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
				activateManhattan();
				if (i > numTry) {
					log.info("\n@ Manhattan : " +  username + " : image doesn't appear after " + numTry + " Tries  => " + img);
					returnValue = false;
					break;
				}
			}
		}catch(Exception e) {
			returnValue = false;
			log.error("Fail to click: " + img + " => " + e.toString());
		}
		
		return returnValue;
	  }
	  
	  /**
	   * This waits until "img" appear and click.
	   * @param s  : This is screen object 
	   * @param img : This is image to click
	   * @param numTry: This is to try how many time to check if an image appears.
	   */
	  public boolean clickAppear(Screen s, Pattern img, int numTry) {
		log.info("\n@ Manhattan : " +  username + " : clicking => " + img);
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
				activateManhattan();
				if (i > numTry) {
					log.info("\n@ Manhattan : " +  username + " : image doesn't appear after " + numTry + " Tries  => " + img);
					returnValue = false;
					break;
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
		    log.info("\n@ Manhattan : " +  username + " : clicking => " + img);
			int i = 0;
			while (true) {
				if (s.exists(img) != null) {
				    s.click(img);
				    break;
				} 
			
				i++;
				wait(2);
				if (i > 5) {
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
