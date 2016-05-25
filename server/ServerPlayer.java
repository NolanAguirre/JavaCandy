

public class ServerPlayer {
	private int x;
	private int y;
	private int attack;
	private int health;
	public ServerPlayer(int x, int y, int attack, int health){
		this.x = x;
		this.y = y;
		this.attack = attack;
		this.health = health;
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
	public int getHealth(){
		return health;
	}
	public int getAttack(){
		return attack;
	}
}
