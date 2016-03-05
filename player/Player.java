package player;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

import sprite.Movable;
import sprite.Sprite;

public class Player implements Movable{
	private final Image DEFAULT;
	private final Image TRANSITION;
	private final Image MOVINGTRANSITION;
	private int x;
	private int y;
	private boolean isMoving;
	public Player(){
		x = 100;
		y = 100;
		try{
            DEFAULT = ImageIO.read(Sprite.SPRITESHEET).getSubimage(0,0,Sprite.HEIGHT,Sprite.WIDTH);
            TRANSITION = ImageIO.read(Sprite.SPRITESHEET).getSubimage(32,0,Sprite.HEIGHT,Sprite.WIDTH);
            MOVINGTRANSITION = ImageIO.read(Sprite.SPRITESHEET).getSubimage(64,0,Sprite.HEIGHT,Sprite.WIDTH);
        }catch(IOException ex){
            throw(new Error("Spritesheet Not Found"));
        }
	}
	@Override
	public synchronized Image getDefaultImg() { // not really sure, better to have it and not need it
		return DEFAULT;
	}
	@Override
	public synchronized Image getTransitionImg() { // not really sure, better to have it and not need it
		return TRANSITION;
	}
	@Override
	public synchronized Image getMovingTransitionImg() { // not really sure, better to have it and not need it
		return MOVINGTRANSITION;
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
}
