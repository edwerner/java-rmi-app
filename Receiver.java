import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry; 
import java.rmi.registry.Registry;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Receiver {
    private SimpleDateFormat simpleDateFormat;
    private static boolean running;
    private Receiver() {}

    public static void main(String[] args) throws RemoteException, NotBoundException, InterruptedException {
        try {  
            // Getting the registry 
            Registry registry = LocateRegistry.getRegistry(null);
            String date; 
            int failureCounter = 5;

            // Looking up the registry for the remote object 
            Heartbeat heartbeat = (Heartbeat) registry.lookup("Heartbeat");
            Heartbeat redundancy = (Heartbeat) registry.lookup("Heartbeat");

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");
            running = true;

            while (running == true) {
                if (failureCounter > 0) {
                    date = simpleDateFormat.format(new Date());
                    heartbeat.printMsg(date);
                    Thread.sleep(5000);
                    failureCounter--;
                } else {
                   running = false;
                   throw new ConnectException("Heartbeat receiver has disconnected");
                }
            }
        } catch (ConnectException e) {
            System.err.println("Client exception: " + e.toString()); 
            e.printStackTrace(); 
        } 
    } 
}