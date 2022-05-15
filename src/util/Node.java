package util;


public class Node {

	Object data;
	double score;
	Node next;
	Node prev;

	public Node(Object dataToAdd,double scoretoAdd) {
		data = dataToAdd;
		score=scoretoAdd;
		next=null;
		prev=null;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	public Node getNext() {
		return next;
	}

	public void setNext(Node next) {
		this.next = next;
	}

	public Node getPrev() {
		return prev;
	}

	public void setPrev(Node prev) {
		this.prev = prev;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}
	
}
