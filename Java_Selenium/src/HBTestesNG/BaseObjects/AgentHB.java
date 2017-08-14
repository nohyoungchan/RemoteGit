package HBTestesNG.BaseObjects;

import java.util.Iterator;
import java.util.Set;

//import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.firefox.FirefoxDriver;
//import org.testng.Assert;
import org.openqa.selenium.firefox.FirefoxDriver;

//import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

public class AgentHB extends Agent {

/*	public AgentHB(String username, String password, String extension) throws Exception {
		super(username, password, extension);
		log.info("\n@(" + agentType + ") " +  username + " #### Initializing ####");
	}*/

	public AgentHB() throws Exception{
		super();
	}
	
		
	     /*   while(true)
        {
			try{
				wait(5);
				releaseAgent();
		  	    resumeAgent();
		  	    break;
			}catch(Exception e){
				log.info("\n@(" + agentType + ") " +  username + " #### loIntoWebAgent: Something wrong, so refresh ####");
				refreshPage();
			}
        }
  	  */

	
	
	public boolean CheckStatus(String strStatus) throws Exception{
		// State can be: idle, busy, unavailable when released
	  	log.info("\n@(" + agentType + ") " +  username + " #### Check Status ####");

	  	try{
			  String txtReturned;
			  WebElement webElement;
	
			  webElement = driver.findElement(By.xpath(AllActors.hbWebAgentXPathHash.get("webAgentStatusBox")));	
			  txtReturned = webElement.getText();
			  wait(2);
			  log.info("\n@(" + agentType + ") " +  username + ": Status=> " + txtReturned);
			  if (txtReturned.contains(strStatus)) //If agent is idle, release.
			  {
				  return true;
			  }else{
			  	return false;
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
				  webElement = driver.findElement(By.xpath(AllActors.hbWebAgentXPathHash.get("webAgentStatusBox")));	
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
  
	

	public boolean logIntoWebAgent() throws Exception {
	    
		log.info("\n@(" + agentType + ") " +  username + " #### Go to Login Page ####");
		WebElement webElement;
		boolean actionResult;
		webElement = null;
		actionResult = true;
		//getCurrentDimenSion();
		log.info("I am here: url is => " +  AllActors.envIni.get("URL", "agentHBURL"));
		driver.get(AllActors.envIni.get("URL", "agentHBURL")); 
		
		//#### Action
		maximizeBrowser();
		//
		
		//#### Precondition
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
			if(webElement != null) actionResult = true;

		}
		minimizeBrowser();
		//restoreDimenSion();
		return actionResult;
	}
	
	  public void logIntoGroup() throws Exception{
		  String txtReturned;
		  
		  log.info("\n@(" + agentType + ") " +  username + " #### Check if the agent needs to log in. ####");
		  txtReturned= driver.findElement(By.xpath(AllActors.hbWebAgentXPathHash.get("btnLogIntoGroup"))).getText();
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
			  getCurrentDimenSion();
			  maximizeBrowser();
			  String txtReturned;  
			  txtReturned= driver.findElement(By.xpath(AllActors.hbWebAgentXPathHash.get("btnLogIntoGroup"))).getText();
			  if (!txtReturned.equalsIgnoreCase("Log into my queues")) //If agent is logged out, log in.
			  {
			      log.info("\n@(" + agentType + ") " +  username + " #### Agent is currently logged in=>Click Log out button ####");
			      click_XPath(("btnLogIntoGroup"));
			      wait(1);
			  }else{//if agent is already logged in, print status only
				  log.info("\n@(" + agentType + ") " +  username + " #### Agent is alreay logged out ####");
			  	//log.info("** Agent Status: " + txtReturned);
			  }
			  restoreDimenSion();
		  }catch(NoSuchElementException e){
			  log.info("\n@(" + agentType + ") " +  username + "=> @@@@@ NoSuchElementException on logOutGroups()");
			  
		  }catch(Exception e){
			  log.info("\n@(" + agentType + ") " +  username + "=> @@@@@ exception on logOutGroups()");
			  
			  
		  }
	  }
	  
	  public void releaseAgent() throws Exception{
		  	log.info("\n@(" + agentType + ") " +  username + " #### Release agent. ####");
		  	if (state.contains("weird")) {
		  		log.info("@" + username + ": The state is $$$$ weird $$$$. So skip this step");
		  		return;
		  	}
		  	
		  	try{
				  String txtReturned;
				  WebElement webElement;
				  maximizeBrowser();
		
				  webElement = driver.findElement(By.xpath(AllActors.hbWebAgentXPathHash.get("btnResume")));	
				  txtReturned = webElement.getText();
				  wait(2);
				  
				  if (txtReturned.contains("Stop taking requests")) //If agent is idle, release.
				  {
				      log.info("\n@(" + agentType + ") " +  username + " #### Agent is idle=>Click release button ####");
				      click_XPath(("btnResume"));
				      wait(5);
				  }else{
				  	log.info("\n@(" + agentType + ") " +  username + ": State=> " + txtReturned);
				  }
				  minimizeBrowser();
			}catch(InterruptedException e){
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
	  
	  public void releaseAgentSecondCode() throws Exception{
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
			      minimizeBrowser();
				  
			}catch(InterruptedException e){
				//log.info("I am handling Interrupted Exceptionj=>"+ e.toString());
				errorCount++;
				throw e;
				//state = "weird";
			}
		  	catch(Exception e){
				log.info("I am handling General exception=>"+ e.toString());
				errorCount++;
				errorString.concat("fail to releaseAgentSecondCode;");
				state = "weird";
			}
	  }
	  
	  public void releaseAgentThirdCode() throws Exception{
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
			      minimizeBrowser();
				  
			}catch(InterruptedException e){
				//log.info("I am handling Interrupted Exceptionj=>"+ e.toString());
				errorCount++;
				throw e;
				//state = "weird";
			}catch(Exception e){
				log.info("I am handling General exception=>"+ e.toString());
				errorCount++;
				state = "weird";
			}
			      
	  }
	  
	  
	  
	  
	  public boolean resumeAgent() throws Exception{
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
					  wait(2);
					  
					  if (txtReturned.contains("Start taking requests") || txtReturned.contains("Commencer à prendre les appels")) //If agent is idle, release.
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
					errorCount++;
					errorString.concat("fail to resume;");
					throw e;
					//state = "weird";
				}
			  	catch(Exception e){
			  		log.error("\n@(" + agentType + ") " +  username + "I am handling General exception=> resumeAgent()");
					state = "weird";
					errorCount++;
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
			  minimizeBrowser();
			  //restoreDimenSion();
			  //Assert.assertTrue(true, "Answered Correctly");
		  }catch(InterruptedException e){
			  errorCount++;
			  log.error("@ " + username + " : @@ Thread inturrepted -> throw again on answerACDCall()");
			  throw e;
		 }catch(Exception e){
			 errorCount++;
			 errorString.concat("fail to " + strFunctionName + ";");
			log.info("\n@(" + agentType + ") " +  username + " exception on answering acd call" + e.toString() );
			state = "weird";
			throw e;
		  }
	  }
	  
	  public synchronized void answerEmail(int ringSec, int talkSec) throws InterruptedException{
		  String strFunctionName = "answerACDCall";
		  log.info("\n@(" + agentType + ") " +  username + " #### Click Answer button ####");
		  try{
			  //getCurrentDimenSion();
			  maximizeBrowser();
			  wait(ringSec, "Ring");
			  
			  click_XPath("btnAnswerEmail");
			  wait(talkSec, "Talk");
			  state = "busy";
			  minimizeBrowser();
			  //restoreDimenSion();
			  //Assert.assertTrue(true, "Answered Correctly");
		  }catch(InterruptedException e){
			  errorCount++;
			  log.error("@ " + username + " : @@ Thread inturrepted -> throw again on answerACDCall()");
			  throw e;
		 }catch(Exception e){
			 errorCount++;
			 errorString.concat("fail to " + strFunctionName + ";");
			log.info("\n@(" + agentType + ") " +  username + " exception on answering acd call" + e.toString() );
			state = "weird";
		  }
	  }
	  
	  public void hold(int secInt) throws InterruptedException{
		  log.info("\n@(" + agentType + ") " +  username + " #### Click hold button ####");
		  try{
			  getCurrentDimenSion();
			  maximizeBrowser();

			  
			  click_XPath("btnHold");
			  wait(secInt, "holding...");
			  state = "busy";
			  restoreDimenSion();
			  //Assert.assertTrue(true, "Answered Correctly");
		  }catch(InterruptedException e){
			  log.error("@ " + username + " : @@ Thread inturrepted -> throw again on answerACDCall()");
			  throw e;
		 }catch(Exception e){
			log.info("\n@(" + agentType + ") " +  username + " exception on answering acd call" + e.toString() );
			state = "weird";
		  }
	  }
	  
	  public void unHold(int secInt) throws InterruptedException{
		  log.info("\n@(" + agentType + ") " +  username + " #### Click unhold button ####");
		  try{
			  getCurrentDimenSion();
			  maximizeBrowser();

			  //unholding is using the same button which is "btnHold"
			  click_XPath("btnHold");
			  wait(secInt, "unholding...");
			  state = "busy";
			  restoreDimenSion();
			  //Assert.assertTrue(true, "Answered Correctly");
		  }catch(InterruptedException e){
			  log.error("@ " + username + " : @@ Thread inturrepted -> throw again on answerACDCall()");
			  throw e;
		 }catch(Exception e){
			log.info("\n@(" + agentType + ") " +  username + " exception on answering acd call" + e.toString() );
			state = "weird";
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
			  getCurrentDimenSion();
			  maximizeBrowser();
			  wait(ringSec, "Ring");

			  resumeAgent();
			  //wait(2);
			  
			  click_XPath("btnAnswer");
			  wait(talkSec, "Talk");
			  state = "busy";
			  enterXPathAndEnter("Hello", "webAgentChatSendTxtBox");
			  restoreDimenSion();
		  }catch(InterruptedException e){
			  log.error("@ " + username + " : @@ Thread inturrepted -> throw again on answerACDCall()");
			  throw e;
		 }catch(Exception e){
			log.info("\n@(" + agentType + ") " +  username + " exception on answering acd call" + e.toString() );
			state = "weird";
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
			  typeElementXPath(AllActors.hbWebAgentXPathHash.get("txtBoxTransfer"), num);
			  click_XPath(("txtBoxTransfer"));
			  wait(2);		  
			  log.info("\n@(" + agentType + ") " +  username + ": Confirm blind transfer");
			  click_XPath(("btnBlindTransfer"));
			  state = "idle";
			  minimizeBrowser();
			  //restoreDimenSion();
			  //Assert.assertTrue(true, "Answered Correctly");
		  }catch(Exception e){
			  log.info("\n@(" + agentType + ") " +  username + " exception on blindTransfer" + e.toString());
			  state = "weird";
		  }
	  }
	  
	  public void blindTransfer_byName(String name) throws Exception{
		  log.info("\n@(" + agentType + ") " +  username + " #### Blind Transfer by Name to =>" + name +" ####");
		  try{
			  getCurrentDimenSion();
			  maximizeBrowser();
			  wait(2);
			  log.info("\n@(" + agentType + ") " +  username + ": Clikc Transfer Button");
			  click_XPath(("btnTransfer"));
			  wait(2);
			  log.info("\n@(" + agentType + ") " +  username + ": Type the name: " + name);
			  typeElementXPath(AllActors.hbWebAgentXPathHash.get("txtBoxTransfer"), name);
			  //selectFromInput_ByPartialText(name);
			  wait(2);		  
			  click_XPath(("FirstlistFromTextBox"));
			  wait(1);
			  log.info("\n@(" + agentType + ") " +  username + ": Confirm blind transfer");
			  click_XPath(("btnBlindTransfer"));
			  state = "idle";
			  restoreDimenSion();
			  //Assert.assertTrue(true, "Answered Correctly");
		  }catch(Exception e){
			  log.info("\n@(" + agentType + ") " +  username + " exception on blindTransfer" + e.toString());
			  state = "weird";
			  throw e;
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
			  typeElementXPath(AllActors.hbWebAgentXPathHash.get("txtBoxTransfer"), num);
			  click_XPath(("txtBoxConference"));
			  wait(2);		  
			  log.info("\n@(" + agentType + ") " +  username + ": Press Consult transfer button");
			  click_XPath(("btnConsultTransfer"));
			  //restoreDimenSion();
		  }catch(Exception e){
			  log.info("\n@(" + agentType + ") " +  username + " exception on Consult Transfer" + e.toString());
			  state = "weird";
			  throw e;
		  }
	  }
	  
	  public void consultTransfer_byName(String name) throws Exception{
		  log.info("\n@(" + agentType + ") " +  username + " #### Consult Transfer to =>" + name+" ####");  
		  try{
			  getCurrentDimenSion();
			  //This is needed for chat transfer since without it, selenium cannot confirm transfer.
			  maximizeBrowser();
			  wait(2);
			  log.info("\n@(" + agentType + ") " +  username + ": Clikc Transfer Button");
			  click_XPath(("btnTransfer"));
			  wait(2);
			  log.info("\n@(" + agentType + ") " +  username + ": Type the number: " + name);
			  //typeElementXPath(AllActors.hbWebAgentXPathHash.get("txtBoxTransfer"), name);
			  //click_XPath(("txtBoxConference"));
			  selectFromInput_ByPartialText(name);
			  wait(2);		  
			  log.info("\n@(" + agentType + ") " +  username + ": Press Consult transfer button");
			  click_XPath(("btnConsultTransfer"));
			  restoreDimenSion();
		  }catch(Exception e){
			  log.info("\n@(" + agentType + ") " +  username + " exception on Consult Transfer" + e.toString());
			  state = "weird";
			  throw e;
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
			  //Assert.assertTrue(true, "Answered Correctly");
		  }catch(Exception e){
			  log.info("\n@(" + agentType + ") " +  username + " exception on blindTransfer" + e.toString());
			  state = "weird";
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
			  typeElementXPath(AllActors.hbWebAgentXPathHash.get("txtBoxConference"), num);
			  click_XPath(("txtBoxConference"));
			  wait(2);		  
			  log.info("\n@(" + agentType + ") " +  username + ": Press Consult Conference button");
			  click_XPath(("btnConsultConference"));
			  wait(2);
			  minimizeBrowser();
			  //restoreDimenSion();
		  }catch(Exception e){
			  log.info("\n@(" + agentType + ") " +  username + " exception on Consult Conference" + e.toString());
			  state = "weird";
		  }

	  }
	  
	public void consultConference_ByAgentName(String agentName) throws Exception{
		  log.info("\n@(" + agentType + ") " +  username + " #### Consult Conference to =>" + agentName +" ####");
		  try{
			  getCurrentDimenSion();
			  //This is needed for chat transfer since without it, selenium cannot confirm transfer.
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
			  restoreDimenSion();
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
			  minimizeBrowser();
		  }catch(Exception e){
			  log.info("\n@(" + agentType + ") " +  username + " exception on Confirm Conference" + e.toString());
			  state = "weird";
			  throw e;
		  }
	  }
	  
	  
	  public void disconnectByWebAgent() {
		  log.info("\n@(" + agentType + ") " +  username + " #### Disconnect by WebAgent ####");
		  boolean res;
		  res = true;
		  try{
			  //getCurrentDimenSion();
			  maximizeBrowser();
			  
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
			  //restoreDimenSion();
			  minimizeBrowser();
		  }catch(Exception e){
			  log.info("\n@(" + agentType + ") " +  username + " exception on disconnectByWebAgent");
			  state = "weird";
		  }
	  }
	  
	  public void disconnectChatByWebAgent() {
		  log.info("\n@(" + agentType + ") " +  username + " #### Disconnect Chat by WebAgent ####");
		  boolean res;
		  res = true;
		  try{
			  getCurrentDimenSion();
			  maximizeBrowser();
			  click_XPath(("panelCurrentChat"));  //This is needed for a consult transferred call.
			  wait(2);
			  if (click_XPath(("btnDisconnect"))) {
				  log.info("\n@(" + agentType + ") " +  username + " disconnectChatByWebAgent() successfully"); 
			  }else {
				  log.info("\n@(" + agentType + ") " +  username + " doesn't need to disconnect"); 
			  };
			  state = "idle";
			  restoreDimenSion();
		  }catch(Exception e){
			  log.info("\n@(" + agentType + ") " +  username + " exception on disconnectChatByWebAgent");
			  state = "weird";
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
	 
			  state = "idle";
			  //restoreDimenSion();
			  minimizeBrowser();
		  
		  }catch(Exception e){
			  log.info("\n@(" + agentType + ") " +  username + " exception on disconnectOACD");
			  state = "weird";
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
			  if (clickElementXPath_Reset(AllActors.hbWebAgentXPathHash.get("panelCurrentCall"))){
				  wait(2);
				  if (clickElementXPath_Reset(AllActors.hbWebAgentXPathHash.get("btnRetrieve"))){
					  wait(2);
					  clickElementXPath_Reset(AllActors.hbWebAgentXPathHash.get("btnDisconnect"));
				  }
				  state = "idle";
			  }
		  
		  }catch(Exception e){
			  log.info("\n@(" + agentType + ") " +  username + " exception on disconnectByWebAgent_Reset" + e.toString());
			  state = "weird";
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
			  if (!click_XPath("btnWrapExtend")){
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
			  errorCount++;
			  errorString.concat("fail to " + strFunctionName + ";");
			  state = "weird";
		  }finally {
				minimizeBrowser();
		  }

	  }
	  
	  public void signoutWebAgent(){
		  log.info("\n@(" + agentType + ") " +  username + " #### Signout ####");
		  String strFunctionName = "signoutWebAgent";
		  
		  try{
			  getCurrentDimenSion();
			  maximizeBrowser();
			  click_XPath(("btnSignOutMenu"));
			  wait(2);
			  click_XPath(("btnSignOut"));
			  wait(5);
			  restoreDimenSion();
		  }catch(Exception e){
			  log.info("\n@(" + agentType + ") " +  username + " exception on signoutWebAgent" + e.toString());
			  errorCount++;
			  errorString.concat("fail to " + strFunctionName + ";");
			  state = "weird";
		  }
	  }
	  
	  public void signoutWebAgentWhenNoCall(){
		  log.info("\n@(" + agentType + ") " +  username + " #### Signout When there is no call####");
		  
		  try{
			  getCurrentDimenSion();
			  maximizeBrowser();
			  resumeAgent();
			  
			  String txtReturned;
			  WebElement webElement;
			  
			 while (true){
			  webElement = driver.findElement(By.xpath(AllActors.hbWebAgentXPathHash.get("webAgentStatusBox")));	
			  txtReturned = webElement.getAttribute("class"); 
			  wait(3);
			  log.info("   \n@(" + agentType + ") " +  username + ": Status=> " + txtReturned);
			  if (txtReturned.contains("idle")) break;
			 } 
			 
			  click_XPath(("btnSignOutMenu"));
			  wait(2);
			  click_XPath(("btnSignOut"));
			  wait(5);
			  restoreDimenSion();
		  }catch(Exception e){
			  log.info("\n@(" + agentType + ") " +  username + " exception on signoutWebAgent" + e.toString());
			  state = "weird";
		  }
	  }
	  
	  
	  
	  public void tearDownAll(){
		log.info("\n@(" + agentType + ") " +  username + " #### Logout and Close FireFox ####");
		//signoutWebAgent();
		try{
			logOutGroups();
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
	
}


/*				synchronized(s_exitSignal) {
	try {
		s_exitSignal.wait(2);
		log.info("I am here $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}*/
