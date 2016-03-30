package animaition;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.JPanel;

import map.TileType;
import map.World;
import sprite.Movable;
import player.Mob;
import player.Player;
import sprite.SpriteTile;
public class GameDisplay extends JPanel implements Runnable{
	private int count;
	private final Mob player;
	private World world;
	private ArrayList<Rectangle> walls;
	private static final long serialVersionUID = 1L;
	public GameDisplay(Mob player){
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
		walls = new ArrayList<Rectangle>();
		int x = 0;
		int y= 0;
		int temp = 0;
		Rectangle rect;
		g.setColor(Color.WHITE);
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
		if(world.getMap().getEnemys().size() > 0){
			for(Mob mob: world.getMap().getEnemys()){
				mob.unfreeze();
				for(Rectangle wall : walls){
					if(mob.isTouching(wall)){
						mob.preventMotion(wall);
						//g.drawRect(player.getX(),player.getY(),32,32);
						//g.drawRect((int)wall.getX()+2,(int)wall.getY()+47,28,1);
						//g.drawRect((int)wall.getX()+2,(int)wall.getY()-15,28,1); 
						//g.drawRect((int)wall.getX()+47,(int)wall.getY()+2,1,28); 
						//g.drawRect((int)wall.getX()-15,(int)wall.getY()+2,1,28); 
					}
				}
				
			}
			for(Mob mob: world.getMap().getEnemys()){
				if(mob.isTouching(new Rectangle(player.getX(), player.getY(), Movable.HEIGHT, Movable.WIDTH))){
					mob.attack(player);
					if(mob.isDead()){
						world.getMap().getEnemys().remove(mob);
						break;
					}
				}
				g.drawImage(mob.getDefaultImg(),mob.getX(), mob.getY(), null);
				g.drawString("HP: " + mob.getHp(),mob.getX() - 6, mob.getY() - 3);
			}
		}		
	}
	private void renderPerson(Graphics g){
		if(player.isDead()){
			System.out.println("Game over");
			System.exit(0);
		}
		for(Rectangle wall : walls){
			if(player.isTouching(wall)){
				player.preventMotion(wall);
				//g.drawRect(player.getX(),player.getY(),32,32);
				//g.drawRect((int)wall.getX()+2,(int)wall.getY()+46,28,1);
				//g.drawRect((int)wall.getX()+2,(int)wall.getY()-15,28,1); 
				//g.drawRect((int)wall.getX()+46,(int)wall.getY()+2,1,28); 
				//g.drawRect((int)wall.getX()-15,(int)wall.getY()+2,1,28); 
			}
		}
		for(Mob mob: world.getMap().getEnemys()){
			if(player.isTouching(new Rectangle(mob.getX(), mob.getY(), Movable.HEIGHT, Movable.WIDTH))){
				if(player.isAttacking()){
					System.out.println("hi");
					player.attack(mob);
				}
				//g.drawRect(player.getX(),player.getY(),32,32);
				//g.drawRect((int)wall.getX()+2,(int)wall.getY()+47,28,1);
				//g.drawRect((int)wall.getX()+2,(int)wall.getY()-15,28,1); 
				//g.drawRect((int)wall.getX()+47,(int)wall.getY()+2,1,28); 
				//g.drawRect((int)wall.getX()-15,(int)wall.getY()+2,1,28); 
			}
		}
		
		if(!player.isMoving()){
			g.drawImage(player.getDefaultImg(), player.getX()%620, player.getY()%640, null);
		}else{
			if(count % 20 > 9){
				g.drawImage(player.getTransitionImg(), player.getX()%620, player.getY()%640, null);
			}else{
				g.drawImage(player.getMovingImg(), player.getX()%620, player.getY()%640, null);
			}
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
		g.drawString("HP: " + player.getHp(),player.getX() - 6, player.getY() - 3);
		g.drawString(player.getX() + " " + player.getY(), 50,50);
	}
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        renderMap(g);
        player.unfreeze();
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
					Thread.sleep(20);
					repaint();
					count++;
				} catch (InterruptedException e) {
					System.out.println("Interrupted: " + e.getMessage());
				}
			}
		}
}

