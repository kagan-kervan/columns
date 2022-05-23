package util;

public class ColumnNode {

	private Object data;
	private ColumnNode down;
	private CardNode right;
	
	public ColumnNode(Object data) {
		this.data=data;
		this.down=null;
		this.right=null;
	}

	public Object getData() {
		return data;
	}
	
	public int getSize() {
		if (this.right == null) return 0;
		
		CardNode card = this.right;
		int count = 0;
		
		while(card != null) {
			card = card.getNext();
			count++;
		}
		
		return count;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public ColumnNode getDown() {
		return down;
	}

	public void setDown(ColumnNode down) {
		this.down = down;
	}

	public CardNode getRight() {
		return right;
	}

	public void setRight(CardNode right) {
		this.right = right;
	}
}
