
package HBTestesNG.TestCases;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;

import HBTestesNG.BaseObjects.*;import org.testng.SkipException;

@Test(groups= {"VoiceACD_Reverse_TransferConference_ClassLevel"})
public class Test_VoiceACD_TransferConference_Reverse extends TestCaseObject {	
	

	Test_VoiceACD_TransferConference_Reverse() throws Exception{
		log.info("* Construction: Test_VoiceACD_Transfer");

	}
	@BeforeClass
	public void beforeClass() throws Exception {
		log.info("* Before Class: Test_VoiceACD_Transfer");
		InitializeAllVariables(); 
	}

  
	@AfterClass
	public void afterClass() throws Exception {
		log.info("* After Class: Test_VoiceACD_Transfer");
	}
	
	
	@Parameters({"rT1", "tT1", "rT2", "tT2", "wT2"})
	public void VoiceACD_CTransfer_ToExt_SameGroup_Reverse(int rT1, int tT1, int rT2, int tT2, int wT2) throws Exception {
		
		String testName = "VoiceACD->Ans->Consult Transfer to Ext on Same Group";
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
			agent2.hold(10);
			agent1.confirmTransfer();
			agent2.answerACDCall(rT1, tT1);

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
	public void VoiceACD_CTransfer_ToExt_DifferentGroup_Reverse(int rT1, int tT1, int rT2, int tT2) throws Exception {
		
		String testName = "VoiceACD->Ans->Consult Transfer to Ext on Different Group";
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
			agent3.hold(10);
			agent1.confirmTransfer();
			agent3.answerACDCall(rT1, tT1);

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
	public void VoiceACD_CTransfer_ToExternal_Reverse(int rT1, int tT1, int rT2, int tT2) throws Exception {
		
		String testName = "VoiceACD->Ans->Consult Transfer to External number";
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
			agent3.hold(10);
			agent1.confirmTransfer();
			//External call can be unhold instead of hold->disconnect->answer
			agent3.unHold(10);



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
	public void VoiceACD_CTransfer_ToIRN_SameGroup_Reverse(int rT1, int tT1, int rT2, int tT2, int wT2) throws Exception {
		
		String testName = "VoiceACD->Ans->Consult Transfer to IRN on Same Group ";
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
			agent2.hold(10);
			agent1.confirmTransfer();
			agent2.answerACDCall(rT1, tT1);

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
	public void VoiceACD_CTransfer_ToIRN_DifferentGroup_Reverse(int rT1, int tT1, int rT2, int tT2, int wT2) throws Exception {
		
		String testName = "VoiceACD->Ans->Consult Transfer to IRN on Different Group ";
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
			agent3.hold(10);
			agent1.confirmTransfer();
			agent3.answerACDCall(rT1, tT1);

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
	public void VoiceACD_CConference_ToExt_SameGroup_Reverse(int rT1, int tT1, int rT2, int tT2, int wT2) throws Exception {
		String testName = "VoiceACD->Ans->Consult Conference to Ext on Same Group";
		if(startTestCase(testName).contains("no")) skipTest("Skipping because user want to end test => " + testName);
		skipTest("@@ Skip this test because of a defect: ENG-467917");
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
			agent1.consultConference_ByNumber(agent2.extension);
			agent2.answerACDCall(rT2, tT2);
			agent2.hold(10);
			agent1.confirmConference();
			agent2.answerACDCall(rT1, tT1);
	
			//############################
			agent2.disconnectByWebAgent();
			wait(10, "Talk after 2nd agent hangs up for the conference");
			agent1.disconnectByWebAgent();
			agent1.wrapupEndWith2WrapupCodes(wT2);
		
		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
	}
	
	@Parameters({"rT1", "tT1", "rT2", "tT2"})
	public void VoiceACD_CConference_ToExt_DifferentGroup_Reverse(int rT1, int tT1, int rT2, int tT2) throws Exception {
		String testName = "VoiceACD->Ans->Consult Conference to Ext on Different Group";
		if(startTestCase(testName).contains("no")) skipTest("Skipping because user want to end test => " + testName);
		skipTest("@@ Skip this test because of a defect: ENG-467917");
		try{
			//#########################
			agent1.resumeAgent();
			agent2.releaseAgentSecondCode();
			agent3.resumeAgent();
			
			//###########################
			customer1.makeACDCall(irn1.didNum);
					
			//##########################
			agent1.answerACDCall(rT1, tT1);
			
			//###########################
			agent1.consultConference_ByNumber(agent3.extension);
			agent3.answerACDCall(rT2, tT2);
			agent3.hold(10);
			agent1.confirmConference();
			agent3.answerACDCall(rT1, tT1);
	
			//############################
			agent3.disconnectByWebAgent();
			wait(10, "Talk after 2nd agent hangs up for the conference");
			agent1.disconnectByWebAgent();
			agent1.wrapupEndWith2WrapupCodes(10);
		
		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
	}
	
	@Parameters({"rT1", "tT1", "rT2", "tT2"})
	public void VoiceACD_CConference_ToExternal_Reverse(int rT1, int tT1, int rT2, int tT2) throws Exception {

		String testName = "VoiceACD->Ans->Consult Conference to External number";
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
			agent1.consultConference_ByNumber(agent3.did);
			agent3.answerACDCall(rT2, tT2);
			agent3.hold(10);
			agent1.confirmConference();
			agent3.unHold(10);

			//############################
			agent3.disconnectByWebAgent();
			wait(10, "Talk after 2nd agent hangs up for the conference");
			agent1.disconnectByWebAgent();
			agent1.wrapupEndWith2WrapupCodes(10);
		
		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
	}
	
	
	
	@Parameters({"rT1", "tT1", "rT2", "tT2", "wT2"})
	public void VoiceACD_CConference_ToIRN_SameGroup_Reverse(int rT1, int tT1, int rT2, int tT2, int wT2) throws Exception {

		String testName = "VoiceACD->Ans->Consult Conference to IRN on Same Group ";
		if(startTestCase(testName).contains("no")) skipTest("Skipping because user want to end test => " + testName);
		if (thisCaseIsNotSupported()) return;
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
			agent1.consultConference_ByNumber(irn1.irnNum);
			agent2.answerACDCall(rT2, tT2);
			agent2.hold(10);
			agent1.confirmConference();
			agent2.answerACDCall(rT1, tT1);

			//############################
			agent2.disconnectByWebAgent();
			wait(10, "Talk after 2nd agent hangs up for the conference");
			agent1.disconnectByWebAgent();
			agent1.wrapupEndWith2WrapupCodes(wT2);
		
		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
	}
	
	@Parameters({"rT1", "tT1", "rT2", "tT2", "wT2"})
	public void VoiceACD_CConference_ToIRN_DifferentGroup_Reverse(int rT1, int tT1, int rT2, int tT2, int wT2) throws Exception {
		String testName = "VoiceACD->Ans->Consult Conference to IRN on Different Group ";
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
			agent1.consultConference_ByNumber(irn2.irnNum);
			agent3.answerACDCall(rT2, tT2);
			agent3.hold(10);
			agent1.confirmConference();
			agent3.answerACDCall(rT1, tT1);

			//############################
			agent3.disconnectByWebAgent();
			wait(10, "Talk after 2nd agent hangs up for the conference");
			agent1.disconnectByWebAgent();
			agent1.wrapupEndWith2WrapupCodes(wT2);
		
		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
	}


	@Parameters({"rT1", "tT1", "rT2", "tT2", "wT2"})
	public void VoiceACD_CTransfer_ToExt_SameGroup_Reverse_byName(int rT1, int tT1, int rT2, int tT2, int wT2) throws Exception {
		
		String testName = "VoiceACD->Ans->Consult Transfer to Ext on Same Group_byName";
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
			agent2.hold(10);
			agent1.confirmTransfer();
			agent2.answerACDCall(rT1, tT1);

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
	public void VoiceACD_CTransfer_ToExt_DifferentGroup_Reverse_byName(int rT1, int tT1, int rT2, int tT2) throws Exception {
		
		String testName = "VoiceACD->Ans->Consult Transfer to Ext on Different Group_byName";
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
			agent3.hold(10);
			agent1.confirmTransfer();
			agent3.answerACDCall(rT1, tT1);

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
	public void VoiceACD_CConference_ToExt_SameGroup_Reverse_byName(int rT1, int tT1, int rT2, int tT2, int wT2) throws Exception {
		String testName = "VoiceACD->Ans->Consult Conference to Ext on Same Group_byName";
		if(startTestCase(testName).contains("no")) skipTest("Skipping because user want to end test => " + testName);
		skipTest("@@ Skip this test because of a defect: ENG-467917");
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

			agent1.consultConference_ByAgentName(agent2.username);
			agent2.answerACDCall(rT2, tT2);
			agent2.hold(10);
			agent1.confirmConference();
			agent2.unHold(10);

	
			//############################
			agent2.disconnectByWebAgent();
			wait(10, "Talk after 2nd agent hangs up for the conference");
			agent1.disconnectByWebAgent();
			agent1.wrapupEndWith2WrapupCodes(wT2);
		
		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
	}
	
	@Parameters({"rT1", "tT1", "rT2", "tT2"})
	public void VoiceACD_CConference_ToExt_DifferentGroup_Reverse_byName(int rT1, int tT1, int rT2, int tT2) throws Exception {
		String testName = "VoiceACD->Ans->Consult Conference to Ext on Different Group_byName";
		if(startTestCase(testName).contains("no")) skipTest("Skipping because user want to end test => " + testName);
		skipTest("@@ Skip this test because of a defect: ENG-467917");
		try{
			//#########################
			agent1.resumeAgent();
			agent2.releaseAgentSecondCode();
			agent3.resumeAgent();
			
			//###########################
			customer1.makeACDCall(irn1.didNum);
					
			//##########################
			agent1.answerACDCall(rT1, tT1);
			
			//###########################
			agent1.consultConference_ByAgentName(agent3.username);
			agent3.answerACDCall(rT2, tT2);
			agent3.hold(10);
			agent1.confirmConference();
			agent3.answerACDCall(rT1, tT1);
	
			//############################
			agent3.disconnectByWebAgent();
			wait(10, "Talk after 2nd agent hangs up for the conference");
			agent1.disconnectByWebAgent();
			agent1.wrapupEndWith2WrapupCodes(10);
		
		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
	}
	


}
