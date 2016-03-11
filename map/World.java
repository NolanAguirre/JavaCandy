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
	private Graph mapGrid;
	public World() throws FileNotFoundException{
		mapGrid = new Graph();
		Scanner input = new Scanner(new File("mapSeed.dat"));
		while(input.hasNext()){
			mapGrid.addMap(new Map(input.next())); // store this in graph to make a world that has different rooms;
		}
		input.close();
		mapGrid.connect(0,1,0,1);
		mapGrid.connect(0,2,1,0);
		mapGrid.connect(0,3,2,3);
		mapGrid.connect(0,4,3,2);
		currentMap = (Map)mapGrid.getDefaultMap();
	}
	public IMap getMap(){
		return currentMap;
	}
	public void renderUp(){
		currentMap = (Map) mapGrid.getUp(currentMap);
	}
	public void renderDown(){
		currentMap = (Map) mapGrid.getDown(currentMap);
	}
	public void renderLeft(){
		currentMap = (Map) mapGrid.getLeft(currentMap);
	}
	public void renderRight(){
		currentMap = (Map) mapGrid.getRight(currentMap);
	}
	class Map implements IMap{
		private ArrayList<SpriteTile>map;
		public Map(String seed){
			map = new ArrayList<SpriteTile>();
			generate(seed);
		}
		private void generate(String seed){
			for(char foo : seed.toCharArray()){
				if(foo == 'f'){
					map.add(new Tile(TileType.WOOD_FLOOR));
				}else if(foo == 'w'){
					map.add(new Tile(TileType.WALL));
				}else if(foo == 'b'){
					map.add(new Tile(TileType.BRICK));
				}else{
					map.add(new Tile(TileType.FILLER));
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
		public ArrayList<SpriteTile> getMapLayout() {
			return map;
		}
		class Tile implements SpriteTile{
			private Image DEFAULT;
			private Image TRANSITION;
			private int compress;
			private TileType type;
			public Tile(TileType type){
				compress = 1;
				this.type = type;
					switch(type){
					case WOOD_FLOOR:
			            loadImage(26);
			            break;
					case WALL:
						loadImage(27);
			            break;
					case BRICK: 
						loadImage(28);
			            break;
			        default:
			        	loadImage(29);
				}
		        
			}
			private void loadImage(int cords){
				try{
					DEFAULT = ImageIO.read(Sprite.SPRITESHEET).getSubimage(cords*32,0,Sprite.HEIGHT,Sprite.WIDTH);
	            	TRANSITION = ImageIO.read(Sprite.SPRITESHEET).getSubimage(cords*32,0,Sprite.HEIGHT,Sprite.WIDTH);
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
