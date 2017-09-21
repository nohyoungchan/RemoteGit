package HBTestesNG.BaseObjects;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.NoSuchElementException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.util.log.Log;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.SkipException;
import Utility.PostCondition;


import HBTestesNG.TestCases.Test_Initiate;

public class TestCaseObject extends TestObject{
	public static AllActors aa;
	public static AgentHB agent1, agent2, agent3, agent4, agent5, agent6, agent7;
	public static CustomerManhattan customer1;
	public static CustomerChat chatCustomer1;
	public static CustomerEmail emailCustomer1;
	public static AgentHBDirector sup1;
	public static IRN irn1, irn2, irn3, irn4, irn5;
	public static PostCondition pc;
	private static String customerReturn;
	private static TimerTask task;
	
	

	
	
	public static void InitializeAllVariables() throws Exception {
		
		
		if (stopTest.contains("yes")) {
			log.info("@@ Stop is requested, so skip => InitializeAllVariables()" );
			throw new SkipException("Skipping InitializeAllVariables()");
		}
		log.info("* Initializing all variables");
		aa = Test_Initiate.allActors;
		if (aa.agents.size() > 0)
		{
			agent1 = aa.agents.get(0);
			agent2 = aa.agents.get(1);
			agent3 = aa.agents.get(2);
		}
		
		if (aa.customers.size() > 0)
		{
			customer1 = aa.customers.get(0);
		}
		
		if (aa.chatCustomers.size() > 0)
		{
			chatCustomer1=aa.chatCustomers.get(0);
		}
		
		if (aa.emailCustomers.size() > 0)
		{
			emailCustomer1=aa.emailCustomers.get(0);
		}
		
		if (aa.supervisors.size() > 0)
		{
			sup1 = aa.supervisors.get(0);
		}
		
		if (aa.irns.size() > 0)
		{
			irn1 = aa.irns.get(0);
			irn2 = aa.irns.get(1);
			irn3 = aa.irns.get(2);
			irn4 = aa.irns.get(3);
			irn5 = aa.irns.get(4);
		}
		pc = aa.postCondition;
		
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
	    
		switch (TestObject.useWhichWebDriver) 
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
	
	public static void classStart(String sClassName){
		 

	    log.info("\n\n****************************************************************************************");
	    log.info("****************************************************************************************");
	    log.info("$$$$$$$$$$ Class Start:   "+sClassName+ " $$$$$$$$$$$$$$$$$$$$$$$$$");
	    log.info("****************************************************************************************");
	    log.info("****************************************************************************************");
	    
	}
	
	public static void classEnd(String sClassName){
		 

	    log.info("\n\n****************************************************************************************");
	    log.info("****************************************************************************************");
	    log.info("$$$$$$$$$$ Class End:   "+sClassName+ " $$$$$$$$$$$$$$$$$$$$$$$$$");
	    log.info("****************************************************************************************");
	    log.info("****************************************************************************************");
	    
	}
	
	
	public static String startTestCase(String sTestCaseName){
		 
		errorCount = 0;
		errorString = "";
		AllActors.superAdmin.sendMessage("Starting scenario @ " + sTestCaseName);
		if (stopTest.contains("yes")) {
			log.info("@@ Stop is requested, so skip => " + sTestCaseName);
			AllActors.superAdmin.sendMessage("Skipping scenario @ " + sTestCaseName);
			return "no";
		}
	    log.info("\n\n\n****************************************************************************************");
	    log.info("****************************************************************************************");
	    log.info("$$$$$$$$$$ Test Start:   "+sTestCaseName+ " $$$$$$$$$$$$$$$$$$$$$$$$$");
	    log.info("****************************************************************************************");
	    log.info("****************************************************************************************");
	    
	    if(sTestCaseName.contains("Voice")){
	    	AllActors.currentCallType = "Voice";
	    } else if(sTestCaseName.contains("Chat")) {
	    	AllActors.currentCallType = "Chat"; 	
	    } else if(sTestCaseName.contains("Email")) {
	    	AllActors.currentCallType = "Email";  	
	    } else {
	    	AllActors.currentCallType = "NotAvailable"; 
	    }
	    
	    return "yes";
	}
	
	

	 
	    //This is to print log for the ending of the test case
	 
	public void endTestCase(String testName) throws Exception{
	 
		String userInputString;
		
		
		if (errorCount > 0) {
			 AllActors.superAdmin.sendMessage("Ending scenario @ " + testName + " @ Failed because => " + errorString);
			 log.info("XXXXXXXXXXXXXXXXXXXXXXX  "+"Test Result(Failed) => "+ testName + " XXXXXXXXXXXXXXXXXXXXXX");
			 log.info("X");
			 log.info("X");
			 
			 
		     //If there is a user input, all test cases will be stopped.
		     userInputString=  WaitingForUserInput(20);
		     //This will stop all remaining test cases
		     if (userInputString.contains("yes")) stopTest = "yes"; 
		     failTest(testName + " has failed because => " + errorString);
		}else {
			AllActors.superAdmin.sendMessage("Ending scenario @ " + testName + " @ Successful");
		    log.info("XXXXXXXXXXXXXXXXXXXXXXX  "+"Test Result(Successful) => "+ testName + " XXXXXXXXXXXXXXXXXXXXXX");
		    log.info("X");
		    log.info("X");
		    
		    //If there is a user input, all test cases will be stopped.
		    userInputString=  WaitingForUserInput(20);
		    //This will stop all remaining test cases
		    if (userInputString.contains("yes")) stopTest = "yes"; 
			
		}
		
	
   }
	
	public void endTestCase_Main(String testName) throws Exception{
		 
		String userInputString;
	    log.info("XXXXXXXXXXXXXXXXXXXXXXX  "+"-END-"+ testName + " XXXXXXXXXXXXXXXXXXXXXX");
	    log.info("X");
	    log.info("X");
	    log.info("X");
	    log.info("X");
	    
   }
	
	public static String WaitingForUserInput_usingTask(int waitSec) throws IOException {

        String strReturn ="no";
        customerReturn = "";
		task = new TimerTask()
	    {
	        public void run()
	        {
	            if( customerReturn.equals(" ") )
	            {
	            	log.info("==> Your input is not valid.  Continue");
	            }
	            
	            if( customerReturn.equals("") )
	            {
	            	log.info("==> You did not enter data, so continue test");
	                System.exit( 0 );
	            }
	        }    
	    };
		

        Timer timer = new Timer();
        timer.schedule( task, waitSec*1000 );

        log.info("@@@ Type y to stop whole test suite within " + waitSec + " (sec) : ");
        BufferedReader in = new BufferedReader(new InputStreamReader( System.in ) );
        customerReturn = in.readLine();
        
        timer.cancel();
        
        if(customerReturn.contains("y")) {
		     log.info("==> You entered: " + customerReturn + ", so stopping whole test suite.  Bye :)");
		     strReturn = "yes";
		}else { 
		     log.info("==> You entered: " + customerReturn + " ,so continue test");
		}

        timer.cancel();
        customerReturn ="";
        
        return strReturn;

	}
	
	 public static String WaitingForUserInput_old(int waitSec) throws IOException, InterruptedException{
		 String strReturn;
		 String res;

		 log.info("@@@ Type y to stop whole test suite within " + waitSec + " (sec) : ");
		 long startTime = System.currentTimeMillis();
		 BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		 sleep(1000);
		 while (((System.currentTimeMillis() - startTime) < waitSec * 1000)  && !in.ready()) {
		 }
       
		 if (in.ready()) {//When something is entered
			 res = in.readLine();
			 log.info("Result is ==> " + res);
			 if(res.contains("y")) {
			     log.info("==> You entered: " + res + ", so stopping whole test suite.  Bye :)");
			     strReturn = "yes";
			}else { 
			     log.info("==> You entered: " + res + " ,so continue test");
				 strReturn = "no";
			}
		 }else {//nothing is entered
		     log.info("==> You did not enter data, so continue test");
		     strReturn = "no";
		 }
		 
		 return strReturn;
	 }
	 
	 /**
	  * This waits for the user input -> y : stop all test, no input: wait 20s and next test, other than y: next test
	  * @param waitSec
	  * @return   if this is yes, stop all test.
	  * @throws IOException
	  * @throws InterruptedException
	  */
	 public static String WaitingForUserInput(int waitSec) throws IOException, InterruptedException{
		 String strStopAllTest = "no";
		 String res;
		 int breakWhile = 0;

		 log.info("@@@ Type y to stop whole test suite within " + waitSec + " (sec) : ");
		 long startTime = System.currentTimeMillis();
		 BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		 while (((System.currentTimeMillis() - startTime) < waitSec * 1000) && breakWhile == 0) {
			 
			 if (in.ready()) {//When something is entered
				 res = in.readLine();
				 log.info("Result is ==> " + res);
				 switch (res) {
				 case "y":
					 log.info("==> You entered: " + res + ", so stopping whole test suite.  Bye :)");
					 strStopAllTest = "yes";
				     breakWhile = 1;
				     break;
				 case "":
					 log.info("==> You entered: " + res + ", this is not a valid input, so wait)");
					 strStopAllTest = "no";
				     breakWhile = 0;
				     break;
				 default:
					 log.info("==> You entered: " + res + ", so start next test.  Continue Test :)");
					 strStopAllTest = "no";
				     breakWhile = 1;
				     break;
				 
				 }
				
			 }
			 
			 if((System.currentTimeMillis() - startTime) > waitSec * 1000){
			     log.info("==> You did not enter any inpu, so start next test");
				 
			 }
			 
			 
		 }
		 
		 return strStopAllTest;
	 }
	 
	 /**
	  * 
	  * @param waitSec
	  * @return : if input is s(Stop Test), m(Stop Main)
	  * @throws IOException
	  */
	 public static String WaitingForUserInput_Main(int waitSec) throws IOException{
		 int x = waitSec; // wait 2 seconds at most
		 String strReturn;

		 log.info("\n@@@@@ Type any to stop test including main within " + waitSec + " (sec) : ");
		 BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		 long startTime = System.currentTimeMillis();
		 while ((System.currentTimeMillis() - startTime) < x * 1000
		         && !in.ready()) {
		 }

		 if (in.ready()) {
			 strReturn = in.readLine();
			 stopMain = "yes";
		     log.info("==> You entered: " + strReturn + ", so leaving this command and wait for another :)");
		     
		 } else {
			 strReturn = "no";
		     log.info("==> You did not enter data, so repeating this command");
		 }
		 
		 return strReturn;
	 }
	
	public void resetAllActors(String testName) throws Exception{
		log.info("#### Resetting because something wrong with =>" + testName + " #######");

		try{
			//this doesn't need for emailCustomer
			customer1.dropCall_reset();
			chatCustomer1.disconnectChat_reset();
			agent1.disconnectByWebAgent_Reset();
			agent2.disconnectByWebAgent_Reset();
			agent3.disconnectByWebAgent_Reset();
		
		}catch(NoSuchElementException e){
			log.error("*NoSuchElementException happened: " + e.toString());
		}
		
		Reporter.log("** Testcase Failed: " + testName);  //this is to show on testng html report
		Assert.fail("\n** Testcase Failed: " + testName +"\n\n");
		
	}
	
	public boolean thisCaseIsNotSupported(){
		boolean notSupportThisScenario = true;
		log.info("*** This test case is only supported on PCM/Physical phone");
		return notSupportThisScenario;
	}
	
     public void threadPoolShutdown(ExecutorService executor) throws InterruptedException {
	    //executor.shutdown();
	   //executor.awaitTermination(30, TimeUnit.SECONDS);
	    executor.shutdownNow();
	  }
     
	    public void skipTest(String reason) throws Exception{
	    	throw new SkipException(reason);
	    }
	    
	    public void failTest(String reason) throws Exception{
	    	Assert.assertTrue(false, reason);
	    }


}
