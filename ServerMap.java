

import java.awt.Rectangle;
import java.util.ArrayList;


public class ServerMap {
	private String seed;
	private byte[][]map;
	private ArrayList<ServerMob> mobs;
	private ArrayList<Rectangle> walls;
	public ServerMap(String seed){
		map = new byte[20][20];
		mobs = new ArrayList<ServerMob>();
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
	public ArrayList<ServerMob> getMobs(){
		return mobs;
	}
	public void addMob(ServerMob mob){
		if(!mobs.contains(mob)){
			mobs.add(mob);
		}
	}
	public byte[][] getMapLayout() {
		return map;
	}
	public void removePlayer(int remove){
		mobs.remove(remove);
	}
	public ArrayList<Rectangle> getWalls(){
		return walls;
	}
}
