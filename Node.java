

import java.util.ArrayList;


public class Node{
	private ArrayList<Direction> directions;
	private int seed;
	public Node(int seed, boolean up, boolean down, boolean left, boolean right) {
		this.seed = seed;
		directions = new ArrayList<Direction>();
		if(up){
			directions.add(Direction.UP);
		}
		if(down){
			directions.add(Direction.DOWN);
		}
		if(left){
			directions.add(Direction.LEFT);
		}
		if(right){
			directions.add(Direction.RIGHT);
		}
	}
	public ArrayList<Direction> getDirections(){
		return directions;
	}
	public int getSeed() {
		return seed;
	}
}
