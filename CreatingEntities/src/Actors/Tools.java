package Actors;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;



public final class Tools extends TestBaseObject {

	static ArrayList<CCSupervisor> ccSups= new ArrayList<CCSupervisor>();
	static ArrayList<CCAgent> ccAgents = new ArrayList<CCAgent>();
	static Hashtable<String, String> gVariableHash = new Hashtable<String, String>();
	static Hashtable<String, String> gCCDXPathHash = new Hashtable<String, String>();
	static Properties configFile = new Properties();
	static Tools tools;
	
	private Tools(){
		
	}
	
	public static Tools getInstance() throws Exception{
		if (tools == null){
			tools = new Tools();
			return tools;
		}else{
			return tools;
		}
	}
	
	public static Hashtable<String, String> readTestDataINI(){
		log.info("## Reading testData.ini ##");
		int max, i, j;
		
	    try{
	
	      //#### Read Global Variable from testData.ini
	        configFile.load(new FileInputStream("testData.ini"));
	        log.info(" I am here => " + configFile.getProperty("globalVariableMax"));
	        max = Integer.parseInt(configFile.getProperty("globalVariableMax"));
	        log.info("max is => " + max);
	        for(i=0; i < max; i++){
	        	j = i+1;
	        	gVariableHash.put(configFile.getProperty("globalVariable" + j+ ".name"), configFile.getProperty("globalVariable" + j+ ".value"));
	        }
	        
	        //#### Assign Global Variable from testData.ini (Main)
	        TestBaseObject.useWhichWebDriver = gVariableHash.get("webDriver");
	      
	     }
	     catch (Exception e) {
	        log.error(e);
	     }
		 return gVariableHash;    
	}
	
	public static Hashtable<String, String> readTestPropertyINI(){
		log.info("## Reading testProperty.ini ##");
		int max, i, j;
		
	    try{
	
	      //#### Read Global Variable from testData.ini
	        configFile.load(new FileInputStream("testProperty.ini"));
	        max = Integer.parseInt(configFile.getProperty("globalVariableMax"));
	        for(i=0; i < max; i++){
	        	j = i+1;
	        	gCCDXPathHash.put(configFile.getProperty("globalVariable" + j+ ".name"), configFile.getProperty("globalVariable" + j+ ".value"));
	        }
	      
	     }
	     catch (Exception e) {
	        log.error(e);
	     }
		 return gCCDXPathHash;    
	}




	
	
	public static ArrayList<CCSupervisor> readTestDataCSV_Super() throws FileNotFoundException, IOException{
		log.info("## Reading testData_Supervisors.csv ##");
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
            	
            	String[] superProperty = line.split(cvsSplitBy);
	        	ccSups.add(new CCSupervisor());
	        	ccSups.get(j).supName = superProperty[0];
	        	ccSups.get(j).supUserName = superProperty[1];
	        	ccSups.get(j).supCOS = superProperty[2];
	        	ccSups.get(j).supAgentName = superProperty[3];
	        	ccSups.get(j).supPermission = superProperty[4];
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
		return ccSups;

	}
	
	public static ArrayList<CCAgent> readTestDataCSV_Agent() throws FileNotFoundException, IOException{
		log.info("## Reading testData_Agents.ini ##");
	    int i, j;

        String csvFile = "testData_Agents.csv";
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
            	
            	String[] superProperty = line.split(cvsSplitBy);
	        	ccAgents.add(new CCAgent());
	        	ccAgents.get(j).agentName= superProperty[0];
	        	ccAgents.get(j).agentUserName = superProperty[1];
	        	ccAgents.get(j).agentID = superProperty[2];
	        	ccAgents.get(j).agenExtension= superProperty[3];
	        	ccAgents.get(j).agentCOS = superProperty[4];
	        	ccAgents.get(j).agentEmailAddress = superProperty[5];
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
		return ccAgents;

	}
	


}
