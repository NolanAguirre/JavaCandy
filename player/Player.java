package player;

import mob.Mob;

public class Player extends Mob{
	public Player(){
		new MobAnimation();
		facing = Direction.DOWN;
		attack = 2;
		hp = 100;
		x = 375;
		y = 375;
        still = loadImage(1);
        imageChain = new int[]{0,1,2};
	}
	@Override
	public synchronized void move(int x, int y) {
		synchronized(this){
			this.x += x;
			this.y += y;
		}
	}
	@Override
	public void attack(Mob mob){
		//damage(mob.getAttack());
		mob.damage(attack);
		isAttacking = false;
	}
	public void setFacing(Direction dir){
		facing = dir;
		switch(dir){
		case LEFT:
			imageChain = new int[]{3,4,5};
			break;
		case DOWN:
			imageChain = new int[]{6,7,6,8};
			break;
		case UP:
			imageChain = new int[]{9,10,9,11};
			break;
		case RIGHT:
			imageChain = new int[]{0,1,2}; 
			break;
		}
	}
}
