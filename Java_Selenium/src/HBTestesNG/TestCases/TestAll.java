package HBTestesNG.TestCases;

import HBTestesNG.BaseObjects.*;import org.testng.SkipException;

public class TestAll extends TestCaseObject{
	 
	public TestAll() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws Exception {
		String waitUntilOneAm, tearDownOrNot, loadOrNot, usePhantomJS;
		int qT1, rT1, tT1, wT1, rT2, tT2, wT2;
		int i;
		
		qT1=10; rT1=10; tT1=10; wT1=10; rT2=10; tT2=10; wT2=10;
		waitUntilOneAm = "no";
		tearDownOrNot = "yes";
		usePhantomJS = "no";
		
		
		
		Test_Initiate testInitiate = new Test_Initiate();
		Test_VoiceACD_Basic testVoiceACDBasic = new Test_VoiceACD_Basic();
		Test_VoiceACD_Transfer testVoiceACDTransfer = new Test_VoiceACD_Transfer();
		
		testInitiate.beforeSuite(waitUntilOneAm);
		InitializeAllVariables();
		
		try{
			testVoiceACDBasic.VoiceACD_NoQ_Answer(rT1, tT1, wT1);
			/*for(i=0;i<1000;i++){
		
				//### Basic test cases ###
				testVoiceACDBasic.VoiceACD_NoQ_Answer(rT1, tT1, wT1);
				//testVoiceACDBasic.VoiceACD_NoQ_Answer_NoWrapTime(rT1, tT1);
				testVoiceACDBasic.VoiceACD_Q_Answer(qT1, rT1, tT1, wT1);
	
				
				//### Transfer Test cases ###
				testVoiceACDTransfer.VoiceACD_BTransfer_ToExt_DifferentGroup(rT1, tT1, rT2, tT2);
				testVoiceACDTransfer.VoiceACD_BTransfer_ToExt_SameGroup(rT1, tT1, rT2, tT2, wT2);
				//testVoiceACDTransfer.VoiceACD_BTransfer_ToExternal(rT1, tT1, rT2, tT2);
				testVoiceACDTransfer.VoiceACD_BTransfer_ToIRN_AQ(rT1, tT1, rT2, tT2, wT2);
				testVoiceACDTransfer.VoiceACD_BTransfer_ToIRN_DifferentGroup(rT1, tT1, rT2, tT2, wT2);
				testVoiceACDTransfer.VoiceACD_BTransfer_ToIRN_SameGroup(rT1, tT1, rT2, tT2, wT2);
				testVoiceACDTransfer.VoiceACD_CTransfer_ToExt_DifferentGroup(rT1, tT1, rT2, tT2);
				testVoiceACDTransfer.VoiceACD_CTransfer_ToExt_SameGroup(rT1, tT1, rT2, tT2, wT2);
				//testVoiceACDTransfer.VoiceACD_CTransfer_ToExternal(rT1, tT1, rT2, tT2);
				testVoiceACDTransfer.VoiceACD_CTransfer_ToIRN_DifferentGroup(rT1, tT1, rT2, tT2, wT2);
				testVoiceACDTransfer.VoiceACD_CTransfer_ToIRN_SameGroup(rT1, tT1, rT2, tT2, wT2);

			}*/
		}catch(Exception e){
			log.info("I am handling exception on TestAll=>"+ e.toString());
		}finally{		
			testInitiate.afterSuite(tearDownOrNot);
		}


	}
 
}