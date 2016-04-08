package animaition;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import mob.Mob;
import player.Direction;
import player.Player;

public class MyFrame extends JFrame {
	private static final long serialVersionUID = 2L;
	private Mob player;
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
          //  if (e.getKeyCode() == KeyEvent.VK_RIGHT ||  e.getKeyCode() == KeyEvent.VK_D) {
             //  player.move(2,0);
             //  player.setMoving(true);
          //  }else if (e.getKeyCode() == KeyEvent.VK_LEFT ||  e.getKeyCode() == KeyEvent.VK_A ) {
            //	player.move(-2,0);
            //	player.setMoving(true);
          //  }else if (e.getKeyCode() == KeyEvent.VK_UP ||  e.getKeyCode() == KeyEvent.VK_W) {
            //	player.move(0,-2);
            //	player.setMoving(true);
          //  }else if (e.getKeyCode() == KeyEvent.VK_DOWN  ||  e.getKeyCode() == KeyEvent.VK_S) {
            //	player.move(0,2);
            //	player.setMoving(true);
          //  }
        }
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
            	if(!player.getFrozenRight()){
            		player.move(7,0);
            		player.setMoving(true);
            	}
            	if(player.getFacing() != Direction.RIGHT){
            		player.setFacing(Direction.RIGHT);
            	}
            }else if (e.getKeyCode() == KeyEvent.VK_LEFT  || e.getKeyCode() == KeyEvent.VK_A) {
            	if(!player.getFrozenLeft()){
            		player.move(-7,0);
            		player.setMoving(true);
            	}
            	if(player.getFacing() != Direction.LEFT){
            		player.setFacing(Direction.LEFT);
            	}
            }else if (e.getKeyCode() == KeyEvent.VK_UP ||  e.getKeyCode() == KeyEvent.VK_W) {
            	if(!player.getFrozenUp()){
            		player.move(0,-7);
            		player.setMoving(true);
            	}
            	if(player.getFacing() != Direction.UP){
            		player.setFacing(Direction.UP);
            	}
            }else if (e.getKeyCode() == KeyEvent.VK_DOWN ||  e.getKeyCode() == KeyEvent.VK_S) {
            	if(!player.getFrozenDown()){
            		player.move(0,7);
            		player.setMoving(true);
            	}
            	if(player.getFacing() != Direction.DOWN){
            		player.setFacing(Direction.DOWN);
            	}
            //}
            //if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            //    player.stopAttack();
             }else{
            	 
            }
        }
        @Override
        public void keyReleased(KeyEvent e) {
        	if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                player.attack();
             }
           player.setMoving(false);
        }
    }
}
