package animaition;

import java.awt.Rectangle;
import java.util.ArrayList;

import map.Map;
import mob.Enemy;
import mob.Mob;
import player.Direction;
import player.Player;

public class HitBox implements Runnable {
	private Player player;
	public Map map;
	private ArrayList<Rectangle> walls;
	private GameClient client;
	private int frames;
	public HitBox(Player player, GameClient client) {
		this.player = player;
		this.client = client;
		map = null;
		Thread thread = new Thread(this);
		thread.start();
	}
	private void renderHitBox() {
		Rectangle playerHitBox = new Rectangle(player.getX(), player.getY(), 32,32);
		for (Mob mob : map.getMobs()) {
			if (mob instanceof Player && !mob.equals(player) && player.isAttacking()
					&& playerHitBox.intersects(new Rectangle(mob.getX(), mob.getY(), 32, 32))) {
				if(frames % 20 == 0){
					client.sendAttack(mob.getID());
					player.stopAttack();
				}
			}else if(mob instanceof Enemy && playerHitBox.intersects(new Rectangle(mob.getX(), mob.getY(), 32, 32))){
				if(player.isAttacking()){
					mob.damage(player.getAttack());
					client.send(((Enemy)mob).toString());
					player.stopAttack();
					System.out.println(mob);
				}
				if(frames % 100 == 0){
					player.damage(mob.getAttack());
				}
			}
		}
		for (Mob mob : map.getMobs()) {
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
		for(int x = 0; x < map.getMobs().size(); x++){
			if(map.getMobs().get(x).getHp() <= 0){
				map.removePlayer(x);
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
		if(frames > 401){
			player.setHP(player.getHp()+3);
			frames = 0;
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
					frames++;
					renderHitBox();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
