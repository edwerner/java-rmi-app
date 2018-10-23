import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

// Implementing the remote interface
public class HeartbeatImpl implements Heartbeat {

    private HeartbeatManager manager = new HeartbeatManager();
    @SuppressWarnings("unused")
	private String count;

    @Override
	public void printMsg(String msg) { 
		System.out.println(msg);  
	}
	@Override
	public int writeHeartbeat() {
		int output = 0;
		try {
			output = manager.writeHeartbeat();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return output;
	}
	@Override
	public String getCount() {
		String value = "Error retrieving heartbeat value";
		try {
			value = manager.getHeartbeatValue().toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return value;
	}
	@Override
	public void setCount(String count) {
		this.count = count;
	}
	public Map<String, Heartbeat> syncHeartbeat(Heartbeat heartbeat, Heartbeat redundancy) {
		Map<String, Heartbeat> heartbeatMap = new HashMap<String, Heartbeat>();
		try {
			heartbeat.setCount(getCount());
			redundancy.setCount(getCount());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		heartbeatMap.put("heartbeat", heartbeat);
		heartbeatMap.put("redundancy", redundancy);
		return heartbeatMap;
	}
}