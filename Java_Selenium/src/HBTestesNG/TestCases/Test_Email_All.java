package HBTestesNG.TestCases;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;

import HBTestesNG.BaseObjects.*;import org.testng.SkipException;

@Test(groups= {"Test_Email_All_ClassLevel"})
public class Test_Email_All extends TestCaseObject {

	Test_Email_All() throws Exception{
		log.info("* Construction: Test_Email_All");

	}
	
	
	@BeforeClass
	public void beforeClass() throws Exception {
		log.info("* Before Class: Test_Email_All");
		try { 
			InitializeAllVariables(); 
			
			 if (stopTest.contains("yes")) {
				 log.info("@@ Stop is requested, so skip the rest" );
			 }else{
				 emailCustomer1.logIntoEmailClient();
				 emailCustomer1.minimizeBrowser();
			 }
		}catch(Exception e){
			log.info("@@@ I am handling General exception and stopping all test cases=>"+ e.toString());
			stopTest= "yes";
		}
    }
	
	@AfterClass
	public void afterClass() throws Exception {
		log.info("* After Class: Test_Email_All");
		//emailCustomer1.maximizeBrowser();
		//emailCustomer1.signOutEmailClient();
	}
	
	
	
	@Parameters({"rT1", "tT1", "wT1"})
	public void Email_NoQ_Answer(int rT1, int tT1, int wT1) throws Exception {
		String testName = "Test Case: Email->NoQ->Answered";
		if(startTestCase(testName).contains("no")) skipTest("Skipping because user want to end test => " + testName);
		
		//#########################
		//resetAllActors(testName);
		agent1.resumeAgent();
		agent2.releaseAgentSecondCode();
		agent3.releaseAgentThirdCode();

		try{
			
			emailCustomer1.sendEmail();
			
			//##########################
			agent1.answerEmail(rT1, tT1);
			
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
	

	
		
	@Parameters({"qT1", "rT1", "tT1", "wT1"})
	public void Email_Q_Answer(int qT1, int rT1, int tT1, int wT1) throws Exception {
		String testName = "Email->Q->Answered";
		if(startTestCase(testName).contains("no")) skipTest("Skipping because user want to end test => " + testName);
		//#########################
		agent1.releaseAgent();
		agent2.releaseAgentSecondCode();
		agent2.releaseAgentThirdCode();
		
		try{
			emailCustomer1.sendEmail();
			wait(qT1, "Queue");
			agent1.resumeAgent();
					
			//##########################
			agent1.answerEmail(rT1, tT1);
			
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
	public void Email_NoQ_Answer_NoWrapTime(int rT1, int tT1) throws Exception {
		String testName = "Email->NoQ->Answered with No Wrap time";
		if(startTestCase(testName).contains("no")) skipTest("Skipping because user want to end test => " + testName);
		//#########################
		//aa.services.get(3)  <= This is email service
		sup1.AllType_Max_LogIn_changeWrapAndFRTime_LogOut_Min("emailservice", aa.services.get(3).name, "0", "40");
		
		
		//sup1.AllType_Max_LogIn_changeWrapAndFRTime_LogOut_Min("emailservice", "Y_EService1", "0", "40");
		//log.info("aa.service3.get3 ==> " + aa.services.get(3).name);
		
		 
		//#########################
		agent1.resumeAgent();
		agent2.releaseAgentSecondCode();
		agent3.resumeAgent();
	
		try{
			//##########################
			emailCustomer1.sendEmail();
			agent1.answerEmail(rT1, tT1);
			
			//############################
			agent1.disconnectByWebAgent();
			wait(2);
			sup1.AllType_Max_LogIn_changeWrapAndFRTime_LogOut_Min("emailservice", aa.services.get(3).name, "20", "30");
		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			resetAllActors(testName);
		}finally{
			endTestCase(testName);
	  }
	}
	

	@Parameters({"rT1", "tT1", "rT2", "tT2", "wT2"})
	public void Email_BTransfer_ToExt_SameGroup(int rT1, int tT1, int rT2, int tT2, int wT2) throws Exception {
	
		String testName = "Email->Ans->Blind Transfer to Ext on Same Group ";
		if(startTestCase(testName).contains("no")) skipTest("Skipping because user want to end test => " + testName);
		//#########################
		
		try{
			agent1.resumeAgent();
			agent2.releaseAgentSecondCode();
			agent3.resumeAgent();

			//###########################
			emailCustomer1.sendEmail();

			//##########################
			agent1.answerEmail(rT1, tT1);

			//###########################
			agent2.resumeAgent();
			agent1.blindTransfer(agent2.extension);
			agent2.answerEmail(rT2, tT2);


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
	public void Email_R_Q_Overflow_R_Ans_2ndGroup(int rT1, int tT1, int rT2, int tT2, int wT2) throws Exception {
		
		String testName = "Email->Ring->Q->Overflow->Ring->Answer on the 2nd group";
		if(startTestCase(testName).contains("no")) skipTest("Skipping because user want to end test => " + testName);
		//#########################
		
		try{
			agent1.resumeAgent();
			agent2.releaseAgentSecondCode();
			agent3.resumeAgent();;

			//###########################
			emailCustomer1.sendEmail();

			//##########################
			emailCustomer1.wait(60, "Wait for Overflow timeout: This includes Ring before forced-released time");
			agent3.answerEmail(rT2, tT2);

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
	public void Email_R_Q_Overflow_Q_R_Ans_2ndGroup(int rT1, int tT1, int rT2, int tT2, int wT2) throws Exception {
		
		String testName = "Email->Ring->Q->Overflow->Q->Ring->Answer on the 2nd group";
		if(startTestCase(testName).contains("no")) skipTest("Skipping because user want to end test => " + testName);
		//#########################
		
		try{
			agent1.resumeAgent();
			agent2.releaseAgentSecondCode();
			agent2.releaseAgentThirdCode();

			//###########################
			emailCustomer1.sendEmail();

			//##########################
			emailCustomer1.wait(80, "Wait for Overflow timeout");
			agent3.resumeAgent();
			agent3.answerEmail(rT2, tT2);

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
	public void Email_Q_Overflow_R_Ans_2ndGroup(int rT1, int tT1, int rT2, int tT2, int wT2) throws Exception {
		
		String testName = "Email->Q->Overflow->Ring->Answer on the 2nd group";
		if(startTestCase(testName).contains("no")) skipTest("Skipping because user want to end test => " + testName);
		//#########################
		
		try{
			agent1.releaseAgent();
			agent2.releaseAgentSecondCode();
			agent3.resumeAgent();;

			//###########################
			emailCustomer1.sendEmail();

			//##########################
			emailCustomer1.wait(60, "Wait for Overflow timeout");
			agent3.answerEmail(rT2, tT2);

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
	public void Email_Q_Overflow_R_Ans_1stGroup(int rT1, int tT1, int rT2, int tT2, int wT2) throws Exception {
		
		String testName = "Email->Q->Overflow->Ring->Answer on the 2nd group";
		if(startTestCase(testName).contains("no")) skipTest("Skipping because user want to end test => " + testName);
		//#########################
		
		try{
			agent1.releaseAgent();
			agent2.releaseAgentSecondCode();
			agent2.releaseAgentThirdCode();

			//###########################
			emailCustomer1.sendEmail();

			//##########################
			emailCustomer1.wait(60, "Wait for Overflow timeout");
			agent1.resumeAgent();
			agent1.answerEmail(rT1, tT1);

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
	public void Email_Q_OverInterflow_Q_R_Ans_3rdGroup(int rT1, int tT1, int rT2, int tT2, int wT2) throws Exception {
		
		String testName = "Email->Q->OverInterflow->Q->Ring->Answer on the 3rd group";
		if(startTestCase(testName).contains("no")) skipTest("Skipping because user want to end test => " + testName);
		//#########################
		
		try{
			agent1.releaseAgent();
			agent2.releaseAgentSecondCode();
			agent3.releaseAgentThirdCode();;

			//###########################
			emailCustomer1.sendEmail();

			//##########################
			emailCustomer1.wait(120, "Wait for Overflow and Interflow timeout");
			agent3.resumeAgent();
			agent3.answerEmail(rT2, tT2);

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
