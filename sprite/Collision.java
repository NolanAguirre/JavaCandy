package sprite;

public interface Collision { // makes and moves hitboxes 
	void generateRect();
	boolean isTouching();
	void moveRect(int x, int y);
}
