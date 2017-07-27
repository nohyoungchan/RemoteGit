package SupervisorLoad;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Properties;

import org.ini4j.Wini;



public final class Utility extends TestObject {

	//static Hashtable globalVariableHash = new Hashtable<String, String>();
	//static Properties configFile = new Properties();
	public static Wini ini;
	
	/*public static void readConfigFile(){
		int max, i, j;
		
	    try{

		    //#### Read Global Variable from testData.ini (Main)
	        configFile.load(new FileInputStream("testData.ini"));
	        max = Integer.parseInt(configFile.getProperty("globalVariableMax"));
	        for(i=0; i < max; i++){
	        	j = i+1;
	        	globalVariableHash.put(configFile.getProperty("globalVariable" + j+ ".name"), configFile.getProperty("globalVariable" + j+ ".value"));
	        }
	        //String systemToTest = globalVariableHash.get("systemToTest");
	      
	     }
	     catch (Exception e) {
	        System.out.println(e);
	     }
	    
	    
	 }*/
	
    public static void read_ConfigINI() {
    	try{
    		
			log("#### Reading testData.ini file ");
			ini = new Wini(new File("testData.ini"));
			//log("Port info => " + ini.get("SuperLoadServer_Config", "port"));
    	}
	     catch (Exception e) {
	        System.out.println(e);
	     }	
	}
	
    public static void log(String message) {
        System.out.println(message);
    }
	

}
