package HBTestesNG.TestCases;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import HBTestesNG.BaseObjects.*;


@Test(groups= {"VoiceACD_Basic_ClassLevel"})
public class Test_Preparation extends TestCaseObject {
	String className;

	Test_Preparation() throws Exception{
		log.info("* Construction: Test_Preparation");
		className = "Test_Preparation";

	}
	
	
	@BeforeClass
	public void beforeClass() throws Exception {
		classStart(className);
		InitializeAllVariables(); 
	  
	}

  
	@AfterClass
	public void afterClass() throws Exception {
		classEnd(className);
	}
	
	
	
	
	@Parameters({"qT1"})
	public void VoiceACD_Q_Abandon(int qT1) throws Exception {
		String testName = "VoiceACD->Q->Answered";
		if(startTestCase(testName).contains("no")) skipTest(testName, "Skipping because user want to end test => " + testName);
		//#########################
		agent1.resumeAgent();
		agent2.releaseAgent();
		agent3.releaseAgent();
		
		try{
			customer1.makeACDCall(irn1.didNum);
			wait(5, "Queue");
			customer1.abandonWhileRingCall();
		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
	}
	
	
	@Parameters({"qT1", "rT1", "tT1", "wT1"})
	public void VoiceACD_Q_Answer(int qT1, int rT1, int tT1, int wT1) throws Exception {
		String testName = "VoiceACD->Q->Answered";
		if(startTestCase(testName).contains("no")) skipTest(testName, "Skipping because user want to end test => " + testName);
		//#########################
		agent1.releaseAgent();
		agent1.getAICState("releaseAgent");
		agent2.releaseAgentSecondCode();
		agent2.getAICState("releaseAgentSecondCode");
		agent3.resumeAgent();
		agent3.getAICState("resumeAgent");
		
		try{
			customer1.makeACDCall(irn1.didNum);
			wait(qT1, "Queue");
			agent1.resumeAgent();
			wait(2);
			agent1.getAICState("resumeAgent");
					
			//##########################
			agent1.answerACDCall(rT1, tT1);
			wait(2);
			agent1.getAICState("answerACDCall");
			
			//############################
			agent1.disconnectByWebAgent();
			wait(2);
			agent1.getAICState("disconnectByWebAgent");
			agent1.wrapupEndWith2WrapupCodes(wT1);
			wait(2);
			agent1.getAICState("wrapupEndWith2WrapupCodes");
						
		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
	}

	
	


}
