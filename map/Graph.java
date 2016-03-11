package map;

import java.util.ArrayList;

public class Graph {
	private ArrayList<IMap> maps;
	private ArrayList<ArrayList<Integer>> mapConnections;
	public Graph(){
		maps = new ArrayList<IMap>();
		mapConnections = new ArrayList<ArrayList<Integer>>();
	}
	public void addMap(IMap map){
		maps.add(map);
		ArrayList<Integer> temp = new ArrayList<Integer>();
		temp.add(-1);
		temp.add(-1);
		temp.add(-1);
		temp.add(-1);
		mapConnections.add(temp);
	}
	public IMap getDefaultMap(){
		return maps.get(0);
	}
	public void connect(int first, int second, int direction,int secondDirection){ // 0 = up; 1 = down; 2 = left; 3 = right
		mapConnections.get(first).set(direction, second);
		mapConnections.get(second).set(secondDirection, first);
	}
	public IMap getUp(IMap map){
		return maps.get(mapConnections.get(maps.indexOf(map)).get(0)); 
	}
	public IMap getDown(IMap map){
		return maps.get(mapConnections.get(maps.indexOf(map)).get(1));
	}
	public IMap getLeft(IMap map){
		return maps.get(mapConnections.get(maps.indexOf(map)).get(2));
	}
	public IMap getRight(IMap map){
		return maps.get(mapConnections.get(maps.indexOf(map)).get(3));
	}
}
