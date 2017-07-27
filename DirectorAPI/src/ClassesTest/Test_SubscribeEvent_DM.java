package ClassesTest;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import ClassesEntity.*;


@Test(groups= {"SubscribeEvent"})
public class Test_SubscribeEvent_DM extends TestCaseObject {
	String strPollingResult, supID, strEntity, strParsedResult;
	
	Test_SubscribeEvent_DM() throws Exception{
		log.info("* Constructor: Test_SubscribeEvent_DM");

	}

	@BeforeClass
	public void beforeClass() throws Exception {
		
		startTestClassMessage("Test_SubscribeEvent_DM Class");
		strCertificate = apiTesterClient.logIn(hbAdmin);
		supID = hbAdmin.supervisorID;
		//Clear all Hash table which is used to accumulate poll result and poll processed result
		pollResultsHash.clear();
		pollResultPorcessedHash.clear();
    }
	
	
	public void Test_SubscribeEvent_DM_Agents() throws Exception {
		String strEntity, strEntityDetail;
		strEntity = "dm-agents";
		strEntityDetail = "*";

		
		//### This returns a string with all agents
		strPollingResult = Test_SubscribeEvent_DMs(supID, strEntity, strEntityDetail);
		//### This analyzes the above string.
		strParsedResult = jMessageBuilder.jasonParsor_DM_Agents(strPollingResult);
		pollResultPorcessedHash.put(strEntity, strParsedResult);
		
		
	}
	
	public void Test_SubscribeEvent_DM_Supervisors() throws Exception {
		String strEntity, strEntityDetail;
		strEntity = "dm-sm-supervisors";
		strEntityDetail = "*";
		
		//### This returns a string with all agents
		strPollingResult = Test_SubscribeEvent_DMs(supID, strEntity, strEntityDetail);
		//### This analyzes the above string.
		strParsedResult = jMessageBuilder.jasonParsor_DM_Supervisors(strPollingResult);
		pollResultPorcessedHash.put(strEntity, strParsedResult);
		
		
	}

	
	public void Test_SubscribeEvent_DM_IVRPorts() throws Exception {
		String strEntity, strEntityDetail;
		strEntity = "dm-ivr-ports";
		strEntityDetail = "*";
		
		//### This returns a string with all agents
		strPollingResult = Test_SubscribeEvent_DMs(supID, strEntity, strEntityDetail);
		//### This analyzes the above string.
		strParsedResult = jMessageBuilder.jasonParsor_DM_IVRPorts(strPollingResult);
		pollResultPorcessedHash.put(strEntity, strParsedResult);
		
		
	}
	

	public void Test_SubscribeEvent_DM_EcEmails() throws Exception {
		String strEntity, strEntityDetail;
		strEntity = "dm-ec-emails";
		strEntityDetail = "*";
		
		//### This returns a string with all agents
		strPollingResult = Test_SubscribeEvent_DMs(supID, strEntity, strEntityDetail);
		//### This analyzes the above string.
		strParsedResult = jMessageBuilder.jasonParsor_DM_EcEmails(strPollingResult);
		pollResultPorcessedHash.put(strEntity, strParsedResult);
		
		
	}
	
	public void Test_SubscribeEvent_DM_ReportingStations() throws Exception {
		//ToDo: No string is returned after subscribtion.  So it may not be ready.
		String strEntity, strEntityDetail;
		strEntity = "dm-reporting-stations";
		strEntityDetail = "*";
		
		//### This returns a string with all agents
		strPollingResult = Test_SubscribeEvent_DMs(supID, strEntity, strEntityDetail);
		//### This analyzes the above string.
		strParsedResult = jMessageBuilder.jasonParsor_DM_ReportingStations(strPollingResult);
		pollResultPorcessedHash.put(strEntity, strParsedResult);
		
		
	}
	
	
	public void Test_SubscribeEvent_DM_SubSystem() throws Exception {
		//Somehow, poll result of this appears on other polls/
		//The poll result returns jason array which is different from other result/
		//This needs more work.
		String strEntity, strEntityDetail;
		strEntity = "dm-sub-system";
		strEntityDetail = "*";
		
		//### This returns a string with a SubSystem DM->email event.
		strPollingResult = Test_SubscribeEvent_DMs_SubSystems(supID, strEntity, strEntityDetail);
		//### This analyzes the above string.
		strParsedResult = jMessageBuilder.jasonParsor_DM_SubSystem(strPollingResult);
		pollResultPorcessedHash.put(strEntity, strParsedResult);
		
		
	}
	

	/**
	 * 
	 * @param supID     : supervisor ID which is from ECCDB->sup table
	 * @param strEntity : Entity to subscribe like agent, group, etc..
	 * @return			: This is return string after subscribing and polling.
	 * @throws Exception
	 */
	public String Test_SubscribeEvent_DMs(String supID, String strEntity, String strEntityDetail) throws Exception {
		String testName, strJasonMessage, strPollResult;

		//#### Print testcase name to log
		testName = "Test Case: Test_SubscribeEvent_DM==> " + strEntity + " : " + strEntityDetail;
		startTestCase(testName);
		
		//#### Building  Jason message for subscribe
		strJasonMessage = jMessageBuilder.subOrUnsubscribeEvents_DM(supID, strEntity, strEntityDetail,  "subscribe");
		
		//#### Send Subscription request for subscribe
		apiTesterClient.sendRequestJson("Subscribe "+ strEntity, strJasonMessage);
		
		//####Start Polling  : This is the final string for the subscription.
		wait(1);
		strPollResult = apiTesterClient.startPolling("Subscribe " + strEntity);
		
		
		//####Try one more time if the return string is empty.
		if (removeFirstLastBracket(strPollResult).isEmpty()){
			apiTesterClient.sendRequestJson("Subscribe "+ strEntity, strJasonMessage);
			
			//###### Start Polling  : This is the final string for the subscription.
			wait(3);
			strPollResult = apiTesterClient.startPolling("Subscribe " + strEntity);
		}
		
		//#### Building  Jason message for unsubscribe
		//strJasonMessage = jMessageBuilder.subOrUnsubscribeEvents_DM(supID, strEntity, "unsubscribe");
				
		//#### Send Subscription request for unsubscribe
		//apiTesterClient.sendRequestJson("Unsubscribe "+ strEntity, strJasonMessage);
		

		//##### Check if the poll result is correct and collect poll result
		if (!removeFirstLastBracket(strPollResult).isEmpty()){
			pollResultsHash.put(strEntity, strPollResult);
		}else{
			pollResultsHash.put(strEntity, "The poll-result is empty string");
			Assert.fail("* Return string is empty");
		}
		
		return strPollResult;
		
	}
	

	/**
	 * 
	 * @param supID     : supervisor ID which is from ECCDB->sup table
	 * @param strEntity : Entity to subscribe like agent, group, etc..
	 * @param strEntityDetail : Detailed Entity
	 * @return			: This is return string after subscribing and polling.
	 * @throws Exception
	 */

	public String Test_SubscribeEvent_DMs_SubSystems(String supID, String strEntity, String strEntityDetail) throws Exception {
		String testName, strJasonMessage, strPollResult;

		//#### Print testcase name to log
		testName = "Test Case: Test_SubscribeEvent_DM==> " + strEntity;
		startTestCase(testName);
		
		//#### Building  Jason message for subscribe
		strJasonMessage = jMessageBuilder.subOrUnsubscribeEvents_DM(supID, strEntity, strEntityDetail, "subscribe");
		
		//#### Send Subscription request for subscribe
		apiTesterClient.sendRequestJson("Subscribe "+ strEntity, strJasonMessage);
		
		//####Start Polling  : This is the final string for the subscription.
		wait(1);
		strPollResult = apiTesterClient.startPolling("Subscribe " + strEntity);
		
		//####Start Polling 2 times for subsystem : This is the final string for the subscription.
		wait(1);
		//strPollResult = apiTesterClient.startPolling("Subscribe " + strEntity);
		
		
		//####Try one more time if the return string is empty.
		if (removeFirstLastBracket(strPollResult).isEmpty()){
			apiTesterClient.sendRequestJson("Subscribe "+ strEntity, strJasonMessage);
			
			//###### Start Polling  : This is the final string for the subscription.
			wait(3);
			strPollResult = apiTesterClient.startPolling("Subscribe " + strEntity);
		}
		
		//#### Building  Jason message for unsubscribe
		//strJasonMessage = jMessageBuilder.subOrUnsubscribeEvents_DM(supID, strEntity, "unsubscribe");
				
		//#### Send Subscription request for unsubscribe
		//apiTesterClient.sendRequestJson("Unsubscribe "+ strEntity, strJasonMessage);
		

		//##### Check if the poll result is correct and collect poll result
		if (!removeFirstLastBracket(strPollResult).isEmpty()){
			pollResultsHash.put(strEntity, strPollResult);
		}else{
			pollResultsHash.put(strEntity, "The poll-result is empty string");
			Assert.fail("* Return string is empty");
		}
		
		return strPollResult;
		
	}
	
	
	
	@AfterClass
	public void afterClass() throws Exception {
		log.info("@@@ After Class: Test_SubscribeEvent_DM @@@");
		
		//#### this prints out all polling results.
		printHashTable("poll Result", pollResultsHash);
		printHashTable("Poll Result processed", pollResultPorcessedHash);
	}
	
	


}
