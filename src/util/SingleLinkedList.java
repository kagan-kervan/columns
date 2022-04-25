package util;

public class SingleLinkedList<T> {
	class Node<T> {
		T element;
		Node<T> next = null;
		
		Node(T element) {
			this.element = element;
		}
	}
	
	private Node<T> head = null;
	
	public SingleLinkedList() {}
	
	public void push(T element) {
		
	}
	
	public T remove() {
		return null;
	}
}