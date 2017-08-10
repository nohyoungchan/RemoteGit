package HBTestesNG.BaseObjects;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Properties;
import Utility.PostCondition;
import org.ini4j.Wini;


public class AllActors extends TestObject{

    public ArrayList<AgentHB> agents;
    public ArrayList<CustomerManhattan> customers;
    public ArrayList<CustomerChat> chatCustomers;
    public ArrayList<CustomerEmail> emailCustomers;
    public ArrayList<AgentHBDirector> supervisors;
    public ArrayList<IRN> irns;
    public static ArrayList<Service> services;
    
    public static Wini testDataIni;
    public static Wini envIni;
    public static Hashtable<String, String> globalVariableHash;
	public static Hashtable<String, String> hbDirectorXPathHash;
	public static Hashtable<String, String> hbWebAgentXPathHash;
	public static Hashtable<String, String> emailOWAXPathHash;
	public static Hashtable<String, String> chatCustomerXPathHash;
	
	public static String currentCallType;
	public static PostCondition postCondition;
	

    
    public AllActors(){
    	initiateAllActors();
    	//initiateAllActors_withPhantomOrNot();
	
	}
    
	//if usPhantomJS = yes, this uses phantom js.
	public void initiateAllActors(){
		int agentStart, max, chatCustomerStart, i, j;
		String configDir = ".\\test_Config_Files\\";

		stopTest = "no";
		currentCallType = "NA";
		agents = new ArrayList<AgentHB>();
		customers = new ArrayList<CustomerManhattan>();
		chatCustomers = new ArrayList<CustomerChat>();
		emailCustomers = new ArrayList<CustomerEmail>();
		supervisors = new ArrayList<AgentHBDirector>();
		irns = new ArrayList<IRN>();
		services = new ArrayList<Service>();
		

		globalVariableHash = new Hashtable<String, String>();
    	hbDirectorXPathHash = new Hashtable<String, String>();
    	hbWebAgentXPathHash = new Hashtable<String, String>();
    	emailOWAXPathHash = new Hashtable<String, String>();
    	chatCustomerXPathHash = new Hashtable<String, String>();

    	
    	
	    try{
	    	
	    	postCondition = new PostCondition();
	        Properties propertyFile = new Properties();

	       	        
	        //#### Read/Assign Global Variable from .\\test_Config_Files\\testData.ini (Main)
	        testDataIni = new Wini(new File(configDir + "testData.ini"));         
	        TestObject.useWhichWebDriver = testDataIni.get("SYSTEM", "webDriver");
	        String systemToTest = testDataIni.get("SYSTEM", "systemToTest");
	        log.info("Testing: " + systemToTest); 
	        
	        envIni = new Wini(new File(configDir + "testData.ini_" + systemToTest));     
	        max = Integer.parseInt(envIni.get("IRN", "max"));
	        for(i=0; i < max; i++){
	        	j = i+1;
	        	irns.add(new IRN());
	        	irns.get(i).irnNum =  envIni.get("IRN", "irn" + j+ ".irnNum");
	        	irns.get(i).dnisNum = envIni.get("IRN", "irn" + j+ ".dnisNum");
	        	irns.get(i).didNum =  envIni.get("IRN", "irn" + j+ ".didNum");
	        	log.info("irn"+ j+ "=>" + irns.get(i).irnNum + ": " + irns.get(i).dnisNum+ ": " + irns.get(i).didNum);
	        }
	        
	        max = Integer.parseInt(envIni.get("Service", "max"));
	        for(i=0; i < max; i++){
	        	j = i+1;
	        	services.add(new Service());
	        	services.get(i).name = envIni.get("Service", "service" + j+ ".name");
	        	services.get(i).wrapupTime = envIni.get("Service", "service" + j+ ".wrapupTime");
	        	services.get(i).forcedReleaseTime = envIni.get("Service", "service" + j+ ".forcedReleaseTime");
	        	services.get(i).destination = envIni.get("Service", "service" + j+ ".destination");
	        	services.get(i).overflowDestination = envIni.get("Service", "service" + j+ ".overflowDestination");
	        	services.get(i).overflowTimeout = envIni.get("Service", "service" + j+ ".overflowTimeout");
	        	services.get(i).interflowDestination = envIni.get("Service", "service" + j+ ".interflowDestination");
	        	services.get(i).interflowDestinationType = envIni.get("Service", "service" + j+ ".interflowDestinationType");
	        	services.get(i).interflowTimeout = envIni.get("Service", "service" + j+ ".interflowTimeout");	
	        }
	        
	        //agentStart is the first agent to initiate until max number of agents.
	        max = Integer.parseInt(envIni.get("Agent", "max"));
	        for(i=0; i < max; i++){
	        	j = i+1;
	        	agents.add(new AgentHB());
	        	agents.get(i).username =  envIni.get("Agent", "agent" + j+ ".name");
	        	agents.get(i).password =  envIni.get("Agent", "agent" + j+ ".password");
	        	agents.get(i).extension = envIni.get("Agent", "agent" + j+ ".extension");
	        	agents.get(i).did =       envIni.get("Agent", "agent" + j+ ".did");
	        	agents.get(i).agentType = "WebAgent";
	        	agents.get(i).threadName = agents.get(i).username + "_Thread";
	        	log.info("Agent"+ j+ "=>" +agents.get(i).username + ": " + agents.get(i).password+ ": " +agents.get(i).extension+ ": " +agents.get(i).did );
	        }
	        

	        
	        
	        max = Integer.parseInt(envIni.get("CustomerVoice", "max"));
	        for(i=0; i < max; i++){
	        	j = i+1;
	        	customers.add(new CustomerManhattan());
	        	customers.get(i).username = envIni.get("CustomerVoice", "CustomerVoice" + j+ ".name");
	        	customers.get(i).password = envIni.get("CustomerVoice", "CustomerVoice" + j+ ".password");
	        	customers.get(i).extension = envIni.get("CustomerVoice", "CustomerVoice" + j+ ".extension");
	        	customers.get(i).did = envIni.get("CustomerVoice", "CustomerVoice" + j+ ".did");
	        	customers.get(i).agentType = "CustomerVoice";
	        	log.info("Customer"+ j+ "=>" + customers.get(i).username + ": " + customers.get(i).password+ ": " +customers.get(i).extension+ ": " +customers.get(i).did );
	        }
	        
	        // #### Creating and Setting ChatCustomer ####
	        String tenantPrefix, chatIRN, chatCustomerNamePrefix, stOrMt;
	        max = Integer.parseInt(envIni.get("CustomerChat", "max"));
	        chatCustomerStart = Integer.parseInt(envIni.get("CustomerChat", "chatCustomerStart"));

	        tenantPrefix = envIni.get("CustomerChat", "tenantPrefix");
	        chatIRN = envIni.get("CustomerChat", "chatIRN");
	        chatCustomerNamePrefix = envIni.get("CustomerChat", "chatCustomerNamePrefix");
	        stOrMt = envIni.get("CustomerChat", "stOrMt");
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
	        max = Integer.parseInt(envIni.get("CustomerEmail", "max"));
	        for(i=0; i < max; i++){
	        	j = i+1;
	        	emailCustomers.add(new CustomerEmail());
	        	emailCustomers.get(i).username = envIni.get("CustomerEmail", "CustomerEmail" + j+ ".name");
	        	emailCustomers.get(i).password = envIni.get("CustomerEmail", "CustomerEmail" + j+ ".password");
	        	emailCustomers.get(i).domain = envIni.get("CustomerEmail", "CustomerEmail" + j+ ".domain");
	        	emailCustomers.get(i).agentType = "CustomerEmail";
	        	log.info("emailCustomer"+ j+ "=>" + emailCustomers.get(i).username + ": " + emailCustomers.get(i).password+ ": " +emailCustomers.get(i).domain);
	        }
	        
	        //Setting supervisor for director
	        max = Integer.parseInt(envIni.get("CCD", "max"));
	        for(i=0; i < max; i++){
	        	j = i+1;
	        	supervisors.add(new AgentHBDirector());
	        	supervisors.get(i).username = envIni.get("CCD", "supervisor" + j+ ".name");
	        	supervisors.get(i).password = envIni.get("CCD", "supervisor" + j+ ".password");
	        	supervisors.get(i).agentType = "HBDirector";
	        	log.info("supervisors"+ j+ "=>" + supervisors.get(i).username + ": " + supervisors.get(i).password);
	        }
	        
	        
	        //######################## Variable applied globally ######################################
	        //Read HB director: Property name and value	     
	        propertyFile.load(new FileInputStream(".\\test_Property_Files\\testProperty.ini"));
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
	             

	     }
	     catch (Exception e) {
	        System.out.println(e);
	     }
	    
	    
	 }

	
	
	
	

}