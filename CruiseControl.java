import java.rmi.Remote; 
import java.rmi.RemoteException;
import java.util.Map;

// Remote interface for our application
public interface CruiseControl extends Remote {
	void printMsg(String message) throws RemoteException;
	int getSpeed() throws RemoteException;
	Map<String, CruiseControl> syncHeartbeat(CruiseControl heartbeat, CruiseControl redundancy) throws RemoteException;
	void setSpeed(int speed) throws RemoteException;
} 