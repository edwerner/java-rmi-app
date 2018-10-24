import java.io.FileNotFoundException;
import java.io.IOException;

public interface HeartbeatIo {
	int writeHeartbeat() throws IOException;
	String getHeartbeatValue() throws FileNotFoundException;
}