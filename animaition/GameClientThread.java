package animaition;

import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.*;

public class GameClientThread extends Thread {
	private Socket socket = null;
	private DataInputStream streamIn = null;
	private String[] data;
	private boolean running;
	private GameClient client;
	public GameClientThread(GameClient client, Socket socket) {
		running = true;
		this.socket = socket;
		this.client = client;
		data = new String[]{""};
		open();
		start();
	}
	public void open() { // opens
		try {
			streamIn = new DataInputStream(socket.getInputStream()); // makes new data in stream
		} catch (IOException ioe) {
			System.out.println("Server is closed.");
			kill();
		}
	}
	public void kill() { // closes 
		try {
			running = false;
			streamIn.close();
		} catch (IOException ioe) {
			System.out.println("Error closing input stream: " + ioe);
		}
	}
	public String[] getData(){
		return data;
	}
	public void run() {
		while (running) {
			try {
				String[] oldData = data;
				ArrayList<String> list = new ArrayList<String>(Arrays.asList(streamIn.readUTF().split("-")));
				list.removeAll(Arrays.asList(""));
				data = list.toArray(new String[list.size()]);
				if(!oldData.equals(data)){
					System.out.println(Arrays.toString(data));
					client.readInput();
				}
			} catch (IOException ioe) {
				System.out.println("Listening error: " + ioe.getMessage());
				//client.stop();
			}
		}
	}
}
