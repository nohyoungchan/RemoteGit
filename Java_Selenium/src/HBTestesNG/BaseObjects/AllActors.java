package HBTestesNG.BaseObjects;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Utility.PostCondition;
import org.ini4j.Wini;

import SupervisorLoad.SupervisorClient;
import SupervisorLoad.Supervisor_Admin;
import SupervisorLoad.Supervisor_Server;
import SupervisorLoad.Supervisor_Server.Handler;


public class AllActors extends TestObject{

    public ArrayList<AgentHB> agents;
    public ArrayList<CustomerManhattan> customers;
    public ArrayList<CustomerChat> chatCustomers;
    public ArrayList<CustomerEmail> emailCustomers;
    public ArrayList<AgentHBDirector> supervisors;
    public static ArrayList<IRN> irns;
    public static ArrayList<Service> services;
    public static ArrayList<SupervisorClient> supClients;
    
    public static Wini iniMain,  iniEnv, iniXPath;
    //public static Hashtable<String, String> globalVariableHash;
	
	public static String currentCallType;
	public static PostCondition postCondition;
	public static String configDir, xPathDir;
	

    public static Supervisor_Admin superAdmin;
    
    public AllActors(){
		configDir = ".\\test_Config_Files\\";
		xPathDir = ".\\test_XPath_Files\\";
    	initiateAllActors();

	
	}
    
	/**
	 * This initiate all actors like agentHB and all components.
	 */
	public void initiateAllActors(){
		int max, i, j;


		stopTest = "no";
		currentCallType = "NA";
		agents = new ArrayList<AgentHB>();
		customers = new ArrayList<CustomerManhattan>();
		chatCustomers = new ArrayList<CustomerChat>();
		emailCustomers = new ArrayList<CustomerEmail>();
		supervisors = new ArrayList<AgentHBDirector>();
		irns = new ArrayList<IRN>();
		services = new ArrayList<Service>();
		


	    try{
	    	
	    	postCondition = new PostCondition();
	        //Properties propertyFile = new Properties();

	       	        
	        //#### Read/Assign Global Variable from .\\test_Config_Files\\testData.ini (Main)
	        iniMain = new Wini(new File(configDir + "testData.ini"));         
	        TestObject.useWhichWebDriver = iniMain.get("WebBrowser", "webDriver");
	        String systemToTest = iniMain.get("SYSTEM", "systemToTest");
	        log.info("Testing: " + systemToTest); 
	        
	        
	        iniEnv = new Wini(new File(configDir + "testData.ini_" + systemToTest));     
	        max = Integer.parseInt(iniEnv.get("IRN", "max"));
	        for(i=0; i < max; i++){
	        	j = i+1;
	        	irns.add(new IRN());
	        	irns.get(i).irnNum =  iniEnv.get("IRN", "irn" + j+ ".irnNum");
	        	irns.get(i).dnisNum = iniEnv.get("IRN", "irn" + j+ ".dnisNum");
	        	irns.get(i).didNum =  iniEnv.get("IRN", "irn" + j+ ".didNum");
	        	log.info("irn"+ j+ "=>" + irns.get(i).irnNum + ": " + irns.get(i).dnisNum+ ": " + irns.get(i).didNum);
	        }
	        
	        max = Integer.parseInt(iniEnv.get("Service", "max"));
	        for(i=0; i < max; i++){
	        	j = i+1;
	        	services.add(new Service());
	        	services.get(i).name = iniEnv.get("Service", "service" + j+ ".name");
	        	services.get(i).wrapupTime = iniEnv.get("Service", "service" + j+ ".wrapupTime");
	        	services.get(i).forcedReleaseTime = iniEnv.get("Service", "service" + j+ ".forcedReleaseTime");
	        	services.get(i).destination = iniEnv.get("Service", "service" + j+ ".destination");
	        	services.get(i).overflowDestination = iniEnv.get("Service", "service" + j+ ".overflowDestination");
	        	services.get(i).overflowTimeout = iniEnv.get("Service", "service" + j+ ".overflowTimeout");
	        	services.get(i).interflowDestination = iniEnv.get("Service", "service" + j+ ".interflowDestination");
	        	services.get(i).interflowDestinationType = iniEnv.get("Service", "service" + j+ ".interflowDestinationType");
	        	services.get(i).interflowTimeout = iniEnv.get("Service", "service" + j+ ".interflowTimeout");	
	        }
	        
	        //agentStart is the first agent to initiate until max number of agents.
	        max = Integer.parseInt(iniEnv.get("Agent", "max"));
	        for(i=0; i < max; i++){
	        	j = i+1;
	        	agents.add(new AgentHB());
	        	agents.get(i).username =  iniEnv.get("Agent", "agent" + j+ ".name");
	        	agents.get(i).password =  iniEnv.get("Agent", "agent" + j+ ".password");
	        	agents.get(i).extension = iniEnv.get("Agent", "agent" + j+ ".extension");
	        	agents.get(i).did =       iniEnv.get("Agent", "agent" + j+ ".did");
	        	agents.get(i).agentType = "WebAgent";
	        	agents.get(i).threadName = agents.get(i).username + "_Thread";
	        	log.info("Agent"+ j+ "=>" +agents.get(i).username + ": " + agents.get(i).password+ ": " +agents.get(i).extension+ ": " +agents.get(i).did );
	        }
	        

	        
	        
	        max = Integer.parseInt(iniEnv.get("CustomerVoice", "max"));
	        for(i=0; i < max; i++){
	        	j = i+1;
	        	customers.add(new CustomerManhattan());
	        	customers.get(i).username = iniEnv.get("CustomerVoice", "CustomerVoice" + j+ ".name");
	        	customers.get(i).password = iniEnv.get("CustomerVoice", "CustomerVoice" + j+ ".password");
	        	customers.get(i).extension = iniEnv.get("CustomerVoice", "CustomerVoice" + j+ ".extension");
	        	customers.get(i).did = iniEnv.get("CustomerVoice", "CustomerVoice" + j+ ".did");
	        	customers.get(i).agentType = "CustomerVoice";
	        	log.info("Customer"+ j+ "=>" + customers.get(i).username + ": " + customers.get(i).password+ ": " +customers.get(i).extension+ ": " +customers.get(i).did );
	        }
	        
	        // #### Creating and Setting ChatCustomer ####
	        String tenantPrefix, chatIRN, chatCustomerNamePrefix, stOrMt;
	        max = Integer.parseInt(iniEnv.get("CustomerChat", "max"));

	        tenantPrefix = iniEnv.get("CustomerChat", "tenantPrefix");
	        chatIRN = iniEnv.get("CustomerChat", "chatIRN");
	        chatCustomerNamePrefix = iniEnv.get("CustomerChat", "chatCustomerNamePrefix");
	        stOrMt = iniEnv.get("CustomerChat", "stOrMt");
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
	        max = Integer.parseInt(iniEnv.get("CustomerEmail", "max"));
	        for(i=0; i < max; i++){
	        	j = i+1;
	        	emailCustomers.add(new CustomerEmail());
	        	emailCustomers.get(i).username = iniEnv.get("CustomerEmail", "CustomerEmail" + j+ ".name");
	        	emailCustomers.get(i).password = iniEnv.get("CustomerEmail", "CustomerEmail" + j+ ".password");
	        	emailCustomers.get(i).domain = iniEnv.get("CustomerEmail", "CustomerEmail" + j+ ".domain");
	        	emailCustomers.get(i).agentType = "CustomerEmail";
	        	log.info("emailCustomer"+ j+ "=>" + emailCustomers.get(i).username + ": " + emailCustomers.get(i).password+ ": " +emailCustomers.get(i).domain);
	        }
	        
	        //Setting supervisor for director
	        max = Integer.parseInt(iniEnv.get("CCD", "max"));
	        for(i=0; i < max; i++){
	        	j = i+1;
	        	supervisors.add(new AgentHBDirector());
	        	supervisors.get(i).username = iniEnv.get("CCD", "supervisor" + j+ ".name");
	        	supervisors.get(i).password = iniEnv.get("CCD", "supervisor" + j+ ".password");
	        	supervisors.get(i).agentType = "HBDirector";
	        	log.info("supervisors"+ j+ "=>" + supervisors.get(i).username + ": " + supervisors.get(i).password);
	        }
	        
	        
	        //######################## Reading XPath ######################################
	        iniXPath = new Wini(new File(xPathDir + "testXPath.ini"));  
	        
	        //####### starting SuperLoadAdmin to communicate with other nla #############
	        start_SuperLoadServer();
	        wait(3);
	        start_SuperLoadAdmin();
	        wait(2);
	        //This is needed to start supervisor_client
	        wait_for_input("\"@@@@ Start SupClient and press any key to continue >>>\"");

	     }
	     catch (Exception e) {
	        System.out.println(e);
	     }
	    
	    
	 }
	
	
	
	private void start_SuperLoadServer() throws Exception{
		// TODO Auto-generated method stub
		Supervisor_Server superServer;
		superServer = new Supervisor_Server();
		superServer.start();
		
	}
	
	private void start_SuperLoadAdmin() throws Exception{
	
		superAdmin = new Supervisor_Admin();
	}
	
	
	
	

}