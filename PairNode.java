
public class PairNode extends Node {
	public Node node;
	public PairNode leftChild;
	public PairNode sibling;
	public PairNode prev;
	
	public PairNode(Node node)
	{	
		super(node.getValue(),node.getFrequency());
		this.node = node;
		leftChild = null;
		sibling = null;
		prev = null;
	}

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	public PairNode getLeftChild() {
		return leftChild;
	}

	public void setLeftChild(PairNode leftChild) {
		this.leftChild = leftChild;
	}

	public PairNode getSibling() {
		return sibling;
	}

	public void setSibling(PairNode sibling) {
		this.sibling = sibling;
	}

	public PairNode getPrev() {
		return prev;
	}

	public void setPrev(PairNode prev) {
		this.prev = prev;
	}
	
	
}
