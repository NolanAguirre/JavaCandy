package sprite;

import player.Direction;

public interface Movable{ // movable things, they collide  with stuff, mobs and players
	int getX();
	int getY();
	int getID();
	void move(int x, int y);
	void set(int x,int y);
	void setMoving(boolean foo);
	void unfreeze();
	boolean isMoving();
	void preventMotion(Direction direction);
}
