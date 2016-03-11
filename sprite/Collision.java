package sprite;

import java.awt.Rectangle;


public interface Collision { // makes and moves hitboxes 
	boolean isTouching(Rectangle wall);
	void preventMotion(Rectangle wall);
}
