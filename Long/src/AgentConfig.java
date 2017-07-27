import java.net.URL;

public class AgentConfig{
	
	public AgentConfig(String aUsername, String aPassword, URL aEngineURL, URL aTargetURL) {
		try {
			m_username = aUsername;
			m_password = aPassword;
			m_jsEngineURL = aEngineURL;
			m_jsTargetURL = aTargetURL;
	
		}
		catch (Exception e) {
		
		}
	}
		
	public String getUsername() {
		return (m_username);
	}
	public URL getEngineURL() {
		return (m_jsEngineURL);
	}
	public URL getTargetURL() {
		return (m_jsTargetURL);
	}
	public String getPassword() {
		return (m_password);
	}
	
	public void print() {
		System.out.println("Username: " + m_username);
		System.out.println("password: " + m_password);
		System.out.println("TargetURL: " + m_jsTargetURL);
		System.out.println("EngineURL: " + m_jsEngineURL);
		
	}
	
	private String m_username;
	private URL m_jsEngineURL;
	private URL m_jsTargetURL;
	private String m_password;
	
} //end of Class Agent

