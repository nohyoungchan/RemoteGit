package HBTestesNG.TestCases;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import HBTestesNG.BaseObjects.*;


@Test(groups= {"VoiceOACD_ByAgandon_Conference_WrapTimeZero_DisconnectByAIC_ClassLevel"})
public class Test_VoiceOACD_ByAbandon_Transfer_WrapTimeZero_DisconnectByAIC extends TestCaseObject {
	
	Integer callbackAbandonSecInt;
	String callbackAbandonSecStr;

	
	@BeforeClass
	public void beforeClass() throws Exception {
		log.info("###### Before Class: Test_VoiceOACD_ByAbandon_Transfer_WrapTimeZero_DisconnectByAIC_DisconnectByAIC");
		String wrapTime, frTime;
		wrapTime = "0";
		frTime = "40";
		callbackAbandonSecStr = "10";
		callbackAbandonSecInt = Integer.parseInt(callbackAbandonSecStr);
		InitializeAllVariables(); 
		//sup1.Max_LogIn_EnableAbandon_LogOut_Min(AllActors.services.get(0).name, callbackAbandonSecStr);
		sup1.Max_LogIn_ChangeWrapFR_EnableAbandon_SetOutboundPreferenceForAbandonLogOut_Min(AllActors.services.get(0).name, wrapTime, frTime, callbackAbandonSecStr);
		//sup1.Max_LogIn_changeWrapAndFRTime_LogOut_Min(AllActors.services.get(0).name, wrapTime, frTime);
		
	  
	}

  
	@AfterClass
	public void afterClass() throws Exception {
		log.info("###### After Class: Test_VoiceOACD_ByAbandon_Transfer_WrapTimeZero_DisconnectByAIC_DisconnectByAIC");
		String wrapTime, frTime;
		wrapTime = "20";
		frTime = "30";
		sup1.Max_LogIn_ChangeWrapFR_DisableAbandon_LogOut_Min(AllActors.services.get(0).name, wrapTime, frTime);
		//sup1.Max_LogIn_DisableAbandon_LogOut_Min(AllActors.services.get(0).name);
		//sup1.Max_LogIn_changeWrapAndFRTime_LogOut_Min(AllActors.services.get(0).name, wrapTime, frTime);
	}
	
	
	@Parameters({"rT1", "tT1", "rT2", "tT2", "wT2"})
	public void VoiceOACD_ByAbandon_BTransfer_ToExt_SameGroup(int rT1, int tT1, int rT2, int tT2, int wT2) throws Exception {
		String testName = "VoiceOACD_ByAbandon->Ans->Blind Transfer to Ext on Same Group_WrapTimeZero_DisconnectByAIC_DisconnectByAIC";
		//######################
		String testReady = "yes";
		if (testReady.contains("no")) skipTest(testName, "Skipping because it is not ready");
		
		//######################
		if(startTestCase(testName).contains("no")) skipTest(testName, "Skipping because user want to end test => " + testName);
		//startTestCase( Test Case: VoiceOACD_ByAbandon->Ans->Blind Transfer to Ext on Same Group_WrapTimeZero_DisconnectByAIC_DisconnectByAIC");
		//#########################
		agent1.resumeAgent();
		agent2.releaseAgentSecondCode();
		agent3.resumeAgent();
		
		try{
			//###########################
			customer1.makeACDCall(irn1.didNum);
			agent1.waitTillRingPlusSec(callbackAbandonSecInt+1);
			customer1.abandonCall();
			agent1.resumeAgent(); //This is needed in case agent1 becomes forced-released
			
			customer1.answerCall(5);
					
			//##########################
			agent1.answerACDCall(rT1, tT1);
			
			//###########################
			agent2.resumeAgent();
			agent1.blindTransfer(agent2.extension);
			agent2.answerACDCall(rT2, tT2);
			wait(10, "Talk after confirmation of consult-Conference");
	
			//############################
			agent2.disconnectOACD();
			//customer1.dropCall();
		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
	}
	
	@Parameters({"rT1", "tT1", "rT2", "tT2"})
	public void VoiceOACD_ByAbandon_BTransfer_ToExt_DifferentGroup(int rT1, int tT1, int rT2, int tT2) throws Exception {
		String testName = "VoiceOACD_ByAbandon->Ans->Blind Transfer to Ext on Different Group";
		
		//######################
		String testReady = "yes";
		if (testReady.contains("no")) skipTest(testName, "Skipping because it is not ready");
		
		//######################
		if(startTestCase(testName).contains("no")) skipTest(testName, "Skipping because user want to end test => " + testName);

		//startTestCase( Test Case: VoiceOACD_ByAbandon->Ans->Blind Transfer to Ext on Different Group_WrapTimeZero_DisconnectByAIC_DisconnectByAIC");
		//#########################
		agent1.resumeAgent();
		agent2.releaseAgentSecondCode();
		agent3.resumeAgent();
		
		try{
			//###########################
			customer1.makeACDCall(irn1.didNum);
			agent1.waitTillRingPlusSec(callbackAbandonSecInt+1);
			customer1.abandonCall();
			agent1.resumeAgent(); //This is needed in case agent1 becomes forced-released
			
			customer1.answerCall(5);
					
			//##########################
			agent1.answerACDCall(rT1, tT1);
			
			//###########################
			agent1.blindTransfer(agent3.extension);
			agent3.answerACDCall(rT2, tT2);
			wait(10, "Talk after confirmation of consult-Conference");
	
			//############################
			//agent1.wrapupEndWith2WrapupCodes(10);	
			wait(10, "Talk after 2nd agent hangs up for the conference");
			agent3.disconnectByWebAgent();
			//customer1.dropCall();;
		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
	}
	
	@Parameters({"rT1", "tT1", "rT2", "tT2"})
	public void VoiceOACD_ByAbandon_BTransfer_ToExternal(int rT1, int tT1, int rT2, int tT2) throws Exception {
		String testName = "VoiceOACD_ByAbandon->Ans->Blind Transfer to External number_WrapTimeZero_DisconnectByAIC_DisconnectByAIC";
		
		//######################
		String testReady = "yes";
		if (testReady.contains("no")) skipTest(testName, "Skipping because it is not ready");
		
		//######################
		if(startTestCase(testName).contains("no")) skipTest(testName, "Skipping because user want to end test => " + testName);
		//startTestCase( Test Case: VoiceOACD_ByAbandon->Ans->Blind Transfer to External number_WrapTimeZero_DisconnectByAIC_DisconnectByAIC");
		//#########################
		agent1.resumeAgent();
		agent2.releaseAgentSecondCode();
		agent3.resumeAgent();
		
		try{
			//###########################
			customer1.makeACDCall(irn1.didNum);
			agent1.waitTillRingPlusSec(callbackAbandonSecInt+1);
			customer1.abandonCall();
			agent1.resumeAgent(); //This is needed in case agent1 becomes forced-released
			
			customer1.answerCall(5);
					
			//##########################
			agent1.answerACDCall(rT1, tT1);
			
			//###########################
			agent1.blindTransfer(agent3.did);
			agent3.answerACDCall(rT2, tT2);
			wait(10, "Talk after confirmation of consult-Conference");
	
			//############################
			//agent1.wrapupEndWith2WrapupCodes(10);	
			wait(10, "Talk after 2nd agent hangs up for the conference");
			agent3.disconnectByWebAgent();
			//customer1.dropCall();;
		
		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
	}
	
	
	
	@Parameters({"rT1", "tT1", "rT2", "tT2", "wT2"})
	public void VoiceOACD_ByAbandon_BTransfer_ToIRN_SameGroup(int rT1, int tT1, int rT2, int tT2, int wT2) throws Exception {
		String testName = "VoiceOACD_ByAbandon->Ans->Blind Transfer to IRN on Same Group_WrapTimeZero_DisconnectByAIC_DisconnectByAIC";
		
		if (thisCaseIsNotSupported(testName + "This test case is only supported on PCM/Physical phone")) return;
		
		//######################
		String testReady = "yes";
		if (testReady.contains("no")) skipTest(testName, "Skipping because it is not ready");
		
		//######################
		if(startTestCase(testName).contains("no")) skipTest(testName, "Skipping because user want to end test => " + testName);
		
		
		//#########################
		agent1.resumeAgent();
		agent2.releaseAgentSecondCode();
		agent3.resumeAgent();
		
		try{
			//###########################
			customer1.makeACDCall(irn1.didNum);
			agent1.waitTillRingPlusSec(callbackAbandonSecInt+1);
			customer1.abandonCall();
			agent1.resumeAgent(); //This is needed in case agent1 becomes forced-released
			
			customer1.answerCall(5);
			
			//##########################
			agent1.answerACDCall(rT1, tT1);
			
			//###########################
			agent2.resumeAgent();
			agent1.blindTransfer(irn1.irnNum);
			agent2.answerACDCall(rT2, tT2);
			wait(10, "Talk after confirmation of consult-Conference");
	
			//############################
			agent2.disconnectByWebAgent();
			//customer1.dropCall();;
		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
	}
	
	@Parameters({"rT1", "tT1", "rT2", "tT2", "wT2"})
	public void VoiceOACD_ByAbandon_BTransfer_ToIRN_DifferentGroup(int rT1, int tT1, int rT2, int tT2, int wT2) throws Exception {
		String testName = "VoiceOACD_ByAbandon->Ans->Blind Transfer to IRN on Different Group_WrapTimeZero_DisconnectByAIC_DisconnectByAIC";
		
		//######################
		String testReady = "yes";
		if (testReady.contains("no")) skipTest(testName, "Skipping because it is not ready");
		
		//######################
		if(startTestCase(testName).contains("no")) skipTest(testName, "Skipping because user want to end test => " + testName);
		//#########################
		agent1.resumeAgent();
		agent2.releaseAgentSecondCode();
		agent3.resumeAgent();
		
		try{
			//###########################
			customer1.makeACDCall(irn1.didNum);
			agent1.waitTillRingPlusSec(callbackAbandonSecInt+1);
			customer1.abandonCall();
			agent1.resumeAgent(); //This is needed in case agent1 becomes forced-released
			
			customer1.answerCall(5);
					
			//##########################
			agent1.answerACDCall(rT1, tT1);
			
			//###########################
			agent1.blindTransfer(irn2.irnNum);
			agent3.answerACDCall(rT2, tT2);
			wait(10, "Talk after confirmation of consult-Conference");
	
			//############################
			agent3.disconnectByWebAgent();
			//customer1.dropCall();;
		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
	}
	
	@Parameters({"rT1", "tT1", "rT2", "tT2"})
	public void VoiceOACD_ByAbandon_BTransfer_ToExt_DifferentGroup_byName(int rT1, int tT1, int rT2, int tT2) throws Exception {
		String testName = "VoiceOACD_ByAbandon->Ans->Blind Transfer to Ext on Different Group_byName";
		
		//######################
		String testReady = "yes";
		if (testReady.contains("no")) skipTest(testName, "Skipping because it is not ready");
		
		//######################
		if(startTestCase(testName).contains("no")) skipTest(testName, "Skipping because user want to end test => " + testName);

		//startTestCase( Test Case: VoiceOACD_ByAbandon->Ans->Blind Transfer to Ext on Different Group_WrapTimeZero_DisconnectByAIC_DisconnectByAIC");
		//#########################
		agent1.resumeAgent();
		agent2.releaseAgentSecondCode();
		agent3.resumeAgent();
		
		try{
			//###########################
			customer1.makeACDCall(irn1.didNum);
			agent1.waitTillRingPlusSec(callbackAbandonSecInt+1);
			customer1.abandonCall();
			agent1.resumeAgent(); //This is needed in case agent1 becomes forced-released
			
			customer1.answerCall(5);
					
			//##########################
			agent1.answerACDCall(rT1, tT1);
			
			//###########################
			agent1.blindTransfer_byName(agent3.username);
			agent3.answerACDCall(rT2, tT2);
			wait(10, "Talk after confirmation of consult-Conference");
	
			//############################
			//agent1.wrapupEndWith2WrapupCodes(10);	
			wait(10, "Talk after 2nd agent hangs up for the conference");
			agent3.disconnectByWebAgent();
			//customer1.dropCall();;
		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
	}
	
	@Parameters({"rT1", "tT1", "rT2", "tT2", "wT2"})
	public void VoiceOACD_ByAbandon_BTransfer_ToExt_SameGroup_byName(int rT1, int tT1, int rT2, int tT2, int wT2) throws Exception {
		String testName = "VoiceOACD_ByAbandon->Ans->Blind Transfer to Ext on Same Group_WrapTimeZero_DisconnectByAIC_DisconnectByAIC_byName";
		//######################
		String testReady = "yes";
		if (testReady.contains("no")) skipTest(testName, "Skipping because it is not ready");
		
		//######################
		if(startTestCase(testName).contains("no")) skipTest(testName, "Skipping because user want to end test => " + testName);
		//startTestCase( Test Case: VoiceOACD_ByAbandon->Ans->Blind Transfer to Ext on Same Group_WrapTimeZero_DisconnectByAIC_DisconnectByAIC");
		//#########################
		agent1.resumeAgent();
		agent2.releaseAgentSecondCode();
		agent3.resumeAgent();
		
		try{
			//###########################
			customer1.makeACDCall(irn1.didNum);
			agent1.waitTillRingPlusSec(callbackAbandonSecInt+1);
			customer1.abandonCall();
			agent1.resumeAgent(); //This is needed in case agent1 becomes forced-released
			
			customer1.answerCall(5);
					
			//##########################
			agent1.answerACDCall(rT1, tT1);
			
			//###########################
			agent2.resumeAgent();
			agent1.blindTransfer_byName(agent2.username);
			agent2.answerACDCall(rT2, tT2);
			wait(10, "Talk after confirmation of consult-Conference");
	
			//############################
			agent2.disconnectOACD();
			//customer1.dropCall();;
		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
	}
	


	@Parameters({"rT1", "tT1", "rT2", "tT2", "wT2"})
	public void VoiceOACD_ByAbandon_CTransfer_ToExt_SameGroup(int rT1, int tT1, int rT2, int tT2, int wT2) throws Exception {
		String testName = "VoiceOACD_ByAbandon->Ans->Consult Transfer to Ext on Same Group_WrapTimeZero_DisconnectByAIC_DisconnectByAIC";
		//######################
		String testReady = "yes";
		if (testReady.contains("no")) skipTest(testName, "Skipping because it is not ready");
		
		//######################
		if(startTestCase(testName).contains("no")) skipTest(testName, "Skipping because user want to end test => " + testName);
		//startTestCase( Test Case: VoiceOACD_ByAbandon->Ans->Consult Transfer to Ext on Same Group_WrapTimeZero_DisconnectByAIC_DisconnectByAIC");
		//#########################
		agent1.resumeAgent();
		agent2.releaseAgentSecondCode();
		agent3.resumeAgent();
		
		try{
			//###########################
			customer1.makeACDCall(irn1.didNum);
			agent1.waitTillRingPlusSec(callbackAbandonSecInt+1);
			customer1.abandonCall();
			agent1.resumeAgent(); //This is needed in case agent1 becomes forced-released
			
			customer1.answerCall(5);
					
			//##########################
			agent1.answerACDCall(rT1, tT1);
			
			//###########################
			agent2.resumeAgent();
			agent1.consultTransfer(agent2.extension);
			agent2.answerACDCall(rT2, tT2);
			agent1.confirmTransfer();
			wait(10, "Talk after confirmation of consult-Conference");
	
			//############################
			agent2.disconnectOACD();
			//customer1.dropCall();;
		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
	}
	
	@Parameters({"rT1", "tT1", "rT2", "tT2"})
	public void VoiceOACD_ByAbandon_CTransfer_ToExt_DifferentGroup(int rT1, int tT1, int rT2, int tT2) throws Exception {
		String testName = "VoiceOACD_ByAbandon->Ans->Consult Transfer to Ext on Different Group";
		
		//######################
		String testReady = "yes";
		if (testReady.contains("no")) skipTest(testName, "Skipping because it is not ready");
		
		//######################
		if(startTestCase(testName).contains("no")) skipTest(testName, "Skipping because user want to end test => " + testName);

		//startTestCase( Test Case: VoiceOACD_ByAbandon->Ans->Consult Transfer to Ext on Different Group_WrapTimeZero_DisconnectByAIC_DisconnectByAIC");
		//#########################
		agent1.resumeAgent();
		agent2.releaseAgentSecondCode();
		agent3.resumeAgent();
		
		try{
			//###########################
			customer1.makeACDCall(irn1.didNum);
			agent1.waitTillRingPlusSec(callbackAbandonSecInt+1);
			customer1.abandonCall();
			agent1.resumeAgent(); //This is needed in case agent1 becomes forced-released
			
			customer1.answerCall(5);
					
			//##########################
			agent1.answerACDCall(rT1, tT1);
			
			//###########################
			agent1.consultTransfer(agent3.extension);
			agent3.answerACDCall(rT2, tT2);
			agent1.confirmTransfer();
			wait(10, "Talk after confirmation of consult-Conference");
	
			//############################
			//agent1.wrapupEndWith2WrapupCodes(10);	
			wait(10, "Talk after 2nd agent hangs up for the conference");
			agent3.disconnectByWebAgent();
			//customer1.dropCall();;
		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
	}
	
	@Parameters({"rT1", "tT1", "rT2", "tT2"})
	public void VoiceOACD_ByAbandon_CTransfer_ToExternal(int rT1, int tT1, int rT2, int tT2) throws Exception {
		String testName = "VoiceOACD_ByAbandon->Ans->Consult Transfer to External number_WrapTimeZero_DisconnectByAIC_DisconnectByAIC";
		
		//######################
		String testReady = "yes";
		if (testReady.contains("no")) skipTest(testName, "Skipping because it is not ready");
		
		//######################
		if(startTestCase(testName).contains("no")) skipTest(testName, "Skipping because user want to end test => " + testName);
		//startTestCase( Test Case: VoiceOACD_ByAbandon->Ans->Consult Transfer to External number_WrapTimeZero_DisconnectByAIC_DisconnectByAIC");
		//#########################
		agent1.resumeAgent();
		agent2.releaseAgentSecondCode();
		agent3.resumeAgent();
		
		try{
			//###########################
			customer1.makeACDCall(irn1.didNum);
			agent1.waitTillRingPlusSec(callbackAbandonSecInt+1);
			customer1.abandonCall();
			agent1.resumeAgent(); //This is needed in case agent1 becomes forced-released
			
			customer1.answerCall(5);
					
			//##########################
			agent1.answerACDCall(rT1, tT1);
			
			//###########################
			agent1.consultTransfer(agent3.did);
			agent3.answerACDCall(rT2, tT2);
			agent1.confirmTransfer();
			wait(10, "Talk after confirmation of consult-Conference");
	
			//############################
			//agent1.wrapupEndWith2WrapupCodes(10);	
			wait(10, "Talk after 2nd agent hangs up for the conference");
			agent3.disconnectByWebAgent();
			//customer1.dropCall();;
			////agent1.wrapupEndWith2WrapupCodes(10);
		
		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
	}
	
	
	
	@Parameters({"rT1", "tT1", "rT2", "tT2", "wT2"})
	public void VoiceOACD_ByAbandon_CTransfer_ToIRN_SameGroup(int rT1, int tT1, int rT2, int tT2, int wT2) throws Exception {
		String testName = "VoiceOACD_ByAbandon->Ans->Consult Transfer to IRN on Same Group_WrapTimeZero_DisconnectByAIC_DisconnectByAIC";
		if (thisCaseIsNotSupported(testName + "This test case is only supported on PCM/Physical phone")) return;
		//######################
		String testReady = "yes";
		if (testReady.contains("no")) skipTest(testName, "Skipping because it is not ready");
		
		//######################
		if(startTestCase(testName).contains("no")) skipTest(testName, "Skipping because user want to end test => " + testName);
		
		
		
		//#########################
		agent1.resumeAgent();
		agent2.releaseAgentSecondCode();
		agent3.resumeAgent();
		
		try{
			//###########################
			customer1.makeACDCall(irn1.didNum);
			agent1.waitTillRingPlusSec(callbackAbandonSecInt+1);
			customer1.abandonCall();
			agent1.resumeAgent(); //This is needed in case agent1 becomes forced-released
			
			customer1.answerCall(5);
			
			//##########################
			agent1.answerACDCall(rT1, tT1);
			
			//###########################
			agent2.resumeAgent();
			agent1.consultTransfer(irn1.irnNum);
			agent2.answerACDCall(rT2, tT2);
			agent1.confirmTransfer();
			wait(10, "Talk after confirmation of consult-Conference");
	
			//############################
			agent2.disconnectByWebAgent();
			//customer1.dropCall();;
		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
	}
	
	@Parameters({"rT1", "tT1", "rT2", "tT2", "wT2"})
	public void VoiceOACD_ByAbandon_CTransfer_ToIRN_DifferentGroup(int rT1, int tT1, int rT2, int tT2, int wT2) throws Exception {
		String testName = "VoiceOACD_ByAbandon->Ans->Consult Transfer to IRN on Different Group_WrapTimeZero_DisconnectByAIC_DisconnectByAIC";
		
		//######################
		String testReady = "yes";
		if (testReady.contains("no")) skipTest(testName, "Skipping because it is not ready");
		
		//######################
		if(startTestCase(testName).contains("no")) skipTest(testName, "Skipping because user want to end test => " + testName);
		//#########################
		agent1.resumeAgent();
		agent2.releaseAgentSecondCode();
		agent3.resumeAgent();
		
		try{
			//###########################
			customer1.makeACDCall(irn1.didNum);
			agent1.waitTillRingPlusSec(callbackAbandonSecInt+1);
			customer1.abandonCall();
			agent1.resumeAgent(); //This is needed in case agent1 becomes forced-released
			
			customer1.answerCall(5);
					
			//##########################
			agent1.answerACDCall(rT1, tT1);
			
			//###########################
			agent1.consultTransfer(irn2.irnNum);
			agent3.answerACDCall(rT2, tT2);
			agent1.confirmTransfer();
			wait(10, "Talk after confirmation of consult-Conference");
	
			//############################
			agent3.disconnectByWebAgent();
			//customer1.dropCall();;
		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
	}
	
	@Parameters({"rT1", "tT1", "rT2", "tT2"})
	public void VoiceOACD_ByAbandon_CTransfer_ToExt_DifferentGroup_byName(int rT1, int tT1, int rT2, int tT2) throws Exception {
		String testName = "VoiceOACD_ByAbandon->Ans->Consult Transfer to Ext on Different Group_byName";
		
		//######################
		String testReady = "yes";
		if (testReady.contains("no")) skipTest(testName, "Skipping because it is not ready");
		
		//######################
		if(startTestCase(testName).contains("no")) skipTest(testName, "Skipping because user want to end test => " + testName);

		//startTestCase( Test Case: VoiceOACD_ByAbandon->Ans->Consult Transfer to Ext on Different Group_WrapTimeZero_DisconnectByAIC_DisconnectByAIC");
		//#########################
		agent1.resumeAgent();
		agent2.releaseAgentSecondCode();
		agent3.resumeAgent();
		
		try{
			//###########################
			customer1.makeACDCall(irn1.didNum);
			agent1.waitTillRingPlusSec(callbackAbandonSecInt+1);
			customer1.abandonCall();
			agent1.resumeAgent(); //This is needed in case agent1 becomes forced-released
			
			customer1.answerCall(5);
					
			//##########################
			agent1.answerACDCall(rT1, tT1);
			
			//###########################
			agent1.consultTransfer_byName(agent3.username);
			agent3.answerACDCall(rT2, tT2);
			agent1.confirmTransfer();
			wait(10, "Talk after confirmation of consult-Conference");
	
			//############################
			//agent1.wrapupEndWith2WrapupCodes(10);	
			wait(10, "Talk after 2nd agent hangs up for the conference");
			agent3.disconnectByWebAgent();
			//customer1.dropCall();;
		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
	}
	
	@Parameters({"rT1", "tT1", "rT2", "tT2", "wT2"})
	public void VoiceOACD_ByAbandon_CTransfer_ToExt_SameGroup_byName(int rT1, int tT1, int rT2, int tT2, int wT2) throws Exception {
		String testName = "VoiceOACD_ByAbandon->Ans->Consult Transfer to Ext on Same Group_WrapTimeZero_DisconnectByAIC_DisconnectByAIC_byName";
		//######################
		String testReady = "yes";
		if (testReady.contains("no")) skipTest(testName, "Skipping because it is not ready");
		
		//######################
		if(startTestCase(testName).contains("no")) skipTest(testName, "Skipping because user want to end test => " + testName);
		//startTestCase( Test Case: VoiceOACD_ByAbandon->Ans->Consult Transfer to Ext on Same Group_WrapTimeZero_DisconnectByAIC_DisconnectByAIC");
		//#########################
		agent1.resumeAgent();
		agent2.releaseAgentSecondCode();
		agent3.resumeAgent();
		
		try{
			//###########################
			customer1.makeACDCall(irn1.didNum);
			agent1.waitTillRingPlusSec(callbackAbandonSecInt+1);
			customer1.abandonCall();
			agent1.resumeAgent(); //This is needed in case agent1 becomes forced-released
			
			customer1.answerCall(5);
					
			//##########################
			agent1.answerACDCall(rT1, tT1);
			
			//###########################
			agent2.resumeAgent();
			agent1.consultTransfer_byName(agent2.username);
			agent2.answerACDCall(rT2, tT2);
			agent1.confirmTransfer();
			wait(10, "Talk after confirmation of consult-Conference");
	
			//############################
			agent2.disconnectOACD();
			//customer1.dropCall();;
		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
	}
	




}
