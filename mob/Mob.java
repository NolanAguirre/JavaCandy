package mob;

import java.awt.Image;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;

import player.Direction;
import sprite.Movable;
import sprite.Sprite;

public abstract class Mob implements Sprite, Movable{
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
	protected int id;
	protected Image loadImage(int cord){
		try{
			return ImageIO.read(Sprite.SPRITESHEET).getSubimage(cord*32,0,32,32);
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
	public Image getImg() {
		return img;
	}
	@Override
	public int getX() {
		return x;
	}
	@Override
	public int getY() {
		return y;
	}
	@Override
	public void setMoving(boolean foo) {
		isMoving = foo;
	}
	@Override
	public boolean isMoving() {
		return isMoving;
	}
	@Override
	public void set(int x, int y) {
		if(this.x < x){
			setFacing(Direction.RIGHT);
		}else if(this.x >x){
			setFacing(Direction.LEFT);
		}else if(this.y < y){
			setFacing(Direction.DOWN);
		}else if(this.y > y){
			setFacing(Direction.UP);
		}
		switch(facing){
			case LEFT:
				imageChain = new int[]{3,4,5};
				break;
			case DOWN:
				imageChain = new int[]{6,7,6,8};
				break;
			case UP:
				imageChain = new int[]{9,10,9,11};
				break;
			case RIGHT:
				imageChain = new int[]{0,1,2}; 
				break;
			}
		this.x = x;
		this.y = y;
	}
	public void preventMotion(Direction direction) {
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
	public void unfreeze() {
		freezeUp = false;
		freezeDown = false;
		freezeLeft = false;
		freezeRight = false;
	}
	public int getAttack(){
		return attack;
	}
	public void damage(int amount){
		hp -= amount;
		if(hp <=0){
			hp = 0;
			dead = true;
		}
	} 
	public boolean isDead(){
		return dead;
	}
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
	public void setHP(int hp){
		if(hp <= 100){
			this.hp = hp;
		}
	}
	public int getID(){
		return id;
	}
	public void setID(int id){
		this.id = id;
	}
}
