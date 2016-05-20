package animaition;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import map.Map;
import mob.Mob;
import sprite.Sprite;

public class GameDisplay extends JPanel implements Runnable{
	private static final long serialVersionUID = 1L;
	public Image WOOD;
	public Image WALL;
	public Image BRICK;
	public Image DEFAULT;
	public Map map;
	private GameState gameState;
	public GameDisplay(){
		gameState = GameState.MAP;
		setPreferredSize(new Dimension(640, 640));
		setDoubleBuffered(true);
		map = null;
		try {
			WOOD = ImageIO.read(Sprite.SPRITESHEET).getSubimage(13*32,0,32,32);
			WALL = ImageIO.read(Sprite.SPRITESHEET).getSubimage(14*32,0,32,32);
			BRICK = ImageIO.read(Sprite.SPRITESHEET).getSubimage(15*32,0,32,32);
			DEFAULT = ImageIO.read(Sprite.SPRITESHEET).getSubimage(0,0,32,32);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		private void renderMap(Graphics g){
			for(int x = 0; x < 20; x++){
				for(int y = 0; y < 20; y++){
					switch(map.getMapLayout()[x][y]){
					case 0: 
						g.drawImage(WALL,x*32, y*32, null);
						break;
					case 1: 
						g.drawImage(WOOD,x*32, y*32, null);
						break;
					case 2:
						g.drawImage(BRICK,x*32, y*32, null);
						break; 				
					default: 
						g.drawImage(DEFAULT,x*32, y*32, null);
					}
				}
			}	
		ArrayList<Mob> ml = map.getMobs();
			for(Mob mob : ml){
				g.drawImage(mob.getImg(), mob.getX(), mob.getY(), null);
				g.drawRect(mob.getX(), mob.getY(), 32, 32);
			}
		}
		private void renderChest(Graphics g){ ////////// to do
			
		}
		private void renderBag(Graphics g){ //////////////// to do
			
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
		public void changeMap(Map map){
			this.map = map;
		}
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			if(map != null){
				renderGameState(g);
			}
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
					Thread.sleep(16);
					repaint();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
