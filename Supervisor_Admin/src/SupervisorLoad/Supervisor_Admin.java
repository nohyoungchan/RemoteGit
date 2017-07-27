package SupervisorLoad;


import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

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
public class Supervisor_Admin extends TestObject{
	int commandNum;
	String[] commandList;
    BufferedReader in;
    PrintWriter out;
    JFrame frame = new JFrame("SupervisorAdmin");
    JTextField txtFieldNLANum = new JTextField(20);
    JComboBox comboBox;
    JTextArea messageArea = new JTextArea(16, 30);
    JButton submitBtn = new JButton("Submit");
    JButton helpBtn = new JButton("Help");
    
    final static String nl = "\n";
    
    

    /**
     * Constructs the client by laying out the GUI and registering a
     * listener with the txtFieldNLANum so that pressing Return in the
     * listener sends the txtFieldNLANum contents to the server.  Note
     * however that the txtFieldNLANum is initially NOT editable, and
     * only becomes editable AFTER the client receives the NAMEACCEPTED
     * message from the server.
     * @throws IOException 
     * @throws FileNotFoundException 
     */
    public Supervisor_Admin() {

        // Layout GUI
    	commandNum=1;

    	//Utility.readConfigFile();
    	Utility.read_ConfigINI();
    	commandList = getCommandList();
    	
        //txtFieldNLANum.setEditable(false);
    	comboBox = new JComboBox(commandList);
        messageArea.setEditable(false);
        messageArea.insert("##### Connected to : "+ getServerAddress() + "/" + getPort() + " ######\n", 0);
        
        txtFieldNLANum.setEditable(false);
        txtFieldNLANum.setText("all");

        Container content = frame.getContentPane();

        JPanel resultPanel = new JPanel(new GridLayout(0,1));
        resultPanel.add(comboBox);
        resultPanel.add(txtFieldNLANum);
        content.add(resultPanel, "North");

        content.add(new JScrollPane(messageArea), "Center");
        
        
        JPanel btnPanel = new JPanel(new GridLayout(1,0));
        btnPanel.add(submitBtn);
        btnPanel.add(helpBtn);
        content.add(btnPanel, "South");
        //content.add(submitBtn, "South");
        frame.pack();

        // Add Listeners
        comboBox.addActionListener(new ActionListener() {
        	
        	 @Override
             public void actionPerformed(ActionEvent e) {
                 // Do something when you select a value
        		 String strSelected = (String) comboBox.getSelectedItem();
        		 if (strSelected.contains("login_NLA_num") || strSelected.contains("WebAgent_login_num")){
        			 txtFieldNLANum.setText("1");
        			 txtFieldNLANum.setEditable(true);
             		   
             	   }else{
             		  txtFieldNLANum.setText("all");
             		  txtFieldNLANum.setEditable(false);
             	   }

             }
        });
        
        submitBtn.addActionListener(new ActionListener() {
           /**
            * Responds to pressing the enter key in the txtFieldNLANum by sending
            * the contents of the text field to the server.    Then clear
            * the text area in preparation for the next message.
            */
           public void actionPerformed(ActionEvent e) {
           	   String strCommandNum = Integer.toString(commandNum);
           	   String strSelectedComboBox = (String) comboBox.getSelectedItem();
           	   String strSelectedTxtFieldNLANum = (String) txtFieldNLANum.getText();
           	   String commandToSend = strSelectedComboBox  + "#" + strSelectedTxtFieldNLANum;
           	   
           	    commandToSend = strSelectedComboBox  + "#" + strSelectedTxtFieldNLANum;
           	   
         /*  	   if (strSelectedComboBox .contains("login_NLA_num")){
           		   commandToSend = strSelectedComboBox  + "#" + strSelectedTxtFieldNLANum;
           		   
           	   }else{
           		   commandToSend = strSelectedComboBox ;
           	   }*/
           	   
           	   out.println(commandToSend);
           		   
               //admin doesn't get message from server, so add by itself.
               //messageArea.insert(strCommandNum + ")  " + strSelected +"\n", 1);
               messageArea.append(strCommandNum + ")  " + commandToSend +"\n");
               commandNum++;
               //txtFieldNLANum.setText("");

           }
       });
        
        helpBtn.addActionListener(new ActionListener() {
            /**
             * Responds to pressing the enter key in the txtFieldNLANum by sending
             * the contents of the text field to the server.    Then clear
             * the text area in preparation for the next message.
             */
            public void actionPerformed(ActionEvent e) {
            	String[] strHelpList;
            	JFrame frameHelp = new JFrame ("Help");
            	JTextArea helpTextArea = new JTextArea(30, 50);
            	helpTextArea.setEditable(false);
            	
            	strHelpList = getHelpContent();
            	for(int i=0 ; i < strHelpList.length; i++){
            		helpTextArea.append(strHelpList[i] + nl);
            	}

                //frameHelp.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
                frameHelp.getContentPane().add (new JScrollPane(helpTextArea));
                frameHelp.pack();
                frameHelp.setVisible (true);

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
    	//strName = (String) Utility.globalVariableHash.get("serverIPAddress");
    	strName = Utility.ini.get("SuperLoadServer_Config", "serverIPAddress");
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
    	//strName = "NA";
    	//strName =  InetAddress.getLocalHost().toString();
		//strName = strName.split("/")[1]; //just to show ip address instead of both hostname/ip
		strName = "admin";  //Since this is admin 
       /* return JOptionPane.showInputDialog(
            frame,
            "Choose a screen name:",
            "Screen name selection",
            JOptionPane.PLAIN_MESSAGE);*/
		return strName;
    }
    
    private String[] getCommandList() {
    	String strResult;
    	String[] strList;
    	strResult = (String) Utility.ini.get("Test_Config","commandList");
    	strList = strResult.split(";");
    	
    	return strList;
    }
    
    public String[] getHelpContent(){
    	String strHelp;
    	String[] strHelpList;
        strHelp = Utility.ini.get("Help", "commandHelp");
        strHelpList = strHelp.split(";");
        return strHelpList;
    }


    /**
     * Connects to the server then enters the processing loop.
     */
    private void run() throws IOException {

        // Make connection and initialize streams
    	int port;
    	
        String serverAddress = getServerAddress();
        port = getPort();
        Socket socket = new Socket(serverAddress, port);
        log("Connected to " + serverAddress);
        in = new BufferedReader(new InputStreamReader(
            socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        // Process all messages from server, according to the protocol.
        while (true) {
            String line = in.readLine();
            log("$$$$$$ Line from server  is ===> " + line);
            if (line.startsWith("SUBMITNAME")) {
                out.println(getName());
            } else if (line.startsWith("NAMEACCEPTED")) {
            	comboBox.setEditable(false);
            } else if (line.startsWith("MESSAGE")) {
            	/*if(line.contains("login")){
            		messageArea.insert(line.substring(8) + "\n", 0);
            	}*/

            }
        }
    }

    /**
     * Runs the client as an application with a closeable frame.
     */
    public static void main(String[] args) throws Exception {
        Supervisor_Admin admin = new Supervisor_Admin();
        admin.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        admin.frame.setVisible(true);
        admin.run();
    }

    
}