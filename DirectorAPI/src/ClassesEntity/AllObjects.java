package ClassesEntity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;


import org.ini4j.Wini;

import CCEntity.Agent;
import CCEntity.JasonHBSupervisor;
import CCEntity.Supervisor;




public class  AllObjects extends TestBaseObject{

    public ArrayList<Agent> agents;
    public ArrayList<Supervisor> emailCustomers;
    public ArrayList<JasonHBSupervisor> hbSupervisors;

    
    public static Hashtable<String, String> globalVariableMainHash;
    public static Hashtable<String, String> globalVariableHash;
    public static Hashtable<String, String> jasonMessageHash;
    public static Hashtable<String, String> jsonMessageSubscribeEntityHash;
    
    public static JasonAPITesterClient apiTesterClient;
    public static JasonMessageBuilder jMessageBuilder;
    public static Connection connection;
    public static TestCaseObject tcObject;


    public AllObjects(){
    	initiateAllObjects();
    	//initiateAllActors_withPhantomOrNot();
	
	}
    
    public void initiateAllObjects(){
		int maxNum, i, j;
		String strKey, strValue;
		
	    try{
	    	
	    	//read_ConfigINI();
	    	log.info("#### Reading testData.ini / testDataJason.ini file ");
			ini = new Wini(new File("testData.ini"));  //This contains all configuration data
			inij = new Wini(new File("testDataJason.ini")); //This contains all jason messages
			
	    	hbSupervisors = new ArrayList<JasonHBSupervisor>();
	        

	        //#### Decide which system to test
	        sysTT = ini.get("system", "systemToTest");
	        maxNum = Integer.parseInt(ini.get(sysTT, "maxNumSupervisors"));
	        log.info("##### Testing: " + sysTT + " ##########"); 

	        //#### initializing supervisor object
	        hbSupervisors = new ArrayList<JasonHBSupervisor>();
	        for(i=0; i < maxNum; i++){
	        	j = i+1;
	        	hbSupervisors.add(new JasonHBSupervisor());
	        	hbSupervisors.get(i).userName= ini.get(sysTT, "sup"+i+".username");
	        	hbSupervisors.get(i).password= ini.get(sysTT, "sup"+i+".password");
	        	hbSupervisors.get(i).supervisorID= ini.get(sysTT, "sup"+i+".supervisorID");
	        	hbSupervisors.get(i).tenantID= ini.get(sysTT, "sup"+i+".tenantID");
	        	log.info("HBSupervisor"+ j+ "=>" +hbSupervisors.get(i).userName + ": " + hbSupervisors.get(i).password+ ": " +hbSupervisors.get(i).supervisorID+ ": " +hbSupervisors.get(i).tenantID);
	        }

            
            //#### Initialize TestCaseObject: This will initialize all static variable on TestCaseObject.
            tcObject = new TestCaseObject();
            TestCaseObject.apiTesterClient = new JasonAPITesterClient();
            TestCaseObject.apiTesterClient.ccapiURL = ini.get(sysTT, "ccapiURL");
            TestCaseObject.jMessageBuilder = new JasonMessageBuilder();
            TestCaseObject.hbAdmin = hbSupervisors.get(0);
            TestCaseObject.hbSup = hbSupervisors.get(1);
            TestCaseObject.pollResultsHash = new Hashtable<String, String>();
            TestCaseObject.pollResultPorcessedHash = new Hashtable<String, String>();
            
            //#### Initialize connection object  : this object is not used at the moment
            connection = new Connection();
            connection.ccapiURL = ini.get(sysTT, "ccapiURL");
            connection.username = hbSupervisors.get(1).userName;
            connection.password = hbSupervisors.get(1).password;
	        
  
	     }
	     catch (Exception e) {
	        System.out.println(e);
	     }
	    
	    
	 }
    
	/*//if usPhantomJS = yes, this uses phantom js.
	public void initiateAllObjects(){
		int max, i, j;
		String strKey, strValue;
		Properties configFileMain, configFileEnvironment, configFileJasonMessage;

		hbSupervisors = new ArrayList<JasonHBSupervisor>();
		agents = new ArrayList<Agent>();

		
		globalVariableMainHash =new Hashtable<String, String>();
		globalVariableHash = new Hashtable<String, String>();
		jasonMessageHash = new Hashtable<String, String>();
		jsonMessageSubscribeEntityHash = new Hashtable<String, String>();
		
	    try{
	    	
	    	read_ConfigINI();
	    	
	    	configFileMain = new Properties();
	    	configFileEnvironment = new Properties();
	    	configFileJasonMessage = new Properties();

		    //#### Read Global Variable from testData.ini (Main)
	        configFileMain.load(new FileInputStream("testData.ini"));
	        max = Integer.parseInt(configFileMain.getProperty("globalVariableMax"));
	        for(i=0; i < max; i++){
	        	j = i+1;
	        	globalVariableMainHash.put(configFileMain.getProperty("globalVariable" + j+ ".name"), configFileMain.getProperty("globalVariable" + j+ ".value"));
	        }
	        

	        //#### Decide which system to test
	        String systemToTest = globalVariableMainHash.get("systemToTest");
	        log.info("##### Testing: " + systemToTest + " ##########"); 
	        
	        //#### Read Variable from testData_???.ini for each environment
	        configFileEnvironment.load(new FileInputStream("testData_" + systemToTest + ".ini"));
	        max = Integer.parseInt(configFileEnvironment.getProperty("globalVariableMax"));
	        for(i=0; i < max; i++){
	        	j = i+1;
	        	strKey = configFileEnvironment.getProperty("globalVariable" + j+ ".name");
	        	strValue = configFileEnvironment.getProperty("globalVariable" + j+ ".value");
	        	globalVariableHash.put(strKey, strValue);
	        	log.info(j+ ") Specific test data : " + strValue); 
	        }
	        
	        //#### Create supervisors.
	        
	        max = Integer.parseInt(configFileEnvironment.getProperty("supervisorMax"));
	        for(i=0; i < max; i++){
	        	j = i+1;
	        	hbSupervisors.add(new JasonHBSupervisor());
	        	hbSupervisors.get(i).userName= configFileEnvironment.getProperty("supervisor" + j+ ".username");
	        	hbSupervisors.get(i).password= configFileEnvironment.getProperty("supervisor" + j+ ".password");
	        	hbSupervisors.get(i).tenantID= configFileEnvironment.getProperty("supervisor" + j+ ".tenantID");
	        	hbSupervisors.get(i).supervisorID= configFileEnvironment.getProperty("supervisor" + j+ ".supervisorID");
	        	log.info("HBSupervisor"+ j+ "=>" +hbSupervisors.get(i).userName + ": " + hbSupervisors.get(i).password+ ": " +hbSupervisors.get(i).tenantID+ ": " +hbSupervisors.get(i).supervisorID );
	        }
	        
	        //#### Initialize apiTestClient ####
	        apiTesterClient = new JasonAPITesterClient();
            apiTesterClient.ccapiURL = ini.get("C2HB2", "ccapiURL");
            //apiTesterClient.ccapiURL = globalVariableHash.get("ccapiURL");
	        
	        
	        //#### Read all Jason messages from testJasonMessage.ini
	        configFileJasonMessage.load(new FileInputStream("testJasonMessage.ini"));
	        max = Integer.parseInt(configFileJasonMessage.getProperty("jasonMessageMax"));
	        for(i=0; i < max; i++){
	        	j = i+1;
	        	strKey = configFileJasonMessage.getProperty("jasonMessage" + j+ ".name");
	        	strValue = configFileJasonMessage.getProperty("jasonMessage" + j+ ".value");
	        	jasonMessageHash.put(strKey, strValue);
	        	//System.out.println(j+ ") Jason Message : " + strValue + "\n"); 
	        }
	        
	        max = Integer.parseInt(configFileJasonMessage.getProperty("jsonMessageSubscribeEntityMax"));
	        for(i=0; i < max; i++){
	        	j = i+1;
	        	strKey = configFileJasonMessage.getProperty("jsonMessageSubscribeEntity" + j+ ".name");
	        	strValue = configFileJasonMessage.getProperty("jsonMessageSubscribeEntity" + j+ ".value");
	        	jsonMessageSubscribeEntityHash.put(strKey, strValue);
	        	//System.out.println(j+ ") Jason Message : " + strValue + "\n"); 
	        }
	        
	        read_ConfigINI();
	        
	        //#### Initialize JasonMessageBuilder
            jMessageBuilder = new JasonMessageBuilder();
	        
  
	     }
	     catch (Exception e) {
	        System.out.println(e);
	     }
	    
	    
	 }*/
	
    public static void read_ConfigINI() throws FileNotFoundException, IOException{
		log.info("#### Reading testData_w.ini file ");
		ini = new Wini(new File("testData.ini"));
		
        //log("Port info => " + ini.get("SuperLoadServer_Config", "port"));
	}
	



	
	
	
	

}