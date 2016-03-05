package animaition;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import player.Player;
import sprite.Movable;



public class MyFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private Movable player;
	public MyFrame(){
			player = new Player();
		 addKeyListener(new CustomKeyListener());
	     add(new GameDisplay(player));
	     setResizable(false);
	     pack();
	     setTitle("Candy Box 3");
	     setLocationRelativeTo(null);
	     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	class CustomKeyListener implements KeyListener{
        @Override
        public void keyTyped(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            	player.move(2,0);
            	player.setMoving(true);
            }
            else if (e.getKeyCode() == KeyEvent.VK_LEFT ) {
            	player.move(-2,0);
            	player.setMoving(true);
            }
            else if (e.getKeyCode() == KeyEvent.VK_UP ) {
            	player.move(0,-2);
            	player.setMoving(true);
            }
            else if (e.getKeyCode() == KeyEvent.VK_DOWN ) {
            	player.move(0,2);
            	player.setMoving(true);
            }
            else{
            }
        }
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_RIGHT ) {
               player.move(2,0);
               player.setMoving(true);
            }
            else if (e.getKeyCode() == KeyEvent.VK_LEFT ) {
            	player.move(-2,0);
            	player.setMoving(true);
            }
            else if (e.getKeyCode() == KeyEvent.VK_UP ) {
            	player.move(0,-2);
            	player.setMoving(true);
            }
            else if (e.getKeyCode() == KeyEvent.VK_DOWN ) {
            	player.move(0,2);
            	player.setMoving(true);
            }
            else{
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
           player.setMoving(false);
        }
    }
}
