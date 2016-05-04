import java.net.*;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import java.io.*;

public class GameServer implements Runnable {
	private ArrayList<GameServerThread> clients;
	private int players;
	private Graph graph;
	private JTextArea textPane;
	public GameServer(int port) {
		clients = new ArrayList<GameServerThread>();
		graph = new Graph();
		launch();
		System.out.println("Binding to port " + port + ", please wait  ...");
		Thread thread = new Thread(this);
		thread.start();
	}
	private void launch(){
		JFrame frame = new JFrame("Server");
		JLabel lblPlayers = new JLabel("Players");
		textPane = new JTextArea();
		textPane.setText("");
		JPanel panel = new JPanel();
		textPane.setEditable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel.setLayout(null);
		textPane.setBounds(300, 20, 130, 250);
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setBounds(100, 100, 450, 300);
		lblPlayers.setBounds(340, 0, 70, 15);
		panel.add(textPane);
		panel.add(lblPlayers);
		frame.add(panel);
	}
	public void run() {
	}
	public synchronized void readInput(){
		int remove = -1;
			for(GameServerThread foo : clients){
				String[] data = foo.getData();
				if(data!= null){
					switch(data[0]){
					case "#":
						 removePlayer(foo);
						if(data[1].equals("RIGHT")){
							System.out.println("right");
							foo.getPlayer().moveRight();
						}else if(data[1].equals("DOWN")){
							System.out.println("down");
							foo.getPlayer().moveDown();
						}else if(data[1].equals("LEFT")){
							foo.getPlayer().moveLeft();
							System.out.println("left");
						}else{
							System.out.println("up");
							foo.getPlayer().moveUp();
						}
						foo.send(graph.get(foo.getPlayer().getRoom()));
						break;
					case "$": move(foo,Integer.parseInt(data[1]), Integer.parseInt(data[2]));//simple motion $-x-y-
						break;
					case "@": attack(foo.getPlayer().getAttack(), Integer.parseInt(data[1]));
						break;
					case "QUIT": remove = foo.getID();
						break;
				}
			}
		}
		if(remove > -1){
			remove(remove);
		}
	}
	private void move(GameServerThread foo, int x, int y){
		foo.getPlayer().set(x,y);
		for(GameServerThread bar: clients){
			if(!foo.equals(bar) && foo.getPlayer().sameRoom(bar.getPlayer().getRoom())){
				bar.send("*-" + foo.getID() + "-" + x + "-" + y + "-" + foo.getPlayer().getHealth());//* means other player
				foo.send("*-" + bar.getID() + "-" + bar.getPlayer().getX()+ "-" + bar.getPlayer().getY() +  "-" + bar.getPlayer().getHealth());
			}
		}
	}
	private void removePlayer(GameServerThread foo){
		for(GameServerThread bar: clients){
			if(!foo.equals(bar) && foo.getPlayer().sameRoom(bar.getPlayer().getRoom())){
				bar.send("!-" + foo.getID());
				foo.send("!-" + bar.getID());
			}
		}
	}
	private void attack(int attack, int otherID){
		GameServerThread temp = clients.get(findClient(otherID));
		temp.getPlayer().damage(2);
		temp.send("@-" + temp.getPlayer().getHealth() + "-");
		move(temp, temp.getPlayer().getX(),temp.getPlayer().getY());
	}
	private int findClient(int Id) { // gets client
		System.out.println("searching for peeps " + Id);
		for(GameServerThread foo: clients){
			System.out.println("peep is " + foo.getID());
			if(foo.getID() == Id){
				return clients.indexOf(foo);
			}
		}
		return -1;
	}
	public synchronized void remove(int ID) {
		try {
			removePlayer(clients.get(findClient(ID)));
			clients.get(findClient(ID)).kill();
			clients.remove(findClient(ID));
			players--;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void addThread(Socket socket) {
		if (players < 50) {
			System.out.println("Client accepted: " + socket); // happens when client is added
			clients.add(new GameServerThread(this, socket));
			try {
				clients.get(players).open();
				clients.get(players).start();
				clients.get(players).send(graph.getCurrent());
				clients.get(players).send("$" +"-"+ 20 + "-" + 350+ "-" + 350 + "-");
				textPane.append("" + clients.get(players).getID() +"\n");
				players++;
			} catch (IOException ioe) {
				System.out.println("Error opening thread: " + ioe);
			}
		} else
			System.out.println("Client refused: maximum " + 50 + " reached.");
	}
	public void close(){
		System.exit(1);
	}	
}
