
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.*;

public class GameServerThread extends Thread {
	private DataInputStream streamIn;
	private DataOutputStream streamOut;
	private GameServer server;
	private ServerPlayer player;
	private Socket socket;
	private final int ID;
	private String[] data;
	private boolean running;
	private boolean tracking;
	public GameServerThread(GameServer server, Socket socket) {
		super();
		data = new String[]{""};
		this.socket = socket;
		this.server = server;
		ID = socket.getPort();
		System.out.println("Socket : " + ID);
		player = new ServerPlayer(300,300,49,49,2,100);
	}
	public synchronized ServerPlayer getPlayer(){
		return player;
	}
	public  String[] getData(){
		return data;
	}
	public void kill() throws IOException{
		running = false;
		socket.close();
		streamIn.close();
		streamOut.close();
	}
	public boolean getTracking(){
		return tracking;
	}
	public void startTracking(){
		tracking = true;
	}
	public void stopTracking(){
		tracking = false;
	}
	public int getID() {
		return ID;
	}
	public void send(String msg) {
		try {
			streamOut.writeUTF(msg);
			streamOut.flush();
		} catch (IOException ioe) {
			System.out.println(ID + " ERROR sending: " + ioe.getMessage());
			data = new String[]{"404"};
		}
	}
	public void open() throws IOException {
		streamIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
		streamOut = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
	}
	@Override
	public void start(){
		running = true;
		super.start();
	}
	public void run() {
		System.out.println("Server Thread " + ID + " running.");
		while (running) {
			try {
				String[] oldData = data;
				ArrayList<String> list = new ArrayList<String>(Arrays.asList(streamIn.readUTF().split("-")));
				list.removeAll(Arrays.asList(""));
				data = list.toArray(new String[list.size()]);
				if(!oldData.equals(data)){
					System.out.println(Arrays.toString(data));
					server.readInput();
				}
			} catch (IOException ioe) {
				data = new String[]{"QUIT", ""+ ID};
			}
		}
	}
}
