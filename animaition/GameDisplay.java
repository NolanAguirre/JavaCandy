package animaition;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.FileNotFoundException;

import javax.swing.JPanel;

import map.World;
import player.PlayerI;
import player.Player;
import sprite.SpriteTile;
public class GameDisplay extends JPanel implements Runnable{
	private int count;
	private final PlayerI player;
	private World world;
	private static final long serialVersionUID = 1L;
	public GameDisplay(PlayerI player){
		this.player = player;
		try {
			world = new World();
		} catch (FileNotFoundException e) {
			throw(new Error("Seed Not Found"));
		}
		requestFocusInWindow();
		player = new Player();
		setPreferredSize(new Dimension(640, 640));
	    setDoubleBuffered(true);
	}	
	private void renderMap(Graphics g){
		int x = 0;
		int y= 0;
		int temp = 0;
		for(SpriteTile foo : world.getMap().getMapLayout()){
			while(foo.getCompress() > temp){
				g.drawImage(foo.getDefaultImg(), x*32, y*32, null);
				x++;
				if(x >= 20){
					y++;
					x=0;
				}
				temp++;
			}
			temp = 0;
		}
	}
	private void renderPerson(Graphics g){
		if(count % 20 > 9){
			g.drawImage(player.getDefaultImg(), player.getX()%640, player.getY()%640, null);
		}else if(!player.isMoving()){
			g.drawImage(player.getTransitionImg(), player.getX()%640, player.getY()%640, null);
		}else{
			g.drawImage(player.getMovingImg(), player.getX()%640, player.getY()%640, null);
		}
	}
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        renderMap(g);
        renderPerson(g);
    }
	@Override
	public void addNotify(){
		super.addNotify();
		Thread animationLoop = new Thread(this);
		animationLoop.start();
	}
		@Override
		public void run(){
			while(true){
				try {
					Thread.sleep(20);
					repaint();
					count++;
				} catch (InterruptedException e) {
					System.out.println("Interrupted: " + e.getMessage());
				}
			}
		}
}

