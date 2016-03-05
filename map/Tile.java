package map;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

import sprite.Sprite;

public class Tile implements Sprite{
	private final Image DEFAULT;
	private final Image TRANSITION;
	private int compress;
	private TileType type;
	public Tile(TileType type){
		compress = 1;
		this.type = type;
		try{
			switch(type){
				case WOOD_FLOOR:
		            DEFAULT = ImageIO.read(Sprite.SPRITESHEET).getSubimage(128,0,Sprite.HEIGHT,Sprite.WIDTH);
		            TRANSITION = ImageIO.read(Sprite.SPRITESHEET).getSubimage(128,0,Sprite.HEIGHT,Sprite.WIDTH);
		            break;
				case WALL:
					DEFAULT = ImageIO.read(Sprite.SPRITESHEET).getSubimage(160,0,Sprite.HEIGHT,Sprite.WIDTH);
		            TRANSITION = ImageIO.read(Sprite.SPRITESHEET).getSubimage(160,0,Sprite.HEIGHT,Sprite.WIDTH);
		            break;
		        default:
		        	DEFAULT = ImageIO.read(Sprite.SPRITESHEET).getSubimage(192,0,Sprite.HEIGHT,Sprite.WIDTH);
		            TRANSITION = ImageIO.read(Sprite.SPRITESHEET).getSubimage(192,0,Sprite.HEIGHT,Sprite.WIDTH);
		        	
			}
        }catch(IOException ex){
            throw(new Error("File Not Found"));
        }
	}
	@Override
	public Image getDefaultImg() {
		return DEFAULT;
	}
	@Override
	public Image getTransitionImg() {
		return TRANSITION;
	}
	public TileType getType(){
		return type;
	}
	public void compress(){
		compress++;
	}
	public int getCompress(){
		return compress;
	}
}