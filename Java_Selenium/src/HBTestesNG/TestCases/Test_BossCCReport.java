package HBTestesNG.TestCases;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.testng.SkipException;
import org.testng.annotations.AfterClass;

import HBTestesNG.TestCases.*;
import HBTestesNG.BaseObjects.*;


@Test(groups= {"BossCCReport_ClassLevel"})
public class Test_BossCCReport extends TestCaseObject {

	Test_BossCCReport() throws Exception{
		log.info("* Construction: Test_BossCCReport");

	}

	@BeforeClass
	public void beforeClass() throws Exception {
		log.info("* Before Class: Test_BossCCReport");
		InitializeAllVariables(); 
    }
	
	@AfterClass
	public void afterClass() throws Exception {
		log.info("* After Class: Test_BossCCReport");
	}
	
	public void BossCCReport_AddReport() throws Exception {
		String testName = "BossCCReport_AddReport";
		globalScenario = "BossCCReport_AddReport";
		if(startTestCase(testName).contains("no")) skipTest("Skipping because user want to end test => " + testName);
		
		int i, bossSupervisorSize;
		bossSupervisorSize = aa.bosses.size();
		
		if (bossSupervisorSize < 1) {
			log.info("boss supervisor size is 0, so finish the exectuion");
			return;
		}

		try{
			
			ExecutorService threadPool = Executors.newFixedThreadPool(bossSupervisorSize);
			
			for (i = 0 ; i < bossSupervisorSize; i++){
				threadPool.submit(aa.bosses.get(i));
				//aa.agents.get(i).start();
				wait(2);
			}
		    
			// wait for keyboard input before exiting
			wait_for_input();
			threadPool.shutdownNow();
	    

		}catch(Exception e){
			log.info("I am handling General exception=>"+ e.toString());
			//resetAllActors(testName);
		}finally{
			endTestCase(testName);
		}
		
	}
	


}
