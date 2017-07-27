package ClassesTest;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.json.JSONException;

import CCEntity.JasonHBSupervisor;
import ClassesEntity.*;

public class Test_Main_API_AgentAll extends TestCaseObject {


	
	public static void main(String[] args) throws Exception {
		//httpsConnection();
		tcpConnection();
	}
	
	public static void httpsConnection() throws Exception{
		AllObjects allObjects;
		JasonAPITesterClient apiTesterClient;
		JasonHBSupervisor hbSup;
		JasonMessageBuilder jMessageBuilder;
		String strJasonMessage, strReturn;

		//############ Read testData.ini file and initiate all objects
		allObjects = new AllObjects();
		apiTesterClient = AllObjects.apiTesterClient;
		jMessageBuilder = AllObjects.jMessageBuilder;
		hbSup = allObjects.hbSupervisors.get(1);  //0 is admin, 1 is supervisor
		
		//############ Log in and get ticket & the ticket is stored on apiTesterClient
		apiTesterClient.logIn(hbSup);
		//apiTesterClient.logIn_Admin();
		
		
		String supID;
		supID = ini.get(sysTT, "sup1.supervisorID");
		//############# Request by Jason message
		strJasonMessage = jMessageBuilder.subscribeEvents(supID, "agent");
		apiTesterClient.sendRequestJson("Subscribe agent", strJasonMessage);
		
		//############# Start Polling
		strReturn = apiTesterClient.startPolling("Subscribe agent");
		jMessageBuilder.jasonParsor_Agents(strReturn);
		
		//############# Request by Jason message
		//strJasonMessage = jMessageBuilder.subscribeEvents(hbSup, "group");
		//apiTesterClient.sendRequestJson("Subscribe agent", strJasonMessage);
		
		//############# Start Polling
		//strReturn = apiTesterClient.startPolling("Subscribe agent");
		
	}
	
	public static void tcpConnection() throws UnknownHostException, IOException, InterruptedException{
		  PrintWriter output;
		  String strReturn;
		  String strUser = "{\"version\":1,\"topic\":\"contact-center\",\"message\":\"authenticate\",\"request-id\":1,\"user\":\"admin\",\"password\":\"81dc9bdb52d04dc20036dbd8313ed055\"}0x00";
		  String strJasonRequest =" {\"version\":1,\"topic\":\"contact-center\",\"message\":\"subscribe-events\",\"request-id\":1,\"subscribe\":[[\"ecc\",\"agent\",[\"*\"]]]}0x00";
		  //BufferedReader inFromUser = new BufferedReader( new InputStreamReader(System.in));
		  //Socket clientSocket = new Socket("localhost", 6789);
		  Socket clientSocket = new Socket("10.23.175.161", 31456);
		  
		  //DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		  output = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

		  //BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		  log.info("I am here 1");
		  //strReturn = inFromServer.readLine();
		  //log.info("FROM SERVER: " + strReturn);
		  //sentence = inFromUser.readLine();
		  
		  //outToServer.writeBytes(strUser + '\n');
		  output.println(strUser);
		  output.flush();
		  BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		  //sleep(5000);
		  log.info("I am here 2");
		  strReturn = inFromServer.readLine();
		  log.info("I am here 3");
		  log.info("FROM SERVER: " + strReturn);
		  log.info("I am here 4");
			 
		  //outToServer.writeBytes(strJasonRequest + '\n');
		  output.println(strJasonRequest);
		  output.flush();
		  log.info("I am here 5");
		  BufferedReader inFromServer2 = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		  strReturn = inFromServer2.readLine();
		  log.info("I am here 6");
		  log.info("FROM SERVER: " + strReturn);
		  clientSocket.close();
	}


}
