public class Weapon extends Items{
  private int attack;
  public Weapon(String name, String effect, int attack){
    super(name, effect);
    this.attack = attack;
  }
  public int attack(){
    return ((int)(Math.random() * 4) + attack);
  }
}
