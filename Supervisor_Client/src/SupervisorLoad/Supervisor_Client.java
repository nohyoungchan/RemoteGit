package SupervisorLoad;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import BaseObject.*;



/**
 * A simple Swing-based client for the chat server.  Graphically
 * it is a frame with a text field for entering messages and a
 * textarea to see the whole dialog.
 *
 * The client follows the Chat Protocol which is as follows.
 * When the server sends "SUBMITNAME" the client replies with the
 * desired screen name.  The server will keep sending "SUBMITNAME"
 * requests as long as the client submits screen names that are
 * already in use.  When the server sends a line beginning
 * with "NAMEACCEPTED" the client is now allowed to start
 * sending the server arbitrary strings to be broadcast to all
 * chatters connected to the server.  When the server sends a
 * line beginning with "MESSAGE " then all characters following
 * this string should be displayed in its message area.
 */
public class Supervisor_Client extends TestObject{

	int commandNum;
    BufferedReader in;
    PrintWriter out;
    JFrame frame = new JFrame("SupervisorClient");
    JTextField textField = new JTextField(40);
    JTextArea messageArea = new JTextArea(8, 40);
    Socket socket;
    String userName, userPassword, userDelaySec; //Default value is "none"
    
    //HBAgent properties;
    HBAgent hbAgent;
    AllMembers allMemberForHBAgent;

    /**
     * Constructs the client by laying out the GUI and registering a
     * listener with the textfield so that pressing Return in the
     * listener sends the textfield contents to the server.  Note
     * however that the textfield is initially NOT editable, and
     * only becomes editable AFTER the client receives the NAMEACCEPTED
     * message from the server.
     */
    public Supervisor_Client() {

    	commandNum=1;
    	userName = "none";
    	userPassword = "none";
    	userDelaySec = "none";
    	hbAgent = null;
        // Layout GUI
        textField.setEditable(false);
        messageArea.setEditable(false);
        frame.getContentPane().add(textField, "North");
        frame.getContentPane().add(new JScrollPane(messageArea), "Center");
        frame.pack();

        // Add Listeners
        textField.addActionListener(new ActionListener() {
            /**
             * Responds to pressing the enter key in the textfield by sending
             * the contents of the text field to the server.    Then clear
             * the text area in preparation for the next message.
             */
            public void actionPerformed(ActionEvent e) {
                //out.println(textField.getText());
                //textField.setText("");
            }
        });
    }

    /**
     * Prompt for and return the address of the server.
     */
    private String getServerAddress() {
    	String strName;
    /*    return JOptionPane.showInputDialog(
            frame,
            "Enter IP Address of the Server:",
            "Welcome to the Chatter",
            JOptionPane.QUESTION_MESSAGE);*/

        //Utility.readConfigFile();
        //Utility.read_ConfigINI();
    	strName = (String) Utility.ini.get("SuperLoadServer_Config", "serverIPAddress");;
    	return strName;
    }
    
    private int getPort() {
    	int intPort;
    	//strName = (String) Utility.globalVariableHash.get("serverIPAddress");
    	intPort = Integer.parseInt(Utility.ini.get("SuperLoadServer_Config", "port"));
    	return intPort;
    }

    /**
     * Prompt for and return the desired screen name.
     */
    private String getName() {
    	String strName;
    	strName = "NA";
    	try {
    		strName =  InetAddress.getLocalHost().toString();
    		strName = strName.split("/")[1]; //just to show ip address instead of both hostname/ip
    		strName = strName + "_" + System.getProperty("user.name");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       /* return JOptionPane.showInputDialog(
            frame,
            "Choose a screen name:",
            "Screen name selection",
            JOptionPane.PLAIN_MESSAGE);*/
		return strName;
    }

    /**
     * Connects to the server then enters the processing loop.
     * @throws Exception 
     */
    void run() throws Exception {

        // Make connection and initialize streams
    	boolean state;
    	state = true;
    	String strCommandNum = Integer.toString(commandNum);
    	try{
    		Utility.read_ConfigINI();
	        String serverAddress = getServerAddress();
	        int port = getPort();
	        socket = new Socket(serverAddress, port);
	        log("Connected to " + serverAddress);
	        in = new BufferedReader(new InputStreamReader(
	            socket.getInputStream()));
	        out = new PrintWriter(socket.getOutputStream(), true);
	        
	        allMemberForHBAgent = new AllMembers();
	        allMemberForHBAgent.initiateAllActors();
	        
	        textField.setText("### Connected to : " + serverAddress + "/" + port + " ###" + "\n");
	
	        // Process all messages from server, according to the protocol.
	        while (state) {
	            String line = in.readLine();
	            if (line.startsWith("SUBMITNAME")) {
	                out.println(getName());
	            } else if (line.startsWith("NAMEACCEPTED")) {
	                textField.setEditable(true);
	            } else if (line.startsWith("MESSAGE")) {
	            	messageArea.insert(strCommandNum + ") " + line.substring(8) + "\n", 0);
	            	if (!line.contains("WebAgent")){
		                //messageArea.append(line.substring(8) + "\n");
		                //messageArea.insert(line.substring(8) + "\n", 0);
	            		//state is needed to end
		                state = handleMessage(line.substring(8));
	            	}else{
	            		//messageArea.insert(line.substring(8) + "\n", 0);
	            		//state is not needed since WebAgent doesn't handle exit
	            		handleMessageForWebAgent(line.substring(8));
	            		
	            	}
	            	commandNum++;
	            	strCommandNum = Integer.toString(commandNum);
	            	
	                
	            }
	        }
	        socket.close();
	        //System.exit(1);
    	}catch (ConnectException e){
    		log("@ Connection Refused from Server.  Server might not be ready");
    		
    	}
    	catch (IOException e) {
            System.out.println(e);
        } finally {
        	//socket.close();
        	log("## Closing client");
	        System.exit(1);
           
        }
    }

    /**
     * Runs the client as an application with a closeable frame.
     */
    public static void main(String[] args) throws Exception {
        Supervisor_Client client = new Supervisor_Client();
        client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.frame.setVisible(true);
        client.run();
        client.frame.setVisible(false);
    }
    
    
    protected boolean handleMessage(String strMessage) throws Exception{
    	
    	int logInHandled;
    	boolean boolState;
    	boolState = true;
    	String strCommand, strComCommand;
    	String strReturn;
    	String superName, superPassword, superDelaySec;
    	String strAutoItCommand;
    	log("#### HandleMessage => " + strMessage);
    	logInHandled = 0;
    	strCommand = strMessage.split(":")[1].trim();
    	
    	log("#### string command is => " + strCommand);
    	
    	if (strCommand.contains("login_NLA_all")){
    		log("    ## Handling login");
	    	strComCommand = strCommand.split(" ")[0].trim();
	    	userName = strCommand.split(" ")[1].trim();
	    	userPassword = strCommand.split(" ")[2].trim();
	    	userDelaySec = strCommand.split(" ")[3].trim();
        	//strReturn = Utility.executeCommand("Cac_login.bat" + " " + superName + " " + superPassword);
	    	strAutoItCommand = "AutoIt3.exe Cac_login_Super.exe " + userName + " " + userPassword + " " + userDelaySec;
	    	strReturn = Utility.executeCommand(strAutoItCommand);
        	//log("superName is "+ superName  + "/" + superPassword);
        	//log("String Return after command => " + strReturn);
        	logInHandled = 1;
    	}
    	
        switch (strCommand){
        case "relogin_NLA":
        	if (userName.contains("none")) {
        		log("Something wrong on relogin_NLA_all");
        	} else {
	        	strAutoItCommand = "AutoIt3.exe Cac_login_Super.exe " + userName + " " + userPassword + " " + userDelaySec;
		    	strReturn = Utility.executeCommand(strAutoItCommand);
	        	log("superName is "+ userName  + "/" + userPassword  + "/" + userPassword);
	        	//log("String Return after command => " + strReturn);
	        	logInHandled = 1;
        	}
        	break;
        case "logout":
        	log("    ## Handling logout");
	    	strAutoItCommand = "AutoIt3.exe Cac_logout_Super.exe";
	    	strReturn = Utility.executeCommand(strAutoItCommand);
        	//log("superName is "+ superName  + "/" + superPassword);
        	//log("String Return after command => " + strReturn);
        	
        	break;
        case "Open_FLA":
        	log("    ## Opening FLA(Historical Report)");
	    	strAutoItCommand = "AutoIt3.exe Cac_FLA_Open.exe";
	    	strReturn = Utility.executeCommand(strAutoItCommand);
        	break;
        case "Close_FLA":
        	log("    ## Closing FLA(Historical Report)");
	    	strAutoItCommand = "AutoIt3.exe Cac_FLA_Close.exe";
	    	strReturn = Utility.executeCommand(strAutoItCommand);
        	break; 
        case "Open_GCCS":
        	log("    ## Opening GCCS(Historical Report)");
	    	strAutoItCommand = "AutoIt3.exe Cac_GCCS_Open.exe";
	    	strReturn = Utility.executeCommand(strAutoItCommand);
        	break;
        case "Close_GCCS":
        	log("    ## Closing GCCS(Historical Report)");
	    	strAutoItCommand = "AutoIt3.exe Cac_GCCS_Close.exe";
	    	strReturn = Utility.executeCommand(strAutoItCommand);
        	break; 	
        
        case "Upgrade_HBSuper":
        	log("    ## Handling upgrade of HB Supervisor");
	    	strAutoItCommand = "AutoIt3.exe Cac_Upgrade_Super.exe";
	    	strReturn = Utility.executeCommand(strAutoItCommand);
        	break;
        case "exit":
        	log("    ## Handling Exit: I am closing myself after 5 sec.   Bye!!!");
        	wait(5);
        	boolState = false;
        	break;
        case "Kill_all":
        	log("    ## Handling Kill_all: I am killing all components in 5 sec.   Bye!!!");
        	wait(5);
        	Utility.executeCommand("kill_All.bat");
        	//boolState = false;
        	break;
        default:
        	if (logInHandled == 1){
        		//If logIn is handled already on if statement, it is not handled anymore here.
        	}else{
        	   log("    ## Handling ???");
        	}
        }
		return boolState;
    	
    }
    
    //This is active when the message contains "WebAgent"
    public void handleMessageForWebAgent(String strCommand) throws Exception{
    	
    	 String commandMain, commandFinal, hbAgentURL;
    	 String strSplitter;
    	 strSplitter = " ";
    	 log("#### string command is => " + strCommand);
    	 
    	 commandMain = strCommand.split("_")[1];
    	 commandFinal = commandMain.split(strSplitter)[0].trim();
    	 log("commandMain is => " + commandMain);
    	 log("commandFinal is => " + commandFinal);
    	 switch (commandFinal){
         case "login":
        	 if (hbAgent == null){
	 	    	userName = commandMain.split(strSplitter)[1].trim();
	 	    	userPassword = commandMain.split(strSplitter)[2].trim();
	 	    	userDelaySec = commandMain.split(strSplitter)[3].trim();
	 	    	hbAgentURL = commandMain.split(strSplitter)[4].trim();
	         	
	 	    	wait(Integer.parseInt(userDelaySec));
	        	hbAgent = new HBAgent(userName, userPassword, hbAgentURL); 
	        	//hbAgent = new HBAgent("young1", "changeme", "http://10.23.175.161:3000/ecc");
				hbAgent.logIntoWebAgent();
				//hbAgent.releaseAgent();
				hbAgent.resumeAgent();
        	 }else{
        		log("@@@ hbAgent is already started");
        	 }
			
         	
         	break;
         case "logout":
        	 if (hbAgent != null){
	        	 hbAgent.signoutWebAgentWhenNoCall();
	        	 hbAgent.closeHBAgent();
	        	 hbAgent = null;
        	 }

         	break;
         case "resume":
        	 if (hbAgent != null){
	        	 hbAgent.resumeAgent();
        	 }
         	break;
         case "release":
        	 if (hbAgent != null){
	        	 //hbAgent.releaseAgent();
        		 hbAgent.releaseAgentSecondCode();
        	 }
         	break;
         default:
        	 log("I am here at default");
    	 }
    	
    }
}