import java.rmi.Remote; 
import java.rmi.RemoteException;

// Remote interface for our application
public interface Heartbeat extends Remote {  
	void printMsg(String date) throws RemoteException;  
} 