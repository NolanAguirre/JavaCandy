package player;

public class Player extends Mob{
	public Player(){
		new MobAnimation();
		facing = Direction.DOWN;
		attack = 2;
		hp = 10000;
		x = 375;
		y = 375;
        still = loadImage(1);
        imageChain = 1;
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
			imageChain = 5;
			break;
		case DOWN:
			imageChain = 9;
			break;
		case UP:
			imageChain = 13;
			break;
		case RIGHT:
			imageChain = 1;
			break;
		}
	}
}
