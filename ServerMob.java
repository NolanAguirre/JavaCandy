import java.awt.Rectangle;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class ServerMob {
	private Direction facing;
	private boolean isMoving;
	private boolean freezeUp;
	private boolean freezeDown;
	private boolean freezeLeft;
	private boolean freezeRight;
	private int x;
	private int y;
	private int health;
	private int attack;
	private int ID;
	public ServerMob(int x, int y, int ID){
		this.x = x;
		this.y = y;
		this.ID = ID;
		health = (int)((Math.random()+1) * 2);
		attack = (int)((Math.random()+1) * 100);
	}
	public synchronized boolean isTouching(Rectangle wall) {
		Rectangle hitBox = new Rectangle(x,y,32,32);
		return hitBox.intersects(wall);
	}
	private void preventMotion(Direction direction) {
		switch(direction){
		case LEFT:
			freezeLeft = true;
			break;
		case RIGHT:
			freezeRight = true;
			break;
		case UP:
			freezeUp = true;
			break;
		case DOWN:
			freezeDown = true;
			break;
		default:
			
		}	
		
	}
	public synchronized void preventMotion(Rectangle wall) {
		if(isTouching(new Rectangle((int)wall.getX()+2,(int)wall.getY()+42,28,1))){
			preventMotion(Direction.UP);
		}
		if(isTouching(new Rectangle((int)wall.getX()+2,(int)wall.getY()-15,28,1))){
			preventMotion(Direction.DOWN);
		}
		if(isTouching(new Rectangle((int)wall.getX()+46,(int)wall.getY()+2,1,28))){
			preventMotion(Direction.LEFT);
		}
		if(isTouching(new Rectangle((int)wall.getX()-15,(int)wall.getY()+2,1,28))){
			preventMotion(Direction.RIGHT);
		}
	}
	public synchronized  void unfreeze() {
		synchronized(this){
			freezeUp = false;
			freezeDown = false;
			freezeLeft = false;
			freezeRight = false;
		}
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
