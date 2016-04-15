package sprite;

import java.awt.Image;
import java.io.File;

public interface Sprite { // has an image in the game
	final static File SPRITESHEET = new File("spritesheet.png");
	Image getImg();
}
