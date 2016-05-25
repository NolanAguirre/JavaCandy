
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;


public class Graph {
	private int x;
	private int y;
	private int rooms;
	private byte[][] graph;
	private ServerMob[][][]mobs;
	private ArrayList<String> seeds;
	private ArrayList<Node> nodes;
	private ArrayList<ServerMap>mapsWithMobs;
	public Graph(){
		rooms = 0;
		mobs = new ServerMob[100][100][50];
		seeds = new ArrayList<String>();
		nodes = new ArrayList<Node>();
		mapsWithMobs= new ArrayList<ServerMap>();
		graph = new byte[100][100];
		for (byte[] row: graph){
		    Arrays.fill(row, (byte)50);
		}
		x = graph.length/2-1;
		y = graph.length/2-1;
		generateNodeTypes();
		graph[x][y] = 0;
		generate();
	}
	public String getStart(){
		System.out.println(x + " " + y + " rooms:  " + rooms);
		return seeds.get(nodes.get(graph[x][y]).getSeed());
	}
	public String get(int[] cords){
		return seeds.get(nodes.get(graph[cords[0]][cords[1]]).getSeed());
	}
	public ServerMob[] getMobs(int[]cords){
		return mobs[cords[0]][cords[1]];
	}
	public void startRoomMobs(int[]cords){
		ServerMap temp = findMap(cords);
		if(temp == null){
			temp = new ServerMap(get(cords),getMobs(cords), cords);
			mapsWithMobs.add(temp);
		}
		if(!temp.isRunning()){
			temp.start();
		}
	}
	public void killMob(int[]cords, int ID){
		findMap(cords).killMob(ID);
	}
	public void hurtMob(int[]cords, int ID, int health){
		findMap(cords).hurtMob(ID, health);
	}
	public void stopRoomMobs(int[]cords){
		findMap(cords).kill();
		mapsWithMobs.remove(findMap(cords));
	}
	public ServerMap findMap(int[]cords){
		for(int x = 0; x < mapsWithMobs.size(); x++){
			if(mapsWithMobs.get(x).getID()[0] == cords[0] && mapsWithMobs.get(x).getID()[1] == cords[1]){
				return mapsWithMobs.get(x);
			}
		}
		return null;
	}
	public void moveUp(){
		x--;
	}
	public void moveDown(){
		x++;
	}
	public void moveLeft(){
		y--;
	}
	public void moveRight(){
		y++;
	}
	private void generateNodeTypes(){
		int temp = 0;
		String s = "";
		while(temp < 37){ // one less than amount of maps
			ArrayList<ArrayList<Character>> edges = new ArrayList<ArrayList<Character>>();
			edges.add(new ArrayList<Character>());
			edges.add(new ArrayList<Character>());
			edges.add(new ArrayList<Character>());
			edges.add(new ArrayList<Character>());
			s = "";
			try{
				BufferedImage img = ImageIO.read(new File("map.png")).getSubimage(temp*20,0,20,20);
				for(int foo = 0; foo < 20; foo++){
					for(int bar = 0; bar < 20; bar++){
						if(img.getRGB(bar,foo) ==-16777216){ //black
							s+= 'b';
						}else if(img.getRGB(bar,foo) ==-65356){ //pink
							s += 'w';
						}else if(img.getRGB(bar,foo) == -589824){ //red
							s +='f';
						}else if(img.getRGB(bar,foo) ==-1){ // old, need to update map
							s+='f';
						}else{
							s+= 'b';
						}
						if(foo == 0){
							edges.get(0).add(s.charAt(s.length()-1));
						}else if(foo == 19){
							edges.get(1).add(s.charAt(s.length()-1));
						}else if(bar == 0){
							edges.get(2).add(s.charAt(s.length()-1));
						}else if(bar == 19){
							edges.get(3).add(s.charAt(s.length()-1));
						}
					}
				}
			}catch(IOException ex){
				throw(new Error("Spritesheet Not Found"));
			}
			seeds.add(s);
			nodes.add(new Node(temp,edges.get(0).contains('f'),edges.get(1).contains('f'),edges.get(2).contains('f'),edges.get(3).contains('f')));
			temp++;
		}

	}
	private void generate(){
		ArrayList<Direction> temp = nodes.get(graph[x][y]).getDirections();
		if(x < graph.length-2  && x > 1 & y < graph.length-2 && y > 1 ){
			if(graph[x-1][y] == 50 && temp.contains(Direction.UP)){
				moveUp();
				setRoom();
				moveDown();
			}
			if(graph[x+1][y] == 50 && temp.contains(Direction.DOWN)){
				moveDown();
				setRoom();
				moveUp();
			}
			if(graph[x][y-1] == 50 && temp.contains(Direction.LEFT)){
				moveLeft();
				setRoom();
				moveRight();
			}
			if(graph[x][y+1] == 50 && temp.contains(Direction.RIGHT)){
				moveRight();
				setRoom();
				moveLeft();
			}
		}
	}
	private ArrayList<Direction> getConnections(){
		ArrayList<Direction> returnVal = new ArrayList<Direction>();
		if(graph[x-1][y] != 50 && nodes.get(graph[x-1][y]).getDirections().contains(Direction.DOWN)){ // if the up opens down
			returnVal.add(Direction.UP);
		}
		if(graph[x+1][y] != 50 && nodes.get(graph[x+1][y]).getDirections().contains(Direction.UP)){ // if the down opens up
			returnVal.add(Direction.DOWN);
		}
		if(graph[x][y-1] != 50 && nodes.get(graph[x][y-1]).getDirections().contains(Direction.RIGHT)){ // if the left opens right
			returnVal.add(Direction.LEFT);
		}
		if(graph[x][y+1] != 50 && nodes.get(graph[x][y+1]).getDirections().contains(Direction.LEFT)){ // if the right opens left
			returnVal.add(Direction.RIGHT);
		}
		return returnVal;
	}
	private ArrayList<Direction> getBlockedConnections(){
		ArrayList<Direction> returnVal = new ArrayList<Direction>();
		if(graph[x-1][y] != 50 && !nodes.get(graph[x-1][y]).getDirections().contains(Direction.DOWN)){ // if the up opens down
			returnVal.add(Direction.UP);
		}
		if(graph[x+1][y] != 50 && !nodes.get(graph[x+1][y]).getDirections().contains(Direction.UP)){ // if the down opens up
			returnVal.add(Direction.DOWN);
		}
		if(graph[x][y-1] != 50 && !nodes.get(graph[x][y-1]).getDirections().contains(Direction.RIGHT)){ // if the left opens right
			returnVal.add(Direction.LEFT);
		}
		if(graph[x][y+1] != 50 && !nodes.get(graph[x][y+1]).getDirections().contains(Direction.LEFT)){ // if the right opens left
			returnVal.add(Direction.RIGHT);
		}
		return returnVal;
	}
	private void setRoom(){
		ArrayList<Integer> temp = new ArrayList<Integer>();
		rooms++;
		for(Node node : nodes){
			boolean state = true;
				for(Direction foo : getConnections()){
					if(!node.getDirections().contains(foo)){
						state = false;
					}
				}
				for(Direction foo : getBlockedConnections()){
					if(node.getDirections().contains(foo)){
						state = false;
					}
				}
				if(state){
					temp.add(node.getSeed());
				}
				state = true;
			}
		graph[x][y] = temp.get(((int)(Math.random()*100)) % temp.size()).byteValue();
		generate();
	}
	public void generateMobs(){
		for(int x = 0; x < graph.length; x++){
			for(int y = 0; y < graph[x].length; y++){
				if(graph[x][y] != 50){
					addMobs(get(new int[]{x,y}), x,y);
				}
			}
		}
	}
	private void addMobs(String seed,int X, int Y){
        int mobCount = 0;
        int x = 0;
        int y = 0;
		for(char foo : seed.toCharArray()){
			if(foo == 'f' && Math.random() > .96){
				mobs[X][Y][mobCount] = new ServerMob(x*32, y*32, mobCount);
                mobCount++;
			}
            x++;
			if(x >= 20){
				y++;
				x=0;
			}
		}
	}
	@Override
	public String toString(){
		String returnVal = "";
		for(byte[] foo : graph){
			for(byte bar : foo){
			 returnVal += bar + "-";
			}
		}
		return returnVal;
	}
}
