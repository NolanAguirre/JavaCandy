package sprite;

import java.awt.Image;

public interface Movable extends Sprite, Collision{ // movable things, they collide  with stuff, mobs and players
	int getX();
	int getY();
	void move(int x, int y);
	void setMoving(boolean foo);
	boolean isMoving();
	Image getMovingImg();
}
