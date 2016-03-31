package map;

import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;

import mob.Enemy;
import player.Mob;
import sprite.Sprite;
import sprite.SpriteTile;

public class World {
	private Graph graph;
	private Map current;
	private ArrayList<String> seeds;
	public World()  throws FileNotFoundException{
		seeds = new ArrayList<String>();
		Scanner scan = new Scanner(new File("mapseed.dat"));
		while(scan.hasNext()){
			seeds.add(scan.next());
		}
		scan.close();
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
		current = new Map(seeds.get(graph.getCurrent().getSeed()));
	}
	//////////////////////////////// map
	class Map implements IMap{
		private String seed;
		private ArrayList<SpriteTile>map;
		private ArrayList<Mob> enemys;
		public Map(String seed){
			map = new ArrayList<SpriteTile>();
			enemys = new ArrayList<Mob>();
			this.seed = seed;
			generate();
		}
		@Override
		public String toString(){
			return seed;
		}
		private void generate(){
			int x = 0;
			int y = 0;
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
					case 'm':
						enemys.add(new Enemy(x*32,y*32));
						x--;
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
			for(int x = 0; x < map.size(); x++){
				if(x+1 >= map.size()){
					break;
				}else{
					if(map.get(x).getType().equals(map.get(x+1).getType())){
						standard = x;
						map.get(standard).compress();
						map.remove(x+1);
						x--;
					}
				}
			}
		}
		public ArrayList<Mob> getEnemys(){
			return enemys;
		}
		public synchronized ArrayList<SpriteTile> getMapLayout() {
			return map;
		}
		//////////////////////// blocks/tiles
		class Tile implements SpriteTile{
			private Image DEFAULT;
			private int compress;
			private TileType type;
			public Tile(TileType type){
				compress = 1;
				this.type = type;
					switch(type){
					case WOOD_FLOOR:
			            loadImage(33);
			            break;
					case WALL:
						loadImage(34);
			            break;
					case BRICK: 
						loadImage(35);
			            break;
			        default:
			        	loadImage(0);
				}
			}
			private void loadImage(int cords){
				try{
					DEFAULT = ImageIO.read(Sprite.SPRITESHEET).getSubimage(cords*32,0,Sprite.HEIGHT,Sprite.WIDTH);
				}catch(IOException ex){
		            throw(new Error("File Not Found"));
		        }
			}
			@Override
			public Image getDefaultImg() {
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
