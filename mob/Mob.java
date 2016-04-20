package mob;

import java.awt.Image;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;

import player.Combat;
import player.Direction;
import sprite.Movable;

public abstract class Mob implements Movable, Combat{
	protected Direction facing;
	protected boolean isMoving;
	protected boolean freezeUp;
	protected boolean freezeDown;
	protected boolean freezeLeft;
	protected boolean freezeRight;
	protected Image img;
	protected int[] imageChain;
	protected int x;
	protected int y;
	protected int attack;
	protected int hp;
	private boolean dead;
	protected boolean isAttacking;
	protected Image loadImage(int cord){
		try{
			return ImageIO.read(Movable.SPRITESHEET).getSubimage(cord*32,0,32,32);
		}catch(IOException ex){
			throw(new Error("Spritesheet Not Found"));
		}
	}
	public void freeze(){
		freezeUp = true;
		freezeDown = true;
		freezeLeft = true;
		freezeRight = true;
	}
	@Override
	public synchronized Image getImg() { // not really sure, better to have it and not need it
		return img;
	}
	@Override
	public int getX() {
		//synchronized(this){
			return x;
		//}
	}
	@Override
	public int getY() {
		//synchronized(this){
			return y;
		//}
	}
	@Override
	public void setMoving(boolean foo) {
		synchronized(this){ // not really sure, better to have it and not need it
			isMoving = foo;
		}
	}
	@Override
	public boolean isMoving() {
		synchronized(this){
			return isMoving;
		}
	}
	@Override
	public synchronized boolean isTouching(Rectangle wall) {
		Rectangle hitBox = new Rectangle(x,y,32,32);
		return hitBox.intersects(wall);
	}
	@Override
	public synchronized void set(int x, int y) {
		if(this.x < x){
			setFacing(Direction.RIGHT);
		}else if(this.x >x){
			setFacing(Direction.LEFT);
		}else if(this.y < y){
			setFacing(Direction.DOWN);
		}else{
			setFacing(Direction.UP);
		}
		this.x = x;
		this.y = y;
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
	@Override
	public synchronized void preventMotion(Rectangle wall) {
		if(isTouching(new Rectangle((int)wall.getX()+2,(int)wall.getY()+46,28,1))){
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
	@Override
	public synchronized  void unfreeze() {
		synchronized(this){
			freezeUp = false;
			freezeDown = false;
			freezeLeft = false;
			freezeRight = false;
		}
	}
	public int getAttack(){
		return attack;
	}
	public void damage(int amount){
		hp -= amount;
		if(hp <=0){
			dead = true;
		}
	} 
	@Override
	public boolean isDead(){
		return dead;
	}
	public void attack(Mob mob){
		//damage(mob.getAttack());
		mob.damage(attack);
		isAttacking = false;
	}
	@Override
	public int getHp(){
		return hp;
	}
	public boolean isAttacking(){
		return isAttacking;
	}
	public void attack(){
		isAttacking = true;
	}
	public void stopAttack(){
		isAttacking = false;
	}
	public boolean getFrozenUp(){
		return freezeUp;
	}
	public boolean getFrozenDown(){
		return freezeDown;
	}
	public boolean getFrozenLeft(){
		return freezeLeft;
	}
	public boolean getFrozenRight(){
		return freezeRight;
	}
	public Direction getFacing(){
		return facing;
	}
	public void setFacing(Direction dir){
		facing = dir;
	}
	public class MobAnimation {
		 private Timer timer;
		 private int image;
		  public MobAnimation(){
			image = 0;
		    timer = new Timer();
		    timer.scheduleAtFixedRate(new Tick(), 1000, 200);
		  }
		  private void update(){
			  img = loadImage(imageChain[image%imageChain.length]);
			  image++;
		  } 
			class Tick extends TimerTask{
				@Override
			    public void run(){
		              update();
			    }
			}
	}

}
