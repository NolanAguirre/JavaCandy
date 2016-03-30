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
	private Map current;
	private int rooms;
	private ArrayList<String> seeds;
	public World() throws FileNotFoundException{
		seeds = new ArrayList<String>();
		Scanner scan = new Scanner(new File("mapseed.dat"));
		while(scan.hasNext()){
			seeds.add(scan.next());
		}
		scan.close();
		current = new Map();
		rooms = 1;
		expand(current);
	}
	private void expand(Map room){
		//System.out.println(room);
		if(room.isGenerateUp()){
			if(room.getUp() == null){
				rooms++;
				Map temp = new Map(room, "up");
				room.setUp(temp);
				temp.setDown(room);
				//System.out.println("up ^" + temp);
			}
			expand(room.getUp());
		}
		if(room.isGenerateDown()){
			if(room.getDown() == null){
				Map temp = new Map(room, "down");
				room.setDown(temp);
				temp.setUp(room);
				rooms++;
				//System.out.println("down ^" + temp);
			}
			expand(room.getDown());
		}
		if(room.isGenerateLeft()){
			if(room.getLeft() == null){
				Map temp = new Map(room, "left");
				room.setLeft(temp);
				temp.setRight(room);
			//	System.out.println("left ^" + temp);
				rooms++;
			}
			expand(room.getLeft());
		}
		if(room.isGenerateRight()){
			if(room.getRight() == null){
				Map temp = new Map(room, "right");
				room.setRight(temp);
				temp.setLeft(room);
			//	System.out.println("right ^" + temp);
				rooms++;
			}
			expand(room.getRight());
		}
	}
	public IMap getMap(){
		return current;
	}
	public void renderUp(){
		current = current.getUp();
	}
	public void renderDown(){
		current = current.getDown();
	}
	public void renderLeft(){
		current = current.getLeft();
	}
	public void renderRight(){
		current = current.getRight();
	}
	//////////////////////////////// map
	class Map implements IMap{
		private boolean generateUp;
		private boolean generateDown;
		private boolean generateLeft;
		private boolean generateRight;
		private Map up;
		private Map down;
		private Map left;
		private Map right;
		private String seed;
		private ArrayList<SpriteTile>map;
		private ArrayList<Mob> enemys;
		public Map(){
			map = new ArrayList<SpriteTile>();
			enemys = new ArrayList<Mob>();
			generateUp = true;
			generateDown = true;
			generateLeft = true;
			generateRight = true;
			seed = seeds.get(0);
			generate(seed);
		}
		public Map(Map room, String connectingEdge){
			map = new ArrayList<SpriteTile>();
			enemys = new ArrayList<Mob>();
			generateUp = true;
			generateDown = true;
			generateLeft = true;
			generateRight = true;
			switch(connectingEdge){
			case "up": init(connectingEdge);
				generateDown = false;
				break;
			case "down": init(connectingEdge);
				generateUp = false;
				break;
			case "left":init(connectingEdge);
				generateRight = false;
				break;
			case "right":init(connectingEdge);
				generateLeft = false;
				break;
			}
			generate(seed);
		}
		private void init(String connectingEdge){
			double rand = Math.random();
			//double rand = .5;
			if(rooms > 100 || rand < .3){
				generateUp = false;
				generateDown = false;
				generateLeft = false;
				generateRight = false;
				switch(connectingEdge){
				case "up": seed = seeds.get(8);
					break;
				case "down": seed = seeds.get(7);
					break;
				case "left": seed = seeds.get(10);
					break;
				case "right": seed = seeds.get(9);
					break;
				}	
			}else if(rand < .48){
					if(rand < .32){
						switch(connectingEdge){
						case "up": seed = seeds.get(5);
							generateLeft = false;
							generateRight = false;
							break;
						case "down": seed = seeds.get(5);
							generateLeft = false;
							generateRight = false;
							break;
						case "left": seed = seeds.get(6);
							generateUp = false;
							generateDown = false;
							break;
						case "right": seed = seeds.get(6);
							generateUp = false;
							generateDown = false;
							break;
						}	
					}else if(rand < .39){
						switch(connectingEdge){
						case "up": seed = seeds.get(12);
							generateLeft = false;
							generateUp = false;
							break;
						case "down": seed = seeds.get(11);
							generateRight = false;
							generateDown = false;
							break;
						case "left": seed = seeds.get(14);
							generateLeft = false;
							generateDown = false;
							break;
						case "right": seed = seeds.get(13);
							generateRight = false;
							generateUp = false;
							break;
						}	
					}else{
						switch(connectingEdge){
						case "up": seed = seeds.get(13);
							generateRight = false;
							generateUp = false;
							break;
						case "down": seed = seeds.get(14);
							generateLeft = false;
							generateDown = false;
							break;
						case "left": seed = seeds.get(12);
							generateLeft = false;
							generateUp = false;
							break;
						case "right": seed = seeds.get(11);
							generateRight = false;
							generateDown = false;
							break;
						}
					}
				
			}else if(rand < .73){
				if(rand < .55){
					switch(connectingEdge){
					case "up": seed = seeds.get(2);
						generateUp = false;
						break;
					case "down": seed = seeds.get(1);
					 	generateDown = false;
						break;
					case "left": seed = seeds.get(1);
						generateDown = false;
						break;
					case "right": seed = seeds.get(1);
						generateDown = false;
						break;
					}	
				}else if (rand < .64){
					switch(connectingEdge){
					case "up": seed = seeds.get(3);
						generateRight = false;
						break;
					case "down": seed = seeds.get(3);
						generateRight = false;
						break;
					case "left": seed = seeds.get(4);
						generateLeft = false;
						break;
					case "right": seed = seeds.get(3);
						generateRight = false;
						break;
					}	
				}else{
					switch(connectingEdge){
					case "up": seed = seeds.get(4);
						generateLeft = false;
						break;
					case "down": seed = seeds.get(4);
						generateLeft = false;
						break;
					case "left": seed = seeds.get(4);
						generateLeft = false;
						break;
					case "right": seed = seeds.get(3);
						generateRight = false;
						break;
					}	
				}
				
			}else if(rand < .96){
				seed = seeds.get(0);
			}else{
				generateUp = false;
				generateDown = false;
				generateLeft = false;
				generateRight = false;
				switch(connectingEdge){
				case "up": seed = seeds.get(16);
					break;
				case "down": seed = seeds.get(15);
					break;
				case "left": seed = seeds.get(18);
					break;
				case "right": seed = seeds.get(17);
					break;
				}
			}
		}
		public Map getUp() {
			return up;
		}
		public void setUp(Map up) {
			this.up = up;
		}
		public Map getDown() {
			return down;
		}
		public void setDown(Map down) {
			this.down = down;
		}
		public Map getLeft() {
			return left;
		}
		public void setLeft(Map left) {
			this.left = left;
		}
		public Map getRight() {
			return right;
		}
		public void setRight(Map right) {
			this.right = right;
		}
		public boolean isGenerateUp() {
			return generateUp;
		}
		public boolean isGenerateDown() {
			return generateDown;
		}
		public boolean isGenerateLeft() {
			return generateLeft;
		}
		public boolean isGenerateRight() {
			return generateRight;
		}
		@Override
		public String toString(){
			return seed;
		}
		private void generate(String seed){
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
