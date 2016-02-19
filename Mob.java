public class Mob{
  private Player player;
  private boolean inBattle;
  private int attack;
  private int hp;
  private Event event;
  public Mob(Player player, Event event){
    this.player = player;
    this.event = event;
  }
  public void mobAttack(){
    inBattle = true;
    hp = (Integer)(Math.random() + player.getLevel()) * 12);
    attack = (Integer)((Math.random() + player.getLevel()) * 10);
    display.promptAttack();
    while(hp >= 0 && player.getHp() >= 0){
      event.playerHitMob();
      player.damage(mob.attack());
      event.mobHitPlayer();
    }
    if(hp <= 0){
      display.playerWinner();
    }else{
      display.mobWinner();
    }
    display.endBattle();
    inBattle = false;
  }
  private int attack(){
    return (Integer)((math.random() * 3) + attack);
  }
  public boolean inBattle(){
    return inBattle;
  }
}
