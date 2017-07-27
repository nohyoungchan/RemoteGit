package SupervisorLoad;

public class TestObject {
	  public void wait(int num) throws Exception{
		  log("* Wait : " + num + " seconds.");
		  Thread.sleep(num * 1000);
	  }
	  
	  public void wait(int num, String reason) throws Exception{
		  log("* Wait: " + reason +  " : " + num + " seconds.");
		  Thread.sleep(num * 1000);
	  }
	  
	    public static void log(String message) {
	        System.out.println(message);
	    }

}
