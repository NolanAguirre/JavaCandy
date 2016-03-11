package animaition;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.JPanel;

import map.TileType;
import map.World;
import player.PlayerI;
import player.Player;
import sprite.SpriteTile;
public class GameDisplay extends JPanel implements Runnable{
	private int count;
	private final PlayerI player;
	private World world;
	private ArrayList<Rectangle> walls;
	private static final long serialVersionUID = 1L;
	public GameDisplay(PlayerI player){
		walls = new ArrayList<Rectangle>();
		this.player = player;
		try {
			world = new World();
		} catch (FileNotFoundException e) {
			throw(new Error("Seed Not Found"));
		}
		requestFocusInWindow();
		player = new Player();
		setPreferredSize(new Dimension(640, 640));
	    setDoubleBuffered(true);
	}	
	private void renderMap(Graphics g){
		int x = 0;
		int y= 0;
		int temp = 0;
		Rectangle rect;
		for(SpriteTile foo : world.getMap().getMapLayout()){
			while(foo.getCompress() > temp){
				g.drawImage(foo.getDefaultImg(),x*32, y*32, null);
				if(foo.getType() == TileType.WALL){
					rect = new Rectangle(x*32,y*32,32,32);
					//g.drawRect(x*32, y*32, 32, 32);
					walls.add(rect);
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
	}
	private void renderPerson(Graphics g){
		player.unfreeze();
		for(Rectangle wall : walls){
			if(player.isTouching(wall)){
				player.preventMotion(wall);
				//g.drawRect(player.getX(),player.getY(),32,32);
				//g.drawRect((int)wall.getX()+2,(int)wall.getY()+47,28,1);
				//g.drawRect((int)wall.getX()+2,(int)wall.getY()-15,28,1); 
				//g.drawRect((int)wall.getX()+47,(int)wall.getY()+2,1,28); 
				//g.drawRect((int)wall.getX()-15,(int)wall.getY()+2,1,28); 
			}
		}
	
		if(count % 20 > 9){
			g.drawImage(player.getDefaultImg(), player.getX()%620, player.getY()%640, null);
		}else if(!player.isMoving()){
			g.drawImage(player.getTransitionImg(), player.getX()%620, player.getY()%640, null);
		}else{
			g.drawImage(player.getMovingImg(), player.getX()%620, player.getY()%640, null);
		}
		if(player.getX() > 620){
			world.renderRight();
			player.set(20,player.getY());
		}else if(player.getX() < 0){
			world.renderLeft();
			player.set(620,player.getY());
		}else if(player.getY() > 620){
			world.renderDown();
			player.set(player.getX(),20);
		}else if(player.getY() < 0){
			world.renderUp();
			player.set(player.getX(),620);
		}
		g.drawString(player.getX() + " " + player.getY(), 50,50);
	}
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        renderMap(g);
        renderPerson(g);
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
					Thread.sleep(30);
					repaint();
					count++;
				} catch (InterruptedException e) {
					System.out.println("Interrupted: " + e.getMessage());
				}
			}
		}
}

