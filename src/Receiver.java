import java.rmi.registry.LocateRegistry; 
import java.rmi.registry.Registry;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer; 
import java.util.TimerTask;

public class Receiver {
private SimpleDateFormat simpleDateFormat;

    private static boolean running;

    private Receiver() {}

    public static void main(String[] args) {
        try {  
            // Getting the registry 
            Registry registry = LocateRegistry.getRegistry(null);
            String date; 
            int failureCounter = 5;

            // Looking up the registry for the remote object 
            Heartbeat stub = (Heartbeat) registry.lookup("Heartbeat");
            Heartbeat stub2 = (Heartbeat) registry.lookup("Heartbeat");
            Heartbeat stub3 = (Heartbeat) registry.lookup("Heartbeat");

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");
            running = true;

            while (running == true) {
                if (failureCounter > 0) {
                    date = simpleDateFormat.format(new Date());
                    stub.printMsg(date);
                    Thread.sleep(5000);
                    failureCounter--;
                } else if (failureCounter == 0) {
                    running = true;
                    failureCounter = 5;
                    System.out.println("System recovering...");
                } else {
                    running = false;
                    System.out.println("System failure");
                }
            }

        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString()); 
            e.printStackTrace(); 
        } 
    } 
}