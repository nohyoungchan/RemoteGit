package HBTestesNG.BaseObjects;

import java.util.Hashtable;

public class TestResult extends TestObject{
	
	public String testName;
	public int errorCount;
	public Hashtable<String, String> errorHash; //String:Method name, String: error reason
	
	TestResult(String testName){
		this.testName = testName;
		errorCount = 0;
		errorHash = new Hashtable<String, String>();
		
	}
	
}
