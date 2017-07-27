package BaseObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;



public class TBaseObject extends Thread{
	
	  public static Logger log= Logger.getLogger("LOG");
	  protected static long startTimeNano, endTimeNano;
	  protected static String startTimeStr, endTimeStr;
	  private static final int MINUTES_IN_AN_HOUR = 60;
	  private static final int SECONDS_IN_A_MINUTE = 60;
	  protected static final Object s_exitSignal = new Object();
	  protected static String usePhantomJS, globalScenario;
	  protected static String useWhichWebDriver, loginSequentially;
	  protected static int globalNumOfEmailSent, globalSec, globalMinToRelogIn;
	  protected String exceptionName;

	  
	  TBaseObject(){
		  PropertyConfigurator.configure(".\\log\\log4j.properties");
	  }
	 
	/*
	  public void log.info(String status) throws Exception {
		  System.out.println(status);
		  //log.info(status);
	  }
	  */
	  
	  public static void wait(int num) throws Exception{
		  log.info("* Wait : " + num + " seconds.");
		  Thread.sleep(num * 1000);
	  }
	  
	  public static void wait(int num, String reason) throws Exception{
		  log.info("* Wait: " + reason +  " : " + num + " seconds.");
		  Thread.sleep(num * 1000);
	  }
	  
	  public static void currentTime() throws Exception{
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
	  
	  public void waitMin(int num) throws Exception{
		  //log.info("@" + username + ": Wait : " + num + " minutes.");
		  //Thread.sleep(num * 60 * 1000);
		  
		  long currentTimeMili, reloginTimeMili;		  
		  Calendar cal = Calendar.getInstance();
			// Printing the current time
		  System.out.println("Current time:"+cal.getTime());	
		  currentTimeMili = cal.getTimeInMillis();
		  cal.set(Calendar.HOUR_OF_DAY, 23);
		  cal.set(Calendar.MINUTE, 59);
		  cal.set(Calendar.SECOND, 59);
		
		  reloginTimeMili = currentTimeMili + (num * 60 *1000);
		  cal.setTimeInMillis(reloginTimeMili);
		  //log.info("######## @" + username + ": Wait " + num + " min until :"+cal.getTime());
		  
		  Thread.sleep(num * 60 * 1000);
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
	  
	  protected static String timeConversion(int totalSeconds) {
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
			  log.info("\n###########################################################################################");
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
		    
		    public void printStrArray(String strWitnDivider, String strDivider){
		    	String[] strArray;
		    	int i;
		    	
		    	strArray = strWitnDivider.split(strDivider);
		    	for (i=0; i < strArray.length; i++){
		    		System.out.println(strArray[i]);
		    	}
		    	
		    }
		    
		    private void log(String message) {
	            System.out.println(message);
	        }

}
