package animaition;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import map.Map;
import player.Player;

public class GameClient implements Runnable {
	private ArrayList<Player> players;
	private Player player;
	private Map map;
	private GameDisplay display;
	private HitBox hitbox;
	private Socket socket = null; // server socket
	private DataOutputStream streamOut = null; // output steam
	private GameClientThread client = null; // input steam from server
	
	public GameClient(String serverName, int serverPort, Player player) {
		System.out.println("Establishing connection. Please wait ...");
		this.player = player;
		display = new GameDisplay();
		hitbox = new HitBox(player, this);
		try {
			players = new ArrayList<Player>();
			socket = new Socket("0.0.0.0", 7958);
			this.player.setID(socket.getLocalPort());
			System.out.println("Connected: " + socket);
			streamOut = new DataOutputStream(socket.getOutputStream());
			client = new GameClientThread(this, socket); // makes listener
			Thread thread = new Thread(this);
			thread.start();
		} catch (UnknownHostException uhe) {
			System.out.println("Host unknown: " + uhe.getMessage());
		} catch (IOException ioe) {
			System.out.println("Unexpected exception: " + ioe.getMessage());
		}
	}
	public GameDisplay getDisplay(){
		return display;
	}
	public void readInput() {
		String[] data = client.getData();
		if (data.length > 1) {
			switch (data[0]) {
			case "*":
				addPlayer(Integer.parseInt(data[1]), Integer.parseInt(data[2]), Integer.parseInt(data[3]),
						Integer.parseInt(data[4]));
				break;
			case "!":
				removePlayer(Integer.parseInt(data[1]));
				break;
			case "@":
				player.setHP(Integer.parseInt(data[1])); // attacked
				break;
			}
		} else {
			if (data[0].length() > 200) {
				map = new Map(data[0]);
				map.addMob(player);
				hitbox.changeMap(map);
				display.changeMap(map);
			}
		}
	}

	private void removePlayer(int id) {
		Player temp = new Player(id);
		for (Player foo : players) {
			if (foo.getID() == id) {
				temp = foo;
				break;
			}
		}
		map.removePlayer(players.remove(players.indexOf(temp)));

	}

	private void addPlayer(int id, int x, int y, int hp) {
		boolean flag = true;
		for (Player foo : players) {
			if (foo.getID() == id) {
				flag = false;
				foo.set(x, y);
				foo.setHP(hp);
				break;
			}
		}
		if (flag) {
			players.add(new Player(id));
		}
	}

	public void sendAttack(int ID){
		player.stopAttack();
		try {
			streamOut.writeUTF("@-" + ID);
			streamOut.flush();
			if (player.getHp() <= 0) {
				System.out.println("Game over");
				streamOut.writeUTF("QUIT");
				System.exit(0);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void changeDirection() {
		try {
			if (player.getX() > 630) {
				streamOut.writeUTF("#-RIGHT");
				write();
			} else if (player.getX() < 0) {
				streamOut.writeUTF("#-LEFT");
				write();
			} else if (player.getY() > 630) {
				streamOut.writeUTF("#-DOWN");
				write();
			} else if (player.getY() < 0) {
				streamOut.writeUTF("#-UP");
			}
			streamOut.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void write() {
		try {
			streamOut.writeUTF("$-" + player.getX() + "-" + player.getY());
			streamOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		int frames = 0;
		write();
		while (true) {
			try {
				frames++;
				if (frames % 40 == 0) {
					write();
				}
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
