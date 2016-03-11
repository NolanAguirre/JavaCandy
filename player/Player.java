package player;

import java.awt.Image;
import java.io.IOException;
import java.awt.Rectangle;

import javax.imageio.ImageIO;


public class Player implements PlayerI{
	private int still;
	private int transition;
	private int moving;
	private int x;
	private int y;
	private boolean legs;
	private boolean isMoving;
	private boolean freezeUp;
	private boolean freezeDown;
	private boolean freezeLeft;
	private boolean freezeRight;
	public Player(){
		x = 375;
		y = 375;
        still = 0;
        transition = 1;
        moving = 2;
	}
	private Image loadImage(int cord){
		try{
			return ImageIO.read(PlayerI.SPRITESHEET).getSubimage(cord*32,0,PlayerI.HEIGHT,PlayerI.WIDTH);
		}catch(IOException ex){
			throw(new Error("Spritesheet Not Found"));
		}
	}
	@Override
	public synchronized Image getDefaultImg() { // not really sure, better to have it and not need it
		return loadImage(still);
	}
	@Override
	public synchronized Image getTransitionImg() { // not really sure, better to have it and not need it
		return loadImage(transition);
	}
	@Override
	public synchronized Image getMovingImg() { // not really sure, better to have it and not need it
		return loadImage(moving);
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
	public void move(int x, int y) {
		boolean flag = true;
			if(y < 0){
				still = 10;
			    transition = 11;
			    if(legs){
			    	moving = 12;	
			    }else{
			    	legs = !legs;
			    	moving = 13;
			    }
				if(freezeUp){
					this.x += x;
					flag = false;
				}
			}
			if(y > 0){
				still = 6;
			    transition = 7;
			    if(legs){
				   	moving = 8;	
				   }else{
				   	legs = !legs;
				   	moving = 9;
				   }
				if(freezeDown){
					this.x += x;
					flag = false;
				}
			}
			if(x < 0 ){
				still = 3;
			    transition = 4;
			    moving = 5;
				if(freezeLeft){
					this.y += y;
					flag = false;
				}
			}
			if(x > 0){
				 still = 0;
			     transition = 1;
			     moving = 2;
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
	public void set(int x, int y) {
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
		if(isTouching(new Rectangle((int)wall.getX()+2,(int)wall.getY()+47,28,1))){
			preventMotion(Direction.UP);
		}
		if(isTouching(new Rectangle((int)wall.getX()+2,(int)wall.getY()-15,28,1))){
			preventMotion(Direction.DOWN);
		}
		if(isTouching(new Rectangle((int)wall.getX()+47,(int)wall.getY()+2,1,28))){
			preventMotion(Direction.LEFT);
		}
		if(isTouching(new Rectangle((int)wall.getX()-15,(int)wall.getY()+2,1,28))){
			preventMotion(Direction.RIGHT);
		}
	}
	@Override
	public synchronized  void unfreeze() {
		freezeUp = false;
		freezeDown = false;
		freezeLeft = false;
		freezeRight = false;	
	}
}
