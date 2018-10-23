import java.io.IOException;
import java.rmi.Remote; 
import java.rmi.RemoteException;
import java.util.Map;

// Remote interface for our application
public interface Heartbeat extends Remote {  
	void printMsg(String date) throws RemoteException;  
	int writeHeartbeat() throws IOException;
	String getCount() throws RemoteException;
	void setCount(String string) throws RemoteException;
	Map<String, Heartbeat> syncHeartbeat(Heartbeat heartbeat, Heartbeat redundancy) throws RemoteException;
} 