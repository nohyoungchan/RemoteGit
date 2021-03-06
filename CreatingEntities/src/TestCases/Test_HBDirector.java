package TestCases;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;

import Actors.*;

@Test(groups= {"CCDirector_ClassLevel"})
public class Test_HBDirector extends TestCaseBaseObject {
	private HBDirector ccd2;
	private AllEntities allEntities;
	

	Test_HBDirector() throws Exception{
		log.info("* Construction: Test_HBDirector");
		//ccd2 = AllEntities.CCD;

	}

	@BeforeClass
	public void beforeClass() throws Exception {
		log.info("* Before Class: Test_HBDirector");
		//InitializeAllVariables(); 
		ccd2 = AllEntities.CCD;
    }
	
	@AfterClass
	public void afterClass() throws Exception {
		log.info("* After Class: Test_HBDirector");
	}
	
	public void CCDirector_CreateAgents() throws Exception {
		String testName = "CCDirector->CreateAgents";
		startTestCase(testName);
		CCAgent ccAgent;
		int i, j;
		
		try{
			ccd2.logIntoHBDirector();
			wait(5);
			ccd2.openHBDirectorPage("agent");
			ccd2.waitUntilMainTitle("Agents");


			
			for (i = 0 ; i < AllEntities.ccAgents.size(); i++){
				j = i+1;
				ccAgent = AllEntities.ccAgents.get(i);

				log.info("\n###### Creating : " + ccAgent.agentName + " ########");

				ccd2.clickXPath(AllEntities.gCCDXPathHash.get("agentNewBtn"));
				ccd2.typeElementXPath(AllEntities.gCCDXPathHash.get("agentNameTxtBox"), ccAgent.agentName);
				ccd2.typeElementXPath(AllEntities.gCCDXPathHash.get("agentUserNameTxtBox"), ccAgent.agentUserName); 
				ccd2.typeElementXPath(AllEntities.gCCDXPathHash.get("agentIDTxtBox"), ccAgent.agentID);
				ccd2.typeElementXPath(AllEntities.gCCDXPathHash.get("agentExtenstionTxtBox"), ccAgent.agenExtension);
				ccd2.selectFromComboBox(AllEntities.gCCDXPathHash.get("agentCOS"), ccAgent.agentCOS);
				ccd2.typeElementXPath(AllEntities.gCCDXPathHash.get("agentCorporateEmailAddress"), ccAgent.agentEmailAddress);
				wait(2);
				//ccd2.clickXPath(AllEntities.gCCDXPathHash.get("agentAutoAnswerFlag"));
				ccd2.clickXPath(AllEntities.gCCDXPathHash.get("agentCreateBtn"));
				wait(2);
				//ccd2.createOneAgent(agentName, agentUserName, agentID, agentExtension, agentEmail);
			}
			//ccd2.logOutHBDirector();;
	
			//##########################

			
			//############################

		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
		}finally{
			endTestCase(testName);
		}
	}
	
	
	public void CCDirector_CreateAgents_Fortuna() throws Exception {
		String testName = "CCDirector->CreateAgents";
		startTestCase(testName);
		CCAgent ccAgent;
		int i, j;
		
		try{
			ccd2.logIntoHBDirector();
			wait(5);
			ccd2.openHBDirectorPage("agent");
			ccd2.waitUntilMainTitle("Agents");


			
			for (i = 0 ; i < AllEntities.ccAgents.size(); i++){
				j = i+1;
				ccAgent = AllEntities.ccAgents.get(i);

				log.info("\n###### Creating : " + ccAgent.agentName + " ########");

				ccd2.clickXPath(AllEntities.gCCDXPathHash.get("agentNewBtn"));
				ccd2.typeElementXPath(AllEntities.gCCDXPathHash.get("agentNameTxtBox"), ccAgent.agentName);
				ccd2.typeElementXPath(AllEntities.gCCDXPathHash.get("agentIDTxtBox"), ccAgent.agentID);
				//TODO:  This field is lackig in Fortuna
				//ccd2.typeElementXPath(AllEntities.gCCDXPathHash.get("agentPasswordTxtBox"), ccAgent.agentPassword);
				ccd2.selectFromComboBox(AllEntities.gCCDXPathHash.get("agentCOS"), ccAgent.agentCOS);
				ccd2.typeElementXPath(AllEntities.gCCDXPathHash.get("agentCorporateEmailAddress"), ccAgent.agentEmailAddress);
				wait(2);
				//ccd2.clickXPath(AllEntities.gCCDXPathHash.get("agentAutoAnswerFlag"));
				ccd2.clickXPath(AllEntities.gCCDXPathHash.get("agentCreateBtn"));
				wait(2);
				//ccd2.createOneAgent(agentName, agentUserName, agentID, agentExtension, agentEmail);
			}
			//ccd2.logOutHBDirector();;
	
			//##########################

			
			//############################

		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
		}finally{
			endTestCase(testName);
		}
	}
	
	//only 1 ccdirector is needed, so update "testData.ini"
	public void CCDirector_UpdateAgents() throws Exception {
		String testName = "CCDirector->UpdateAgents";
		startTestCase(testName);
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
			ccd2.logIntoHBDirector();
			wait(5);
			ccd2.openHBDirectorPage("agent");
			ccd2.waitUntilMainTitle("Agents");
			
			for (int i= 0; i < totNumAgentsToCreate; i++){
				j = i+1;
				agentName = agentPrefix+j;
				agentUserName = agentPrefix+j+"@"+agentDomain;
				agentID=Integer.toString(idExtStartNum+j);
				agentExtension=Integer.toString(idExtStartNum+j);
				agentEmail=agentEmailPrefix+j+agentEmailDomain;
				
				log.info("\n###### Updating : " + agentEmail + " ########");

				
				ccd2.typeElementXPath(AllEntities.gCCDXPathHash.get("searchBox"), agentUserName); 
				//ccd2.clickXPath(AllEntities.gCCDXPathHash.get("agentNewBtn"));
				//ccd2.typeElementXPath(AllEntities.gCCDXPathHash.get("agentNameTxtBox"), agentName);
				//ccd2.typeElementXPath(AllEntities.gCCDXPathHash.get("agentUserNameTxtBox"), agentUserName); 
				//ccd2.typeElementXPath(AllEntities.gCCDXPathHash.get("agentIDTxtBox"), agentID);
				//ccd2.typeElementXPath(AllEntities.gCCDXPathHash.get("agentExtenstionTxtBox"), agentExtension);
				//ccd2.selectFromComboBox(AllEntities.gCCDXPathHash.get("agentCOS"), "LOAD_COS");
				ccd2.typeElementXPath(AllEntities.gCCDXPathHash.get("agentCorporateEmailAddress"), agentEmail);
				
				wait(1);
				ccd2.clickXPath(AllEntities.gCCDXPathHash.get("submitButton"));
				wait(2);
			}
			ccd2.logOutHBDirector();;
	
			//##########################

			
			//############################

		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
	}
	
	public void CCDirector_CreateSupervisors() throws Exception {
		String testName = "CCDirector->CreateSupervisors";
		startTestCase(testName);
		CCSupervisor ccSup;
		int i, j;
		
		try{
			
			if (ccd2 == null) log.info("ccd2 is null");
			ccd2.logIntoHBDirector();
			log.info("I am here with ou 1");
			wait(5);
			ccd2.openHBDirectorPage("supervisor");
			log.info("I am here with ou 2");
			ccd2.waitUntilMainTitle("Supervisors");


			
			for (i = 0 ; i < AllEntities.ccSupers.size(); i++){
				j = i+1;
				ccSup = AllEntities.ccSupers.get(i);
				log.info("\n###### Creating : " + ccSup.supName + " ########");

				ccd2.clickXPath(AllEntities.gCCDXPathHash.get("agentNewBtn"));
				ccd2.typeElementXPath(AllEntities.gCCDXPathHash.get("supName"), ccSup.supName);
				ccd2.typeElementXPath(AllEntities.gCCDXPathHash.get("supUsername"), ccSup.supUserName); 
				ccd2.selectFromComboBox(AllEntities.gCCDXPathHash.get("supCOS"), ccSup.supCOS);
				ccd2.selectFromComboBox(AllEntities.gCCDXPathHash.get("supAgentName"), ccSup.supAgentName);
				ccd2.clickXPath(AllEntities.gCCDXPathHash.get("supPermission_SupAdmin"));
				wait(2);
				ccd2.clickXPath(AllEntities.gCCDXPathHash.get("createBtn"));
				wait(2);
			}
			//ccd2.logOutHBDirector();;
	
			//##########################

			
			//############################

		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
		}finally{
			endTestCase(testName);
		}
	}
	
	

}
