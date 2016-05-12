package animaition;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import map.Map;
import mob.Mob;
import player.Direction;
import player.Player;

public class GameClient implements Runnable {
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
			socket = new Socket("10.229.1.88", 7958);
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
	public synchronized void readInput() {
		String[] data = client.getData();
		if (data.length > 1) {
			switch (data[0]) {
			case "*":
				addPlayer(Integer.parseInt(data[1]), Integer.parseInt(data[2]), Integer.parseInt(data[3]),Integer.parseInt(data[4]));
				break;
			case "!":
				removePlayer(Integer.parseInt(data[1]));
				break;
			case "@":
				player.setHP(Integer.parseInt(data[1])); // attacked
				break;
			case "QUIT":
				System.exit(1);
				break;
			}
		} else {
			if (data[0].length() > 50) {
				map = new Map(data[0]);
				map.addMob(player);
				hitbox.changeMap(map);
				display.changeMap(map);
			}
		}
	}
	public void close(){
		try {
			streamOut.writeUTF("QUIT");
			streamOut.close();
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void removePlayer(int id) {
		Mob temp = new Player(id);
		for (Mob foo :  map.getMobs()) {
			if (foo.getID() == id) {
				temp = foo;
				break;
			}
		}
		map.removePlayer(map.getMobs().indexOf(temp));

	}
	
	private void addPlayer(int id, int x, int y, int hp) {
		boolean flag = true;
		for (Mob foo : map.getMobs()) {
			if (foo.getID() == id) {
				flag = false;
				foo.set(x, y);
				foo.setHP(hp);
				break;
			}
		}
		if (flag) {
			map.addMob(new Player(id));
		}
	}

	public void sendAttack(int ID){
		try {
			streamOut.writeUTF("@-" + ID);
			streamOut.flush();
			if (player.getHp() <= 0) {
				System.out.println("Game over");
				streamOut.writeUTF("QUIT");
				System.exit(0);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void changeDirection(Direction dir) {
		try {
			if (dir == Direction.RIGHT) {
				streamOut.writeUTF("#-RIGHT");
				write();
			} else if (dir == Direction.LEFT) {
				streamOut.writeUTF("#-LEFT");
				write();
			} else if (dir == Direction.DOWN) {
				streamOut.writeUTF("#-DOWN");
				write();
			} else if (dir == Direction.UP) {
				streamOut.writeUTF("#-UP");
			}
			streamOut.flush();
		} catch (IOException e) {
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
		while (true) {
			try {
				if (player.getHp() <= 0) {
					close();
				}
				write();
				readInput();
				Thread.sleep(10);
			} catch (InterruptedException e) {
				System.out.println("Out Stream closed");
			}
		}
	}
}
