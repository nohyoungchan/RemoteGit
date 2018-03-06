package TestCases;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.testng.annotations.AfterClass;
import BaseObjects.*;

@Test(groups= {"Test_Load_ClassLevel"})
public class Test_Load extends TestCaseObject {
	
	
	Test_Load() throws Exception{
		log.info("* Construction: Test_Load");
	}
	
	
	@BeforeClass
	public void beforeClass() throws Exception {
		classStart("Test_Load");
		//log.info("* Before Class: Test_Load");
		aa = Test_Initiate.allActors;
    }
	
	@AfterClass
	public void afterClass() throws Exception {
		//log.info("* After Class: Test_Load");
		classEnd("Test_Load");
	}
	

	public void Load_Mix() throws Exception {
		String testName = "Load_Mix";
		globalScenario = "Load_Mix";
		if(startTestCase(testName).contains("no")) skipTest(testName, "Skipping because user want to end test => " + testName);
		int i, agentSize;
		agentSize = aa.agents.size();

		try{
			
			ExecutorService threadPool = Executors.newFixedThreadPool(agentSize);
			
			for (i = 0 ; i < agentSize; i++){
				threadPool.submit(aa.agents.get(i));
				wait(2);
			}
		    
			// wait for keyboard input before exiting
			wait_for_input();
			threadPoolShutdown(threadPool);
			
		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
		}finally{
			endTestCase(testName);
		}
		
	}
	

	public void Load_LogInOnly_ByThread() throws Exception {
		String testName = "Load_LogInOnly_ByThread";
		globalScenario = "Load_LogInOnly_ByThread";
		if(startTestCase(testName).contains("no")) skipTest(testName, "Skipping because user want to end test => " + testName);
		int i, agentSize;
		agentSize = aa.agents.size();

		try{
			
			ExecutorService threadPool = Executors.newFixedThreadPool(agentSize);
			
			for (i = 0 ; i < agentSize; i++){
				threadPool.submit(aa.agents.get(i));
				//aa.agents.get(i).start();
				wait(2);
			}
		    
			// wait for keyboard input before exiting
			wait_for_input();
			threadPoolShutdown(threadPool);

		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			//resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
		
	}
	
	public void Load_LogAndReLogInAfterMin_ByThread() throws Exception {
		String testName = "Load_LogAndReLogInAfterMin_ByThread";
		globalScenario = "Load_LogAndReLogInAfterMin_ByThread";
		if(startTestCase(testName).contains("no")) skipTest(testName, "Skipping because user want to end test => " + testName);
		int i, agentSize;
		agentSize = aa.agents.size();

		try{
			
			ExecutorService threadPool = Executors.newFixedThreadPool(agentSize);
			
			for (i = 0 ; i < agentSize; i++){
				threadPool.submit(aa.agents.get(i));
				wait(2);
			}
		    
			// wait for keyboard input before exiting
			wait_for_input();
			threadPoolShutdown(threadPool);

		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
		}finally{
			endTestCase(testName);
		}
		
	}
	
	public void Load_LogAndSignoutCloseAfterMin_ByThread() throws Exception {
		String testName = "Load_LogAndSignoutCloseAfterMin_ByThread";
		globalScenario = "Load_LogAndSignoutCloseAfterMin_ByThread";
		if(startTestCase(testName).contains("no")) skipTest(testName, "Skipping because user want to end test => " + testName);
		int i, agentSize;
		agentSize = aa.agents.size();

		try{
			
			ExecutorService threadPool = Executors.newFixedThreadPool(agentSize);
			
			for (i = 0 ; i < agentSize; i++){
				threadPool.submit(aa.agents.get(i));
				//aa.agents.get(i).start();
				wait(2);
			}
		    
			
			//wait 1 more min than global min to wait for all agents out.
			//s: stop test, m: stop main
			WaitingForUserInput_Main(globalMinToRelogIn*60 + globalSec);
			
			//This waits until the last agent closes browser.
			/*if(!stopMain.equals("yes")){
				while(true){
					if (aa.agents.get(i-1).state.contains("closed")) break;
					wait(5);
				}
			}*/

		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			//resetAllActors(testName);
		}finally{
			endTestCase_Main(testName);
		}
		
	}
	
    /**
     * This is not using Thread.   This just change state serially
     * @throws Exception
     */
	public void Load_ChangeState() throws Exception {
		String testName = "Load_ChangeState_Consistently";
		globalScenario = "Load_ChangeState_Consistently";
		log.info("testing change state yeah!!");
		if(startTestCase(testName).contains("no")) skipTest(testName, "Skipping because user want to end test => " + testName);
		int i, j, agentSize;
		agentSize = aa.agents.size();
		int waitSec; 
		waitSec =200/agentSize;  //I tried to make each agent change state every 4 min.

		try{
			for(i = 0 ; i < 50 ; i++){
				log.info("I am here 1");
				
				for (j = 0 ; j < agentSize; j++){
					aa.agents.get(j).releaseAgentSecondCode();
					wait(waitSec);
				}
				
				for (j = 0 ; j < agentSize; j++){
					aa.agents.get(j).resumeAgent();
					wait(waitSec);
				}
				
				for (j = 0 ; j < agentSize; j++){
					aa.agents.get(j).releaseAgentThirdCode();
					wait(waitSec);
				}
				
				for (j = 0 ; j < agentSize; j++){
					aa.agents.get(j).resumeAgent();
					wait(waitSec);
				}
				
				if (i > 30){
					for (j = 0 ; j < agentSize; j++){
						aa.agents.get(j).signoutWebAgent();
						aa.agents.get(j).tearDownAll();
						wait(1);
					}
					break;
				}
			}

		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			//resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
		
	}
	

	public void Load_ChangeState_ByThread() throws Exception {
		String testName = "Load_ChangeState_ByThread";
		globalScenario = "Load_ChangeState_ByThread";
		if(startTestCase(testName).contains("no")) skipTest(testName, "Skipping because user want to end test => " + testName);

		try{
			
			ExecutorService threadPoolAgent = Executors.newFixedThreadPool(aa.agents.size());
			
			for (int i = 0 ; i < aa.agents.size(); i++){
				threadPoolAgent.submit(aa.agents.get(i));
				wait(2);
			}
		    
			// wait for keyboard input before exiting
			wait_for_input();
			threadPoolShutdown(threadPoolAgent);
		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			//resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
		
	}
	
	
	//Log in agents in bulk and "answer emails->Disconnect->Wrap->Repeat"
		public void Load_VoiceACD_Answer_ByThread() throws Exception {
			String testName = "Load_VoiceACD_Answer_ByThread";
			globalScenario = "Load_VoiceACD_Answer_ByThread";
			if(startTestCase(testName).contains("no")) skipTest(testName, "Skipping because user want to end test => " + testName);
			int i, agentSize;
			agentSize = aa.agents.size();

			try{
				
				ExecutorService threadPool = Executors.newFixedThreadPool(agentSize);
				
				for (i = 0 ; i < agentSize; i++){
					threadPool.submit(aa.agents.get(i));
					//aa.agents.get(i).start();
					wait(2);
				}
			    
				// wait for keyboard input before exiting
				wait_for_input();
				threadPoolShutdown(threadPool);

			}catch(Exception e){
				log.info("I am handling General exception=>"+ e.toString());
				//resetAllActors(testName);
			}finally{
				endTestCase(testName);
			}
			
		}
	
	//Log in agents in bulk and "answer emails->Disconnect->Wrap->Repeat"
	public void Load_Email_Answer_ByThread() throws Exception {
		String testName = "Load_Email_Answer_ByThread";
		globalScenario = "Load_Email_Answer_ByThread";
		if(startTestCase(testName).contains("no")) skipTest(testName, "Skipping because user want to end test => " + testName);
		int i, agentSize;
		agentSize = aa.agents.size();

		try{
			
			ExecutorService threadPool = Executors.newFixedThreadPool(agentSize);
			
			for (i = 0 ; i < agentSize; i++){
				threadPool.submit(aa.agents.get(i));
				//aa.agents.get(i).start();
				wait(2);
			}
		    
			// wait for keyboard input before exiting
			wait_for_input();
			threadPoolShutdown(threadPool);

		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			//resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
		
	}
	
	//Log in agents in bulk and "answer emails->Disconnect->Wrap->Repeat"
	public void Load_Email_SendAndAnswer_ByThread() throws Exception {
		String testName = "Load_Email_SendAndAnswer_ByThread";
		globalScenario = "Load_Email_SendAndAnswer_ByThread";
		if(startTestCase(testName).contains("no")) skipTest(testName, "Skipping because user want to end test => " + testName);
		int i;

		try{
			
			ExecutorService threadPoolAgent = Executors.newFixedThreadPool(aa.agents.size());
			ExecutorService threadPoolEmailCustomer = Executors.newFixedThreadPool(aa.emailCustomers.size());
			
			for (i = 0 ; i < aa.agents.size(); i++){
				threadPoolAgent.submit(aa.agents.get(i));
				wait(2);
			}
			
			for (i = 0 ; i < aa.emailCustomers.size(); i++){
				threadPoolEmailCustomer.submit(aa.emailCustomers.get(i));
				wait(2);
			}
		    
			// wait for keyboard input before exiting
			wait_for_input();
			
			threadPoolShutdown(threadPoolAgent);
			threadPoolShutdown(threadPoolEmailCustomer);

		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			//resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
		
	}
	
	

	public void Load_Email_Send_ByThread() throws Exception {
		String testName = "Load_Email_Send_ByThread";
		globalScenario = "Load_Email_Send_ByThread";
		if(startTestCase(testName).contains("no")) skipTest(testName, "Skipping because user want to end test => " + testName);

		try{
			
			ExecutorService threadPool = Executors.newFixedThreadPool(aa.emailCustomers.size());
			
			for (int i = 0 ; i < aa.emailCustomers.size(); i++){
				threadPool.submit(aa.emailCustomers.get(i));
				wait(2);
			}
		    
			// wait for keyboard input before exiting
			wait_for_input();
			threadPool.shutdownNow();
			threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			//resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
		
	}
	
	//This is used to login OWS(Web-outlook) clients for the first time.
	//##1. only 1 OWA is needed- 1 email customer
	//##2. Inside logIntoEmailClientTemp(), you can decide number of users.
	//##3. This is used only for qaxchange10->OWA
	public void Load_Email_LogInFortheFirstTime() throws Exception {
		String testName = "Load_Email_SendEmail_Continuously_ByThread";
		globalScenario = "Load_Email_SendEmail_Continuously_ByThread";
		if(startTestCase(testName).contains("no")) skipTest(testName, "Skipping because user want to end test => " + testName);

		try{
			
			aa.emailCustomers.get(0).logIntoEmailClient_FirstTime();
			wait(1);

		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
		}finally{
			endTestCase(testName);
		}
		
	}
	
	//This is used to login OWS(Web-outlook) clients for the first time.
	//##1. only 1 OWA is needed- 1 email customer
	//##2. Inside logIntoEmailClientTemp(), you can decide number of users.
	//##3. This is used only for qaxchange10->OWA
	public void Load_Email_logIntoEmailClient_DeleteAllEmails() throws Exception{
		String testName = "Load_Email_logIntoEmailClient_DeleteAllEmails";
		globalScenario = "Load_Email_logIntoEmailClient_DeleteAllEmails";
		if(startTestCase(testName).contains("no")) skipTest(testName, "Skipping because user want to end test => " + testName);

		try{
			aa.emailCustomers.get(0).logIntoEmailClient_DeleteAllEmails();
			wait(2);

		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
		}finally{
			endTestCase(testName);
		}
		
	}
	
	
	
	//Log in agents in bulk and "answer Chats->Disconnect->Wrap->Repeat"
		public void Load_Chat_SendAndAnswer_ByThread() throws Exception {
			String testName = "Load_Chat_SendAndAnswer_ByThread";
			globalScenario = "Load_Chat_SendAndAnswer_ByThread";
			if(startTestCase(testName).contains("no")) skipTest(testName, "Skipping because user want to end test => " + testName);
			int i;

			try{
				
				ExecutorService threadPoolAgent = Executors.newFixedThreadPool(aa.agents.size());
				ExecutorService threadPoolChatCustomer = Executors.newFixedThreadPool(aa.chatCustomers.size());
				
				for (i = 0 ; i < aa.agents.size(); i++){
					threadPoolAgent.submit(aa.agents.get(i));
					wait(2);
				}
				
				for (i = 0 ; i < aa.chatCustomers.size(); i++){
					threadPoolChatCustomer.submit(aa.chatCustomers.get(i));
					wait(2);
				}
			    
				// wait for keyboard input before exiting
				wait_for_input();
				threadPoolShutdown(threadPoolAgent);
				threadPoolShutdown(threadPoolChatCustomer);

			}catch(Exception e){
				log.info("I am handling General exception=>"+ e.toString());
				//resetAllActors(testName);
			}finally{
				endTestCase(testName);
			}
			
		}
		
	


}
