package Asim;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
//import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;

import java.io.IOException;
import java.net.MalformedURLException;
//import java.net.URL;
import java.util.concurrent.*;

public class test1 {

    private static final long s_WaitTimeout = 30;//seconds
    private static final int s_numThreads = 100;
    private static final long s_delayBetweenLogins = 10 * 1000; //10 seconds

    private static final String s_USERNAME_LABEL = "username";
    private static final String s_PASSWORD_LABEL = "password";
    private static final String s_SUBMIT_BTN_LABEL = "submitBtn";

    private static final Object s_exitSignal = new Object();

    private static AgentConfig g_agentConfig = null;
    
    private static String g_filename = null;
    private static boolean g_printUsers = false;

    public static void wait_for_input() {
		try {
		    System.out.print("press a key >>>");
		    int b = System.in.read();
		} catch(IOException e) {
		    System.out.println("caught exception" + e);
		}
    }

    public static DesiredCapabilities get_caps() {
		DesiredCapabilities caps = new DesiredCapabilities();
		//caps.setPlatform(Platform.MAC);
		//caps.setCapability("resolution", "1200x1250");
		//caps.setCapability("browser", "Safari");
		//caps.setCapability("browser_version", "8.0");
		//caps.setCapability("os", "OS X");
		//caps.setCapability("os_version", "Yosemite");
		caps.setCapability("acceptSslCerts", "true");
	
		return caps;

    }

    private static WebElement find_wait_until_usable(WebDriver p_drv, String p_label, long p_timeOut) {

		WebElement res = p_drv.findElement(By.id(p_label));
		WebDriverWait wait = new WebDriverWait(p_drv, p_timeOut);
		wait.until(ExpectedConditions.elementToBeClickable(res));
		//wait.until(ExpectedConditions.visibilityOf(res));
	
		return res;
	}
	    
	private static WebElement find_and_fill(WebDriver p_drv, String p_label, String p_value, boolean p_pressEnter) {
	
		WebElement res = find_wait_until_usable(p_drv, p_label, s_WaitTimeout);
	
		res.clear();
		res.sendKeys(p_value);
		if(p_pressEnter) {
		    res.sendKeys(Keys.ENTER);
		}
	
		return res;
    }

    private static WebElement find_and_click(WebDriver p_drv, String p_label) {
		//WebElement res = p_drv.findElement(By.id(p_label));
		WebElement res = find_wait_until_usable(p_drv, p_label, s_WaitTimeout);
		
		res.click();
	
		return res;
    }
    
    public static void login(WebDriver p_drv, String p_username, String p_password) {
		try {
		    // wait for "username" box as marker that login page has more or less finished loading
		    // this would save us from throwing an exception if the element is not found
		    WebElement dummy = (new WebDriverWait(p_drv, s_WaitTimeout)).until(ExpectedConditions.visibilityOfElementLocated(By.id(s_USERNAME_LABEL)));
		    
		    find_and_fill(p_drv, s_USERNAME_LABEL, p_username, false);
		    find_and_fill(p_drv, s_PASSWORD_LABEL, p_password, true);
		    find_and_click(p_drv, s_SUBMIT_BTN_LABEL);
		} catch(Exception e) {
		    System.err.println("login: caught exception" + e);
		}
    }

    private static void run_agent_loop(AgentConfig aAgentConfig) {
		try {
		    	
		    //set up driver
		    DesiredCapabilities caps = get_caps();
		    //WebDriver driverInst = new RemoteWebDriver(p_url, caps);
		    WebDriver driverInst = new RemoteWebDriver(aAgentConfig.getEngineURL(), caps);
		    
		    driverInst.manage().window().setSize(new Dimension(1200, 1250));
	
		    System.out.println("logging in " + aAgentConfig.getUsername() + " to " + aAgentConfig.getTargetURL().toString());
		    
		    driverInst.get(aAgentConfig.getTargetURL().toString());
		    login(driverInst, aAgentConfig.getUsername(), aAgentConfig.getPassword());
	
		    // wait for exit
		    // to be signaled
		    synchronized(s_exitSignal) {
		    	s_exitSignal.wait();
		    }
		    
		    driverInst.quit();

		} 
		catch(Exception e) {
		    System.err.println("run_agent_loop: caught exception" + e);
		}
    }
 
    
    private static void printUsage() {
    	System.out.println("\n\n Usage \n");
    	System.out.println ("java phantomjstest1 <filename> <options>");
    	System.out.println("filename is the XML file with agent config");
    	System.out.println("Options are:");
    	System.out.println("-p print agent config ONLY");
    }

    private static boolean parse_cmdline_and_commit(String[] p_args) {
	    if (p_args.length == 0) {
	    	printUsage();
	    	return (false);
	    }
	    else {
	    	g_filename = p_args[0];
	    	
	    	if (p_args.length == 2) {
	    		if (p_args[1].equalsIgnoreCase("-p")) {
	    			g_printUsers = true;
	    		}
	    	}
	    }
		return (true);
    }
    
    public static void main(String[] args) throws MalformedURLException {

	if (!parse_cmdline_and_commit(args))
		return;
	
	Config config = new Config();
	if (!config.init(g_filename))
		return;
	else
		System.out.println("XML file loaded .....");
	
	if (g_printUsers) {
		
		System.out.println("\n***Will only print users ****\n");
		
		config.print();
		return;
	}
	
	List<AgentConfig> agentList = config.getAgents();
	System.out.println("\nTotal Users: " + agentList.size() + "\n");
	
	ExecutorService threadPool = Executors.newFixedThreadPool(s_numThreads);
	
	for(int cnt = 0; cnt < agentList.size(); ++cnt) {
	    try {
	    	Thread.sleep(s_delayBetweenLogins);
	    } catch(Exception e) {
	    	System.err.println("caught exception e=" + e + "while spawning login jobs - ignoring at this stage");
	    }
	    g_agentConfig = agentList.get(cnt);
	    // () -> { };   //this creates a "Runnable task" which can be used in a threadPool.
	    threadPool.submit(() -> { run_agent_loop(g_agentConfig); });
	    
	   // ++s_index;
	}

	// wait for keyboard input before exiting
	wait_for_input();

	//then signal all workers to exit
	synchronized(s_exitSignal) {
	    s_exitSignal.notifyAll();
	}

	//exit here
    }

}

