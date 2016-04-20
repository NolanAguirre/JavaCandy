package map;

import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import mob.Enemy;
import mob.Mob;
import sprite.Sprite;
import sprite.SpriteTile;

public class Map {
	private String seed;
	private ArrayList<SpriteTile>map;
	private ArrayList<Mob> mobs;
	private int x;
	private int y;
	public Map(String seed){
		map = new ArrayList<SpriteTile>();
		mobs = new ArrayList<Mob>();
		this.seed = seed;
		generate();
	}
	@Override
	public String toString(){
		return seed;
	}
	private void generate(){
		x = 0;
		y = 0;
		for(char foo : seed.toCharArray()){
			switch(foo){
			case 'w': 
				map.add(new Tile(TileType.WALL));
				break;
			case 'm': 
				mobs.add(new Enemy(x,y));
				map.add(new Tile(TileType.WALL));
				break;
			case 'b': 
				map.add(new Tile(TileType.BRICK));
				break;
			case 'f':
				map.add(new Tile(TileType.WOOD_FLOOR));
				break; 				
			default: 
				map.add(new Tile(TileType.FILLER));
			}
			x++;
			if(x >= 20){
				y++;
				x=0;
			}
		}
	}
	public ArrayList<Mob> getMobs(){
		return mobs;
	}
	public void addMob(Mob player){
		if(!mobs.contains(player)){
			mobs.add(player);
		}
	}
	public ArrayList<SpriteTile> getMapLayout() {
		return map;
	}
	//////////////////////// blocks/tiles
	class Tile implements SpriteTile{
		private Image DEFAULT;
		private TileType type;
		public Tile(TileType type){
			this.type = type;
			switch(type){
			case WOOD_FLOOR:
				loadImage(13);
				break;
			case WALL:
				loadImage(14);
				break;
			case BRICK: 
				loadImage(15);
				break;
			default:
				loadImage(0);
			}
		}
		private void loadImage(int cords){
			try{
				DEFAULT = ImageIO.read(Sprite.SPRITESHEET).getSubimage(cords*32,0,32,32);
			}catch(IOException ex){
				throw(new Error("File Not Found"));
			}
		}
		@Override
		public Image getImg() {
			return DEFAULT;
		}
		public TileType getType(){
			return type;
		}
	}		
}
