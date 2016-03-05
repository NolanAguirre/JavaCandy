package sprite;

import java.awt.Image;
import java.io.File;

public interface Sprite {
	final File SPRITESHEET = new File("spritesheet.png");
	final int HEIGHT = 32;
	final int WIDTH = 32;
	Image getDefaultImg();
	Image getTransitionImg();
}
