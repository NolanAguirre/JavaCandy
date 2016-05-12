package animaition;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import player.Direction;
import player.Player;

public class MyFrame extends JFrame implements WindowListener{
	private Player player;
	private GameClient temp;
	private static final long serialVersionUID = 2L;
	public MyFrame(){
		player = new Player(8);
		temp = new GameClient("",0, player);
		JPanel pane = new JPanel();
		pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));
	    pane.add(temp.getDisplay());
	    pane.add(temp.getHUD());
	    add(pane);
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
	@Override
	public void windowClosed(WindowEvent e){
		
	}
	@Override
	public void windowActivated(WindowEvent e) {
		
	}
	@Override
	public void windowClosing(WindowEvent e) {
		temp.close();
	}
	@Override
	public void windowDeactivated(WindowEvent e) {
		
	}
	@Override
	public void windowDeiconified(WindowEvent e) {
		
	}
	@Override
	public void windowIconified(WindowEvent e) {
		
	}
	@Override
	public void windowOpened(WindowEvent e) {
		
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






