package HBTestesNG.TestCases;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;

import HBTestesNG.BaseObjects.*;import org.testng.SkipException;

@Test(groups= {"VoiceACD_Transfer_WrapTimeZero_ClassLevel"})
public class Test_VoiceACD_Transfer_WrapTimeZero extends TestCaseObject {
	
	Test_VoiceACD_Transfer_WrapTimeZero() throws Exception{
		log.info("* Construction: Test_VoiceACD_Transfer_WrapTimeZero");

	}
	@BeforeClass
	public void beforeClass() throws Exception {
		log.info("* Before Class: Test_VoiceACD_Transfer_WrapTimeZero");
		InitializeAllVariables(); 
		
		sup1.Max_LogIn_changeWrapAndFRTime_LogOut_Min(aa.services.get(0).name, "0", "40");
	  
	}

  
	@AfterClass
	public void afterClass() throws Exception {
		log.info("* After Class: Test_VoiceACD_Transfer_WrapTimeZero");
		sup1.Max_LogIn_changeWrapAndFRTime_LogOut_Min(aa.services.get(0).name, "20", "30");
	}
	

	@Parameters({"rT1", "tT1", "rT2", "tT2", "wT2"})
	public void VoiceACD_BTransfer_ToExt_SameGroup(int rT1, int tT1, int rT2, int tT2, int wT2) throws Exception {
		String testName = "VoiceACD->Ans->Blind Transfer to Ext on Same Group_WrapTimeZero ";
		if(startTestCase(testName).contains("no")) skipTest("Skipping because user want to end test => " + testName);
		//#########################
		
		try{
			agent1.resumeAgent();
			agent2.releaseAgentSecondCode();
			agent3.resumeAgent();

			//###########################
			customer1.makeACDCall(irn1.didNum);

			//##########################
			agent1.answerACDCall(rT1, tT1);

			//###########################
			agent2.resumeAgent();
			agent1.blindTransfer(agent2.extension);
			agent2.answerACDCall(rT2, tT2);


			//############################
			agent2.disconnectByWebAgent();
			//agent2.wrapupEndWith2WrapupCodes(wT2);
		
		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
	}
	
	@Parameters({"rT1", "tT1", "rT2", "tT2"})
	public void VoiceACD_BTransfer_ToExt_DifferentGroup(int rT1, int tT1, int rT2, int tT2) throws Exception {
		
		String testName = "VoiceACD->Ans->Blind Transfer to Ext on Different Group_WrapTimeZero ";
		if(startTestCase(testName).contains("no")) skipTest("Skipping because user want to end test => " + testName);
		//#########################
		
		try{
			agent1.resumeAgent();
			agent2.releaseAgentSecondCode();
			agent3.resumeAgent();

			//###########################
			customer1.makeACDCall(irn1.didNum);

			//##########################
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
	public void VoiceACD_BTransfer_ToExternal(int rT1, int tT1, int rT2, int tT2) throws Exception {
		
		String testName = "VoiceACD->Ans->Blind Transfer to External_WrapTimeZero ";
		if(startTestCase(testName).contains("no")) skipTest("Skipping because user want to end test => " + testName);
		//#########################
		
		try{
			agent1.resumeAgent();
			agent2.releaseAgentSecondCode();
			agent3.resumeAgent();

			//###########################
			customer1.makeACDCall(irn1.didNum);

			//##########################
			agent1.answerACDCall(rT1, tT1);

			//###########################
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
	public void VoiceACD_BTransfer_ToIRN_SameGroup(int rT1, int tT1, int rT2, int tT2, int wT2) throws Exception {
		
		String testName = "VoiceACD->Ans->Blind Transfer to Ext on Same Group_WrapTimeZero ";
		if(startTestCase(testName).contains("no")) skipTest("Skipping because user want to end test => " + testName);
		//#########################
		
		try{
			agent1.resumeAgent();
			agent2.releaseAgentSecondCode();
			agent3.resumeAgent();

			//###########################
			customer1.makeACDCall(irn1.didNum);

			//##########################
			agent1.answerACDCall(rT1, tT1);

			//###########################
			agent2.resumeAgent();
			agent1.blindTransfer(irn1.irnNum);
			agent2.answerACDCall(rT2, tT2);


			//############################
			agent2.disconnectByWebAgent();
			//agent2.wrapupEndWith2WrapupCodes(wT2);
		
		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
	}
	
	@Parameters({"rT1", "tT1", "rT2", "tT2", "wT2"})
	public void VoiceACD_BTransfer_ToIRN_DifferentGroup(int rT1, int tT1, int rT2, int tT2, int wT2) throws Exception {
		
		String testName = "VoiceACD->Ans->Blind Transfer to Ext on Different Group_WrapTimeZero ";
		if(startTestCase(testName).contains("no")) skipTest("Skipping because user want to end test => " + testName);
		//#########################
		
		try{
			agent1.resumeAgent();
			agent2.releaseAgentSecondCode();
			agent3.resumeAgent();

			//###########################
			customer1.makeACDCall(irn1.didNum);

			//##########################
			agent1.answerACDCall(rT1, tT1);

			//###########################
			agent1.blindTransfer(irn2.irnNum);
			agent3.answerACDCall(rT2, tT2);


			//############################
			agent3.disconnectByWebAgent();
			//agent3.wrapupEndWith2WrapupCodes(wT2);

		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
	}
	
	@Parameters({"rT1", "tT1", "rT2", "tT2", "wT2"})
	public void VoiceACD_CTransfer_ToExt_SameGroup(int rT1, int tT1, int rT2, int tT2, int wT2) throws Exception {
		
		String testName = "VoiceACD->Ans->Consult Transfer to Ext on Same Group_WrapTimeZero";
		if(startTestCase(testName).contains("no")) skipTest("Skipping because user want to end test => " + testName);
		//#########################
		
		try{
			agent1.resumeAgent();
			agent2.releaseAgentSecondCode();
			agent3.resumeAgent();

			//###########################
			customer1.makeACDCall(irn1.didNum);

			//##########################
			agent1.answerACDCall(rT1, tT1);

			//###########################
			agent2.resumeAgent();
			agent1.consultTransfer(irn1.irnNum);
			agent2.answerACDCall(rT2, tT2);
			agent1.confirmTransfer();
			wait(20, "Talk after confirmation of consult-transfer");

			//############################
			agent2.disconnectByWebAgent();
			//agent2.wrapupEndWith2WrapupCodes(wT2);
		
		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
	}
	
	@Parameters({"rT1", "tT1", "rT2", "tT2"})
	public void VoiceACD_CTransfer_ToExt_DifferentGroup(int rT1, int tT1, int rT2, int tT2) throws Exception {
		
		String testName = "VoiceACD->Ans->Consult Transfer to Ext on Different Group_WrapTimeZero";
		if(startTestCase(testName).contains("no")) skipTest("Skipping because user want to end test => " + testName);
		//#########################
		
		try{
			agent1.resumeAgent();
			agent2.releaseAgentSecondCode();
			agent3.resumeAgent();

			//###########################
			customer1.makeACDCall(irn1.didNum);

			//##########################
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
	public void VoiceACD_CTransfer_ToExternal(int rT1, int tT1, int rT2, int tT2) throws Exception {
		
		String testName = "VoiceACD->Ans->Consult Transfer to External number_WrapTimeZero";
		if(startTestCase(testName).contains("no")) skipTest("Skipping because user want to end test => " + testName);
		//#########################
		
		try{
			agent1.resumeAgent();
			agent2.releaseAgentSecondCode();
			agent3.resumeAgent();

			//###########################
			customer1.makeACDCall(irn1.didNum);

			//##########################
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
	public void VoiceACD_CTransfer_ToIRN_SameGroup(int rT1, int tT1, int rT2, int tT2, int wT2) throws Exception {
		
		String testName = "VoiceACD->Ans->Consult Transfer to IRN on Same Group_WrapTimeZero ";
		if(startTestCase(testName).contains("no")) skipTest("Skipping because user want to end test => " + testName);
		//#########################
		
		try{
			agent1.resumeAgent();
			agent2.releaseAgentSecondCode();
			agent3.resumeAgent();

			//###########################
			customer1.makeACDCall(irn1.didNum);

			//##########################
			agent1.answerACDCall(rT1, tT1);

			//###########################
			agent2.resumeAgent();
			agent1.consultTransfer(irn1.irnNum);
			agent2.answerACDCall(rT2, tT2);
			agent1.confirmTransfer();
			wait(20, "Talk after confirmation of consult-transfer");

			//############################
			agent2.disconnectByWebAgent();
			//agent2.wrapupEndWith2WrapupCodes(wT2);

		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
	}
	
	@Parameters({"rT1", "tT1", "rT2", "tT2", "wT2"})
	public void VoiceACD_CTransfer_ToIRN_DifferentGroup(int rT1, int tT1, int rT2, int tT2, int wT2) throws Exception {
		
		String testName = "VoiceACD->Ans->Consult Transfer to IRN on Different Group_WrapTimeZero ";
		if(startTestCase(testName).contains("no")) skipTest("Skipping because user want to end test => " + testName);
		//#########################
		
		try{
			agent1.resumeAgent();
			agent2.releaseAgentSecondCode();
			agent3.resumeAgent();

			//###########################
			customer1.makeACDCall(irn1.didNum);

			//##########################
			agent1.answerACDCall(rT1, tT1);

			//###########################
			agent1.consultTransfer(irn2.irnNum);
			agent3.answerACDCall(rT2, tT2);
			agent1.confirmTransfer();
			wait(20, "Talk after confirmation of consult-transfer");

			//############################
			agent3.disconnectByWebAgent();
			//agent3.wrapupEndWith2WrapupCodes(wT2);

		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
	}



}
