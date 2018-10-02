import java.rmi.registry.LocateRegistry; 
import java.rmi.registry.Registry;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Client {  
   private Client() {} 
   private static int TIME = 999999999; 
   private SimpleDateFormat simpleDateFormat;
   public static void main(String[] args) {
      try {  
         // Getting the registry 
         Registry registry = LocateRegistry.getRegistry(null); 

         // Looking up the registry for the remote object 
         Heartbeat stub = (Heartbeat) registry.lookup("Heartbeat");
         SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");
         String date = simpleDateFormat.format(new Date());
         
         //print date
         stub.printMsg(date);
      } catch (Exception e) {
         System.err.println("Client exception: " + e.toString()); 
         e.printStackTrace(); 
      } 
   } 
}