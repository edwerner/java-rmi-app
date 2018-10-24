import java.io.IOException;
import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Receiver {
	@SuppressWarnings("unused")
	private SimpleDateFormat simpleDateFormat;
	private static Map<String, Heartbeat> heartbeatList = new HashMap<String, Heartbeat>();

	private Receiver() {}

	public static void main(String[] args) throws NotBoundException, InterruptedException, IOException {

		// Getting the registry
		Registry registry = LocateRegistry.getRegistry(null);
		int failureCounter = randInt(2, 6);

		// Looking up the registry for the remote object
		Heartbeat heartbeat = (Heartbeat) registry.lookup("Heartbeat");
		Heartbeat redundantHeartbeat = (Heartbeat) registry.lookup("Heartbeat");

		// time-date stamp
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");

		// get Java runtime
		Runtime rt = Runtime.getRuntime();
		try {
			heartbeat.printMsg("Hearbeat has started");
			while (true) {
				
				// sync heartbeat and redundancy
				heartbeatList = heartbeat.syncHeartbeat(heartbeat, redundantHeartbeat);
				if (failureCounter > 0) {
					String date = simpleDateFormat.format(new Date());
					heartbeatList.get("heartbeat").printMsg("Heartbeat received at " + date + " count: " + heartbeat.writeHeartbeat());
					Thread.sleep(2500);
					failureCounter--;
				} else {
					// throw exception when failure
					// counter reaches zero
					throw new ConnectException("Heartbeat receiver has disconnected");
				}
			}
		} catch (Exception e) {
			
			// create heartbeat counter lookahead for missed count
			int redundantCount = Integer.valueOf(redundantHeartbeat.getCount()) + 1;
			heartbeatList.get("redundancy").printMsg("Hearbeat has disconnected at count: " + redundantCount);
			rt.exec("java Receiver");
		}
	}

	/**
	 * Calculate random number within bounds
	 * @param min
	 * @param max
	 * @return random integer
	 */
	public static int randInt(int min, int max) {
		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}
}