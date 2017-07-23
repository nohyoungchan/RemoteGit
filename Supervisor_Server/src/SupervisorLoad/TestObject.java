package SupervisorLoad;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JDialog;
import javax.swing.JFrame;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class TestObject{
	
	 //public static Logger log= Logger.getLogger("LOG");  //not working well
	
	 TestObject(){
		 // PropertyConfigurator.configure("log4j.properties"); //not working well
	  }
	 
	  public static void waits(int num){
		  log("* Wait : " + num + " seconds.");
		  try {
			Thread.sleep(num * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
	  
	  public static void waits(int num, String reason){
		  log("* Wait: " + num + " (sec) => " + reason );
		  try {
			Thread.sleep(num * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
	  
	 public static void log(String message) {
	        System.out.println(message);
	 }
	 
	 public static void DialogueWait(int waitSec){
		 log("I am at Dialogue Wait 1");
		 JFrame f = new JFrame();
		 final JDialog dialog = new JDialog(f, "Test", true);

		 //Must schedule the close before the dialog becomes visible
		 ScheduledExecutorService s = Executors.newSingleThreadScheduledExecutor();     
		 s.schedule(new Runnable() {
		     public void run() {
		         dialog.setVisible(true); //should be invoked on the EDT
		         dialog.dispose();
		         log("I am at Dialogue Wait 2");
		     }
		 }, waitSec, TimeUnit.SECONDS);
		 log("I am at Dialogue Wait 3");

		  dialog.setVisible(false); // if modal, application will pause here

		  log("Dialog closed");
	 }
	 
	 public static String WaitForUserInput(int waitSec) throws IOException{
		 int x = waitSec; // wait 2 seconds at most
		 String strReturn;

		 log("@@@ Type any to stop this command within " + waitSec + " (sec) : ");
		 BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		 long startTime = System.currentTimeMillis();
		 while ((System.currentTimeMillis() - startTime) < x * 1000
		         && !in.ready()) {
		 }

		 if (in.ready()) {
		     log("==> You entered: " + in.readLine() + ", so leaving this command and wait for another :)");
		     strReturn = "yes";
		     
		 } else {
			 strReturn = "no";
		     log("==> You did not enter data, so repeating this command");
		 }
		 
		 return strReturn;
	 }
	 
	  public static String currentTime(){
		  String returnDate;
		  
		  DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		  Date date = new Date();
		  returnDate= dateFormat.format(date);
		  return returnDate;
		  //log("* Current date/time : " + returnDate);
	  }

}
