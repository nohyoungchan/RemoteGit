package SupervisorLoad;

import java.io.BufferedReader;
import java.io.File;
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

import org.ini4j.Wini;



public final class Utility {
	//static Hashtable superServerHash = new Hashtable<String, String>();
	//public static Hashtable<String, String> gHash = new Hashtable<String, String>();
	static ArrayList<SupervisorClient> supClients = new ArrayList<SupervisorClient>();
	static ArrayList<SupervisorHBAgent> supHBAgent = new ArrayList<SupervisorHBAgent>();
	//static Properties configFile = new Properties();
	static Properties p = new Properties();
	public static Wini ini;
	
	/*public static void initiateAll_INI() throws FileNotFoundException, IOException{
		log("#### Initializing testData.ini file ");
		int i, j, max;
		gHash.clear();  //This is also used to reload everything.
		
		 configFile.load(new FileInputStream("testData.ini"));
	     max = Integer.parseInt(configFile.getProperty("globalVariableMax"));
	     for(i=0; i < max; i++){
	        	j = i+1;
	        	gHash.put(configFile.getProperty("globalVariable" + j+ ".name"), configFile.getProperty("globalVariable" + j+ ".value"));
	        	//log("## this ==> " + gHash.get(configFile.getProperty("globalVariable" + j+ ".name")));
	        }
	}*/
	
	
	public static void initiateAll_CSV() throws FileNotFoundException, IOException{
	    int i, j;
	    
	    log("#### Reading testDataSuperList.csv file  for supervisors");

        String csvFile = "testDataSuperList.csv";
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
            	//log("Line input is => " + line);
	        	supClients.add(new SupervisorClient());
	        	if (superProperty.length > 2)  //The length should be 3
	        	{ 	
	        		supClients.get(j).name = superProperty[0];
	        		supClients.get(j).password = superProperty[1];
	        		supClients.get(j).delaySec = superProperty[2];
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
	
	public static void initiateAllHBAgent_CSV() throws FileNotFoundException, IOException{
	    int i, j;
	    log("#### Reading testDataHBAgentList.csv file for all agents");

        String csvFile = "testDataHBAgentList.csv";
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
            	//log("Line input is => " + line);
            	supHBAgent.add(new SupervisorHBAgent());
	        	if (superProperty.length > 2)  //The length should be 3
	        	{ 	
	        		supHBAgent.get(j).name = superProperty[0];
	        		supHBAgent.get(j).password = superProperty[1];
	        		supHBAgent.get(j).delaySec = superProperty[2];
	        		supHBAgent.get(j).hgAgentURL= superProperty[3];
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
	
	public static void reload_CSV() throws FileNotFoundException, IOException{
		log("#### Reloading testDataSuperList.csv file ");
		
		supClients.clear();
		supHBAgent.clear();
		initiateAll_CSV();
		initiateAllHBAgent_CSV();
		
	   /* int i, j;

        String csvFile = "testDataSuperList.csv";
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
	        	//supClients.add(new SupervisorClient());
	        	supClients.get(j).name = superProperty[0];
	        	supClients.get(j).password = superProperty[1];
	        	supClients.get(j).delaySec = superProperty[2];
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
        }*/

	}
	
    public static void log(String message) {
        System.out.println(message);
    }
    
    public static void read_ConfigINI() throws FileNotFoundException, IOException{
		log("#### Reading testData.ini file ");
		ini = new Wini(new File("testData.ini"));
        //log("Port info => " + ini.get("SuperLoadServer_Config", "port"));
	}


}
