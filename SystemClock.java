import java.util.Timer;
import java.util.TimerTask;
public class SystemClock{
  private int counter;
  private Timer timer;
  private Store store;
  public SystemClock(Store store){
    this.store = store;
    timer = new Timer();
    timer.scheduleAtFixedRate(new Tick(), 1000, 1000);
  }
  private void update(){
    store.candyCount.add();
  }
		class Tick extends TimerTask{
			@Override
		    public void run(){
               	counter++;
                store.systemClock.update();
		    }
		}
}
