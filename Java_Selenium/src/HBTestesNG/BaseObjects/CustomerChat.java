package HBTestesNG.BaseObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.SessionNotFoundException;


//import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

public class CustomerChat extends Agent {
	
	protected String tenantPrefix, chatIRN, stOrMt;

	public CustomerChat() throws Exception{
		super();
	}
	
public boolean startChat() throws Exception {
	    String strName = "startChat";
		log.info("\n@(" + agentType + ") " +  username + " #### Start Chat####");
		Boolean working;
		working = true;
		WebElement webElement;
		webElement = null;
		
		maximizeBrowser();
		driver.get(AllActors.envIni.get("URL", "chatURL")); 
		
		//#### Precondition
		wait(3);
		if (stOrMt.contains("st")){
			webElement=conditonClickable_XPath("startChat_PreCondition", "chatCustomerNameTxtBox", 60);
			if(webElement != null){
				enterXPath(username, "chatCustomerNameTxtBox");
				click_XPath("chatConnectBtn");
				log.info("\n@(" + agentType + ") " +  username + " : Chat Started=> ST");
			}else{
				log.info("\n@(" + agentType + ") " +  username + " : Problem starting Chat=>ST");
				working = false;
			}
		}else{
			webElement=conditonClickable_XPath("startChat_PreCondition", "tenantPrefixTxtBox", 60);
			if(webElement != null){
				enterXPath(tenantPrefix, "tenantPrefixTxtBox");
				enterXPath(chatIRN, "chatIRNTxtBox");
				enterXPath(username, "chatCustomerNameTxtBox");
				click_XPath("chatConnectBtn");
				log.info("\n@(" + agentType + ") " +  username + " : Chat Started=> MT");
			}else{
				log.info("\n@(" + agentType + ") " +  username + " : Problem starting Chat=>MT");
				working = false;
			}
			
		}

		state = "busy";
		minimizeBrowser();
		return working;
	}
	
	
	
	public void startChatAndDisconnect() throws Exception {
	    
		log.info("\n@(" + agentType + ") " +  username + " #### Start->Talk->Disconnect Chat: MT ####");
		WebElement webElement;
		int ringTimeSec, talkTimeSec, wrapTimeSec;
		webElement = null;
		ringTimeSec = Integer.parseInt(AllActors.testDataIni.get("LOAD", "ringTimeSec"));
		talkTimeSec = Integer.parseInt(AllActors.testDataIni.get("LOAD", "talkTimeSec"));
		wrapTimeSec = Integer.parseInt(AllActors.testDataIni.get("LOAD", "wrapTimeSec"));
		
		try{
			maximizeBrowser();
			driver.get(AllActors.envIni.get("URL", "chatURL")); 
			
			//#### Precondition
			wait(3);
			if (stOrMt.contains("st")){
				webElement=conditonClickable_XPath("startChat_PreCondition", "usernameTxtBox", 60);
				if(webElement != null){
					enterXPath(username, "chatCustomerNameTxtBox");
					click_XPath("chatConnectBtn");
					log.info("\n@(" + agentType + ") " +  username + " : Chat Started=> ST");
				}else{
					log.info("\n@(" + agentType + ") " +  username + " : Problem starting Chat=>ST");
				}
			}else{
				webElement=conditonClickable_XPath("startChat_PreCondition", "tenantPrefixTxtBox", 60);
				if(webElement != null){
					enterXPath(tenantPrefix, "tenantPrefixTxtBox");
					enterXPath(chatIRN, "chatIRNTxtBox");
					enterXPath(username, "chatCustomerNameTxtBox");
					click_XPath("chatConnectBtn");
					log.info("\n@(" + agentType + ") " +  username + " : Chat Started=> MT");
				}else{
					log.info("\n@(" + agentType + ") " +  username + " : Problem starting Chat=>MT");
				}
				
			}
	
			state = "busy";
			wait(ringTimeSec+talkTimeSec+wrapTimeSec, "ChatRing-Talk-Disconnect");
			
			int i =0;
			while(true){
				if(waitUntilClickable("chatAgentFirstReply", 90) != null){
					log.info("\n@(" + agentType + ") " +  username + " : HB Agent replied.");
					break;
				}else{
					log.info("\n@(" + agentType + ") " +  username + " : HB Agent not replied...wait ");
				}	
				i++;
				wait(3, "wiat for an HB agent to reply");
				if (i > 5) {
					log.info("\n@(" + agentType + ") " +  username + " : Wait too long...Abandon chat");
					break;
				}
				
			}
			click_XPath("chatDisconnectBtn");


		}catch(InterruptedException e){
			  log.error("@ " + username + " : @@ Thread inturrepted -> throw again on startChatAndDisconnect()");
			  throw e;
		 }catch(SessionNotFoundException e){
			  log.error("@ " + username + " : @@ SessionNotFoundException -> throw again on startChatAndDisconnect()");
			  throw e;
		 }catch(Exception e){
			 log.error("\n@(" + agentType + ") " + username + " =>@@@@ Exception on startChatAndDisconnect() => " + e.toString());
			 throw e;
		 }
	}
	
	
	
	public void disconnectChat() throws Exception {
		String strFunctionName = "disconnectChat";
		try{
			maximizeBrowser();
			log.info("\n@(" + agentType + ") " +  username + " #### Disconnect a chat ####");
			click_XPath("chatDisconnectBtn");
			state = "idle";
			wait(1);
			minimizeBrowser();
		}catch(Exception e){
			 log.error("\n@(" + agentType + ") " + username + " =>@@@@ Exception on disconnectChat() => " + e.toString());
			 errorCount++;
			 errorString.concat("fail to " + strFunctionName + ";");
			 throw e;
		 }
	}
	
	public void disconnectChat_reset() throws Exception {
		String strFunctionName = "disconnectChat_reset";
		if (state.contains("idle")) return;
		try{
			maximizeBrowser();
			log.info("\n@(" + agentType + ") " +  username + " #### Disconnect a chat ####");
			click_XPath(("btnDisconnectChat"));
			state = "idle";
			wait(1);
			minimizeBrowser();
		}catch(Exception e){
			 log.error("\n@(" + agentType + ") " + username + " =>@@@@ Exception on disconnectChat_reset() => " + e.toString());
			 
			 errorCount++;
			 errorString.concat("fail to " + strFunctionName + ";");
			 throw e;
	 }
	}
	
	
	
	
	  public void tearDownAll() throws Exception {
		log.info("\n@(" + agentType + ") " +  username + " #### Close FireFox ####");
	    driver.quit();
	    currentTime();	    
	  }
	  
	  public void run(){
		  int waitSec;
		  waitSec = globalSec;

		  log.info("\n@(" + agentType + ") " +  username + ": Running => " +  threadName );
	      try {
	    	while (!this.isInterrupted()) 
			{
	    		startChatAndDisconnect();
				wait(waitSec);
			}
			}catch(InterruptedException e){
				log.info("\n@(" + agentType + ") " +  username + " : @@@@@@@@@ Thread inturrepted -> Leaving the thread. Bye! @@@@@@@@@@@");
			}catch(SessionNotFoundException e){
				  log.info("@ " + username + " : @@ SessionNotFoundException on run()  -> Leaving the thread. Bye! @@@@@@@@@@@");
				  throw e;
			 }catch (Exception e) {
				log.error("\n@(" + agentType + ") " +  username + " : Exception happens  -> Leaving the thread. Bye! @@@@@@@@@@@");
				//e.printStackTrace();
			}
		  
	  }
	
}
