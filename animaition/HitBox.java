package animaition;

import java.awt.Rectangle;
import java.util.ArrayList;

import map.Map;
import mob.Mob;
import player.Direction;
import player.Player;

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
		for (Mob mob : map.getMobs()) {
			if (mob instanceof Player && !mob.equals(player) && player.isAttacking() && player.isTouching(new Rectangle(mob.getX(), mob.getY(), 32, 32))) {
				client.sendAttack(mob.getID());
				player.stopAttack();
			}
		}
		for (Mob mob : map.getMobs()) {
			mob.unfreeze();
			for (Rectangle wall : walls) {
				if (mob.isTouching(wall)) {
					mob.preventMotion(wall);
				}
			}
		}
		if (player.getX() > 630) {
			client.changeDirection(Direction.RIGHT);
			player.set(10, player.getY());
			player.freeze();
		} else if (player.getX() < 0) {
			client.changeDirection(Direction.LEFT);
			player.set(630, player.getY());
			player.freeze();
		} else if (player.getY() > 630) {
			client.changeDirection(Direction.DOWN);
			player.set(player.getX(), 10);
			player.freeze();
		} else if (player.getY() < 0) {
			client.changeDirection(Direction.UP);
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
				Thread.sleep(5);
				if(map != null){
					renderHitBox();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
