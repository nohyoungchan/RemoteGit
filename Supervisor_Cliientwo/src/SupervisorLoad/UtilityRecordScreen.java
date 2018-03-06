package SupervisorLoad;

import org.sikuli.script.KeyModifier;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

public final class UtilityRecordScreen extends TestObject{
	public static String imgFolder = ".\\Resources\\imgs\\";
	public static Screen screen = new Screen();
	
	  /**
	   * This waits until "image" appear and click.
	   * @param s  : This is screen object 
	   * @param img : This is image to click
	   * @param numTry: This is to try how many time to check if an image appears.
	   */
	  public static boolean clickAppear(Screen s, Pattern img, int numTry) {
		log.info("## clicking => " + img);
		int i = 0;
		boolean returnValue = true;
		try {
		while (true) {
				if (s.exists(img) != null) {
				    s.click(img);
				    log.info("## Image Clicked => " + img);
				    break;
				} 
				i++;
				Thread.sleep(2000);
				if (i > numTry) {
					log.info("## image doesn't appear after " + numTry + " Tries  => " + img);
					returnValue = false;
					break;
				}
			}
		}catch(Exception e) {
			returnValue = false;
			log.info("## Fail to click: " + img + " => " + e.toString());
		}
		
		return returnValue;
	  }
	  
	  /**
	   * This waits until "image" appear and click.
	   * @param s  : This is screen object 
	   * @param img : This is image to click
	   * @param numTry: This is to try how many time to check if an image appears.
	   */
	  public static boolean clickAppear(String img, int numTry) {
		log.info("## clicking => " + img);
		int i = 0;
		boolean returnValue = true;
		try {
		while (true) {
				if (screen.exists(img) != null) {
					screen.click(img);
				    log.info("## Image Clicked => " + img);
				    break;
				} 
				i++;
				wait(2);
				if (i > numTry) {
					log.info("## image doesn't appear after " + numTry + " Tries  => " + img);
					returnValue = false;
					break;
				}
			}
		}catch(Exception e) {
			returnValue = false;
			log.info("## Fail to click: " + img + " => " + e.toString());
		}
		
		return returnValue;
	  }
	  
	  /**
	   * This types strToType to a screen wherever mouse is pointed
	   * @param screen
	   * @param strToType
	   */
	  public static void typeToScreen(String strToType) {
		  log.info("## Typing to screen : " + strToType);
		  screen.write(strToType);
	  }
	  
	  /**
	   * This types strToType to a screen wherever mouse is pointed
	   * @param screen
	   * @param strToType
	   */
	  public static void startRecording() {
		  log.info("## Start Recording  ");
		  screen.type ("R", KeyModifier.CTRL | KeyModifier.SHIFT );
	  }
	  
	  /**
	   * This types strToType to a screen wherever mouse is pointed
	   * @param screen
	   * @param strToType
	   */
	  public static void stopRecording() {
		  log.info("## Stop Recording  ");
		  screen.type ("S", KeyModifier.CTRL | KeyModifier.SHIFT );
	  }
	  
	  /**
	   * This types strToType to a screen wherever mouse is pointed
	   * @param screen
	   * @param strToType
	   */
	  public static void enterFileNameAndSave(String strToType) {
		  log.info("## Enter file name : " + strToType);
		  
		  String fileNameBox = imgFolder +"iceFileNameTxtBox.PNG";
		  String okBtn = imgFolder + "iceOKBtn.PNG";
		  clickAppear(fileNameBox, 5);
		  screen.write(strToType);
		  clickAppear(okBtn, 5);
		  
	  }
	  
	  public static void wait(int waitSec) throws InterruptedException {
		  Thread.sleep(waitSec * 1000);
	  }
	  
	  /**
	   * process command from superload_server to return a string with a scenario name.
	   * @param strCommand this is a command from superload_server.
	   * @return
	   */
	  public static String processCommandForRecording(String strCommand) {
		    log.info("## String Command to process is => " + strCommand);
		    //This contains "screenshot scenario"
		    String[] strScenario = strCommand.split("@");

		    //This contains scenario name
	    	String strScenario1 = strScenario[1].trim();
	    	strScenario1 = strScenario1.replaceAll(" ", "_");
	    	strScenario1 = strScenario1.replaceAll("->", "_");
	    	strScenario1 = strScenario1.replaceAll(">", "_"); 
	    	
	    	//This part contains username(before @ part) and xpath like btnAnswer.
	    	String strScenario2 = strScenario[2].trim();
	    	strScenario2 = strScenario2.replaceAll(" ", "_");
	    	
	    	
	    	//reason to fail can have many \n, so just add "_Failed"
	    	if (strScenario2.contains("Failed")) {
	    		strScenario1 = strScenario1 + "_Failed";
	    	}


	    	log.info("## The processed string is => " + strScenario1);
	    	
	    	return strScenario1;
	  }
	  
	  
	  
	  
	  
	  
  


}
