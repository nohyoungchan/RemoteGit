package BaseObject;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;


public class CustomerCMWin extends WebBrowser {
   
	public CustomerCMWin() throws Exception{
		super();
		
	}
	
	
/*	public CustomerCMWin(String username, String password, String extension) throws Exception {
		super(username, password, extension);
		log.info("\n@(" + agentType + ") " +  username + " #### Initializing ####");
	}*/
	
	
	public void logIntoCMWin() throws Exception {
		log.info("\n@(" + agentType + ") " +  username + " #### Opening firefox ####");
	    //driver = new FirefoxDriver();
	    //baseUrl = "http://www.google.com/ ";
	    //driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
	    
		log.info("\n@(" + agentType + ") " +  username + " #### Go to Login Page ####");
		//driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.get(AllMembers.gVariableHash.get("agentCMWinURL")); 
		maximizeFireFox();
		
		log.info("\n@(" + agentType + ") " +  username + " #### Adding username -> password -> Submit ####");
		driver.findElement(By.id("login")).clear();
		driver.findElement(By.id("login")).sendKeys(username);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(password);
		wait(2);
		driver.findElement(By.id("SUBMIT1")).click();
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
	  
	  public void makeACDCall(String number) throws Exception{
		  log.info("\n@(" + agentType + ") " +  username + " #### Before making ACD call ####");
		  int j;
		  String onHook, txtReturned, contactBox;
		  
		  onHook = ".//*[starts-with(@class, 'phonestatus')]";	  
		  contactBox = ".//*[@id='contacts']/form/div/table/tbody/tr/td[1]/div/input";
		  
		  for (int i = 0; i < 1000; i++) {
		     j = i+1;
		    	
		     txtReturned= driver.findElement(By.xpath(onHook)).getAttribute("title");
		     //log.info("\n@(" + agentType + ") " +  username + ": On Hook?=> " + txtReturned);
		     if (txtReturned.contains("On Hook")) //If CMWin is not in a call.
		     {
		    	wait(2);
			    driver.findElement(By.xpath(contactBox)).sendKeys(number);
			    driver.findElement(By.xpath(contactBox)).sendKeys(Keys.RETURN);
			    state = "busy";
			    log.info("@" + username + ": Made ACD call to => " + number);
			    wait(2);  //this is needed because it calls 2 times.
			    break;
		     }else{
		  	    log.info("\n@(" + agentType + ") " +  username + ": CMWin's state: " + txtReturned);
		  	    wait(10,"Not ready to call");
		     }
		     
		    if (j > 10) {
	    		log.error("\n#Reset: Something Wrong with CMWin \n\n");
	    		throw noSuchElementException;
		    }
		  }

	  }
	  
	  public void dropCall() throws Exception {
		  
		  log.info("\n@(" + agentType + ") " +  username + " #### Dropping a call ####");
		  getCurrentDimenSion();
		  //This is needed for chat transfer since without it, selenium cannot confirm transfer.
		  maximizeFireFox();	
		  wait(2);
		  clickImageByName("drop");
		  state = "idle"; //This is needed when exception happens and reset=>idle :No need to reset
		  restoreDimenSion();
		  //setSizeAndLocation(1300, 200, 20, 500);
	  }
	  
	  public void answerCall() throws Exception {
		  
		  log.info("\n@(" + agentType + ") " +  username + " #### Answering a call ####");
		  getCurrentDimenSion();
		  //This is needed for chat transfer since without it, selenium cannot confirm transfer.
		  maximizeFireFox();	
		  wait(2);
		  clickImageByName("answer");
		  state = "busy"; //This is needed when exception happens and reset=>idle :No need to reset
		  restoreDimenSion();
		  //setSizeAndLocation(1300, 200, 20, 500);

	  }
	  
	  public void dropCall_reset() throws Exception {
		  if (state.contains("idle")) return;
		  
		  log.info("\n@(" + agentType + ") " +  username + " #### Dropping a call for a reset ####");
		  getCurrentDimenSion();
		  //This is needed for chat transfer since without it, selenium cannot confirm transfer.
		  maximizeFireFox();	
		  wait(2);
		  clickImageByName("drop");
		  state = "idle";
		  restoreDimenSion();
		  //setSizeAndLocation(1300, 200, 20, 500);

	  }
	  
	  
	  public void tearDownAll() throws Exception {
		log.info("\n@(" + agentType + ") " +  username + " #### Close FireFox ####");
	    driver.quit();
	    currentTime();	    
	  }

}
