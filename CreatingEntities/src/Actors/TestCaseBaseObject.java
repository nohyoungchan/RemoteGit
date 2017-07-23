package Actors;


import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.Reporter;

import TestCases.*;

public class TestCaseBaseObject extends TestBaseObject{
	public static AllEntities aa;

	
	
	public static void InitializeAllVariables() throws Exception {
		log.info("* Initializing all variables");

	}
	
	public static void InitializeAllVariables_Load() throws Exception {
		log.info("* Initializing all variables");
	
	}
	
	public static void startTestSuitMessage(){
		 
	    log.info("\n\n###################################################");
	    log.info("###################################################");
	    log.info("###########  Starting a Test Suite ################");
	    log.info("###################################################");
	    log.info("###################################################");
	 
	    }
	
	public static void endtTestSuitMessage() throws IOException{
		 

	    log.info("###################################################");
	    log.info("###################################################");
	    log.info("###### End of TestSuite ###########################");
	    log.info("###################################################");
	    log.info("###################################################");
	    
		switch (TestBaseObject.useWhichWebDriver) 
		   {
	         case "chrome":
	         	//log.info("@ WebDriver=> chrome"); 
	         	executeShellCommand("taskkill /IM  chromedriver.exe /F");
	        	break;
	         case "firefox":
	        	 //log.info("@ WebDriver=> firefox : no need to kill any remaining"); 
		         //executeShellCommand("taskkill /IM  chromedriver.exe /F");
	            break;
	         case "ie":
	        	 //log.info("@ WebDriver=> ie");  
		         	executeShellCommand("taskkill /IM  IEDriverServer.exe /F");
	             break;
	         case "phantomjs":
	        	 //log.info("@ WebDriver=> phantomjs"); 
		         	executeShellCommand("taskkill /IM  phantomjs.exe /F");
	         	 break;
	         case "remote":
	        	 //log.info("@ WebDriver=> remote");  
		         	executeShellCommand("taskkill /IM  RemoteWebDriver.exe /F");
	         	 break;
	         case "safari":
	        	 //log.info("@ WebDriver=> safari"); 
		         	executeShellCommand("taskkill /IM  SafariDriver.exe /F");
	         	 break;
	         default:
	             //log.info("No matching WebDriver");
		   }
	 
	    }
	
	public static void startTestCase(String sTestCaseName){
		 
	    log.info("\n\n\n****************************************************************************************");
	    log.info("****************************************************************************************");
	    log.info("$$$$$$$$$$$$$$$$$$$$$  "+sTestCaseName+ " $$$$$$$$$$$$$$$$$$$$$$$$$");
	    log.info("****************************************************************************************");
	    log.info("****************************************************************************************");
	    
	}

	 
	    //This is to print log for the ending of the test case
	 
	public void endTestCase(String testName) throws Exception{
	 
	    log.info("XXXXXXXXXXXXXXXXXXXXXXX  "+"-END-"+ testName + " XXXXXXXXXXXXXXXXXXXXXX");
	    log.info("X");
	    log.info("X");
	    log.info("X");
	    log.info("X");
	    
	    waitBetweenTestCase(2);
   }
	
	public void resetAllActors(String testName) throws Exception{
		log.info("#### Resetting because something wrong with =>" + testName + " #######");
		
		Reporter.log("** Testcase Failed: " + testName);  //this is to show on testng html report
		Assert.fail("\n** Testcase Failed: " + testName +"\n\n");
		
	}
	
	public boolean thisCaseIsNotSupported(){
		boolean notSupportThisScenario = true;
		log.info("*** This test case is only supported on PCM/Physical phone");
		return notSupportThisScenario;
	}
	
     public void threadPoolShutdown(ExecutorService executor) throws InterruptedException {
	    executor.shutdownNow();
	  }


}
