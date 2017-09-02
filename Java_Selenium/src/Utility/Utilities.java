package Utility;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

import org.ini4j.Wini;



public final class Utilities {

	static Properties configFile = new Properties();
	public static Wini ini;

    
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
    
  
	  public void captureScreen(String strFileName) {
		  try {
	          Robot robot = new Robot();
	          String format = "jpg";
	          String fileName = strFileName + format;
	          ImageOutputStream file = (ImageOutputStream) new File(fileName);
	           
	          Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
	          BufferedImage screenFullImage = robot.createScreenCapture(screenRect);
	          ImageIO.write(screenFullImage, format, file);
	           
	          System.out.println("@@ A full screenshot saved!");
	      } catch (Exception e) {
	    	  System.out.println(e.toString());
	      }
	  }
	  
}
