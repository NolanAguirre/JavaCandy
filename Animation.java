import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
public class Animation extends JFrame {
    public static Store store = new Store();
    public Animation() {
        initUI(store);
    }
    private void initUI(Store store) {
        addKeyListener(new MyKeyListener());
        add(new Board(store));
        setResizable(false);
        pack();
        setTitle("Star");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame ex = new Animation();
                ex.setVisible(true);
            }
        });
    }

    class MyKeyListener implements KeyListener{
        @Override
        public void keyTyped(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_RIGHT ) {
                store.player.move(2,0);
            }
            else if (e.getKeyCode() == KeyEvent.VK_LEFT ) {
                store.player.move(-2,0);
            }
            else if (e.getKeyCode() == KeyEvent.VK_UP ) {
                store.player.move(0,-2);
            }
            else if (e.getKeyCode() == KeyEvent.VK_DOWN ) {
                store.player.move(0,2);
            }
            else{
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_RIGHT ) {
                store.player.move(2,0);
            }
            else if (e.getKeyCode() == KeyEvent.VK_LEFT ) {
                store.player.move(-2,0);
            }
            else if (e.getKeyCode() == KeyEvent.VK_UP ) {
                store.player.move(0,-2);
            }
            else if (e.getKeyCode() == KeyEvent.VK_DOWN ) {
                store.player.move(0,2);
            }
            else{
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            store.player.move(0,0);
            store.player.move(0,0);
        }
    }
}
