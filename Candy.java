public class Candy extends Items{
  private Store store;
  private String name;
  private String effect;
  private int candyUseEffect;
  public Candy(String name, String effect, int candyUseEffect, Store store){
    super(name, effect);
    this.candyUseEffect = candyUseEffect;
    this.store = store;
  }
  @Override
  public void use(){
    store.candyCount.add(candyUseEffect);
    remove();
  }
  private void remove(){
      store.bag.remove(this);
  }
}
