package animaition;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import player.Player;

public class HUD extends JPanel implements Runnable {
	private static final long serialVersionUID = 1L;
	private Player player;
	public HUD(Player player){
		setPreferredSize(new Dimension(640, 100));
		setDoubleBuffered(true);
		this.player = player;
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawRect(10, 9, 230, 21); 
		g.setColor(Color.green);
		g.fillRect(11, 10, (int)(player.getHp() * 2.3), 20); 
		g.setColor(Color.black);
		g.drawString(player.getHp() + "/100", 110, 25); 
		g.setColor(Color.gray);
		g.fillRect(250, 10, 380, 80); 
		g.fillRect(9, 40, 350, 50);
		g.setColor(Color.white);
		g.fillRect(12, 43, 44, 44);
		g.fillRect(60, 43, 44, 44);
		g.fillRect(108, 43, 44, 44);
		g.fillRect(156, 43, 44, 44);
		g.fillRect(204, 43, 44, 44);
		g.fillRect(254, 13, 74, 74);
		g.fillRect(333, 13, 74, 74);
		g.fillRect(412, 13, 74, 74);
		g.fillRect(491, 13, 134, 74);
	}
	@Override
	public void addNotify(){
		super.addNotify();
		Thread animationLoop = new Thread(this);
		animationLoop.start();
	}
	@Override
	public void run() {
		while(true){
			try {
				Thread.sleep(10);
				repaint();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
