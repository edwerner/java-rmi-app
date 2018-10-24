import java.io.*;

public class HeartbeatIOManager {
	private static final String FILE_NAME = "data.txt";

	/**
	 * Increment and write heartbeat to file
	 * @return heartbeat count
	 * @throws IOException
	 */
	public int writeHeartbeat() throws IOException {
		String heartbeatCounter = getHeartbeatValue().toString();
		int heartbeatInt = Integer.parseInt(heartbeatCounter);
		heartbeatInt++;
		BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME));
		writer.write(String.valueOf(heartbeatInt));
		writer.close();
		return heartbeatInt;
	}
	
	/**
	 * Read heartbeat count from file
	 * @return heartbeat value
	 * @throws FileNotFoundException
	 */
	public String getHeartbeatValue() throws FileNotFoundException {
		File file = new File(FILE_NAME);
		@SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(new FileReader(file));
		String st = null;
		try {
			while ((st = br.readLine()) != null)
				return st.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return st;
	}
}