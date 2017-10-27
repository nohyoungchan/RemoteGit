package HBTestesNG.TestCases;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;

import HBTestesNG.BaseObjects.*;
import org.testng.Assert;


@Test(groups= {"Chat_All_ClassLevel"})
public class Test_Chat_All extends TestCaseObject {

	Test_Chat_All() throws Exception{
		log.info("* Construction: Test_Chat_All");

	}
	
	
	@BeforeClass
	public void beforeClass() throws Exception {
		log.info("* Before Class: Test_Chat_All");
		InitializeAllVariables(); 
    }
	
	@AfterClass
	public void afterClass() throws Exception {
		log.info("* After Class: Test_Chat_All");
	}
	
	@Parameters({"rT1"})
	public void Chat_Ring_Abandon(int rT1) throws Exception {
		String testName = "Chat->Ring->Disconnect";
		if(startTestCase(testName).contains("no")) skipTest(testName, "Skipping because user want to end test => " + testName);
		
		//#########################
		//resetAllActors(testName);
		agent1.resumeAgent();
		agent2.releaseAgentSecondCode();
		agent3.resumeAgent();

		try{
			if(!chatCustomer1.startChat()) Assert.fail("@@ Cannot send chat at all") ;
			wait(rT1);
			//##########################
			chatCustomer1.disconnectChat();
			
		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
		
	}
	
	@Parameters({"rT1"})
	public void Chat_Queue_Abandon(int rT1) throws Exception {
		String testName = "Chat->Queue->Disconnect";
		if(startTestCase(testName).contains("no")) skipTest(testName, "Skipping because user want to end test => " + testName);
		
		//#########################
		//resetAllActors(testName);
		agent1.releaseAgent();
		agent2.releaseAgentSecondCode();
		agent3.resumeAgent();

		try{
			if(!chatCustomer1.startChat()) Assert.fail("@@ Failed since I cannot send chat at all") ;
			wait(rT1);
			//##########################
			chatCustomer1.disconnectChat();
			
		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
		
	}
	
	@Parameters({"rT1", "tT1", "wT1"})
	public void Chat_NoQ_Answer(int rT1, int tT1, int wT1) throws Exception {
		String testName = "Chat->NoQ->Answered";
		if(startTestCase(testName).contains("no")) skipTest(testName, "Skipping because user want to end test => " + testName);
		
		//#########################
		//resetAllActors(testName);
		agent1.resumeAgent();
		agent2.releaseAgentSecondCode();
		agent3.releaseAgentThirdCode();

		try{
			if(!chatCustomer1.startChat()) Assert.fail("@@ Failed since I cannot send chat at all") ;
			
			//##########################
			agent1.answerACDCall(rT1, tT1);
			//Assert.assertEquals(pc.PostCondition_Chat_Answered(agent1), true, testName + "is failed ");
			//if(!pc.PostCondition_Chat_Answered(agent1)){}
			
			
			//############################
			chatCustomer1.disconnectChat();
			agent1.wrapupEndWith2WrapupCodes(wT1);
		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
		
	}
	
		
	@Parameters({"qT1", "rT1", "tT1", "wT1"})
	public void Chat_Q_Answer(int qT1, int rT1, int tT1, int wT1) throws Exception {
		String testName = "Chat->Q->Answered";
		if(startTestCase(testName).contains("no")) skipTest(testName, "Skipping because user want to end test => " + testName);
		//#########################
		agent1.releaseAgent();
		agent2.releaseAgentSecondCode();
		agent3.releaseAgentThirdCode();
		
		try{
			if(!chatCustomer1.startChat()) Assert.fail("@@ Failed since I cannot send chat at all") ;
			wait(qT1, "Queue");
			agent1.resumeAgent();
					
			//##########################
			agent1.answerACDCall(rT1, tT1);
			
			//############################
			chatCustomer1.disconnectChat();
			agent1.wrapupEndWith2WrapupCodes(wT1);
		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
	}
	
	@Parameters({"rT1", "tT1"})
	public void Chat_NoQ_Answer_NoWrapTime(int rT1, int tT1) throws Exception {
		String testName = "Chat->NoQ->Answered with No Wrap time";
		if(startTestCase(testName).contains("no")) skipTest(testName, "Skipping because user want to end test => " + testName);
		//#########################
		sup1.Max_LogIn_changeWrapAndFRTime_Min(AllActors.services.get(0).name, "0", "40");
		 
		//#########################
		agent1.resumeAgent();
		agent2.releaseAgentSecondCode();
		agent3.resumeAgent();
	
		try{
			//##########################
			if(!chatCustomer1.startChat()) Assert.fail("@@ Failed since I cannot send chat at all") ;
			agent1.answerACDCall(rT1, tT1);
			
			//############################
			chatCustomer1.disconnectChat();
			
		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			resetAllActors(testName);
		}finally{
			sup1.Max_changeWrapAndFRTime_LogOut_Min(AllActors.services.get(0).name, "20", "30");
			endTestCase(testName);
	  }
	}
	

	@Parameters({"rT1", "tT1", "rT2", "tT2", "wT2"})
	public void Chat_BTransfer_ByExt_SameGroup(int rT1, int tT1, int rT2, int tT2, int wT2) throws Exception {
	
		String testName = "Chat->Ans->Blind Transfer to Ext on Same Group ";
		if(startTestCase(testName).contains("no")) skipTest(testName, "Skipping because user want to end test => " + testName);
		//#########################
		
		try{
			agent1.resumeAgent();
			agent2.releaseAgentSecondCode();
			agent3.resumeAgent();

			//###########################
			if(!chatCustomer1.startChat()) Assert.fail("@@ Failed since I cannot send chat at all") ;

			//##########################
			agent1.answerACDCall(rT1, tT1);

			//###########################
			agent2.resumeAgent();
			agent1.blindTransfer(agent2.extension);
			agent2.answerACDCall(rT2, tT2);


			//############################
			chatCustomer1.disconnectChat();
			agent2.wrapupEndWith2WrapupCodes(wT2);

		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
	}
	
	@Parameters({"rT1", "tT1", "rT2", "tT2", "wT2"})
	public void Chat_BTransfer_ByAgentName_SameGroup(int rT1, int tT1, int rT2, int tT2, int wT2) throws Exception {
	
		String testName = "Chat->Ans->Blind Transfer by agent name on Same Group ";
		if(startTestCase(testName).contains("no")) skipTest(testName, "Skipping because user want to end test => " + testName);
		//#########################
		
		try{
			agent1.resumeAgent();
			agent2.releaseAgentSecondCode();
			agent3.resumeAgent();

			//###########################
			if(!chatCustomer1.startChat()) Assert.fail("@@ Failed since I cannot send chat at all") ;

			//##########################
			agent1.answerACDCall(rT1, tT1);

			//###########################
			agent2.resumeAgent();
			agent1.blindTransfer_byName(agent2.username);
			agent2.answerACDCall(rT2, tT2);


			//############################
			chatCustomer1.disconnectChat();
			agent2.wrapupEndWith2WrapupCodes(wT2);

		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
	}
	
	
	@Parameters({"rT1", "tT1", "rT2", "tT2", "wT2"})
	public void Chat_R_Q_Overflow_R_Ans_2ndGroup(int rT1, int tT1, int rT2, int tT2, int wT2) throws Exception {
		
		String testName = "Chat->Ring->Q->Overflow->Ring->Answer on the 2nd group";
		if(startTestCase(testName).contains("no")) skipTest(testName, "Skipping because user want to end test => " + testName);
		//#########################
		
		try{
			agent1.resumeAgent();
			agent2.releaseAgentSecondCode();
			agent3.resumeAgent();;

			//###########################
			if(!chatCustomer1.startChat()) Assert.fail("@@ Failed since I cannot send chat at all") ;

			//##########################
			chatCustomer1.wait(60, "Wait for Overflow timeout: This includes Ring before forced-released time");
			agent3.answerACDCall(rT2, tT2);

			//############################
			chatCustomer1.disconnectChat();
			agent3.wrapupEndWith2WrapupCodes(wT2);

		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
	}
	
	
	@Parameters({"rT1", "tT1", "rT2", "tT2", "wT2"})
	public void Chat_R_Q_Overflow_Q_R_Ans_2ndGroup(int rT1, int tT1, int rT2, int tT2, int wT2) throws Exception {
		
		String testName = "Chat->Ring->Q->Overflow->Q->Ring->Answer on the 2nd group";
		if(startTestCase(testName).contains("no")) skipTest(testName, "Skipping because user want to end test => " + testName);
		//#########################
		
		try{
			agent1.resumeAgent();
			agent2.releaseAgentSecondCode();
			agent3.releaseAgentThirdCode();

			//###########################
			if(!chatCustomer1.startChat()) Assert.fail("@@ Failed since I cannot send chat at all") ;

			//##########################
			chatCustomer1.wait(80, "Wait for Overflow timeout");
			agent3.resumeAgent();
			agent3.answerACDCall(rT2, tT2);

			//############################
			chatCustomer1.disconnectChat();
			agent3.wrapupEndWith2WrapupCodes(wT2);
		
		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
	}
	
	
	
	@Parameters({"rT1", "tT1", "rT2", "tT2", "wT2"})
	public void Chat_Q_Overflow_R_Ans_2ndGroup(int rT1, int tT1, int rT2, int tT2, int wT2) throws Exception {
		
		String testName = "Chat->Q->Overflow->Ring->Answer on the 2nd group";
		if(startTestCase(testName).contains("no")) skipTest(testName, "Skipping because user want to end test => " + testName);
		//#########################
		
		try{
			agent1.releaseAgent();
			agent2.releaseAgentSecondCode();
			agent3.resumeAgent();;

			//###########################
			if(!chatCustomer1.startChat()) Assert.fail("@@ Failed since I cannot send chat at all") ;

			//##########################
			chatCustomer1.wait(60, "Wait for Overflow timeout");
			agent3.answerACDCall(rT2, tT2);

			//############################
			chatCustomer1.disconnectChat();
			agent3.wrapupEndWith2WrapupCodes(wT2);
		
		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
	}
	
	@Parameters({"rT1", "tT1", "rT2", "tT2", "wT2"})
	public void Chat_Q_Overflow_R_Ans_1stGroup(int rT1, int tT1, int rT2, int tT2, int wT2) throws Exception {
		
		String testName = "Chat->Q->Overflow->Ring->Answer on the 2nd group";
		if(startTestCase(testName).contains("no")) skipTest(testName, "Skipping because user want to end test => " + testName);
		//#########################
		
		try{
			agent1.releaseAgent();
			agent2.releaseAgentSecondCode();
			agent3.releaseAgentThirdCode();

			//###########################
			if(!chatCustomer1.startChat()) Assert.fail("@@ Failed since I cannot send chat at all") ;

			//##########################
			chatCustomer1.wait(60, "Wait for Overflow timeout");
			agent1.resumeAgent();
			agent1.answerACDCall(rT1, tT1);

			//############################
			chatCustomer1.disconnectChat();
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
	public void Chat_Q_OverInterflow_Q_R_Ans_3rdGroup(int rT1, int tT1, int rT2, int tT2, int wT2) throws Exception {
		
		String testName = "Chat->Q->OverInterflow->Q->Ring->Answer on the 3rd group";
		if(startTestCase(testName).contains("no")) skipTest(testName, "Skipping because user want to end test => " + testName);
		//#########################
		
		try{
			agent1.releaseAgent();
			agent2.releaseAgentSecondCode();
			agent3.releaseAgentThirdCode();;

			//###########################
			if(!chatCustomer1.startChat()) Assert.fail("@@ Failed since I cannot send chat at all") ;

			//##########################
			chatCustomer1.wait(120, "Wait for Interflow timeout");
			agent3.resumeAgent();
			agent3.answerACDCall(rT2, tT2);

			//############################
			chatCustomer1.disconnectChat();
			agent3.wrapupEndWith2WrapupCodes(wT2);
		
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
		public void Chat_All_Repeat(int rT1, int tT1, int rT2, int tT2, int wT2) throws Exception {
			
			String testName = "Chat_All_Repeat";
			if(startTestCase(testName).contains("no")) skipTest(testName, "Skipping because user want to end test => " + testName);
			//#########################
			
			try{
				for (int i = 1; i < 10001 ; i++) {
					log.info("@@@@ " + i + "th try ==> ");
					Chat_NoQ_Answer(10, 10, 10);
					Chat_Q_Answer(10, 10, 10, 10);
					Chat_BTransfer_ByAgentName_SameGroup(10, 10, 10, 10, 10);
					Chat_BTransfer_ByExt_SameGroup(10, 10, 10, 10, 10);
					
				}
					
			
			}catch(Exception e){
				log.info("I am handling General exception=>"+ e.toString());
				resetAllActors(testName);
			}finally{
				endTestCase(testName);
			}
		}
		
		
	
	


}
