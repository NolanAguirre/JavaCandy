package sprite;

import java.awt.Image;
import java.io.File;

public interface Sprite { // has an image in the game
	final static File SPRITESHEET = new File("spritesheet.png");
	final int HEIGHT = 32;
	final int WIDTH = 32;
	Image getImg();
}
