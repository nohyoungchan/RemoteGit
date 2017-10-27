package HBTestesNG.TestCases;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import HBTestesNG.BaseObjects.*;

@Test(groups= {"VoiceACD_Conference_ClassLevel"})
public class Test_VoiceACD_Conference extends TestCaseObject {
	String className;
	

	Test_VoiceACD_Conference() throws Exception{
		log.info("* Construction: Test_VoiceACD_Conference");
		className = "Test_VoiceACD_Conference";

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
	
	@Parameters({"rT1", "tT1", "rT2", "tT2", "wT2"})
	public void VoiceACD_CConference_ToExt_SameGroup(int rT1, int tT1, int rT2, int tT2, int wT2) throws Exception {
		String testName = "VoiceACD->Ans->Consult Conference to Ext on Same Group";
		if(startTestCase(testName).contains("no")) skipTest(testName, "Skipping because user want to end test => " + testName);
		//#########################
		try{
			agent1.resumeAgent();
			agent2.releaseAgentSecondCode();
			agent3.resumeAgent();
			
			//###########################
			customer1.makeACDCall(irn1.didNum);
			//customer1.makeCallAfterDrop(irn1.didNum);
					
			//##########################
			agent1.answerACDCall(rT1, tT1);
			
			//###########################
			agent2.resumeAgent();
			agent1.consultConference_ByNumber(agent2.extension);
			agent2.answerACDCall(rT2, tT2);
			agent1.confirmConference();
			wait(10, "Talk after confirmation of consult-Conference");
	
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
	public void VoiceACD_CConference_ToExt_SameGroup_byName(int rT1, int tT1, int rT2, int tT2, int wT2) throws Exception {
		String testName = "VoiceACD->Ans->Consult Conference to Ext on Same Group";
		if(startTestCase(testName).contains("no")) skipTest(testName, "Skipping because user want to end test => " + testName);
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
			agent1.confirmConference();
			wait(10, "Talk after confirmation of consult-Conference");
	
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
	public void VoiceACD_CConference_ToExt_DifferentGroup(int rT1, int tT1, int rT2, int tT2) throws Exception {
		String testName = "VoiceACD->Ans->Consult Conference to Ext on Different Group";
		if(startTestCase(testName).contains("no")) skipTest(testName, "Skipping because user want to end test => " + testName);
		
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
			agent1.confirmConference();
			wait(10, "Talk after confirmation of consult-Conference");
	
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
	public void VoiceACD_CConference_ToExt_DifferentGroup_byName(int rT1, int tT1, int rT2, int tT2) throws Exception {
		String testName = "VoiceACD->Ans->Consult Conference to Ext on Different Group by Name";
		if(startTestCase(testName).contains("no")) skipTest(testName, "Skipping because user want to end test => " + testName);
		
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
			agent1.confirmConference();
			wait(10, "Talk after confirmation of consult-Conference");
	
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
	public void VoiceACD_CConference_ToExternal(int rT1, int tT1, int rT2, int tT2) throws Exception {

		String testName = "VoiceACD->Ans->Consult Conference to External number";
		if(startTestCase(testName).contains("no")) skipTest(testName, "Skipping because user want to end test => " + testName);
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
			agent1.confirmConference();
			wait(10, "Talk after confirmation of consult-Conference");

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
	public void VoiceACD_CConference_ToIRN_SameGroup(int rT1, int tT1, int rT2, int tT2, int wT2) throws Exception {

		String testName = "VoiceACD->Ans->Consult Conference to IRN on Same Group ";
		if (thisCaseIsNotSupported(testName + "This test case is only supported on PCM/Physical phone")) return;
		if(startTestCase(testName).contains("no")) skipTest(testName, "Skipping because user want to end test => " + testName);
		
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
			agent1.confirmConference();
			wait(10, "Talk after confirmation of consult-Conference");

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
	public void VoiceACD_CConference_ToIRN_DifferentGroup(int rT1, int tT1, int rT2, int tT2, int wT2) throws Exception {
		String testName = "VoiceACD->Ans->Consult Conference to IRN on Different Group ";
		if(startTestCase(testName).contains("no")) skipTest(testName, "Skipping because user want to end test => " + testName);
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
			agent1.confirmConference();
			wait(10, "Talk after confirmation of consult-Conference");

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
	public void VoiceACD_CConference_All(int rT1, int tT1, int rT2, int tT2, int wT2) throws Exception {
		String testName = "VoiceACD->Ans->Consult Conference : All test cases";
		skipTest(testName, "Skipping because this is loop test: no fit for functional test");
		if(startTestCase(testName).contains("no")) skipTest(testName, "Skipping because user want to end test => " + testName);
		//#########################
		try{
			for (int i = 1; i < 10001 ; i++) {
				log.info("@@@@ " + i + "th try ==> ");
				VoiceACD_CConference_ToExt_DifferentGroup_byName(10, 10, 10, 10);	
			}
				
		
		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
	}



}
