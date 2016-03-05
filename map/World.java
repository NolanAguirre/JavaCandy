package map;

import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;

import sprite.Sprite;
import sprite.SpriteTile;

public class World {
	private Map currentMap;
	//private static final Graph mapConnectionGrid = new Graph();
	public World() throws FileNotFoundException{
		Scanner input = new Scanner(new File("mapSeed.dat"));
		while(input.hasNext()){
			currentMap = new Map(input.next()); // store this in graph to make a world that has different rooms;
		}
		input.close();
	}
	public IMap getMap(){
		return currentMap;
	}
	class Map implements IMap{
		private ArrayList<SpriteTile>map;
		public Map(String seed){
			map = new ArrayList<SpriteTile>();
			generate(seed);
			compress();
		}
		private void generate(String seed){
			for(char foo : seed.toCharArray()){
				if(foo == 'f'){
					map.add(new Tile(TileType.WOOD_FLOOR));
				}else if(foo == 'w'){
					map.add(new Tile(TileType.WALL));
				}else{
					map.add(new Tile(TileType.FILLER));
				}
				compress();
			}
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
		public ArrayList<SpriteTile> getMapLayout() {
			return map;
		}
		class Tile implements SpriteTile{
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
				            DEFAULT = ImageIO.read(Sprite.SPRITESHEET).getSubimage(96,0,Sprite.HEIGHT,Sprite.WIDTH);
				            TRANSITION = ImageIO.read(Sprite.SPRITESHEET).getSubimage(96,0,Sprite.HEIGHT,Sprite.WIDTH);
				            break;
						case WALL:
							DEFAULT = ImageIO.read(Sprite.SPRITESHEET).getSubimage(128,0,Sprite.HEIGHT,Sprite.WIDTH);
				            TRANSITION = ImageIO.read(Sprite.SPRITESHEET).getSubimage(128,0,Sprite.HEIGHT,Sprite.WIDTH);
				            break;
				        default:
				        	DEFAULT = ImageIO.read(Sprite.SPRITESHEET).getSubimage(160,0,Sprite.HEIGHT,Sprite.WIDTH);
				            TRANSITION = ImageIO.read(Sprite.SPRITESHEET).getSubimage(160,0,Sprite.HEIGHT,Sprite.WIDTH);
				        	
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
		
	}
}
