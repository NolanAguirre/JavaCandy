public class Player{
  private Store store;
  private String name;
  private int level;
  private int attack;
  private int hp;
  private int exp;
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
}
