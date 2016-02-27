public class Store{
  public Bag bag;
  public CandyCount candyCount;
  public Gui gui;
  public Mob mob;
  public Player player;
  public SystemClock systemClock;
  public Store(){
    bag = new Bag();
   candyCount = new CandyCount(this);
    gui = new Gui(this);
    mob = new Mob(this);
    player = new Player(this);
    systemClock = new SystemClock(this);
  }
}
