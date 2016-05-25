import java.net.*;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class GameServer implements Runnable {
	private ArrayList<GameServerThread> clients;
	private int players;
	private Graph graph;
	private JPanel panel;
	private JScrollPane scrollPane;
	private JFrame frame;
	private boolean mobs;
	public GameServer(int port) {
		clients = new ArrayList<GameServerThread>();
		graph = new Graph();
		launch();
		Thread thread = new Thread(this);
        thread.start();
		System.out.println("Binding to port " + port + ", please wait  ...");
	}
	private void launch(){
		frame = new JFrame("Game Server");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JCheckBox chckbxMobs = new JCheckBox("Mobs");
		chckbxMobs.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						graph.generateMobs();
                        mobs = !mobs;
                        for(GameServerThread foo : clients){
                        	graph.startRoomMobs(foo.getRoom());
                        }
					}});
		chckbxMobs.setBounds(8, 241, 94, 23);
		frame.getContentPane().add(chckbxMobs);

		JTextArea textArea = new JTextArea();
		textArea.setBounds(8, 0, 277, 212);
		textArea.setEditable(false);
		frame.getContentPane().add(textArea);

		JButton btnEndGame = new JButton("End Game");
		btnEndGame.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						close();
					}});
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
		int frames = 0;
		while(true){
			if(mobs){
				if(frames % 10 == 0){
					sendMobs();
				}
				if(frames > 1000000000){
					frames = 0;
				}
			}
			try {
				Thread.sleep(10);
				frames++;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	private void sendMobs(){
		for(GameServerThread foo : clients){
			for(ServerMob bar : graph.getMobs(foo.getRoom())){
				if(bar != null){
					foo.send(bar.toString());
				}
			}
		}
	}
	public synchronized void readInput(GameServerThread foo){
		int remove = -1;
		String[] data = foo.getData();
		if (data != null) {
			switch (data[0]) {
			case "CHANGEROOM":
				removePlayer(foo);
				if(mobs){
					stopTracking(foo);
				}
				if (data[1].equals("RIGHT")) {
					foo.moveRight();
				} else if (data[1].equals("DOWN")) {
					foo.moveDown();
				} else if (data[1].equals("LEFT")) {
					foo.moveLeft();
				} else {
					foo.moveUp();
				}
				if(mobs){
					graph.startRoomMobs(foo.getRoom());
				}
				foo.send(graph.get(foo.getRoom()));
				break;
			case "MOVE":
				move(foo, Integer.parseInt(data[1]), Integer.parseInt(data[2]));
				break;
			case "ATTACK":
				attack(foo.getPlayer().getAttack(), Integer.parseInt(data[1]));
				break;
			case "QUIT":
				remove = foo.getID();
				break;
			case "MOB":
				if(Integer.parseInt(data[4]) > 0){
					updateMob(foo.getRoom(), Integer.parseInt(data[1]), Integer.parseInt(data[2]),
							Integer.parseInt(data[3]), Integer.parseInt(data[4]));
				}else{
					updateMob(foo.getRoom(), Integer.parseInt(data[1]), Integer.parseInt(data[2]),
							Integer.parseInt(data[3]), Integer.parseInt(data[4]));
					removeMob(foo.getRoom(), Integer.parseInt(data[1]));
				}
				break;
			}
		}
		if (remove > -1) {
			remove(remove);
		}
	}
	private void stopTracking(GameServerThread foo){
		for(GameServerThread bar: clients){
			if(!foo.equals(bar) && foo.sameRoom(bar.getRoom())){
				return;
			}
		}
		graph.stopRoomMobs(foo.getRoom());
	}
	private void updateMob(int[] room, int ID, int x, int y, int health){
		graph.getMobs(room)[ID].setX(x);
		graph.getMobs(room)[ID].setY(y);
		graph.getMobs(room)[ID].setHealth(health);
	}
	private void removeMob(int[] room, int ID){
		sendMobs();
		graph.killMob(room, ID);
	}
	private void move(GameServerThread foo, int x, int y){
		foo.getPlayer().set(x,y);
		for(GameServerThread bar: clients){
			if(!foo.equals(bar) && foo.sameRoom(bar.getRoom())){
				bar.send("*-" + foo.toString());//* means other player
				foo.send("*-" + bar.toString());
			}
		}
	}
	private void removePlayer(GameServerThread foo){
		for(GameServerThread bar: clients){
			if(!foo.equals(bar) && foo.sameRoom(bar.getRoom())){
				bar.send("REMOVEPLAYER-" + foo.getID());
				foo.send("REMOVEPLAYER-" + bar.getID());
			}
		}
	}
	private void attack(int attack, int otherID){
		GameServerThread temp = clients.get(findClient(otherID));
		temp.getPlayer().damage(2);
		if(temp.getPlayer().getHealth() <= 0){
			temp.send("QUIT");
		}
		temp.send("ATTACK-" + temp.getPlayer().getHealth() + "-");
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
			removePlayer(clients.get(findClient(ID)));
			clients.get(findClient(ID)).kill();
			clients.remove(findClient(ID));
			
			panel = new JPanel();
			panel.setLayout(new GridLayout(50,1));
			scrollPane.setViewportView(panel);
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
				clients.get(players).send(graph.getStart());
				addButton(clients.get(players));
				players++;
			} catch (IOException ioe) {
				System.out.println("Error opening thread: " + ioe);
			}
		} else
			System.out.println("Client refused: maximum " + 50 + " reached.");
	}
	public void close(){
		for(GameServerThread foo : clients){
			foo.send("QUIT");
			try {
				foo.kill();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.exit(1);
	}
	private void addButton(GameServerThread foo){
		JButton temp = new JButton("" + foo.getID());
		temp.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				clients.get(findClient(Integer.parseInt(((JButton)e.getSource()).getText()))).send("QUIT");
				System.out.println("kicked player");
			}
		});
		panel.add(temp);
		scrollPane.setViewportView(panel);
	}
}
