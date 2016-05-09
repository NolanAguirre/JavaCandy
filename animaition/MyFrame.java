package animaition;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

import player.Direction;
import player.Player;

public class MyFrame extends JFrame {
	private Player player;
	private static final long serialVersionUID = 2L;
	public MyFrame(){
		player = new Player(8);
		GameClient temp = new GameClient("",0, player);
	     add(temp.getDisplay());
	     setResizable(false);
	     setFocusable(true);
		requestFocusInWindow();
		 addKeyListener(new CustomKeyListener());
		 addMouseListener(new CustomMouseListener());
	     pack();
	     setTitle("Candy Box 3");
	     setLocationRelativeTo(null);
	     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	class CustomMouseListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent arg0) {
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
		}

		@Override
		public void mousePressed(MouseEvent mouse) {
			//if(world.getMap().getChest() != null){
			//	Rectangle temp = new Rectangle(world.getMap().getChest().getX(), world.getMap().getChest().getY(), 32, 32);
			//	if(temp.contains(mouse.getPoint()) && player.isTouching(temp)){
			//		gameState = GameState.CHEST;
			//	}
			//}
		}
		@Override
		public void mouseReleased(MouseEvent arg0) {
			
		}
		
	}
	class CustomKeyListener implements KeyListener{
        @Override
        public void keyTyped(KeyEvent e) {
        }
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
            	if(!player.getFrozenRight()){
            		player.move(5,0);
            		player.setMoving(true);
            	}
            	if(player.getFacing() != Direction.RIGHT){
            		player.setFacing(Direction.RIGHT);
            	}
            }else if (e.getKeyCode() == KeyEvent.VK_LEFT  || e.getKeyCode() == KeyEvent.VK_A) {
            	if(!player.getFrozenLeft()){
            		player.move(-5,0);
            		player.setMoving(true);
            	}
            	if(player.getFacing() != Direction.LEFT){
            		player.setFacing(Direction.LEFT);
            	}
            }else if (e.getKeyCode() == KeyEvent.VK_UP ||  e.getKeyCode() == KeyEvent.VK_W) {
            	if(!player.getFrozenUp()){
            		player.move(0,-5);
            		player.setMoving(true);
            	}
            	if(player.getFacing() != Direction.UP){
            		player.setFacing(Direction.UP);
            	}
            }else if (e.getKeyCode() == KeyEvent.VK_DOWN ||  e.getKeyCode() == KeyEvent.VK_S) {
            	if(!player.getFrozenDown()){
            		player.move(0,5);
            		player.setMoving(true);
            	}
            	if(player.getFacing() != Direction.DOWN){
            		player.setFacing(Direction.DOWN);
            	}
             }else if(e.getKeyCode() == KeyEvent.VK_E){
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






