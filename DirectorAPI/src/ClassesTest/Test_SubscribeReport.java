package ClassesTest;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import ClassesEntity.*;


@Test(groups= {"SubscribeReport"})
public class Test_SubscribeReport extends TestCaseObject {
	
	
	Test_SubscribeReport() throws Exception{
		log.info("* Constructor: Test_SubscribeReport");

	}

	@BeforeClass
	public void beforeClass() throws Exception {
		log.info("* Before Class: Test_SubscribeReport");
		numPolling = 0;
		//InitializeAllVariables();
		//allObjects = Test_Initiate.getAllObjects();
		
		//apiTesterClient = AllObjects.apiTesterClient;
		//jMessageBuilder = AllObjects.jMessageBuilder;
		//hbAdmin = allObjects.hbSupervisors.get(0);
		//hbSup = allObjects.hbSupervisors.get(1);  //0 is admin, 1 is supervisor
		
		//############ Log in and get ticket & the ticket is stored on apiTesterClient
		//strCertificate = Test_Initiate.strCertificate;
    }
	
	public void Test_SubscribeReport_new() throws Exception {
		String strReturn, supID, reportName, saveType;
		supID = ini.get(sysTT, "sup1.supervisorID");
		saveType = "new";
		reportName = sysTT + "hello";
		
		strReturn = Test_SubscribeReports(supID, reportName, saveType);
		
	}

	

	//type: new or delete
	public String Test_SubscribeReports(String supID, String reportName, String type) throws Exception {
		String testName = "Test Case: Test_SubscribeReport==> " + reportName;
		startTestCase(testName);
		
		String strJasonMessage, strReturn;
		
		//############# Request by Jason message
		strJasonMessage = jMessageBuilder.saveReport(supID, reportName, type);
		apiTesterClient.sendRequestJson("Subscribe "+ reportName, strJasonMessage);
		
		//############# Start Polling  : This is the final string for the subscription.
		wait(3);
		strReturn = apiTesterClient.startPolling("Subscribe " + reportName);
		
		//Try one more time if the return string is empty.
		if (removeFirstLastBracket(strReturn).isEmpty()){
			apiTesterClient.sendRequestJson("Subscribe "+ reportName, strJasonMessage);
			
			//############# Start Polling  : This is the final string for the subscription.
			wait(3);
			strReturn = apiTesterClient.startPolling("Subscribe " + reportName);
		}
		
		if (removeFirstLastBracket(strReturn).isEmpty()) Assert.fail("* Return string is empty");
		
		//### This analyzes the above string.
		return strReturn;
		
	}
	
	
	@AfterClass
	public void afterClass() throws Exception {
		log.info("* Afater Class: Test_SubscribeReport");	
	}
	
	


}
