public class Bag{
  private ArrayList<Items> contents;
  private Weapon equiped;
  public Bag(){
    contents = new ArrayList<Items>();
  }
  public Weapon equipedWeapon(){
    return equiped;
  }
  public void equipWeapon(Weapon weapon){
    equiped = weapon;
  }
  public void useCandy(Candy candy){
    candy.use();
    contents.remove(candy);
  }
}
