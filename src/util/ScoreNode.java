package util;


public class ScoreNode {

	Object data;
	double score;
	ScoreNode next;
	ScoreNode prev;

	public ScoreNode(Object dataToAdd, double scoretoAdd) {
		data = dataToAdd;
		score = scoretoAdd;
		next = null;
		prev = null;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public ScoreNode getNext() {
		return next;
	}

	public void setNext(ScoreNode next) {
		this.next = next;
	}

	public ScoreNode getPrev() {
		return prev;
	}

	public void setPrev(ScoreNode prev) {
		this.prev = prev;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}


}
