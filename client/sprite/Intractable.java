package sprite;

public interface Intractable extends Sprite { // items that can be had
	void pickUp();
	String getInfo();
	void use();
	void destroy();
}
