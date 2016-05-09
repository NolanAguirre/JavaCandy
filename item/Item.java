package item;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import sprite.Sprite;

public class Item implements Items, Sprite{
	private int amount;
	private ItemType type;
	private StatusEffect effect;
	private Image img;
	private int effectAmount;
	public Item(ItemType type){
		switch(type){
		case POTION:
			this.type = type;
			effectAmount = 20;
			effect = StatusEffect.HP;
			break;
		}
		amount = 1;
		img = loadImage();
	}
	private Image loadImage(){
		try{
			switch(type){
			case POTION:
				return ImageIO.read(Sprite.SPRITESHEET).getSubimage(2,34,28,28);
	        default:
	        	return null;
			}
		}catch(IOException ex){
			throw(new Error("Spritesheet Not Found"));
		}
	}
	public int getEffectAmount(){
		return effectAmount;
	}
	@Override
	public int getAmount() {
		return amount;
	}
	@Override
	public void add() {
		amount++;
	}
	@Override
	public Items use() {
		amount--;
		return this;
	}

	@Override
	public Image getImg() {
		return img;
	}

	@Override
	public ItemType getType() {
		return type;
	}

	@Override
	public StatusEffect getEffect() {
		return effect;
	}
	@Override
	public int getX() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int getY() {
		// TODO Auto-generated method stub
		return 0;
	}

}
