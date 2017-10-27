package HBTestesNG.TestCases;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;

import HBTestesNG.BaseObjects.*;import org.testng.SkipException;

@Test(groups= {"VoiceScript_Transfer_ClassLevel"})
public class Test_VoiceScript_Transfer extends TestCaseObject {	
	

	Test_VoiceScript_Transfer() throws Exception{
		log.info("* Construction: Test_VoiceScript_Transfer");

	}
	@BeforeClass
	public void beforeClass() throws Exception {
		log.info("* Before Class: Test_VoiceScript_Transfer");
		InitializeAllVariables(); 
	}

  
	@AfterClass
	public void afterClass() throws Exception {
		log.info("* After Class: Test_VoiceScript_Transfer");
	}
	
	@Test
	@Parameters({"rT1", "tT1", "rT2", "tT2", "wT2"})
	public void VoiceScript_BTransfer_ToExt_SameGroup(int rT1, int tT1, int rT2, int tT2, int wT2) throws Exception {
		
		String testName = "VoiceScript->Ans->Blind Transfer to Ext on Same Group ";
		if(startTestCase(testName).contains("no")) skipTest(testName, "Skipping because user want to end test => " + testName);
		//#########################
		
		try{
			agent1.resumeAgent();
			agent2.releaseAgentSecondCode();
			agent3.resumeAgent();

			//###########################
			customer1.makeACDCall(irn5.didNum);
			agent1.answerACDCall(rT1, tT1);
			
			//###########################
			agent2.resumeAgent();
			agent1.blindTransfer(agent2.extension);
			agent2.answerACDCall(rT2, tT2);


			//############################
			agent2.disconnectByWebAgent();
			agent2.wrapupEndWith2WrapupCodes(wT2);
		
		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
	}
	
	@Parameters({"rT1", "tT1", "rT2", "tT2"})
	public void VoiceScript_BTransfer_ToExt_DifferentGroup(int rT1, int tT1, int rT2, int tT2) throws Exception {
		
		String testName = "VoiceScript->Ans->Blind Transfer to Ext on Different Group ";
		if(startTestCase(testName).contains("no")) skipTest(testName, "Skipping because user want to end test => " + testName);
		//#########################
		
		try{
			agent1.resumeAgent();
			agent2.releaseAgentSecondCode();
			agent3.resumeAgent();

			//###########################
			customer1.makeACDCall(irn5.didNum);
			agent1.answerACDCall(rT1, tT1);
			
			//###########################
			agent1.blindTransfer(agent3.extension);
			agent3.answerACDCall(rT2, tT2);


			//############################
			agent3.disconnectByWebAgent();
		
		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
	}
	
	@Parameters({"rT1", "tT1", "rT2", "tT2"})
	public void VoiceScript_BTransfer_ToExternal(int rT1, int tT1, int rT2, int tT2) throws Exception {
		
		String testName = "VoiceScript->Ans->Blind Transfer to External number ";
		if(startTestCase(testName).contains("no")) skipTest(testName, "Skipping because user want to end test => " + testName);
		//#########################
		
		try{
			agent1.resumeAgent();
			agent2.releaseAgentSecondCode();
			agent3.resumeAgent();

			//###########################
			customer1.makeACDCall(irn5.didNum);
			agent1.answerACDCall(rT1, tT1);
			
			//###### Blind transfer to the external number of agent3
			agent1.blindTransfer(agent3.did);
			agent3.answerACDCall(rT2, tT2);


			//############################
			agent3.disconnectByWebAgent();
		
		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
	}
	
	@Parameters({"rT1", "tT1", "rT2", "tT2", "wT2"})
	public void VoiceScript_BTransfer_ToIRN_SameGroup(int rT1, int tT1, int rT2, int tT2, int wT2) throws Exception {
		
		String testName = "VoiceScript->Ans->Blind Transfer to Ext on Same Group ";
		if(startTestCase(testName).contains("no")) skipTest(testName, "Skipping because user want to end test => " + testName);
		//#########################
		
		try{
			agent1.resumeAgent();
			agent2.releaseAgentSecondCode();
			agent3.resumeAgent();

			//###########################
			customer1.makeACDCall(irn5.didNum);
			agent1.answerACDCall(rT1, tT1);
			
			//###########################
			agent2.resumeAgent();
			agent1.blindTransfer(irn1.irnNum);
			agent2.answerACDCall(rT2, tT2);


			//############################
			agent2.disconnectByWebAgent();
			agent2.wrapupEndWith2WrapupCodes(wT2);
		
		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
	}
	
	@Parameters({"rT1", "tT1", "rT2", "tT2", "wT2"})
	public void VoiceScript_BTransfer_ToIRN_DifferentGroup(int rT1, int tT1, int rT2, int tT2, int wT2) throws Exception {
		
		String testName = "VoiceScript->Ans->Blind Transfer to Ext on Different Group ";
		if(startTestCase(testName).contains("no")) skipTest(testName, "Skipping because user want to end test => " + testName);
		//#########################
		
		try{
			agent1.resumeAgent();
			agent2.releaseAgentSecondCode();
			agent3.resumeAgent();

			//###########################
			customer1.makeACDCall(irn5.didNum);
			agent1.answerACDCall(rT1, tT1);
			
			//###########################
			agent1.blindTransfer(irn2.irnNum);
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
	public void VoiceScript_BTransfer_ToIRN_AQ(int rT1, int tT1, int rT2, int tT2, int wT2) throws Exception {
		
		String testName = "VoiceScript->Ans->Blind Transfer to IRN to AQ ";
		if(startTestCase(testName).contains("no")) skipTest(testName, "Skipping because user want to end test => " + testName);
		//#########################
		
		try{
			agent1.resumeAgent();
			agent2.releaseAgentSecondCode();
			agent3.resumeAgent();

			//###########################
			customer1.makeACDCall(irn5.didNum);
			agent1.answerACDCall(rT1, tT1);
			
			//###########################
			agent1.blindTransfer(irn4.irnNum);
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
	public void VoiceScript_CTransfer_ToExt_SameGroup(int rT1, int tT1, int rT2, int tT2, int wT2) throws Exception {
		
		String testName = "VoiceScript->Ans->Consult Transfer to Ext on Same Group";
		if(startTestCase(testName).contains("no")) skipTest(testName, "Skipping because user want to end test => " + testName);
		//#########################
		
		try{
			agent1.resumeAgent();
			agent2.releaseAgentSecondCode();
			agent3.resumeAgent();

			//###########################
			customer1.makeACDCall(irn5.didNum);
			agent1.answerACDCall(rT1, tT1);
			
			//###########################
			agent2.resumeAgent();
			agent1.consultTransfer(irn1.irnNum);
			agent2.answerACDCall(rT2, tT2);
			agent1.confirmTransfer();
			wait(20, "Talk after confirmation of consult-transfer");

			//############################
			agent2.disconnectByWebAgent();
			agent2.wrapupEndWith2WrapupCodes(wT2);
		
		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
	}
	
	@Parameters({"rT1", "tT1", "rT2", "tT2"})
	public void VoiceScript_CTransfer_ToExt_DifferentGroup(int rT1, int tT1, int rT2, int tT2) throws Exception {
		
		String testName = "VoiceScript->Ans->Consult Transfer to Ext on Different Group";
		if(startTestCase(testName).contains("no")) skipTest(testName, "Skipping because user want to end test => " + testName);
		//#########################
		
		try{
			agent1.resumeAgent();
			agent2.releaseAgentSecondCode();
			agent3.resumeAgent();

			//###########################
			customer1.makeACDCall(irn5.didNum);
			agent1.answerACDCall(rT1, tT1);
			
			//###########################
			agent1.consultTransfer(irn2.irnNum);
			agent3.answerACDCall(rT2, tT2);
			agent1.confirmTransfer();
			wait(20, "Talk after confirmation of consult-transfer");

			//############################
			agent3.disconnectByWebAgent();
		
		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
	}
	
	@Parameters({"rT1", "tT1", "rT2", "tT2"})
	public void VoiceScript_CTransfer_ToExternal(int rT1, int tT1, int rT2, int tT2) throws Exception {
		
		String testName = "VoiceScript->Ans->Consult Transfer to External number";
		if(startTestCase(testName).contains("no")) skipTest(testName, "Skipping because user want to end test => " + testName);
		//#########################
		
		try{
			agent1.resumeAgent();
			agent2.releaseAgentSecondCode();
			agent3.resumeAgent();

			//###########################
			customer1.makeACDCall(irn5.didNum);
			agent1.answerACDCall(rT1, tT1);
			
			//###########################
			agent1.consultTransfer(agent3.did);
			agent3.answerACDCall(rT2, tT2);
			agent1.confirmTransfer();
			wait(20, "Talk after confirmation of consult-transfer");

			//############################
			agent3.disconnectByWebAgent();
		
		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
	}
	
	
	
	
	
	@Parameters({"rT1", "tT1", "rT2", "tT2", "wT2"})
	public void VoiceScript_CTransfer_ToIRN_SameGroup(int rT1, int tT1, int rT2, int tT2, int wT2) throws Exception {
		
		String testName = "VoiceScript->Ans->Consult Transfer to IRN on Same Group ";
		if(startTestCase(testName).contains("no")) skipTest(testName, "Skipping because user want to end test => " + testName);
		//#########################
		
		try{
			agent1.resumeAgent();
			agent2.releaseAgentSecondCode();
			agent3.resumeAgent();

			//###########################
			customer1.makeACDCall(irn5.didNum);
			agent1.answerACDCall(rT1, tT1);
			
			//###########################
			agent2.resumeAgent();
			agent1.consultTransfer(irn1.irnNum);
			agent2.answerACDCall(rT2, tT2);
			agent1.confirmTransfer();
			wait(20, "Talk after confirmation of consult-transfer");

			//############################
			agent2.disconnectByWebAgent();
			agent2.wrapupEndWith2WrapupCodes(wT2);
		
		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
	}
	
	@Parameters({"rT1", "tT1", "rT2", "tT2", "wT2"})
	public void VoiceScript_CTransfer_ToIRN_DifferentGroup(int rT1, int tT1, int rT2, int tT2, int wT2) throws Exception {
		
		String testName = "VoiceScript->Ans->Consult Transfer to IRN on Different Group ";
		if(startTestCase(testName).contains("no")) skipTest(testName, "Skipping because user want to end test => " + testName);
		//#########################
		
		try{
			agent1.resumeAgent();
			agent2.releaseAgentSecondCode();
			agent3.resumeAgent();

			//###########################
			customer1.makeACDCall(irn5.didNum);
			agent1.answerACDCall(rT1, tT1);
			
			//###########################
			agent1.consultTransfer(irn2.irnNum);
			agent3.answerACDCall(rT2, tT2);
			agent1.confirmTransfer();
			wait(20, "Talk after confirmation of consult-transfer");

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
