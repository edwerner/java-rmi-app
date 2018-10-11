import java.rmi.registry.Registry; 
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject; 

public class Server extends HeartbeatImpl { 
    public Server() {} 
    public static void main(String args[]) { 
    try {
            // Instantiating the implementation class 
            HeartbeatImpl obj = new HeartbeatImpl(); 

            // Exporting the object of implementation class  
            // (here we are exporting the remote object to the stub) 
            Heartbeat stub = (Heartbeat) UnicastRemoteObject.exportObject(obj, 0);  

            // Binding the remote object (stub) in the registry 
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("Heartbeat", stub);

            System.err.println("Server ready"); 
        } catch (Exception e) { 
            System.err.println("Server exception: " + e.toString()); 
            e.printStackTrace(); 
        } 
    }
}