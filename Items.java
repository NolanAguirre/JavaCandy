public class Items{
  protected String name;
  protected String effect;
  // maybe have weight
  public Items(String name, String effect){
    this.name = name;
    this.effect = effect;
  }
  public void use(){
  }
  @Override
  public String toString(){
    return effect;
  }
}
