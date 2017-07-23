package Actors;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


import org.ini4j.Wini;



public final class Utility {

	public static Wini ini;
    public static void log(String message) {
        System.out.println(message);
    }
    
    public static void read_ConfigINI() throws FileNotFoundException, IOException{
		log("#### Reading testData.ini file ");
		ini = new Wini(new File("testData.ini"));
        //log("Port info => " + ini.get("SuperLoadServer_Config", "port"));
	}


}
