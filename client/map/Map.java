package map;

import java.awt.Rectangle;
import java.util.ArrayList;

import mob.Mob;

public class Map {
	private String seed;
	private byte[][]map;
	private ArrayList<Mob> mobs;
	private ArrayList<Rectangle> walls;
	public Map(String seed){
		map = new byte[20][20];
		mobs = new ArrayList<Mob>();
		walls = new ArrayList<Rectangle>();
		this.seed = seed;
		generate();
	}
	private void generate(){
		int x = 0;
		int y = 0;
		char[] cA = seed.toCharArray();
		for(char foo : cA){
			switch(foo){
			case 'w': 
				map[x][y] = 0;
				walls.add(new Rectangle(x*32, y*32, 32, 32));
				break;
			case 'b': 
				map[x][y] = 2;
				break;
			case 'f':
				map[x][y] = 1;
				break; 				
			default: 
				map[x][y] = 3;
			}
			x++;
			if(x >= 20){
				y++;
				x=0;
			}
		}
	}
	public synchronized ArrayList<Mob> getMobs(){
		synchronized(mobs){
			return mobs;
		}
	}
	public synchronized void addMob(Mob player){
		if(!mobs.contains(player)){
			synchronized(mobs){
				mobs.add(player);
			}
		}
	}
	public byte[][] getMapLayout() {
		return map;
	}
	public synchronized void removePlayer(int remove){
		synchronized(mobs){
			mobs.remove(remove);
		}
	}
	public ArrayList<Rectangle> getWalls(){
		return walls;
	}
}
