package CCEntity;

public class Supervisor {
	protected String name, username;  // entered to text-box
	protected String scos, agentName, alertType;  //select from combo box
	protected int autoAnswerACDVoiceCalls, forcedWrapup;    //1:Checked, 0:Unchecked
	
	//1. supervisor Administrator
	//2. Entity Administrator
	//3. Entity Monitor : No option checked
	//4. Entity Monitor: View Real-Time Records
	//5. Entity Monitor: View Historical Reports
	protected int supervisorPermissions; 
		

}
