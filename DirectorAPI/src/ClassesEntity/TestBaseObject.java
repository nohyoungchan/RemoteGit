package ClassesEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.ini4j.Wini;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.testng.Assert;



public class TestBaseObject extends Thread{
	
	  public static Logger log= Logger.getLogger("LOG");
	  protected static long startTimeNano, endTimeNano;
	  protected static String startTimeStr, endTimeStr;
	  private static final int MINUTES_IN_AN_HOUR = 60;
	  private static final int SECONDS_IN_A_MINUTE = 60;
	  protected static final Object s_exitSignal = new Object();
	  protected static String usePhantomJS, globalScenario;
	  protected static String useWhichWebDriver, loginSequentially;
	  protected static int globalNumOfEmailSent, globalSec;
	  protected String exceptionName;
	  
	  
      public static Wini ini, inij;
      public static String sysTT;

	  
	  protected TestBaseObject(){
		  PropertyConfigurator.configure(".\\log\\log4j.properties");
	  }
	 
	/*
	  public void log.info(String status) throws Exception {
		  System.out.println(status);
		  //log.info(status);
	  }
	  */
	  
	  public void wait(int num) throws Exception{
		  log.info("* Wait : " + num + " seconds.");
		  Thread.sleep(num * 1000);
	  }
	  
	  public void wait(int num, String reason) throws Exception{
		  log.info("* Wait: " + reason +  " : " + num + " seconds.");
		  Thread.sleep(num * 1000);
	  }
	  
	  public void currentTime() throws Exception{
		  DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		  Date date = new Date();
		  String date1= dateFormat.format(date);
		  log.info("* Current date/time : " + date1);
	  }
	  
	  public void currentTimeStart() throws Exception{
		  DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		  Date date = new Date();
		  startTimeStr = dateFormat.format(date);
		  startTimeNano = currentTimeNanosec();
	  }
	  
	  
	  public void currentTimeEnd() throws Exception{
		  DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		  Date date = new Date();
		  endTimeStr= dateFormat.format(date);
		  endTimeNano = currentTimeNanosec();
	  }
	  
	  
	  public static void waitUntilTomorrowOneAm(String waitTillOneAm) throws Exception{
		  
		  if (waitTillOneAm.contains("no")){return;}
		  
		  long currentTimeMili, endOfDay, oneAmTomorrowMili;
		  int secUNtilTommrowOneAm;

				  
		  Calendar cal = Calendar.getInstance();
			// Printing the current time
		  System.out.println("Current time:"+cal.getTime());	
		  currentTimeMili = cal.getTimeInMillis();
		  cal.set(Calendar.HOUR_OF_DAY, 23);
		  cal.set(Calendar.MINUTE, 59);
		  cal.set(Calendar.SECOND, 59);
		
		  endOfDay = cal.getTimeInMillis();
		  oneAmTomorrowMili = endOfDay + (60*60*1000);
		  secUNtilTommrowOneAm = (int) ((oneAmTomorrowMili- currentTimeMili)/1000);
		  log.info("$$$$ To start test, wait until tomorrow 1 am =>  " + timeConversion(secUNtilTommrowOneAm));
		  Thread.sleep(oneAmTomorrowMili- currentTimeMili);
		  
	  }
	  
	  //@Parameters({"waitBetweenTestCaseSec"})
	  public static void waitBetweenTestCase(int waitBetweenTestCaseSec) throws Exception{
		  
		  //if (waitOrNot.contains("no")){return;}
		  log.info("* Wait for the next test case: " + waitBetweenTestCaseSec + " seconds");
		  Thread.sleep(waitBetweenTestCaseSec * 1000);
		  
	  }
	  
	  public static void waitUntil(int day, int hour,String waitOrNot) throws Exception{
		  
		  //ToDo
		  if (waitOrNot.contains("no")){return;}
		  
		  long currentTimeMili, endOfDay, oneAmTomorrowMili;
		  int secUNtilTommrowOneAm;

				  
		  Calendar cal = Calendar.getInstance();
			// Printing the current time
		  System.out.println("Current time:"+cal.getTime());	
		  currentTimeMili = cal.getTimeInMillis();
		  cal.set(Calendar.HOUR_OF_DAY, 23);
		  cal.set(Calendar.MINUTE, 59);
		  cal.set(Calendar.SECOND, 59);
		
		  endOfDay = cal.getTimeInMillis();
		  oneAmTomorrowMili = endOfDay + (60*60*1000);
		  secUNtilTommrowOneAm = (int) ((oneAmTomorrowMili- currentTimeMili)/1000);
		  log.info("$$$$ To start test, wait until tomorrow 1 am =>  " + timeConversion(secUNtilTommrowOneAm));
		  Thread.sleep(oneAmTomorrowMili- currentTimeMili);
		  
	  }
	  
	  private static String timeConversion(int totalSeconds) {
	        int hr = totalSeconds / MINUTES_IN_AN_HOUR / SECONDS_IN_A_MINUTE;
	        int min = (totalSeconds - (hoursToSeconds(hr)))
	                / SECONDS_IN_A_MINUTE;
	        int sec = totalSeconds
	                - ((hoursToSeconds(hr)) + (minutesToSeconds(min)));

	        return hr + " hr " + min + " min " + sec + " sec";
	    }

	    private static int hoursToSeconds(int hours) {
	        return hours * MINUTES_IN_AN_HOUR * SECONDS_IN_A_MINUTE;
	    }

	    private static int minutesToSeconds(int minutes) {
	        return minutes * SECONDS_IN_A_MINUTE;
	    }
	    
		  public long currentTimeNanosec() throws Exception{
			  long currentTime = System.nanoTime();
			  return currentTime;
		  }
		  
		  public void totalExecutionTime() throws Exception{
			  long durationNanoSec;
			  durationNanoSec = endTimeNano - startTimeNano;
			  System.out.println("\n\n#############################################################");
			  //log.info("### times: Start=>" + startTime + ", End=> " + endTime);
			  log.info("### times: @@Start=>" + startTimeStr + ", @@End=> " + endTimeStr);
			  //log.info("### Call Duration was(Nano Sec) : " + durationNanoSec);
			  log.info("### Total execution time: " 
					  + TimeUnit.NANOSECONDS.toDays(durationNanoSec) + "(Days) " 
					  + TimeUnit.NANOSECONDS.toHours(durationNanoSec)%24 + "(Hour) " 
					  + TimeUnit.NANOSECONDS.toMinutes(durationNanoSec)%60 + "(Min) " + 
					  + TimeUnit.NANOSECONDS.toSeconds(durationNanoSec)%60 + "(Sec)");
		  }
		  
		  protected Date nanosecondsToDate(long nanoseconds) throws ParseException{
			  String target = "1904/01/01 12:00 AM";  // Your given date string

			  long millis = TimeUnit.MILLISECONDS.convert(nanoseconds, TimeUnit.NANOSECONDS); 

			  DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd hh:mm aaa");
			  formatter.setTimeZone(TimeZone.getTimeZone("US/Pacific"));
			  Date date = formatter.parse(target);

			  long newTimeInmillis = date.getTime() + millis;

			  Date date2 = new Date(newTimeInmillis);
			  return date2;
		  }
		  
		    public static void wait_for_inputToEnd(String strMessage) {
				try {
				    System.out.print("#### " + strMessage +" >>>");
				    int c = System.in.read();
				} catch(IOException e) {
				    System.out.println("caught exception" + e);
				}
		    }
		    
		    public static void wait_for_input() {
				try {
				    System.out.print("press a key >>>");
				    int b = System.in.read();
				} catch(IOException e) {
				    System.out.println("caught exception" + e);
				}
		    }
		    
		    public static void executeShellCommand(String strCommand) throws IOException{
					Runtime.getRuntime().exec(strCommand);
					
		    }
		    
		    //This converts integer into String with leading number of 0: Fixed width.
		    public String intToStringWithLeadingZero(int width, int number) {
		        String numberAsString = String.format("%0"+width+"d", number);
		        return numberAsString;
		    }
		    
		    public String intToString(int number) {
		        String numberAsString = Integer.toString(number);
		        return numberAsString;
		    }
		    
		    public int stringToInt(String number) {
		        int numberAsString = Integer.parseInt(number);
		        return numberAsString;
		    }
		    
		    /**
		     * printing string array
		     * @param strWithDivider  : This is whole string
		     * @param strDivider : This is divider
		     * @return : Return the string with divider
		     */
		    public static boolean printStrArray(String strWithDivider, String strDivider){
		    	String[] strArray;
		    	int i;
		    	
		    	strArray = strWithDivider.split(strDivider);
		    	if (strArray.length < 2) {
		    		log.info("@ String Returned is not good: " + strWithDivider);
		    		return false;
		    	}
		    	for (i=0; i < strArray.length; i++){
		    		if ( i == strArray.length-1) strDivider = "\n";
		    		System.out.println(strArray[i] + " " +strDivider);
		    		//log.debug(strArray[i] + " " +strDivider);
		    	}
		    	
		    	return true;
		    	
		    	
		    }
		    
		    public static String removeFirstLastBracket(String strJasonMessage){
		    	// Remove the first [, and the last ] since json fails with them.
		    	int i, j;
		    	String strSubString;
		    	// Remove only the first [, and the last ], not in the middle
		    	if (strJasonMessage.indexOf("[") >2) return strJasonMessage;
		    	// Remove the first [, and the last ] since json fails with them.
		    	i = strJasonMessage.indexOf("[");
		    	//System.out.println("Index of [ is => " + i);
		    	j = strJasonMessage.length();
		    	strSubString = strJasonMessage.substring(i+1, j-1);
		    	strSubString = strSubString.trim();
		    	//System.out.println("final sub string " + strSubString);
		    	
				return strSubString;
		    }
		    
		    /**
		     * This appends strToAppend to strFirst, and return the combined string.
		     * @param strFirst
		     * @param strToAppend
		     * @return
		     */
		    public static String printA(String strFirst, String strToAppend ){
		    	String strTemp;
		    	strTemp = strFirst + strToAppend;
		    	System.out.println(strTemp);
		    	
		    	return strTemp;
		    	
		    }
		    
		    /**
		     * This is the same as Sysem.out.println(String strToPrint) except returning strToPrint.  
		     * I just don't like to type System.out.println
		     * @param strToPrint
		     * @return strToPrint + "\n"
		     */
		    public static String printS(String strToPrint ){
		    	System.out.println(strToPrint);
		    	
		    	return strToPrint + "\n";
		    	
		    }
		    
		    
		    /**
		     * 
		     * @param strJasonMessage	: Jason-message
		     * @param strEntity			: Entity like agents, groups
		     * @return					: Returning Jason-array based on strEntity
		     * @throws JSONException
		     */
		    public static JSONArray returnJasonArray(String strJasonMessage, String strEntity) throws JSONException{
		    	String strSubString;
		    	JSONArray arr;
		    	arr = null;
		      	// Remove the first [, and the last ] since json fails with them.
		    	strSubString = removeFirstLastBracket(strJasonMessage);
		    	if (strSubString.isEmpty()){
		    		Assert.fail("@ The string is empty, so I am out");
		    	}
		    	
		    	try{
		    	//Get the agents array.
		    		JSONObject obj = new JSONObject(strSubString);
		    		arr = obj.getJSONArray(strEntity);
		    	}catch (JSONException e){
		    		Assert.fail("@@ Failed on returnJasonArray because no jason entity for  => " + strEntity);
		    		//log.info("@@ Failed on returnJasonArray because no jason entity for  => " + strEntity);	
		    	}
		    	
		    	return arr;
		    }
		    
		    

}
