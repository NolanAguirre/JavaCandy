
import java.util.ArrayList;
import java.util.Arrays;
import java.io.*;
import java.net.Socket;

public class GameServerThread extends Thread {
	private DataInputStream streamIn;
	private DataOutputStream streamOut;
	private GameServer server;
	private ServerPlayer player;
	private Socket socket;
	private final int ID;
	private String[] data;
	private boolean running;
	private int roomX;
	private int roomY;
	public GameServerThread(GameServer server, Socket socket) {
		super();
		roomX = 49;
		roomY = 49;
		data = new String[]{""};
		this.socket = socket;
		this.server = server;
		ID = socket.getPort();
		System.out.println("Socket : " + ID);
		player = new ServerPlayer(300,300,2,100);
	}
	public synchronized ServerPlayer getPlayer(){
		return player;
	}
	public  String[] getData(){
		return data;
	}
	public void kill() throws IOException{
		streamIn.close();
		streamOut.close();
		socket.close();
		running = false;
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
	@Override
	public String toString(){
		return  ID + "-" + player.getX() + "-" + player.getY() + "-" + player.getHealth();
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
					server.readInput(this);
				}
			} catch (IOException ioe) {
				data = new String[]{"QUIT", ""+ ID};
			}
		}
	}
	public void moveUp(){
		roomX--;
	}
	public void moveDown(){
		roomX++;
	}
	public void moveLeft(){
		roomY--;
	}
	public void moveRight(){
		roomY++;
	}
	public int[] getRoom(){
		return new int[]{roomX,roomY};
	}
	public boolean sameRoom(int[] foo){
		return foo[0] == roomX && foo[1] == roomY;
	}
}
