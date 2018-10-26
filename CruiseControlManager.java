import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.ConnectException;
import java.rmi.NotBoundException;
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
	private static Map<String, Heartbeat> heartbeatMap = new HashMap<String, Heartbeat>();

	private CruiseControlManager() {}

	public static void main(String[] args) throws NotBoundException, InterruptedException, IOException, AlreadyBoundException {

		Receiver receiver = new Receiver();

        // Instantiating the implementation class 
        CruiseControlImpl obj = new CruiseControlImpl(); 

        // Exporting the object of implementation class  
        // (here we are exporting the remote object to the stub) 
        Heartbeat stub = (Heartbeat) UnicastRemoteObject.exportObject(obj, 0);
        
        // Binding the remote object (stub) in the registry 
        Registry registry = LocateRegistry.getRegistry();
        registry.bind("Heartbeat", stub);
        
		// Getting the registry
		int failureCounter = randInt(2, 6);

		// Looking up the registry for the remote object
		Heartbeat heartbeat = (Heartbeat) registry.lookup("Heartbeat");
		Heartbeat redundantHeartbeat = (Heartbeat) registry.lookup("Heartbeat");

		// time-date stamp
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");

		// get Java runtime
//		Runtime rt = Runtime.getRuntime();

        System.out.println("Server ready");
        
		try {
			heartbeat.printMsg("Hearbeat has started");
			while (true) {
				
				// sync heartbeat and redundancy
				heartbeatMap = heartbeat.syncHeartbeat(heartbeat, redundantHeartbeat);
				if (failureCounter > 0) {
					String date = simpleDateFormat.format(new Date());
					
					// print heartbeat message
					heartbeatMap.get("heartbeat").printMsg("Heartbeat received at " + date + " count: " + heartbeat.logHeartbeat());
					
					// Suspend thread for 2.5 seconds
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
			
			// print redundant heartbeat message
			heartbeatMap.get("redundancy").printMsg("Hearbeat has disconnected at count: " + redundantCount);
			
			// invoke exec process to restart receiver with
			// Java runtime
//			rt.exec("java Receiver");
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