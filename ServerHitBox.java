

import java.awt.Rectangle;
import java.util.ArrayList;

public class ServerHitBox implements Runnable {
	public ServerMap map;
	private ArrayList<Rectangle> walls;
	private byte frames;
	public ServerHitBox() {
		frames = 0;
		map = null;
		Thread thread = new Thread(this);
		thread.start();
	}
	private void renderHitBox() {
		for (ServerMob mob : map.getMobs()) {
			mob.unfreeze();
			for (Rectangle wall : walls) {
				if (mob.isTouching(wall)) {
					mob.preventMotion(wall);
				}
			}
		}
	}
	public void changeMap(ServerMap map){
		this.map = map;
		walls = map.getWalls();
	}
	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(5);
				if(map != null){
					frames++;
					renderHitBox();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
