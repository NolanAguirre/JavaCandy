package map;

public class Node {
	private Node up;
	private Node down;
	private Node left;
	private Node right;
	private int seed;
	public Node(int seed) {
		this.seed = seed;
	}
	public void setUp(Node up) {
		this.up = up;
	}
	public void setDown(Node down) {
		this.down = down;
	}
	public void setLeft(Node left) {
		this.left = left;
	}
	public void setRight(Node right) {
		this.right = right;
	}
	public void setUp() {
		double rand = Math.random();
		if(rand < .3){
			up = new Node(8);
		}else if(rand < .48){
			if(rand < .32){
				up = new Node(5);
			}else if(rand < .39){
				up = new Node(12);	
			}else{
				up = new Node(13);
			}
		}else if(rand < .73){
			if(rand < .55){
				up = new Node(2);
			}else if (rand < .64){
				up = new Node(3);	
			}else{
				up = new Node(4);
			}
		}else if(rand < .96){
			up = new Node(0);
		}else{
			up = new Node(16);
		}
	}
	public void setDown() {
		double rand = Math.random();
		if(rand < .3){
		}else if(rand < .48){
			if(rand < .32){
				down = new Node(5);
			}else if(rand < .39){
				down = new Node(11);
			}else{
				down = new Node(14);
			}
		}else if(rand < .73){
			if(rand < .55){
				down = new Node(1);
			}else if (rand < .64){
				down = new Node(3);
			}else{
				down = new Node(4);
			}
		}else if(rand < .96){
			down = new Node(0);
		}else{
			down = new Node(15);
		}
	}
	public void setLeft() {
		double rand = Math.random();
		if( rand < .3){
			left = new Node(10);	
		}else if(rand < .48){
			if(rand < .32){
				left = new Node(6);
			}else if(rand < .39){
				left = new Node(14);
			}else{
				left = new Node(12);
			}
		}else if(rand < .73){
			if(rand < .55){
				left = new Node(1);
			}else if (rand < .64){
				left = new Node(4);
			}else{
				left = new Node(4);
			}
		}else if(rand < .96){
			left = new Node(0);
		}else{
			left = new Node(18);
		}
	}
	public void setRight() {
		double rand = Math.random();
		if(rand < .3){	
			 right = new  Node(9);	
		}else if(rand < .48){
			if(rand < .32){
				 right = new  Node(6);					
			}else if(rand < .39){			
				 right = new  Node(16);					
			}else{				
				 right = new  Node(11);			
			}		
		}else if(rand < .73){
			if(rand < .55){
				 right = new  Node(1);
			}else if (rand < .64){		
				 right = new  Node(3);			
			}else{		
				 right = new  Node(3);
			}	
		}else if(rand < .96){
			 right = new  Node(0);
		}else{
			 right = new  Node(17);
		}
	}
	public Node getUp(){
		return up;
	}
	public Node getDown() {
		return down;
	}
	public Node getLeft() {
		return left;
	}
	public Node getRight() {
		return right;
	}
	public int getSeed() {
		return seed;
	}
}
