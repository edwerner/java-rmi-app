public HeartbeatFileWrite {
	private static final String FILE_NAME = "data.txt;
	
	public void writeHeartbeat(String msg) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME));
		writer.write(HEARTBEAT_MSG);
		writer.close();
	}
}