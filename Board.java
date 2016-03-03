import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.Rectangle;
public class Board extends JPanel implements Runnable{
    private final int B_WIDTH = 700;
    private final int B_HEIGHT = 700;
    private final int DELAY = 20;
    private Image person;
    private Image map;
    private Thread animator;
    private Store store;
    Map currentMap;
    public enum Map{
        MAIN,
        LEFT,
        RIGHT
    }
    public File getMap(){
        switch(currentMap){
            case MAIN:
                return new File("mainMap.png");
        }
        return new File("mainMap.png");
    }
    public Board(Store store) {
        this.store = store;
        currentMap = Map.MAIN;
        initBoard();
    }
    private void loadImage() {
        try{
            BufferedImage foo = ImageIO.read(new File("person.png"));
            person = foo.getSubimage(35,35,65,75);
        }catch(IOException ex){
            System.out.println("image not fount");
        }

    }
    private void loadMap(){
        try{
            BufferedImage tempMap = ImageIO.read(getMap());
            map = tempMap.getSubimage(0,0,350,350);
        }catch(IOException ex){
            System.out.println("image not fount");
        }
    }
    private void initBoard() {
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        setDoubleBuffered(true);
        requestFocusInWindow();
        loadImage();
        loadMap();
    }
    @Override
    public void addNotify() {
        super.addNotify();
        animator = new Thread(this);
        animator.start();
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawMap(g);
        drawPerson(g);
    }
    private void drawPerson(Graphics g) {
        g.drawImage(person, store.player.getX(), store.player.getY(), this);
        //Rectangle hitBox = new Rectangle(x, y, 65, 75);
        Toolkit.getDefaultToolkit().sync();
    }
    private void drawMap(Graphics g) {
        g.drawImage(map, 0, 0, this);
        Toolkit.getDefaultToolkit().sync();
    }
    private void cycle() {
    }
    @Override
    public void run() {
        long beforeTime, timeDiff, sleep;
        beforeTime = System.currentTimeMillis();
        while (true) {
            cycle();
            repaint();
            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = DELAY - timeDiff;
            if (sleep < 0) {
                sleep = 2;
            }
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                System.out.println("Interrupted: " + e.getMessage());
            }
            beforeTime = System.currentTimeMillis();
        }
    }
}
