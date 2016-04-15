package player.bag;

import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import item.Items;
import sprite.Sprite;

public abstract class GameContainer{
	protected ArrayList<Items>items;
	private Image filler;
	protected void loadImage(){
		try{
			filler = ImageIO.read(Sprite.SPRITESHEET).getSubimage(0,32,32,32);
		}catch(IOException e){
            throw(new Error("File Not Found"));
        }
	}
	public void addItem(Items item){
		if(items.contains(item)){
			items.get(items.indexOf(item)).add();
		}else{
			items.add(item);
		}
	}
	public void useItem(Items item){
		if(items.get(items.indexOf(item)).getAmount() > 1){
			items.get(items.indexOf(item)).use();
		}
		items.remove(items.indexOf(item));
	}
	public ArrayList<Items> getItems(){
		return items;
	}
	public Image getImage(){
		return filler;
	}
}
