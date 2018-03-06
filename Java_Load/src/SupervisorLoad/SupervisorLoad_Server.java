package SupervisorLoad;



import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Hashtable;
import org.ini4j.Wini;
import BaseObjects.AllActors;
import BaseObjects.TestObject;


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
public class SupervisorLoad_Server extends TestObject{


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
    private static Wini iniMain = AllActors.iniMain;
    //public static ArrayList<SupervisorClient> supClients = AllActors.supClients;
    public static ArrayList<SupervisorLoad_Client> supClients;
    
    public void run() {
    	try {
    		initiateSupervisorClient();
			startSuperLoadServer();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    private void startSuperLoadServer() throws Exception{
		// TODO Auto-generated method stub
		int PORT;
		ServerSocket listener = null;
        try {
        	log.info("$$@@ SuperloadServer: Trying to start");
        	PORT = Integer.parseInt(iniMain.get("SupervisorLoad", "port"));
        	listener = new ServerSocket(PORT);
        	log.info("$$@@ SuperloadServer: Running at Port => " + PORT);
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
    public static class Handler extends Thread{
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
                	log.info("@@ SuperloadServer: ConcurrentModificationException occurs on Supervisor_Server.java. ");
                } catch(Exception e){
                	log.info("@@ SuperloadServer: Exception occurs on Supervisor_Server.java. ");
                }
                
                adminWriterName = "none";
                if (name.equals("Supervisor_Admin")){
                	adminWriterName = out.toString();
                	log.info("Supervisor_Admin writername is : " + adminWriterName);
                }
                

                // Accept messages from this client and broadcast them.
                // Ignore other clients that cannot be broadcasted to.
                String input, inputOriginal;
                int intSecBeforeLogout, numNLA;
                String retString = "no";
                //intSecBeforeLogout= Integer.parseInt(iniMain.get("SupervisorLoad", "intSecBeforeLogout"));
                while (true) {
                    input = in.readLine();

                    if (input == null) {
                        return;
                    }

                    //if name is not admin, go back to while loop
                    if (!name.equals("Supervisor_Admin")) continue;
                    
                    //only admin can broadcast to all
            		log.info("@@ Supervisor_Server:  Command is ==> " + input + " : " + returnTime() + " @@@@@@");
            		inputOriginal = input;
            		//input = input.split("#")[0].trim();  //This is when using GUI.
            		input = input.trim();

            		switch (input){
            		//This is to send login_NLA_all command to limited number of clients.
            		
            		
            		case "login_NLA_num":
            			numNLA = Integer.parseInt(inputOriginal.split("#")[1].trim());
            			input= "login_NLA_all";
            			SendMessagesToNumOfClientWithSpecfic(adminWriterName, name, input, numNLA);
            			break;
            		case "login_NLA_num_repeat":
            			numNLA = Integer.parseInt(inputOriginal.split("#")[1].trim());
            			intSecBeforeLogout= Integer.parseInt(iniMain.get("SupervisorLoad", "intSecBeforeLogout"));
            			retString = "no";
            			j = 1;
            			while (retString.contains("no")){//repeat until retString becomes "yes"
            				log.info("**** " + j + "=> Time login_NLA_num_repeat " + " : " + returnTime());
            				
                			log.info("@@ SuperloadServer: login_NLA_all");
                			input= "login_NLA_all";
                			SendMessagesToNumOfClientWithSpecfic(adminWriterName, name, input, numNLA);
                			
                			//sleep(intSecBeforeLogout *1000);
                			waits(intSecBeforeLogout, "Wait before logout");
                			
                			
                			log.info("####Logging out all NLA " + " : " + returnTime());
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
        				
            			log.info("@@ SuperloadServer: login_NLA_all " + " : " + returnTime());
            			input= "login_NLA_all";
            			intSecBeforeLogout= Integer.parseInt(iniMain.get("SupervisorLoad", "intSecBeforeLogout"));
            			SendMessagesToClientWithSpecific(adminWriterName, name, input);
                		
                		//sleep(intSecBeforeLogout *1000);
                		waits(intSecBeforeLogout, "Wait before logout");
                		
                		log.info("####Logging out all NLA " + " : " + returnTime());
            			input= "logout";
            			SendMessagesToClient(adminWriterName, name, input);
            			
            			waits(20, "Wait after logout");
            			retString = "no";
            			j = 1;
            			while (retString.contains("no")){//repeat until retString becomes "yes"
            				log.info("**** " + j + "=> Time relogin_NLA_repeat " + " : " + returnTime());
            				
                			log.info("@@ SuperloadServer: relogin all NLA");
                			input= "relogin_NLA";
                			SendMessagesToClient(adminWriterName, name, input);
                			
                			//sleep(intSecBeforeLogout *1000);
                			waits(intSecBeforeLogout, "Wait before logout");
                			
                			
                			log.info("####Logging out all NLA " + " : " + returnTime());
                			input= "logout";
                			SendMessagesToClient(adminWriterName, name, input);
                			j++; //This is to check how may relogin_NLA_repead has been done.
                			retString = WaitForUserInput(20);
            			}//end of while loop for relogin_NLA_repeat
                			
            			
            			break;
            		case "logout":
            			log.info("####Logging out all NLA ");
            			SendMessagesToClient(adminWriterName, name, input);
        				break;
            		
            		case "server_list":
            			//log.info("### Showing the list of all Clients/Admin ");
        				i = 0;
        				for (String name : names){
        					if(name.equalsIgnoreCase("Supervisor_Admin")) continue;
        					i++;
                			logConsole(i + ") Client Name : " + name);
                		}
        				log.info("@@@@@@ End of Command ==> " + input + " : " + returnTime()+ " @@@@@@");
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
            			log.info("I am here kill all");
            			SendMessagesToAdmins(adminWriterName, name, input);
            			sleep(100);
            		break;
            		
            		default: //upgrade, logout, exit, hello, relogin, etc...
            			SendMessagesToClient(adminWriterName, name, input);
            		
            		}
                }
            } catch(SocketException e){
            	log.info("@ Connection Reset on => "+ name);
            } catch (IOException e) {
                System.out.println(e);
            } catch (InterruptedException e) {
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
                    log.info(" ==> Socket is disconnected on => " + name);
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
			log.info(i + ") Client Name : " + writerIDAndName.get(writer.toString()) + " => " + input);
			if (input.contains("record")) 
			{
			   waits(5, "for sending to another client since they might have a conflict with recording");
			}

		}
		
		log.info("@@@@@@@@ End of Command ==> " + input + " : " + returnTime() + " @@@@@@");
    	
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
				logConsole(i + ") Client Name : " + writerIDAndName.get(writer.toString()) + " => " + input);
			}
			

		}
		
		log.info("@@@@@@@@ End of Command ==> " + input + " : " + returnTime() + " @@@@@@");
    	
    }
    
    
    
    //This sends message to all clients with name/password/delay time
    private static void SendMessagesToClientWithSpecific(String adminWriterName, String senderName, String input){
    	int i;
    	String inputOriginal;
    	String clientName, clientPassword, clientDelaySec;
    	SupervisorLoad_Client supClient;
    	
    	//senderName is admin
    	i= 0;
		inputOriginal = input;  //This is not to append existing clients.
		for (PrintWriter writer : writers){
			if(adminWriterName.contains(writer.toString())) continue;
			supClient = supClients.get(i);

         	clientName = supClient.getName();
         	clientPassword = supClient.getPassword();
         	clientDelaySec = supClient.getDelaySec();
         	input = inputOriginal + " " + clientName + " " + clientPassword + " " + clientDelaySec;
         	writer.println("MESSAGE " + senderName + ": " + input);
         	
         	i++;
         	input = inputOriginal;
         	logConsole(i+") ## Sent Message to "+ clientName + " / " + clientPassword + " / " + clientDelaySec + " (by: " + senderName + ": Command=> " + input + ")");
         	
		}
		
		log.info("@@@@@@ End of Command ==> " + input + " : " + returnTime()+ " @@@@@@");
    	
    }
    
    
    //This sends message to all clients with name/password/delay time
    private static void SendMessagesToNumOfClientWithSpecfic(String adminWriterName, String senderName, String input, int maxNumOfClients){
    	int i;
    	String inputOriginal;
    	String clientName, clientPassword, clientDelaySec;
    	SupervisorLoad_Client supClient;
    	
    	//senderName is admin
    	i= 0;
		inputOriginal = input;  //This is not to append existing clients.
		for (PrintWriter writer : writers){
			if(adminWriterName.contains(writer.toString())) continue;
			supClient = supClients.get(i);

         	clientName = supClient.getName();
         	clientPassword = supClient.getPassword();
         	clientDelaySec = supClient.getDelaySec();
         	input = inputOriginal + " " + clientName + " " + clientPassword + " " + clientDelaySec;
         	writer.println("MESSAGE " + senderName + ": " + input);
         	
         	i++;
         	input = inputOriginal;
         	logConsole(i+") ## Sent Message to "+ clientName + " / " + clientPassword + " / " + clientDelaySec + " ( by: " + senderName + ": Command=> " + input + ")");
         	
         	if (i >= maxNumOfClients) break;
		}
		
		log.info("@@@@@@ End of Command ==> " + input + " : " + returnTime()+ " @@@@@@");
    	
    }
    
    /**
	 * This reads from testDataSuperList.csv and initiate SupervisorClient.
	 * SupervisorClient is used to communicate with remote agent manager.
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void initiateSupervisorClient() throws FileNotFoundException, IOException{
	    int i, j;
	    String configDir;
	    configDir = AllActors.configDir;
	    
	    log.info("@@ SuperloadServer: Reading testDataSuperList.csv file  for supervisors");

        String csvFile = configDir + "testDataSuperList.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {
        	supClients = new ArrayList<SupervisorLoad_Client>();
            br = new BufferedReader(new FileReader(csvFile));
            i = 0; j =0;
            while ((line = br.readLine()) != null) {
            	if(i ==0){
            		i++;
            		continue;
            	}
            	
            	String[] superProperty = line.split(cvsSplitBy);
            	//log("Line input is => " + line);
	        	supClients.add(new SupervisorLoad_Client());
	        	if (superProperty.length > 2)  //The length should be 3
	        	{ 	
	        		supClients.get(j).name = superProperty[0];
	        		supClients.get(j).password = superProperty[1];
	        		supClients.get(j).delaySec = superProperty[2];
	        	}
	        	i++;j++;
            }
            
            

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

	}
	
    
 
}
