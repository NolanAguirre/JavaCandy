package sprite;

import java.awt.Image;

public interface Movable extends Sprite{
	int getX();
	int getY();
	void move(int x, int y);
	void setMoving(boolean foo);
	boolean isMoving();
	Image getMovingTransitionImg();
}
