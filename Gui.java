import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
public class Gui extends JFrame {
    private Store store;
	private JPanel contentPane;
	public JTextField candyAmountDisplay;
	public Gui(Store foo) {
        this.store = foo;
		this.setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
        //display hello or something
        setName();
	}
    private void setName(){
        //store.player.setName(); userInputName.getText();
        loadGame();
    }
    private void loadGame(){
        navBar();
        mainMap();
    }
    public void navBar(){
        candyCount();
        playerStats();
    }
    public void candyCount(){
        candyAmountDisplay = new JTextField();
        candyAmountDisplay.setText("0");
        candyAmountDisplay.setBounds(10, 11, 44, 20);
        contentPane.add(candyAmountDisplay);
        candyAmountDisplay.setColumns(10);
        JButton btnNewButton = new JButton("Click For More Candy!");
        btnNewButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                store.candyCount.add();
            }
        });
        btnNewButton.setBounds(128, 231, 193, 20);
        contentPane.add(btnNewButton);
    }
    public void playerStats(){
        /*
            button to inventory
            health
            weapon equiped
            level
            name
        */
    }
    private void inventory(){

    }
    private void mainMap(){
        /*
            on left click go to the left
            on right click go to the right
            on up click go up
            on down click go down

        */
    }
    public void promptAttack(){

    }
    public void mobHitPlayer(){

    }
    public void playerHitMob(){

    }
    public void mobWinner(){

    }
    public void playerWinner(){

    }
    public void endBattle(){
        loadGame();
    }
    public void gameOver(){
        System.exit(0);
    }
}
