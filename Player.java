import java.awt.Image;
import java.awt.Rectangle;
public class Player{
  private Image img;
  private Rectangle hitBox;
  private Store store;
  private String name;
  private int level;
  private int attack;
  private int hp;
  private int exp;
  private int x;
  private int y;
  public Player(Store store){
    level = 1;
    hp = 10;
    attack = 4;
    this.store = store;
  }
  public void setName(String name){
      this.name = name;
  }
  public int getHp(){
    return hp;
  }
  public void setHp(int hp){
      this.hp = hp;
  }
  public void damage(double damage){
    hp -= damage;
  }
  public int attack(){
    return store.bag.getEquiped().attack() + attack;
  }
  public void addExp(){
      exp++;
      if(exp == Math.pow(2, level)){
          level++;
          exp = 0;
      }
  }
  public int getLevel(){
      return level;
  }
  public void setImage(Image img){
      this.img = img;
  }
  public Image getImage(){
      return this.img;
  }
  public void move(int x, int y){
      this.x += x;
      this.y += y;
  }
  public int getX(){
      return x;
  }
  public int getY(){
      return y;
  }
}
