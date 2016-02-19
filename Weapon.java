public class Weapon extends Items{
  private int attack;
  public Weapon(String name, int attack){
    super(name);
    this.attack = attack;
  }
  public int attack(){
    return (Integer)((Math.random() * 4) + attack);
  }
}
