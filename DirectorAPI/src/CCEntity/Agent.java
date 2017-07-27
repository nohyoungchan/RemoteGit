package CCEntity;

import ClassesEntity.TestBaseObject;

public class Agent extends TestBaseObject {
	Agent() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected String name, username, agentID, agentExtension, emailAddress;  // entered to text-box
	protected String cos, popUpProfile, aqProfile, eaqProfile;  //select from combo box
	protected int autoAnswerACDVoiceCalls, forcedWrapup;    //1:Checked, 0:Unchecked
	
	protected String groupSelectionCriteria;
	protected String callSelectionPrimaryRule, callSelectionSecondaryRule;
	protected int callSelectionUseSecondaryRule;
	
	protected String groups[], skills[]; //multiple data
	
	public void createAgent(){
		
	}
	
	public void updateAgent(){
		
	}
	
	
	
	public void verifyAgentByDirector(){
		
	}
	
	public void verifyAgentByCall(){
		
	}
	

}
