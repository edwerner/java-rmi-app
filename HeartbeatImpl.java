import java.io.IOException;

// Implementing the remote interface
public class HeartbeatImpl implements Heartbeat {

    private HeartbeatFileWrite fileWriter = new HeartbeatFileWrite();

	// Implementing the interface method 
	public void printMsg(String date) { 
		System.out.println("Heartbeat received at " + date);  
	}
	public void writeHeartbeat() {
		try {
			fileWriter.writeHeartbeat();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}