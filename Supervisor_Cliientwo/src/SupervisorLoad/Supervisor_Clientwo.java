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

import org.sikuli.script.Screen;




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
public class Supervisor_Clientwo extends TestObject{

	int commandNum, screenshotNum_start, screenshotNum_end;
	String originalScenario;
    BufferedReader in;
    PrintWriter out;
    JFrame frame = new JFrame("Supervisor_Client");
    JTextField textField = new JTextField(90);
    JTextArea messageArea = new JTextArea(8, 90);
    Socket socket;
    String userName, userPassword, userDelaySec; //Default value is "none"
    

    /**
     * Constructs the client by laying out the GUI and registering a
     * listener with the textfield so that pressing Return in the
     * listener sends the textfield contents to the server.  Note
     * however that the textfield is initially NOT editable, and
     * only becomes editable AFTER the client receives the NAMEACCEPTED
     * message from the server.
     */
    public Supervisor_Clientwo() {

    	commandNum=1;
    	screenshotNum_start = 100;
    	screenshotNum_end = 100;
    	originalScenario ="original";
    	userName = "none";
    	userPassword = "none";
    	userDelaySec = "none";
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
    	strName = (String) Utility.ini.get("SuperLoadServer_Config", "serverIPAddress");;
    	return strName;
    }
    
    private int getPort() {
    	int intPort;
    	intPort = Integer.parseInt(Utility.ini.get("SuperLoadServer_Config", "port"));
    	return intPort;
    }
    
    private String getScreenshotFolder( ) {
    	String sFolder;
    	sFolder = Utility.ini.get("SuperLoadServer_Config",  "screenshot_folder");
    	log.info("foldername is => "+ sFolder);
    	return sFolder;
    }
    
    

    /**
     * Prompt for and return the desired screen name like ipaddress_username 10.23.177.888_admin
     */
    private String getName() {
    	String strName;
    	strName = "NA";
    	try {
    		strName =  InetAddress.getLocalHost().toString();
    		strName = strName.split("/")[1]; //just to show ip address instead of both hostname/ip
    		strName = strName + "_" + System.getProperty("user.name");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return strName;
    }
    
    

    /**
     * Connects to the server then enters the processing loop.
     * @throws Exception 
     */
    void run_old() throws Exception {

        // Make connection and initialize streams
    	boolean state;
    	state = true;
    	String strCommandNum = Integer.toString(commandNum);
    	try{
    		Utility.read_ConfigINI();
	        String serverAddress = getServerAddress();
	        int port = getPort();
	        socket = new Socket(serverAddress, port);
	        log.info("Connected to " + serverAddress);
	        in = new BufferedReader(new InputStreamReader(
	            socket.getInputStream()));
	        out = new PrintWriter(socket.getOutputStream(), true); //out is not used for this now
	        
	        
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
	            	state = handleMessage(line.substring(8));
	            	commandNum++;
	            	strCommandNum = Integer.toString(commandNum); 	
	                
	            }
	        }
	        socket.close();
	        //System.exit(1);
    	}catch (ConnectException e){
    		log.info("@ Connection Refused from Server.  Server might not be ready");
    		
    	}
    	catch (IOException e) {
            log.info(e.toString());
        } finally {
        	//socket.close();
        	log.info("## Closing client");
	        System.exit(1);
           
        }
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
    	Utility.read_ConfigINI();
        String serverAddress = getServerAddress();
        int port = getPort();
        
        //when "exit" is received, it closes all, but until then, keeps running.
        while(state) {
        	try{
    	        socket = new Socket(serverAddress, port);
    	        log.info("Connected to " + serverAddress);
    	        in = new BufferedReader(new InputStreamReader(
    	            socket.getInputStream()));
    	        out = new PrintWriter(socket.getOutputStream(), true); //out is not used for this now
    	        
    	        
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
    	            	state = handleMessage(line.substring(8));
    	            	commandNum++;
    	            	strCommandNum = Integer.toString(commandNum); 	
    	                
    	            }
    	        }
        	}catch (ConnectException e){
        		log.info("@ Connection Refused from Server.  Server might not be ready");
       		    textField.setText("### Connection disconnected: Reconnecting... ###" + "\n");
        		wait(5);
        	}
        	catch (IOException e) {
                log.info(e.toString());
            } 
        	
        }

        socket.close();
    	log.info("## Closing client");
        System.exit(1);
        
        //End of run()
    }
    
   
    
    
    protected boolean handleMessage(String strMessage) throws Exception{
    	
    	int logInHandled;
    	boolean boolState;
    	boolState = true;
    	String strCommand, strComCommand;
    	String strReturn;
    	String superName, superPassword, superDelaySec;
    	String strAutoItCommand;
    	log.info("#### HandleMessage => " + strMessage);
    	logInHandled = 0;
    	strCommand = strMessage.split(":")[1].trim();
    	
    	log.info("#### string command is => " + strCommand);
    	
    	if (strCommand.contains("login_NLA_all")){
    		log.info("    ## Handling login");
	    	strComCommand = strCommand.split(" ")[0].trim();
	    	userName = strCommand.split(" ")[1].trim();
	    	userPassword = strCommand.split(" ")[2].trim();
	    	userDelaySec = strCommand.split(" ")[3].trim();
        	//strReturn = Utility.executeCommand("Cac_login.bat" + " " + superName + " " + superPassword);
	    	strAutoItCommand = "AutoIt3.exe Cac_login_Super.exe " + userName + " " + userPassword + " " + userDelaySec;
	    	strReturn = Utility.executeCommand(strAutoItCommand);
        	//log.info("superName is "+ superName  + "/" + superPassword);
        	//log.info("String Return after command => " + strReturn);
        	logInHandled = 1;
        	
        	return boolState;
    	}
    	
    	//all messages for each scenario start like "starting scenario:..." "ending scenario:..."
    	//When scenario is started or ended make a screenshot.
    	//Since this uses a command which contains scenario, it cannot be used on Swtich.case which requires an exact match.
    	if (strCommand.contains("screenshot")){
    		log.info("    ## Handling screenshot");
	    	if (strCommand.contains("starting")) screenshotNum_start++;
	    	screenshotNum_end++;
	    	String strScenario = Utility.processCommandForScreenshot(strCommand, getName(), screenshotNum_end);
	    	strScenario = screenshotNum_start + "_" + strScenario;
	    	strScenario = getScreenshotFolder() + strScenario;
	    	Utility.captureScreem(strScenario);
	    	if (strCommand.contains("ending")) screenshotNum_end=100;
	    	logInHandled = 1;
	    	
    	}
    	
    	
    	if (strCommand.contains("record")){
    		
	    	//String strScenario = UtilityRecordScreen.processCommandForRecording(strCommand);
	    	if (strCommand.contains("starting")) {		
	    		//log.info(" ## Start Recording on => " + strScenario);
		    	UtilityRecordScreen.startRecording();

		    //another message only arrive with "ending".
	    	}else{
	    		//log.info(" ## Stop Recording on=> " + strScenario);
	    		String strScenario = UtilityRecordScreen.processCommandForRecording(strCommand);
	    		UtilityRecordScreen.stopRecording();
	    		strScenario = strScenario + "_" + getName();
	    		strScenario = screenshotNum_start + "_" + strScenario;
		    	UtilityRecordScreen.enterFileNameAndSave(strScenario);
		    	screenshotNum_start++;	
	    	}
	    	
	    	return boolState;
	    	
    	}
    	
        switch (strCommand){
        case "clearMessage":
        	//This cleans message textBox
    		messageArea.selectAll();
    		messageArea.replaceSelection("");
    		commandNum = 0;
    		//This resets screenshot and record number
    		screenshotNum_start = 100;
        	screenshotNum_end = 100;
        	logInHandled = 1;

        	break;
        case "relogin_NLA":
        	if (userName.contains("none")) {
        		log.info("Something wrong on relogin_NLA_all");
        	} else {
	        	strAutoItCommand = "AutoIt3.exe Cac_login_Super.exe " + userName + " " + userPassword + " " + userDelaySec;
		    	strReturn = Utility.executeCommand(strAutoItCommand);
	        	log.info("superName is "+ userName  + "/" + userPassword  + "/" + userPassword);
	        	//log.info("String Return after command => " + strReturn);
	        	logInHandled = 1;
        	}
        	break;
        case "logout":
        	log.info("    ## Handling logout");
	    	strAutoItCommand = "AutoIt3.exe Cac_logout_Super.exe";
	    	strReturn = Utility.executeCommand(strAutoItCommand);
        	//log.info("superName is "+ superName  + "/" + superPassword);
        	//log.info("String Return after command => " + strReturn);
        	
        	break;
        case "Open_FLA":
        	log.info("    ## Opening FLA(Historical Report)");
	    	strAutoItCommand = "AutoIt3.exe Cac_FLA_Open.exe";
	    	strReturn = Utility.executeCommand(strAutoItCommand);
        	break;
        case "Close_FLA":
        	log.info("    ## Closing FLA(Historical Report)");
	    	strAutoItCommand = "AutoIt3.exe Cac_FLA_Close.exe";
	    	strReturn = Utility.executeCommand(strAutoItCommand);
        	break; 
        case "Open_GCCS":
        	log.info("    ## Opening GCCS(Historical Report)");
	    	strAutoItCommand = "AutoIt3.exe Cac_GCCS_Open.exe";
	    	strReturn = Utility.executeCommand(strAutoItCommand);
        	break;
        case "Close_GCCS":
        	log.info("    ## Closing GCCS(Historical Report)");
	    	strAutoItCommand = "AutoIt3.exe Cac_GCCS_Close.exe";
	    	strReturn = Utility.executeCommand(strAutoItCommand);
        	break; 	
        
        case "Upgrade_HBSuper":
        	log.info("    ## Handling upgrade of HB Supervisor");
	    	strAutoItCommand = "AutoIt3.exe Cac_Upgrade_Super.exe";
	    	strReturn = Utility.executeCommand(strAutoItCommand);
        	break;
        case "exit":
        	log.info("    ## Handling Exit: I am closing myself after 5 sec.   Bye!!!");
        	wait(5);
        	boolState = false;
        	break;
        case "Kill_all":
        	log.info("    ## Handling Kill_all: I am killing all components in 5 sec.   Bye!!!");
        	wait(5);
        	Utility.executeCommand("kill_All.bat");
        	//boolState = false;
        	break;
        default:
        	if (logInHandled == 1){
        		//If logIn is handled already on if statement, it is not handled anymore here.
        	}else{
        	   log.info("    ## Handling ???");
        	}
        }
		return boolState;
    	
    }
    
   
    
    /**
     * Runs the client as an application with a closeable frame.
     */
    public static void main(String[] args) throws Exception {
        Supervisor_Clientwo client = new Supervisor_Clientwo();
        client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.frame.setVisible(true);
        client.run();
        client.frame.setVisible(false);
    }
}