import java.io.IOException;
import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Receiver {
    private SimpleDateFormat simpleDateFormat;
    private static boolean running;
    private Receiver() {}

    public static void main(String[] args) throws RemoteException, NotBoundException, InterruptedException {

        // Getting the registry 
        Registry registry = LocateRegistry.getRegistry(null);
        String date;
        //int failureCounter = 5;
        int failureCounter = randInt(3, 5);

        // Looking up the registry for the remote object 
        Heartbeat heartbeat = (Heartbeat) registry.lookup("Heartbeat");
        Heartbeat redundancy = (Heartbeat) registry.lookup("Heartbeat");

        // time-date stamp
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");

        // heartbeat is running
        running = true;

        // get Java runtime
        Runtime rt = Runtime.getRuntime();

        while (running == true) {
            if (failureCounter > 0) {
                date = simpleDateFormat.format(new Date());
                heartbeat.printMsg(date);
                Thread.sleep(5000);
                failureCounter--;
            } else {
                running = false;
                try {
                    redundancy.printMsg("Hearbeat has crashed");
                    rt.exec("java Receiver");
                    running = true;
                    failureCounter = 5;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                redundancy.printMsg("Hearbeat has recovered");
                throw new ConnectException("Heartbeat receiver has disconnected");
            }
        }
    }

    public static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }
}