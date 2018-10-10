import java.rmi.registry.LocateRegistry; 
import java.rmi.registry.Registry;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer; 
import java.util.TimerTask;

public class Client {
private SimpleDateFormat simpleDateFormat;

    private static boolean running;

    private Client() {

    }

    public static void main(String[] args) {
        try {  
            // Getting the registry 
            Registry registry = LocateRegistry.getRegistry(null);
            String date = ""; 

            // Looking up the registry for the remote object 
            Heartbeat stub = (Heartbeat) registry.lookup("Heartbeat");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");
            running = true;

            while (running) {
                date = simpleDateFormat.format(new Date());
                stub.printMsg(date);
                Thread.sleep(5000);
            }

        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString()); 
            e.printStackTrace(); 
        } 
    } 
}