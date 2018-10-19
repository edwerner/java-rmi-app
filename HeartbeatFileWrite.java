import java.io.*;
import java.util.Scanner;
public class HeartbeatFileWrite {
	private static final String FILE_NAME = "data.txt";
	private static final String INITIALIZE_VALUE = "0";
	
	public void writeHeartbeat() throws IOException {
		String heartbeatCounter = getValue();
		BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME));
		writer.write(heartbeatCounter);
		writer.close();
	}
	public String getValue() {
		Scanner reader = new Scanner(FILE_NAME);
		if(reader.hasNext()) {
			String value = incrementValue(reader.next());
			reader.close();
			return value;
		}
		reader.close();
		return incrementValue(INITIALIZE_VALUE);	
	}
	public String incrementValue(String value) {
		return String.valueOf(Integer.valueOf(value)+1);
		
	}
}