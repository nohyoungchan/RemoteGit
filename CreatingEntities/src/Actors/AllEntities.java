package Actors;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Properties;

import org.ini4j.Wini;


public class AllEntities extends TestBaseObject{


    public static Hashtable<String, String> gVariableHash;
    public static Hashtable<String, String> gCCDXPathHash;
    public static Hashtable<String, String> gHBAgentHash;
    public static ArrayList<CCSupervisor> ccSupers;
    public static ArrayList<CCAgent> ccAgents;
    public static AllEntities allEntities;
    public static HBDirector CCD; 
    public static Properties configFile;
    public static Wini wini;
  
    
    public AllEntities() throws Exception{
    	initiateAllActors();
	
	}
    
	//if usPhantomJS = yes, this uses phantom js.
	public void initiateAllActors() throws Exception{
		log.info("## Initializing all actors ##");
		
		ccAgents = new ArrayList<CCAgent>();
		ccSupers = new ArrayList<CCSupervisor>();
		gVariableHash = new Hashtable<String, String>();
		gCCDXPathHash = new Hashtable<String, String>();
		gHBAgentHash = new Hashtable<String, String>();
		
		
		
		configFile = new Properties();
		
		readTestPropertyINI_ForHBAgent();
		readTestPropertyINI_ForHBSuper();
		//gVariableHash = readTestDataINI();
		gCCDXPathHash = readTestPropertyINI_ForHBDirector();
		read_testDataINI();

		CCD = new HBDirector();

	    
	 }
	
	public static void readTestPropertyINI_ForHBAgent() throws FileNotFoundException, IOException{
	    int i, j;

        String csvFile = "testData_Agents.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {
            br = new BufferedReader(new FileReader(csvFile));
            i = 0; j =0;
            while ((line = br.readLine()) != null) {
            	//This is to ignore header(Field names)
            	if(i ==0){
            		i++;
            		continue;
            	}
            	

            	
            	String[] agentContent = line.split(cvsSplitBy);
                
            	//log("Line input is => " + line);
	        	ccAgents.add(new CCAgent());
	        	if (agentContent.length > 2)  //The length should be 3
	        	{ 	
        		    log.info("Name is => " + i);
        		    log.info(ccAgents.get(j).agentName);
		        	ccAgents.get(j).agentName= agentContent[0];
		        	log.info(" I am here at read 1 " + agentContent[0]);
		        	ccAgents.get(j).agentUserName= agentContent[1];
		        	log.info(" I am here at read 1 " + agentContent[1]);
		        	ccAgents.get(j).agentID= agentContent[2];
		        	ccAgents.get(j).agenExtension= agentContent[3];
		        	ccAgents.get(j).agentCOS= agentContent[4];
		        	ccAgents.get(j).agentEmailAddress= agentContent[5];;
		        	
		        	log.info("CCAgent"+ j+ "=>" +ccAgents.get(j).agentName + ": " + ccAgents.get(j).agentEmailAddress);
	        	}
	        	i++;j++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

	}
	
	public static void readTestPropertyINI_ForHBSuper() throws FileNotFoundException, IOException{
	    int i, j;

        String csvFile = "testData_Supervisors.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {
            br = new BufferedReader(new FileReader(csvFile));
            i = 0; j =0;
            while ((line = br.readLine()) != null) {
            	if(i ==0){
            		i++;
            		continue;
            	}
            	//name,userName,supCOS,agentName,supPermission
            	
            	String[] superContent = line.split(cvsSplitBy);
            	//log("Line input is => " + line);
	        	ccSupers.add(new CCSupervisor());
	        	if (superContent.length > 2)  //The length should be 3
	        	{ 	
        		
	        		ccSupers.get(j).supName= superContent[0];
	        		ccSupers.get(j).supUserName= superContent[1];
	        		ccSupers.get(j).supCOS= superContent[2];
	        		ccSupers.get(j).supAgentName= superContent[3];
	        		ccSupers.get(j).supPermission= superContent[4];
		        	
		        	log.info("CCSuper"+ j+ "=>" +ccSupers.get(j).supName + ": " + ccSupers.get(j).supUserName);
	        	}
	        	i++;j++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        	
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

	}
	
	


	public Hashtable<String, String> readTestDataINI(){
		log.info("## Reading testData.ini ##");
		Hashtable<String, String> gHash;
		gHash = new Hashtable<String, String>();
		
		int max, i, j;
		
	    try{
	
	      //#### Read Global Variable from testData.ini
	        configFile.load(new FileInputStream("testData.ini"));
	        max = Integer.parseInt(configFile.getProperty("globalVariableMax"));
	        //log.info("max is => " + max);
	        for(i=0; i < max; i++){
	        	j = i+1;
	        	gHash.put(configFile.getProperty("globalVariable" + j+ ".name"), configFile.getProperty("globalVariable" + j+ ".value"));
	        	//log.info("## this ==> " + gHash.get(configFile.getProperty("globalVariable" + j+ ".name")));
	        }
	        
	        //#### Assign Global Variable from testData.ini (Main)
	        TestBaseObject.useWhichWebDriver = gHash.get("webDriver");
	      
	     }
	     catch (Exception e) {
	        log.error(e);
	     }
		 return gHash;    
	}
	
	public Hashtable<String, String> readTestPropertyINI_ForHBDirector(){
		log.info("## Reading testProperty.ini ##");
		int max, i, j;
		Hashtable<String, String> gCCDXPathHash = new Hashtable<String, String>();
		
	    try{
	
	      //#### Read Global Variable from testData.ini
	        configFile.load(new FileInputStream("testProperty.ini"));
	        max = Integer.parseInt(configFile.getProperty("directorMax"));
	        for(i=0; i < max; i++){
	        	j = i+1;
	        	gCCDXPathHash.put(configFile.getProperty("director" + j+ ".name"), configFile.getProperty("director" + j+ ".value"));
	        }
	      
	     }
	     catch (Exception e) {
	        log.error(e);
	     }
		 return gCCDXPathHash;    
	}
	
    public static void read_testDataINI() throws FileNotFoundException, IOException{
		log.info("#### Reading testData.ini file ");
		wini = new Wini(new File("testData.ini"));
        //log("Port info => " + ini.get("SuperLoadServer_Config", "port"));
	}
	




	
	
	

	
	
	
	

}