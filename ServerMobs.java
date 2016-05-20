import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class ServerMobs {
	private int x;
	private int y;
	private int health;
	private int attack;
	private int ID;
	public ServerMobs(int x, int y, int ID){
		this.x = x;
		this.y = y;
		this.ID = ID;
		health = (int)((Math.random()+1) * 2);
		attack = (int)((Math.random()+1) * 100);
	}
	public void move(int x, int y){
		this.x += x;
		this.y += y;
	}
	public int getID(){
		return ID;
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
	@Override
	public String toString(){
		return "MOB-"+ ID + "-" + x + "-" + y + "-" + health;
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
