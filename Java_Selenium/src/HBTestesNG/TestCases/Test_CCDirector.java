package HBTestesNG.TestCases;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;

import HBTestesNG.BaseObjects.*;import org.testng.SkipException;

@Test(groups= {"CCDirector_ClassLevel"})
public class Test_CCDirector extends TestCaseObject {

	Test_CCDirector() throws Exception{
		log.info("* Construction: Test_CCDirector");

	}

	@BeforeClass
	public void beforeClass() throws Exception {
		log.info("* Before Class: Test_CCDirector");
		InitializeAllVariables(); 
    }
	
	@AfterClass
	public void afterClass() throws Exception {
		log.info("* After Class: Test_CCDirector");
	}
	
	public void CCDirector_CreateAgents() throws Exception {
		//######################
				String testReady = "no";
				if (testReady.contains("no")) throw new SkipException("Skipping because it is not ready");
				
		
		String testName = "CCDirector->CreateAgents";
		if(startTestCase(testName).contains("no")) skipTest("Skipping because user want to end test => " + testName);
		
		String agentPrefix, agentDomain, agentName, agentUserName, agentID, agentExtension;
		String agentEmail, agentEmailPrefix, agentEmailDomain;
		String agentCOS;
		
		int totNumAgentsToCreate, j, idExtStartNum;
		int startingNum;
		
		// ### this is used when agent name has leading 0s ###
		//agentPrefix = "Agt_";
		//agentDomain = "@LT1.com";
		//agentCOS="C1H1_COS1";
		
		//agentEmailPrefix = "hbagent";
		//agentEmailDomain = "@qa.shoretel.com";
		//#######################################################
		
		//### This is used when agent name has no leading 0s ###
		agentPrefix = "eagent";
		agentDomain = "@loadtest1.com";
		agentCOS="LOAD_COS";
		
		agentEmailPrefix = "agent";
		agentEmailDomain = "@loadtest1.com";
		//#######################################################
		
		try{
			sup1.logIntoHBDirector();
			wait(5);
			sup1.openHBDirectorPage("agent");
			sup1.waitUntilMainTitle("Agents");
			
			totNumAgentsToCreate = 199;
			startingNum =0;
			idExtStartNum=400; //This is for agent ID and extension
			
			for (int i = startingNum ; i < totNumAgentsToCreate; i++){
				j = i+1;
				
				// ### this is used when agent name has leading 0s ###
				//agentName = agentPrefix + intToStringWithLeadingZero(3, j);
				//agentUserName = agentName+agentDomain;
				
				// ### this is used when agent name has no leading 0s ###
				agentName = agentPrefix+j;
				agentUserName = agentPrefix+j+agentDomain;

				agentID=Integer.toString(idExtStartNum+j);
				agentExtension=Integer.toString(idExtStartNum+j);
				agentEmail=agentEmailPrefix+j+agentEmailDomain;
				
				log.info("\n###### Creating : " + agentEmail + " ########");

				sup1.clickXPath(AllActors.hbDirectorXPathHash.get("agentNewBtn"));
				sup1.typeElementXPath(AllActors.hbDirectorXPathHash.get("agentNameTxtBox"), agentName);
				sup1.typeElementXPath(AllActors.hbDirectorXPathHash.get("agentUserNameTxtBox"), agentUserName); 
				sup1.typeElementXPath(AllActors.hbDirectorXPathHash.get("agentIDTxtBox"), agentID);
				sup1.typeElementXPath(AllActors.hbDirectorXPathHash.get("agentExtenstionTxtBox"), agentExtension);
				sup1.selectFromComboBox(AllActors.hbDirectorXPathHash.get("agentCOS"), agentCOS);
				sup1.typeElementXPath(AllActors.hbDirectorXPathHash.get("agentCorporateEmailAddress"), agentEmail);
				sup1.clickXPath(AllActors.hbDirectorXPathHash.get("agentAutoAnswerFlag"));
				sup1.clickXPath(AllActors.hbDirectorXPathHash.get("agentCreateBtn"));
				wait(2);
				//sup1.createOneAgent(agentName, agentUserName, agentID, agentExtension, agentEmail);
			}
			sup1.logOutHBDirector();;
	
			//##########################

			
			//############################

		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
	}
	
	public void CCDirector_CreateAgents_UsingINI() throws Exception {
		//######################
				String testReady = "no";
				if (testReady.contains("no")) throw new SkipException("Skipping because it is not ready");
				
		String testName = "CCDirector->CreateAgents using ini file";
		if(startTestCase(testName).contains("no")) skipTest("Skipping because user want to end test => " + testName);
		
		String agentPrefix, agentDomain, agentName, agentUserName, agentID, agentExtension;
		String agentEmail, agentEmailPrefix, agentEmailDomain;
		String agentCOS;
		String agentNameWithLeadingZero;
		int fixedWidth;
		
		int totNumAgentsToCreate, j, idExtStartNum;
		int startingNum;
		
		
		//if fixedWidth is 4,  agent0001, if fixedWidth is 5, agent00001
		agentNameWithLeadingZero = AllActors.hbDirectorAgentCreateHash.get("agentNameWithLeadingZero");
		fixedWidth = Integer.parseInt(AllActors.hbDirectorAgentCreateHash.get("fixedWidth"));

		//### This is used when agent name has no leading 0s ###
		agentPrefix = AllActors.hbDirectorAgentCreateHash.get("agentPrefix");
		agentDomain = AllActors.hbDirectorAgentCreateHash.get("agentDomain");
		agentCOS=AllActors.hbDirectorAgentCreateHash.get("agentCOS");
		
		agentEmailPrefix = AllActors.hbDirectorAgentCreateHash.get("agentEmailPrefix");
		agentEmailDomain = AllActors.hbDirectorAgentCreateHash.get("agentEmailDomain");
		//#######################################################
		
		try{
			sup1.logIntoHBDirector();
			wait(5);
			sup1.openHBDirectorPage("agent");
			sup1.waitUntilMainTitle("Agents");
			
			totNumAgentsToCreate = Integer.parseInt(AllActors.hbDirectorAgentCreateHash.get("totNumAgentsToCreate"));
			startingNum =Integer.parseInt(AllActors.hbDirectorAgentCreateHash.get("startingNum"));
			idExtStartNum=Integer.parseInt(AllActors.hbDirectorAgentCreateHash.get("idExtStartNum")); //This is for agent ID and extension
			
			for (int i = startingNum ; i < totNumAgentsToCreate; i++){
				j = i+1;
				
				// ### this is used when agent name has leading 0s with fixedwidth ###
				if( agentNameWithLeadingZero.contains("yes")){
					agentName = agentPrefix + intToStringWithLeadingZero(fixedWidth, j);
					agentUserName = agentName+agentDomain;
				}else{	
					// ### this is used when agent name has no leading 0s ###
					agentName = agentPrefix+j;
					agentUserName = agentPrefix+j+agentDomain;
				}

				agentID=Integer.toString(idExtStartNum+j);
				agentExtension=Integer.toString(idExtStartNum+j);
				
				// ### this is used when agent name has leading 0s with fixedwidth ###
				if( agentNameWithLeadingZero.contains("yes")){
					agentEmail = agentEmailPrefix + intToStringWithLeadingZero(fixedWidth, j);
					agentEmail = agentEmail+agentEmailDomain;
				}else{	
					// ### this is used when agent name has no leading 0s ###
					agentEmail=agentEmailPrefix+j+agentEmailDomain;
				}
				//agentEmail=agentEmailPrefix+j+agentEmailDomain;
				
				log.info("\n###### Creating : " + agentEmail + " ########");

				sup1.clickXPath(AllActors.hbDirectorXPathHash.get("agentNewBtn"));
				sup1.typeElementXPath(AllActors.hbDirectorXPathHash.get("agentNameTxtBox"), agentName);
				sup1.typeElementXPath(AllActors.hbDirectorXPathHash.get("agentUserNameTxtBox"), agentUserName); 
				sup1.typeElementXPath(AllActors.hbDirectorXPathHash.get("agentIDTxtBox"), agentID);
				sup1.typeElementXPath(AllActors.hbDirectorXPathHash.get("agentExtenstionTxtBox"), agentExtension);
				sup1.selectFromComboBox(AllActors.hbDirectorXPathHash.get("agentCOS"), agentCOS);
				sup1.typeElementXPath(AllActors.hbDirectorXPathHash.get("agentCorporateEmailAddress"), agentEmail);
				sup1.clickXPath(AllActors.hbDirectorXPathHash.get("agentAutoAnswerFlag"));
				sup1.clickXPath(AllActors.hbDirectorXPathHash.get("agentCreateBtn"));
				wait(2);
				//sup1.createOneAgent(agentName, agentUserName, agentID, agentExtension, agentEmail);
			}
			sup1.logOutHBDirector();;
	
			//##########################

			
			//############################

		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
	}
	
	
	
	//only 1 ccdirector is needed, so update "testData.ini"
	public void CCDirector_UpdateAgents() throws Exception {
		//######################
				String testReady = "no";
				if (testReady.contains("no")) throw new SkipException("Skipping because it is not ready");
				
		String testName = "CCDirector->UpdateAgents";
		if(startTestCase(testName).contains("no")) skipTest("Skipping because user want to end test => " + testName);
		int totNumAgentsToCreate, j, idExtStartNum;
		String agentPrefix, agentDomain, agentName, agentUserName, agentID, agentExtension;
		String agentEmail, agentEmailPrefix, agentEmailDomain;
		totNumAgentsToCreate = 50;
		idExtStartNum=200;
		agentPrefix = "agent";
		agentDomain = "loadtest1.com";
		agentEmailPrefix = "hbagent";
		agentEmailDomain = "@qa.shoretel.com";
		
		
		try{
			sup1.logIntoHBDirector();
			wait(5);
			sup1.openHBDirectorPage("agent");
			sup1.waitUntilMainTitle("Agents");
			
			for (int i= 0; i < totNumAgentsToCreate; i++){
				j = i+1;
				agentName = agentPrefix+j;
				agentUserName = agentPrefix+j+"@"+agentDomain;
				agentID=Integer.toString(idExtStartNum+j);
				agentExtension=Integer.toString(idExtStartNum+j);
				agentEmail=agentEmailPrefix+j+agentEmailDomain;
				
				log.info("\n###### Updating : " + agentEmail + " ########");

				
				sup1.typeElementXPath(AllActors.hbDirectorXPathHash.get("searchBox"), agentUserName); 
				//sup1.clickXPath(AllActors.hbDirectorXPathHash.get("agentNewBtn"));
				//sup1.typeElementXPath(AllActors.hbDirectorXPathHash.get("agentNameTxtBox"), agentName);
				//sup1.typeElementXPath(AllActors.hbDirectorXPathHash.get("agentUserNameTxtBox"), agentUserName); 
				//sup1.typeElementXPath(AllActors.hbDirectorXPathHash.get("agentIDTxtBox"), agentID);
				//sup1.typeElementXPath(AllActors.hbDirectorXPathHash.get("agentExtenstionTxtBox"), agentExtension);
				//sup1.selectFromComboBox(AllActors.hbDirectorXPathHash.get("agentCOS"), "LOAD_COS");
				sup1.typeElementXPath(AllActors.hbDirectorXPathHash.get("agentCorporateEmailAddress"), agentEmail);
				
				wait(1);
				sup1.clickXPath(AllActors.hbDirectorXPathHash.get("submitButton"));
				wait(2);
			}
			sup1.logOutHBDirector();;
	
			//##########################

			
			//############################

		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
	}
	
	
	public void CCDirector_CopyIRNs() throws Exception {
		String testName = "CCDirector->CreateAgents";
		//######################
		String testReady = "no";
		if (testReady.contains("no")) throw new SkipException("Skipping because it is not ready");
		
		
		//The following value is entered while running scenarios to stop all
		if(startTestCase(testName).contains("no")) skipTest("Skipping because user want to end test => " + testName);
		int numberOfIRNs, j, startingNumber;
		String irnNumber;
		numberOfIRNs = 1500;
		startingNumber=500;

		
		
		try{
			sup1.logIntoHBDirector();
			sup1.waitUntilMainTitle("Agents");
			wait(5);
			sup1.openHBDirectorPage("irn");
			sup1.waitUntilMainTitle("IRN");
			
			for (int i= 0; i < numberOfIRNs; i++){
				irnNumber= Integer.toString(startingNumber+i+1);
				
			
				log.info("\n###### Creating : IRN number => " + irnNumber + " ########");

				sup1.click_XPath("copyBtn");
				sup1.enterXPath(irnNumber, "irnNameTxtBox");
				sup1.click_XPath("createBtn");

				wait(1);
				
			}
			sup1.logOutHBDirector();;
	
			//##########################

			
			//############################

		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
	}
	
	
	public void CCDirector_Service() throws Exception {
		String testName = "CCDirector_Service";
		//######################
		String testReady = "yes";
		if (testReady.contains("no")) throw new SkipException("Skipping because it is not ready");
		
		
		//The following value is entered while running scenarios to stop all
		if(startTestCase(testName).contains("no")) skipTest("Skipping because user want to end test => " + testName);
		
		
		
		try{

			//sup1.Max_LogIn_changeWrapAndFRTime_LogOut_Min(aa.services.get(0).name, "20", "30");
			//sup1.Max_LogIn_EnableAbandon_LogOut_Min(aa.services.get(0).name, "50");
			//sup1.Max_LogIn_DisableAbandon_LogOut_Min(aa.services.get(0).name);
			//sup1.Max_LogIn_ChangeWrapFR_EnableAbandon_SetOutboundPreferenceForAbandonLogOut_Min(aa.services.get(0).name, "20", "40", "10");
			//sup1.logOutHBDirector();
			//### The following is for OACD
			//sup1.Max_LogIn_ChangeWrapFR_EnableAbandon_SetOutboundPreferenceForAbandonLogOut_Min(aa.services.get(0).name, "20", "40", "10");
			//sup1.Max_LogIn_ChangeWrapFR_DisableAbandon_LogOut_Min(aa.services.get(0).name, "20", "40");
			
			//sup1.AllType_Max_LogIn_changeWrapAndFRTime_LogOut_Min("emailservice", aa.services.get(3).name, "0", "40");
			sup1.Max_LogIn_PrepareTest_LogOut_Min();
			

		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
	}
	

	


}
