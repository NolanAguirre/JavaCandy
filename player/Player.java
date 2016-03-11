package player;

import java.awt.Image;
import java.io.IOException;
import java.awt.Rectangle;

import javax.imageio.ImageIO;


public class Player implements PlayerI{
	private PlayerSprite DEFAULT;
	private PlayerSprite TRANSITION;
	private PlayerSprite MOVING;
	private int x;
	private int y;
	private boolean isMoving;
	private boolean freezeUp;
	private boolean freezeDown;
	private boolean freezeLeft;
	private boolean freezeRight;
	public Player(){
		x = 375;
		y = 375;
        DEFAULT = PlayerSprite.DEFAULT_DEFAULT;
        TRANSITION = PlayerSprite.DEFAULT_TRANSITION;
        MOVING = PlayerSprite.DEFAULT_MOVING;
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
		switch(DEFAULT){
		case DEFAULT_DEFAULT: return loadImage(0);
		case SWORD_DEFAULT: return loadImage(3);
		case SHIELD_DEFAULT:return loadImage(6);
		default: return loadImage(0);
		}
	}
	@Override
	public synchronized Image getTransitionImg() { // not really sure, better to have it and not need it
		switch(TRANSITION){
		case DEFAULT_TRANSITION: return loadImage(1);
		case SWORD_TRANSITION: return loadImage(4);
		case SHIELD_TRANSITION:return loadImage(7);
		default: return loadImage(0);
		}
	}
	@Override
	public synchronized Image getMovingImg() { // not really sure, better to have it and not need it
		switch(MOVING){
		case DEFAULT_MOVING: return loadImage(2);
		case SWORD_MOVING: return loadImage(5);
		case SHIELD_MOVING:return loadImage(8);
		default: return loadImage(0);
		}
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
			if(freezeUp && y < 0){
				this.x += x;
				flag = false;
			}
			if(freezeDown && y > 0){
				this.x += x;
				flag = false;
			}
			if(freezeLeft && x < 0 ){
				this.y += y;
				flag = false;
			}
			if(freezeRight && x > 0){
				this.y += y;
				flag = false;
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
