import java.io.IOException;

// Implementing the remote interface
public class HeartbeatImpl implements Heartbeat {

    private HeartbeatFileWrite fileWriter = new HeartbeatFileWrite();

	// Implementing the interface method 
	public void printMsg(String date) { 
		System.out.println("Heartbeat received at " + date);  
	}
	public int writeHeartbeat() {
		int output = 0;
		try {
			output = fileWriter.writeHeartbeat();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return output;
	}
}