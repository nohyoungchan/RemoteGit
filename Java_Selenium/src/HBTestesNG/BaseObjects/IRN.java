package HBTestesNG.BaseObjects;

public class IRN extends TestObject{
	public String irnNum, dnisNum, didNum;
	
	IRN(String irnNum, String dnisNum, String didNum) throws Exception{
		this.irnNum = irnNum;
		this.dnisNum = dnisNum;
		this.didNum =didNum;
		
		log.info("\n@(IRN)" + this.irnNum + " Initializing ################### ");
	}

	public IRN() {
		// TODO Auto-generated constructor stub
	}

}
