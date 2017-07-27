package ClassesEntity;

import java.io.IOException;
import java.util.Arrays;
import java.util.Hashtable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;

import CCEntity.JasonHBSupervisor;
import ClassesTest.Test_Initiate;




public class TestCaseObject extends TestBaseObject{
	
	protected static Hashtable<String, String> pollResultsHash;
	protected static Hashtable<String, String> pollResultPorcessedHash;
	protected static JasonAPITesterClient apiTesterClient;
	protected static JasonHBSupervisor hbAdmin, hbSup;
	protected static JasonMessageBuilder jMessageBuilder;
	protected static AllObjects allObjects;;
	protected static String strCertificate;
	protected static int numPolling;
	
/*	public static void InitializeAllVariables() throws Exception {
		log.info("* Initializing all variables");
		
		allObjects = Test_Initiate.getAllObjects();
		apiTesterClient = AllObjects.apiTesterClient;
		jMessageBuilder = AllObjects.jMessageBuilder;
		//hbAdmin = allObjects.hbSupervisors.get(0);
		//hbSup = allObjects.hbSupervisors.get(1);  //0 is admin, 1 is supervisor
	}*/

	public static void startTestSuitMessage(String sTestCaseName){
		 
	    log.info("\n\n######################################################################################################");
	    log.info("###################################################");
	    log.info("###########  "+sTestCaseName+ " ####################");
	    log.info("###################################################");
	    log.info("###################################################");
	 
	    }
	
	public static void endtTestSuitMessage() throws IOException{

	    log.info("###################################################");
	    log.info("###################################################");
	    log.info("###### End of TestSuite ###########################");
	    log.info("###################################################");
	    log.info("###################################################");

	    }
	
	public static void startTestClassMessage(String sTestClassName){
		 
	    //log.info("\n\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
	    log.info("\n\n@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
	    log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
	    log.info("@@@@@@@@@@@@@@@@@@  "+sTestClassName+ " @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
	    log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@\n\n");
	 
	    }
	
	public static void startTestCase(String sTestCaseName){
		 
	    log.info("\n\n\n**********************************************************************************************************************************************");
	    log.info("****************************************************************************************");
	    log.info("$$$$$$$$$$$$$$$$$$$$$  "+sTestCaseName+ " $$$$$$$$$$$$$$$$$$$$$$$$$");
	    log.info("****************************************************************************************");
	    //log.info("****************************************************************************************");
	    
	}

	 
	    //This is to print log for the ending of the test case
	 
	public void endTestCase(String testName) throws Exception{
	 
	    log.info("XXXXXXXXXXXXXXXXXXXXXXX  "+"-END-"+ testName + " XXXXXXXXXXXXXXXXXXXXXX");
	    log.info("X");
	    log.info("X");
	    log.info("X");
	    log.info("X");
	    
	    waitBetweenTestCase(2);
   }
	
	
	/**
	 * print HHashtable<String, String>
	 * @param ht
	 * @throws JSONException 
	 */
	public static void printHashTable(String strHashName, Hashtable ht) throws JSONException{
		log.info("\n\n #################### Printing out : " + strHashName + " ########################");
		int i;
		i=1;
		
		String[] keys = (String[]) ht.keySet().toArray(new String[0]);
        Arrays.sort(keys);
        for(String key : keys) {
        	printS("#####################################################");
            printS("@@@@@@ " + key + " : " + ht.get(key));
        }
        
		/*for (Object objname:ht.keySet()){
			 log.info("@@@@ " + i + ". " + objname + " @@@@");
			 log.info(ht.get(objname) + "\n");
			 //### This cannot be used since entity name is not the same as the entity of the polled string.
			 //jasonParsor_General(objname.toString(), ht.get(objname).toString());
			 i++;
		}*/
		
	}
	
	 public static void jasonParsor_General(String entity, String strJasonMessage) throws JSONException{
	    	JSONObject temp;
	    	int i, j, totalArrays;
	    	log.info("\n#### Parsing Jason Message: " + entity + " ####");
	    	
	    	JSONArray arr = returnJasonArray(strJasonMessage, entity);
	    	if (arr == null) return;
	    	totalArrays= arr.length();
	    	System.out.println("==> Total " + entity + " : " + totalArrays);
	    	
	    	for (i =0; i < totalArrays; i++){
	    		j = i+1;
	    		temp = arr.getJSONObject(i);
	    		
	    		System.out.println(j + ". " + entity + " Records ####");
	    		j = 1;
	    		System.out.println("   " + j++ + ") " + entity + " name is            ==> " + temp.get(entity + "-name"));
	    		System.out.println("   " + j++ + ") " + entity + " id is              ==> " + temp.get(entity + "-id"));

	    	}
	    	System.out.println("####################################################\n");
	    	
	    }
	 
	 /**
	  * If a and b are equal, this will make a test Pass, if not fail.
	  * @param a
	  * @param b
	  */
	 public static void AssertEuqal(int a, int b){
		 Assert.assertEquals(a, b);
	 }
	 
	 /**
	  * If a and b are equal, this will make a test Pass, if not fail.
	  * @param a
	  * @param b
	  */
	 public static void AssertEuqal(String a, String b){
		 Assert.assertEquals(a, b); 
	 }
	 
	 /**
	  * If bool is true, the test is passed.
	  * @param bool
	  */
	 public static void AssertTrue(Boolean bool){
		 Assert.assertTrue(bool);
	 }
	
}
