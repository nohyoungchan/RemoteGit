package HBTestesNG.BaseObjects;



//import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.InvalidElementStateException;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import ExceptionCustom.EC_DisconnectBtn;
import ExceptionCustom.EC_logintoAIC;


public class AgentHB extends Agent {


	public AgentHB() throws Exception{
		super();
	}
	
		
	
	
	public boolean CheckStatus(String strStatus) throws Exception{
		// State can be: idle, busy, unavailable when released
	  	log.info("\n@(" + agentType + ") " +  username + " #### Check Status ####");

	  	try{
			  String txtReturned;
			  WebElement webElement;
	
			  webElement = getWebElement("webAgentStatusBox", 10);	
			  txtReturned = webElement.getText();
			  wait(2);
			  log.info("\n@(" + agentType + ") " +  username + ": Status=> " + txtReturned);
			  if (txtReturned.contains(strStatus)) //If agent is idle, release.
			  {
				  return true;
			  }else{
			  	return false;
			  }
		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			state = "weird";
			throw e;
		}
  }
	
	/**
	   * This wait for an email arrive, and when no email arrives throw exception
	   * @param numTry  each try takes about 11 second
	   * @return
	   * @throws Exception
	   */
	  public boolean waitForEmailArrives(int numTry) throws Exception {
			 WebElement webElement;
			 boolean returnResult;
			 int waitTimeSec;
			 int i;
			 i=0;
			 
			 webElement = null;
			 returnResult = true;
			 waitTimeSec = 10;
			 
			 log.info("\n@(" + agentType + ") " + username + " ########### Wait for email arrives : maximum => " + numTry * waitTimeSec);

			 try{
				 maximizeBrowser();
				 
				while(true){
					webElement = waitUntilClickable("btnAnswerEmail", waitTimeSec); 
					if (webElement != null) {
						log.info("\n@(" + agentType + ") " + username + " Email Arrived");
						break;
					}else {
						log.info("\n@(" + agentType + ") " + username + "=>@@ Fail to receive email yet");
						
					}
					i++;
					wait(1);
					if (i > numTry) {
						returnResult = false;
						//errorCount++;
						TestStatus.errorReason.add(username + ": failed on => receiving an email");
						throw new Exception();
					}
				}
			 }catch(ElementNotVisibleException e){
				 log.error("\n@(" + agentType + ") " + username + " =>@@ ElementNotVisibleException on " + " " + "btnAnswerEmail");
				 returnResult = false;
				 
			 }catch(InterruptedException e){
				  log.error("@ " + username + " : @@ Thread inturrepted -> throw again on click_XPath()");
				  throw e;
			 }catch(Exception e){
				 log.error("\n@(" + agentType + ") " + username + " =>@@@@ Exception on " + "btnAnswerEmail" + " " +e.toString());
				 returnResult = false;
				 throw e;
			 }finally {
				 wait(1);
				 minimizeBrowser();
				 return returnResult;
			 }
			
			
		}
		  
	  
	
	public void WaitUntil_StatusBecomesIdle() throws Exception{
		// State can be: idle, busy, unavailable when released
	  	log.info("\n@(" + agentType + ") " +  username + " #### WaitUntil_StatusBecomesIdle ####");

	  	try{
	  		  int i;
	  		  i=0;
			  String txtReturned;
			  WebElement webElement;
			  
			 while (true){
				  i++;
				  webElement = getWebElement("webAgentStatusBox", 10);
				  txtReturned = webElement.getAttribute("class"); 
				  wait(3, i);
				  log.info("   \n@(" + agentType + ") " +  username + ": Status=> " + txtReturned);
				  if (txtReturned.contains("idle")) break;
				  
				  switch (txtReturned) 
				   {
			         case "status title-bg unavailable":
			        	resumeAgent();
			            break;
			         case "status title-bg busy":
			        	 break;
			         case "status unavailable title-bg":
				         resumeAgent();
				         break;
			         default:
			        	 log.info("   \n@(" + agentType + ") " +  username + "Something wrong: Need to handle this state");
				   }

			 } 
		}catch(UnhandledAlertException e){
			popUpHandle_Close();
		}catch(NoSuchElementException e){
			popUpHandle_Close();
		}
	  	catch(InterruptedException e){
			//log.info("I am handling Interrupted Exceptionj=>"+ e.toString());
			throw e;
			//state = "weird";
		}
	  	catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			state = "weird";
			throw e;
		}
  }
  
	

	public void logIntoWebAgent() throws Exception {
	    
		log.info("\n@(" + agentType + ") " +  username + " #### Go to Login Page ####");
		WebElement webElement;
		boolean actionResult;
		webElement = null;
		actionResult = true;
		//getCurrentDimenSion();
		log.info("I am here: url is => " +  AllActors.iniEnv.get("URL", "agentHBURL"));
		driver.get(AllActors.iniEnv.get("URL", "agentHBURL")); 
		
		//#### Action
		maximizeBrowser();
		//
		
		log.info("## Precondition: logIntoWebAgent");
		wait(3);
		//actionResult = logIntoWebAgent_PreCondition();
		webElement=conditonClickable_XPath("logIntoWebAgent_PreCondition", "webAgentUserNameTxtBox", 60);
		if(webElement != null){
			
			log.info("\n@(" + agentType + ") " +  username + " #### Adding username -> password -> Submit ####");
			driver.findElement(By.id("username")).clear();
			driver.findElement(By.id("username")).sendKeys(username);
			driver.findElement(By.id("password")).clear();
			driver.findElement(By.id("password")).sendKeys(password);
			wait(2);
			driver.findElement(By.id("submitBtn")).click();
			
			//##### Post Condition: Checks if release/resule works, if not refresh
			wait(3);
			
			webElement=conditonClickable_XPath("logIntoWebAgent_PostCondition", "btnResume", 60);
			if(webElement == null) actionResult = false;

		}else {
			actionResult = false;
		}
		minimizeBrowser();
		if (actionResult == false)  throw new EC_logintoAIC("@@ " + username + "logIntoWebAgent() failed");

		//return actionResult;
	}
	
	  public void logIntoGroup() throws Exception{
		  String txtReturned;
		  
		  log.info("\n@(" + agentType + ") " +  username + " #### Check if the agent needs to log in. ####");
		  txtReturned= getWebElement("btnLogIntoGroup", 10).getText();
		  if (txtReturned.equalsIgnoreCase("Log into my queues")) //If agent is logged out, log in.
		  {
		      log.info("\n@(" + agentType + ") " +  username + " #### Agent is logged out=>Click Log in button ####");
		      click_XPath(("btnLogIntoGroup"));
		      wait(5);
		  }else{//if agent is already logged in, print status only
		  	log.info("** Agent Status: " + txtReturned);
		  }
	  }
	  
	  public void logOutGroups() throws Exception{
		  log.info("\n@(" + agentType + ") " +  username + " #### Logging out ####");
		  try{
			  //getCurrentDimenSion();
			  maximizeBrowser();
			  String txtReturned;  
			  txtReturned= getWebElement("btnLogIntoGroup", 10).getText();
			  if (!txtReturned.equalsIgnoreCase("Log into my queues")) //If agent is logged out, log in.
			  {
			      log.info("\n@(" + agentType + ") " +  username + " #### Agent is currently logged in=>Click Log out button ####");
			      click_XPath("btnLogIntoGroup");
			      wait(1);
			  }else{//if agent is already logged in, print status only
				  log.info("\n@(" + agentType + ") " +  username + " #### Agent is alreay logged out ####");
			  	//log.info("** Agent Status: " + txtReturned);
			  }
			  //restoreDimenSion();
			  
		  }catch(NoSuchElementException e){
			  log.info("\n@(" + agentType + ") " +  username + "=> @@@@@ NoSuchElementException on logOutGroups()");
			  
		  }catch(Exception e){
			  log.info("\n@(" + agentType + ") " +  username + "=> @@@@@ exception on logOutGroups()");
			  
		  }finally{
			  minimizeBrowser();
		  }
	  }
	  
	  public void releaseAgent() throws Exception{
		  	log.info("\n@(" + agentType + ") " +  username + " #### Release agent. ####");
		  	if (state.contains("weird")) {
		  		log.info("@" + username + ": The state is $$$$ weird $$$$. So skip this step");
		  		return;
		  	}
		  	
		  	try{
				  String txtReturned, strAICState;
				  WebElement webElement;
				  maximizeBrowser();
		
				  webElement = getWebElement("btnResume", 10);
				  txtReturned = webElement.getText();
				  wait(2);
				  
				  //if (txtReturned.contains("Stop taking requests") || getAICState().contains("")) //If agent is idle, release.
				  strAICState = getAICState();
				  if (!strAICState.contains("Release"))
				  {
					  if (txtReturned.contains("Stop taking requests")) {
						  TestStatus.errorReason.add(username + ": AIC shows idle but still reads \" Stop taking requests \"");
					  }
					  
				      log.info("\n@(" + agentType + ") " +  username + " #### Agent is idle=>Click release button ####");
				      click_XPath(("btnResume"));
				      wait(2);
				  }else{
				  	log.info("\n@(" + agentType + ") " +  username + ": State is already released=> " + strAICState);
				  }
			}catch(InterruptedException e){
				//log.info("I am handling Interrupted Exceptionj=>"+ e.toString());
				throw e;
				//state = "weird";
			}
		  	catch(Exception e){
				log.info("I am handling General exception=>"+ e.toString());
				state = "weird";
				throw e;
			}finally{
				minimizeBrowser();
			  }
	  }
	  
	  public void releaseAgent_problem() throws Exception{
		  	log.info("\n@(" + agentType + ") " +  username + " #### Release agent. ####");
		  	if (state.contains("weird")) {
		  		log.info("@" + username + ": The state is $$$$ weird $$$$. So skip this step");
		  		return;
		  	}
		  	
		  	try{
				  String txtReturned;
				  WebElement webElement;
				  maximizeBrowser();
				    webElement=conditonClickable_XPath("resumeAgent_PreCondition", "btnResume", 60);
					if(webElement != null){

						  txtReturned = webElement.getText();	  	
						  log.info("\n@(" + agentType + ") " +  username+ " ResumeAgent and State Returned: " + txtReturned);
						  wait(2);
						  
						  if (txtReturned.contains("Stop taking requests")) //If agent is released
						  {
						      log.info("\n@(" + agentType + ") " +  username+ " #### Agent is idle>Click resume button ####");
						      click_XPath(("btnResume"));
						      wait(2);
						  }else{
						  	log.info("\n@(" + agentType + ") " +  username+ ": State=> Released");
						  }
					}

			}catch(InterruptedException e){
				//log.info("I am handling Interrupted Exceptionj=>"+ e.toString());
				throw e;
				//state = "weird";
			}
		  	catch(Exception e){
				log.info("I am handling General exception=>"+ e.toString());
				state = "weird";
				throw e;
			}finally{
				  minimizeBrowser();
			}
	  }
	  
	  public void releaseAgentSecondCode() throws Exception{
		    //This is temporary solution : Defect is open : UCC-1958
		    if (AllActors.iniMain.get("TestFlow", "releaseAgentSecondCode_defect").contains("yes"))
		    {
		       log.info("\n@(" + agentType + ") " +  username + " #### releaseAgentSecondCode_defect = yes, so just release ####");
		       releaseAgent();
		       return;
		    }
		    
		  	log.info("\n@(" + agentType + ") " +  username + " #### Release agent. ####");
		  	if (state.contains("weird")) {
		  		log.info("@" + username + ": The state is $$$$ weird $$$$. So skip this step");
		  		return;
		  	}
		  	
		  	try{
		  		  maximizeBrowser();
				  //resumeAgent();
			      wait(1);
			      click_XPath("btnReleaseCodeSelect");
			      wait(2);
			      click_XPath("secondReleaseCode");
			      wait(2);
				  
			}catch(InterruptedException e){
				//log.info("I am handling Interrupted Exceptionj=>"+ e.toString());
				TestStatus.errorReason.add(username + ": failed on => releaseAgentSecondCode");
				throw e;
				//state = "weird";
			}
		  	catch(Exception e){
				log.info("I am handling General exception=>"+ e.toString());
				//errorCount++;
				TestStatus.errorReason.add(username + ": failed on => releaseAgentSecondCode");
				state = "weird";
			}finally{
				  minimizeBrowser();
			  }
	  }
	  
	  public void releaseAgentThirdCode() throws Exception{
		  
		    //This is temporary solution : Defect is open : UCC-1958
		    if (AllActors.iniMain.get("TestFlow", "releaseAgentSecondCode_defect").contains("yes"))
		    {
		       releaseAgent();
		       return;
		    }
		    
		  	log.info("\n@(" + agentType + ") " +  username + " #### Release agent. ####");
		  	if (state.contains("weird")) {
		  		log.info("@" + username + ": The state is $$$$ weird $$$$. So skip this step");
		  		return;
		  	}
		  	
		  	try{
				  //resumeAgent();
				  maximizeBrowser();
			      wait(1);
			      click_XPath(("btnReleaseCodeSelect"));
			      wait(2);
			      click_XPath(("thirdReleaseCode"));
			      wait(2);
				  
			}catch(InterruptedException e){
				//log.info("I am handling Interrupted Exceptionj=>"+ e.toString());
				TestStatus.errorReason.add(username + ": failed on releaseAgentThirdCode");
				throw e;
				//state = "weird";
			}catch(Exception e){
				log.info("I am handling General exception=>"+ e.toString());
				TestStatus.errorReason.add(username + ": failed on releaseAgentThirdCode");
				state = "weird";
			}finally{
				  minimizeBrowser();
			  }
			      
	  }
	  
	  
	  
	  
	  public boolean resumeAgent_problem() throws Exception{
		    log.info("\n@(" + agentType + ") " +  username + " #### Resume agent. ####");
		    WebElement webElement;
		    boolean actionResult;
		    String txtReturned;
		    webElement = null;
		    actionResult = true;

		    maximizeBrowser();
		    webElement=conditonClickable_XPath("resumeAgent_PreCondition", "btnResume", 60);
			if(webElement != null){
		  	
			  	try{	
					  txtReturned = webElement.getText();	  	
					  log.info("\n@(" + agentType + ") " +  username+ " ResumeAgent and State Returned: " + txtReturned);
					  wait(2);
					  
					  if (txtReturned.contains("Start taking requests")) //If agent is released
					  {
					      log.info("\n@(" + agentType + ") " +  username+ " #### Agent is released=>Click resume button ####");
					      click_XPath(("btnResume"));
					      wait(2);
					  }else{
					  	log.info("\n@(" + agentType + ") " +  username+ ": State=> Idle");
					  }
					  
				}catch(InterruptedException e){
					log.error("\n@(" + agentType + ") " +  username + " I am handling Interrupted exception=> resumeAgent(), and throw again");
					actionResult = false;
					//errorCount++;
					TestStatus.errorReason.add(username + ": failed  to resume");
					throw e;
					//state = "weird";
				}
			  	catch(Exception e){
			  		log.error("\n@(" + agentType + ") " +  username + "I am handling General exception=> resumeAgent()");
					state = "weird";
					//errorCount++;
					TestStatus.errorReason.add(username + ": failed  to resume");
					actionResult = false;
			  	}
			}else{
				log.info("\n@(" + agentType + ") " +  username +" Precondtion is not met=> resumeAgent()");
			  	actionResult = false;
			}
			
			minimizeBrowser();
			return actionResult;
	  }
	  
	  public boolean resumeAgent() throws Exception{
		    log.info("\n@(" + agentType + ") " +  username + " #### Resume agent. ####");
		    WebElement webElement;
		    boolean actionResult;
		    String txtReturned, strAICState;
		    webElement = null;
		    actionResult = true;

		    maximizeBrowser();
		    webElement=conditonClickable_XPath("resumeAgent_PreCondition", "btnResume", 60);
			if(webElement != null){
		  	
			  	try{	
					  txtReturned = webElement.getText();	  	
					  log.info("\n@(" + agentType + ") " +  username+ " ResumeAgent and State Returned: " + txtReturned);
					  wait(2);
					  
					  //if (txtReturned.contains("Start taking requests") || txtReturned.contains("Commencer à prendre les appels")) //If agent is idle, release.
					  //{
					  //if (getAICState().contains("Release")) //If agent is Release, resume: This is old way
					  strAICState = getAICState();
					  if (strAICState.contains("Release")) //If agent is Release, resume
					  {
						  if (txtReturned.contains("Start taking requests")) {
							  TestStatus.errorReason.add(username + ": AIC shows Released but still reads \" Start taking reauests \"");
						  }
					      log.info("\n@(" + agentType + ") " +  username+ " #### Agent is released=>Click resume button ####");
					      click_XPath(("btnResume"));
					      wait(2);
					  }else{
					  	log.info("\n@(" + agentType + ") " +  username+ ": State is idle alreay => " + strAICState);
					  }
					  
				}catch(InterruptedException e){
					log.error("\n@(" + agentType + ") " +  username + " I am handling Interrupted exception=> resumeAgent(), and throw again");
					actionResult = false;
					//errorCount++;
					TestStatus.errorReason.add(username + ": failed  to resume");
					throw e;
					//state = "weird";
				}
			  	catch(Exception e){
			  		log.error("\n@(" + agentType + ") " +  username + "I am handling General exception=> resumeAgent()");
					state = "weird";
					//errorCount++;
					TestStatus.errorReason.add(username + ": failed  to resume");
					actionResult = false;
			  	}
			}else{
				log.info("\n@(" + agentType + ") " +  username +" Precondtion is not met=> resumeAgent()");
			  	actionResult = false;
			}
			
			minimizeBrowser();
			return actionResult;
	  }
	  
		  
	  public synchronized void answerACDCall(int ringSec, int talkSec) throws Exception{
		  String strFunctionName = "answerACDCall";
		  log.info("\n@(" + agentType + ") " +  username + " #### Click Answer button ####");
		  try{
			  //getCurrentDimenSion();
			  maximizeBrowser();
			  wait(ringSec, "Ring");
			  
			  click_XPath("btnAnswer");
			  wait(talkSec, "Talk");
			  state = "busy";
			  //minimizeBrowser();
			  //restoreDimenSion();
			  //Assert.assertTrue(true, "Answered Correctly");
		  }catch(InterruptedException e){
				 TestStatus.errorReason.add(username + ": failed on"  + strFunctionName);
			  log.error("@ " + username + " : @@ Thread inturrepted -> throw again on answerACDCall()");
			  throw e;
		 }catch(Exception e){
			 //errorCount++;
			 TestStatus.errorReason.add(username + ": failed on"  + strFunctionName);
			log.info("\n@(" + agentType + ") " +  username + " exception on answering acd call" + e.toString() );
			state = "weird";
			stopTest = "yes"; //This will stop all test cases
			throw e;
		  }finally {
			  minimizeBrowser();
		  }
	  }
	  
	  public synchronized void answerEmail(int ringSec, int talkSec) throws InterruptedException{
		  String strFunctionName = "answerEmail";
		  log.info("\n@(" + agentType + ") " +  username + " #### Click Answer button ####");
		  try{
			  //getCurrentDimenSion();
			  maximizeBrowser();
			  wait(ringSec, "Ring");
			  
			  click_XPath("btnAnswerEmail");
			  wait(talkSec, "Talk");
			  state = "busy";
		  }catch(InterruptedException e){
			  TestStatus.errorReason.add(username + ": failed on => " + strFunctionName);
			  log.error("@ " + username + " : @@ Thread inturrepted -> throw again on answerACDCall()");
			  throw e;
		 }catch(Exception e){
			//errorCount++;
			TestStatus.errorReason.add(username + ": failed on => " + strFunctionName);
			log.info("\n@(" + agentType + ") " +  username + " exception on answering acd call" + e.toString() );
			state = "weird";
		  }finally{
			  minimizeBrowser();
		  }
	  }
	  
	  public void hold(int secInt) throws InterruptedException{
		  log.info("\n@(" + agentType + ") " +  username + " #### Click hold button ####");
		  try{
			  maximizeBrowser();
 
			  click_XPath("btnHold");
			  wait(secInt, "holding...");
			  state = "busy";

		  }catch(InterruptedException e){
			  log.error("@ " + username + " : @@ Thread inturrepted -> throw again on answerACDCall()");
			  throw e;
		 }catch(Exception e){
			log.info("\n@(" + agentType + ") " +  username + " exception on answering acd call" + e.toString() );
			state = "weird";
		  }finally{
			  minimizeBrowser();
		  }
	  }
	  
	  public void unHold(int secInt) throws InterruptedException{
		  log.info("\n@(" + agentType + ") " +  username + " #### Click unhold button ####");
		  try{
			  maximizeBrowser();

			  //unholding is using the same button which is "btnHold"
			  click_XPath("btnHold");
			  wait(secInt, "unholding...");
			  state = "busy";

		  }catch(InterruptedException e){
			  log.error("@ " + username + " : @@ Thread inturrepted -> throw again on answerACDCall()");
			  throw e;
		 }catch(Exception e){
			log.info("\n@(" + agentType + ") " +  username + " exception on answering acd call" + e.toString() );
			state = "weird";
		  }finally{
			  minimizeBrowser();
		  }
	  }
	  
	  public synchronized void answerAndDisconnect(int ringSec, int talkSec) throws InterruptedException{
		  log.info("\n@(" + agentType + ") " +  username + " #### Click Answer button ####");
		  try{
			  getCurrentDimenSion();
			  maximizeBrowser();
			  wait(ringSec, "Ring");
			  
			  resumeAgent();
			  //wait(2);
			  
			  if(click_XPath("btnAnswer")){
				  wait(5);
				  if(waitUntilClickable("btnDisconnect", 5) != null){
					  wait(talkSec, "Talk");
					  click_XPath("btnDisconnect");
				  }else{
					  log.info("\n@(" + agentType + ") " +  username+ " =>Answered is not done, so no disconnect");
					  
				  }
				  
			  }else{
				  if(waitUntilClickable("btnDisconnect", 5) != null){
					  //wait(talkSec, "Talk");
					  click_XPath("btnDisconnect");
				  }else{
					  log.info("\n@(" + agentType + ") " +  username+ " => is not talking, so don't disconnect");
					  
				  }
			  }
			  restoreDimenSion();
			  //Assert.assertTrue(true, "Answered Correctly");
		  }catch(InterruptedException e){
			  log.info("@ " + username + " : @@ Thread inturrepted on answerAndDisconnect(). Throw again!");
			  throw e;
		 } catch(Exception e){
			log.error("\n@(" + agentType + ") " +  username + " exception on answerAndDisconnect()" + e.toString() );
			state = "weird";
		  }
	  }
	  
	  public synchronized void answerChatAndType(int ringSec, int talkSec) throws InterruptedException{
		  log.info("\n@(" + agentType + ") " +  username + " #### answerChatAndType ####");
		  try{
			  maximizeBrowser();
			  wait(ringSec, "Ring");

			  resumeAgent();
			  //wait(2);
			  
			  click_XPath("btnAnswer");
			  wait(talkSec, "Talk");
			  state = "busy";
			  enterXPathAndEnter("Hello", "webAgentChatSendTxtBox");
		  }catch(InterruptedException e){
			  log.error("@ " + username + " : @@ Thread inturrepted -> throw again on answerACDCall()");
			  throw e;
		 }catch(Exception e){
			log.info("\n@(" + agentType + ") " +  username + " exception on answering acd call" + e.toString() );
			state = "weird";
		  }finally{
			  minimizeBrowser();
		  }
	  }
	  
	  
	  //This is blind transfer by number
	  public void blindTransfer(String num){
		  log.info("\n@(" + agentType + ") " +  username + " #### Blind Transfer to =>" + num +" ####");
		  try{
			  //getCurrentDimenSion();
			  maximizeBrowser();
			  wait(2);
			  log.info("\n@(" + agentType + ") " +  username + ": Clikc Transfer Button");
			  click_XPath(("btnTransfer"));
			  wait(2);
			  log.info("\n@(" + agentType + ") " +  username + ": Type the number: " + num);
			  typeElementXPath(AllActors.iniXPath.get("AIC", "txtBoxTransfer"), num);
			  click_XPath(("txtBoxTransfer"));
			  wait(2);		  
			  log.info("\n@(" + agentType + ") " +  username + ": Confirm blind transfer");
			  click_XPath(("btnBlindTransfer"));
			  state = "idle";
		  }catch(Exception e){
			  log.info("\n@(" + agentType + ") " +  username + " exception on blindTransfer" + e.toString());
			  state = "weird";
		  }finally{
			  minimizeBrowser();
		  }
	  }
	  
	  public void blindTransfer_byName(String name) throws Exception{
		  log.info("\n@(" + agentType + ") " +  username + " #### Blind Transfer by Name to =>" + name +" ####");
		  try{
			  //getCurrentDimenSion();
			  maximizeBrowser();
			  wait(2);
			  log.info("\n@(" + agentType + ") " +  username + ": Clikc Transfer Button");
			  click_XPath(("btnTransfer"));
			  wait(2);
			  log.info("\n@(" + agentType + ") " +  username + ": Type the name: " + name);

			  selectFromInput_ByPartialText(name, "txtBoxTransfer");
			  wait(1);
			  log.info("\n@(" + agentType + ") " +  username + ": Click blind transfer btn");
			  click_XPath("btnBlindTransfer");
			  state = "idle";
			  wait(2);
		  }catch(Exception e){
			  log.info("\n@(" + agentType + ") " +  username + " exception on blindTransfer" + e.toString());
			  state = "weird";
			  throw e;
		  }finally{
			  minimizeBrowser();
		  }
	  }
	  
	  public void consultTransfer(String num) throws Exception{
		  log.info("\n@(" + agentType + ") " +  username + " #### Consult Transfer to =>" + num +" ####");  
		  try{
			  //getCurrentDimenSion();
			  //This is needed for chat transfer since without it, selenium cannot confirm transfer.
			  maximizeBrowser();
			  wait(2);
			  log.info("\n@(" + agentType + ") " +  username + ": Clikc Transfer Button");
			  click_XPath(("btnTransfer"));
			  wait(2);
			  log.info("\n@(" + agentType + ") " +  username + ": Type the number: " + num);
			  typeElementXPath(AllActors.iniXPath.get("AIC", "txtBoxTransfer"), num);
			  click_XPath(("txtBoxConference"));
			  wait(2);		  
			  log.info("\n@(" + agentType + ") " +  username + ": Press Consult transfer button");
			  click_XPath(("btnConsultTransfer"));
			  //restoreDimenSion();
		  }catch(Exception e){
			  log.info("\n@(" + agentType + ") " +  username + " exception on Consult Transfer" + e.toString());
			  state = "weird";
			  throw e;
		  }finally{
			  minimizeBrowser();
		  }
	  }
	  
	  public void consultTransfer_byName(String name) throws Exception{
		  log.info("\n@(" + agentType + ") " +  username + " #### Consult Transfer to =>" + name+" ####");  
		  try{
			  maximizeBrowser();
			  wait(2);
			  log.info("\n@(" + agentType + ") " +  username + ": Clikc Transfer Button");
			  click_XPath(("btnTransfer"));
			  wait(2);
			  log.info("\n@(" + agentType + ") " +  username + ": Type the number: " + name);
			  //typeElementXPath(AllActors.iniXPath.get("AIC", "txtBoxTransfer"), name);
			  //click_XPath(("txtBoxConference"));
			  selectFromInput_ByPartialText(name);
			  wait(2);		  
			  log.info("\n@(" + agentType + ") " +  username + ": Press Consult transfer button");
			  click_XPath(("btnConsultTransfer"));
		  }catch(Exception e){
			  log.info("\n@(" + agentType + ") " +  username + " exception on Consult Transfer" + e.toString());
			  state = "weird";
			  throw e;
		  }finally{
			  minimizeBrowser();
		  }
	  }
	  
	  public void confirmTransfer(){  	 
		  log.info("\n@(" + agentType + ") " +  username + ": Press Confirm button");
		  try{
			  maximizeBrowser();
			  wait(2);	  
			  click_XPath(("btnConsultTransferConfirm"));
			  state = "idle";
			  minimizeBrowser();
		  }catch(Exception e){
			  log.info("\n@(" + agentType + ") " +  username + " exception on blindTransfer" + e.toString());
			  state = "weird";
		  }finally{
			  minimizeBrowser();
		  }
	  }
	  
	  public void consultConference_ByNumber(String num){
		  log.info("\n@(" + agentType + ") " +  username + " #### Consult Conference to =>" + num +" ####");
		  try{
			  //getCurrentDimenSion();
			  //This is needed for chat transfer since without it, selenium cannot confirm transfer.
			  maximizeBrowser();		  
			  wait(2);
			  log.info("\n@(" + agentType + ") " +  username + ": Clikc Conference Button");
			  click_XPath(("btnConference"));
			  wait(2);
			  log.info("\n@(" + agentType + ") " +  username + ": Type the number: " + num);
			  typeElementXPath(AllActors.iniXPath.get("AIC", "txtBoxConference"), num);
			  click_XPath(("txtBoxConference"));
			  wait(2);		  
			  log.info("\n@(" + agentType + ") " +  username + ": Press Consult Conference button");
			  click_XPath(("btnConsultConference"));
			  wait(2);
		  }catch(Exception e){
			  log.info("\n@(" + agentType + ") " +  username + " exception on Consult Conference" + e.toString());
			  state = "weird";
		  }finally{
			  minimizeBrowser();
		  }

	  }
	  
	public void consultConference_ByAgentName(String agentName) throws Exception{
		  log.info("\n@(" + agentType + ") " +  username + " #### Consult Conference to =>" + agentName +" ####");
		  try{
			  maximizeBrowser();		  
			  wait(2);
			  log.info("\n@(" + agentType + ") " +  username + ": Clikc Conference Button");
			  click_XPath(("btnConference"));
			  wait(2);
			  
			  log.info("\n@(" + agentType + ") " +  username + ": select (for consult_conf)" + agentName);
			  selectFromInput_ByPartialText(agentName);
			  wait(2);		  
			  log.info("\n@(" + agentType + ") " +  username + ": Press Consult Conference button");
			  click_XPath("btnConsultConference");
		  }catch(Exception e){
			  log.info("\n@(" + agentType + ") " +  username + " exception on Consult Conference" + e.toString());
			  state = "weird";
			  throw e;
		  }finally{
			  minimizeBrowser();
		  }
		  

	  }
	  
	  public void confirmConference() throws Exception{		
		  log.info("\n@(" + agentType + ") " +  username + ": Press Confirm button");
		  try{
			  maximizeBrowser();
			  wait(2);	  
			  if(!click_XPath(("btnConsultConferenceConfirm"))) throw new Exception();
			  //Assert.assertTrue(true, "Answered Correctly");
			  wait(2);
		  }catch(Exception e){
			  log.info("\n@(" + agentType + ") " +  username + " exception on Confirm Conference" + e.toString());
			  state = "weird";
			  throw e;
		  }finally{
			  minimizeBrowser();
		  }
	  }
	  
	  public void disconnectByWebAgent(int waitSec) throws EC_DisconnectBtn {
		  log.info("\n@(" + agentType + ") " +  username + " #### Disconnect by WebAgent ####");

		  try{
			  //getCurrentDimenSion();
			  maximizeBrowser();
			  wait(waitSec, "wait before clicking btnDisconnect");
			  
			  log.info("\n ## Pre-Condition of disconnectByWebAgent()");
			  if(waitUntilClickable("btnDisconnect", 5) == null){
				  click_XPath(("panelCurrentCall"));  //This is needed for a consult transferred/conference call.
			  }
			  
			  wait(2);
			  if (click_XPath(("btnDisconnect"))) {
				  log.info("\n@(" + agentType + ") " +  username + " disconnectByWebAgent() successfully"); 
			  }else {
				  log.info("\n@(" + agentType + ") " +  username + " doesn't need to disconnect"); 
			  };
			  
			  state = "idle";
			  
		  }catch(EC_DisconnectBtn e) {
			  TestStatus.errorReason.add(username + ": failed -Exception on btnDisconnectByWebAent");
			  //Assert.assertTrue(false, errorString);
			  throw new EC_DisconnectBtn("Fail to close disconnectBtn");}
		  catch(Exception e){
			  TestStatus.errorReason.add(username + ": failed -Exception on btnDisconnectByWebAent");
			  log.info("\n@(" + agentType + ") " +  username + " exception on disconnectByWebAgent");
			  state = "weird";
		  }finally {
				minimizeBrowser();
		  }
	  }
	  
	  
	  public void disconnectByWebAgent() throws EC_DisconnectBtn {
		  log.info("\n@(" + agentType + ") " +  username + " #### Disconnect by WebAgent ####");
		  try{
			  //getCurrentDimenSion();
			  maximizeBrowser();
			  
			  log.info("\n ## Pre-Condition of disconnectByWebAgent()");
			  if(waitUntilClickable("btnDisconnect", 5) == null){
				  click_XPath(("panelCurrentCall"));  //This is needed for a consult transferred/conference call.
			  }
			  
			  wait(2);
			  if (click_XPath(("btnDisconnect"))) {
				  log.info("\n@(" + agentType + ") " +  username + " disconnectByWebAgent()"); 
			  }else {
				  log.info("\n@(" + agentType + ") " +  username + " doesn't need to disconnect"); 
			  };
			  
			  //This only works with a single call.  Not working when
			  log.info("\n ## Post-Condition of disconnectByWebAgent()");
			  wait(2);
			  //if the result is not Busy, the disconnect is successful
			  if(getAICStateAfterDisconnect().contains("Busy")) {
			      log.info("\n@(" + agentType + ") " +  username + " disconnectBtn still exist after clicking it");
			      captureScreenshot("DisconnectByWebAgent_panelExist");
			      throw new EC_DisconnectBtn("disconnectBtn is not working");
			  }
			  log.info("\n@(" + agentType + ") " +  username + " disconnectByWebAgent() successfully"); 
			  state = "idle";
			  //restoreDimenSion();
			  
		  }catch(EC_DisconnectBtn e) {
			  //errorCount++;
			  TestStatus.errorReason.add(username + ": failed -Exception after pressing btnDisconnect");
			  //Assert.assertTrue(false, errorString);
			  throw new EC_DisconnectBtn("Fail to close disconnectBtn");}
		  catch(Exception e){
			  log.info("\n@(" + agentType + ") " +  username + " exception on disconnectByWebAgent");
			  TestStatus.errorReason.add(username + ": failed -Exception after pressing btnDisconnect");
			  state = "weird";
		  }finally {
				minimizeBrowser();
		  }
	  }
	  
	  public void disconnectChatByWebAgent() {
		  log.info("\n@(" + agentType + ") " +  username + " #### Disconnect Chat by WebAgent ####");
		  boolean res;
		  res = true;
		  try{
			  maximizeBrowser();
			  click_XPath(("panelCurrentChat"));  //This is needed for a consult transferred call.
			  wait(2);
			  if (click_XPath(("btnDisconnect"))) {
				  log.info("\n@(" + agentType + ") " +  username + " disconnectChatByWebAgent() successfully"); 
			  }else {
				  log.info("\n@(" + agentType + ") " +  username + " doesn't need to disconnect"); 
			  };
			  state = "idle";
		  }catch(Exception e){
			  log.info("\n@(" + agentType + ") " +  username + " exception on disconnectChatByWebAgent");
			  TestStatus.errorReason.add(username + ": failed -Exception after pressing disconnectChatByWebAgent");
			  state = "weird";
		  }finally{
			  minimizeBrowser();
		  }
	  }
	  
	  public void disconnectOACD(){
		  log.info("\n@(" + agentType + ") " +  username + " #### Disconnect by WebAgent : OACD call ####");
		  try{
			  //getCurrentDimenSion();
			  maximizeBrowser();
			  click_XPath(("panelCurrentCall"));
			  wait(2);
			  click_XPath(("btnDisconnectOACD"));
			  wait(2); //The following is needed for successful OACD call.
			  click_XPath(("OACDDisconnectSuccessful"));
			  
			//This only works with a single call.  Not working when
			  log.info("\n ## Post-Condition of disconnectOACD()");
			  wait(2);
			  //if the result is not Busy, the disconnect is successful
			  if(getAICStateAfterDisconnect().contains("Busy")) {
			      log.info("\n@(" + agentType + ") " +  username + " disconnectBtn still exist after clicking it");
			      throw new EC_DisconnectBtn("disconnectOACD is not working");
			  }
			  log.info("\n@(" + agentType + ") " +  username + " disconnectByWebAgent() successfully"); 
			  state = "idle";
		  
		  }catch(Exception e){
			  log.info("\n@(" + agentType + ") " +  username + " exception on disconnectOACD");
			  TestStatus.errorReason.add(username + ": failed on disconnectOACD");
			  state = "weird";
		  }finally{
			  minimizeBrowser();
		  }
	  }
	  
	  public void Resume_checkState_IfIdleReturn_InCallDisconnect(){
		  log.info("\n### Reset: @" + username+ " #### checkState_IfIdleReturn_InCallDisconnect####");
		  try{
			  resumeAgent();
			  if (state.contains("idle")) {
				  log.info("\n@(" + agentType + ") " +  username + " is Idle");
				  return;
			  }
			  disconnectByWebAgent();

		  }catch(Exception e){
			  log.info("\n@(" + agentType + ") " +  username + " exception on disconnectByWebAgent_Reset" + e.toString());
			  state = "weird";
		  }

	  }
	  
	  public void disconnectByWebAgent_Reset(){
		  log.info("\n### Reset: @" + username+ " #### Disconnect by WebAgent####");
		  try{
			  if (state.contains("idle")) {
				  log.info("\n@(" + agentType + ") " +  username + " is Idle");
				  return;
			  }
			  if (clickElementXPath_Reset(AllActors.iniXPath.get("AIC", "panelCurrentCall"))){
				  wait(2);
				  if (clickElementXPath_Reset(AllActors.iniXPath.get("AIC", "btnRetrieve"))){
					  wait(2);
					  clickElementXPath_Reset(AllActors.iniXPath.get("AIC", "btnDisconnect"));
				  }
				  state = "idle";
			  }
		  
		  }catch(Exception e){
			  log.info("\n@(" + agentType + ") " +  username + " exception on disconnectByWebAgent_Reset" + e.toString());
			  state = "idle";  //There might be any call left, so this is OK.
		  }

	  }
	  
	  /**
	   * If wrap-up fails, it doesn't need to stop a test, so not throwing exception
	   */
	  public void wrapupEnd(){
		  
		  log.info("\n@(" + agentType + ") " +  username + " #### Wrapup End ####"); 
		  log.info("\n@(" + agentType + ") " +  username + " : Pressing Extend Wrapup button");
		  
		  try{
			  maximizeBrowser();
			  if (!click_XPath("btnWrapExtend")){
				  log.info("\n@(" + agentType + ") " +  username + " : Wraup could be 0, so finish wrapup");
				  return;
			  }
			  log.info("\n@(" + agentType + ") " +  username + " : Pressing End Wrapup button");
			  click_XPath("btnWrapupEnd", 3);
			  state = "idle";
			  //Assert.assertTrue(true, "Wrapup end Correctly");
			  //wait(10);
		  }catch(Exception e){
			  log.info("\n@(" + agentType + ") " +  username + " exception on wrapupEnd" + e.toString());
			  state = "weird";
		  }finally {
			  minimizeBrowser();
		  }
	  }
	  
	  /**
	   *  If wrap-up fails, it doesn't need to stop a test, so not throwing exception
	   * @param wrapSec
	   */
	  public void wrapupEnd(int wrapSec) {
		  log.info("\n@(" + agentType + ") " +  username + " #### Wrapup End ####");
		  log.info("\n@(" + agentType + ") " +  username + " : Pressing Extend Wrapup button");
		  try{
			  maximizeBrowser();
			  if (!click_XPath("btnWrapExtend")){
				  log.info("\n@(" + agentType + ") " +  username + " : Wraup could be 0, so finish wrapup");
				  return;
			  }
			  wait(wrapSec, "Wrapup time");
			  log.info("\n@(" + agentType + ") " +  username + " : Pressing End Wrapup button");
			  click_XPath(("btnWrapupEnd"));
			  state = "idle";
			  //Assert.assertTrue(true, "Wrapup end Correctly");
			  //wait(10);
		  }catch(Exception e){
			  log.info("\n@(" + agentType + ") " +  username + " exception on wrapupEnd" + e.toString());
			  state = "weird";
		  }finally {
			  minimizeBrowser();
		  }
	  }
	  
	  /**
	   *  If wrap-up fails, it doesn't need to stop a test, so not throwing exception
	   * @param wrapSec
	   */
	  public void wrapupEndWith2WrapupCodes(int wrapSec) {
		  String strFunctionName = "wrapupEndWith2WrapupCode";
		  log.info("\n@(" + agentType + ") " +  username + " #### Wrapup End ####");
		  log.info("\n@(" + agentType + ") " +  username + " : Pressing Extend Wrapup button");


		  try{
			  maximizeBrowser();
			  if (!click_XPath("btnWrapExtend", 2)){
				  log.info("\n@(" + agentType + ") " +  username + " : Wraup could be 0, so finish wrapup");
				  return;
			  }
			  //Todo : Enter wrapup Code
			  wait(1);
			  click_XPath("comboBoxWrapupCode", 1);
			  wait(1);
			  click_XPath("wrapupCodeFirst", 3);
			  wait(1);
			  click_XPath("wrapupCodeSecond", 3);
			  wait(1);
			  click_XPath(("comboBoxWrapupCode"));
			  wait(wrapSec, "Wrapup time");
			  log.info("\n@(" + agentType + ") " +  username + " : Pressing End Wrapup button");
			  click_XPath(("btnWrapupEnd"));
			  state = "idle";
		  }catch(Exception e){
			  log.info("\n@(" + agentType + ") " +  username + " exception on wrapupEndWith2WrapupCodes" + e.toString());
			  TestStatus.errorReason.add(username + ": failed on wrapupEndWith2WrapupCodes");
			  state = "weird";
		  }finally {
				minimizeBrowser();
		  }

	  }
	  
	  public void signoutWebAgent(){
		  log.info("\n@(" + agentType + ") " +  username + " #### Signout ####");
		  String strFunctionName = "signoutWebAgent";
		  
		  try{
			  //getCurrentDimenSion();
			  maximizeBrowser();
			  click_XPath("btnSignOutMenu");
			  wait(2);
			  click_XPath("btnSignOut");
			  wait(5);
			  minimizeBrowser();
			  //restoreDimenSion();
		  }catch(Exception e){
			  log.info("\n@(" + agentType + ") " +  username + " exception on signoutWebAgent" + e.toString());
			  TestStatus.errorReason.add(username + ": failed on signoutWebAgent");
			  state = "weird";
		  }
	  }
	  
	  public void signoutWebAgentWhenNoCall(){
		  log.info("\n@(" + agentType + ") " +  username + " #### Signout When there is no call####");
		  
		  try{
			  //getCurrentDimenSion();
			  maximizeBrowser();
			  resumeAgent();
			  
			  String txtReturned;
			  WebElement webElement;
			  
			 while (true){
			  webElement = getWebElement("webAgentStatusBox", 10);	
			  txtReturned = webElement.getAttribute("class"); 
			  wait(3);
			  log.info("   \n@(" + agentType + ") " +  username + ": Status=> " + txtReturned);
			  if (txtReturned.contains("idle")) break;
			 } 
			 
			  click_XPath(("btnSignOutMenu"));
			  wait(2);
			  click_XPath(("btnSignOut"));
			  wait(5);
			  maximizeBrowser();
			  //restoreDimenSion();
		  }catch(Exception e){
			  log.info("\n@(" + agentType + ") " +  username + " exception on signoutWebAgent" + e.toString());
			  state = "weird";
		  }
	  }
	  
	  
	  
	  public void tearDownAll(){
		log.info("\n@(" + agentType + ") " +  username + " #### Logout and Close FireFox ####");
		try{
			logOutGroups();
			//signoutWebAgent();
			wait(2);
			driver.quit();
			driver = null;
			currentTime();	  
		}catch(Exception e){
			log.info("\n@(" + agentType + ") " +  username + " exception on tearDownAllt" + e.toString());
			state = "weird";
		}
	  }
	  
	  public void closeAndQuit(){
			log.info("\n@(" + agentType + ") " +  username + " #### Close Browser and quit driver ####");
			//signoutWebAgent();
			try{
				state = "closed";
				
				driver.quit();
				driver = null;
				currentTime();	  
			}catch(Exception e){
				log.info("\n@(" + agentType + ") " +  username + " exception on closeAndQuit" + e.toString());
				state = "weird";
			}
		  }
	  
	  public boolean checkState(){
		  try{
		  		if (state.contains("weird")) 
		  		{
		  			log.info("@" + username + ": The state is $$$$ weird $$$$. So skip this step");
		  			return false;
		  		}
		  		
		  }catch(Exception e){
			  	log.info("\n@(" + agentType + ") " +  username + " exception on checkState" + e.toString());
			  	state = "weird";
			  	return false;
		  }
		  return true;
	  }
	  
	  public void run()
	  {
		  int i, waitSec, ringTimeSec, talkTimeSec;
		  boolean boolExit;
		  boolExit = false;
		  i=0;
		  waitSec = globalSec;
		  log.info("@" + username + ": Running => " +  threadName );
	      try {
	    	  
	    	  if (TestObject.loginSequentially.contains("no")) logIntoWebAgent(); 
	    	  //if scenario is not mixed, use global scenario
	    	  if (!globalScenario.contains("Load_Mix")) scenarioLocal = globalScenario;
	    	
	    	  while (!this.isInterrupted()) 
	    	  {
	    			i++;
				   switch (scenarioLocal) 
				   {
			         case "Load_LogInOnly_ByThread":
			        	log.info("@" + username + ": Thread on=>Load_LogInOnly_ByThread");
			        	//agent is already logged in by this point on beforesuite();
			            break;
			         case "Load_ChangeState_ByThread":
			        	log.info("@" + username + ": Thread on=>Load_ChangeState_ByThread");
			     		wait(waitSec);
						releaseAgentSecondCode();
						wait(waitSec);
						resumeAgent();
						wait(waitSec);
						releaseAgentThirdCode();
						wait(waitSec);
						resumeAgent();
			        	 break;
			         case "Load_LogAndSignOut_ByThread":
				        	log.info("@" + username + ": Thread on=>Load_LogAndSignOut_ByThread");
				        	wait(globalSec);
				        	signoutWebAgent();
				        	wait(10);
				        	logIntoWebAgent();
				            break;
			         case "Load_LogAndReLogInAfterMin_ByThread":
				        	log.info("@" + username + ": Thread on=>Load_LogAndReLogInAfterMin_ByThread");
				        	waitMin(globalMinToRelogIn);
				        	WaitUntil_StatusBecomesIdle();
				        	signoutWebAgent();
				        	wait(10);
				        	logIntoWebAgent();
				            break;
			         case "Load_LogAndSignoutCloseAfterMin_ByThread":
				        	log.info("@" + username + ": Thread on=>Load_LogAndSignoutCloseAfterMin_ByThread");
				        	boolExit = true;
				        	waitMin(globalMinToRelogIn);
				        	WaitUntil_StatusBecomesIdle();
				        	signoutWebAgent();
				        	closeAndQuit();
				        	
				            break;
			         
			         default:
			             log.info("No matching load scenaro");
				   }
				log.info("@" + username + ": Just Finished Running =>  " + i + "th times");
				if (boolExit == true) {
					log.info("@" + username + ": I closed brower and out");
					break;
				}
				wait(waitSec);
				if (i < 5) conditonClickable_XPath("logIntoWebAgent_PostCondition", "btnResume", 60);;
				
				
	    	  }

			}catch(InterruptedException e){
				log.error("@ " + username + " : @@@@@@@@@ Thread inturrepted -> Leaving the thread. Bye! on on run()");
			} catch(NoSuchElementException e){
				log.error("@" + username + ": @@@@@@ NoSuchElementException happens on run()");	
			}  catch (Exception e) {
				log.error("@" + username + ": @@@@@ Exception happens on run()");
				e.printStackTrace();
			}
		  
	  }
	  
/*	  public void start(){
		  log.info("\n@(" + agentType + ") " +  username + " #### Starting Thread ####");
		  if (thread == null)
		  {
			  thread = new Thread(this, threadName);
			  thread.start();
		  }
		  
		    // to be signaled
		  synchronized(s_exitSignal) {
	    	try {
	    		s_exitSignal.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		    
		    driver.quit();
		  
	  }*/
	  
	  public synchronized void disconnectAndWrapupWith2Codes(int wrapupTime) throws Exception{
			 disconnectByWebAgent();
			 wrapupEndWith2WrapupCodes(wrapupTime);	
		  
	  }
	  
	  public void runLoadScenarioWithSwitch() throws Exception {
		  int waitSec;
		  waitSec = 5;
		     switch (globalScenario) {
		         case "Load_LogInOnly_ByThread":
		        	log.info("@" + username + ": Thread on=>Load_LogInOnly_ByThread"); 
		            break;
		         case "Load_ChangeState_Consistently":
		        	log.info("@" + username + ": Thread on=>Load_ChangeState_Consistently");
		     		wait(waitSec);
					releaseAgentSecondCode();
					wait(waitSec);
					resumeAgent();
					wait(waitSec);
					releaseAgentThirdCode();
					wait(waitSec);
					resumeAgent();
		        	 break;
		         case "Load_Email_SendEmail_Continuously_ByThread":
		        	 log.info("@" + username + ": Thread on=>Load_Email_SendEmail_Continuously_ByThread"); 
		             break;
		         case "Load_AnswerEmail_ByThread":
		        	 log.info("@" + username + ": Thread on=>Load_AnswerEmail_ByThread"); 
		        	 answerACDCall(5, 10);
		 			 disconnectByWebAgent();
					 wrapupEndWith2WrapupCodes(5);	
		         	 break;
		         case "Load_SendAndAnswerEmail_ByThread":
		        	 log.info("@" + username + ": Thread on=>Load_SendAndAnswerEmail_ByThread"); 
		        	 answerACDCall(10, 10);
		 			 disconnectByWebAgent();
					 wrapupEndWith2WrapupCodes(10);	
		         	 break;
		         default:
		             log.info("No matching load scenaro");

		     }
		}
	  
	  public void popUpHandle_Close() throws Exception
	  {

		     log.info("\n@(" + agentType + ") " + username + " => Handling Popup or unhandled alert ");

	        /*Set<String> windowId = driver.getWindowHandles();    // get  window id of current window
	        Iterator<String> itr = windowId.iterator();   
	        
	        String mainWebID, popUpWebID;
	        mainWebID="mainWebID";
	        popUpWebID = "popUpwebID";

	        int i = 0;
	        while(itr.hasNext()){
	        	i++;
	        	if (i==1){
	        		mainWebID = itr.next();
	        		log.info("\n@(" + agentType + ") " + username + " => Window ID(" + i + ") : " + mainWebID);  
	        	}else{
	        		popUpWebID = itr.next();
	        		log.info("\n@(" + agentType + ") " + username + " => Window ID(" + i + ") : " + popUpWebID);  
	        	}
	        	
	        }
	        
	        if (i == 1){
	        	return;
	        }else{
		        driver.switchTo().window(popUpWebID);
		        log.info("\n@(" + agentType + ") " + username + " => new Popup title : " + driver.getTitle());
		        wait(2);
		        driver.close();
		        driver.switchTo().window(mainWebID);
	        	
	        }*/
		    getCurrentDimenSion();
	        driver.quit();
	        wait(2);
	        openWebBrowser();
	        //restoreDimenSion();
	        logIntoWebAgent();
	  }
	  
	  /**
	   * The result can be other than Busy or Wrap-up after disconnect a call.
	   * This is only used after disconnect a call for post disconnect checking
	   * @return
	   * @throws Exception
	   */
	  public String getAICStateAfterDisconnect() throws Exception
	  {
		  String strWebElementGetText;

	      log.info("\n@(" + agentType + ") " + username + " => ######### Getting AIC state ########");
	      WebElement webElement = getWebElement("webAgentStatusBox", 10);     
	      strWebElementGetText = webElement.getText();
	      log.info("\n@(" + agentType + ") " + username + " => strWebElementGetText: " + strWebElementGetText);
	      return strWebElementGetText;
	        
	  }
	  
	  public String getAICState_old() throws Exception
	  {
		  String strWebElementGetText;

	      log.info("\n@(" + agentType + ") " + username + " => ######### Getting AIC state ########");
	      WebElement webElement = getWebElement("webAgentStatusBox", 10);     
	      strWebElementGetText = webElement.getText();
	      log.info("\n@(" + agentType + ") " + username + " => strWebElementGetText: " + strWebElementGetText);
	      return strWebElementGetText;
	        
	  }
	  
	  public String getAICState() throws Exception
	  {
		  String strWebElementGetText;

	      log.info("\n@(" + agentType + ") " + username + " => ######### Getting AIC state ########");
	      WebElement webElement = getWebElement("webAgentStatusBox", 10);     
	      strWebElementGetText = webElement.getText();
	      log.info("\n@(" + agentType + ") " + username + " => strWebElementGetText: " + strWebElementGetText);
	      return strWebElementGetText;
	        
	  }
	  
	  
	  
	  public void getAICState(String strState) throws Exception
	  {
		  String strWebElementGetText, strWebElementGetTagName, strWebElementToString;
		  String strClassToString, strClassGetName, strClassGetSimpleName;

	      log.info("\n@(" + agentType + ") " + username + " => ######### Getting AIC state after : " + strState);
	      WebElement webElement = getWebElement("webAgentStatusBox", 10);
	      
	      strWebElementGetText = webElement.getText();
	      strWebElementGetTagName= webElement.getTagName();
	      strWebElementToString = webElement.toString();

	      
	      strClassToString = webElement.getClass().toString();
	      strClassGetName = webElement.getClass().getName();
	      strClassGetSimpleName = webElement.getClass().getSimpleName();
	      
	      log.info("\n@(" + agentType + ") " + username + " => strWebElementGetText: " + strWebElementGetText);
	      log.info("\n@(" + agentType + ") " + username + " => strWebElementGetTagName: " + strWebElementGetTagName);
	      log.info("\n@(" + agentType + ") " + username + " => strWebElementToString: " + strWebElementToString);
	      
	      
	      //log.info("\n@(" + agentType + ") " + username + " => strClassToString: " + strClassToString);
	      //log.info("\n@(" + agentType + ") " + username + " => strClassGetName: " + strClassGetName);
	      //log.info("\n@(" + agentType + ") " + username + " => strClassGetSimpleName : " + strClassGetSimpleName );
	      
	      log.info("\n@(" + agentType + ") " + username + " => ######### Ending AIC state ##########");
	      
	      

	        
	  }
	
}
	



