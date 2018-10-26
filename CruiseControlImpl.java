import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

// Implementing the remote interface
public class CruiseControlImpl implements CruiseControl {

    @SuppressWarnings("unused")
    private Map<String, CruiseControl> heartbeatMap;
	private int count;
	private int speed;

    /**
     * Instantiate heartbeat map
     */
    public CruiseControlImpl() {
    	 heartbeatMap = new HashMap<String, CruiseControl>();
    }
    /**
     * Print heartbeat message
     */
    @Override
	public void printMsg(String msg) { 
		System.out.println(msg);  
	}
    /**
     * Print get heartbeat count
     */
	@Override
	public int getCount() {
		return this.count;
	}
    /**
     * Set heartbeat count
     */
	@Override
	public void setCount(int count) {
		this.count = count;
	}
	/**
	 * Synchronize heartbeats
	 */
	@Override
	public Map<String, CruiseControl> syncHeartbeat(CruiseControl heartbeat, CruiseControl redundancy) {
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
	/**
	 * Get current speed
	 */
	@Override
	public int getSpeed() throws RemoteException {
		return speed;
	}
	/**
	 * Set current speed
	 */
	@Override
	public void setSpeed(int speed) throws RemoteException {
		this.speed = speed;
	}
}