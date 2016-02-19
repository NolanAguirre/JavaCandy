public class Candy extends Items{
  private CandyCount candyCount;
  private String effect;
  private int candyUseEffect;
  public Candy(String name, String effect, int candyUseEffect, CandyCount candyCount){
    super(name);
    this.effect = effect;
    this.candyUseEffect = candyUseEffect;
    this.candyCount = candyCount;
  }
  public void use(){
    candyCount.addCandy(candyUseEffect);
  }
  public int getCandyUseEffect(){
    return candyUseEffect;
  }
  @Override
  public String toString(){
    return effect;
  }
}
