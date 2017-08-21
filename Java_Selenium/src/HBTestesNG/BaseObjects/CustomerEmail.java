package HBTestesNG.BaseObjects;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.SessionNotFoundException;

import HBTestesNG.TestCases.Test_Initiate;

public class CustomerEmail extends Agent {

	
	public String domain;
    private String mainEmailWinID;
    private String newEmailWinID;

	public CustomerEmail() throws Exception{
		super();
		mainEmailWinID = driver.getWindowHandle();
		
		log.info("\n@(" + agentType + ") " +  username + "  Main Email Window ID: " + mainEmailWinID);
	}
	

	public void logIntoEmailClient() throws Exception {
		maximizeBrowser();
		log.info("\n@(" + agentType + ") " +  username + " #### Go to Email Login Page ####");
		driver.get(AllActors.iniEnv.get("URL", "owaURL")); 
		
		log.info("\n@(" + agentType + ") " +  username + " #### Entering customer username/password -> Submit ####");
		enterXPath(username, "EmailLoginUsername");
		wait(1);
		enterXPath(password, "EmailLoginPassword");
		wait(1);
		click_XPath("EmailLoginSubmitBtn");
		wait(1);
	}
	
	
	//To run this, start only 1 customer email and run this
	//this is to set setting when logging into for the first time.
	public void logIntoEmailClient_FirstTime() throws Exception {
		maximizeBrowser();
		String agentNamePrefix, agentNameDomain, agentUserName;
		String agentPassword;
		agentNamePrefix = "hbagent";
		agentNameDomain = "@qa.shoretel.com";
		agentPassword ="changeme";
		
		
		for(int i=4;i<51;i++)
		{
			driver.get(AllActors.iniEnv.get("URL", "emailURL"));
			
			agentUserName = agentNamePrefix+i+agentNameDomain;
			log.info("\n@ "+agentUserName + " #### Entering customer username/password -> Submit ####");
			
			enterXPath(agentUserName, AllActors.iniXPath.get("AIC", "EmailLoginUsername"));
			wait(1);
			enterXPath(agentPassword, AllActors.iniXPath.get("AIC", "EmailLoginPassword"));
			wait(1);
			click_XPath(("EmailBtnSignIn_qaxchange10"));
			wait(2);
			click_XPath(("EmailBtnOK_qaxchange10"));
			wait(2);
			click_XPath(("EmailBtnLogOff_qaxchange10"));
			wait(2);
		}

	}
	
	//To run this, start only 1 customer email and run this
	//this is to delete all emails in Inbox and logs out.
	public void logIntoEmailClient_DeleteAllEmails() throws Exception {
		
		maximizeBrowser();
		int i, agentStartNum, agentMaxNum, emailToNum;
		String emailTo;
		String agentNamePrefix, agentNameDomain, agentUserName;
		String agentPassword;
		String xPathEmailSubject;
		WebElement webElement;
		agentNamePrefix = "hbagent";
		agentNameDomain = "@qa.shoretel.com";
		agentPassword ="changeme";
		
		xPathEmailSubject = AllActors.iniXPath.get("AIC", "EmailInboxEmailSubject_qaxchange10");
		driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
		
		//Delete all emals from agents only used in the email scenario
		//agentStartNum = Integer.parseInt(AllActors.iniEnv.get("agentStartNum"));
		//agentMaxNum = Integer.parseInt(AllActors.iniEnv.get("agentMaxNum"));
		agentStartNum = 1;
		agentMaxNum = 29;
		
		for(i=agentStartNum;i<= agentMaxNum;i++)
		{
			//driver.get(AllActors.iniEnv.get("emailURL"));
			driver.get("https://qaxchange10.qa.shoretel.com/owa/auth/logon.aspx");
			//https://qaxchange13.qa.shoretel.com/owa/auth/logon.aspx
			agentUserName = agentNamePrefix+i+agentNameDomain;
			log.info("\n@ "+agentUserName + " #### Entering customer username/password -> Submit : " + agentUserName);
			
			enterXPath(agentUserName, "EmailLoginUsername");
			wait(1);
			enterXPath(agentPassword, "EmailLoginPassword");
			wait(1);
			click_XPath("EmailBtnSignIn_qaxchange10");
			wait(2);
			
			while(true){
				webElement = waitUntilClickable("EmailInboxEmailSubject_qaxchange10", 30);
				if (webElement == null) {
					break;
				}
				click_XPath("EmailDeleteBtn_qaxchange10");
				//wait(1);
			}
			click_XPath(("EmailBtnLogOff_qaxchange10"));
			wait(1);
		}
		
		
		emailTo = AllActors.iniEnv.get("Email", "emailTo");
		if (emailTo.contains("41")){
			emailToNum = 41;
		}else {emailToNum = 43;}
		
		
		//Starting 41,  this is corporate account
		for(i=emailToNum;i<emailToNum+2;i++)
		{
			//driver.get(AllActors.iniEnv.get("emailURL"));
			driver.get("https://qaxchange10.qa.shoretel.com/owa/auth/logon.aspx");
			https://qaxchange13.qa.shoretel.com/owa/auth/logon.aspx
			agentUserName = agentNamePrefix+i+agentNameDomain;
			log.info("\n@ "+agentUserName + " #### Entering customer username/password -> Submit : " + agentUserName);
			
			enterXPath(agentUserName, "EmailLoginUsername");
			wait(1);
			enterXPath(agentPassword, "EmailLoginPassword");
			wait(1);
			click_XPath("EmailBtnSignIn_qaxchange10");
			wait(2);
			
			while(true){
				webElement = waitUntilClickable("EmailInboxEmailSubject_qaxchange10", 30);
				if (webElement == null) {
					break;
				}
				click_XPath("EmailDeleteBtn_qaxchange10");
				//wait(1);
			}
			click_XPath(("EmailBtnLogOff_qaxchange10"));
			wait(1);
		}
		
		//Starting 50,  this is interflowed account
		for(i=50;i<51;i++)
		{
			//driver.get(AllActors.iniEnv.get("emailURL"));
			driver.get("https://qaxchange10.qa.shoretel.com/owa/auth/logon.aspx");
			https://qaxchange13.qa.shoretel.com/owa/auth/logon.aspx
			agentUserName = agentNamePrefix+i+agentNameDomain;
			log.info("\n@ "+agentUserName + " #### Entering customer username/password -> Submit : " + agentUserName);
			
			enterXPath(agentUserName, "EmailLoginUsername");
			wait(1);
			enterXPath(agentPassword, "EmailLoginPassword");
			wait(1);
			click_XPath("EmailBtnSignIn_qaxchange10");
			wait(2);
			
			while(true){
				webElement = waitUntilClickable("EmailInboxEmailSubject_qaxchange10", 30);
				if (webElement == null) {
					break;
				}
				click_XPath("EmailDeleteBtn_qaxchange10");
				//wait(1);
			}
			click_XPath(("EmailBtnLogOff_qaxchange10"));
			wait(1);
		}

	}
	
	public void signOutEmailClient() throws Exception {
		log.info("\n@(" + agentType + ") " +  username + " #### Signout Email Client ####");
		click_XPath(("EmailBtnUser"));
		wait(2);
		click_XPath(("EmailBtnSignOut"));
	}
	
	
	
	
	
	public void sendEmail() throws Exception{

		//I assume that the customers logged into qaxchange16.qa.shoretel.com
		log.info("\n@(" + agentType + ") " +  username + " #### Sending an email ####");
		String emailTo, emailSubject, emailContent;
		String emailToXPath, emailSubjectXPath, emailContentXPath;
		
		
		emailTo = AllActors.iniEnv.get("Email", "emailTo");
		emailSubject = AllActors.iniEnv.get("Email", "emailSubject");
		emailContent = AllActors.iniEnv.get("Email", "emailContent");
		emailToXPath = "EmailTo";
		emailSubjectXPath = "EmailSubject";
		emailContentXPath = "EmailContent";
		
		try{
			maximizeBrowser();
			click_XPath("EmailNewEmail");
			wait(1);
			
			//The following steps for Exchange 2007
			//collectEmailWindowHandles();
			//changeToNewEmailWindow();
			log.info("\n@(" + agentType + ") " +  username + " : Entering Email destionation=> " + emailTo);
			enterXPath(emailTo, emailToXPath);
			wait(1);
			log.info("\n@(" + agentType + ") " +  username + " : Entering Email Subject=> " + emailSubject);
			enterXPath(emailSubject, emailSubjectXPath);
			wait(1);
			//log.info("\n@(" + agentType + ") " +  username + " : Entering Email Content=> " + emailContent);
			//enterXPath(emailContent, emailContentXPath);
			//wait(1);
			log.info("\n@(" + agentType + ") " +  username + " : Pressing Send button ");
			click_XPath(("EmailBtnSend"));
			wait(1);
			minimizeBrowser();
			
			//This is needed to have multiple windows.
			//changeToMainEmailWindow();
			//minimizeBrowser();
		}catch(InterruptedException e){
			  log.error("@ " + username + " : @@ Thread inturrepted -> throw again on sendEmail()");
			  throw e;
		 }catch(SessionNotFoundException e){
			  log.error("@ " + username + " : @@ SessionNotFoundException -> throw again on sendEmail()");
			  throw e;
		 }catch(Exception e){
			 log.error("\n@(" + agentType + ") " + username + " =>@@@@ Exception on sendEmail() => " + e.toString());
			 throw e;
		 }

	}
		
	public void tearDownAll() throws Exception {
		try {
			//logIntoEmailClient_DeleteAllEmails();
		} catch (Exception e1) {
			log.info("@ " + username + " : Exception while cleaning emails");
			e1.printStackTrace();
		}
		log.info("\n@(" + agentType + ") " +  username + " #### Close FireFox ####");
	    driver.quit();
	    currentTime();	    
	}
	

	
	
	public void collectEmailWindowHandles() throws InterruptedException{
		
		log.info("\n@(" + agentType + ") " +  username + " #### Collect All Email Window IDs(Handles) ####");

        Set<String> windowId = driver.getWindowHandles();    // get  window id of current window
        Iterator<String> itererator = windowId.iterator();   

        newEmailWinID = itererator.next();  //This assigns the main window ID.
        newEmailWinID = itererator.next();  //This assigns the email window ID.
        log.info("\n@(" + agentType + ") " +  username + "  Main Email Window ID: " + mainEmailWinID);
        log.info("\n@(" + agentType + ") " +  username + " New Email Window ID: " + newEmailWinID);
        
        
	}
	
	public void changeToNewEmailWindow() throws InterruptedException{
		log.info("\n@(" + agentType + ") " +  username + " Switch to new email window: " + newEmailWinID);
        driver.switchTo().window(newEmailWinID);
        
	}
	
	public void changeToMainEmailWindow() throws InterruptedException{
		log.info("\n@(" + agentType + ") " +  username + " Switch to main email window: " + mainEmailWinID);
        driver.switchTo().window(mainEmailWinID);
        
	}
	
	public void genWindowHandle() throws InterruptedException{
		//ToDo: 
		WebDriver driver= new FirefoxDriver(); 
        driver.get("http://www.rediff.com/");
        WebElement sign = driver.findElement(By.xpath("//html/body/div[3]/div[3]/span[4]/span/a"));
        sign.click();

        Set<String> windowId = driver.getWindowHandles();    // get  window id of current window
        Iterator<String> itererator = windowId.iterator();   

        String mainEmailWinID = itererator.next();
        String  newEmailWinID = itererator.next();

        driver.switchTo().window(newEmailWinID);
        System.out.println(driver.getTitle());
        Thread.sleep(3000);
        driver.close();

        driver.switchTo().window(mainEmailWinID);
        System.out.println(driver.getTitle());
        Thread.sleep(2000);

        WebElement email_id= driver.findElement(By.xpath("//*[@id='c_uname']"));
        email_id.sendKeys("hi");
        Thread.sleep(5000);

        driver.close();
        driver.quit();
	}
	
	/*public void sendEmailbySMTP(){
		String server, port;
		
		Properties properties = System.getProperties();
		properties.put("mail.smtp.host", server);
		properties.put("mail.smtp.port", "" + port);
		
		Session session = Session.getInstance(properties);
		Transport transport = session.getTransport("smtp");
		
		transport.connect(server, username, password);
		
		for (int i = 0; i < count; i++) {
		
		    Message message = new MimeMessage(session);
		    message.setFrom(new InternetAddress(from));
		    InternetAddress[] address = {new InternetAddress(to)};
		    message.setRecipients(Message.RecipientType.TO, address);
		
		    message.setSubject(subject + "JavaMail API");
		    message.setSentDate(new Date());
		
		    setHTMLContent(message);
		    message.saveChanges();
		    transport.sendMessage(message, address);
		
		}
		
		transport.close();
	}*/
	
	  public void run(){
		  int waitSec, totalEmailToSend;
		  waitSec = globalSec;
		  
		  //totalEmailToSend = Integer.parseInt(Test_Initiate.AllActors.iniEnv.get("emailTotalToSend"));
		  totalEmailToSend = Integer.parseInt(AllActors.iniMain.get("LOAD", "emailTotalToSend"));
		  log.info("@" + username + ": Running => " +  threadName );
	      try {
		    	logIntoEmailClient();
		    	while (!this.isInterrupted()) 
				{
					sendEmail();
					globalNumOfEmailSent++;
					log.info("@" + username + ": globalNumOfEmailSent/totalEmalToSend => " +  globalNumOfEmailSent + " / " + totalEmailToSend);
					if (globalNumOfEmailSent > totalEmailToSend) {
						log.info("@" + username + ": I have reached the max, so stop sending");
						log.info("@" + username + ": globalNumOfEmailSent/totalEmalToSend => " +  globalNumOfEmailSent + " / " + totalEmailToSend);
						break;
					}
					wait(waitSec);
				}
		    	
			}catch(InterruptedException e){
				log.info("@ " + username + " : @@@@@@@@@ Thread inturrepted on run()-> start cleaning up emails. Bye! @@@@@@@@@@@");
			} catch (Exception e) {
				log.info("@" + username + ": Exception happens");
				e.printStackTrace();
			}
	      
		  
	  }
	
	
	
	
}
