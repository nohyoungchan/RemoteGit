package BaseObject;


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


public class AllMembers extends TBaseObject{


    public static Hashtable<String, String> gVariableHash;
    public static Hashtable<String, String> gCCDXPathHash;
    public static Hashtable<String, String> gHBAgentXPathHash;
    //public static AllObjects AllObjects;
    public static HBAgent hbAgent; 
    public static Properties configFile1, configFile2;
    public static Wini ini;
  
    
    public AllMembers() throws Exception{
    	//initiateAllActors();
	
	}
    
	//if usPhantomJS = yes, this uses phantom js.
	public void initiateAllActors() throws Exception{
		log.info("## Initializing all actors ##");
		configFile1 = new Properties();
		configFile2 = new Properties();
		gVariableHash = new Hashtable<String, String>();
		gCCDXPathHash = new Hashtable<String, String>();
		gHBAgentXPathHash = new Hashtable<String, String>();
		
		read_ConfigINI();
		//readTestDataINI();
		readTestProperty();
		
		//hbAgent = new HBAgent();
	    
	 }
	


	public void readTestDataINI(){
		log.info("## Reading testData.ini ##");
		
		int max, i, j;
		
	    try{
	
	      //#### Read Global Variable from testData.ini
	        configFile1.load(new FileInputStream("testData.ini"));
	        max = Integer.parseInt(configFile1.getProperty("globalVariableMax"));
	        //log.info("max is => " + max);
	        for(i=0; i < max; i++){
	        	j = i+1;
	        	gVariableHash.put(configFile1.getProperty("globalVariable" + j+ ".name"), configFile1.getProperty("globalVariable" + j+ ".value"));
	        	//log.info("## this ==> " + gVariableHash.get(configFile1.getProperty("globalVariable" + j+ ".name")));
	        }
	        	      
	     }
	     catch (Exception e) {
	        log.error(e);
	     }
	}
	
    public static void read_ConfigINI() {
		log.info("#### Reading testData.ini file ");
		try{
			ini = new Wini(new File("testData.ini"));
			//log("Port info => " + ini.get("SuperLoadServer_Config", "port"));
	    } catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void readTestProperty(){
		log.info("## Reading testProperty_XPath.ini ##");
		int max, i, j;
		
	    try{
	
	      //#### Read Global Variable from testData.ini
	        configFile2.load(new FileInputStream("testProperty_XPath.ini"));
	        max = Integer.parseInt(configFile2.getProperty("directorMax"));
	        
	        for(i=0; i < max; i++){
	        	j = i+1;
	        	gCCDXPathHash.put(configFile2.getProperty("director" + j+ ".name"), configFile2.getProperty("director" + j+ ".value"));
	        	
	        }
	        
	        max = Integer.parseInt(configFile2.getProperty("webAgentXPathMax"));
	        //log.info("max2 is => " + max);
	        for(i=0; i < max; i++){
	        	j = i+1;
	        	gHBAgentXPathHash.put(configFile2.getProperty("webAgentXPath" + j+ ".name"), configFile2.getProperty("webAgentXPath" + j+ ".value"));
	        	
	        	//log.info(i + " ) "+ configFile2.getProperty("webAgentXPath" + j+ ".name") + " => " + gHBAgentXPathHash.get(configFile2.getProperty("webAgentXPath" + j+ ".name"))); 
	        }
	      
	     }
	     catch (Exception e) {
	        log.error(e);
	     } 
	}
	

	
	

}