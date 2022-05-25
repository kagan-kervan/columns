package util;

public class CardNode {

	private Object data;
	public CardNode next;
	public CardNode(Object data) 
	{
		this.data=data;
		this.next=null;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public CardNode getNext() {
		return next;
	}
	public void setNext(CardNode next) {
		this.next = next;
	}
	
}
