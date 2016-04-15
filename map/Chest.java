package map;

import java.util.ArrayList;

import item.Item;
import item.ItemType;
import item.Items;
import player.bag.GameContainer;

public class Chest extends GameContainer{
	private int x;
	private int y;
	public Chest(int x, int y){
		this.x = x;
		this.y = y;
		items = new ArrayList<Items>();
		super.loadImage();
		loadItems();
	}
	private void loadItems(){
		int max = (int)Math.random()*20;
		do{
			if(true){
				items.add(new Item(ItemType.POTION));
			}
		}while(x < max);
		
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
}
