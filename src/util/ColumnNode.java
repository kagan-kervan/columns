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
