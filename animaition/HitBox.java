package animaition;

import java.awt.Rectangle;
import java.util.ArrayList;

import map.Map;
import player.Player;
import sprite.Movable;

public class HitBox implements Runnable {
	private Player player;
	public Map map;
	private ArrayList<Rectangle> walls;
	private GameClient client;
	public HitBox(Player player, GameClient client) {
		this.player = player;
		this.client = client;
		map = null;
		Thread thread = new Thread(this);
		thread.start();
	}
	private void renderHitBox() {
		for (Movable mob : map.getMobs()) {
			if (!mob.equals(player) && player.isAttacking() && player.isTouching(new Rectangle(mob.getX(), mob.getY(), 32, 32))) {
				client.sendAttack(mob.getID());
			}
		}
		for (Movable mob : map.getMobs()) {
			mob.unfreeze();
			for (Rectangle wall : walls) {
				if (mob.isTouching(wall)) {
					mob.preventMotion(wall);
				}
			}
		}
		if (player.getX() > 630) {
			client.changeDirection();
			player.set(10, player.getY());
			player.freeze();
		} else if (player.getX() < 0) {
			client.changeDirection();
			player.set(630, player.getY());
			player.freeze();
		} else if (player.getY() > 630) {
			client.changeDirection();
			player.set(player.getX(), 10);
			player.freeze();
		} else if (player.getY() < 0) {
			client.changeDirection();
			player.set(player.getX(), 630);
			player.freeze();
		}
	}
	public void changeMap(Map map){
		this.map = map;
		walls = map.getWalls();
	}
	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(10);
				if(map != null){
					renderHitBox();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
