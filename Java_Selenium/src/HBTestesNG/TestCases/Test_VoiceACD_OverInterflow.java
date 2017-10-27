package HBTestesNG.TestCases;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;

import HBTestesNG.BaseObjects.*;import org.testng.SkipException;

@Test(groups= {"VoiceACD_OInterflow_ClassLevel"})
public class Test_VoiceACD_OverInterflow extends TestCaseObject {
	

	Test_VoiceACD_OverInterflow() throws Exception{
		log.info("* Construction: Test_VoiceACD_OverInterflow");

	}
	@BeforeClass
	public void beforeClass() throws Exception {
		log.info("* Before Class: Test_VoiceACD_OverInterflow");
		InitializeAllVariables(); 
		//This class assumes the following
		// 1) Overflow timeout: 60s
		// 2) Overflow destination: Group
		// 3) agent3 belongs to Group2 & Group3
		// 4) Interflow timeout: 120s
	  
	}

  
	@AfterClass
	public void afterClass() throws Exception {
		log.info("* After Class: Test_VoiceACD_OverInterflow");
	}
	
	@Parameters({"rT1", "tT1", "rT2", "tT2", "wT2"})
	public void VoiceACD_R_Q_Overflow_R_Ans_2ndGroup(int rT1, int tT1, int rT2, int tT2, int wT2) throws Exception {
		
		String testName = "VoiceACD->Ring->Q->Overflow->Ring->Answer on the 2nd group";
		if(startTestCase(testName).contains("no")) skipTest(testName, "Skipping because user want to end test => " + testName);
		//#########################
		
		try{
			agent1.resumeAgent();
			agent2.releaseAgentSecondCode();
			agent3.resumeAgent();;

			//###########################
			customer1.makeACDCall(irn1.didNum);

			//##########################
			wait(60, "Wait for Overflow timeout: This includes Ring before forced-released time");
			agent3.answerACDCall(rT2, tT2);

			//############################
			agent3.disconnectByWebAgent();
			agent3.wrapupEndWith2WrapupCodes(wT2);

		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
	}
	
	
	@Parameters({"rT1", "tT1", "rT2", "tT2", "wT2"})
	public void VoiceACD_R_Q_Overflow_Q_R_Ans_2ndGroup(int rT1, int tT1, int rT2, int tT2, int wT2) throws Exception {
		
		String testName = "VoiceACD->Ring->Q->Overflow->Q->Ring->Answer on the 2nd group";
		if(startTestCase(testName).contains("no")) skipTest(testName, "Skipping because user want to end test => " + testName);
		//#########################
		
		try{
			agent1.resumeAgent();
			agent2.releaseAgentSecondCode();
			agent2.releaseAgentThirdCode();

			//###########################
			customer1.makeACDCall(irn1.didNum);

			//##########################
			wait(80, "Wait for Overflow timeout");
			agent3.resumeAgent();
			agent3.answerACDCall(rT2, tT2);

			//############################
			agent3.disconnectByWebAgent();
			agent3.wrapupEndWith2WrapupCodes(wT2);
		
		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
	}
	
	
	
	@Parameters({"rT1", "tT1", "rT2", "tT2", "wT2"})
	public void VoiceACD_Q_Overflow_R_Ans_2ndGroup(int rT1, int tT1, int rT2, int tT2, int wT2) throws Exception {
		
		String testName = "VoiceACD->Q->Overflow->Ring->Answer on the 2nd group";
		if(startTestCase(testName).contains("no")) skipTest(testName, "Skipping because user want to end test => " + testName);
		//#########################
		
		try{
			agent1.releaseAgent();
			agent2.releaseAgentSecondCode();
			agent3.resumeAgent();;

			//###########################
			customer1.makeACDCall(irn1.didNum);

			//##########################
			wait(60, "Wait for Overflow timeout");
			agent3.answerACDCall(rT2, tT2);

			//############################
			agent3.disconnectByWebAgent();
			agent3.wrapupEndWith2WrapupCodes(wT2);
		
		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
	}
	
	@Parameters({"rT1", "tT1", "rT2", "tT2", "wT2"})
	public void VoiceACD_Q_Overflow_R_Ans_1stGroup(int rT1, int tT1, int rT2, int tT2, int wT2) throws Exception {
		
		String testName = "VoiceACD->Q->Overflow->Ring->Answer on the 1st group";
		if(startTestCase(testName).contains("no")) skipTest(testName, "Skipping because user want to end test => " + testName);
		//#########################
		
		try{
			agent1.releaseAgent();
			agent2.releaseAgentSecondCode();
			agent2.releaseAgentThirdCode();

			//###########################
			customer1.makeACDCall(irn1.didNum);

			//##########################
			wait(60, "Wait for Overflow timeout");
			agent1.resumeAgent();
			agent1.answerACDCall(rT1, tT1);

			//############################
			agent1.disconnectByWebAgent();
			agent1.wrapupEndWith2WrapupCodes(wT2);
		
		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
	}
	
	
	//agent3 belongs to both Group2 and Group3
	//This test assumes that overflow time(60s), Interflow time(120s)
	@Parameters({"rT1", "tT1", "rT2", "tT2", "wT2"})
	public void VoiceACD_Q_OverInterflow_Q_R_Ans_3rdGroup(int rT1, int tT1, int rT2, int tT2, int wT2) throws Exception {
		
		String testName = "VoiceACD->Q->OverInterflow->Q->Ring->Answer on the 3rd group";
		if(startTestCase(testName).contains("no")) skipTest(testName, "Skipping because user want to end test => " + testName);
		//#########################
		
		try{
			agent1.releaseAgent();
			agent2.releaseAgentSecondCode();
			agent2.releaseAgentThirdCode();;

			//###########################
			customer1.makeACDCall(irn1.didNum);

			//##########################
			wait(120, "Wait for Overflow timeout");
			agent3.resumeAgent();
			agent3.answerACDCall(rT2, tT2);

			//############################
			agent3.disconnectByWebAgent();
			agent3.wrapupEndWith2WrapupCodes(wT2);
		
		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
	}
	
	
	

}
