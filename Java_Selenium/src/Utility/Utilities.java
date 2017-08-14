package Utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.Properties;
import org.ini4j.Wini;



public final class Utilities {

	static Hashtable globalVariableHash = new Hashtable<String, String>();
	static Properties configFile = new Properties();
	public static Wini ini;

	public static void readConfigFile(){
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
	    
	    
	 }
	
    public static void log(String message) {
        System.out.println(message);
    }
    
    public static String executeCommand(String command) {

		StringBuffer output = new StringBuffer();

		Process p;
		try {
			p = Runtime.getRuntime().exec(command);
			p.waitFor();
			BufferedReader reader =
                           new BufferedReader(new InputStreamReader(p.getInputStream()));

			String line = "";
			while ((line = reader.readLine())!= null) {
				output.append(line + "\n");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return output.toString();

	}
    
    public static void read_ConfigINI() {
		log("#### Reading testData.ini file ");
		try{
			ini = new Wini(new File("testData.ini"));
			//log("Port info => " + ini.get("SuperLoadServer_Config", "port"));
	    } catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    public void wait(int num) throws Exception{
		  log("* Wait : " + num + " seconds.");
		  Thread.sleep(num * 1000);
	  }
	  
	  public void wait(int num, String reason) throws Exception{
		  log("* Wait: " + reason +  " : " + num + " seconds.");
		  Thread.sleep(num * 1000);
	  }
	  
	  
}
