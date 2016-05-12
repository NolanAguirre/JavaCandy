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
		g.drawRect(50, 30, 200, 20); 
		g.setColor(Color.green);
		g.fillRect(50, 30, player.getHp() * 2, 20); 
		g.setColor(Color.black);
		g.drawString(player.getHp() + "/100", 135, 45); 
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
