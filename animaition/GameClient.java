package animaition;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JPanel;

import map.Map;
import map.TileType;
import mob.Enemy;
import mob.Mob;
import player.Direction;
import player.Player;
import sprite.SpriteTile;
public class GameClient extends JPanel implements Runnable{
	private ArrayList<Player> players;
	private Player player;
	public Map map;
	private ArrayList<Rectangle> walls;
	private int frames;
	private static final long serialVersionUID = 1L;
	private GameState gameState;
	private boolean worldIsCreated;
	
	
	//////////////////////////
	private Socket socket = null; //server socket 
	private DataOutputStream streamOut = null; // output steam
	private GameClientThread client = null; // input steam from server
	public GameClient(String serverName, int serverPort) {
		System.out.println("Establishing connection. Please wait ...");
		try {
			players = new ArrayList<Player>();
			gameState = GameState.MAP;
			frames = 0;
			addKeyListener(new CustomKeyListener());
		    addMouseListener(new CustomMouseListener());
			setFocusable(true);
			requestFocusInWindow();
			setPreferredSize(new Dimension(640, 640));
			setDoubleBuffered(true);
			socket = new Socket("0.0.0.0", 7958);
			player = new Player(socket.getLocalPort());
			System.out.println("Connected: " + socket);
			streamOut = new DataOutputStream(socket.getOutputStream());
			client = new GameClientThread(this,socket); // makes listener thread only
		} catch (UnknownHostException uhe) {
			System.out.println("Host unknown: " + uhe.getMessage());
		} catch (IOException ioe) {
			System.out.println("Unexpected exception: " + ioe.getMessage());
		}
	}
	public void readInput(){
		String[] data = client.getData();
		if(data.length > 1){
			switch(data[0]){
				case "*": addPlayer(Integer.parseInt(data[1]),Integer.parseInt(data[2]), Integer.parseInt(data[3]));
					break;
			}
		}else{
			if(data[0].length() > 200){
				map = new Map(data[0]);
				setUpWorld();
			}
		}
	}
	private void addPlayer(int id, int x, int y){
		boolean flag = true;
		for(Player foo: players){
			if(foo.getID() == id){
				flag = false;
				foo.set(x, y);
			}
		}
		if(flag){
			players.add(new Player(id));
		}
	}
	///////////////////////
	public void setUpWorld(){
		worldIsCreated = true;
	}
	public void setGameState(){
		if(gameState != GameState.BAG){
			player.freeze();
			gameState = GameState.BAG;
		}else{
			gameState = GameState.MAP;
		}
	}
	private void renderMap(Graphics g) throws IOException{
		walls = new ArrayList<Rectangle>();
		map.addMob(player);
		for(Player foo: players){
			map.addMob(foo);
		}
		int x = 0;
		int y= 0;
		g.setColor(Color.WHITE);
		
		for(SpriteTile foo : map.getMapLayout()){ // map stuff
				g.drawImage(foo.getImg(),x*32, y*32, null);
				if(foo.getType() == TileType.WALL){
					walls.add(new Rectangle(x*32,y*32,32,32));
				}
				x++;
				if(x == 20){
					y++;
					x = 0;
				}
			}
		
		for(Mob mob: map.getMobs()){
			g.drawImage(mob.getImg(),mob.getX(), mob.getY(), null);
			g.drawString("HP: " + mob.getHp(),mob.getX() - 6, mob.getY() - 3);
			if(mob instanceof Enemy && mob.isTouching(new Rectangle(player.getX(), player.getY(), 32, 32))){
				if(player.isAttacking()){
					player.attack(mob);
				}
				if(frames > 60){
					mob.attack(player);
					frames = 0;
				}
				if(mob.isDead()){
					map.getMobs().remove(mob);
					break;
				}
			}
		}
		for(Mob mob: map.getMobs()){
			mob.unfreeze();
			for(Rectangle wall : walls){
				if(mob.isTouching(wall)){
					mob.preventMotion(wall);
				}
			}
		}
		if(player.isDead()){
			System.out.println("Game over");
			System.exit(0);
		}
		if(player.getX() > 630){
			streamOut.writeUTF("#-" + socket.getLocalPort() + "-"+ "RIGHT");
			player.set(20,player.getY());
			players = new ArrayList<Player>();
		}else if(player.getX() < 0){
			streamOut.writeUTF("#-" + socket.getLocalPort() + "-"+"LEFT");
			player.set(630,player.getY());
			players = new ArrayList<Player>();
		}else if(player.getY() > 630){
			streamOut.writeUTF("#-" + socket.getLocalPort() + "-"+ "DOWN");
			player.set(player.getX(),10);
			players = new ArrayList<Player>();
		}else if(player.getY() < 0){
			streamOut.writeUTF("#-" + socket.getLocalPort() + "-"+ "UP");
			player.set(player.getX(),630);
			players = new ArrayList<Player>();
		}
		g.drawString(player.getX()+" " +  player.getY(), 200, 200);
	}
	private void renderChest(Graphics g){ ////////// to do
		
	}
	private void renderBag(Graphics g){ //////////////// to do
		g.drawImage(player.getImg(),player.getX(), player.getY(), null);
	}
	private void renderGameState(Graphics g){
		switch(gameState){
		case MAP:
			try {
				renderMap(g);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case CHEST:
			renderChest(g);
			break;
		case BAG:
			renderBag(g);
			break;
		}
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(worldIsCreated){
			renderGameState(g);
		}
	}
	@Override
	public void addNotify(){
		super.addNotify();
		Thread animationLoop = new Thread(this);
		animationLoop.start();
	}
	public void write(){
		try {
			streamOut.writeUTF("$-" + socket.getLocalPort() + "-"+player.getX() +"-"+player.getY());
			streamOut.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	@Override
	public void run(){
		while(true){
			try {
				Thread.sleep(16);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			frames++;
			repaint();
		}
	}
	//////////////////////////
	class CustomMouseListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent mouse) {
			//if(world.getMap().getChest() != null){
			//	Rectangle temp = new Rectangle(world.getMap().getChest().getX(), world.getMap().getChest().getY(), 32, 32);
			//	if(temp.contains(mouse.getPoint()) && player.isTouching(temp)){
			//		gameState = GameState.CHEST;
			//	}
			//}
		}
		@Override
		public void mouseReleased(MouseEvent arg0) {
			
		}
		
	}
	class CustomKeyListener implements KeyListener{
        @Override
        public void keyTyped(KeyEvent e) {
        }
        @Override
        public void keyPressed(KeyEvent e) {
        	write();
            if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
            	if(!player.getFrozenRight()){
            		player.move(7,0);
            		player.setMoving(true);
            	}
            	if(player.getFacing() != Direction.RIGHT){
            		player.setFacing(Direction.RIGHT);
            	}
            }else if (e.getKeyCode() == KeyEvent.VK_LEFT  || e.getKeyCode() == KeyEvent.VK_A) {
            	if(!player.getFrozenLeft()){
            		player.move(-7,0);
            		player.setMoving(true);
            	}
            	if(player.getFacing() != Direction.LEFT){
            		player.setFacing(Direction.LEFT);
            	}
            }else if (e.getKeyCode() == KeyEvent.VK_UP ||  e.getKeyCode() == KeyEvent.VK_W) {
            	if(!player.getFrozenUp()){
            		player.move(0,-7);
            		player.setMoving(true);
            	}
            	if(player.getFacing() != Direction.UP){
            		player.setFacing(Direction.UP);
            	}
            }else if (e.getKeyCode() == KeyEvent.VK_DOWN ||  e.getKeyCode() == KeyEvent.VK_S) {
            	if(!player.getFrozenDown()){
            		player.move(0,7);
            		player.setMoving(true);
            	}
            	if(player.getFacing() != Direction.DOWN){
            		player.setFacing(Direction.DOWN);
            	}
             }else if(e.getKeyCode() == KeyEvent.VK_E){
            	 setGameState();
            }
        }
        @Override
        public void keyReleased(KeyEvent e) {
        	if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                player.attack();
             }
           player.setMoving(false);
        }
    }
}

