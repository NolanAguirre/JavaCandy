package mob;

import java.util.Timer;
import java.util.TimerTask;

import player.Direction;
import player.Mob;

public class Enemy  extends Mob{
	public Enemy(int x, int y){
		new EnemyAI();
		new MobAnimation();
		facing = Direction.DOWN;
		attack = 1;
		hp = 10000;
		this.x = x;
		this.y = y;
        still = loadImage(6);
        imageChain = 32;
	}
	@Override
	public void attack(Mob mob){
		//damage(mob.getAttack());
		mob.damage(attack);
	}
	public void move(int x, int y) {
		boolean flag = true;
			if(y < 0){
				still = loadImage(32);
				if(freezeUp){
					this.x += x;
					flag = false;
				}
			}
			if(y > 0){
				still = loadImage(32);
				if(freezeDown){
					this.x += x;
					flag = false;
				}
			}
			if(x < 0 ){
				still = loadImage(32);
				if(freezeLeft){
					this.y += y;
					flag = false;
				}
			}
			if(x > 0){
				 still = loadImage(32);
				if(freezeRight){
					this.y += y;
					flag = false;
				}
			}
			if(flag){
				this.x += x;
				this.y += y;
			}
	}
	class EnemyAI {
		  private Timer timer;
		  public EnemyAI(){
		    timer = new Timer();
		    timer.scheduleAtFixedRate(new Tick(), 1000, 200);
		  }
		  private void update(){
			  double rand = Math.random();
			  if(rand < .25){
				  move(4,0);
			  }else if(rand < .5){
				  move(-4,0);
			  }else if(rand < .75){
				  move(0,4);
			  }else{
				  move(0,-4);
			  }
		  }
			class Tick extends TimerTask{
				@Override
			    public void run(){
		              update();
			    }
			}
		}
}
