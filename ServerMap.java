

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;



public class ServerMap implements Runnable{
	private ServerMob[] mobs;
	private ArrayList<Rectangle> walls;
	private boolean running;
	private final int[] ID;
	public ServerMap(String seed, ServerMob[]mobs, int[]ID){
		this.mobs = mobs;
		walls = new ArrayList<Rectangle>();
		this.ID = ID;
		int x = 0;
		int y = 0;
		char[] cA = seed.toCharArray();
		for(char foo : cA){
			if(foo == 'w'){
				walls.add(new Rectangle(x*32, y*32, 32, 32));
			}
			x++;
			if(x >= 20){
				y++;
				x=0;
			}
		}
		start();
	}
	public int[] getID(){
		return ID;
	}
	public void kill(){
		running = false;
	}
	public void start(){
		running = true;
		Thread thread = new Thread(this);
		thread.start();
	}
	private void renderHitBox() {
		for(ServerMob mob : mobs){
			if(mob != null){
				Rectangle mobHitBox = new Rectangle(mob.getX(), mob.getY(), 32,32);
				mob.unfreeze();
				for (Rectangle wall : walls) {
					if (mobHitBox.intersects(wall)) {
						if(mobHitBox.intersects(new Rectangle((int)wall.getX()+2,(int)wall.getY()+42,28,1))){
							mob.preventMotion(Direction.UP);
						}
						if(mobHitBox.intersects(new Rectangle((int)wall.getX()+2,(int)wall.getY()-15,28,1))){
							mob.preventMotion(Direction.DOWN);
						}
						if(mobHitBox.intersects(new Rectangle((int)wall.getX()+46,(int)wall.getY()+2,1,28))){
							mob.preventMotion(Direction.LEFT);
						}
						if(mobHitBox.intersects(new Rectangle((int)wall.getX()-15,(int)wall.getY()+2,1,28))){
							mob.preventMotion(Direction.RIGHT);
						}
					}
				}
			}
		}
	}
	@Override
	public void run() {
		while(running){
			System.out.println("yo im working" + ID[0] + ID[1]);
			renderHitBox();
			Random random=new Random();
			for(ServerMob foo : mobs){
				if(foo != null){
					foo.move((random.nextInt(5)-2), (random.nextInt(5)-2));
				}
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
