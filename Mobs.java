import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class Mobs {
	private int x;
	private int y;
	private int health;
	private int attack;
	public Mobs(int x, int y){
		this.x = x;
		this.y = y;
		health = (int)((Math.random()+1) * 2);
		attack = (int)((Math.random()+1) * 100);
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	public int getAttack() {
		return attack;
	}
	public class MobAnimation {
		 private Timer timer;
		  public MobAnimation(){
		    timer = new Timer();
		    timer.scheduleAtFixedRate(new Tick(), 500, 200);
		  }
		  private void update(){
			  Random random=new Random();
			  int randomNumber = (random.nextInt(9)-4);
			  x += randomNumber;
			  randomNumber = (random.nextInt(9)-4);
			  y += randomNumber;
		  }
			class Tick extends TimerTask{
				@Override
			    public void run(){
		              update();
			    }
			}
	}
}
