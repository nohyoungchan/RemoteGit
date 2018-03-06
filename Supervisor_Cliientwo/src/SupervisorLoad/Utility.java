package SupervisorLoad;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Properties;

import javax.imageio.ImageIO;

import org.ini4j.Wini;



public final class Utility extends TestObject{

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
	        log.info(e.toString());
	     }
	    
	    
	 }
	
    public static void log(String message) {
        log.info(message);
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
    
	  
	  /**
	   * process command from superload_server to return a string with a scenario name.
	   * @param strCommand this is a command from superload_server.
	   * @return
	   */
	  public static String processCommandForScreenshot(String strCommand, String strName, int numEnd) {
		    log.info("## String Command to process is => " + strCommand);
		    //This contains "screenshot scenario"
		    String[] strScenario = strCommand.split("@");
		    
		    //String strScenario1 = strCommand.split("@")[0].trim();

		    //This contains scenario name
	    	String strScenario2 = strScenario[1].trim();
	    	strScenario2 = strScenario2.replaceAll(" ", "_");
	    	strScenario2 = strScenario2.replaceAll("->", "_");
	    	strScenario2 = strScenario2.replaceAll(">", "_"); 
	    	
	    	//This part contains username(before @ part) and xpath like btnAnswer.
	    	String strScenario3 = strScenario[2].trim();
	    	strScenario3 = strScenario3.replaceAll(" ", "_");
	    	
	    	if (strScenario3.contains("Failed")) {
	    		strScenario3 = "ending_Failed";
	    	}
	    	
	    	//So it becomes like..
	    	//Voice_ACD_Answered_10.23.172.54_admin_100_agent1_starting
	    	//Voice_ACD_Answered_10.23.172.54_admin_101_agent1_btnAnswer
	    	strScenario2 = strScenario2 +"_" + strName + "_" + numEnd + "_" + strScenario3;

/*	    	
	    	
	    	if (strScenario1.contains("Starting")) {
	    		strScenario2 = strScenario2 + "_start";
	    		
	    	}else if (strScenario1.contains("Ending")){
	    		strScenario2 = strScenario2 + "_end";
	    		
	    	}else if (strScenario1.contains("Skipping")){
	    		strScenario2 = strScenario2 + "_skip";
	    		
	    	}*/
	    		    		
	    	log.info("## The processed string is => " + strScenario2);
	    	
	    	return strScenario2;
	  }
	  
	  
	  /**
	   * Capture screenshot of the full screen and save it as captureName
	   * @param captureName
	   */
	  public static void captureScreem(String captureName) {
		  try {
	            Robot robot = new Robot();
	            String format = "jpg";
	            String fileName = captureName + ".jpg";
	             
	            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
	            BufferedImage screenFullImage = robot.createScreenCapture(screenRect);
	            ImageIO.write(screenFullImage, format, new File(fileName));
	             
	            log.info("A full screenshot saved!");
	        } catch (AWTException | IOException ex) {
	            System.err.println(ex);
	        }
	  }
	  
	  
	  
	  
}
