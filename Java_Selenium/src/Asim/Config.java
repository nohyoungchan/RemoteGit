package Asim;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
//import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.net.MalformedURLException;
import java.util.LinkedList;
import java.util.List;
import java.net.URL;

/*
 * Config class is responsible for loading and storing XML config from a well formatted XML file 
 */
public class Config {
		
	public Config () {	
		m_defaultJSEngineURL = null;
		m_defaultJSTargetURL = null;
		m_defaultUserPassword = null;
		m_agents = null;

	}
	
	public boolean init(String aFilename) {
		
		try {
			if (aFilename == null) {
				System.out.println("Filename is NULL");
				return (false);
			}
			
			System.out.println("\nLoading XML file: \"" + aFilename + "\"");
			File xmlfile = new File(aFilename);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlfile);
			System.out.println("XML file loaded.\nWill parse XML file now ....\n");
			
			//Normalize to ensure we take care of newlines.
			doc.getDocumentElement().normalize();

			Element rootElement = doc.getDocumentElement();
			
			m_agents = new LinkedList<AgentConfig>();
			
			//System.out.println("Root element :" + rootElement.getNodeName());
			return (parseXML(rootElement));
			
		}
		catch (Exception e) {
			e.printStackTrace();
			return (false);
		}
	}
	
	public void print() {
		// Let's print the agent
		
		System.out.println("\n Total Agents: " + m_agents.size() + "\n\n");

		System.out.println("*************************************");
		for (int i = 0; i < m_agents.size(); ++i) {
			m_agents.get(i).print();
			System.out.println("----------------------------------");
		}
		System.out.println("*************************************");
	}
	
	public List<AgentConfig> getAgents() {
		return (m_agents);
	}
	
	/*
	 * Private Methods
	 */
	
	// this method parses root elements.
	private boolean parseXML(Element aRootElement) {
		Element el = null;
		try {
			NodeList nl = aRootElement.getElementsByTagName(AGENTCONFIG);
			if (nl != null && nl.getLength() > 0) {
				el = (Element)nl.item(0);
			}
			else 
				return (false);

			return (parseAgentConfig(el));
			
		}
		catch (Exception e) {
			e.printStackTrace();
			return (false);
		}
	}
	
	// Parses Agent Config.
	// Input: element pointing to AgentConfig 
	private boolean parseAgentConfig(Element aElement) {
		try {
			String defaultJSTargetString = getTextValue(aElement, DEFAULTJSTARGETURL);
			String defaultJSEngineString = getTextValue(aElement, DEFAULTJSENGINEURL);
			m_defaultUserPassword = getTextValue(aElement, DEFAULTUSERPASS);
			
			if (m_defaultUserPassword == null || defaultJSTargetString == null || defaultJSEngineString == null) {
				System.out.println("ALL default values must be present (DefUserPass, DefEngineURL, DefTargetURL");
				return (false);
			}
	
			m_defaultJSTargetURL = new URL (defaultJSTargetString);
			m_defaultJSEngineURL = new URL (defaultJSEngineString);
	
			
			// Now, let's parse Agent ranges.
			Element el = null;
			NodeList nl = aElement.getElementsByTagName(AGENTRANGE);
			if (nl != null && nl.getLength() > 0) {
				for (int i = 0; i < nl.getLength(); ++i) {
					el = (Element)nl.item(i);
					if (!parseAgentRange(el)) {
						System.out.println("Error parsing Agent Range");
						return (false);
					}
				} // end of for
			} // end of if
		}
		catch (Exception e) {
			e.printStackTrace();
			return (false);
		}
	
			
		return (true);
		
	}// End of parseAgentConfig
	
	// Input: element pointing to AgentRange 
	private boolean parseAgentRange(Element aElement) {
		String prefix = null;
		String suffix = null;
		int numOfUsers = 0;
		String password = null;
		int counterStart = 0;
		URL engineURL = null;
		URL targetURL = null;
		String dummyText = null;
		
		try {
			prefix = getTextValue(aElement, USERPREFIX);
			if (prefix == null) {
				System.out.println ("Prefix is null");
				return (false);
			}
			
			suffix = getTextValue(aElement, USERSUFFIX);
			if (suffix == null) {
				System.out.println ("Suffix is null for Prefix: " + prefix);
				return (false);
			}
			
			dummyText = getTextValue(aElement, NUMOFUSERS);
			if (dummyText == null) {
				System.out.println ("NumOfUsers is null for Prefix: " + prefix);
				return (false);
			}
			numOfUsers = Integer.parseInt(dummyText);
			
			password = getTextValue(aElement, USERPASS);
			if (password == null)
				password = m_defaultUserPassword;
			
			dummyText = getTextValue(aElement, JSENGINEURL);
			if (dummyText == null)
				engineURL = m_defaultJSEngineURL;
			else
				engineURL = new URL (dummyText);
			
			dummyText = getTextValue(aElement, JSTARGETURL);
			if (dummyText == null)
				targetURL = m_defaultJSTargetURL;
			else
				targetURL = new URL (dummyText);
			
			dummyText = getTextValue(aElement, USERCOUNTERSTART);
			if (dummyText != null)
				counterStart = Integer.parseInt(dummyText);
			
			// Let's create agents now.
			AgentConfig agent = null;
			for (int i = 0; i < numOfUsers; ++i) {
				agent = new AgentConfig(prefix + counterStart++ + suffix, password, engineURL, targetURL);
				m_agents.add(agent);
			}
		} //end of try
		catch (Exception e){
			e.printStackTrace();
			return (false);
		}
		
		return (true);
	}
		
	/**
	 * Take a xml element and the tag name, look for the tag and get
	 * the text content
	 * i.e for <employee><name>John</name></employee> xml snippet if
	 * the Element points to employee node and tagName is 'name' I will return John
	 */
	
	private String getTextValue(Element aElement, String aTagName) {
		String textVal = null;
		
		NodeList nl = aElement.getElementsByTagName(aTagName);
		if (nl != null && nl.getLength() > 0) {
			Element el = (Element)nl.item(0);
			textVal = el.getFirstChild().getNodeValue();
		}
		return (textVal);
	}
	
	
	

	// Attributes
	
	private URL m_defaultJSEngineURL;
	private URL m_defaultJSTargetURL;
	private String m_defaultUserPassword;
	private List<AgentConfig> m_agents;

	// #DEFINES

	private static final String DEFAULTJSENGINEURL = "DefaultJSEngineURL";
	private static final String DEFAULTJSTARGETURL = "DefaultJSTargetURL";
	private static final String DEFAULTUSERPASS = "DefaultPassword";
	
	
	private static final String JSENGINEURL = "JSEngineURL";
	private static final String JSTARGETURL = "JSTargetURL";
	private static final String USERPREFIX = "UsernamePrefix";
	private static final String USERSUFFIX = "UsernameSuffix";
	private static final String USERPASS = "UserPassword";
	private static final String AGENTCONFIG = "AgentConfig";
	private static final String AGENTRANGE = "AgentRange";
	private static final String NUMOFUSERS = "NumOfUsers";
	private static final String USERCOUNTERSTART = "UsernameCounterStart";


	public static void main(String[] args) throws MalformedURLException {
		
		//  Config class.
		Config config = new Config();
		if (config.init("LoadTester.xml"))
			config.print();
		
	} // end of main.



}	//end of Class Config


