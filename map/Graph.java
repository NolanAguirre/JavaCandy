package map;
public class Graph {
	private Node current;

	public Graph(){
		current = new Node(0);
	}
	public Node getCurrent(){
		return current;
	}
	public void moveUp(){
		if(current.getUp() == null){
			current.setUp();
			current.getUp().setDown(current);
		}
		current = current.getUp();
	}
	public void moveDown(){
		if(current.getDown() == null){
			current.setDown();
			current.getDown().setUp(current);
		}
		current = current.getDown();
	}
	public void moveLeft(){
		if(current.getLeft() == null){
			current.setLeft();
			current.getLeft().setRight(current);
		}
		current = current.getLeft();
	}
	
	public void moveRight(){
		if(current.getRight() == null){
			current.setRight();
			current.getRight().setLeft(current);
		}
		current = current.getRight();
	}	
}
