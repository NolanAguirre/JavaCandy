package animaition;

import javax.swing.JFrame;

public class MyFrame extends JFrame {
	private static final long serialVersionUID = 2L;
	public MyFrame(){
		 GameClient display =  new GameClient("",6);
	     add(display);
	     setResizable(false);
	     pack();
	     setTitle("Candy Box 3");
	     setLocationRelativeTo(null);
	     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}






