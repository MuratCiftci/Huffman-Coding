import java.util.ArrayList;

public class HuffmanBinaryTree {
	Node root;
	ArrayList<String> stringArray;
	public HuffmanBinaryTree() {
		root  = null;
		this.stringArray = new ArrayList<>();

	}
	
	public ArrayList<String> getStringArray() {
		return stringArray;
	}

	public void setStringArray(ArrayList<String> stringArray) {
		this.stringArray = stringArray;
	}

	public class Node {
		String value;
		Node leftChild;
		Node rightChild;
		
		public Node(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public Node getLeftChild() {
			return leftChild;
		}

		public void setLeftChild(Node leftChild) {
			this.leftChild = leftChild;
		}

		public Node getRightChild() {
			return rightChild;
		}

		public void setRightChild(Node rightChild) {
			this.rightChild = rightChild;
		}

	}

	public void insert(String str) {
		if(root==null)
		 root = new Node("root");
		 Node head = root;
		for (int k = 0; k <= str.length()-1; k++) {
			if (k == str.length() - 1) {
				Node node = new Node(str);
				if(str.charAt(k)=='1'){
					head.rightChild = node;
				}
				else if(str.charAt(k)=='0'){
					head.leftChild = node;
				}
			} else {
				Node temp = new Node("$");
				if (str.charAt(k) == '1') {
					if (head.rightChild == null) {

						head.rightChild = temp;
						head = head.rightChild;
					} else {
						head = head.rightChild;
					}
				} else if (str.charAt(k) == '0') {
					if (head.leftChild == null) {
						head.leftChild = temp;
						head = head.leftChild;
					} else {
						head = head.leftChild;
					}
				}
			}
		}
	}
}
