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


//    public static Hashtable<String, String> gVariableHash;
//    public static Hashtable<String, String> gCCDXPathHash;
//    public static Hashtable<String, String> gHBAgentHash;
    public static ArrayList<CCSupervisor> ccSupers;
    public static ArrayList<CCAgent> ccAgents;
    public static AllEntities allEntities;
    public static HBDirector CCD; 
//    public static Properties configFile;
    public static Wini wini, pini;  //wini for testData.ini,  pini for testProperty.ini
  
    
    public AllEntities() throws Exception{
    	initiateAllActors();
	
	}
    
	//if usPhantomJS = yes, this uses phantom js.
	public void initiateAllActors() throws Exception{
		log.info("## Initializing all actors ##");
		
		ccAgents = new ArrayList<CCAgent>();
		ccSupers = new ArrayList<CCSupervisor>();
	
	
		readTestPropertyINI_ForHBAgent();
		readTestPropertyINI_ForHBSuper();
		read_testDataINI();
		read_testPropertyINI();
		TestBaseObject.useWhichWebDriver = wini.get("Supervisor", "webDriver");

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
	
	
	
    public static void read_testDataINI() throws FileNotFoundException, IOException{
		log.info("#### Reading testData.ini file ");
		wini = new Wini(new File("testData.ini"));
        //log("Port info => " + ini.get("SuperLoadServer_Config", "port"));
	}
    
    public static void read_testPropertyINI() throws FileNotFoundException, IOException{
		log.info("#### Reading testProperty.ini file ");
		pini = new Wini(new File("testProperty.ini"));
        //log("Port info => " + ini.get("SuperLoadServer_Config", "port"));
	}
	




	
	
	

	
	
	
	

}