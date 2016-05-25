public class ServerMob{
	private int x;
	private int y;
	private int health;
	private int attack;
	private int ID;
	private boolean freezeUp;
	private boolean freezeDown;
	private boolean freezeLeft;
	private boolean freezeRight;
	public ServerMob(int x, int y, int ID){
		this.x = x;
		this.y = y;
		this.ID = ID;
		health = (int)((Math.random()+1) * 20);
		attack = (int)((Math.random()+1) * 2);
	}
	public void move(int x, int y){
		if(!freezeLeft && x < 0){
			this.x += x;
		}else if(!freezeRight && x > 0){
			this.x += x;
		}
		if(!freezeUp && y < 0){
			this.y += y;
		}else if(!freezeDown && y > 0){
			this.y += y;
		}		
	}
	public int getID(){
		return ID;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	public int getAttack() {
		return attack;
	}
	public void preventMotion(Direction direction) {
		switch(direction){
		case LEFT:
			freezeLeft = true;
			break;
		case RIGHT:
			freezeRight = true;
			break;
		case UP:
			freezeUp = true;
			break;
		case DOWN:
			freezeDown = true;
			break;
		default:
			
		}	
		
	}
	public boolean getFrozenUp(){
		return freezeUp;
	}
	public boolean getFrozenDown(){
		return freezeDown;
	}
	public boolean getFrozenLeft(){
		return freezeLeft;
	}
	public boolean getFrozenRight(){
		return freezeRight;
	}
	public synchronized  void unfreeze() {
		freezeUp = false;
		freezeDown = false;
		freezeLeft = false;
		freezeRight = false;
	}
	@Override
	public String toString(){
		return "MOB-"+ ID + "-" + x + "-" + y + "-" + health;
	}
}
