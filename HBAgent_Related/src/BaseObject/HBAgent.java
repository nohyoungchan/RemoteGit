package BaseObject;


import java.io.BufferedReader;
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

public class HBAgent extends WebBrowser {

	protected String hbAgentURL;
	protected BufferedReader in;
	//protected AllMembers AllMembers;
	
	public HBAgent(String username, String password, String hbAgentURL) throws Exception {
		this.username = username;
		this.password = password;
		this.hbAgentURL = hbAgentURL;
		agentType = "WebAgent";
		log.info("\n@(" + username + ")  #### Initializing ####");
	}
	
	public HBAgent(String username, String password, String hbAgentURL, BufferedReader in) throws Exception {
		this.username = username;
		this.password = password;
		this.hbAgentURL = hbAgentURL;
		agentType = "WebAgent";
		this.in = in;
		log.info("\n@(" + username + ")  #### Initializing ####");
	}
	
	

	public HBAgent() throws Exception{
		super();
	}
	
/*	public void SetAllMembers(AllMembers all){
		AllMembers = all;
	}
	*/
	

		

	
	
	public boolean CheckStatus(String strStatus) throws Exception{
		// State can be: idle, busy, unavailable when released
	  	log.info("\n@(" + agentType + ") " +  username + " #### Check Status ####");

	  	try{
			  String txtReturned;
			  WebElement webElement;
	
			  webElement = driver.findElement(By.xpath(AllMembers.gHBAgentXPathHash.get("webAgentStatusBox")));	
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
				  webElement = driver.findElement(By.xpath(AllMembers.gHBAgentXPathHash.get("webAgentStatusBox")));	
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
				  
				 /* if (txtReturned.contains("status title-bg unavailable")) {
					  resumeAgent();
				  }else if (txtReturned.contains("status title-bg busy")){
					  
				  }else {
					  log.info("   \n@(" + agentType + ") " +  username + "Something wrong");
					  //driver.close();  
				  }*/
				  /*if (txtReturned.contains("status unavailable title-bg")){
					  log.info("   \n@(" + agentType + ") " +  username + " => Refreshing the page");
					  driver.navigate().refresh();
					  wait(5);
					  //popUpHandle_Close();
					  wait(5);
				  }*/
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

		driver.get(hbAgentURL); 

		//#### Action
		maximizeFireFox();

		
		//#### Precondition
		wait(3);

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
		////restoreDimenSion();
		return actionResult;
	}
	
	  public void logIntoGroup() throws Exception{
		  String txtReturned;
		  
		  log.info("\n@(" + agentType + ") " +  username + " #### Check if the agent needs to log in. ####");
		  txtReturned= driver.findElement(By.xpath(AllMembers.gHBAgentXPathHash.get("btnLogIntoGroup"))).getText();
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
			  maximizeFireFox();
			  String txtReturned;  
			  txtReturned= driver.findElement(By.xpath(AllMembers.gHBAgentXPathHash.get("btnLogIntoGroup"))).getText();
			  log.info("Ï am here at logout 2 => txt returned => "+ txtReturned);
			  if (!txtReturned.equalsIgnoreCase("Log into my queues")) //If agent is logged out, log in.
			  {
			      log.info("\n@(" + agentType + ") " +  username + " #### Agent is currently logged in=>Click Log out button ####");
			      click_XPath(("btnLogIntoGroup"));
			      wait(1);
			  }else{//if agent is already logged in, print status only
				  log.info("\n@(" + agentType + ") " +  username + " #### Agent is alreay logged out ####");
			  	//log.info("** Agent Status: " + txtReturned);
			  }
			  //restoreDimenSion();
		  }catch(NoSuchElementException e){
			  log.info("\n@(" + agentType + ") " +  username + "=> @@@@@ NoSuchElementException on logOutGroups(). It may be logout already.");
			  
		  }catch(Exception e){
			  log.info("\n@(" + agentType + ") " +  username + "=> @@@@@ exception on logOutGroups(). It may be loggout already.");
			  
			  
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
		
				  webElement = driver.findElement(By.xpath(AllMembers.gHBAgentXPathHash.get("btnResume")));	
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
				  resumeAgent();
			      wait(1);
			      click_XPath("btnReleaseCodeSelect");
			      wait(2);
			      click_XPath("secondReleaseCode");
				  
			}catch(InterruptedException e){
				//log.info("I am handling Interrupted Exceptionj=>"+ e.toString());
				throw e;
				//state = "weird";
			}
		  	catch(Exception e){
				log.info("I am handling General exception=>"+ e.toString());
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
				  resumeAgent();
			      wait(1);
			      click_XPath(("btnReleaseCodeSelect"));
			      wait(2);
			      click_XPath(("thirdReleaseCode"));
				  
			}catch(InterruptedException e){
				//log.info("I am handling Interrupted Exceptionj=>"+ e.toString());
				throw e;
				//state = "weird";
			}catch(Exception e){
				log.info("I am handling General exception=>"+ e.toString());
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
					throw e;
					//state = "weird";
				}
			  	catch(Exception e){
			  		log.error("\n@(" + agentType + ") " +  username + "I am handling General exception=> resumeAgent()");
					state = "weird";
					actionResult = false;
			  	}
			}else{
				log.info("\n@(" + agentType + ") " +  username +" Precondtion is not met=> resumeAgent()");
			  	actionResult = false;
			}
			
			return actionResult;
	  }
	  
		  
	  public synchronized void answerACDCall(int ringSec, int talkSec) throws InterruptedException{
		  log.info("\n@(" + agentType + ") " +  username + " #### Click Answer button ####");
		  try{
			  getCurrentDimenSion();
			  maximizeFireFox();
			  wait(ringSec, "Ring");

	
/*			  webElement = waitUntilClickable("btnResume", 60);	
			  txtReturned = webElement.getText();	  		  
			  //check if the agent is released.
			  if (txtReturned.contains("Start taking requests") || txtReturned.contains("Commencer à prendre les appels")) //If agent is idle, release.
			  {
			      log.info("\n@(" + agentType + ") " +  username+ " =>Agent is released=>Click resume button ####");
			      click_XPath("btnResume");
			      //wait(2);
			  }else{
			  	log.info("\n@(" + agentType + ") " +  username+ ": State=> Agent is idle ");
			  }*/
			  
			  //resumeAgent();
			  //wait(2);
			  
			  click_XPath("btnAnswer");
			  wait(talkSec, "Talk");
			  state = "busy";
			  //restoreDimenSion();
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
			  maximizeFireFox();
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
			  //restoreDimenSion();
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
			  maximizeFireFox();
			  wait(ringSec, "Ring");

			  resumeAgent();
			  //wait(2);
			  
			  click_XPath("btnAnswer");
			  wait(talkSec, "Talk");
			  state = "busy";
			  enterXPathAndEnter("Hello", "webAgentChatSendTxtBox");
			  //restoreDimenSion();
		  }catch(InterruptedException e){
			  log.error("@ " + username + " : @@ Thread inturrepted -> throw again on answerACDCall()");
			  throw e;
		 }catch(Exception e){
			log.info("\n@(" + agentType + ") " +  username + " exception on answering acd call" + e.toString() );
			state = "weird";
		  }
	  }
	  
	  
	  public void blindTransfer(String num){
		  log.info("\n@(" + agentType + ") " +  username + " #### Blind Transfer to =>" + num +" ####");
		  try{
			  getCurrentDimenSion();
			  maximizeFireFox();
			  wait(2);
			  log.info("\n@(" + agentType + ") " +  username + ": Clikc Transfer Button");
			  click_XPath(("btnTransfer"));
			  wait(2);
			  log.info("\n@(" + agentType + ") " +  username + ": Type the number: " + num);
			  typeElementXPath(AllMembers.gHBAgentXPathHash.get("txtBoxTransfer"), num);
			  click_XPath(("txtBoxConference"));
			  wait(2);		  
			  log.info("\n@(" + agentType + ") " +  username + ": Confirm blind transfer");
			  click_XPath(("btnBlindTransfer"));
			  state = "idle";
			  //restoreDimenSion();
			  //Assert.assertTrue(true, "Answered Correctly");
		  }catch(Exception e){
			  log.info("\n@(" + agentType + ") " +  username + " exception on blindTransfer" + e.toString());
			  state = "weird";
		  }
	  }
	  
	  public void consultTransfer(String num){
		  log.info("\n@(" + agentType + ") " +  username + " #### Consult Transfer to =>" + num +" ####");  
		  try{
			  getCurrentDimenSion();
			  //This is needed for chat transfer since without it, selenium cannot confirm transfer.
			  maximizeFireFox();
			  wait(2);
			  log.info("\n@(" + agentType + ") " +  username + ": Clikc Transfer Button");
			  click_XPath(("btnTransfer"));
			  wait(2);
			  log.info("\n@(" + agentType + ") " +  username + ": Type the number: " + num);
			  typeElementXPath(AllMembers.gHBAgentXPathHash.get("txtBoxTransfer"), num);
			  click_XPath(("txtBoxConference"));
			  wait(2);		  
			  log.info("\n@(" + agentType + ") " +  username + ": Press Consult transfer button");
			  click_XPath(("btnConsultTransfer"));
			  //restoreDimenSion();
		  }catch(Exception e){
			  log.info("\n@(" + agentType + ") " +  username + " exception on Consult Transfer" + e.toString());
			  state = "weird";
	  }
	  }
	  
	  public void confirmTransfer(){  	 
		  log.info("\n@(" + agentType + ") " +  username + ": Press Confirm button");
		  try{
			  wait(2);	  
			  click_XPath(("btnConsultTransferConfirm"));
			  state = "idle";
			  //Assert.assertTrue(true, "Answered Correctly");
		  }catch(Exception e){
			  log.info("\n@(" + agentType + ") " +  username + " exception on blindTransfer" + e.toString());
			  state = "weird";
		  }
	  }
	  
	  public void consultConference(String num){
		  log.info("\n@(" + agentType + ") " +  username + " #### Consult Conference to =>" + num +" ####");
		  try{
			  getCurrentDimenSion();
			  //This is needed for chat transfer since without it, selenium cannot confirm transfer.
			  maximizeFireFox();		  
			  wait(2);
			  log.info("\n@(" + agentType + ") " +  username + ": Clikc Conference Button");
			  click_XPath(("btnConference"));
			  wait(2);
			  log.info("\n@(" + agentType + ") " +  username + ": Type the number: " + num);
			  typeElementXPath(AllMembers.gHBAgentXPathHash.get("txtBoxConference"), num);
			  click_XPath(("txtBoxConference"));
			  wait(2);		  
			  log.info("\n@(" + agentType + ") " +  username + ": Press Consult Conference button");
			  click_XPath(("btnConsultConference"));
			  //restoreDimenSion();
		  }catch(Exception e){
			  log.info("\n@(" + agentType + ") " +  username + " exception on Consult Conference" + e.toString());
			  state = "weird";
		  }

	  }
	  
	  public void confirmConference(){		
		  log.info("\n@(" + agentType + ") " +  username + ": Press Confirm button");
		  try{
			  wait(2);	  
			  click_XPath(("btnConsultConferenceConfirm"));
			  //Assert.assertTrue(true, "Answered Correctly");
		  }catch(Exception e){
			  log.info("\n@(" + agentType + ") " +  username + " exception on Confirm Conference" + e.toString());
			  state = "weird";
		  }
	  }
	  
	  
	  public void disconnectByWebAgent() {
		  log.info("\n@(" + agentType + ") " +  username + " #### Disconnect by WebAgent ####");
		  boolean res;
		  res = true;
		  try{
			  getCurrentDimenSion();
			  maximizeFireFox();
			  click_XPath(("panelCurrentCall"));  //This is needed for a consult transferred call.
			  wait(2);
			  if (click_XPath(("btnDisconnect"))) {
				  log.info("\n@(" + agentType + ") " +  username + " disconnectByWebAgent() successfully"); 
			  }else {
				  log.info("\n@(" + agentType + ") " +  username + " doesn't need to disconnect"); 
			  };
			  state = "idle";
			  //restoreDimenSion();
		  }catch(Exception e){
			  log.info("\n@(" + agentType + ") " +  username + " exception on disconnectByWebAgent");
			  state = "weird";
		  }
	  }
	  
	  public void disconnectOACD(){
		  log.info("\n@(" + agentType + ") " +  username + " #### Disconnect by WebAgent : OACD call ####");
		  try{
			  getCurrentDimenSion();
			  maximizeFireFox();
			  click_XPath(("panelCurrentCall"));
			  wait(2);
			  click_XPath(("btnDisconnectOACD"));
			  wait(2); //The following is needed for successful OACD call.
			  click_XPath(("OACDDisconnectSuccessful"));
	 
			  state = "idle";
			  //restoreDimenSion();
		  
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
			  if (clickElementXPath_Reset(AllMembers.gHBAgentXPathHash.get("panelCurrentCall"))){
				  wait(2);
				  if (clickElementXPath_Reset(AllMembers.gHBAgentXPathHash.get("btnRetrieve"))){
					  wait(2);
					  clickElementXPath_Reset(AllMembers.gHBAgentXPathHash.get("btnDisconnect"));
				  }
				  state = "idle";
			  }
		  
		  }catch(Exception e){
			  log.info("\n@(" + agentType + ") " +  username + " exception on disconnectByWebAgent_Reset" + e.toString());
			  state = "weird";
		  }

	  }
	  
	  
	  public void wrapupEnd(){
		  
		  log.info("\n@(" + agentType + ") " +  username + " #### Wrapup End ####"); 
		  log.info("\n@(" + agentType + ") " +  username + " : Pressing Extend Wrapup button");
		  try{
			  click_XPath(("btnWrapExtend"));
			  log.info("\n@(" + agentType + ") " +  username + " : Pressing End Wrapup button");
			  click_XPath(("btnWrapupEnd"));
			  state = "idle";
			  //Assert.assertTrue(true, "Wrapup end Correctly");
			  //wait(10);
		  }catch(Exception e){
			  log.info("\n@(" + agentType + ") " +  username + " exception on wrapupEnd" + e.toString());
			  state = "weird";
		  }
	  }
	  
	  public void wrapupEnd(int wrapSec) {
		  log.info("\n@(" + agentType + ") " +  username + " #### Wrapup End ####");
		  log.info("\n@(" + agentType + ") " +  username + " : Pressing Extend Wrapup button");
		  try{
			  click_XPath(("btnWrapExtend"));
			  wait(wrapSec, "Wrapup time");
			  log.info("\n@(" + agentType + ") " +  username + " : Pressing End Wrapup button");
			  click_XPath(("btnWrapupEnd"));
			  state = "idle";
			  //Assert.assertTrue(true, "Wrapup end Correctly");
			  //wait(10);
		  }catch(Exception e){
			  log.info("\n@(" + agentType + ") " +  username + " exception on wrapupEnd" + e.toString());
			  state = "weird";
		  }
	  }
	  
	  public void wrapupEndWith2WrapupCodes(int wrapSec) {
		  log.info("\n@(" + agentType + ") " +  username + " #### Wrapup End ####");
		  log.info("\n@(" + agentType + ") " +  username + " : Pressing Extend Wrapup button");
		  
		  try{
			  click_XPath(("btnWrapExtend"));
			  //Todo : Enter wrapup Code
			  wait(1);
			  click_XPath(("comboBoxWrapupCode"));
			  wait(1);
			  click_XPath(("wrapupCodeFirst"));
			  wait(1);
			  click_XPath(("wrapupCodeSecond"));
			  wait(1);
			  click_XPath(("comboBoxWrapupCode"));
			  wait(wrapSec, "Wrapup time");
			  log.info("\n@(" + agentType + ") " +  username + " : Pressing End Wrapup button");
			  click_XPath(("btnWrapupEnd"));
			  state = "idle";
		  }catch(Exception e){
			  log.info("\n@(" + agentType + ") " +  username + " exception on wrapupEndWith2WrapupCodes" + e.toString());
			  state = "weird";
		  }

	  }
	  
	  public void signoutWebAgent(){
		  log.info("\n@(" + agentType + ") " +  username + " #### Signout ####");
		  
		  try{
			  getCurrentDimenSion();
			  maximizeFireFox();
			  click_XPath(("btnSignOutMenu"));
			  wait(2);
			  click_XPath(("btnSignOut"));
			  wait(5);
			  //restoreDimenSion();
		  }catch(Exception e){
			  log.info("\n@(" + agentType + ") " +  username + " exception on signoutWebAgent" + e.toString());
			  state = "weird";
		  }
	  }
	  
	  public void signoutWebAgentWhenNoCall(){
		  log.info("\n@(" + agentType + ") " +  username + " #### Signout When there is no call####");
		  
		  try{
			  //getCurrentDimenSion();
			  maximizeFireFox();
			  resumeAgent();
			  
			  String txtReturned;
			  WebElement webElement;
			  
			  
			  
			 while (true){
			  webElement = driver.findElement(By.xpath(AllMembers.gHBAgentXPathHash.get("webAgentStatusBox")));	
			  txtReturned = webElement.getAttribute("class"); 
			  wait(3);
			  log.info("   \n@(" + agentType + ") " +  username + ": Status=> " + txtReturned);
			  if (txtReturned.contains("idle")) break;
			 } 
			 
						 
			  click_XPath(("btnSignOutMenu"));
			  wait(2);
			  click_XPath(("btnSignOut"));
			  wait(5);
			  ////restoreDimenSion();
		  }catch(Exception e){
			  log.info("\n@(" + agentType + ") " +  username + " exception on signoutWebAgent : The agent is not normal signout condition");
			  state = "weird";
		  }
	  }
	  
	  public void closeHBAgent(){
			log.info("\n@(" + agentType + ") " +  username + " #### Close FireFox ####");
			//signoutWebAgent();
			try{
				//logOutGroups();
				driver.quit();
				currentTime();	  
			}catch(Exception e){
				log.info("\n@(" + agentType + ") " +  username + " exception on tearDownAllt" + e.toString());
				state = "weird";
			}
		  }
	  
	  
	  public void tearDownAll(){
		log.info("\n@(" + agentType + ") " +  username + " #### Logout and Close FireFox ####");
		//signoutWebAgent();
		try{
			logOutGroups();
			driver.quit();
			currentTime();	  
		}catch(Exception e){
			log.info("\n@(" + agentType + ") " +  username + " exception on tearDownAllt" + e.toString());
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
		  i=0;
		  waitSec = globalSec;
		  ringTimeSec = Integer.parseInt(AllMembers.gVariableHash.get("ringTimeSec"));
		  talkTimeSec = Integer.parseInt(AllMembers.gVariableHash.get("talkTimeSec"));
		  log.info("@" + username + ": Running => " +  threadName );
	      try {
	    	  

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
			         case "Load_VoiceACD_Answer_ByThread":
			        	 log.info("@" + username + ": Thread on=>Load_VoiceACD_Answer_ByThread");
			        	 //answerACDCall(1, 30);
			        	 //disconnectByWebAgent();
			        	 answerAndDisconnect(ringTimeSec, talkTimeSec);
			        	 //disconnectAndWrapupWith2Codes(5); //this is not good for load
			         	 break;
			         case "Load_Email_Send_ByThread":
			        	 log.info("@" + username + ": Thread on=>Load_Email_Send_ByThread");
			             break;
			         case "Load_Email_Answer_ByThread":
			        	 log.info("@" + username + ": Thread on=>Load_Email_Answer_ByThread");
			        	 answerAndDisconnect(ringTimeSec, talkTimeSec);
			        	 //disconnectAndWrapupWith2Codes(5); //this is not good for load
			         	 break;
			         case "Load_Email_SendAndAnswer_ByThread":
			        	 log.info("@" + username + ": Thread on=>Load_Email_SendAndAnswer_ByThread");
			        	 answerAndDisconnect(ringTimeSec, talkTimeSec);
			         	 break;
			         case "Load_Chat_SendAndAnswer_ByThread":
			        	 //ChatCustomer disconnects.
			        	 log.info("@" + username + ": Thread on=>Load_Chat_SendAndAnswer_ByThread");
			        	 answerChatAndType(ringTimeSec, talkTimeSec);
			         	 break;
			         default:
			             log.info("No matching load scenaro");
				   }
				wait(waitSec);
				if (i < 5) conditonClickable_XPath("logIntoWebAgent_PostCondition", "btnResume", 60);;
				log.info("@" + username + ": Running =>  " + i + "th times");
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
	        ////restoreDimenSion();
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
