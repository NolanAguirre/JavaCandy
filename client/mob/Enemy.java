package mob;

import player.Direction;

public class Enemy  extends Mob{
	public Enemy(int id,int x, int y){
		this.id = id;
		//new MobAnimation();
		facing = Direction.DOWN;
		attack = 1;
		hp = 10;
		this.x = x;
		this.y = y;
        img = loadImage(12);
        imageChain = new int[]{12};
	}
	
	public void move(int x, int y) {
		boolean flag = true;
			if(y < 0){
				if(freezeUp){
					this.x += x;
					flag = false;
				}
			}
			if(y > 0){
				if(freezeDown){
					this.x += x;
					flag = false;
				}
			}
			if(x < 0 ){
				if(freezeLeft){
					this.y += y;
					flag = false;
				}
			}
			if(x > 0){
				if(freezeRight){
					this.y += y;
					flag = false;
				}
			}
			if(flag){
				this.x += x;
				this.y += y;
			}
	}
	@Override
	public String toString(){
		return "MOB-"+ id + "-" + x + "-" + y + "-" + hp;
	}
}
