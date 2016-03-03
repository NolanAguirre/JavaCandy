import javax.swing.JTextField;
public class CandyCount {
	private int candies;
    private Store store;
	public CandyCount(Store store){
        this.store = store;
        candies = 0;
	}
	public void add(int candies){
		this.candies += candies;
        update();
	}
	public void add(){
		candies++;
        update();
	}
    public void update(){
        //store.gui.candyAmountDisplay.setText(""+ candies);
    }
	public int getCandies(){
		return candies;
    }
}
