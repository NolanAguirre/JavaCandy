import java.util.*;
public class Demo{
    Timer time;
    public Demo(){
            time = new Timer();
            time.schedule(new task(),1000, 1000);
    }
    class task extends TimerTask{
        @Override
        public void run(){
            System.out.println("Hello world!");
        }
    }
}
