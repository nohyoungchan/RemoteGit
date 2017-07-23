package HBTestesNG.TestCases;
	
import org.sikuli.script.Screen;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;

public class TestTemp {

	public static void main(String[] args) {
		Screen screen = new Screen();

		
        try{
        	Screen s = new Screen();
        	Pattern patternUsername = new Pattern(".\\imgs\\ic_login_username.PNG");
        	Pattern patternPassword = new Pattern(".\\imgs\\ic_login_password.PNG");
        	Pattern patternLoginButton = new Pattern(".\\imgs\\ic_login_loginButton.PNG");
                //s.click(".\\imgs\\ic_login_username.PNG");
                //s.write("hello world");
        	s.click(patternUsername);
        	s.write("young6");
        	s.click(patternPassword);
        	s.write("changeme");
        	s.click(patternLoginButton);

        	
        	
        	//s.(pattern);
        }
        catch(FindFailed e){
                e.printStackTrace();
        }
	}


}
