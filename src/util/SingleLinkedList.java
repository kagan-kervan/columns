package util;

public class SingleLinkedList {
	class Node {
		Object data;
		Node link = null;

		public Node(Object dataToAdd) {
			data = dataToAdd;
			link = null;
		}

		public Object getData() {
			return data;
		}

		public void setData(Object data) {
			this.data = data;
		}

		public Node getLink() {
			return link;
		}

		public void setLink(Node link) {
			this.link = link;
		}
	}

	private Node head = null;

	public SingleLinkedList() {
	}

	public void add(Object data) {
		if (head == null) {
			Node newNode = new Node(data);
			head = newNode;
		} else {
			Node temp = head;
			while (temp.getLink() != null) {
				temp = temp.getLink();
			}
			Node newNode = new Node(data);
			temp.setLink(newNode);
		}
	}

	public void removeNodeWithPosition(int position) {
		if (head == null) {
			return;
		}

		Node temp = head;

		if (position == 0) {
			head = temp.link;
			return;
		}
		for (int i = 0; temp != null && i < position - 1; i++) {
			temp = temp.link;
		}
		if (temp == null || temp.link == null) {
			return;
		}
		Node next = temp.link.link;

		temp.link = next;
	}

	public void addSorted(Object dataToAdd) {

		if (head == null) {
			Node newnode = new Node(dataToAdd);
			head = newnode;
		}

		else if ((int) dataToAdd > (int) head.getData()) {
			Node newnode = new Node(dataToAdd);
			newnode.setLink(head);
			head = newnode;
		}

		else {
			Node temp = head;
			Node previous = null;
			while (temp != null && (int) dataToAdd <= (Integer) temp.getData()) {
				previous = temp;
				temp = temp.getLink();

			}
			if (temp == null) {
				Node newnode = new Node(dataToAdd);
				previous.setLink(newnode);
			} else {
				Node newnode = new Node(dataToAdd);
				previous.setLink(newnode);
				newnode.setLink(temp);

			}
		}
	}

	public int returnHead() {
		return (int) head.getData();

	}

	public int size() {
		if (head == null) {
			return 0;
		} else {
			int count = 0;
			Node temp = head;
			while (temp != null) {
				temp = temp.getLink();
				count++;
			}
			return count;
		}
	}

	public void display() {
		if (head == null)
			System.out.println("List is empty");
		else {
			Node temp = head;

			while (temp != null) {
				System.out.print(temp.getData() + " ");
				temp = temp.getLink();
			}
		}
	}

	public void remove(Object dataToDelete) {
		if (head == null) {
			System.out.println("linked lis is empty");
		} else if (size() == 1) {
			head = null; ////////////////
		} else {
			while ((Integer) head.getData() == (Integer) dataToDelete)
				head = head.getLink();

			Node temp = head;
			Node previous = null;
			while (temp != null) {
				if ((Integer) temp.getData() == (Integer) dataToDelete) {
					previous.setLink(temp.getLink());
					temp = previous;
				}
				previous = temp;
				temp = temp.getLink();
			}
		}

	}

	public int findMax() {
		if (head == null) {
			System.err.println("The Linked List is empty");
			return Integer.MIN_VALUE;
		} else {
			int maxVal = Integer.MIN_VALUE;

			Node temp = head;

			while (temp != null) {
				if ((int) temp.getData() > maxVal) {
					maxVal = (int) temp.getData();
				}
				temp = temp.getLink();
			}
			return maxVal;
		}

	}

	public boolean search(Object data) {
		if (head == null) {
			System.out.println("List is empty");
			return false;
		} else {
			Node temp = head;
			while (temp != null) {
				if ((Integer) temp.getData() == (Integer) data)
					return true;
				temp = temp.getLink();
			}
			return false;
		}
	}

	public int getIndex(Object data) {
		if (head == null) {
			System.out.println("List is empty");
			return 0;
		} else {
			Node temp = head;
			int counter = 0;
			while (temp != null) {
				if ((Integer) temp.getData() == (Integer) data) {
					return counter;
				}
				counter++;
				temp = temp.getLink();
			}
			return 0;
		}
	}

	public Node getNode(String data) {
		return new Node(data);
	}

	public Node addToPosition(int position, String data) {
		Node temp = head;
		if (position < 1) {
			System.out.print("Invalid position");
		}
		if (position == 1) {
			Node newNode = new Node(data);
			newNode.link = temp;
			head = newNode;
		} else {
			while (position-- != 0) {

				if (position == 1) {

					Node newNode = getNode(data);
					newNode.link = temp.link;
					temp.link = newNode;

					break;
				}
				temp = temp.link;
			}
			if (position != 1) {
				System.out.print("Position out of range");
			}
		}
		return head;
	}
}