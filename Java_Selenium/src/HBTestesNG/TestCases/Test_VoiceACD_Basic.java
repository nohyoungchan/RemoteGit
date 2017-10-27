package HBTestesNG.TestCases;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import HBTestesNG.BaseObjects.*;


@Test(groups= {"VoiceACD_Basic_ClassLevel"})
public class Test_VoiceACD_Basic extends TestCaseObject {

	Test_VoiceACD_Basic() throws Exception{
		log.info("* Construction: Test_VoiceACD_Basic");

	}
	
	
	@BeforeClass
	public void beforeClass() throws Exception {
		log.info("* Before Class: Test_VoiceACD_Basic");
		InitializeAllVariables(); 
    }
	
	@AfterClass
	public void afterClass() throws Exception {
		log.info("* After Class: Test_VoiceACD_Basic");
	}
	
	@Parameters({"rT1", "tT1", "wT1"})
	public void VoiceACD_NoQ_Answer(int rT1, int tT1, int wT1) throws Exception {
		String testName = "VoiceACD->NoQ->Answered";
		if(startTestCase(testName).contains("no")) skipTest(testName, "Skipping because user want to end test => " + testName);
		
		
		//#########################
		//resetAllActors(testName);
		agent1.resumeAgent();
		//agent2.releaseAgent();
		agent2.releaseAgentSecondCode();
		agent3.resumeAgent();

		try{
			customer1.makeACDCall(irn1.didNum);
					
			//##########################
			agent1.answerACDCall(rT1, tT1);
			
			//############################
			agent1.disconnectByWebAgent();
			//customer1.dropCall();
			agent1.wrapupEndWith2WrapupCodes(wT1);	
			//agent1.wrapupEndWith2WrapupCodes(wT1);
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
		if(startTestCase(testName).contains("no")) skipTest(testName, "Skipping because user want to end test");
		//#########################
		agent1.releaseAgent();
		agent2.releaseAgentSecondCode();
		agent3.resumeAgent();
		
		try{
			customer1.makeACDCall(irn1.didNum);
			wait(qT1, "Queue");
			agent1.resumeAgent();
					
			//##########################
			agent1.answerACDCall(rT1, tT1);
			
			//############################
			agent1.disconnectByWebAgent();
			agent1.wrapupEndWith2WrapupCodes(wT1);
		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
	}
	
	@Parameters({"rT1", "tT1"})
	public void VoiceACD_NoQ_Answer_NoWrapTime(int rT1, int tT1) throws Exception {
		String testName = "VoiceACD->NoQ->Answered with No Wrap time";
		if(startTestCase(testName).contains("no")) skipTest(testName, "Skipping because user want to end test");
		//#########################
		sup1.Max_LogIn_changeWrapAndFRTime_LogOut_Min(AllActors.services.get(0).name, "0", "40");
		 
		//#########################
		agent1.resumeAgent();
		agent2.releaseAgentSecondCode();
		agent3.resumeAgent();
	
		try{
			//##########################
			customer1.makeACDCall(irn1.didNum);
			agent1.answerACDCall(rT1, tT1);
			
			//############################
			agent1.disconnectByWebAgent();
			
		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			resetAllActors(testName);
		}finally{
			sup1.Max_LogIn_changeWrapAndFRTime_LogOut_Min(AllActors.services.get(0).name, "20", "30");
			endTestCase(testName);
	}
	}
	
	


}
