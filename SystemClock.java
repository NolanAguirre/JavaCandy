import java.util.Timer;
import java.util.TimerTask;
public class SystemClock{
  int counter;
  Timer timer;
  Event event;
  public SystemClock(){
    timer = new Timer();
    timer.scheduleAtFixedRate(new Task(), 1000, 1000);
  }
  private void update(){
    event.candyCountupdate();
    if(counter % 3000 == 0 && !event.mob.inBattle()){
      event.mobAttack();
    }
    event.candyCountupdate();
  }
  class Tick{
		class Task extends TimerTask{
			@Override
		    public void run(){
          counter++;
          update();
		    }
		}
	}
}
