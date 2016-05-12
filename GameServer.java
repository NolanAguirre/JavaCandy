import java.net.*;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.GridLayout;
import java.io.*;

public class GameServer implements Runnable {
	private ArrayList<GameServerThread> clients;
	private int players;
	private Graph graph;
	private JPanel panel;
	private JScrollPane scrollPane;
	public GameServer(int port) {
		clients = new ArrayList<GameServerThread>();
		graph = new Graph();
		launch();
		System.out.println("Binding to port " + port + ", please wait  ...");
		Thread thread = new Thread(this);
		thread.start();
	}
	private void launch(){
		JFrame frame = new JFrame("Game Server");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JCheckBox chckbxMobs = new JCheckBox("Mobs");
		chckbxMobs.setBounds(8, 241, 94, 23);
		frame.getContentPane().add(chckbxMobs);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(8, 0, 277, 212);
		textArea.setEditable(false);
		frame.getContentPane().add(textArea);
		
		JButton btnEndGame = new JButton("End Game");
		btnEndGame.setBounds(102, 235, 117, 25);
		frame.getContentPane().add(btnEndGame);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(297, 0, 141, 260);
		frame.getContentPane().add(scrollPane);
		
		panel = new JPanel();
		panel.setLayout(new GridLayout(50,1));
		scrollPane.setViewportView(panel);
		frame.setVisible(true);
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
							foo.getPlayer().moveRight();
						}else if(data[1].equals("DOWN")){
							foo.getPlayer().moveDown();
						}else if(data[1].equals("LEFT")){
							foo.getPlayer().moveLeft();
						}else{
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
		if(temp.getPlayer().getHealth() <= 0){
			temp.send("QUIT");
		}
		temp.send("@-" + temp.getPlayer().getHealth() + "-");
		move(temp, temp.getPlayer().getX(),temp.getPlayer().getY());
	}
	private int findClient(int Id) {
		for(GameServerThread foo: clients){
			if(foo.getID() == Id){
				return clients.indexOf(foo);
			}
		}
		return -1;
	}
	public synchronized void remove(int ID) {
		try {
			panel.removeAll();
			removePlayer(clients.get(findClient(ID)));
			clients.get(findClient(ID)).kill();
			clients.remove(findClient(ID));
			for(GameServerThread foo : clients){
				addButton(foo);
			}
			players--;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void addThread(Socket socket) {
		if (players < 50) {
			clients.add(new GameServerThread(this, socket));
			try {
				clients.get(players).open();
				clients.get(players).start();
				clients.get(players).send(graph.getCurrent());
				clients.get(players).send("$" +"-"+ 20 + "-" + 350+ "-" + 350 + "-");
				addButton(clients.get(players));
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
	private void addButton(GameServerThread foo){
		JButton temp = new JButton("" + foo.getID());
		panel.add(temp);
		scrollPane.setViewportView(panel);
	}
}
