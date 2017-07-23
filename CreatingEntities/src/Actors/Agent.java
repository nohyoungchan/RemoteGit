package Actors;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.eclipse.jetty.util.log.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;


//import com.sun.webkit.Utilities;

import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionNotFoundException;
import org.openqa.selenium.support.ui.Select;


public class Agent extends TestBaseObject {
	
	
	protected String username, password, agentType, state, scenarioLocal;
	public String extension, did;  //This needs to be public
	protected WebDriver driver;
	public NoSuchElementException noSuchElementException;
	public Dimension dn;
	public Point pt;
	
	public Thread thread;
	public String threadName;
	protected boolean booleanMaximized;
	
	protected int numOfCapture;
	protected JavascriptExecutor executor;  //This is to help click
	protected boolean dimensionChecked;
	

	
	public Agent() throws Exception{
		booleanMaximized = false;
		numOfCapture = 0;
		dimensionChecked = false;
		
		
		log.info("## WebBrowser is => " + TestBaseObject.useWhichWebDriver);
		
	   switch (TestBaseObject.useWhichWebDriver) 
	   {
         case "chrome":
         	log.info("@ WebDriver=> chrome"); 
         	openChrome();
        	break;
         case "firefox":
        	 log.info("@ WebDriver=> firefox"); 
        	 openFireFox();
            break;
         case "ie":
        	 log.info("@ WebDriver=> ie");  
        	 openIE();
             break;
         case "phantomjs":
        	 log.info("@ WebDriver=> phantomjs"); 
        	 openPhantomJS();
         	 break;
         case "remote":
        	 log.info("@ WebDriver=> remote");  
        	 openRemote();
         	 break;
         case "safari":
        	 log.info("@ WebDriver=> safari"); 
        	 openSafari();
         	 break;
         default:
             log.info("No matching WebDriver");
	   }
		//This is for exception handling.  This is either idle/busy.
		//The default is "idle".
		//When exception happen, only "busy" agents are reset.
		state = "idle"; 
		executor = (JavascriptExecutor) driver;
	}
	
	public void openWebBrowser() throws Exception{
		
		   switch (TestBaseObject.useWhichWebDriver) 
		   {
	         case "chrome":
	         	log.info("@ WebDriver=> chrome"); 
	         	openChrome();
	        	break;
	         case "firefox":
	        	 log.info("@ WebDriver=> firefox"); 
	        	 openFireFox();
	            break;
	         case "ie":
	        	 log.info("@ WebDriver=> ie");  
	        	 openIE();
	             break;
	         case "phantomjs":
	        	 log.info("@ WebDriver=> phantomjs"); 
	        	 openPhantomJS();
	         	 break;
	         case "remote":
	        	 log.info("@ WebDriver=> remote");  
	        	 openRemote();
	         	 break;
	         case "safari":
	        	 log.info("@ WebDriver=> safari"); 
	        	 openSafari();
	         	 break;
	         default:
	             log.info("No matching WebDriver");
		   }
			//This is for exception handling.  This is either idle/busy.
			//The default is "idle".
			//When exception happen, only "busy" agents are reset.
			state = "idle"; 
			executor = (JavascriptExecutor) driver;
	}

	@Override
	public void run() {
		// This is implemented on extended class
		
	}
	
	public void openFireFox() throws Exception
	{
		log.info("\n@ #### Opening firefox ####");
		ProfilesIni profile = new ProfilesIni();
		FirefoxProfile ffprofile = profile.getProfile("Y_Test");
		
	    driver = new FirefoxDriver(ffprofile);
	    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	    driver.get("http://www.google.com");
	      
	}
	
	public void openChrome() throws Exception
	{
		log.info("\n@ #### Opening Chrome ####");
		/*ChromeOptions options = new ChromeOptions();
		options.addArguments("chrome.switches","--disable-extensions");
		System.setProperty("webdriver.chrome.driver",(System.getProperty("user.dir") + "//src//test//resources//chromedriver_new.exe"));
		*/
		
		ChromeOptions options = new ChromeOptions();
		options.addArguments("chrome.switches","--disable-extensions");
		//System.setProperty("webdriver.chrome.driver", "C:\\young\\HBLoadAutomation\\chromedriver.exe");
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
	    driver = new ChromeDriver(options);
	    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	    driver.get("http://www.google.com"); 
	}
	
	public void openIE() throws Exception
	{
		log.info("\n@ #### Opening IE: not ready yet");
		return;
		//System.setProperty("webdriver.ie.driver", "C:\\young\\HBLoadAutomation\\MicrosoftWebDriver.exe");
		//System.setProperty("webdriver.ie.driver", "C:\\young\\HBLoadAutomation\\IEDriverServer.exe");
		//driver = new InternetExplorerDriver();
	    //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	    //driver.get("http://www.google.com"); 
	}
	
	public void openSafari() throws Exception
	{
		log.info("\n@ #### Opening Safari is not ready####");
		return;
		//System.setProperty("webdriver.chrome.driver", "C:\\young\\HBLoadAutomation\\chromedriver.exe");
	    //driver = new ChromeDriver();
	    //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	   // driver.get("http://www.google.com"); 
	}
	
	
	
	
	public void openPhantomJS() throws Exception
	{
		log.info("\n@ #### Opening firefox using PhantomJS ####");
		
		DesiredCapabilities desireCaps = new DesiredCapabilities();
		//desireCaps.setJavascriptEnabled(true);
		desireCaps.setCapability("browser", "firefox");
		desireCaps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, 
				new String[] {"--ssl-protocol=tlsv1", "--web-security=no", "--ignore-ssl-errors=yes"});
		desireCaps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, 
				"C:/young/HBLoadAutomation/phantomjs.exe");
		
		driver = new PhantomJSDriver(desireCaps);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	    driver.get("http://www.google.com");	       
	}
	
	
	public void openRemote()
	{
		String hubURL;
		//hubURL = "http://10.23.176.58:5555/grid/register";
		//hubURL = "http://10.23.176.58:4441/wd/hub";
		//hubURL = "http://10.23.176.58:4444/wd/hub";
		//hubURL = "http://127.0.0.1:4444/wd/hub";
		hubURL = "http://10.23.172.103:5555/wd/hub";
		//hubURL = "http://10.23.172.103:5555/grid/register";
		
		DesiredCapabilities caps = new DesiredCapabilities();
		//caps.firefox();
		//caps.setPlatform(Platform.MAC);
		//caps.setCapability("resolution", "1200x1250");
		caps.setCapability("browser", "firefox");
		caps.setCapability("browser_version", "44.0.2");
		//caps.setCapability("os", "OS X");
		//caps.setCapability("os_version", "Yosemite");
		caps.setCapability("acceptSslCerts", "true");
		caps.setCapability("platform", Platform.WINDOWS);
		caps.setCapability("binary", "C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe"); 
		//caps.setCapability("binary", "/ms/dist/fsf/PROJ/firefox/16.0.0/bin/firefox"); 
		
		 try {
			driver = new RemoteWebDriver(new URL(hubURL), caps);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		    driver.get("http://www.google.com");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	

	}
	
	public void captureScreenshot(String screenShotName) {
		
		String nameString;
		int i;
		i = numOfCapture+1;
		
		if (numOfCapture > 4){
			log.info("\n@ " +  username+ " : made 4 screenshots already. so no more screenshots.");
			return;
		}
		
		try{
			nameString = screenShotName+"_"+ agentType + "_" + username + "_" + currentTimeWithDash();
			TakesScreenshot ts = (TakesScreenshot)driver;
			File src = ts.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(src, new File("./Screenshots/"+ nameString + ".jpg" ));
			log.info("\n@ " +  username+ "=> took " + i + "th screenshot : "+ nameString + ".jpg");
			numOfCapture++;
			
		}catch(Exception e){
			log.info("\n@ " +  username+ "=> Exception while taking a screenshot");	
			
		}
	}
	
	
	  public void tearDownAll() throws Exception {
		log.info("\n@ " +  username+ " #### Close FireFox ####");
	    driver.quit();
	    //currentTime();	    
	  }
	  
	  
	  public void clickXPath(String btnToPress) throws Exception, NoSuchElementException {
		    //out means an agent is in a weird state, so don't do anything.
/*		  	if (state.contains("weird")) {
		  		log.info("@" + username + ": The state is $$$$ weird $$$$. So skip this step");
		  		return;
		  	}*/
		    for (int second = 0;; second++) {
		    	log.info("@" + username + ":" + second +": Wait for : " + btnToPress);
		    	//This is because of stale exception.
	    		if (existsElementXPath(btnToPress)) break;
		    	wait(2); 
		    	
		  
		    	//This is to check if an agent is released whtn trying to answer.
		    	if (second > 1 && btnToPress.contains("btnAnswer")){
		    		log.info("@" + username + ": should be resumed to answer.  Resuming the agent" );
		    		click_XPath(("btnResume"));
		    	}
		    	
		    	//This is when signout button is not visible, just close firefox.
		    	if (second > 4 && btnToPress.contains(".//*[@id='header']/div/ng-include/div[3]/div[1]")){
		    		log.info("@" + username + ": SingOut is not visible =>" + btnToPress );
		    		return;
		    	}

		    	
		    	if (second > 7) {
		    		state = "weird";
		    		log.error("@" + username + ":Button desn't exist(Wrong) and change state to $$$ weird $$$ => " +btnToPress+ "\n\n");
		    		//throw noSuchElementException;
		    		break;
		    	}
		    }
            driver.findElement(By.xpath(btnToPress)).click();
            log.info("@" + username+ ": Clicked=> " + btnToPress);
		    
	}
	  
	  
	  
	
	  
	  public boolean retryClickXPath(String strXPath) {
	        boolean result = false;
	        int attempts = 0;
	        while(attempts < 5) {
	            try {
	                driver.findElement(By.xpath(strXPath)).click();
	                log.info("@" + username+ ": Clicked=> " + strXPath);
	                result = true;
	                break;
	            } catch(StaleElementReferenceException e) {
	            	log.info("@" + username+ "("+ attempts + ") * StaleElementReferenceException happened, and retry");
	            }
	            attempts++;
	        }
	        return result;
	}
	  
	  public boolean clickElementXPath_Reset(String btnToPress) throws Exception {
	    	log.info("## Reset ## @" + username + ": Wait for: " + btnToPress);
	    	try {
	    		if (existsElementXPath(btnToPress)){ 
	    		    driver.findElement(By.xpath(btnToPress)).click();
			        log.info("## Reset ## @" + username+ ": Clicked=> " + btnToPress);
			        return true;
	    		}else{
	    			log.info("## Reset ## @" + username+ ": It doesn't exist=> " + btnToPress);
	    			return false;
	    		}
	    	}catch(NullPointerException e){
				  log.info("NummPointerException is thrown at clickElementXPath_Reset function: " + e.toString());
				  return false;
	    	}

	}
	  
	//This function check if the previous action was completed successfully.  
	//Fore example
	//After login, strXPath=signout webelement, strName = usernam
	@SuppressWarnings("finally")
	public boolean checkResultXPath(String strXPath, String strName) {
		log.info("\n@ " +  username+ " CheckResultXPath");
		String txtReturned;
		WebElement webElement;
		boolean success;
		String[] output;
		success = true;
		try {
		    webElement= driver.findElement(By.xpath(strXPath));
		    log.info("\n@ " +  username+ " : XPath=> " + strXPath + " : exists");
		    txtReturned = webElement.getText(); //something wrong with this part..returning blank
		    log.info("#### 1) text on element=> " + txtReturned + "::: comparing text => " + strName);

		    //Split based on @, and remove only the 1st string.
			output = txtReturned.split("@");
			txtReturned = output[0];
			output = strName.split("@");
			strName = output[0];
			
			log.info("#### 2) text on element=> " + txtReturned + "::: comparing text => " + strName);

		    
		    if (txtReturned.contains(strName)) 
			  {
			      log.info("\n@ " +  username+ " Text=> " + strName  + " : exists");

			  }else{
				  log.info("\n@ " +  username+ " Text=> " + strName  + " : doesn't exist");
				  success = false;
			  }
		} catch (NoSuchElementException e) {
			log.info("NoSuchElementExceptio is catched");
			success = false;
		}catch (Exception e){  //This catches all exception and errors
			log.info("General exception is catched");
			success = false;
		}finally {
			return success;
		}
	}
	
	  private boolean existsElementXPath(String strXPath) {
		    try {
		        driver.findElement(By.xpath(strXPath));
		        log.info("XPath=> " + strXPath + " : exists");
		    } catch (NoSuchElementException e) {
		    	log.info("NoSuchElementExceptio is catched");
		        return false;
		    }catch (Throwable e){  //This catches all exception and errors
		    	log.info("General exception is catched");
		    	return false;
		    }
		    return true;
		}
	  
	  
	  private boolean existsElementID(String strID) {
		    try {
		        driver.findElement(By.id(strID));
		    } catch (NoSuchElementException e) {
		        return false;
		    }
		    return true;
		}
	  

	  
	  
	public void typeElementXPath(String btnToPress, String strToType) throws Exception {		    
		    clickAndSelectAllByXPath(btnToPress);
		    //driver.findElement(By.xpath(btnToPress)).click();
		    driver.findElement(By.xpath(btnToPress)).sendKeys(strToType);
		    log.info("@" + username+ ": Typed=> " + strToType);
	}
	
	public void typeElementXPathWithoutSelectAll(String btnToPress, String strToType) throws Exception {		    
	    //clickAndSelectAllByXPath(btnToPress);
	    //driver.findElement(By.xpath(btnToPress)).click();
	    driver.findElement(By.xpath(btnToPress)).sendKeys(strToType);
	    log.info("@" + username+ ": Typed=> " + strToType);
}
	
	  public void clickElementID(String elementID) throws Exception {
		    for (int second = 0;; second++) {
		    	log.info("@" + username + ":" + second +": Wait for : " + elementID);
		    	try { 
		    		if (existsElementID(elementID)) 
		    			break; 
		    		} catch (Exception e) {}
		    	wait(2); 
		    	if (second > 10) {
		    		log.error("ID doesn't exist(Wrong!) => " +elementID);
		    		throw noSuchElementException;
		    		//Assert.assertTrue(false, "ID doesn't exist(Wrong!) => " +elementID);
		    	}
		    }

		    driver.findElement(By.id(elementID)).click();
		    log.info("@" + username+ ": Clicked=> " + elementID);
		    //Assert.assertFalse(true, "Element exist and clicked! => " + elementID);
		    //currentTime();
	}
	  

	  
	public void typeElementID(String elementID, String strToType) throws Exception {		
		    clickTextBoxByID(elementID);
		    driver.findElement(By.id(elementID)).sendKeys(strToType);
		    log.info("@" + username+ ": Typed=> " + strToType);
		    //currentTime();
	}
	  
	  
	  public void clickTextBoxByID(String elementID) throws Exception{
		    clickElementID(elementID);
		    wait(1);
		    driver.findElement(By.id(elementID)).sendKeys(Keys.CONTROL + "a"); 
		    wait(1);
		    //driver.findElement(By.id(elementID)).sendKeys(Keys.DELETE); //clear() doesn't work.
		    //wait(1);
	  }
	  
	  public void clickAndSelectAllByXPath(String strXPath) throws Exception{
		    for (int second = 0;; second++) {
		    	log.info("@" + username + ":" + second +": Wait for : " + strXPath);
		    	try { 
		    		if (existsElementXPath(strXPath)) 
		    			break; 
		    		} catch (Exception e) {}
		    	wait(2); 
		    	if (second > 10) {
		    		log.error("It doesn't exist(Wrong!) => " +strXPath);
		    		throw noSuchElementException;
		    	}
		    }

		    retryClickXPath(strXPath);
		    //driver.findElement(By.xpath(strXPath)).click();
		    log.info("@" + username+ ": Clicked=> " + strXPath);
		    wait(1);
		    driver.findElement(By.xpath(strXPath)).sendKeys(Keys.CONTROL + "a"); 
		    wait(1);
	  }
	  
	  public boolean enterXPath(String strToEnter, String strXPath) throws Exception{ 
		  
			 WebElement webElement;
			 boolean returnResult;
			 int waitTimeSec;
			 
			 webElement = null;
			 returnResult = true;
			 waitTimeSec = 60;

			 try{
				webElement = waitUntilClickable(strXPath, waitTimeSec); 
				if (webElement != null) {
					webElement.sendKeys(Keys.CONTROL + "a"); 
					webElement.sendKeys(strToEnter);
					log.info("\n@ " + username + " Entered=> " + strToEnter + " at " + strXPath);
				}else {
					log.error("\n@ " + username + "=>@@@@ Fail to Enter => " +  strToEnter + "since it is null => "+ strXPath);
					returnResult = false;
				}
	    	}catch(Exception e){
				 log.error("\n@ " + username + " =>@@@@ Exception on " + strXPath);
				 returnResult = false;
			 }
			 
			 return returnResult;
	  }
	  
	 
	  public boolean enterXPathAndEnter(String strToEnter, String strXPath) throws Exception{ 
		  
			 WebElement webElement;
			 boolean returnResult;
			 int waitTimeSec;
			 
			 webElement = null;
			 returnResult = true;
			 waitTimeSec = 60;

			 try{
				webElement = waitUntilClickable(strXPath, waitTimeSec); 
				if (webElement != null) {
					webElement.sendKeys(Keys.CONTROL + "a"); 
					webElement.sendKeys(strToEnter);
					webElement.sendKeys(Keys.ENTER);
					log.info("\n@ " + username + " Entered=> " + strToEnter + " at " + strXPath);
				}else {
					log.error("\n@ " + username + "=>@@@@ Fail to Enter => " +  strToEnter + "since it is null => "+ strXPath);
					returnResult = false;
				}
	    	}catch(Exception e){
				 log.error("\n@ " + username + " =>@@@@ Exception on " + strXPath);
				 returnResult = false;
			 }
			 
			 return returnResult;
	  }
	  
	  
	  
	  public void clickImageByName(String imgName) throws Exception{
		  List<WebElement> allImages = driver.findElements(By.tagName("img"));
		  
		  try {
			  for(WebElement eachImage : allImages)
			  {	  
			      String tmpText = eachImage.getAttribute("src");
			      
			      if (tmpText.contains(imgName))
			      { 
			    	  log.info("\n@ " +  username+ "* image exist: " + imgName);
			    	  eachImage.click();
			    	  log.info("\n@ " +  username+ "* image clicked");
			      	  break;
			      }
			      
			  }
		  }catch(NullPointerException e){
			  log.info("NummPointerException is thrown at ClickImageByName function: " + e.toString());
		  }
	  }
	  
	  public void printAllImage3() throws Exception{
		  List<WebElement> allImages = driver.findElements(By.tagName("img"));
		  for(WebElement eachImage : allImages)
		  {	  
		      String tmp1 = eachImage.getAttribute("src");
		      String tmp2 = eachImage.getAttribute("alt");
		      System.out.println("Text of source: "+tmp1); //You may match for your required text, before printing/using it.
		      System.out.println("Text of source: "+tmp2);
		  }
	  }
	    
	  public void wait(int num) throws Exception{
		  System.out.println("@" + username + ": Wait : " + num + " seconds.");
		  Thread.sleep(num * 1000);
	  }
	  
	  //This shows how many times to wait.
	  public void wait(int num, int intNth) throws Exception{
		  System.out.println(intNth + ") @" + username + ": Wait : " + num + " seconds.");
		  Thread.sleep(num * 1000);
	  }
	  
	  public void wait(int num, String reason) throws Exception{
		  log.info("@" + username + ": " + reason +  " : " + num + " seconds.");
		  Thread.sleep(num * 1000);
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
		  log.info("######## @" + username + ": Wait " + num + " min until :"+cal.getTime());
		  
		  Thread.sleep(num * 60 * 1000);
	  }
	  
	  public void currentTime() throws Exception{
		  DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		  Date date = new Date();
		  String date1= dateFormat.format(date);
	  }
	  
	  public String currentTimeWithDash() throws Exception{
		  DateFormat dateFormat = new SimpleDateFormat("MMddHHmmss");
		  Date date = new Date();
		  String date1= dateFormat.format(date);
		  return date1;
	  }
	  

	  
	  
	  
	 public void setSizeAndLocation(int width, int height, int xLocation, int yLocation) throws Exception {
		 if (TestBaseObject.useWhichWebDriver.contains("phantomjs")) return;
		 
		 log.info("\n@ " +  username + " => Setting size to: W("+ width +"), H("+height+"), X("+xLocation+"), Y("+yLocation+")");
		 Dimension windowSize = new Dimension(width,height);
		 Point windowLocation = new Point(xLocation, yLocation);
		 
		 driver.manage().window().setSize(windowSize);
		 wait(2);
		 driver.manage().window().setPosition(windowLocation);
	 }
	 
	 public void setPointFireFox(int xLocation, int yLocation) {
		 if (TestBaseObject.useWhichWebDriver.contains("phantomjs")) return;
		 Point windowLocation = new Point(xLocation, yLocation);
		 driver.manage().window().setPosition(windowLocation);
	 }
	 

	 public void maximizeFireFox() throws Exception {
		 if (booleanMaximized == true) return;
		 //if (TestBaseObject.useWhichWebDriver.contains("phantomjs")) return;
		 log.info("\n@ " + username + " => Maximizing FireFox");
		 driver.manage().window().maximize();
		 booleanMaximized = true;
	 }

	 // This is not really minimize but imitate it.
	 public void minimizeFireFox() throws Exception {
		 if (TestBaseObject.useWhichWebDriver.contains("phantomjs")) return;
		 if (TestBaseObject.globalScenario.contains("Load_Email")) return;
		 log.info("\n@ " + username + " => minimizing FireFox");
		 setSizeAndLocation(10, 10, 10, 700);
		 booleanMaximized = false;

	 }
	 
	 
	 public void waitUntilPageTitle(String pageName) throws Exception{
		 log.info("Wait until the title:" + pageName);
		 WebDriverWait wait = new WebDriverWait(driver, 120); //you can play with the time integer  to wait for longer than 15 seconds.`
		 wait.until(ExpectedConditions.titleContains(pageName));
	 }
	 
	 public boolean waitUntilVisible(String strXPath, int intTimeOutSec) {
		 
		 boolean returnResult;
		 returnResult = true;
		 try{
			 WebDriverWait wait = new WebDriverWait(driver, intTimeOutSec); //you can play with the time integer  to wait for longer than 15 seconds.`
			 wait.ignoring(NoSuchElementException.class);
			 wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(strXPath)));
		 }catch(TimeoutException e){
			 log.error("\n@ " + username + " => Timeout happens");
			 returnResult = false;
		 }
		 
		 return returnResult;
	 }
	 
	 //Wait until xpath component is clickable.
	  public WebElement waitUntilClickable(String strXPath, int intTimeOutSec) throws Exception {

		 String xPath;
		 WebElement webElement;
		 xPath="Not_Initialized_Yet";
		 webElement =null;

		 xPath = AllEntities.gCCDXPathHash.get(strXPath);	 
		 try{
			webElement = driver.findElement(By.xpath(xPath));
			WebDriverWait wait = new WebDriverWait(driver, intTimeOutSec);
			wait.ignoring(NoSuchElementException.class);
			wait.ignoring(ElementNotVisibleException.class);
			wait.ignoring(StaleElementReferenceException.class);
			wait.until(ExpectedConditions.elementToBeClickable(webElement));
			log.info("XPath is : " + xPath);
			//wait.until(ExpectedConditions.visibilityOf(res));
		 }catch(TimeoutException e){
			 log.error("\n@ " + username + " => Timeout on waitUntilClickable");
			 webElement = null;
		 }catch(NoSuchElementException e){
			 log.error("\n@ " + username + " => NoSuchElement on waitUntilClickable");
			 webElement = null;
		 }catch(ElementNotVisibleException e){
			 log.error("\n@ " + username + " => ElementNotVisible on waitUntilClickable");
			 webElement = null;
		 }/*catch(InterruptedException e){
			 log.error("\n@ " + username + " => InterruptedException on waitUntilClickable");
			  webElement = null;
			  exceptionName = "InterruptedException";
		 }*/
		 /*catch(Exception e){
			 log.error("\n@ " + username + " => Exception on waitUntilClickable " + e.toString());
			 webElement = null;
		 }*/
		
		return webElement;
	 }
	  
	  
	  public WebElement conditonClickable_XPath(String strCondition, String strXPath, int secToWait) throws Exception{
		  
			//Boolean actionResult;	
			WebElement webElement;
			webElement = null;
			//actionResult = true;
			
/*		  	if (state.contains("weird")) {
		  		log.info("@" + username + ": The state is $$$$ weird $$$$. So skip this step");
		  		actionResult = false;
		  		return actionResult;
		  	}*/
			
			log.info("\n@ " +  username + " => " + strCondition);
			
			
			//waitUntilVisible(AllActors.hbWebAgentXPathHash.get("webAgentUserNameTxtBox"), 60);
			webElement= waitUntilClickable(strXPath, secToWait);
			if (webElement != null){
				log.info("\n@ " +  username + " => " + strCondition + ": Success");
				
			}else{
				log.error("\n@ " +  username + " => " + strCondition + ": Failed");
				maximizeFireFox();
				captureScreenshot(strCondition);
			}
			
			return webElement;
			

	  }
	  
	  
	  public boolean click_XPath(String strXPath) throws Exception {
		 WebElement webElement;
		 boolean returnResult;
		 int waitTimeSec;
		 int i;
		 i=0;
		 
		 webElement = null;
		 returnResult = true;
		 waitTimeSec = 60;

		 try{
			 
			while(true){
				webElement = waitUntilClickable(strXPath, waitTimeSec); 
				if (webElement != null) {
					///JavascriptExecutor executor = (JavascriptExecutor) driver;
					executor.executeScript("arguments[0].click();", webElement);
					//webElement.click();   //somehow this doesn't work.
					log.info("\n@ " + username + " Clicked=> " + strXPath);
					break;
				}else {
					log.info("\n@ " + username + "=>@@ Fail to Click since => " + strXPath + " is not ready");
					//log.info("@@ This means=> null, or no such element exist, so wait until it appears.");
				}
				i++;
				wait(3);
				if (i > 3) {
					returnResult = false;
					break;
				}
			}
		 }catch(ElementNotVisibleException e){
			 log.error("\n@ " + username + " =>@@ ElementNotVisibleException on " + " " + strXPath);
			 returnResult = false;
		 }catch(InterruptedException e){
			  log.error("@ " + username + " : @@ Thread inturrepted -> throw again on click_XPath()");
			  throw e;
		 }catch(SessionNotFoundException e){
			  log.error("@ " + username + " : @@ SessionNotFoundException -> throw again on click_XPath()");
			  throw e;
		 }catch(Exception e){
			 log.error("\n@ " + username + " =>@@@@ Exception on " + strXPath + " " +e.toString());
			 returnResult = false;
		 }
		
			return returnResult;
	}
	  
	  
	 
	 public void waitUntilMainTitle(String mainTitleSubText) throws Exception{
		 
		 	String strMainTitle= "";
		    for (int second = 0;; second++) {
		    	log.info("\n@ " + username + ":" + second +": Wait for ID: mainTitle");
		    	try { 
		    		if (driver.findElement(By.id("mainTitle")).isDisplayed()) 
		    			strMainTitle = driver.findElement(By.id("mainTitle")).getText();
		    		    log.info("\n@ " + username + ": MainTitleID text is  " + strMainTitle);
					    if (strMainTitle.contains(mainTitleSubText))
					    {
					    	//currentTime();
					    	break;	
					    }
		    	} catch (Exception e) {}
		    	
		    	wait(2); 
		    	if (second > 20) {
		    		Assert.assertTrue(false, "ID doesn't exist(Wrong!) => mainTitle");
		    	}
		    }
		    


		 
	 }

	 public void getCurrentDimenSion() {
		 if (dimensionChecked == true){ 
			 log.info("\n@ " + username + " => Skip getCurrentDimension");
			 return;
		 }
		 if (TestBaseObject.useWhichWebDriver.contains("phantomjs")) return;
		 //The following might not need for email load
		 if (TestBaseObject.globalScenario.contains("Load_Email")) return;
		 log.info("\n@ " + username + " => Collecting current dimension and point");
		 pt = driver.manage().window().getPosition();
		 dn = driver.manage().window().getSize();
		 dimensionChecked = true;
	}
	 
	 public void restoreDimenSion() throws Exception {
		 if (TestBaseObject.useWhichWebDriver.contains("phantomjs")) return;
		 //The following might not need for email load
		 if (TestBaseObject.globalScenario.contains("Load_Email")) return;
		 log.info("\n@ " + username + " => Restoring previous dimension and point");
		 driver.manage().window().setSize(dn);
		 driver.manage().window().setPosition(pt);
		 booleanMaximized = false;
		 dimensionChecked = false;
	}
	 
	 
	 public void selectFromComboBox(String xPath, String value) throws Exception {
		 
		 log.info("### updating combobox=> " + xPath + " with index " + value);
		 Select select=new Select(driver.findElement(By.xpath(xPath)));
		 //select.selectByVisibleText("DEFAULT COS");
		 select.selectByVisibleText(value);
		 //select.selectByIndex(index);

		 
	 }
	 
	 //xPath1 is root-menu, xPath2 is sub-menu
	 public void selectFromSubMenu(String xPath1, String xPath2) throws Exception {
		 
		 log.info("\n@ " + username + " => Selecting from rootMenu=> " + xPath1 + " with subMenu=>" + xPath2);
		 //Menu

		  WebElement rootMenu=driver.findElement(By.xpath(xPath1));
		  System.out.println(rootMenu.isDisplayed());
		 
		  Actions builder = new Actions(driver);
		  builder.moveToElement(rootMenu).build().perform();

		//sub Menu

		  WebElement subMenu=driver.findElement(By.xpath(xPath2));
		  System.out.println(subMenu.isDisplayed());
		   
		  builder.moveToElement(subMenu).build().perform();
		  subMenu.click();
		  //((JavascriptExecutor)driver).executeScript("document.getElementById('" + SubMenu + "').click();");

		 
	 }
	 
	 public void refreshPage(){
		 log.info("\n@ " + username + " => Refreshing a page. " );
		 driver.navigate().refresh();
	 }
	 
	 protected boolean isElementPresent(String strXPath) throws Exception {
		    log.info("\n@ " + username + " => checking if an element is present " );
		    driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		    boolean returnVal = true;
		    
		    while(true){
			    try{
			    	driver.findElement(By.xpath(strXPath));
			    	break;
			    } catch (NoSuchElementException e){
			    	log.info("NoSuchElementExceptio is catched");
			        returnVal = false;
			        wait(5);
			    } 
		    }
		    driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		    return returnVal;
		}
	 

	 
	 
	 

}
