import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

// Implementing the remote interface
public class HeartbeatImpl implements Heartbeat {

    private HeartbeatIoImpl manager = new HeartbeatIoImpl();
    @SuppressWarnings("unused")
	private String count;
    private Map<String, Heartbeat> heartbeatMap;

    /**
     * Instantiate heartbeat map
     */
    public HeartbeatImpl() {
    	 heartbeatMap = new HashMap<String, Heartbeat>();
    }
    /**
     * Print heartbeat message
     */
    @Override
	public void printMsg(String msg) { 
		System.out.println(msg);  
	}
    
    /**
     * Write heartbeat to file
     * @return heartbeat count value
     */
	@Override
	public int logHeartbeat() {
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