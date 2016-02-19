import javax.swing.JTextField;
public class CandyCount {
	private int candies;
	JTextField text;
	public CandyCount(JTextField display){
		candies = 0;
		text = display;
		Tick upCount = new Tick(this);
	}
	public void addCandy(int candies){
		this.candies += candies;
	}
	public void addCandy(){
		candies++;
	}
	public int getCandies(){
		return candies;
	}
	private void update(){
		text.setText("" +candies);
	}
}
