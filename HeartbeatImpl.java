import java.io.IOException;

// Implementing the remote interface
public class HeartbeatImpl implements Heartbeat {

    private HeartbeatFileWrite fileWriter = new HeartbeatFileWrite();

	// Implementing the interface method 
	public void printMsg(String msg) { 
		System.out.println(msg);  
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