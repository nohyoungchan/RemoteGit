package HBTestesNG.TestCases;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;


import HBTestesNG.BaseObjects.*;

import org.testng.Assert;
import org.testng.SkipException;

@Test(groups= {"VoiceACD_Conference_WrapTimeZero_ClassLevel"})
public class Test_VoiceACD_Conference_WrapTimeZero extends TestCaseObject {

	Test_VoiceACD_Conference_WrapTimeZero() throws Exception{
		log.info("* Construction: Test_VoiceACD_Conference_WrapTimeZero");

	}
	@BeforeClass
	public void beforeClass() throws Exception {
		log.info("* Before Class: Test_VoiceACD_Conference_WrapTimeZero");
		InitializeAllVariables(); 
		
		try{
			sup1.Max_LogIn_changeWrapAndFRTime_LogOut_Min(aa.services.get(0).name, "0", "40");
		}catch (Exception e){
			log.error("@@@ Error here ==> " + e.toString());
		}
	  
	}

  
	@AfterClass
	public void afterClass() throws Exception {
		log.info("* After Class: Test_VoiceACD_Conference_WrapTimeZero");
		sup1.Max_LogIn_changeWrapAndFRTime_LogOut_Min(aa.services.get(0).name, "20", "30");
	}
	
	
	@Parameters({"rT1", "tT1", "rT2", "tT2", "wT2"})
	public void VoiceACD_CConference_ToExt_SameGroup(int rT1, int tT1, int rT2, int tT2, int wT2) throws Exception {
		String testName = "VoiceACD->Ans->Consult Conference to Ext on Same Group_WrapTimeZero";
		if(startTestCase(testName).contains("no")) skipTest("Skipping because user want to end test => " + testName);
		//startTestCase( Test Case: VoiceACD->Ans->Consult Conference to Ext on Same Group_WrapTimeZero");
		//#########################
		agent1.resumeAgent();
		agent2.releaseAgentSecondCode();
		agent3.resumeAgent();
		
		try{
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
		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
	}
	
	@Parameters({"rT1", "tT1", "rT2", "tT2"})
	public void VoiceACD_CConference_ToExt_DifferentGroup(int rT1, int tT1, int rT2, int tT2) throws Exception {
		String testName = "VoiceACD->Ans->Consult Conference to Ext on Different Group_WrapTimeZero";
		if(startTestCase(testName).contains("no")) skipTest("Skipping because user want to end test => " + testName);
		//startTestCase( Test Case: VoiceACD->Ans->Consult Conference to Ext on Different Group_WrapTimeZero");
		//#########################
		agent1.resumeAgent();
		agent2.releaseAgentSecondCode();
		agent3.resumeAgent();
		
		try{
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
		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
	}
	
	@Parameters({"rT1", "tT1", "rT2", "tT2"})
	public void VoiceACD_CConference_ToExternal(int rT1, int tT1, int rT2, int tT2) throws Exception {
		String testName = "VoiceACD->Ans->Consult Conference to External number_WrapTimeZero";
		if(startTestCase(testName).contains("no")) skipTest("Skipping because user want to end test => " + testName);
		//startTestCase( Test Case: VoiceACD->Ans->Consult Conference to External number_WrapTimeZero");
		//#########################
		agent1.resumeAgent();
		agent2.releaseAgentSecondCode();
		agent3.resumeAgent();
		
		try{
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
			//agent1.wrapupEndWith2WrapupCodes(10);
		
		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
	}
	
	
	
	@Parameters({"rT1", "tT1", "rT2", "tT2", "wT2"})
	public void VoiceACD_CConference_ToIRN_SameGroup(int rT1, int tT1, int rT2, int tT2, int wT2) throws Exception {
		String testName = "VoiceACD->Ans->Consult Conference to IRN on Same Group_WrapTimeZero";
		if(startTestCase(testName).contains("no")) skipTest("Skipping because user want to end test => " + testName);
		
		if (thisCaseIsNotSupported()) return;
		
		//#########################
		agent1.resumeAgent();
		agent2.releaseAgentSecondCode();
		agent3.resumeAgent();
		
		try{
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
		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
	}
	
	@Parameters({"rT1", "tT1", "rT2", "tT2", "wT2"})
	public void VoiceACD_CConference_ToIRN_DifferentGroup(int rT1, int tT1, int rT2, int tT2, int wT2) throws Exception {
		String testName = "VoiceACD->Ans->Consult Conference to IRN on Different Group_WrapTimeZero";
		if(startTestCase(testName).contains("no")) skipTest("Skipping because user want to end test => " + testName);
		//#########################
		agent1.resumeAgent();
		agent2.releaseAgentSecondCode();
		agent3.resumeAgent();
		
		try{
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
		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
	}



}
