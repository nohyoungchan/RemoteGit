package SupervisorLoad;



import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import org.ini4j.Wini;
import HBTestesNG.BaseObjects.AllActors;
import HBTestesNG.BaseObjects.TestObject;




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
	ArrayList<String> commandHistory;
    BufferedReader in;
    PrintWriter out;
    int intHistory;
    private static Wini iniMain = AllActors.iniMain;
    
    public Supervisor_Admin() {
    	try {
    		startSuperAdmin();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	commandHistory = new ArrayList<String>();
    	intHistory = 0;
    	
    }
 
    
    public void sendMessage(String command) {
    	log.info("$$ Supervisor_Admin: command==> " + command + " : " + returnTime());
    	out.println(command);
    	commandHistory.add(command);
    	intHistory++;
    	wait(1);
    }
    
    public void printAllMessages() {
    	log.info("#### Supervisor_Admin: printing all command history ");
    	log.info("==> Total command sent : " + commandHistory.size());
    	int i = 1;
    	for(String command: commandHistory) {
    		log.info(i + ") " + command);
    		i++;
    	}
    }


    /**
     * Connects to the server then enters the processing loop.
     */
    public void run(){

    	try {
    		startSuperAdmin();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
    
  
    
    @SuppressWarnings("resource")
	private void startSuperAdmin() throws Exception{
		int port;
		Socket socket;
        try {
	
        	log.info("$$$$$$ Supervisor_admin: Starting...$$$$");
            String serverAddress = iniMain.get("SupervisorLoad", "superLoadServerIP");
            port = Integer.parseInt(iniMain.get("SupervisorLoad", "port"));
            socket = new Socket(serverAddress, port);
            logConsole("Connected to " + serverAddress);
            in = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            
            while (true) {
                String line = in.readLine();
                logConsole("$$ SuperloadAdmin:  Line from server  is ===> " + line);
                if (line.startsWith("SUBMITNAME")) {
                    out.println("Supervisor_Admin");
                    break;
                } 
              }
            
        } finally {

        }
	
    }

}