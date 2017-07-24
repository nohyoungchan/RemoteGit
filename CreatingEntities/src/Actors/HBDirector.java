package Actors;

import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

import org.ini4j.Wini;
import org.openqa.selenium.By;


public class HBDirector extends Agent{
	
	 private Hashtable<String, String> gVariableHash;
	 private Hashtable<String, String> gCCDXPathHash;
	 public Wini inic;
	 private String supUserName, supPassword;
	

	public HBDirector() throws Exception {
		super();
		gVariableHash = AllEntities.gVariableHash;
		gCCDXPathHash = AllEntities.gCCDXPathHash;
		inic= AllEntities.wini;
		supUserName = inic.get("Supervisor", "supUserName");
		supPassword = inic.get("Supervisor", "supPassword");
		//username = supUserName;
		log.info("supUserName ==> " + supUserName);

		// TODO Auto-generated constructor stub
	}
	

	
	

	public void logIntoHBDirector() throws Exception {
		
		log.info("\n@ " +  supUserName + " #### Go to Login Page ####");
		maximizeFireFox();
		driver.get(inic.get("URL", "hbDirectorURL")); 
		
		log.info("\n@ " +  supUserName + " #### Adding supUserName -> password -> Submit ####");
		// ## This is for MT ##
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys(supUserName);
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys(supPassword);
		wait(2);
		driver.findElement(By.id("submitBtn")).click();
		//currentTime();

	}
	
	
	public void openHBDirectorPage(String pageName) throws Exception{
		String pageURL;
		String part;
		
		part = "#page=";
		pageURL= inic.get("URL", "hbDirectorURLAfterLogIn") + part + pageName;
		
		log.info("\n@ " +  supUserName + " opens HB Director " + pageURL);
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		driver.get(pageURL);
			
	}
	
	public void searchName(String nameToSearch) throws Exception{
		log.info("\n@ " +  supUserName + " searching=> " + nameToSearch);
		driver.findElement(By.id("listFilterGlobal")).clear();
		driver.findElement(By.id("listFilterGlobal")).sendKeys(nameToSearch);	
	}
	
	
	public void clickComponentByID(String componentID, String componentName) throws Exception{
		log.info("\n@ " +  supUserName + " clicks =>" + componentName);
		driver.findElement(By.id(componentID)).click();
	} 
	
	
	public void changeValueByID(String componentID, String componentName, String value) throws Exception{
		log.info("\n@ " +  supUserName + " changes =>" + componentName + " To : " + value);
		driver.findElement(By.id(componentID)).clear();
		driver.findElement(By.id(componentID)).sendKeys(value);	
	} 
	  
	 public void tearDownAll() throws Exception {
		log.info("\n@ " +  supUserName + " #### Close FireFox ####");
	    driver.quit();
	    currentTime();	    
	  }
	 
	 public void changeWrapupTime(String serviceName, String wuTime) throws Exception{
		 log.info("\n@ " +  supUserName + " : change Wrapup time of " + serviceName);
		 maximizeFireFox();
		 logIntoHBDirector();
		 
		 wait(2);
		 openHBDirectorPage("service");
		 wait(2);
		 typeElementXPath(gCCDXPathHash.get("searchBox"), serviceName);
		 clickXPath(gCCDXPathHash.get("serviceGeneralTab")); 
		 typeElementXPath(gCCDXPathHash.get("wrapupTime_TxtBox"), wuTime); 
		 clickXPath(gCCDXPathHash.get("submitButton"));

		 logOutHBDirector();
		 minimizeFireFox();
	 }
	 
	 public void changeWrapAndForcedReleaseTime(String serviceName, String wuTime, String frTime) throws Exception{
		 log.info("\n@ " +  supUserName + " : change Wrapup and Forced Release time of " + serviceName);
		 maximizeFireFox();
		 //logIntoHBDirector();
		 
		 wait(2);
		 openHBDirectorPage("service");
		 wait(2);
		 typeElementXPath(gCCDXPathHash.get("searchBox"), serviceName);
		 clickXPath(gCCDXPathHash.get("serviceGeneralTab")); 
		 typeElementXPath(gCCDXPathHash.get("wrapupTime_TxtBox"), wuTime); 
		 typeElementXPath(gCCDXPathHash.get("ForcedReleaseTimeout_TxtBox"), frTime);
		 clickXPath(gCCDXPathHash.get("submitButton"));
		 
		 //logOutHBDirector();
		 minimizeFireFox();
	 }
	 
	 public void Max_LogIn_changeWrapAndFRTime_LogOut_Min(String serviceName, String wuTime, String frTime) throws Exception{
		 log.info("\n@ " +  supUserName + " : Maximize->LogIn->change Wrapup and Forced Release time of "+ serviceName + "->Log out->Minmize ");
		 maximizeFireFox();
		 logIntoHBDirector();
		 waitUntilMainTitle("Favorites");
		 
		 wait(2);
		 openHBDirectorPage("service");
		 waitUntilMainTitle("Services");
		 wait(2);
		 typeElementXPath(gCCDXPathHash.get("searchBox"), serviceName);
		 clickXPath(gCCDXPathHash.get("serviceGeneralTab")); 
		 typeElementXPath(gCCDXPathHash.get("wrapupTime_TxtBox"), wuTime); 
		 typeElementXPath(gCCDXPathHash.get("ForcedReleaseTimeout_TxtBox"), frTime);
		 clickXPath(gCCDXPathHash.get("submitButton"));
		 
		 logOutHBDirector();
		 minimizeFireFox();
	 }
	 
	 

	 // This should go with Max_LogIn_EnableAbandon_LogOut_Min
	 public void Max_LogIn_DisableAbandon_LogOut_Min(String serviceName) throws Exception{
		 log.info("\n@ " +  supUserName + " : Maximize->LogIn->Enable Abandon Callback "+ serviceName + "->Log out->Minmize ");
		 Boolean enabled = true;
		 maximizeFireFox();
		 logIntoHBDirector();
		 waitUntilMainTitle("Favorites");
		 
		 wait(2);
		 openHBDirectorPage("service");
		 waitUntilMainTitle("Services");
		 wait(2);
		 typeElementXPath(gCCDXPathHash.get("searchBox"), serviceName);
		 clickXPath(gCCDXPathHash.get("serviceAbandonTab")); 
		 
		 enabled = checkEnabledXPath(gCCDXPathHash.get("minTimeBeforeAbandon_TxtBox"));
		 if(enabled){
			 clickXPath(gCCDXPathHash.get("enableAbandon_CheckBox"));
			 clickXPath(gCCDXPathHash.get("submitButton"));
			 log.info("\n@ " +  supUserName + " : Disabled Abandon Callback ");
		 }else {
			 log.info("\n@ " +  supUserName + " : Abandon Callback is already disabled. ");
		 }
			  
		 logOutHBDirector();
		 minimizeFireFox();
	 }
	 
	 private Boolean checkEnabledXPath(String string) {
		 log.info("\n@ " +  supUserName + " : Check if the following is enabled  " + string);
		 Boolean enabled = true;
		 enabled = driver.findElement(By.xpath(string)).isEnabled(); 
		 return enabled;
	}
	 
	// This goes with Max_LogIn_ChangeWrapFR_DisableAbandon_LogOut_Min
	public void Max_LogIn_ChangeWrapFR_EnableAbandon_LogOut_Min(String serviceName, String wuTime, String frTime, String minAbandonTime) throws Exception{
		 log.info("\n@ " +  supUserName + " : Maximize->LogIn->Enable Abandon Callback "+ serviceName + "->Log out->Minmize ");
		 Boolean enabled = true;
		 maximizeFireFox();
		 logIntoHBDirector();
		 waitUntilMainTitle("Favorites");
		 
		 wait(2);
		 openHBDirectorPage("service");
		 waitUntilMainTitle("Services");
		 wait(2);
		 typeElementXPath(gCCDXPathHash.get("searchBox"), serviceName);
		 //Change wrapup and forced released time
		 clickXPath(gCCDXPathHash.get("serviceGeneralTab")); 
		 typeElementXPath(gCCDXPathHash.get("wrapupTime_TxtBox"), wuTime); 
		 typeElementXPath(gCCDXPathHash.get("ForcedReleaseTimeout_TxtBox"), frTime);
		 
		 //Enable Abandon and enter abandon time
		 clickXPath(gCCDXPathHash.get("serviceAbandonTab")); 
		 enabled = checkEnabledXPath(gCCDXPathHash.get("minTimeBeforeAbandon_TxtBox"));
		 if(!enabled){
			 
			 clickXPath(gCCDXPathHash.get("enableAbandon_CheckBox"));
			 typeElementXPath(gCCDXPathHash.get("minTimeBeforeAbandon_TxtBox"), minAbandonTime);
			 log.info("\n@ " +  supUserName + " : Enabled Abandon Callback ");
		 }else {
			 log.info("\n@ " +  supUserName + " : Abandon Callback is already enabled. ");
		 }
		 clickXPath(gCCDXPathHash.get("submitButton"));	  
		 logOutHBDirector();
		 minimizeFireFox();
	 }
	
	 // This should go with Max_LogIn__ChangeWrapFR_EnableAbandon_LogOut_Min
	 public void Max_LogIn_ChangeWrapFR_DisableAbandon_LogOut_Min(String serviceName, String wuTime, String frTime) throws Exception{
		 log.info("\n@ " +  supUserName + " : Maximize->LogIn->Enable Abandon Callback "+ serviceName + "->Log out->Minmize ");
		 Boolean enabled = true;
		 maximizeFireFox();
		 logIntoHBDirector();
		 waitUntilMainTitle("Favorites");
		 
		 wait(2);
		 openHBDirectorPage("service");
		 waitUntilMainTitle("Services");
		 wait(2);
		 typeElementXPath(gCCDXPathHash.get("searchBox"), serviceName);
		//Change wrapup and forced released time
		 clickXPath(gCCDXPathHash.get("serviceGeneralTab")); 
		 typeElementXPath(gCCDXPathHash.get("wrapupTime_TxtBox"), wuTime); 
		 typeElementXPath(gCCDXPathHash.get("ForcedReleaseTimeout_TxtBox"), frTime);
		 
		 
		 clickXPath(gCCDXPathHash.get("serviceAbandonTab")); 
		 
		 enabled = checkEnabledXPath(gCCDXPathHash.get("minTimeBeforeAbandon_TxtBox"));
		 if(enabled){
			 clickXPath(gCCDXPathHash.get("enableAbandon_CheckBox"));
			 log.info("\n@ " +  supUserName + " : Disabled Abandon Callback ");
		 }else {
			 log.info("\n@ " +  supUserName + " : Abandon Callback is already disabled. ");
		 }
			  
		 clickXPath(gCCDXPathHash.get("submitButton"));
		 logOutHBDirector();
		 minimizeFireFox();
	 }

	 
	// This should go with Max_LogIn_DisableAbandon_LogOut_Min
	public void Max_LogIn_EnableAbandon_LogOut_Min(String serviceName, String minAbandonTime) throws Exception{
		 log.info("\n@ " +  supUserName + " : Maximize->LogIn->Enable Abandon Callback "+ serviceName + "->Log out->Minmize ");
		 Boolean enabled = true;
		 maximizeFireFox();
		 logIntoHBDirector();
		 waitUntilMainTitle("Favorites");
		 
		 wait(2);
		 openHBDirectorPage("service");
		 waitUntilMainTitle("Services");
		 wait(2);
		 typeElementXPath(gCCDXPathHash.get("searchBox"), serviceName);
		 clickXPath(gCCDXPathHash.get("serviceAbandonTab")); 
		 
		 enabled = checkEnabledXPath(gCCDXPathHash.get("minTimeBeforeAbandon_TxtBox"));
		 if(!enabled){
			 
			 clickXPath(gCCDXPathHash.get("enableAbandon_CheckBox"));
			 typeElementXPath(gCCDXPathHash.get("minTimeBeforeAbandon_TxtBox"), minAbandonTime);
			 clickXPath(gCCDXPathHash.get("submitButton"));
			 log.info("\n@ " +  supUserName + " : Enabled Abandon Callback ");
		 }else {
			 log.info("\n@ " +  supUserName + " : Abandon Callback is already enabled. ");
		 }
			  
		 logOutHBDirector();
		 minimizeFireFox();
	 }

	public void Max_LogIn_changeWrapAndFRTime_Min(String serviceName, String wuTime, String frTime) throws Exception{
		 log.info("\n@ " +  supUserName + " : Maximize->LogIn->change Wrapup and Forced Release time of "+ serviceName + "->Log out->Minmize ");
		 maximizeFireFox();
		 logIntoHBDirector();
		 waitUntilMainTitle("Favorites");
		 
		 wait(2);
		 openHBDirectorPage("service");
		 waitUntilMainTitle("Services");
		 wait(2);
		 typeElementXPath(gCCDXPathHash.get("searchBox"), serviceName);
		 //clickXPath(gCCDXPathHash.get("serviceGeneralTab")); 
		 typeElementXPath(gCCDXPathHash.get("wrapupTime_TxtBox"), wuTime); 
		 typeElementXPath(gCCDXPathHash.get("ForcedReleaseTimeout_TxtBox"), frTime);
		 clickXPath(gCCDXPathHash.get("submitButton"));
		 
		 //logOutHBDirector();
		 minimizeFireFox();
	 }
	 
	 public void AllType_Max_LogIn_changeWrapAndFRTime_Min(String serviceType, String serviceName, String wuTime, String frTime) throws Exception{
		 log.info("\n@ " +  supUserName + " : Maximize->LogIn->change Wrapup and Forced Release time of "+ serviceName + "->Log out->Minmize ");
		 maximizeFireFox();
		 logIntoHBDirector();
		 waitUntilMainTitle("Favorites");
		 
		 wait(2);
		 if (serviceType.contains("email")){
			 openHBDirectorPage("email_service");
			 waitUntilMainTitle("Email Services");
		 }
		 else {
			 openHBDirectorPage("service");
		 	 waitUntilMainTitle("Services");
		 }
		 
		 wait(2);
		 typeElementXPath(gCCDXPathHash.get("searchBox"), serviceName);
		 clickXPath(gCCDXPathHash.get("serviceGeneralTab")); 
		 typeElementXPath(gCCDXPathHash.get("wrapupTime_TxtBox"), wuTime); 
		 typeElementXPath(gCCDXPathHash.get("ForcedReleaseTimeout_TxtBox"), frTime);
		 clickXPath(gCCDXPathHash.get("submitButton"));
		 
		 //logOutHBDirector();
		 minimizeFireFox();
	 }
	 
	 
	 public void AllType_Max_LogIn_changeWrapAndFRTime_LogOut_Min(String serviceType, String serviceName, String wuTime, String frTime) throws Exception{
		 log.info("\n@ " +  supUserName + " : Maximize->LogIn->change Wrapup and Forced Release time of "+ serviceName + "->Log out->Minmize ");
		 maximizeFireFox();
		 logIntoHBDirector();
		 waitUntilMainTitle("Favorites");
		 
		 wait(2);
		 if (serviceType.contains("email")){
			 openHBDirectorPage("email_service");
			 waitUntilMainTitle("Email Services");
		 }
		 else {
			 openHBDirectorPage("service");
		 	 waitUntilMainTitle("Services");
		 }
		 
		 wait(2);
		 typeElementXPath(gCCDXPathHash.get("searchBox"), serviceName);
		 clickXPath(gCCDXPathHash.get("serviceGeneralTab")); 
		 typeElementXPath(gCCDXPathHash.get("wrapupTime_TxtBox"), wuTime); 
		 typeElementXPath(gCCDXPathHash.get("ForcedReleaseTimeout_TxtBox"), frTime);
		 clickXPath(gCCDXPathHash.get("submitButton"));
		 
		 logOutHBDirector();
		 minimizeFireFox();
	 }
	 
	 
	 
	 
	 public void Max_changeWrapAndFRTime_LogOut_Min(String serviceName, String wuTime, String frTime) throws Exception{
		 log.info("\n@ " +  supUserName + " : Maximize->LogIn->change Wrapup and Forced Release time of "+ serviceName + "->Log out->Minmize ");
		 maximizeFireFox();
		 //logIntoHBDirector();
		 
		 wait(2);
		 openHBDirectorPage("service");
		 waitUntilMainTitle("Services");
		 wait(2);
		 typeElementXPath(gCCDXPathHash.get("searchBox"), serviceName);
		 clickXPath(gCCDXPathHash.get("serviceGeneralTab")); 
		 typeElementXPath(gCCDXPathHash.get("wrapupTime_TxtBox"), wuTime); 
		 typeElementXPath(gCCDXPathHash.get("ForcedReleaseTimeout_TxtBox"), frTime);
		 clickXPath(gCCDXPathHash.get("submitButton"));
		 
		 logOutHBDirector();
		 minimizeFireFox();
	 }
	 
	 public void AllType_Max_changeWrapAndFRTime_LogOut_Min(String serviceType, String serviceName, String wuTime, String frTime) throws Exception{
		 log.info("\n@ " +  supUserName + " : Maximize->LogIn->change Wrapup and Forced Release time of "+ serviceName + "->Log out->Minmize ");
		 maximizeFireFox();
		 //logIntoHBDirector();
		 
		 wait(2);
		 if (serviceType.contains("email")){
			 openHBDirectorPage("email_service");
			 waitUntilMainTitle("email Services");
		 }
		 else {
			 openHBDirectorPage("service");
		 	 waitUntilMainTitle("Services");
		 }
		 
		 wait(2);
		 typeElementXPath(gCCDXPathHash.get("searchBox"), serviceName);
		 clickXPath(gCCDXPathHash.get("serviceGeneralTab")); 
		 typeElementXPath(gCCDXPathHash.get("wrapupTime_TxtBox"), wuTime); 
		 typeElementXPath(gCCDXPathHash.get("ForcedReleaseTimeout_TxtBox"), frTime);
		 clickXPath(gCCDXPathHash.get("submitButton"));
		 
		 logOutHBDirector();
		 minimizeFireFox();
	 }
	 
	 
	 
	 public void logOutHBDirector() throws Exception
	 {
		 log.info("\n@(Super)" + supUserName + ": Log out HB-Director");
		 maximizeFireFox();
		 wait(2);
		 clickXPath(gCCDXPathHash.get("logoutLink"));
		 wait(2);
		 minimizeFireFox();
	 }
	 
	public void createOneAgent(String agentName, String agentUserName, String agentID, String agentExtension, String agentEmail) {	
		log.info("\n@ " +  supUserName + " :Creating agent=>  "+ agentUserName );
		String agentCOS;
		agentCOS = "LOAD_COS";
		try{
			 clickXPath(gCCDXPathHash.get("agentNewBtn"));
			 typeElementXPath(gCCDXPathHash.get("agentNameTxtBox"), agentName);
			 typeElementXPath(gCCDXPathHash.get("agentUserNameTxtBox"), agentUserName); 
			 typeElementXPath(gCCDXPathHash.get("agentIDTxtBox"), agentID);
			 typeElementXPath(gCCDXPathHash.get("agentExtenstionTxtBox"), agentExtension);
			 selectFromComboBox(gCCDXPathHash.get("agentCOS"), agentCOS);
			 typeElementXPath(gCCDXPathHash.get("agentCorporateEmailAddress"), agentEmail);
			 clickXPath(gCCDXPathHash.get("agentCreateBtn"));
			 wait(2);
		}catch(Exception e){
			log.info("\n@ " +  supUserName + " fail to create an agent=> "+ agentName);
		}
	}
	 
	 
	
}
