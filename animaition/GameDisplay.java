package animaition;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JPanel;

import map.TileType;
import map.World;
import mob.Enemy;
import mob.Mob;
import player.Player;
import sprite.SpriteTile;
public class GameDisplay extends JPanel implements Runnable{
	private final Player player;
	public World world;
	private ArrayList<Rectangle> walls;
	private int frames;
	private static final long serialVersionUID = 1L;
	public GameState gameState;
	public GameDisplay(Mob player){
	     addMouseListener(new CustomMouseListener());
		gameState = GameState.MAP;
		frames = 0;
		this.player = (Player) player;
		world = new World();
		requestFocusInWindow();
		setPreferredSize(new Dimension(640, 640));
		setDoubleBuffered(true);
	}	
	public void setGameState(){
		if(gameState != GameState.BAG){
			player.freeze();
			gameState = GameState.BAG;
		}else{
			gameState = GameState.MAP;
		}
	}
	private void renderMap(Graphics g){
		walls = new ArrayList<Rectangle>();
		world.getMap().addMob(player);
		int x = 0;
		int y= 0;
		int temp = 0;
		g.setColor(Color.WHITE);
		for(SpriteTile foo : world.getMap().getMapLayout()){ // map stuff
			while(foo.getCompress() > temp){
				g.drawImage(foo.getImg(),x*32, y*32, null);
				if(foo.getType() == TileType.WALL){
					walls.add(new Rectangle(x*32,y*32,32,32));
				}
				x++;
				if(x >= 20){
					y++;
					x=0;
				}
				temp++;
			}
			temp = 0;
		}
		for(Mob mob: world.getMap().getMobs()){
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
					world.getMap().getMobs().remove(mob);
					break;
				}
			}
		}
		for(Mob mob: world.getMap().getMobs()){
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
			world.renderRight();
			player.set(20,player.getY());
		}else if(player.getX() < 0){
			world.renderLeft();
			player.set(630,player.getY());
		}else if(player.getY() > 630){
			world.renderDown();
			player.set(player.getX(),10);
		}else if(player.getY() < 0){
			world.renderUp();
			player.set(player.getX(),630);
		}
	}
	private void renderChest(Graphics g){ ////////// to do
		g.drawImage(world.getMap().getChest().getImage(),200, 200, null);

	}
	private void renderBag(Graphics g){ //////////////// to do
		g.drawImage(player.getImg(),player.getX(), player.getY(), null);
	}
	private void renderGameState(Graphics g){
		switch(gameState){
		case MAP:
			renderMap(g);
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
		renderGameState(g);
	}
	@Override
	public void addNotify(){
		super.addNotify();
		Thread animationLoop = new Thread(this);
		animationLoop.start();
	}
	@Override
	public void run(){
		while(true){
			try {
				Thread.sleep(20);
				frames++;
				repaint();
			} catch (InterruptedException e) {
				System.out.println("Interrupted: " + e.getMessage());
			}
		}
	}
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
			if(world.getMap().getChest() != null){
				Rectangle temp = new Rectangle(world.getMap().getChest().getX(), world.getMap().getChest().getY(), 32, 32);
				if(temp.contains(mouse.getPoint()) && player.isTouching(temp)){
					gameState = GameState.CHEST;
				}
			}
		}
		@Override
		public void mouseReleased(MouseEvent arg0) {
			
		}
		
	}
}

