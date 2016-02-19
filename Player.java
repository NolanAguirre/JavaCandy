public class Player{
  private String name;
  private int level;
  private int attack;
  private int hp;
  private Bag bag;
  public player(String name, Bag bag){
    level = 1;
    hp = 10;
    attack = 4;
    this.name = name;
    this.bag = bag;
  }
  public int getHp(){
    return hp;
  }
  public void damage(int foo){
    hp -= foo;
  }
  public int attack(){
    return bag.equipedWeapon().attack() + attack;
  }
}
