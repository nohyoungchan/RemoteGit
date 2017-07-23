package Utility;


import HBTestesNG.BaseObjects.*;
import org.openqa.selenium.WebElement;

public class PostCondition extends TestCaseObject{
	
	 public PostCondition() throws Exception{
		log.info("* Construction: PostCondition");
	}
	 
	@SuppressWarnings("finally")
	public boolean PostCondition_Chat_Answered(Agent agent){
		log.info("* PostCondition_Chat_Answered");
		Boolean result= true;
		String strXPath;
		strXPath = "chatDisconnectBtn";
		int intTimeOutSec = 30;
		WebElement element = null;
		
		try {
			//result = agent.existsElementXPath(strXPath);
			element = agent.waitUntilClickable(strXPath, intTimeOutSec);
			if (element != null) result = true;
		}catch (Throwable e){  //This catches all exception and errors
	    	log.info("General exception is catched");
	    	result= false;
	    }finally{
	    	return result;
	    }
	}
	

}
