package BaseObjects;

import BaseObjects.TestObject;

public class Service extends TestObject{
	public String name, destination, wrapupTime, forcedReleaseTime;
	public String overflowTimeout, overflowDestination;
	public String interflowTimeout, interflowDestinationType, interflowDestination;
	
	Service(int serviceID) throws Exception{
		initializeService(serviceID);
		log.info("\n@(Service)" + this.name + " Initializing ################### ");
	}
	
	public Service() {
		// TODO Auto-generated constructor stub
	}

	public void initializeService(int serviceID){
		
	}

}
