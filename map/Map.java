package map;

import java.util.ArrayList;

import mob.Enemy;
import mob.Mob;

public class Map {
	private String seed;
	private byte[][]map;
	private ArrayList<Mob> mobs;
	public Map(String seed){
		map = new byte[20][20];
		mobs = new ArrayList<Mob>();
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
				map[x][y] = 0;
				break;
			case 'm': 
				mobs.add(new Enemy(x,y));
				map[x][y] = 0;
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
	public ArrayList<Mob> getMobs(){
		return mobs;
	}
	public void addMob(Mob player){
		if(!mobs.contains(player)){
			mobs.add(player);
		}
	}
	public byte[][] getMapLayout() {
		return map;
	}
	public void removePlayer(Mob remove){
		mobs.remove(mobs.indexOf(remove));
	}
}