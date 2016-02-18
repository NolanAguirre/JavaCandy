/* package whatever; // don't place package name! */

import java.util.*;
import java.lang.*;
import java.io.*;
public class JavaReminder {
    Timer timer;

    public JavaReminder(int seconds) {
        timer = new Timer(); 
        timer.schedule(new RemindTask(), seconds*1000, 1000);
    }

    class RemindTask extends TimerTask {

        @Override
        public void run() {
            System.out.println("ReminderTask is completed by Java timer");
        }
    }

    public static void main(String args[]) {
        System.out.println("Java timer is about to start");
        JavaReminder reminderBeep = new JavaReminder(1);
        System.out.println("Remindertask is scheduled with Java timer.");
    }
}
