package SupervisorLoad;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Hashtable;

/**
 * A multithreaded chat room server.  When a client connects the
 * server requests a screen name by sending the client the
 * text "SUBMITNAME", and keeps requesting a name until
 * a unique one is received.  After a client submits a unique
 * name, the server acknowledges with "NAMEACCEPTED".  Then
 * all messages from that client will be broadcast to all other
 * clients that have submitted a unique screen name.  The
 * broadcast messages are prefixed with "MESSAGE ".
 *
 * Because this is just a teaching example to illustrate a simple
 * chat server, there are a few features that have been left out.
 * Two are very useful and belong in production code:
 *
 *     1. The protocol should be enhanced so that the client can
 *        send clean disconnect messages to the server.
 *
 *     2. The server should do some logging.
 */
public class Supervisor_Server extends TestObject{

    /**
     * The port that the server listens on.
     */
    private static int PORT;
    private static int totalSuperClientLoggedIn = 0;
    private static int logIntervalSec = 0;  //log interval for each clients
    private static boolean useLogIntervalSec = false;

    /**
     * The set of all names of clients in the chat room.  Maintained
     * so that we can check that new clients are not registering name
     * already in use.
     */
    private static HashSet<String> names = new HashSet<String>();

    /**
     * The set of all the print writers for all the clients.  This
     * set is kept so we can easily broadcast messages.
     */
    private static HashSet<PrintWriter> writers = new HashSet<PrintWriter>();
    private static Hashtable<String, PrintWriter> writerIDAndWriter = new Hashtable<String, PrintWriter>();
    private static Hashtable<String, String> writerIDAndName = new Hashtable<String, String>();
    private static int intSuperClient = 0;

    /**
     * The appplication main method, which just listens on a port and
     * spawns handler threads.
     */
    public static void main(String[] args) throws Exception {
        ServerSocket listener = null;
        try {
        	//Utility.initiateAll_INI();
        	Utility.read_ConfigINI();
        	Utility.initiateAll_CSV();
        	Utility.initiateAllHBAgent_CSV();
        	
        	PORT = Integer.parseInt(Utility.ini.get("SuperLoadServer_Config", "port"));
        	listener = new ServerSocket(PORT);
        	log("@@@ The Supervisor Load server is running at Port => " + PORT);
            while (true) {
            	//This will create a Handler for each connection.
            	//There could be hundres of Handlers
                new Handler(listener.accept()).start();
            }

            
        } finally {
        	listener.close();
        }
    }

    /**
     * A handler thread class.  Handlers are spawned from the listening
     * loop and are responsible for a dealing with a single client
     * and broadcasting its messages.
     */
    private static class Handler extends Thread{
        private String name;
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;

        /**
         * Constructs a handler thread, squirreling away the socket.
         * All the interesting work is done in the run method.
         */
        public Handler(Socket socket) {
            this.socket = socket;
        }

        /**
         * Services this thread's client by repeatedly requesting a
         * screen name until a unique one has been submitted, then
         * acknowledges the name and registers the output stream for
         * the client in a global set, then repeatedly gets inputs and
         * broadcasts them.
         */
        public void run() {
        	int i, j;
        	String adminWriterName;
        	String clientName, clientPassword, clientDelaySec;
        	SupervisorClient supClient;

            try {
                // Create character streams for the socket.
                in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                // Request a name from this client.  Keep requesting until
                // a name is submitted that is not already used.  Note that
                // checking for the existence of a name and adding the name
                // must be done while locking the set of names.
                while (true) {
                    out.println("SUBMITNAME");
                    name = in.readLine();
                    if (name == null) {
                        return;
                    }
                    synchronized (names) {
                        if (!names.contains(name)) {
                            names.add(name);
                            break;
                        }
                    }
                }

                // Now that a successful name has been chosen, add the
                // socket's print writer to the set of all writers so
                // this client can receive broadcast messages.
                out.println("NAMEACCEPTED");
                
                //ConcurrentModificationException happens when adding/removing from listarray
                //while accessing the array concurrently by several threads.
                try{
	                writers.add(out);
	                writerIDAndWriter.put(out.toString(), out);
	                writerIDAndName.put(out.toString(), name);
                } catch(ConcurrentModificationException e){
                	log("@@ ConcurrentModificationException occurs on Supervisor_Server.java. ");
                } catch(Exception e){
                	log("@@ Exception occurs on Supervisor_Server.java. ");
                }
                
                adminWriterName = "none";
                if (name.equals("admin")){
                	adminWriterName = out.toString();
                	log("admin writername is : " + adminWriterName);
                }
                

                // Accept messages from this client and broadcast them.
                // Ignore other clients that cannot be broadcasted to.
                String input, inputOriginal;
                int intSecBeforeLogout, intSecReleaseResume, numNLA;
                String retString = "no";
                //intSecBeforeLogout= Integer.parseInt(Utility.ini.get("Test_Config", "intSecBeforeLogout"));
                while (true) {
                    input = in.readLine();

                    if (input == null) {
                        return;
                    }
                    
                    //if name is not admin, go back to while loop
                    if (!name.equals("admin")) continue;
                    
                    //only admin can broadcast to all
            		log("@@@@@@ Command is ==> " + input + " : " + currentTime() + " @@@@@@");
            		inputOriginal = input;
            		input = input.split("#")[0].trim();

            		switch (input){
            		//This is to send login_NLA_all command to limited number of clients.
            		case "WebAgent_login_num":
            			input= "WebAgent_login";
            			if (inputOriginal.split("#")[1].trim().contains("all")){
            				SendMessagesToAllOfHBAgent(adminWriterName, name, input);
            			}else{
	            			numNLA = Integer.parseInt(inputOriginal.split("#")[1].trim());
	            			SendMessagesToNumOfHBAgentWithSpecfic(adminWriterName, name, input, numNLA);
            			}
            			break;
            		case "WebAgent_logout":
            			SendMessagesToClient(adminWriterName, name, input);
            			break;
            		case "WebAgent_resume":
            			SendMessagesToClient(adminWriterName, name, input);
            			break;
            		case "WebAgent_release":
            			SendMessagesToClient(adminWriterName, name, input);
            			break;
            		case "WebAgent_releaseResume_repeat":
            			intSecReleaseResume = Integer.parseInt(Utility.ini.get("Test_Config", "intSecReleaseResume"));
            			retString = "no";
            			j = 1;
            			while (retString.contains("no")){//repeat until retString becomes "yes"
            				
                			log("#### WebAgent_release " + " : " + currentTime());
                			input= "WebAgent_release";
                			SendMessagesToClient(adminWriterName, name, input);
                			
                			waits(intSecReleaseResume, "Wait before resume");
                			
                			
                			log("####WebAgent_resume " + " : " + currentTime());
                			input= "WebAgent_resume";
                			SendMessagesToClient(adminWriterName, name, input);
                			j++; //This is to check how may relogin_NLA_repead has been done.
                			waits(intSecReleaseResume, "Wait before release");
                			retString = WaitForUserInput(20);
            			}//end of while loop for relogin_NLA_repeat
            			break;
            		case "login_NLA_num":
            			numNLA = Integer.parseInt(inputOriginal.split("#")[1].trim());
            			input= "login_NLA_all";
            			SendMessagesToNumOfClientWithSpecfic(adminWriterName, name, input, numNLA);
            			break;
            		case "login_NLA_num_repeat":
            			numNLA = Integer.parseInt(inputOriginal.split("#")[1].trim());
            			intSecBeforeLogout= Integer.parseInt(Utility.ini.get("Test_Config", "intSecBeforeLogout"));
            			retString = "no";
            			j = 1;
            			while (retString.contains("no")){//repeat until retString becomes "yes"
            				log("**** " + j + "=> Time login_NLA_num_repeat " + " : " + currentTime());
            				
                			log("#### login_NLA_all");
                			input= "login_NLA_all";
                			SendMessagesToNumOfClientWithSpecfic(adminWriterName, name, input, numNLA);
                			
                			//sleep(intSecBeforeLogout *1000);
                			waits(intSecBeforeLogout, "Wait before logout");
                			
                			
                			log("####Logging out all NLA " + " : " + currentTime());
                			input= "logout";
                			SendMessagesToClient(adminWriterName, name, input);
                			j++; //This is to check how may relogin_NLA_repead has been done.
                			retString = WaitForUserInput(20);
            			}//end of while loop for relogin_NLA_repeat
                			
            			break;
            		case "login_NLA_all":
            			SendMessagesToClientWithSpecific(adminWriterName, name, input);
            			//this is for suchi
            			/*for(int k=0;k<2048;k++){
            				System.out.print("a");
            			}*/
            			break;
            		case "relogin_NLA_repeat":
        				
            			log("#### login_NLA_all " + " : " + currentTime());
            			input= "login_NLA_all";
            			intSecBeforeLogout= Integer.parseInt(Utility.ini.get("Test_Config", "intSecBeforeLogout"));
            			SendMessagesToClientWithSpecific(adminWriterName, name, input);
                		
                		//sleep(intSecBeforeLogout *1000);
                		waits(intSecBeforeLogout, "Wait before logout");
                		
                		log("####Logging out all NLA " + " : " + currentTime());
            			input= "logout";
            			SendMessagesToClient(adminWriterName, name, input);
            			
            			waits(20, "Wait after logout");
            			retString = "no";
            			j = 1;
            			while (retString.contains("no")){//repeat until retString becomes "yes"
            				log("**** " + j + "=> Time relogin_NLA_repeat " + " : " + currentTime());
            				
                			log("#### relogin all NLA");
                			input= "relogin_NLA";
                			SendMessagesToClient(adminWriterName, name, input);
                			
                			//sleep(intSecBeforeLogout *1000);
                			waits(intSecBeforeLogout, "Wait before logout");
                			
                			
                			log("####Logging out all NLA " + " : " + currentTime());
                			input= "logout";
                			SendMessagesToClient(adminWriterName, name, input);
                			j++; //This is to check how may relogin_NLA_repead has been done.
                			retString = WaitForUserInput(20);
            			}//end of while loop for relogin_NLA_repeat
                			
            			
            			break;
            		case "logout":
            			log("####Logging out all NLA ");
            			SendMessagesToClient(adminWriterName, name, input);
        				break;
            		
            		case "server_list":
            			//log("### Showing the list of all Clients/Admin ");
        				i = 0;
        				for (String name : names){
        					if(name.equalsIgnoreCase("admin")) continue;
        					i++;
                			log(i + ") Client Name : " + name);
                		}
        				log("@@@@@@ End of Command ==> " + input + " : " + currentTime()+ " @@@@@@");
            			break;
            		case "server_reload":
        				Utility.reload_CSV();
        				Utility.read_ConfigINI();
        				break;
            		case "Open_FLA":
            			SendMessagesToClient(adminWriterName, name, input);
            			sleep(100);
            		break;
            		case "Open_GCCS":
            			SendMessagesToClient(adminWriterName, name, input);
            			sleep(100);
            		break;
            		case "Kill_all":
            			log("I am here kill all");
            			SendMessagesToAdmins(adminWriterName, name, input);
            			sleep(100);
            		break;
            		
            		default: //upgrade, logout, exit, hello, relogin, etc...
            			SendMessagesToClient(adminWriterName, name, input);
            		
            		}
                }
            } catch(SocketException e){
            	log("@ Connection Reset on => "+ name);
            } catch (IOException e) {
                System.out.println(e);
            } catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
                // This client is going down!  Remove its name and its print
                // writer from the sets, and close its socket.
                if (name != null) {
                    names.remove(name);
                }
                if (out != null) {
                    writers.remove(out);
                    writerIDAndWriter.remove(out.toString());
                    writerIDAndName.remove(out.toString());
                }
                try {
                    socket.close();
                    log(" ==> Socket is disconnected on => " + name);
                } catch (IOException e) {
                }
            }
        }
    }
    
    //This send messages to all
    private static void SendMessagesToClient(String adminWriterName, String senderName, String input){
    	int i;
    	i = 0;
    	
    	//senderName is admin
		for (PrintWriter writer : writers){
			if(adminWriterName.contains(writer.toString())) continue;
			writer.println("MESSAGE " + senderName + ": " + input);
			i++;
			log(i + ") Client Name : " + writerIDAndName.get(writer.toString()) + " => " + input);

		}
		
		log("@@@@@@@@ End of Command ==> " + input + " : " + currentTime() + " @@@@@@");
    	
    }
    
  //This send messages to all _admin
    private static void SendMessagesToAdmins(String adminWriterName, String senderName, String input){
    	int i;
    	i = 0;
    	
    	//senderName is admin for server
    	//Admins is admin for each client
		for (PrintWriter writer : writers){
			if(adminWriterName.contains(writer.toString())) continue;
			if(writerIDAndName.get(writer.toString()).contains("_admin")){
				writer.println("MESSAGE " + senderName + ": " + input);
				i++;
				log(i + ") Client Name : " + writerIDAndName.get(writer.toString()) + " => " + input);
			}
			

		}
		
		log("@@@@@@@@ End of Command ==> " + input + " : " + currentTime() + " @@@@@@");
    	
    }
    
    
    
    //This sends message to all clients with name/password/delay time
    private static void SendMessagesToClientWithSpecific(String adminWriterName, String senderName, String input){
    	int i;
    	String inputOriginal;
    	String clientName, clientPassword, clientDelaySec;
    	SupervisorClient supClient;
    	
    	//senderName is admin
    	i= 0;
		inputOriginal = input;  //This is not to append existing clients.
		for (PrintWriter writer : writers){
			if(adminWriterName.contains(writer.toString())) continue;
			supClient = Utility.supClients.get(i);

         	clientName = supClient.getName();
         	clientPassword = supClient.getPassword();
         	clientDelaySec = supClient.getDelaySec();
         	input = inputOriginal + " " + clientName + " " + clientPassword + " " + clientDelaySec;
         	writer.println("MESSAGE " + senderName + ": " + input);
         	
         	i++;
         	input = inputOriginal;
         	log(i+") ## Sent Message to "+ clientName + " / " + clientPassword + " / " + clientDelaySec + " (by: " + senderName + ": Command=> " + input + ")");
         	
		}
		
		log("@@@@@@ End of Command ==> " + input + " : " + currentTime()+ " @@@@@@");
    	
    }
    
    
    //This sends message to all clients with name/password/delay time
    private static void SendMessagesToNumOfClientWithSpecfic(String adminWriterName, String senderName, String input, int maxNumOfClients){
    	int i;
    	String inputOriginal;
    	String clientName, clientPassword, clientDelaySec;
    	SupervisorClient supClient;
    	
    	//senderName is admin
    	i= 0;
		inputOriginal = input;  //This is not to append existing clients.
		for (PrintWriter writer : writers){
			if(adminWriterName.contains(writer.toString())) continue;
			supClient = Utility.supClients.get(i);

         	clientName = supClient.getName();
         	clientPassword = supClient.getPassword();
         	clientDelaySec = supClient.getDelaySec();
         	input = inputOriginal + " " + clientName + " " + clientPassword + " " + clientDelaySec;
         	writer.println("MESSAGE " + senderName + ": " + input);
         	
         	i++;
         	input = inputOriginal;
         	log(i+") ## Sent Message to "+ clientName + " / " + clientPassword + " / " + clientDelaySec + " ( by: " + senderName + ": Command=> " + input + ")");
         	
         	if (i >= maxNumOfClients) break;
		}
		
		log("@@@@@@ End of Command ==> " + input + " : " + currentTime()+ " @@@@@@");
    	
    }
    
    private static void SendMessagesToNumOfHBAgentWithSpecfic(String adminWriterName, String senderName, String input, int maxNumOfClients){
    	int i;
    	String inputOriginal;
    	String clientName, clientPassword, clientDelaySec, clientHBAgentURL;
    	SupervisorHBAgent supHBAgent;
    	
    	//senderName is admin
    	i= 0;
		inputOriginal = input;  //This is not to append existing clients.
		for (PrintWriter writer : writers){
			if(adminWriterName.contains(writer.toString())) continue;
			supHBAgent = Utility.supHBAgent.get(i);

         	clientName = supHBAgent.getName();
         	clientPassword = supHBAgent.getPassword();
         	clientDelaySec = supHBAgent.getHbDelaySec();
         	clientHBAgentURL = supHBAgent.getHbAgentURL();
         	input = inputOriginal + " " + clientName + " " + clientPassword + " " + clientDelaySec + " " + clientHBAgentURL;
         	writer.println("MESSAGE " + senderName + ": " + input);
         	
         	i++;
         	input = inputOriginal;
         	log(i+") ## Sent Message to "+ clientName + " / " + clientPassword + " / " + clientDelaySec + " ( by: " + senderName + ": Command=> " + input + ")");
         	
         	if (i >= maxNumOfClients) break;
		}
		
	
		
		log("@@@@@@ End of Command ==> " + input + " : " + currentTime()+ " @@@@@@");
    	
    }
    
    private static void SendMessagesToAllOfHBAgent(String adminWriterName, String senderName, String input){
    	int i;
    	String inputOriginal;
    	String clientName, clientPassword, clientDelaySec, clientHBAgentURL;
    	SupervisorHBAgent supHBAgent;
    	
    	//senderName is admin
    	i= 0;
		inputOriginal = input;  //This is not to append existing clients.
		for (PrintWriter writer : writers){
			if(adminWriterName.contains(writer.toString())) continue;
			supHBAgent = Utility.supHBAgent.get(i);

         	clientName = supHBAgent.getName();
         	clientPassword = supHBAgent.getPassword();
         	clientDelaySec = supHBAgent.getHbDelaySec();
         	clientHBAgentURL = supHBAgent.getHbAgentURL();
         	input = inputOriginal + " " + clientName + " " + clientPassword + " " + clientDelaySec + " " + clientHBAgentURL;
         	writer.println("MESSAGE " + senderName + ": " + input);
         	
         	i++;
         	input = inputOriginal;
         	log(i+") ## Sent Message to "+ clientName + " / " + clientPassword + " / " + clientDelaySec + " ( by: " + senderName + ": Command=> " + input + ")");
		}
    }

}
