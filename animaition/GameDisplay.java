package animaition;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JPanel;

import map.TileType;
import map.World;
import mob.Enemy;
import mob.Mob;
import sprite.Movable;
import sprite.SpriteTile;
public class GameDisplay extends JPanel implements Runnable{
	private final Mob player;
	private World world;
	private ArrayList<Rectangle> walls;
	private int frames;
	private static final long serialVersionUID = 1L;
	public GameDisplay(Mob player){
		frames = 0;
		this.player = player;
		world = new World();
		requestFocusInWindow();
		setPreferredSize(new Dimension(640, 640));
		setDoubleBuffered(true);
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
			if(mob instanceof Enemy && mob.isTouching(new Rectangle(player.getX(), player.getY(), Movable.HEIGHT, Movable.WIDTH))){
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
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		renderMap(g);
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
}

