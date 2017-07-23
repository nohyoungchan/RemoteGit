package HBTestesNG.BaseObjects;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Properties;
import Utility.PostCondition;


public class AllActors extends TestObject{

    public ArrayList<AgentHB> agents;
    public ArrayList<CustomerCMWin> customers;
    public ArrayList<CustomerChat> chatCustomers;
    public ArrayList<CustomerEmail> emailCustomers;
    public ArrayList<AgentHBDirector> supervisors;
    public ArrayList<IRN> irns;
    public static ArrayList<Service> services;
    public ArrayList<Boss> bosses;
    
    public static Hashtable<String, String> globalVariableMainHash;
    public static Hashtable<String, String> globalVariableHash;
	public static Hashtable<String, String> hbDirectorXPathHash;
	public static Hashtable<String, String> hbWebAgentXPathHash;
	public static Hashtable<String, String> emailOWAXPathHash;
	public static Hashtable<String, String> chatCustomerXPathHash;
	
	public static Hashtable<String, String> bossXPathHash;
	public static Hashtable<String, String> globalVariableBossHash;
	public static Hashtable<String, String> hbDirectorAgentCreateHash;
	
	public static String currentCallType;
	public static PostCondition postCondition;
	

    
    public AllActors(){
    	initiateAllActors();
    	//initiateAllActors_withPhantomOrNot();
	
	}
    
	//if usPhantomJS = yes, this uses phantom js.
	public void initiateAllActors(){
		int agentStart, bossStart, max, chatCustomerStart, i, j;

		stopTest = "no";
		currentCallType = "NA";
		agents = new ArrayList<AgentHB>();
		customers = new ArrayList<CustomerCMWin>();
		chatCustomers = new ArrayList<CustomerChat>();
		emailCustomers = new ArrayList<CustomerEmail>();
		supervisors = new ArrayList<AgentHBDirector>();
		irns = new ArrayList<IRN>();
		services = new ArrayList<Service>();
		bosses = new ArrayList<Boss>();
		
		globalVariableMainHash =new Hashtable<String, String>();
		globalVariableHash = new Hashtable<String, String>();
		globalVariableBossHash = new Hashtable<String, String>();
		bossXPathHash = new Hashtable<String, String>();;
    	hbDirectorXPathHash = new Hashtable<String, String>();
    	hbWebAgentXPathHash = new Hashtable<String, String>();
    	emailOWAXPathHash = new Hashtable<String, String>();
    	chatCustomerXPathHash = new Hashtable<String, String>();
		
    	hbDirectorAgentCreateHash =new Hashtable<String, String>();
    	
    	
	    try{
	    	
	    	postCondition = new PostCondition();
	    	Properties configFileMain = new Properties();
	        Properties configFile = new Properties();
	        Properties propertyFile = new Properties();
	        Properties configFileAgentCreate = new Properties();

	        //#### this is for CC Director =>Agent creation and update
	        configFileAgentCreate.load(new FileInputStream(".\\test_Config_Files\\testData.ini_CCDirector"));
	        max = Integer.parseInt(configFileAgentCreate.getProperty("globalVaribleMax"));
	        log.info("globalVaribleMax => " + max);
	        for(i=0; i < max; i++){
	        	j = i+1;
	        	hbDirectorAgentCreateHash.put(configFileAgentCreate.getProperty("globalVarible" + j+ ".name"), configFileAgentCreate.getProperty("globalVarible" + j+ ".value"));

	        }
	        
	        
	      //#### Read Global Variable from .\\test_Config_Files\\testData.ini (Main)
	        configFileMain.load(new FileInputStream(".\\test_Config_Files\\testData.ini"));
	        max = Integer.parseInt(configFileMain.getProperty("globalVariableMax"));
	        for(i=0; i < max; i++){
	        	j = i+1;
	        	globalVariableMainHash.put(configFileMain.getProperty("globalVariable" + j+ ".name"), configFileMain.getProperty("globalVariable" + j+ ".value"));
	        }
	        
	        //#### Assign Global Variable from .\\test_Config_Files\\testData.ini (Main)
	        TestObject.useWhichWebDriver = globalVariableMainHash.get("webDriver");
	        TestObject.globalSec = Integer.parseInt(globalVariableMainHash.get("waitSecBetweenRun"));
	        TestObject.globalMinToRelogIn = Integer.parseInt(globalVariableMainHash.get("globalMinToRelogIn"));
	        TestObject.globalScenario= globalVariableMainHash.get("globalScenario");
	        TestObject.loginSequentially= globalVariableMainHash.get("loginSequentially");
	        log.info("TestObject.loginSequentially => " + TestObject.loginSequentially);
	        
	        String systemToTest = globalVariableMainHash.get("systemToTest");
	        if (!systemToTest.contains("Boss")){
	        	log.info("Testing: " + systemToTest); 
	        	configFile.load(new FileInputStream(".\\test_Config_Files\\testData.ini_" + systemToTest));
	        }else{ //This is for Boss testing
	        	log.info("Testing: Boss"); 
            	configFile.load(new FileInputStream(".\\test_Config_Files\\testData.ini_Boss"));
            	propertyFile.load(new FileInputStream(".\\test_Property_Files\\testProperty.ini_Boss"));
            	 
            	//#### Read global variables from testData_Boss.ini files.
     	        max = Integer.parseInt(configFile.getProperty("globalVaribleMax"));
     	        for(i=0; i < max; i++){
     	        	j = i+1;
     	        	globalVariableBossHash.put(configFile.getProperty("globalVarible" + j+ ".name"), configFile.getProperty("globalVarible" + j+ ".value"));
     	        }
     	        
     	        //#### Create instances
     	        max = Integer.parseInt(configFile.getProperty("bossMax"));
     	        bossStart = Integer.parseInt(configFile.getProperty("bossStart"));
     	        for(i=0; i < max; i++){
     	        	j = bossStart+i;
     	        	bosses.add(new Boss());
     	        	bosses.get(i).username = configFile.getProperty("boss" + j+ ".username");
     	        	bosses.get(i).password = configFile.getProperty("boss" + j+ ".password");
     	        	bosses.get(i).bossURL = configFile.getProperty("boss" + j+ ".bossURL");
     	        	bosses.get(i).emailDestination = configFile.getProperty("boss" + j+ ".emailDestination");
     	        	bosses.get(i).agentType = "BossSuper";
     	        	log.info("boss"+ j+ "=>" + bosses.get(i).username + ": " + bosses.get(i).password
     	        			+ bosses.get(i).bossURL + ": " + bosses.get(i).emailDestination);
     	        }
     	        
    		    //Read Boss: Property name and value	 
    	        max = Integer.parseInt(propertyFile.getProperty("bossXPathMax"));
    	        log.info("bossXPathMax => " + max);
    	        for(i=0; i < max; i++){
    	        	j = i+1;
    	        	bossXPathHash.put(propertyFile.getProperty("bossXPath" + j+ ".name"), propertyFile.getProperty("bossXPath" + j+ ".value"));

    	        }
	        }
	       
	        
	        propertyFile.load(new FileInputStream(".\\test_Property_Files\\testProperty.ini"));
	        
	        
	        
	        //#### Read global variables from .\\test_Config_Files\\testData.ini of specific system
	        max = Integer.parseInt(configFile.getProperty("globalVaribleMax"));
	        for(i=0; i < max; i++){
	        	j = i+1;
	        	globalVariableHash.put(configFile.getProperty("globalVarible" + j+ ".name"), configFile.getProperty("globalVarible" + j+ ".value"));
	        }
	        
	        //#### Assign Global Variable from .\\test_Config_Files\\testData.ini of specific system
	        //TestObject.useWhichWebDriver = globalVariableHash.get("webDriver");
	        
	        
	        max = Integer.parseInt(configFile.getProperty("irnMax"));
	        for(i=0; i < max; i++){
	        	j = i+1;
	        	irns.add(new IRN());
	        	irns.get(i).irnNum = configFile.getProperty("irn" + j+ ".irnNum");
	        	irns.get(i).dnisNum = configFile.getProperty("irn" + j+ ".dnisNum");
	        	irns.get(i).didNum = configFile.getProperty("irn" + j+ ".didNum");
	        	log.info("irn"+ j+ "=>" + irns.get(i).irnNum + ": " + irns.get(i).dnisNum+ ": " + irns.get(i).didNum);
	        }
	        
	        max = Integer.parseInt(configFile.getProperty("serviceMax"));
	        for(i=0; i < max; i++){
	        	j = i+1;
	        	services.add(new Service());
	        	services.get(i).name = configFile.getProperty("service" + j+ ".name");
	        	services.get(i).wrapupTime = configFile.getProperty("service" + j+ ".wrapupTime");
	        	services.get(i).forcedReleaseTime = configFile.getProperty("service" + j+ ".forcedReleaseTime");
	        	services.get(i).destination = configFile.getProperty("service" + j+ ".destination");
	        	services.get(i).overflowDestination = configFile.getProperty("service" + j+ ".overflowDestination");
	        	services.get(i).overflowTimeout = configFile.getProperty("service" + j+ ".overflowTimeout");
	        	services.get(i).interflowDestination = configFile.getProperty("service" + j+ ".interflowDestination");
	        	services.get(i).interflowDestinationType = configFile.getProperty("service" + j+ ".interflowDestinationType");
	        	services.get(i).interflowTimeout = configFile.getProperty("service" + j+ ".interflowTimeout");	
	        }
	        
	        //agentStart is the first agent to initiate until max number of agents.
	        max = Integer.parseInt(configFile.getProperty("agentMax"));
	        globalVariableHash.put("agentMaxNum",  Integer.toString(max)); //This will be used on logIntoEmailClient_DeleteAllEmails()
	        agentStart = Integer.parseInt(configFile.getProperty("agentStart"));
	        globalVariableHash.put("agentStartNum",  Integer.toString(agentStart)); //This will be used on logIntoEmailClient_DeleteAllEmails()
	        if(configFile.getProperty("agentUseLoop").contains("no")){       
		        for(i=0; i < max; i++){
		        	j = agentStart+i;
		        	agents.add(new AgentHB());
		        	agents.get(i).username = configFile.getProperty("agent" + j+ ".name");
		        	agents.get(i).password = configFile.getProperty("agent" + j+ ".password");
		        	agents.get(i).extension = configFile.getProperty("agent" + j+ ".extension");
		        	agents.get(i).did = configFile.getProperty("agent" + j+ ".did");
		        	agents.get(i).scenarioLocal = configFile.getProperty("agent" + j+ ".scenario");
		        	agents.get(i).agentType = "WebAgent";
		        	agents.get(i).threadName = agents.get(i).username + "_Thread";
		        	log.info("Agent"+ j+ "=>" +agents.get(i).username + ": " + agents.get(i).password+ ": " +agents.get(i).extension+ ": " +agents.get(i).did );
		        }
	        }else{
	        	String namePrefix, domainName, password, extensionStartNumber, didStartNumber, scenario;

	        	namePrefix = configFile.getProperty("agent.namePrefix");
	        	domainName = configFile.getProperty("agent.domainName");
	        	password = configFile.getProperty("agent.password");
	        	extensionStartNumber = configFile.getProperty("agent.extensionStartNumber");
	        	didStartNumber = configFile.getProperty("agent.didStartNumber");
	        	scenario = configFile.getProperty("agent.scenario");

	 	        for(i=0; i < max; i++){
		        	j = agentStart+i;
		        	agents.add(new AgentHB());
		        	if (systemToTest.contains("C1HB1_with200")){
		        		//c1h1 username is like Agent_001@domain.com
			        	agents.get(i).username =namePrefix+intToStringWithLeadingZero(3, j)+domainName;
		        	}else{
		        		//other username is like Agent1@domain.com
		        		agents.get(i).username= namePrefix+j+domainName;
		        	}
		        	agents.get(i).password = password;
		        	agents.get(i).extension = extensionStartNumber+j;
		        	agents.get(i).did= didStartNumber+j;
		        	agents.get(i).scenarioLocal = scenario;
		        	agents.get(i).agentType = "WebAgent";
		        	agents.get(i).threadName = agents.get(i).username + "_Thread";
		        	log.info("Agent"+ j+ "=>" +agents.get(i).username + ": " + agents.get(i).password+ ": " +agents.get(i).extension+ ": " +agents.get(i).did );
	 	        }
	        }
	        
	        max = Integer.parseInt(configFile.getProperty("customerMax"));
	        for(i=0; i < max; i++){
	        	j = i+1;
	        	customers.add(new CustomerCMWin());
	        	customers.get(i).username = configFile.getProperty("customer" + j+ ".name");
	        	customers.get(i).password = configFile.getProperty("customer" + j+ ".password");
	        	customers.get(i).extension = configFile.getProperty("customer" + j+ ".extension");
	        	customers.get(i).did = configFile.getProperty("customer" + j+ ".did");
	        	customers.get(i).agentType = "CustomerVoice";
	        	log.info("Customer"+ j+ "=>" + customers.get(i).username + ": " + customers.get(i).password+ ": " +customers.get(i).extension+ ": " +customers.get(i).did );
	        }
	        
	        // #### Creating and Setting ChatCustomer ####
	        String tenantPrefix, chatIRN, chatCustomerNamePrefix, stOrMt;
	        max = Integer.parseInt(configFile.getProperty("chatCustomerMax"));
	        chatCustomerStart = Integer.parseInt(configFile.getProperty("chatCustomerStart"));

	        tenantPrefix = configFile.getProperty("chatCustomer.tenantPrefix");
	        chatIRN = configFile.getProperty("chatCustomer.chatIRN");
	        chatCustomerNamePrefix = configFile.getProperty("chatCustomer.chatCustomerNamePrefix");
	        stOrMt = configFile.getProperty("chatCustomer.stOrMt");
	        for(i=0; i < max; i++){
	        	j = i+1;
	        	chatCustomers.add(new CustomerChat());
	        	chatCustomers.get(i).tenantPrefix = tenantPrefix;
	        	chatCustomers.get(i).chatIRN = chatIRN;
	        	chatCustomers.get(i).username = chatCustomerNamePrefix+j;
	        	chatCustomers.get(i).stOrMt= stOrMt;
	        	chatCustomers.get(i).agentType = "CustomerChat";

	        	log.info("ChatCustomer Info : "+ j+ ") =>" + chatCustomers.get(i).username + ": stOrMT? => " + stOrMt);
	        }
	        
	        
	        // #### Creating and Setting EmailCustomer ####
	        max = Integer.parseInt(configFile.getProperty("emailCustomerMax"));
	        for(i=0; i < max; i++){
	        	j = i+1;
	        	emailCustomers.add(new CustomerEmail());
	        	emailCustomers.get(i).username = configFile.getProperty("emailCustomer" + j+ ".name");
	        	emailCustomers.get(i).password = configFile.getProperty("emailCustomer" + j+ ".password");
	        	emailCustomers.get(i).domain = configFile.getProperty("emailCustomer" + j+ ".domain");
	        	emailCustomers.get(i).agentType = "CustomerEmail";
	        	log.info("emailCustomer"+ j+ "=>" + emailCustomers.get(i).username + ": " + emailCustomers.get(i).password+ ": " +emailCustomers.get(i).domain);
	        }
	        
	        max = Integer.parseInt(configFile.getProperty("supervisorMax"));
	        for(i=0; i < max; i++){
	        	j = i+1;
	        	supervisors.add(new AgentHBDirector());
	        	supervisors.get(i).username = configFile.getProperty("supervisor" + j+ ".name");
	        	supervisors.get(i).password = configFile.getProperty("supervisor" + j+ ".password");
	        	supervisors.get(i).agentType = "HBDirector";
	        	log.info("supervisors"+ j+ "=>" + supervisors.get(i).username + ": " + supervisors.get(i).password);
	        }
	        
	        
	        //######################## Variable applied globally ######################################
	        //Read HB director: Property name and value	        
	        max = Integer.parseInt(propertyFile.getProperty("directorMax"));
	        //allXPathID = new AllXPathAndID();
	        for(i=0; i < max; i++){
	        	j = i+1;
	        	hbDirectorXPathHash.put(propertyFile.getProperty("director" + j+ ".name"), propertyFile.getProperty("director" + j+ ".value"));
	        }
	        
	        
	      //Read HB WebAgent: Property name and value	 
	        max = Integer.parseInt(propertyFile.getProperty("webAgentXPathMax"));
	        for(i=0; i < max; i++){
	        	j = i+1;
	        	hbWebAgentXPathHash.put(propertyFile.getProperty("webAgentXPath" + j+ ".name"), propertyFile.getProperty("webAgentXPath" + j+ ".value"));
	        }
	        
		    //Read email OWA: Property name and value	 
	        max = Integer.parseInt(propertyFile.getProperty("emailOWAXPathMax"));
	        for(i=0; i < max; i++){
	        	j = i+1;
	        	emailOWAXPathHash.put(propertyFile.getProperty("emailOWAXPath" + j+ ".name"), propertyFile.getProperty("emailOWAXPath" + j+ ".value"));
	        }
	        
		    //Read ChatCustomerXPath: Property name and value	
	        max = Integer.parseInt(propertyFile.getProperty("chatCustomerXPathMax"));
	        for(i=0; i < max; i++){
	        	j = i+1;
	        	chatCustomerXPathHash.put(propertyFile.getProperty("chatCustomerXPath" + j+ ".name"), propertyFile.getProperty("chatCustomerXPath" + j+ ".value"));
	        }
	             
     
	        //p.list(System.out);
	     }
	     catch (Exception e) {
	        System.out.println(e);
	     }
	    
	    
	 }

	
	
	
	

}