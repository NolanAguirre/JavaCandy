

public class ServerPlayer {
	private int x;
	private int y;
	private int roomX;
	private int roomY;
	private int attack;
	private int health;
	private int Id;
	public ServerPlayer(int x, int y, int roomX, int roomY, int attack, int health, int Id){
		this.x = x;
		this.y = y;
		this.roomX = roomX;
		this.roomY = roomY;
		this.attack = attack;
		this.health = health;
		this.Id = Id;
	}
	public void move(int x, int y){
		this.x += x;
		this.y += y;
	}
	public void set(int x, int y){
		this.x = x;
		this.y = y;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public void damage(int damage){
		health -= damage;
	}
	public void moveUp(){
		roomX--;
	}
	public void moveDown(){
		roomX++;
	}
	public void moveLeft(){
		roomY--;
	}
	public void moveRight(){
		roomY++;
	}
	public int[] getRoom(){
		return new int[]{roomX,roomY};
	}
	public boolean sameRoom(int[] foo){
		return foo[0] == roomX && foo[1] == roomY;
	}
	public int getHealth(){
		return health;
	}
	public int getAttack(){
		return attack;
	}
}
