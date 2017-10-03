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
		driver.get(AllActors.iniEnv.get("URL", "hbDirectorURL")); 
		
		log.info("\n@(" + agentType + ") " +  username + " #### Adding username -> password -> Submit ####");
		driver.findElement(By.id("username")).clear();
		wait(1);
		driver.findElement(By.id("username")).sendKeys(username);
		wait(1);
		driver.findElement(By.id("password")).clear();
		wait(1);
		driver.findElement(By.id("password")).sendKeys(password);
		wait(2);
		driver.findElement(By.id("submitBtn")).click();
		//currentTime();
	}
	
	
	public void openHBDirectorPage(String pageName) throws Exception{
		String pageURL;
		String part;
		
		part = "#page=";
		pageURL= AllActors.iniEnv.get("URL", "hbDirectorURLAfterLogIn") + part + pageName;
		
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
		 typeElementXPath(AllActors.iniXPath.get("CCD", "searchBox"), serviceName);
		 clickXPath(AllActors.iniXPath.get("CCD", "serviceGeneralTab")); 
		 typeElementXPath(AllActors.iniXPath.get("CCD", "wrapupTime_TxtBox"), wuTime); 
		 clickXPath(AllActors.iniXPath.get("CCD", "submitButton"));
		 //Wait until new button is enabled which means save is completed.
		 dependableWait_byID("form_action_new", 5);

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
		 typeElementXPath(AllActors.iniXPath.get("CCD", "searchBox"), serviceName);
		 clickXPath(AllActors.iniXPath.get("CCD", "serviceGeneralTab")); 
		 typeElementXPath(AllActors.iniXPath.get("CCD", "wrapupTime_TxtBox"), wuTime); 
		 typeElementXPath(AllActors.iniXPath.get("CCD", "ForcedReleaseTimeout_TxtBox"), frTime);
		 clickXPath(AllActors.iniXPath.get("CCD", "submitButton"));
		 //Wait until new button is enabled which means save is completed.
		 dependableWait_byID("form_action_new", 5);
		 
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
		 //Wait until new button is enabled which means save is completed.
		 dependableWait_byID("form_action_new", 5);

		 
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
		 
		 enabled = checkEnabledXPath(AllActors.iniXPath.get("CCD", "minTimeBeforeAbandon_TxtBox"));
		 if(enabled){
			 clickXPath(AllActors.iniXPath.get("CCD", "enableAbandon_CheckBox"));
			 clickXPath(AllActors.iniXPath.get("CCD", "submitButton"));
			 //Wait until new button is enabled which means save is completed.
			 dependableWait_byID("form_action_new", 5);
			 log.info("\n@(" + agentType + ") " +  username + " : Disabled Abandon Callback ");
		 }else {
			 log.info("\n@(" + agentType + ") " +  username + " : Abandon Callback is already disabled. ");
		 }
		 
		 wait(3);
			  
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
		 enabled = checkEnabledXPath(AllActors.iniXPath.get("CCD", "minTimeBeforeAbandon_TxtBox"));
		 if(!enabled){ 
			 clickXPath(AllActors.iniXPath.get("CCD", "enableAbandon_CheckBox"));
			 typeElementXPath(AllActors.iniXPath.get("CCD", "minTimeBeforeAbandon_TxtBox"), minAbandonTime);
			 log.info("\n@(" + agentType + ") " +  username + " : Enabled Abandon Callback ");
		 }else {
			 log.info("\n@(" + agentType + ") " +  username + " : Abandon Callback is already enabled. ");
		 }
		 wait(5);
		 dependableClick_byID("submitButton");	 
		 //Wait until new button is enabled which means save is completed.
		 dependableWait_byID("form_action_new", 5);
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
		 find_and_fill_byID("listFilterGlobal", serviceName, false);
		 dependableClick_byID("tab0_link");
		 find_and_fill_byID("wrap_up_time", wuTime, false);
		 find_and_fill_byID("forced_release_time", frTime, false);

		 dependableClick_byID("tab7_link");  //Click abandon tab
		 
		 enabled = checkEnabledXPath(AllActors.iniXPath.get("CCD", "minTimeBeforeAbandon_TxtBox"));
		 if(enabled){
			 clickXPath(AllActors.iniXPath.get("CCD", "enableAbandon_CheckBox"));
			 log.info("\n@(" + agentType + ") " +  username + " : Disabled Abandon Callback ");
		 }else {
			 log.info("\n@(" + agentType + ") " +  username + " : Abandon Callback is already disabled. ");
		 }
			  
		 wait(2);
		 dependableClick_byID("submitButton");	  
		 //Wait until new button is enabled which means save is completed.
		 dependableWait_byID("form_action_new", 5);
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
		 //clickXPath(AllActors.iniXPath.get("CCD", "serviceAbandonTab")); 
		 
		 enabled = checkEnabledXPath(AllActors.iniXPath.get("CCD", "minTimeBeforeAbandon_TxtBox"));
		 if(!enabled){
			 
			 clickXPath(AllActors.iniXPath.get("CCD", "enableAbandon_CheckBox"));
			 typeElementXPath(AllActors.iniXPath.get("CCD", "minTimeBeforeAbandon_TxtBox"), minAbandonTime);
			 clickXPath(AllActors.iniXPath.get("CCD", "submitButton"));
			 //Wait until new button is enabled which means save is completed.
			 dependableWait_byID("form_action_new", 5);
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
		 typeElementXPath(AllActors.iniXPath.get("CCD", "searchBox"), serviceName);
		 //clickXPath(AllActors.iniXPath.get("CCD", "serviceGeneralTab")); 
		 typeElementXPath(AllActors.iniXPath.get("CCD", "wrapupTime_TxtBox"), wuTime); 
		 typeElementXPath(AllActors.iniXPath.get("CCD", "ForcedReleaseTimeout_TxtBox"), frTime);
		 clickXPath(AllActors.iniXPath.get("CCD", "submitButton"));
		 //Wait until new button is enabled which means save is completed.
		 dependableWait_byID("form_action_new", 5);
		 
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
		 typeElementXPath(AllActors.iniXPath.get("CCD", "searchBox"), serviceName);
		 clickXPath(AllActors.iniXPath.get("CCD", "serviceGeneralTab")); 
		 typeElementXPath(AllActors.iniXPath.get("CCD", "wrapupTime_TxtBox"), wuTime); 
		 typeElementXPath(AllActors.iniXPath.get("CCD", "ForcedReleaseTimeout_TxtBox"), frTime);
		 clickXPath(AllActors.iniXPath.get("CCD", "submitButton"));
		 //Wait until new button is enabled which means save is completed.
		 dependableWait_byID("form_action_new", 5);
		 
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
		 //Wait until new button is enabled which means save is completed.
		 dependableWait_byID("form_action_new", 5);
		 
		 
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
		 //Wait until new button is enabled which means save is completed.
		 dependableWait_byID("form_action_new", 5);
		 
		 
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
		 typeElementXPath(AllActors.iniXPath.get("CCD", "searchBox"), serviceName);
		 clickXPath(AllActors.iniXPath.get("CCD", "serviceGeneralTab")); 
		 typeElementXPath(AllActors.iniXPath.get("CCD", "wrapupTime_TxtBox"), wuTime); 
		 typeElementXPath(AllActors.iniXPath.get("CCD", "ForcedReleaseTimeout_TxtBox"), frTime);
		 clickXPath(AllActors.iniXPath.get("CCD", "submitButton"));
		 //Wait until new button is enabled which means save is completed.
		 dependableWait_byID("form_action_new", 5);
		 
		 logOutHBDirector();
		 minimizeBrowser();
	 }
	 
	 
	 
	 public void logOutHBDirector() throws Exception
	 {
		 log.info("\n@(Super)" + username + ": Log out HB-Director");
		 maximizeBrowser();
		 wait(2);
		 clickXPath(AllActors.iniXPath.get("CCD", "logoutLink"));
		 wait(2);
		 minimizeBrowser();
	 }
	 
	public void createOneAgent(String agentName, String agentUserName, String agentID, String agentExtension, String agentEmail) {	
		log.info("\n@(" + agentType + ") " +  username + " :Creating agent=>  "+ agentUserName );
		String agentCOS;
		agentCOS = "LOAD_COS";
		try{
			 clickXPath(AllActors.iniXPath.get("CCD", "agentNewBtn"));
			 typeElementXPath(AllActors.iniXPath.get("CCD", "agentNameTxtBox"), agentName);
			 typeElementXPath(AllActors.iniXPath.get("CCD", "agentUserNameTxtBox"), agentUserName); 
			 typeElementXPath(AllActors.iniXPath.get("CCD", "agentIDTxtBox"), agentID);
			 typeElementXPath(AllActors.iniXPath.get("CCD", "agentExtenstionTxtBox"), agentExtension);
			 selectFromComboBox(AllActors.iniXPath.get("CCD", "agentCOS"), agentCOS);
			 typeElementXPath(AllActors.iniXPath.get("CCD", "agentCorporateEmailAddress"), agentEmail);
			 clickXPath(AllActors.iniXPath.get("CCD", "agentCreateBtn"));
			 wait(2);
		}catch(Exception e){
			log.info("\n@(" + agentType + ") " +  username + " fail to create an agent=> "+ agentName);
		}
	}
	 
	/**
	 * This prepares CCd before running test scenrios
	 * It includes Service setup with Destination/Overflow(60)/Interflow(120s)
	 * and outbound default destination and email irn default destination, etc.
	 * @throws Exception
	 */
		public void Max_LogIn_PrepareTest_LogOut_Min() throws Exception{
			 log.info("\n@(" + agentType + ") " +  username + " ##### PrepareTest #####");
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
			 enabled = checkEnabledXPath(AllActors.iniXPath.get("CCD", "minTimeBeforeAbandon_TxtBox"));
			 if(enabled){
				 clickXPath(AllActors.iniXPath.get("CCD", "enableAbandon_CheckBox"));
				 log.info("\n@(" + agentType + ") " +  username + " : Disabled Abandon Callback ");
			 }else {
				 log.info("\n@(" + agentType + ") " +  username + " : Abandon Callback is already disabled. ");
			 }
			 wait(3);
			 dependableClick_byID("submitButton");	  
			 //Wait until new button is enabled which means save is completed.
			 dependableWait_byID("form_action_new", 5);
			 
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
			 //Wait until new button is enabled which means save is completed.
			 dependableWait_byID("form_action_new", 5);
			 
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
		
		/**
		 * This resets NLA real-time reports like Group/DNIS reports for voice/chat/email
		 * @throws Exception
		 */
		public void Max_LogIn_reset_NLA_LogOut_Min() throws Exception{
			if (AllActors.iniMain.get("CCD", "skipNLAReset").contains("yes"))
				 return;
			
			if (AllActors.currentCallType.contains("Email")){
				Max_LogIn_reset_EGroup_EDNIS_LogOut_Min();
			}else {
				Max_LogIn_reset_Group_DNIS_LogOut_Min();
				
			}

		}
		
		/**
		 * This changes Group/DNIS-> Interval time to 1 and 60 to rest NLA Group Report
		 * This only sets voice and chat group.
		 * @throws Exception
		 */
		public void Max_LogIn_reset_Group_DNIS_LogOut_Min() throws Exception{
			
			 //if (AllActors.currentCallType.contains("Voice")) {
			 log.info("\n@(" + agentType + ") " +  username + " ##### Reset Group/DNIS intervl -> NLA #####");
			 String intervalTimeLong, intervalTimeShort;
			 String groupName1, groupName2, irnName1;
			 int intervalResetTime;
			 
			 intervalResetTime = Integer.parseInt(AllActors.iniMain.get("CCD", "intervalResetTime"));
			 intervalTimeLong = "60";
			 intervalTimeShort = "1";
			 groupName1 = AllActors.services.get(0).destination;
			 groupName2 = AllActors.services.get(2).destination;
			 irnName1 = AllActors.irns.get(0).irnNum;
			 
			 maximizeBrowser();
			 logIntoHBDirector();
			 waitUntilMainTitle("Favorites");
			 
			 //##### Reseting Group #####
			 wait(2);
			 openHBDirectorPage("group");
			 waitUntilMainTitle("Groups");
			 wait(2);

			 log.info("\n@(" + agentType + ") " +  username + " ##### Set 1st Group Interval to 1 sec: " + groupName1);
			 find_and_fill_byID("listFilterGlobal", groupName1 , false);
			 dependableClick_byID("tab0_link");
			 find_and_fill_byID("g_interval_time", intervalTimeShort, false);
			 dependableClick_byID("submitButton");	
			 //Wait until new button is enabled which means save is completed.
			 dependableWait_byID("form_action_new", 5);
			 
			 log.info("\n@(" + agentType + ") " +  username + " ##### Set 2nd Group Interval to  1 sec: " + groupName2);
			 find_and_fill_byID("listFilterGlobal", groupName2 , false);
			 dependableClick_byID("tab0_link");
			 find_and_fill_byID("g_interval_time", intervalTimeShort, false);
			 dependableClick_byID("submitButton");	
			 //Wait until new button is enabled which means save is completed.
			 dependableWait_byID("form_action_new", 5);
			 
			//##### Reseting IRN #####
			 openHBDirectorPage("irn");
			 waitUntilMainTitle("IRN");
			 wait(2);
			 
			 log.info("\n@(" + agentType + ") " +  username + " ##### Set 1st IRN Interval to 1 sec: " + irnName1 );
			 find_and_fill_byID("listFilterGlobal", irnName1 , false);
			 dependableClick_byID("tab2_link");
			 find_and_fill_byID("dn_interval_time", intervalTimeShort, false);
			 dependableClick_byID("submitButton");	
			 //Wait until new button is enabled which means save is completed.
			 dependableWait_byID("form_action_new", 5);
			 
			 //Wait
			 log.info("\n@(" + agentType + ") " +  username + " ##### wait until NLA reset for : " + intervalResetTime);
			 wait(intervalResetTime);
			 
			 
			 //##### Reseting Group #####
			 wait(2);
			 openHBDirectorPage("group");
			 waitUntilMainTitle("Groups");
			 wait(2);
			 log.info("\n@(" + agentType + ") " +  username + " ##### Set 1st Group Interval to 60s: " + groupName1);
			 find_and_fill_byID("listFilterGlobal", groupName1 , false);
			 dependableClick_byID("tab0_link");
			 find_and_fill_byID("g_interval_time", intervalTimeLong, false);
			 dependableClick_byID("submitButton");	
			 //Wait until new button is enabled which means save is completed.
			 dependableWait_byID("form_action_new", 5);
			 
			 log.info("\n@(" + agentType + ") " +  username + " ##### Set 2nd Group Interval to 60 : " + groupName2);
			 find_and_fill_byID("listFilterGlobal", groupName2 , false);
			 dependableClick_byID("tab0_link");
			 find_and_fill_byID("g_interval_time", intervalTimeLong, false);
			 dependableClick_byID("submitButton");	
			 //Wait until new button is enabled which means save is completed.
			 dependableWait_byID("form_action_new", 5);
			 
			 
			//##### Reseting IRN to 60 s#####
			 openHBDirectorPage("irn");
			 waitUntilMainTitle("IRN");
			 wait(2);
			 
			 log.info("\n@(" + agentType + ") " +  username + " ##### Set 1st IRN Interval to 60 : " + irnName1);
			 find_and_fill_byID("listFilterGlobal", irnName1 , false);
			 dependableClick_byID("tab2_link");
			 find_and_fill_byID("dn_interval_time", intervalTimeLong, false);
			 dependableClick_byID("submitButton");	
			 //Wait until new button is enabled which means save is completed.
			 dependableWait_byID("form_action_new", 5);
			 
			 
					 
			 logOutHBDirector();
			 minimizeBrowser();
		 }
		
		
		/**
		 * This changes Email Group/DNIS-> Interval time to 1 and 60 to rest NLA Group Report
		 * This only sets voice and chat group.
		 * @throws Exception
		 */
		public void Max_LogIn_reset_EGroup_EDNIS_LogOut_Min() throws Exception{

			 //if (AllActors.currentCallType.contains("Voice")) {
			 log.info("\n@(" + agentType + ") " +  username + " ##### Reset Email Group/DNIS intervl -> NLA #####");
			 String intervalTimeLong, intervalTimeShort;
			 String groupName1, irnName1;
			 int intervalResetTime;
			 
			 intervalResetTime = Integer.parseInt(AllActors.iniMain.get("CCD", "intervalResetTime"));
			 intervalTimeLong = "0100";
			 intervalTimeShort = "0001";
			 groupName1 = AllActors.services.get(3).destination;
			 irnName1 = AllActors.irns.get(5).irnNum;
			 
			 maximizeBrowser();
			 logIntoHBDirector();
			 waitUntilMainTitle("Favorites");
			 
			 //##### Reseting Group #####
			 wait(2);
			 openHBDirectorPage("email_group");
			 waitUntilMainTitle("Email Groups");
			 wait(2);

			 log.info("\n@(" + agentType + ") " +  username + " ##### Set 1st Email Group Interval to 1 sec: " + groupName1);
			 find_and_fill_byID("listFilterGlobal", groupName1 , false);
			 dependableClick_byID("tab0_link");
			 find_and_fill_byID("g_interval_time", intervalTimeShort, false);
			 dependableClick_byID("submitButton");	
			 //Wait until new button is enabled which means save is completed.
			 dependableWait_byID("form_action_new", 5);
			 
						 
			//##### Reseting IRN #####
			 openHBDirectorPage("email_irn");
			 waitUntilMainTitle("Email IRN");
			 wait(2);
			 
			 log.info("\n@(" + agentType + ") " +  username + " ##### Set 1st Email IRN Interval to 1 sec: " + irnName1 );
			 find_and_fill_byID("listFilterGlobal", irnName1 , false);
			 dependableClick_byID("tab2_link");
			 find_and_fill_byID("dn_interval_time", intervalTimeShort, false);
			 dependableClick_byID("submitButton");	
			 //Wait until new button is enabled which means save is completed.
			 dependableWait_byID("form_action_new", 5);
			 
			 //Wait
			 log.info("\n@(" + agentType + ") " +  username + " ##### wait until NLA reset for : " + intervalResetTime);
			 wait(intervalResetTime);
			 
			 
			 //##### Reseting Group #####
			 wait(2);
			 openHBDirectorPage("email_group");
			 waitUntilMainTitle("Email Groups");
			 wait(2);
			 
			 log.info("\n@(" + agentType + ") " +  username + " ##### Set 1st Group Interval to 60s: " + groupName1);
			 find_and_fill_byID("listFilterGlobal", groupName1 , false);
			 dependableClick_byID("tab0_link");
			 find_and_fill_byID("g_interval_time", intervalTimeLong, false);
			 dependableClick_byID("submitButton");	
			 //Wait until new button is enabled which means save is completed.
			 dependableWait_byID("form_action_new", 5);
			 
			 			 
			//##### Reseting IRN to 60 s#####
			 openHBDirectorPage("email_irn");
			 waitUntilMainTitle("Email IRN");
			 wait(2);
			 
			 log.info("\n@(" + agentType + ") " +  username + " ##### Set 1st IRN Interval to 60 : " + irnName1);
			 find_and_fill_byID("listFilterGlobal", irnName1 , false);
			 dependableClick_byID("tab2_link");
			 find_and_fill_byID("dn_interval_time", intervalTimeLong, false);
			 dependableClick_byID("submitButton");	
			 //Wait until new button is enabled which means save is completed.
			 dependableWait_byID("form_action_new", 5);
			 
			 
					 
			 logOutHBDirector();
			 minimizeBrowser();
		 }
	 
	 
	
}
