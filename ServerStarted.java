

import java.io.IOException;
import java.net.ServerSocket;

public class ServerStarted implements Runnable{
	private final GameServer server;
	private ServerSocket serverSocket;
	public ServerStarted(int port){
		server = new GameServer(port);
		try {
			System.out.println("Binding to port " + port + ", please wait  ...");
			serverSocket = new ServerSocket(port);
			System.out.println("Server started: " + serverSocket);
			Thread thread = new Thread(this);
			thread.start();
		} catch (IOException ioe) {
			System.out.println("Can not bind to port " + port + ": " + ioe.getMessage());
		}
	}
	@Override
	public void run(){
		while(true){
			try {
				System.out.println("Waiting for a client ...");
				server.addThread(serverSocket.accept());
			} catch (IOException ioe) {
				System.out.println("Server accept error: " + ioe);
			}
		}
	}
	public static void main(String args[]) {
		//if (args.length != 1)
		//	System.out.println("Usage: java GameServer port");
		//else
			new ServerStarted(7958);
	}
}
