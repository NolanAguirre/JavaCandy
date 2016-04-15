package map;

import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import mob.Enemy;
import mob.Mob;
import sprite.Sprite;
import sprite.SpriteTile;

public class World {
	private Graph graph;
	private Map current;
	public World(){
		graph = new Graph();
		update();
	}
	public IMap getMap(){
		return current;
	}
	public void renderUp(){
		graph.moveUp();
		update();
	}
	public void renderDown(){
		graph.moveDown();
		update();
	}
	public void renderLeft(){
		graph.moveLeft();
		update();
	}
	public void renderRight(){
		graph.moveRight();
		update();
	}
	private void update(){
		current = new Map(graph.getCurrent());
	}
	//////////////////////////////// map
	class Map implements IMap{
		private Chest chest;
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
			compress();
		}
		private void compress(){
			int standard = 0;
			for(int foo = 0; foo < map.size(); foo++){
				if(foo+1 >= map.size()){
					break;
				}else{
					if(map.get(foo).getType().equals(map.get(foo+1).getType())){
						standard = foo;
						map.get(standard).compress();
						map.remove(foo+1);
						foo--;
					}
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
		public synchronized ArrayList<SpriteTile> getMapLayout() {
			return map;
		}
		public Chest getChest(){
			return chest;
		}
		//////////////////////// blocks/tiles
		class Tile implements SpriteTile{
			private Image DEFAULT;
			private int compress;
			private TileType type;
			public Tile(TileType type){
				compress = 1;
				double rand = Math.random();
				this.type = type;
					switch(type){
					case WOOD_FLOOR:
						if(rand > .999 && chest == null){
							chest = new Chest(x*32,y*32);
							loadImage(16);
							this.type = TileType.CHEST;
							break;
						}else if(rand > .99){
							mobs.add(new Enemy(x*32,y*32));
						}
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
			public void compress(){
				compress++;
			}
			public int getCompress(){
				return compress;
			}
		}		
	}
}
