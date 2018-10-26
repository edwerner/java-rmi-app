import java.rmi.AlreadyBoundException;
import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CruiseControlManager {
	@SuppressWarnings("unused")
	private SimpleDateFormat simpleDateFormat;
	private static Map<String, CruiseControl> heartbeatMap = new HashMap<String, CruiseControl>();
	private static CruiseControl heartbeat;
	private static CruiseControl redundantHeartbeat;
	private static int failureCounter;
	private static CruiseControl stub;
	private static Registry registry;
	private static int heartbeatCount;

	private CruiseControlManager() {}

	public static void main(String[] args) throws RemoteException, AlreadyBoundException, NotBoundException {

		// Instantiating the implementation class
		CruiseControlImpl obj = new CruiseControlImpl();

		// Exporting the object of implementation class
		// (here we are exporting the remote object to the stub)
		stub = (CruiseControl) UnicastRemoteObject.exportObject(obj, 0);

		// Binding the remote object (stub) in the registry
		registry = LocateRegistry.getRegistry();
		registry.bind("Heartbeat", stub);

		// Looking up the registry for the remote object
		heartbeat = (CruiseControl) registry.lookup("Heartbeat");
		redundantHeartbeat = (CruiseControl) registry.lookup("Heartbeat");
		
		// initialize heartbeat count
		heartbeatCount = 0;
		
		initialize();

		System.out.println("Cruise control manager ready");
	}
	
	public static void initialize() throws RemoteException, AlreadyBoundException, NotBoundException {
		try {
			// Getting the registry
			failureCounter = randInt(2, 6);

			// time-date stamp
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");
			
			heartbeat.printMsg("Hearbeat has started");
			while (true) {

				// sync heartbeat and redundancy
				heartbeatMap = heartbeat.syncHeartbeat(heartbeat, redundantHeartbeat);
				if (failureCounter > 0) {
					
					heartbeat.setCount(heartbeatCount++);
					String date = simpleDateFormat.format(new Date());

					// print heartbeat message
					heartbeatMap.get("heartbeat")
							.printMsg("Heartbeat received at " + date + " count: " + heartbeat.getCount());

					// Suspend thread for 2.5 seconds
					Thread.sleep(500);
					failureCounter--;
				} else {
					// throw exception when failure
					// counter reaches zero
					throw new ConnectException("Heartbeat receiver has disconnected");
				}
			}
		} catch (Exception e) {
			// create heartbeat counter lookahead for missed count
			int redundantCount = Integer.valueOf(redundantHeartbeat.getCount());

			// print redundant heartbeat message
			heartbeatMap.get("redundancy").printMsg("Hearbeat has disconnected at count: " + redundantCount);
			
			// rebind failed heartbeat
			registry.rebind("heartbeat", stub);
			
			// re-initialize heartbeat
			initialize();
		}
	}

	/**
	 * Calculate random number within bounds
	 * 
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