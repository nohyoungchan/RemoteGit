package HBTestesNG.BaseObjects;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


public class AgentHBDirector extends Agent {

	
/*	public AgentHBDirector(String username, String password) throws Exception {
		super(username, password);
		log.info("\n@(supervisor)" + username+ " #### Initializing ####");
	}*/

	public AgentHBDirector() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	

	
	

	public void logIntoHBDirector() throws Exception {
		
		log.info("\n@(" + agentType + ") " +  username + " #### Go to Login Page ####");
		maximizeBrowser();
		driver.get(AllActors.envIni.get("URL", "hbDirectorURL")); 
		
		log.info("\n@(" + agentType + ") " +  username + " #### Adding username -> password -> Submit ####");
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys(username);
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys(password);
		wait(2);
		driver.findElement(By.id("submitBtn")).click();
		//currentTime();
	}
	
	
	public void openHBDirectorPage(String pageName) throws Exception{
		String pageURL;
		String part;
		
		part = "#page=";
		pageURL= AllActors.envIni.get("URL", "hbDirectorURLAfterLogIn") + part + pageName;
		
		log.info("\n@(" + agentType + ") " +  username + " opens HB Director " + pageURL);
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		driver.get(pageURL);
			
	}
	
	public void searchName(String nameToSearch) throws Exception{
		log.info("\n@(" + agentType + ") " +  username + " searching=> " + nameToSearch);
		driver.findElement(By.id("listFilterGlobal")).clear();
		driver.findElement(By.id("listFilterGlobal")).sendKeys(nameToSearch);	
	}
	
	
	public void clickComponentByID(String componentID, String componentName) throws Exception{
		log.info("\n@(" + agentType + ") " +  username + " clicks =>" + componentName);
		driver.findElement(By.id(componentID)).click();
	} 
	
	
	public void changeValueByID(String componentID, String componentName, String value) throws Exception{
		log.info("\n@(" + agentType + ") " +  username + " changes =>" + componentName + " To : " + value);
		driver.findElement(By.id(componentID)).clear();
		driver.findElement(By.id(componentID)).sendKeys(value);	
	} 
	  
	 public void tearDownAll() throws Exception {
		log.info("\n@(" + agentType + ") " +  username + " #### Close FireFox ####");
	    driver.quit();
	    currentTime();	    
	  }
	 
	 public void changeWrapupTime(String serviceName, String wuTime) throws Exception{
		 log.info("\n@(" + agentType + ") " +  username + " : change Wrapup time of " + serviceName);
		 maximizeBrowser();
		 logIntoHBDirector();
		 
		 wait(2);
		 openHBDirectorPage("service");
		 wait(2);
		 typeElementXPath(AllActors.hbDirectorXPathHash.get("searchBox"), serviceName);
		 clickXPath(AllActors.hbDirectorXPathHash.get("serviceGeneralTab")); 
		 typeElementXPath(AllActors.hbDirectorXPathHash.get("wrapupTime_TxtBox"), wuTime); 
		 clickXPath(AllActors.hbDirectorXPathHash.get("submitButton"));

		 logOutHBDirector();
		 minimizeBrowser();
	 }
	 
	 public void changeWrapAndForcedReleaseTime(String serviceName, String wuTime, String frTime) throws Exception{
		 log.info("\n@(" + agentType + ") " +  username + " : change Wrapup and Forced Release time of " + serviceName);
		 maximizeBrowser();
		 //logIntoHBDirector();
		 
		 wait(2);
		 openHBDirectorPage("service");
		 wait(2);
		 typeElementXPath(AllActors.hbDirectorXPathHash.get("searchBox"), serviceName);
		 clickXPath(AllActors.hbDirectorXPathHash.get("serviceGeneralTab")); 
		 typeElementXPath(AllActors.hbDirectorXPathHash.get("wrapupTime_TxtBox"), wuTime); 
		 typeElementXPath(AllActors.hbDirectorXPathHash.get("ForcedReleaseTimeout_TxtBox"), frTime);
		 clickXPath(AllActors.hbDirectorXPathHash.get("submitButton"));
		 
		 //logOutHBDirector();
		 minimizeBrowser();
	 }
	 
	 public void Max_LogIn_changeWrapAndFRTime_LogOut_Min(String serviceName, String wuTime, String frTime) throws Exception{
		 log.info("\n@(" + agentType + ") " +  username + " : Maximize->LogIn->change Wrapup and Forced Release time of "+ serviceName + "->Log out->Minmize ");
		 if (stopTest.contains("yes")) {
			log.info("@@ Stop is requested, so skip this function" );
			return ;
		 }
		 maximizeBrowser();
		 logIntoHBDirector();
		 waitUntilMainTitle("Favorites");
		 
		 wait(2);
		 openHBDirectorPage("service");
		 waitUntilMainTitle("Services");
		 wait(3);
		 
		 find_and_fill_byID("listFilterGlobal", serviceName, false);
		 dependableClick_byID("tab0_link");
		 find_and_fill_byID("wrap_up_time", wuTime, false);
		 find_and_fill_byID("forced_release_time", frTime, false);
		 dependableClick_byID("submitButton");

		 
		 logOutHBDirector();
		 minimizeBrowser();
	 }
	 
	 

	 // This should go with Max_LogIn_EnableAbandon_LogOut_Min
	 public void Max_LogIn_DisableAbandon_LogOut_Min(String serviceName) throws Exception{
		 log.info("\n@(" + agentType + ") " +  username + " : Maximize->LogIn->Enable Abandon Callback "+ serviceName + "->Log out->Minmize ");
		 if (stopTest.contains("yes")) {
			log.info("@@ Stop is requested, so skip this function" );
			return ;
		 }
		 Boolean enabled = true;
		 maximizeBrowser();
		 logIntoHBDirector();
		 waitUntilMainTitle("Favorites");
		 
		 wait(2);
		 openHBDirectorPage("service");
		 waitUntilMainTitle("Services");
		 wait(2);
		 find_and_fill_byID("listFilterGlobal", serviceName, false);
		 dependableClick_byID("tab7_link");  //Click abandon tab
		 
		 enabled = checkEnabledXPath(AllActors.hbDirectorXPathHash.get("minTimeBeforeAbandon_TxtBox"));
		 if(enabled){
			 clickXPath(AllActors.hbDirectorXPathHash.get("enableAbandon_CheckBox"));
			 clickXPath(AllActors.hbDirectorXPathHash.get("submitButton"));
			 log.info("\n@(" + agentType + ") " +  username + " : Disabled Abandon Callback ");
		 }else {
			 log.info("\n@(" + agentType + ") " +  username + " : Abandon Callback is already disabled. ");
		 }
			  
		 logOutHBDirector();
		 minimizeBrowser();
	 }
	 
	 private Boolean checkEnabledXPath(String stringXPath) {
		 log.info("\n@(" + agentType + ") " +  username + " : Check if the following is enabled ==> " + stringXPath);
		 //WebElement webElement;
		 Boolean enabled = true;
		 try{
			 log.info("I am here enabled  => " + enabled);
			 enabled = driver.findElement(By.xpath(stringXPath)).isEnabled();
			 log.info("\n@("+ agentType + ") " +  username + " : " + stringXPath  + "is enabled? => " + enabled);
		 }catch (Exception e){
			 log.error("@@ Exception happens on checkEnabledXPath ==> " + e.toString());
		 }
		 return enabled;
	}
	 
	// This goes with Max_LogIn_ChangeWrapFR_DisableAbandon_LogOut_Min
	public void Max_LogIn_ChangeWrapFR_EnableAbandon_SetOutboundPreferenceForAbandonLogOut_Min(String serviceName, String wuTime, String frTime, String minAbandonTime) throws Exception{
		 log.info("\n@(" + agentType + ") " +  username + " : Maximize->LogIn->Enable Abandon Callback "+ serviceName + "->Log out->Minmize ");
		 Boolean enabled = true;
		 maximizeBrowser();
		 logIntoHBDirector();
		 waitUntilMainTitle("Favorites");
		 
		 wait(2);
		 openHBDirectorPage("service");
		 waitUntilMainTitle("Services");
		 wait(2);

		 find_and_fill_byID("listFilterGlobal", serviceName, false);
		 dependableClick_byID("tab0_link");
		 find_and_fill_byID("wrap_up_time", wuTime, false);
		 find_and_fill_byID("forced_release_time", frTime, false);

		 dependableClick_byID("tab7_link");  //Click abandon tab
		 enabled = checkEnabledXPath(AllActors.hbDirectorXPathHash.get("minTimeBeforeAbandon_TxtBox"));
		 if(!enabled){ 
			 clickXPath(AllActors.hbDirectorXPathHash.get("enableAbandon_CheckBox"));
			 typeElementXPath(AllActors.hbDirectorXPathHash.get("minTimeBeforeAbandon_TxtBox"), minAbandonTime);
			 log.info("\n@(" + agentType + ") " +  username + " : Enabled Abandon Callback ");
		 }else {
			 log.info("\n@(" + agentType + ") " +  username + " : Abandon Callback is already enabled. ");
		 }
		 wait(5);
		 dependableClick_byID("submitButton");	  
		 wait(2);
		 //Set Outbound->General Preferences
		 openHBDirectorPage("sys_outbound");
		 waitUntilMainTitle("General Preferences");
		 //find_and_fill_byID("local_area_code", "408", false);
		 //find_and_fill_byID("outbound_prefix", "9", false);
		 selectFromComboBox_ByID("cb_def_srv_id", serviceName);
		 
		 wait(1);
		 dependableClick_byID("submitButton");	
		 
		 logOutHBDirector();
		 minimizeBrowser();
	 }
	
	 // This should go with Max_LogIn__ChangeWrapFR_EnableAbandon_LogOut_Min
	 public void Max_LogIn_ChangeWrapFR_DisableAbandon_LogOut_Min(String serviceName, String wuTime, String frTime) throws Exception{
		 log.info("\n@(" + agentType + ") " +  username + " : Maximize->LogIn->Enable Abandon Callback "+ serviceName + "->Log out->Minmize ");
		 Boolean enabled = true;
		 maximizeBrowser();
		 logIntoHBDirector();
		 waitUntilMainTitle("Favorites");
		 
		 wait(2);
		 openHBDirectorPage("service");
		 waitUntilMainTitle("Services");
		 
		 wait(2);
		 typeElementXPath(AllActors.hbDirectorXPathHash.get("searchBox"), serviceName);
		//Change wrapup and forced released time
		 clickXPath(AllActors.hbDirectorXPathHash.get("serviceGeneralTab")); 
		 typeElementXPath(AllActors.hbDirectorXPathHash.get("wrapupTime_TxtBox"), wuTime); 
		 typeElementXPath(AllActors.hbDirectorXPathHash.get("ForcedReleaseTimeout_TxtBox"), frTime);
		 
		 
		 clickXPath(AllActors.hbDirectorXPathHash.get("serviceAbandonTab")); 
		 
		 enabled = checkEnabledXPath(AllActors.hbDirectorXPathHash.get("minTimeBeforeAbandon_TxtBox"));
		 if(enabled){
			 clickXPath(AllActors.hbDirectorXPathHash.get("enableAbandon_CheckBox"));
			 log.info("\n@(" + agentType + ") " +  username + " : Disabled Abandon Callback ");
		 }else {
			 log.info("\n@(" + agentType + ") " +  username + " : Abandon Callback is already disabled. ");
		 }
			  
		 clickXPath(AllActors.hbDirectorXPathHash.get("submitButton"));
		 logOutHBDirector();
		 minimizeBrowser();
	 }

	 
	// This should go with Max_LogIn_DisableAbandon_LogOut_Min
	public void Max_LogIn_EnableAbandon_LogOut_Min(String serviceName, String minAbandonTime) throws Exception{
		 log.info("\n@(" + agentType + ") " +  username + " : Maximize->LogIn->Enable Abandon Callback "+ serviceName + "->Log out->Minmize ");
		 if (stopTest.contains("yes")) {
			log.info("@@ Stop is requested, so skip this function" );
			return ;
		 }
		 Boolean enabled = true;
		 maximizeBrowser();
		 logIntoHBDirector();
		 waitUntilMainTitle("Favorites");
		 
		 wait(2);
		 openHBDirectorPage("service");
		 waitUntilMainTitle("Services");
		 wait(2);
		 
		 find_and_fill_byID("listFilterGlobal", serviceName, false);
		 dependableClick_byID("tab7_link");  //Click abandon tab
		 //clickXPath(AllActors.hbDirectorXPathHash.get("serviceAbandonTab")); 
		 
		 enabled = checkEnabledXPath(AllActors.hbDirectorXPathHash.get("minTimeBeforeAbandon_TxtBox"));
		 if(!enabled){
			 
			 clickXPath(AllActors.hbDirectorXPathHash.get("enableAbandon_CheckBox"));
			 typeElementXPath(AllActors.hbDirectorXPathHash.get("minTimeBeforeAbandon_TxtBox"), minAbandonTime);
			 clickXPath(AllActors.hbDirectorXPathHash.get("submitButton"));
			 log.info("\n@(" + agentType + ") " +  username + " : Enabled Abandon Callback ");
		 }else {
			 log.info("\n@(" + agentType + ") " +  username + " : Abandon Callback is already enabled. ");
		 }
			  
		 logOutHBDirector();
		 minimizeBrowser();
	 }

	public void Max_LogIn_changeWrapAndFRTime_Min(String serviceName, String wuTime, String frTime) throws Exception{
		 log.info("\n@(" + agentType + ") " +  username + " : Maximize->LogIn->change Wrapup and Forced Release time of "+ serviceName + "->Log out->Minmize ");
		 maximizeBrowser();
		 logIntoHBDirector();
		 waitUntilMainTitle("Favorites");
		 
		 wait(2);
		 openHBDirectorPage("service");
		 waitUntilMainTitle("Services");
		 wait(2);
		 typeElementXPath(AllActors.hbDirectorXPathHash.get("searchBox"), serviceName);
		 //clickXPath(AllActors.hbDirectorXPathHash.get("serviceGeneralTab")); 
		 typeElementXPath(AllActors.hbDirectorXPathHash.get("wrapupTime_TxtBox"), wuTime); 
		 typeElementXPath(AllActors.hbDirectorXPathHash.get("ForcedReleaseTimeout_TxtBox"), frTime);
		 clickXPath(AllActors.hbDirectorXPathHash.get("submitButton"));
		 
		 //logOutHBDirector();
		 minimizeBrowser();
	 }
	 
	 public void AllType_Max_LogIn_changeWrapAndFRTime_Min(String serviceType, String serviceName, String wuTime, String frTime) throws Exception{
		 log.info("\n@(" + agentType + ") " +  username + " : Maximize->LogIn->change Wrapup and Forced Release time of "+ serviceName + "->Log out->Minmize ");
		 maximizeBrowser();
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
		 typeElementXPath(AllActors.hbDirectorXPathHash.get("searchBox"), serviceName);
		 clickXPath(AllActors.hbDirectorXPathHash.get("serviceGeneralTab")); 
		 typeElementXPath(AllActors.hbDirectorXPathHash.get("wrapupTime_TxtBox"), wuTime); 
		 typeElementXPath(AllActors.hbDirectorXPathHash.get("ForcedReleaseTimeout_TxtBox"), frTime);
		 clickXPath(AllActors.hbDirectorXPathHash.get("submitButton"));
		 
		 //logOutHBDirector();
		 minimizeBrowser();
	 }
	 
	 
	 public void AllType_Max_LogIn_changeWrapAndFRTime_LogOut_Min(String serviceType, String serviceName, String wuTime, String frTime) throws Exception{
		 log.info("\n@(" + agentType + ") " +  username + " : Maximize->LogIn->change Wrapup and Forced Release time of "+ serviceName + "->Log out->Minmize ");
		 maximizeBrowser();
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
		 
		 find_and_fill_byID("listFilterGlobal", serviceName, false);
		 dependableClick_byID("tab0_link");
		 find_and_fill_byID("wrap_up_time", wuTime, false);
		 find_and_fill_byID("forced_release_time", frTime, false);
		 dependableClick_byID("submitButton");
		 
		 
		 logOutHBDirector();
		 minimizeBrowser();
	 }
	 
	 
	 
	 
	 public void Max_changeWrapAndFRTime_LogOut_Min(String serviceName, String wuTime, String frTime) throws Exception{
		 log.info("\n@(" + agentType + ") " +  username + " : Maximize->LogIn->change Wrapup and Forced Release time of "+ serviceName + "->Log out->Minmize ");
		 maximizeBrowser();
		 //logIntoHBDirector();
		 
		 wait(2);
		 openHBDirectorPage("service");
		 waitUntilMainTitle("Services");
		 wait(2);
		 
		 find_and_fill_byID("listFilterGlobal", serviceName, false);
		 dependableClick_byID("tab0_link");
		 find_and_fill_byID("wrap_up_time", wuTime, false);
		 find_and_fill_byID("forced_release_time", frTime, false);
		 dependableClick_byID("submitButton");
		 
		 
		 logOutHBDirector();
		 minimizeBrowser();
	 }
	 
	 public void AllType_Max_changeWrapAndFRTime_LogOut_Min(String serviceType, String serviceName, String wuTime, String frTime) throws Exception{
		 log.info("\n@(" + agentType + ") " +  username + " : Maximize->LogIn->change Wrapup and Forced Release time of "+ serviceName + "->Log out->Minmize ");
		 maximizeBrowser();
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
		 typeElementXPath(AllActors.hbDirectorXPathHash.get("searchBox"), serviceName);
		 clickXPath(AllActors.hbDirectorXPathHash.get("serviceGeneralTab")); 
		 typeElementXPath(AllActors.hbDirectorXPathHash.get("wrapupTime_TxtBox"), wuTime); 
		 typeElementXPath(AllActors.hbDirectorXPathHash.get("ForcedReleaseTimeout_TxtBox"), frTime);
		 clickXPath(AllActors.hbDirectorXPathHash.get("submitButton"));
		 
		 logOutHBDirector();
		 minimizeBrowser();
	 }
	 
	 
	 
	 public void logOutHBDirector() throws Exception
	 {
		 log.info("\n@(Super)" + username + ": Log out HB-Director");
		 maximizeBrowser();
		 wait(2);
		 clickXPath(AllActors.hbDirectorXPathHash.get("logoutLink"));
		 wait(2);
		 minimizeBrowser();
	 }
	 
	public void createOneAgent(String agentName, String agentUserName, String agentID, String agentExtension, String agentEmail) {	
		log.info("\n@(" + agentType + ") " +  username + " :Creating agent=>  "+ agentUserName );
		String agentCOS;
		agentCOS = "LOAD_COS";
		try{
			 clickXPath(AllActors.hbDirectorXPathHash.get("agentNewBtn"));
			 typeElementXPath(AllActors.hbDirectorXPathHash.get("agentNameTxtBox"), agentName);
			 typeElementXPath(AllActors.hbDirectorXPathHash.get("agentUserNameTxtBox"), agentUserName); 
			 typeElementXPath(AllActors.hbDirectorXPathHash.get("agentIDTxtBox"), agentID);
			 typeElementXPath(AllActors.hbDirectorXPathHash.get("agentExtenstionTxtBox"), agentExtension);
			 selectFromComboBox(AllActors.hbDirectorXPathHash.get("agentCOS"), agentCOS);
			 typeElementXPath(AllActors.hbDirectorXPathHash.get("agentCorporateEmailAddress"), agentEmail);
			 clickXPath(AllActors.hbDirectorXPathHash.get("agentCreateBtn"));
			 wait(2);
		}catch(Exception e){
			log.info("\n@(" + agentType + ") " +  username + " fail to create an agent=> "+ agentName);
		}
	}
	 
	// This prepares HB Director for the whole test.
		public void Max_LogIn_PrepareTest_LogOut_Min() throws Exception{
			 log.info("\n@(" + agentType + ") " +  username + " : PrepareTest ");
			 String wuTime, frTime, overflowTime, interflowTime;
			 String serviceName, eServiceName, chatIRN, emailIRN;
			 Boolean enabled;
			 
			 wuTime = "20";
			 frTime = "30";
			 overflowTime = "60";
			 interflowTime = "120";
			 enabled = true;
			 serviceName = AllActors.services.get(0).name;
			 eServiceName = AllActors.services.get(3).name;
			 chatIRN = "5555";
			 emailIRN = "Email IRN 1"; //ToDo
			 
			 maximizeBrowser();
			 logIntoHBDirector();
			 waitUntilMainTitle("Favorites");
			 
			 wait(2);
			 openHBDirectorPage("service");
			 waitUntilMainTitle("Services");
			 wait(2);

			 //#### Set wrapup/Forced release time to 20/30 ####
			 find_and_fill_byID("listFilterGlobal", serviceName, false);
			 dependableClick_byID("tab0_link");
			 find_and_fill_byID("wrap_up_time", wuTime, false);
			 find_and_fill_byID("forced_release_time", frTime, false);

			 //#### Overflow setting ####
			 dependableClick_byID("tab3_link"); 
			 find_and_fill_byID("overflow_destination_of_interval_value", overflowTime, false);
			 wait(2);
			 
			 //#### Interflow setting ####
			 dependableClick_byID("tab4_link"); 
			 find_and_fill_byID("srv_abs_intflw_timeout", interflowTime, false);
			 
			 //#### Disable Abandon ####
			 dependableClick_byID("tab7_link");  //Click abandon tab
			 enabled = checkEnabledXPath(AllActors.hbDirectorXPathHash.get("minTimeBeforeAbandon_TxtBox"));
			 if(enabled){
				 clickXPath(AllActors.hbDirectorXPathHash.get("enableAbandon_CheckBox"));
				 log.info("\n@(" + agentType + ") " +  username + " : Disabled Abandon Callback ");
			 }else {
				 log.info("\n@(" + agentType + ") " +  username + " : Abandon Callback is already disabled. ");
			 }
			 wait(3);
			 dependableClick_byID("submitButton");	  
			 wait(3);
			 
			 //#### Set destination for Chat
			 openHBDirectorPage("irn");
			 waitUntilMainTitle("IRN");
			 find_and_fill_byID("listFilterGlobal", chatIRN, false);
			 dependableClick_byID("tab4_link"); 
			//this is needed to enable service button
			 selectFromComboBox_ByID("dest_type", "Script"); //this is needed to 
			 selectFromComboBox_ByID("dest_type", "Service");
			 selectFromComboBox_ByID("srv_dest", serviceName);
			 wait(3);
			 dependableClick_byID("submitButton");
			 wait(3);
			 
			 //#### Set destination for email
			 /* TODO
			 openHBDirectorPage("email_irn");
			 waitUntilMainTitle("Email IRN");
			 find_and_fill_byID("listFilterGlobal", emailIRN, false);
			 dependableClick_byID("tab4_link"); 
			 //this is needed to enable service button
			 selectFromComboBox_ByID("dest_type", "Script"); //this is needed to 
			 selectFromComboBox_ByID("dest_type", "Service");
			 selectFromComboBox_ByID("srv_dest", eServiceName);
			 wait(3);
			 dependableClick_byID("submitButton");
			 wait(3);
			 
			 //#### Set Outbound->General Preferences
			 openHBDirectorPage("sys_outbound");
			 waitUntilMainTitle("General Preferences");
			 find_and_fill_byID("local_area_code", "408", false);
			 find_and_fill_byID("outbound_prefix", "9", false);
			 selectFromComboBox_ByID("cb_def_srv_id", serviceName);
			 wait(3);
			 dependableClick_byID("submitButton");	
			 wait(3);
			 */
			 
			 logOutHBDirector();
			 minimizeBrowser();
		 }
	 
	
}
