package HBTestesNG.BaseObjects;

import org.openqa.selenium.By;


//import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

public class Boss extends Agent {
	
	public String bossURL, emailDestination;

	public Boss() throws Exception{
		super();
	}
	
	  public void run()
	  {
		  int i, waitSec;
		  i=0;
		  waitSec = 5;
		  log.info("@" + username + ": Running => " +  threadName );
	      try {
	    	
	    	  while (!this.isInterrupted()) 
	    	  {
	    			i++;
				   switch (globalScenario) 
				   {
			         case "BossCCReport_AddReport":
			        	log.info("@" + username + ": BossCCReport_AddReport");
			        	goToBossPortalPage();
			        	wait(2);
			        	logIntoBossPortal();
			        	wait(2);
			        	openContactCenterReportPage();
			        	//Wait 15m and try again
			        	wait(900);
			            break;
			         case "BossCCReport_AddCopyEditDeleteReport":
			        	log.info("@" + username + ": BossCCReport_AddCopyEditDeleteReport");
			     		wait(waitSec);
			        	 break;
			         default:
			             log.info("No matching load scenaro");
				   }
				wait(waitSec);
				log.info("@" + username + ": Running =>  " + i + "th times");
	    	  }

			}catch(InterruptedException e){
				log.info("@ " + username + " : @@@@@@@@@ Thread inturrepted -> Leaving the thread. Bye! @@@@@@@@@@@");
			} catch (Exception e) {
				log.info("@" + username + ": Exception happens");
				e.printStackTrace();
			}
		  
	  }
	
	public boolean goToBossPortalPage() throws Exception {
		log.info("\n@(" + agentType + ") " +  username + " #### Go to Login Page ####");
		int i;
		boolean returnValue;

		returnValue = true;
		i=0;
		
		maximizeBrowser();
		driver.get(bossURL); 
		
		for(;;){
			if (null != driver.findElement(By.id("UserName"))){
				break;	
			}
			
			if(i > 60) {
				log.info("\n@(" + agentType + ") " +  username + " ## Cannot goto login page: "+ bossURL);
				returnValue = false;
				break;
			}
			
			i++;
			wait(1);
			
		}
	
		return returnValue;
		
	}
	public boolean logIntoBossPortal() throws Exception {
		log.info("\n@(" + agentType + ") " +  username + " #### Log into BossPortal ####");
		int i;
		String strXPath;
		boolean returnValue;
		
		i=0;
		returnValue = true;

		
/*		driver.findElement(By.id("UserName")).clear();
		driver.findElement(By.id("UserName")).sendKeys(username);
		driver.findElement(By.id("Password")).clear();
		driver.findElement(By.id("Password")).sendKeys(password);
		wait(2);
		driver.findElement(By.id("m5-logon-form")).click();*/
		
		typeElementXPath(AllActors.bossXPathHash.get("username"), username); 
		typeElementXPath(AllActors.bossXPathHash.get("password"), password);
		clickXPath(AllActors.bossXPathHash.get("btnLogIn"));
		 
		strXPath = AllActors.bossXPathHash.get("linkTeam");
		log.info("linkteam xpath = " + strXPath);
		
		
		///strXPath is the link for "Team", if this is visible, the login is successful
		for(;;){
			if (null != driver.findElement(By.xpath(strXPath))){
				break;	
			}
			
			if(i > 60) {
				log.info("\n@(" + agentType + ") " +  username + " ## Cannot find the link Team: "+ strXPath);
				log.info("#### This means that the first BOSS Portal page is not visible ####");
				returnValue = false;
				break;
			}
			
			i++;
			wait(1);
			
		}
		
		return returnValue;
	}
	
	public boolean openContactCenterReportPage() throws Exception {
		log.info("\n@(" + agentType + ") " +  username + " #### Open BOSS Contact Center Report ####");
		
		int i;
		String rootMenu, subMenu, strXPath;
		boolean returnValue;
		
		i=0;
		returnValue = true;

		rootMenu = AllActors.bossXPathHash.get("linkTeam");
		subMenu = AllActors.bossXPathHash.get("linkCCReport");
		selectFromSubMenu(rootMenu, subMenu);
		
		strXPath = AllActors.bossXPathHash.get("btnAddNewReport");
		for(;;){
			if (null != driver.findElement(By.xpath(strXPath))){
				break;	
			}
			
			if(i > 60) {
				log.info("\n@(" + agentType + ") " +  username + " ## Cannot find Add button "+ strXPath);
				log.info("#### This means that the list of reports are not available ####");
				returnValue = false;
				break;
			}
			
			i++;
			wait(1);
			
		}
		
		return returnValue;
	}
	
		
	
	  public void tearDownAll() throws Exception {
		log.info("\n@(" + agentType + ") " +  username + " #### Close FireFox ####");
	    driver.quit();
	    currentTime();	    
	  }
	
}
