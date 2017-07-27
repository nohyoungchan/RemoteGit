package TestObject;


import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import BaseObject.*;



public class Test_Basic extends TCaseBaseObject {

	Test_Basic() throws Exception{
		log.info("* Construction: Test_Basic");

	}
	
	public void VoiceACD_Login() throws Exception {
		String testName = "Test Case: VoiceACD->Login";
		startTestCase(testName);
		HBAgent agent1 = new HBAgent("young1", "changeme", "http://10.23.175.161:3000/ecc");
		
		//#########################
		try{
			agent1.logIntoWebAgent();

		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
		
	}
	
	

	public void VoiceACD_NoQ_Answer(int rT1, int tT1, int wT1) throws Exception {
		String testName = "Test Case: VoiceACD->NoQ->Answered";
		startTestCase(testName);
		
		//#########################
		HBAgent agent1 = new HBAgent();
		HBAgent agent2 = new HBAgent();
		HBAgent agent3 = new HBAgent();
		CustomerCMWin customer1 = new CustomerCMWin();
		
		agent1.resumeAgent();
		agent2.releaseAgent();
		//agent2.releaseAgentSecondCode();
		agent3.resumeAgent();

		try{
			//customer1.makeACDCall(irn1.didNum);
					
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
	


}
