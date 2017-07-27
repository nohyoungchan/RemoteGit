package ClassesEntity;

import javax.net.ssl.HttpsURLConnection;

public class Connection {
	protected String username, password; 
	protected String ccapiURL;
	protected String strCertificate;
	protected HttpsURLConnection httpsConn;
	
	Connection(){
		httpsConn = null;
	}

}
