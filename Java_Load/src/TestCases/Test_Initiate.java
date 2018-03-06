package TestCases;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;
import ExceptionCustom.EC_LogintoAIC;
import BaseObjects.*;

@Test(groups= {"Test_Initiate"})
public class  Test_Initiate extends TestCaseObject{
	public static AllActors allActors;

	
	Test_Initiate() throws Exception{
		log.info("* Constructor: Test_initiate");
	}
	
	@BeforeSuite
	public void beforeSuite() throws Exception {
		
		try  {
			//wait_for_inputToStart("Press Enter to start a test");

			allActors = new AllActors(); //This reads all ini and starts web.
			startTestSuitMessage();  
			waitUntilTomorrowOneAm(AllActors.iniMain.get("TestFlow", "waitUntilOneAm"));
			currentTimeStart();
			
			
			//Log in Manhattan client: only 1 if needed.
			String startLocation;
			if (allActors.customers.size() == 1){
/*				startLocation = AllActors.iniMain.get("Manhattan", "startingLocation");
				String splitResult[] = startLocation.split("/");
				allActors.customers.get(0).startManhattan(splitResult[0], splitResult[1]);*/
				allActors.customers.get(0).logIn();
				
			}

			

			
			//CCD configuration before starting suite: You can skip this by "skipCCDPrepare" variable
			if (allActors.supervisors.size() >0 &&  AllActors.iniMain.get("CCD", "skipCCDPrepare").contains("no")) 
			{ 
				allActors.supervisors.get(0).Max_LogIn_PrepareTest_LogOut_Min();
			}
	
				
			for (int i=0; i < allActors.agents.size() ; i++ ){
				if (0 ==allActors.agents.size()) { break;}
				allActors.agents.get(i).logIntoWebAgent();
			}
			
			
			
		}catch (EC_LogintoAIC e) {
			log.error("Exception on before suite(logIntoWebAgent: Failing all : " + e.toString());
			stopTest = "yes";
			//afterSuite();
			
		}catch (Exception e) {
			log.error("Exception on before suite: Failing all : " + e.toString());
			stopTest = "yes";
			//afterSuite();
			
		}
	
			


	}


	@AfterSuite
	public void afterSuite() throws Exception {
		
		log.info("\n\n");
		log.info("#######################################################");
		log.info("########### Starting End of TestSuite  ###################");
		log.info("#########################################################\n\n");
		
		

		

		if (AllActors.iniMain.get("TestFlow", "tearDownOrNot").contains("no")) {
			log.info("\n\n%%% Waiting for a user input: Enter any key to close all actors %%%");
			System.in.read();
			//return;
		}

		try {
			for (int i=0; i < allActors.agents.size() ; i++ ){
				if (0 ==allActors.agents.size()) { break;}
				
				//if WebBrowser is not closed, close
				if (!allActors.agents.get(i).state.contains("closed"))
					allActors.agents.get(i).tearDownAll();
				wait(1);
			}
		}catch(Exception e){
			log.info("Somthing wrong with AIC teardown : " + e.toString());
		}
		
		try {
			for (int i=0; i < allActors.supervisors.size() ; i++ ){
				if (0 ==allActors.supervisors.size()) { break;}
				
				//if WebBrowser is not closed, close
				if (!allActors.supervisors.get(i).state.contains("closed"))
					allActors.supervisors.get(i).tearDownAll();
				wait(1);
			}
		}catch(Exception e){
			log.info("Somthing wrong with CCD teardown : " + e.toString());
		}
		
		try {
			for (int i=0; i < allActors.chatCustomers.size() ; i++ ){
				if (0 ==allActors.chatCustomers.size()) { break;}
				//if WebBrowser is not closed, close
				if (!allActors.chatCustomers.get(i).state.contains("closed"))
					allActors.chatCustomers.get(i).tearDownAll();
				wait(1);
			}
		}catch(Exception e){
			log.info("Somthing wrong with Chat customer teardown : " + e.toString());
		}
		
		try {
			for (int i=0; i < allActors.emailCustomers.size() ; i++ ){
				if (0 ==allActors.emailCustomers.size()) { break;}
				
				//if WebBrowser is not closed, close
				if (!allActors.emailCustomers.get(i).state.contains("closed"))
					allActors.emailCustomers.get(i).tearDownAll();
				wait(1);
			}
		}catch(Exception e){
			log.info("Somthing wrong with email customer teardown : " + e.toString());
		}
		
		try {
			for (int i=0; i < allActors.customers.size() ; i++ ){
				if (0 ==allActors.customers.size()) { break;}
				
				allActors.customers.get(i).tearDownAll();
				//executeShellCommand("taskkill /IM  ShoreTel.exe /F");  //This is to kill ShoreTel.exe when graceful close failed.
	
			}
		}catch(Exception e){
			log.info("Somthing wrong with voice customer teardown : " + e.toString());
		}
		
		//This clears all message on Supervisor_Clent ->textMessage box
		AllActors.superAdmin.sendMessage("clearMessage");
		AllActors.superAdmin.printAllMessages();
		TestStatus.printFailedCases();
		TestStatus.printSkippedCases();
		
		currentTimeEnd();
		totalExecutionTime();
		endtTestSuitMessage();
	}
	
	public static AllActors getAllObjects(){
	    return allActors;
	}

}
