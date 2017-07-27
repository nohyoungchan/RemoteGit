package ClassesTest;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import ClassesEntity.*;


@Test(groups= {"SubscribeEvent"})
public class Test_SubscribeEvent extends TestCaseObject {
	String strPollingResult, supID, strEntity, strParsedResult;
	
	Test_SubscribeEvent() throws Exception{
		log.info("* Constructor: Test_SubscribeEvent");

	}

	@BeforeClass
	public void beforeClass() throws Exception {
		startTestClassMessage("Test_SubscribeEvent Class");
		//strCertificate = apiTesterClient.logIn(hbAdmin);
		//supID = hbAdmin.supervisorID;
		strCertificate = apiTesterClient.logIn(hbSup);
		supID = hbSup.supervisorID;
		
		//Clear all Hash table which is used to accumulate poll result and poll processed result
		pollResultsHash.clear();
		pollResultPorcessedHash.clear();
    }
	

	public void Test_SubscribeEvent_schedule() throws Exception {
		strEntity = "schedule";

		
		strPollingResult = Test_SubscribeEvents(supID, strEntity);
		strParsedResult = jMessageBuilder.jasonParsor_Schedule(strPollingResult);
		pollResultPorcessedHash.put(strEntity, strParsedResult);
		
		
	}
	
	
	public void Test_SubscribeEvent_agent() throws Exception {
		strEntity = "agent";
		
		//### This returns a string with all agents
		strPollingResult = Test_SubscribeEvents(supID, strEntity);
		//### This analyzes the above string.
		strParsedResult = jMessageBuilder.jasonParsor_Agents(strPollingResult);
		pollResultPorcessedHash.put(strEntity, strParsedResult);
		
		
	}
	
	public void Test_SubscribeEvent_group() throws Exception {
		strEntity = "group";
		
		strPollingResult = Test_SubscribeEvents(supID, strEntity);
		strParsedResult = jMessageBuilder.jasonParsor_Groups(strPollingResult);
		pollResultPorcessedHash.put(strEntity, strParsedResult);
		
		
	}
	
	
	public void Test_SubscribeEvent_dnis() throws Exception {
		strEntity = "dnis";
		
		strPollingResult = Test_SubscribeEvents(supID, strEntity);
		strParsedResult = jMessageBuilder.jasonParsor_Dnis(strPollingResult);
		pollResultPorcessedHash.put(strEntity, strParsedResult);
		
		
	}
	
	public void Test_SubscribeEvent_service() throws Exception {
		strEntity = "service";
		
		strPollingResult = Test_SubscribeEvents(supID, strEntity);
		strParsedResult = jMessageBuilder.jasonParsor_Service(strPollingResult);
		pollResultPorcessedHash.put(strEntity, strParsedResult);
		
		
	}
	
	public void Test_SubscribeEvent_reportTemplate() throws Exception {
		strEntity = "report-template";
		
		strPollingResult = Test_SubscribeEvents(supID, strEntity);
		strParsedResult = jMessageBuilder.jasonParsor_reportTemplate(strPollingResult);
		pollResultPorcessedHash.put(strEntity, strParsedResult);
		
		
	}
	
	public void Test_SubscribeEvent_customReport() throws Exception {
		strEntity = "custom-report";
		
		strPollingResult = Test_SubscribeEvents(supID, strEntity);
		strParsedResult = jMessageBuilder.jasonParsor_customReport(strPollingResult);
		pollResultPorcessedHash.put(strEntity, strParsedResult);
		
		
	}
	
	public void Test_SubscribeEvent_IVRApplication() throws Exception {
		strEntity = "ivr-application";
		
		strPollingResult = Test_SubscribeEvents(supID, strEntity);
		strParsedResult = jMessageBuilder.jasonParsor_IVRApplication(strPollingResult);
		pollResultPorcessedHash.put(strEntity, strParsedResult);
		
		
	}
	
	public void Test_SubscribeEvent_tenant() throws Exception {
		strEntity = "tenant";
		
		//### This returns a string with all agents
		strPollingResult = Test_SubscribeEvents(supID, strEntity);
		//### This analyzes the above string.
		//strParsedResult = jMessageBuilder.jasonParsor_Agents(strPollingResult);
		//pollResultPorcessedHash.put(strEntity, strParsedResult);
		
		
	}

	/**
	 * 
	 * @param supID     : supervisor ID which is from ECCDB->sup table
	 * @param strEntity : Entity to subscribe like agent, group, etc..
	 * @return			: This is return string after subscribing and polling.
	 * @throws Exception
	 */
	public String Test_SubscribeEvents(String supID, String strEntity) throws Exception {
		String testName, strJasonMessage, strPollResult;

		//#### Print testcase name to log
		testName = "Test Case: Test_SubscribeEvent==> " + strEntity;
		startTestCase(testName);
		
		//#### Building  Jason message
		strJasonMessage = jMessageBuilder.subscribeEvents(supID, strEntity);
		
		//#### Send Subscription request
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
		log.info("@@@ After Class: Test_SubscribeEvent @@@");
		
		//#### this prints out all polling results.
		printHashTable("poll Result", pollResultsHash);
		printHashTable("Poll Result processed", pollResultPorcessedHash);
	}
	
	


}
