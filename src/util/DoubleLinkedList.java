package util;

public class DoubleLinkedList {

	public ScoreNode head;
	public ScoreNode tail;

	public DoubleLinkedList() {
		head = null;
		tail = null;
	}

	public void SortedAdd(ScoreNode NewNode) {
		if (head == null) // If list is empty, the node adds as head.
		{
			head = NewNode;
			tail= NewNode;
		}
		 else if (NewNode.getScore() > head.getScore()) {
				NewNode.setNext(head);
				head = NewNode;
				NewNode.getNext().setPrev(NewNode);

			}
		else {
			ScoreNode current = head; // Current node
			ScoreNode save = null; // Node that saves one step before.
			while (current.getNext() != null && current.getScore() > NewNode.getScore()) // Takes till the score is
																							// smaller.
			{
				save = current;
				current = current.getNext();
			}
			if (current.getNext() == null && current.getScore() > NewNode.getScore()) // if the node is the smallest.
			{
				tail.setNext(NewNode); // Makes the new node, tail.
				NewNode.setPrev(tail);
				tail = NewNode;
			} else {
				save.setNext(NewNode);
				NewNode.setNext(current); // Adds the new node between the save and current nodes.
				current.setPrev(NewNode);
				NewNode.setPrev(save);

			}
		}

	}

	public void displayDescending() // Descending display.
	{
		ScoreNode temp = head;
		while (temp != null) {
			System.out.println(temp.getData() + "-" + temp.getScore());
			temp = temp.getNext();
		}
		System.out.println();
	}

	public void displayAscending() // Ascending display.
	{
		ScoreNode temp = tail;
		while (temp != null) {
			System.out.println(temp.getData() + "-" + temp.getScore());
			temp = temp.getPrev();
		}
		System.out.println();
	}

}
