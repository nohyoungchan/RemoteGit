package ClassesEntity;

import java.util.Hashtable;

import org.ini4j.Wini;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;

/**
 * This build and analyzes jasonMessage
 * @author Admin
 *
 */
public class JasonMessageBuilder extends TestBaseObject {
	//protected String superID; 
	protected Hashtable<String, String> jasonMessageHash;

	
    
    public JasonMessageBuilder(){
	}

    /**
     * 
     * @param supID		: supervisor ID from eccdb->sup table
     * @param strEntity	: entity to subscribe like agent, group, etc
     * @return : This returns a string for subscribeEvents to use
     * @throws JSONException
     */
	public String subscribeEvents(String supID, String strEntity) throws JSONException
	{
		StringBuffer strBuffer;
		String substring, strTemp, strReplaced;
		int position;
		
		strTemp = inij.get("jasonMessage", "subscribe-events");
		log.info("@subscribe-events->Raw: " + strTemp);
		//strReplaced = strTemp.replaceAll("replaceMe", strEntity);
		//log.info("@subscribe-events->change 1: " + strReplaced);
    	JSONObject jObject = new JSONObject(strTemp);
    	
    	jObject.remove("request-id");
    	jObject.put("request-id" , Integer.parseInt(supID));  //Integer needs to this
    	
    	
    	//### replace "replaceMe" from file to entity name.
    	strTemp = jObject.toString();
    	strBuffer = new StringBuffer(strTemp);
    	substring = "replaceMe";
    	position = strBuffer.lastIndexOf(substring);
    	strBuffer.replace(position,  position+substring.length(), strEntity);
    	strTemp = strBuffer.toString();
    	
    	//strTemp = strTemp.replaceAll("\"", "\\\"");
		
		return strTemp;
	}
	
    /**
     * 
     * @param supID		: supervisor ID from eccdb->sup table
     * @param strEntity	: entity to subscribe like agent, group, etc
     * @return : This returns a string for subscribeEvents to use
     * @throws JSONException
     */
	public String subscribeEvents_DM(String supID, String strEntity) throws JSONException
	{
		StringBuffer strBuffer;
		String substring, strTemp, strReplaced;
		int position;
		
		strTemp = inij.get("jasonMessage", "subscribe-events-DM");
		log.info("@subscribe-events->Raw: " + strTemp);
		//strReplaced = strTemp.replaceAll("replaceMe", strEntity);
		//log.info("@subscribe-events->change 1: " + strReplaced);
    	JSONObject jObject = new JSONObject(strTemp);
    	
    	jObject.remove("request-id");
    	jObject.put("request-id" , Integer.parseInt(supID));  //Integer needs to this
    	
    	
    	//### replace "replaceMe" from file to entity name.
    	strTemp = jObject.toString();
    	strBuffer = new StringBuffer(strTemp);
    	substring = "replaceMe";
    	position = strBuffer.lastIndexOf(substring);
    	strBuffer.replace(position,  position+substring.length(), strEntity);
    	strTemp = strBuffer.toString();
    	
    	//strTemp = strTemp.replaceAll("\"", "\\\"");
		
		return strTemp;
	}
	
	/**
	 * 
     * @param supID		: supervisor ID from eccdb->sup table
     * @param strEntity	: entity to subscribe like agent, group, etc
	 * @param subscribeOrUnsubscribe  : This is either "subscribe" or "unsubscribe"
	 * @return			: This returns a string for subscribeEvents to use
	 * @throws JSONException
	 */
	public String subOrUnsubscribeEvents_DM(String supID, String strEntity, String subscribeOrUnsubscribe) throws JSONException
	{
		StringBuffer strBuffer;
		String substring, strTemp;
		int position;
		
		strTemp = inij.get("jasonMessage", "subscribe-events-DM");
		
		if (subscribeOrUnsubscribe.contains("unsubscribe")) {
			strTemp = strTemp.replaceAll("subscribe-events", "unsubscribe-events");
			log.info("@unsubscribe-events->Raw: " + strTemp);
			log.info("I am here at unsubscribe");
			
		}else{
			log.info("@subscribe-events->Raw: " + strTemp);
		}

    	JSONObject jObject = new JSONObject(strTemp);
    	
    	jObject.remove("request-id");
    	jObject.put("request-id" , Integer.parseInt(supID));  //Integer needs to this
    	
    	//### replace "replaceMe" from file to entity name.
    	strTemp = jObject.toString();
    	strTemp = strTemp.replaceAll("replaceMe", strEntity);
    	
    	//### replace "replaceMe" from file to entity name.
    	/*strTemp = jObject.toString();
    	strBuffer = new StringBuffer(strTemp);
    	substring = "replaceMe";
    	position = strBuffer.lastIndexOf(substring);
    	strBuffer.replace(position,  position+substring.length(), strEntity);
    	strTemp = strBuffer.toString();*/
    	
    	//strTemp = strTemp.replaceAll("\"", "\\\"");
		
		return strTemp;
	}
	
	public String subOrUnsubscribeEvents_DM(String supID, String strEntity, String strEntityDetail, String subscribeOrUnsubscribe) throws JSONException
	{
		String strTemp;
		
		strTemp = inij.get("jasonMessage", "subscribe-events-DM");
		
		if (subscribeOrUnsubscribe.contains("unsubscribe")) {
			strTemp = strTemp.replaceAll("subscribe-events", "unsubscribe-events");
			log.info("@unsubscribe-events->Raw: " + strTemp);
			log.info("I am here at unsubscribe");
			
		}else{
			log.info("@subscribe-events->Raw: " + strTemp);
		}

    	JSONObject jObject = new JSONObject(strTemp);
    	
    	jObject.remove("request-id");
    	jObject.put("request-id" , Integer.parseInt(supID));  //Integer needs to this
    	
    	//### replace "replaceMe" from file to entity name.
    	strTemp = jObject.toString();
    	strTemp = strTemp.replaceAll("replaceMe_Entity", strEntity);
    	strTemp = strTemp.replaceAll("replaceMe_Detail", strEntityDetail);
    	//### replace "replaceMe" from file to entity name.
    	/*strTemp = jObject.toString();
    	strBuffer = new StringBuffer(strTemp);
    	substring = "replaceMe";
    	position = strBuffer.lastIndexOf(substring);
    	strBuffer.replace(position,  position+substring.length(), strEntity);
    	strTemp = strBuffer.toString();*/
    	
    	//strTemp = strTemp.replaceAll("\"", "\\\"");
    	
    	log.info("I am here: strTemp ==> " + strTemp);
		
		return strTemp;
	}
	
	
	/**
	 * 
	 * @param supID
	 * @param reportName
	 * @param type
	 * @return				: This create a new Agent report with agentID(only 1 agent)
	 * @throws JSONException
	 */
	public String saveReport(String supID, String reportName, String type) throws JSONException
	{
		String strReplaced, strTemp, agentID;
		agentID = ini.get(sysTT, "saveReport.agentID");
		strTemp = ini.get("jasonMessage", "saveReport");
		log.info("@saveReport->Raw: " + strTemp);
		
		strReplaced = strTemp.replaceAll("replaceme_actionType", type);
		strReplaced = strTemp.replaceAll("replaceme_superID", supID);
		strReplaced = strTemp.replaceAll("replaceme_customReportName", reportName);
		strReplaced = strTemp.replaceAll("replaceme_agentID", agentID);
		//log.info("@subscribe-events->change 1: " + strReplaced);
    	JSONObject jObject = new JSONObject(strTemp);
    	
    	jObject.remove("request-id");
    	jObject.put("request-id" , Integer.parseInt(supID));  //Integer needs to this
    	
		
		return strReplaced;
	}
	
	/**
	 * This function parses a string(which is raw) form JsonAPITesterClient.sendRequest and returns processed string
	 * @param strPollResult
	 * @throws JSONException
	 */
    public String jasonParsor_Agents(String strPollResult) throws JSONException{
    	JSONObject temp;
    	int i, j, totalArrays;
    	String entity, strParsedResultp;
    	String strTemp = " ";
    	entity = "agents";
    	log.info("\n#### Parsing Jason Message: " + entity + " ####");
    	
    	JSONArray arr = returnJasonArray(strPollResult, entity);
    	totalArrays= arr.length();
    	strTemp = strTemp + printS("==> Total " + entity + " : " + totalArrays);
    	
    	for (i =0; i < totalArrays; i++){
    		j = i+1;
    		temp = arr.getJSONObject(i);
    		
    		strTemp = strTemp + printS(j + ". " + entity + " Records ####");
    		j = 1;
    		strTemp = strTemp + printS("   " + j++ + ") agent-name is            ==> " + temp.get("agent-name"));
    		strTemp = strTemp + printS("   " + j++ + ") agent-username is        ==> " + temp.get("agent-username"));
    		strTemp = strTemp + printS("   " + j++ + ") agent-id is              ==> " + temp.get("agent-id"));
    		strTemp = strTemp + printS("   " + j++ + ") agent-extension is       ==> " + temp.get("agent-extension"));
    		strTemp = strTemp + printS("   " + j++ + ") agent-number is          ==> " + temp.get("agent-number"));
    		strTemp = strTemp + printS("   " + j++ + ") agent-cos is             ==> " + temp.get("agent-cos"));
    		strTemp = strTemp + printS("   " + j++ + ") screen-pop-profile-id is ==> " + temp.get("screen-pop-profile-id"));
    		strTemp = strTemp + printS("   " + j++ + ") agent-email is           ==> " + temp.get("agent-email"));
    		strTemp = strTemp + printS("   " + j +   ") agent-supervisor is      ==> " + temp.get("agent-supervisor"));
    		//strTemp = strTemp + printS("   " + j +   ") tenant-id is             ==> " + temp.get("tenant-id"));
    	}
    	log.info("########\n");
    	return strTemp;
    	
    	    	
    }
    
    public String jasonParsor_Groups(String strPollResult) throws JSONException{
    	JSONObject temp;
    	int i, j, totalArrays;
    	String entity;
    	String strTemp = " ";
    	entity = "groups";
    	log.info("\n#### Parsing Jason Message: " + entity + " ####");
    	
    	JSONArray arr = returnJasonArray(strPollResult, entity);
    	totalArrays= arr.length();
    	strTemp = strTemp + printS("==> Total " + entity + " : " + totalArrays);
    	
    	for (i =0; i < totalArrays; i++){
    		j = i+1;
    		temp = arr.getJSONObject(i);
    		
    		strTemp = strTemp + printS(j + ". " + entity + " Records ####");
    		j = 1;
    		strTemp = strTemp + printS("   " + j++ + ") group-name is            ==> " + temp.get("group-name"));
    		strTemp = strTemp + printS("   " + j++ + ") group-id is              ==> " + temp.get("group-id"));

    	}
    	strTemp = strTemp + printS("####################################################\n");
    	return strTemp;
    	
    }
    
    public String jasonParsor_Dnis(String strPollResult) throws JSONException{
    	JSONObject temp;
    	int i, j, totalArrays;
    	String entity;
    	String strTemp = " ";
    	entity = "dnis";
    	log.info("\n#### Parsing Jason Message: " + entity + " ####");
    	
    	JSONArray arr = returnJasonArray(strPollResult, entity);
    	totalArrays= arr.length();
    	strTemp = strTemp + printS("==> Total " + entity + " : " + totalArrays);
    	
    	for (i =0; i < totalArrays; i++){
    		j = i+1;
    		temp = arr.getJSONObject(i);
    		
    		strTemp = strTemp + printS(j + ". " + entity + " Records ####");
    		j = 1;
    		strTemp = strTemp + printS("   " + j++ + ") dnis-name is            ==> " + temp.get("dnis-name"));
    		strTemp = strTemp + printS("   " + j++ + ") dnis-number is          ==> " + temp.get("dnis-number"));
    		strTemp = strTemp + printS("   " + j++ + ") dnis-id is              ==> " + temp.get("dnis-id"));

    	}
    	
    	log.info("########\n");
    	return strTemp;
    	
    }
    
    public String jasonParsor_Schedule(String strPollResult) throws JSONException{

    	JSONObject temp;
    	int i, j, totalArrays;
    	String entity;
    	String strTemp = " ";
    	entity = "schedule";
    	log.info("\n#### Parsing Jason Message: " + entity + " ####");
    	
    	JSONArray arr = returnJasonArray(strPollResult, entity);
    	totalArrays= arr.length();
    	strTemp = strTemp + printS("==> Total " + entity + " : " + totalArrays);
    	
    	for (i =0; i < totalArrays; i++){
    		j = i+1;
    		temp = arr.getJSONObject(i);
    		
    		strTemp = strTemp + printS(j + ". " + entity + " Records ####");
    		j = 1;
    		strTemp = strTemp + printS("   " + j++ + ") schedule-name is            ==> " + temp.get("schedule-name"));
    		strTemp = strTemp + printS("   " + j++ + ") schedule-id is              ==> " + temp.get("schedule-id"));

    	}
    	
    	log.info("########\n");
    	return strTemp;
    	
    }
    
    public String jasonParsor_Service(String strPollResult) throws JSONException{

    	JSONObject temp;
    	int i, j, totalArrays;
    	String entity;
    	String strTemp = " ";
    	entity = "services";
    	log.info("\n#### Parsing Jason Message: " + entity + " ####");
    	
    	JSONArray arr = returnJasonArray(strPollResult, entity);
    	totalArrays= arr.length();
    	strTemp = strTemp + printS("==> Total " + entity + " : " + totalArrays);
    	
    	for (i =0; i < totalArrays; i++){
    		j = i+1;
    		temp = arr.getJSONObject(i);
    		
    		strTemp = strTemp + printS(j + ". " + entity + " Records ####");
    		j = 1;
    		strTemp = strTemp + printS("   " + j++ + ") service-name is            ==> " + temp.get("service-name"));
    		strTemp = strTemp + printS("   " + j++ + ") service-id is              ==> " + temp.get("service-id"));

    	}
    	
    	log.info("########\n");
    	return strTemp;
    	
    }
    
    public String jasonParsor_reportTemplate(String strPollResult) throws JSONException{

    	JSONObject temp;
    	int i, j, totalArrays;
    	String entity;
    	String strTemp = " ";
    	entity = "templates";
    	log.info("\n#### Parsing Jason Message: " + entity + " ####");
    	
    	JSONArray arr = returnJasonArray(strPollResult, entity);
    	totalArrays= arr.length();
    	strTemp = strTemp + printS("==> Total " + entity + " : " + totalArrays);
    	
    	//entity = "template-";
    	for (i =0; i < totalArrays; i++){
    		j = i+1;
    		temp = arr.getJSONObject(i);
    		
    		strTemp = strTemp + printS(j + ". " + entity + " Records ####");
    		j = 1;
    		strTemp = strTemp + printS("   " + j++ + ") template-name is            ==> " + temp.get("template-name"));
    		strTemp = strTemp + printS("   " + j++ + ") template-id is              ==> " + temp.get("template-id"));
    	}
    	log.info("########\n");
    	return strTemp;
    	
    }
    
    /**
     * 
     * @param strPollResult
     * The following is the sample strPollResult
     * "reports":[{"report-id":103,"report-creator-id":2147483643,"report-name":"RG1.1 Group Performance Report by Interval"
     * @throws JSONException
     */
   
    public String jasonParsor_customReport(String strPollResult) throws JSONException{

    	JSONObject temp;
    	int i, j, totalArrays;
    	String entity;
    	String strTemp = " ";
    	entity = "reports";
    	log.info("\n#### Parsing Jason Message: " + entity + " ####");
    	
    	JSONArray arr = returnJasonArray(strPollResult, entity);
    	totalArrays= arr.length();
    	strTemp = strTemp + printS("==> Total " + entity + " : " + totalArrays);
    	
    	for (i =0; i < totalArrays; i++){
    		j = i+1;
    		temp = arr.getJSONObject(i);
    		
    		strTemp = strTemp + printS(j + ". " + entity + " Records ####");
    		j = 1;
    		strTemp = strTemp + printS("   " + j++ + ") report-name is            ==> " + temp.get("report-name"));
    		strTemp = strTemp + printS("   " + j++ + ") report-id is              ==> " + temp.get("report-id"));
    	}
    	log.info("########\n");
    	return strTemp;
    	
    }

    
    /**
     * 
     * @param strPollResult
     * The following is the sample strPollResult
     * "reports":[{"version":1,"sequence":2,"timestamp":"2016-11-16 16:00:31.893","topic":"contact-center","message":"entity-snapshot","sub-topic":"ivr-application-snapshot","ivr-applications":[{"ivr-application-id":1,
     * @throws JSONException
     */
   
    public String jasonParsor_IVRApplication(String strPollResult) throws JSONException{

    	JSONObject temp;
    	int i, j, totalArrays;
    	String entity;
    	String strTemp = " ";
    	entity = "ivr-applications";
    	log.info("\n#### Parsing Jason Message: " + entity + " ####");
    	
    	JSONArray arr = returnJasonArray(strPollResult, entity);
    	totalArrays= arr.length();
    	strTemp = strTemp + printS("==> Total " + entity + " : " + totalArrays);

    	for (i =0; i < totalArrays; i++){
    		j = i+1;
    		temp = arr.getJSONObject(i);
    		
    		strTemp = strTemp + printS(j + ". " + entity + " Records ####");
    		j = 1;
    		strTemp = strTemp + printS("   " + j++ + ") ivr-application-name is            ==> " + temp.get("ivr-application-name"));
    		strTemp = strTemp + printS("   " + j++ + ") ivr-application-id is              ==> " + temp.get("ivr-application-id"));
    	}
    	log.info("########\n");
    	return strTemp;
    	
    }
    
    
    public String jasonParsor_DM_IVRPorts(String strPollResult) throws JSONException{

    	JSONObject temp;
    	int i, j, totalArrays;
    	String entity;
    	String strTemp = " ";
    	entity = "ivr-ports";
    	log.info("\n#### Parsing Jason Message: " + entity + " ####");
    	
    	//this is to find correct Jason string with entity 
    	strPollResult = findCorrectJasonMessage(strPollResult, entity);
    	log.info("poll result after filter ==>  " + strPollResult);
    	
    	JSONArray arr = returnJasonArray(strPollResult, entity);
    	totalArrays= arr.length();
    	strTemp = strTemp + printS("==> Total " + entity + " : " + totalArrays);
    	
    	for (i =0; i < totalArrays; i++){
    		j = i+1;
    		temp = arr.getJSONObject(i);
    		
    		strTemp = strTemp + printS(j + ". " + entity + " Records ####");
    		j = 1;
    		strTemp = strTemp + printS("   " + j++ + ") dial-num is   \t\t==> " + temp.get("dial-num"));
    		strTemp = strTemp + printS("   " + j++ + ") state is       \t\t==> " + temp.get("state"));
    		strTemp = strTemp + printS("   " + j++ + ") station-name is  \t\t==> " + temp.get("station-name"));
    		strTemp = strTemp + printS("   " + j++ + ") tenant-id is     \t\t==> " + temp.get("tenant-id"));
    	}
    	log.info("########\n");
    	return strTemp;
    	
    }
    
    
    public String jasonParsor_DM_SubSystem(String strPollResult) throws JSONException{

    	JSONObject temp;
    	int i, j, totalArrays;
    	String entity;
    	String strTemp;
    	strTemp = " ";
    	entity = "sub-system";
    	
    	
    	//DM_subSystem string contains multiple Jason Object.
    	JSONArray arr = new JSONArray(strPollResult);
    	totalArrays = arr.length();
    	strTemp = strTemp + printS("==> Total " + entity + " : " + totalArrays);

    	  //loop through each object
    	for (i=0; i<totalArrays; i++){
    	   j = i+1;
    	   JSONObject jObject = arr.getJSONObject(i);
    	   
    	   
    	   //This is to prevent an exception, so check if the object->string contains entity data.
    	   if (!jObject.toString().contains(entity)) continue;

  		   strTemp = strTemp + printS("   " + j++ + ") sub-system      \t\t==> " + jObject.get("sub-system"));
  		   //strTemp = strTemp + printS("   " + j++ + ") sequence      \t\t==> " + jObject.get("sequence"));

    	}

    	log.info("########\n");
    	return strTemp;
    	
    }


    
    public String jasonParsor_DM_EcEmails(String strPollResult) throws JSONException{

    	JSONObject temp;
    	int i, j, totalArrays;
    	String entity;
    	String strTemp;
    	strTemp = " ";
    	entity = "emails";


    	JSONArray arr = returnJasonArray(strPollResult, entity);
    	totalArrays= arr.length();
    	strTemp = strTemp + printS("==> Total " + entity + " : " + totalArrays);
    	
    	for (i =0; i < totalArrays; i++){
    		j = i+1;
    		temp = arr.getJSONObject(i);
    		
    		strTemp = strTemp + printS(j + ". " + entity + " Records ####");
    		j = 1;
    		//strTemp = strTemp + printS("   " + j++ + ") dial-num is   \t\t==> " + temp.get("dial-num"));
    		
    	}
    	log.info("########\n");
    	return strTemp;
    	
    	
    	/*JSONArray arr = new JSONArray(strPollResult);
    	totalArrays = arr.length();
    	strTemp = strTemp + printS("==> Total " + entity + " : " + totalArrays);

    	  //loop through each object
    	for (i=0; i<totalArrays; i++){
    	   j = i+1;
    	   JSONObject jObject = arr.getJSONObject(i);
    	  
  		   strTemp = strTemp + printS("   " + j++ + ") status       \t\t==> " + jObject.get("emails"));

    	}

    	log.info("########\n");
    	return strTemp;*/
    	
    }
    
    public String jasonParsor_DM_ReportingStations(String strPollResult) throws JSONException{

    	JSONObject temp;
    	int i, j, totalArrays;
    	String entity;
    	String strTemp;
    	strTemp = " ";
    	entity = "reporting-stations";

    	//this is to find correct Jason string with entity 
    	strPollResult = findCorrectJasonMessage(strPollResult, entity);
    	log.info("poll result after filter ==>  " + strPollResult);

    	JSONArray arr = returnJasonArray(strPollResult, entity);
    	totalArrays= arr.length();
    	strTemp = strTemp + printS("==> Total " + entity + " : " + totalArrays);
    	
    	for (i =0; i < totalArrays; i++){
    		j = i+1;
    		temp = arr.getJSONObject(i);
    		
    		strTemp = strTemp + printS(j + ". " + entity + " Records ####");
    		j = 1;
    		strTemp = strTemp + printS("   " + j++ + ") staion-ip is   \t\t\t==> " + temp.get("station-ip"));
    		strTemp = strTemp + printS("   " + j++ + ") station-id is   \t\t\t==> " + temp.get("station-id"));
    		strTemp = strTemp + printS("   " + j++ + ") station-state is   \t\t==> " + temp.get("station-state"));
    		strTemp = strTemp + printS("   " + j++ + ") station-build-version is   \t==> " + temp.get("station-build-version"));
    		strTemp = strTemp + printS("   " + j++ + ") station-tenant-list is   \t\t==> " + temp.get("station-tenant-list"));
    		strTemp = strTemp + printS("   " + j++ + ") station-replication-status is   \t==> " + temp.get("station-replication-status"));
    		
    	}
    	log.info("########\n");
    	return strTemp;
    	
    	
    	
    }
    
    public String jasonParsor_DM_Agents(String strPollResult) throws JSONException{

    	JSONObject temp;
    	int i, j, totalArrays;
    	String entity;
    	String strTemp;
    	strTemp = " ";
    	entity = "agents";

    	//this is to find correct Jason string with entity 
    	strPollResult = findCorrectJasonMessage(strPollResult, entity);
    	log.info("poll result after filter ==>  " + strPollResult);

    	JSONArray arr = returnJasonArray(strPollResult, entity);
    	totalArrays= arr.length();
    	strTemp = strTemp + printS("==> Total " + entity + " : " + totalArrays);
    	
    	for (i =0; i < totalArrays; i++){
    		j = i+1;
    		temp = arr.getJSONObject(i);
    		
    		strTemp = strTemp + printS(j + ". " + entity + " Records ####");
    		j = 1;
    		strTemp = strTemp + printS("   " + j++ + ") tenant-id is   \t\t\t==> " + temp.get("tenant-id"));
    		strTemp = strTemp + printS("   " + j++ + ") number is   \t\t\t==> " + temp.get("number"));
    		strTemp = strTemp + printS("   " + j++ + ") state is   \t\t\t==> " + temp.get("state"));
    		//strTemp = strTemp + printS("   " + j++ + ") station-build-version is   \t==> " + temp.get("station-build-version"));
    		//strTemp = strTemp + printS("   " + j++ + ") tenant-id is   \t\t\t==> " + temp.get("tenant-id"));
    		
    	}
    	log.info("########\n");
    	return strTemp;
    	
    	
    	
    }
    
    
    
    public String jasonParsor_DM_Supervisors(String strPollResult) throws JSONException{

    	JSONObject temp;
    	int i, j, totalArrays;
    	String entity;
    	String strTemp;
    	strTemp = " ";
    	entity = "system-manager-supervisors";

    	//this is to find correct Jason string with entity 
    	strPollResult = findCorrectJasonMessage(strPollResult, entity);
    	log.info("poll result after filter ==>  " + strPollResult);

    	JSONArray arr = returnJasonArray(strPollResult, entity);
    	totalArrays= arr.length();
    	strTemp = strTemp + printS("==> Total " + entity + " : " + totalArrays);
    	
    	for (i =0; i < totalArrays; i++){
    		j = i+1;
    		temp = arr.getJSONObject(i);
    		
    		strTemp = strTemp + printS(j + ". " + entity + " Records ####");
    		j = 1;
    		strTemp = strTemp + printS("   " + j++ + ") tenant-id is   \t\t\t==> " + temp.get("tenant-id"));
    		strTemp = strTemp + printS("   " + j++ + ") name is   \t\t\t==> " + temp.get("name"));
    		//strTemp = strTemp + printS("   " + j++ + ") state is   \t\t\t==> " + temp.get("state"));
    		//strTemp = strTemp + printS("   " + j++ + ") station-build-version is   \t==> " + temp.get("station-build-version"));
    		//strTemp = strTemp + printS("   " + j++ + ") tenant-id is   \t\t\t==> " + temp.get("tenant-id"));
    		
    	}
    	log.info("########\n");
    	return strTemp;
    	
    	
    	
    }
    
    /**
     * This finds a correct jason string with entity info incase there is poll result 
     * which provides other entity information.
     * @param strPollResult
     * @param entity
     * @return
     * @throws JSONException
     */
    public String findCorrectJasonMessage(String strPollResult, String entity) throws JSONException{
    	
    	int totalArrays, i, j;
    	String strTemp;
    	strTemp = " ";
    	
    	JSONArray arr = new JSONArray(strPollResult);
    	totalArrays = arr.length();
    	if (totalArrays <=1) {
    		//This case means strPollResult doesn't contain other entity info
    		return strPollResult;
    		
    	}
    	log.info("==> Total " + entity + " : " + totalArrays);

    	  //loop through each object
    	for (i=0; i<totalArrays; i++){
    	   j = i+1;
    	   JSONObject jObject = arr.getJSONObject(i);
    	   strTemp = jObject.toString();
    	   if (strTemp.contains(entity)){ 
    		  break;
    	   }

    	}
    	return strTemp;
    	
    }


    

/*    *//**
     * 
     * @param strPollResult	: Jason-message
     * @param strEntity			: Entity like agents, groups
     * @return					: Returning Jason-array based on strEntity
     * @throws JSONException
     *//*
    public JSONArray returnJasonArray(String strPollResult, String strEntity) throws JSONException{
    	String strSubString;
    	JSONArray arr;
    	arr = null;
      	// Remove the first [, and the last ] since json fails with them.
    	strSubString = removeFirstLastBracket(strPollResult);
    	if (strSubString.isEmpty()){
    		Assert.fail("@ The string is empty, so I am out");
    	}
    	
    	//Get the agents array.
    	JSONObject obj = new JSONObject(strSubString);
    	try {
    		arr = obj.getJSONArray(strEntity);
    	}catch (JSONException e){
    		Assert.fail("@@ Failed on returnJasonArray because no jason entity for  => " + strEntity);
    	}
    	
    	return arr;
    }*/
    
    
  


}
