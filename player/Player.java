package player;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Player implements PlayerI{
	private PlayerSprite DEFAULT;
	private PlayerSprite TRANSITION;
	private PlayerSprite MOVING;
	private int x;
	private int y;
	private boolean isMoving;
	public Player(){
		x = 100;
		y = 100;
		generateRect();
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
		// TODO Auto-generated method stub
		return y;
	}
	@Override
	public void move(int x, int y) {
		this.x += x;
		this.y += y;

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
	public void generateRect() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean isTouching() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void moveRect(int x, int y) {
		// TODO Auto-generated method stub
		
	}
}
