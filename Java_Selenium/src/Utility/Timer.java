package Utility;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
 
public class Timer {
	private long startTime = 0;
	private long endTime = 0;
	private String LOG_FILE;

	public Timer(String aLOG_FILE) {
		LOG_FILE = aLOG_FILE;
	}

	public void start() throws IOException {
		this.startTime = System.currentTimeMillis();
		//logger.Log(LOG_FILE, "TIMER START -> "+this.dateParser(this.startTime));
	}

	public void end() throws IOException {
		this.endTime = System.currentTimeMillis();
		//logger.Log(LOG_FILE, "TIMER END -> "+this.dateParser(this.endTime));
	}

	public Date getStartTime() throws IOException {
		//logger.Log(LOG_FILE, "GET START -> "+this.dateParser(this.startTime));
		return this.dateParser(this.startTime);
	}

	public Date getEndTime() throws IOException {
		//logger.Log(LOG_FILE, "GET END -> "+this.dateParser(this.endTime));
		return this.dateParser(this.endTime);
	}

	public long getTotalTime() throws IOException {
		long deltaTime = this.endTime - this.startTime;
		//logger.Log(LOG_FILE, "GET TOTAL -> "+deltaTime+" ms");
		return deltaTime;
	}

	public Date dateParser(long unixTime){
		Date date = new Date ();
		date.setTime(unixTime);
		return date;
	}
	
}