import java.awt.EventQueue;

import javax.swing.JFrame;

import animaition.MyFrame;

public class Main {
	public static void main(String[] args) {
		 EventQueue.invokeLater(new Runnable() {
	            @Override
	            public void run() {
	                JFrame ex = new MyFrame();
	                ex.setVisible(true);
	            }
	        });
	}
}