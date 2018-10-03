// Implementing the remote interface
public class ImplExample implements Heartbeat {

	// Implementing the interface method 
	public void printMsg(String date) { 
		System.out.println("Heartbeat received at " + date);  
	}  
}