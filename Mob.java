public class Mob{
  private boolean inBattle;
  private int attack;
  private int hp;
  private Store store;
  public Mob(Store store){
    this.store = store;
  }
  public void mobAttack(){
    int tempHp = store.player.getHp();
    inBattle = true;
    hp = (int)(Math.random() + store.player.getLevel()) * 12;
    attack = (int)(Math.random() + store.player.getLevel()) * 10;
    store.gui.promptAttack();
    while(hp >= 0 && store.player.getHp() >= 0){
      hp -= store.player.attack();
      store.gui.playerHitMob();
      store.player.damage(attack());
      store.gui.mobHitPlayer();
    }
    if(hp <= 0){
      store.gui.playerWinner();
      store.player.addExp();
    }else{
      store.gui.mobWinner();
    }
    inBattle = false;
    store.player.setHp(tempHp);
  }
  private int attack(){
    return ((int)(Math.random() * 3) + attack);
    }
    public boolean inBattle(){
        return inBattle;
    }
}
