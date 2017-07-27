package ClassesEntity;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import CCEntity.JasonHBSupervisor;


public class JasonAPITesterClient extends TestBaseObject
{
	//These are read from ini file when initiated.
	protected String ccapiURL, strTicket;  
	protected static URL ccAPIURL_URL;
	
	protected List<String> cookies;
	//protected HttpsURLConnection conn;
	protected BufferedReader bufferedReader;
	protected DataOutputStream wr;
	protected Connection conn;
	
	public JasonAPITesterClient() throws Exception
	{
		super();
		//conn = null;
	}
	
	
	//### This logs into CCAPI and returns ticket while ignoring it.
	public String logIn(JasonHBSupervisor hbSup) throws Exception
	{
		
		String urlParameters, strReturn;
		String[] output;
		URL ccAPIURL_URL;
		ccAPIURL_URL = null;
		conn = AllObjects.connection;
		// make sure cookies is turn on
		CookieHandler.setDefault(new CookieManager());
		
		//########## Ignore all certificates
		TrustAllCertificate(ini.get(sysTT, "ccapiURL"));  //This is needed to trust all kinds of certificate


		//############ Log in and get ticket.
		urlParameters =  "{\"message\":\"authenticate\",\"user\":\"" +hbSup.userName + "\",\"password\":\"" + hbSup.password + "\"}";
		strReturn = sendRequest("LogIn", urlParameters, true);

		//log.info(strReturn);
		output = strReturn.split(",");
		output = output[1].split(":");
		strTicket = output[1];
		conn.strCertificate = strTicket;
		
		return strTicket;
		
	}
	
	public String logIn(String supUserName, String supPassword) throws Exception
	{
		
		String urlParameters, strReturn;
		String[] output;
		URL ccAPIURL_URL;
		ccAPIURL_URL = null;
		conn = AllObjects.connection;
		// make sure cookies is turn on
		CookieHandler.setDefault(new CookieManager());
		
		//########## Ignore all certificates
		TrustAllCertificate(ini.get(sysTT, "ccapiURL"));  //This is needed to trust all kinds of certificate


		//############ Log in and get ticket.
		urlParameters =  "{\"message\":\"authenticate\",\"user\":\"" + supUserName + "\",\"password\":\"" + supPassword + "\"}";
		strReturn = sendRequest("LogIn", urlParameters, true);

		//log.info(strReturn);
		output = strReturn.split(",");
		output = output[1].split(":");
		strTicket = output[1];
		conn.strCertificate = strTicket;
		
		return strTicket;
		
	}
	
	/*public String logIn_Admin() throws Exception
	{
		
		String urlParameters, strReturn;
		String[] output;

		// make sure cookies is turn on
		CookieHandler.setDefault(new CookieManager());
		
		//########## Ignore all certificates
		TrustAllCertificate();  //This is needed to trust all kinds of certificate


		//############ Log in and get ticket.
		urlParameters =  "{\"message\":\"authenticate\",\"user\":\""+adminUserName+"\",\"password\":\"" + adminPassword + "\"}";
		strReturn = sendRequest("LogIn", urlParameters, true);

		//log.info(strReturn);
		output = strReturn.split(",");
		output = output[1].split(":");
		strTicket = output[1];
		
		return strTicket;
		
	}*/
	
	public String sendRequestJson(String reasonToSend, String strJasonMessage) throws Exception
	{
		//############# Request by Jason
		log.info("@ sendRequestJson => (reason):  " + reasonToSend + ": message => " + strJasonMessage);
		String urlParameters, strAck;

		
		urlParameters = "{\"request\": " + strJasonMessage + ", \"ticket\": " + strTicket + "}";
		strAck = sendRequest(reasonToSend, urlParameters, false);		
		return strAck;
	}
	
	public String startPolling(String strReason) throws Exception
	{
		//############# start polling
		log.info("@ StartPolling => " + strReason);
		String urlParameters, strReturn;

		urlParameters = "{\"request\": \"poll\", \"ticket\": " + strTicket + "}";
		strReturn = sendRequest("Start Polling after "+ strReason, urlParameters, true);
		
		return strReturn;
		
	}
	
	public void stopPolling()
	{
		
	}
	
	/**
	 * 
	 * @param reasonToSend  : Reason can be subscribe/polling
	 * @param urlParameters : This is actual string to send
	 * @param closeOrNot  : This argument decides whether close connection or not
	 * @return
	 * @throws Exception
	 */
	 private String sendRequest(String reasonToSend, String urlParameters, boolean closeOrNot) throws Exception 
	 {
		log.info("\n\n######## Reason to SendRequest : " + reasonToSend + " ###########");
		boolean returnTrue;
		returnTrue = true;
	    openConnection(reasonToSend);
	    writeOutputStream(reasonToSend, urlParameters);

	    BufferedReader in = new BufferedReader(
	            new InputStreamReader(conn.httpsConn.getInputStream()));
	    String inputLine;
	    StringBuffer response = new StringBuffer();

	    while ((inputLine = in.readLine()) != null) {
	        response.append(inputLine);
	    }
	    

	    //print result
	    log.info("4) Response of sendRequest ");
	    returnTrue = printStrArray(response.toString(), ",");
 
	    if (closeOrNot == true) {
	    	log.info("#### Close Input Stream on : " + reasonToSend + " ####");
	    	closeInputStream(in);
	    }
		return response.toString();

	  }
	 
	 private static void closeInputStream(Reader reader) {
		    if (reader != null) {
		        try {
		            reader.close();
		        } catch (IOException exp) {
		            log.error(exp);
		            
		        }
		    }
		}
	  
	 
	
	 
	 public static void TrustAllCertificate(String strURL)
	  {
		  log.info("### Trust All certificate ###");
		  
		  //This temporarily disable the hostname check
		  javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
				  new javax.net.ssl.HostnameVerifier(){

				      public boolean verify(String hostname,
				              javax.net.ssl.SSLSession sslSession) {
				          return true;
				      }
				  });
		  
		// Create a trust manager that does not validate certificate chains
		  TrustManager[] trustAllCerts = new TrustManager[] { 
		      new X509TrustManager() {     
		          public java.security.cert.X509Certificate[] getAcceptedIssuers() { 
		              return new java.security.cert.X509Certificate[0];
		          } 
		          public void checkClientTrusted( 
		              java.security.cert.X509Certificate[] certs, String authType) {
		              } 
		          public void checkServerTrusted( 
		              java.security.cert.X509Certificate[] certs, String authType) {
		          }
		      } 
		  }; 

		  // Install the all-trusting trust manager
		  try {
		      SSLContext sc = SSLContext.getInstance("SSL"); 
		      sc.init(null, trustAllCerts, new java.security.SecureRandom()); 
		      HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		  } catch (GeneralSecurityException e) 
		  {
		  } 
		  // Now you can access an https ccAPIURL without having the certificate in the truststore
		  try { 
			  ccAPIURL_URL = new URL(strURL); 
		  } catch (MalformedURLException e) 
		  {
		  }
		  finally{
		  }
		  
	  }
	  
	  public static void TrustAllCertificate_old2(String strURL)
	  {
		  log.info("### Trust All certificate ###");
		// Create a trust manager that does not validate certificate chains
		  TrustManager[] trustAllCerts = new TrustManager[] { 
		      new X509TrustManager() {     
		          public java.security.cert.X509Certificate[] getAcceptedIssuers() { 
		              return new java.security.cert.X509Certificate[0];
		          } 
		          public void checkClientTrusted( 
		              java.security.cert.X509Certificate[] certs, String authType) {
		              } 
		          public void checkServerTrusted( 
		              java.security.cert.X509Certificate[] certs, String authType) {
		          }
		      } 
		  }; 

		  // Install the all-trusting trust manager
		  try {
		      SSLContext sc = SSLContext.getInstance("SSL"); 
		      sc.init(null, trustAllCerts, new java.security.SecureRandom()); 
		      HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		  } catch (GeneralSecurityException e) 
		  {
		  } 
		  // Now you can access an https ccAPIURL without having the certificate in the truststore
		  try { 
		      URL ccAPIRUL = new URL("https://hostname/index.html"); 
		  } catch (MalformedURLException e) 
		  {
		  } 
	  }
	  
	  

	  public List<String> getCookies() 
	  {
		return cookies;
	  }

	  public void setCookies(List<String> cookies) 
	  {
		this.cookies = cookies;
	  }
		  
	  private void openConnection(String reasonToSend) throws IOException
	  {
/*		  if (conn.httpsConn != null)
		  {
			  log.info("### Connection is already established");
			  return;
		  }
		  */
		  log.info("### opening Connection on " + reasonToSend + " :  ###");
		  //URL obj = new URL(ccapiURL);
		  //URL obj = new URL("https://ccapi1.eccqac1.com/");

		  conn.httpsConn= (HttpsURLConnection) ccAPIURL_URL.openConnection();

		  // default is GET
		  conn.httpsConn.setRequestMethod("POST");
			
		  //add reuqest header
		  conn.httpsConn.setRequestMethod("POST"); 
		  conn.httpsConn.setRequestProperty("Content-Type", "application/json");
		  conn.httpsConn.setRequestProperty("charset", "UTF-8");
			
		  // Send post request
		  conn.httpsConn.setDoOutput(true);
		 
		  

	  }
	  
	  /**
	   * This sends subscription to CC server
	   * @param reasonToSend  : This can be subscription or polling
	   * @param urlParameters  : Actual string to send
	   * @throws IOException
	   */
	  private void writeOutputStream(String reasonToSend, String urlParameters) throws IOException
	  {
		  log.info("### Write Outputstream on " + reasonToSend + " :  ###");
		  int responseCode;
		  try{ 
			  
			  DataOutputStream wr = new DataOutputStream(conn.httpsConn.getOutputStream());
			  wr.writeBytes(urlParameters);
			    
			  log.info("==> Flush/Close Outputstream after writeOutputStream");
			  wr.flush();
			  wr.close();
			  
			  responseCode = conn.httpsConn.getResponseCode();
			  log.info("==> The following is the message sent to Outputstream");
			  log.info("1) CCAPI_URL : " + conn.ccapiURL);
			  log.info("2) Full Jason Message : ");
			  printStrArray(urlParameters, ",");
			  
			  log.info("3) Response Code : " + responseCode);
			  switch (responseCode) {
			  case 200:
				  log.info("===> It means :the response contains a payload.  Good!");
				  break; 
			  case 501:
				  log.info("===> It means : Internal Server error");
				  break;
			  case 502:
				  log.info("===> It means : Bad Gateway");
				  break;
			  case 504:
				  log.info("===> It means : Gateway Timeout error");
				  break; 
			  default:
				  
			  }
		  }catch(Exception e){
			  log.error("Problem on writeout ==> " + e.toString());
			  
		  }
	  }
	  
	


}
