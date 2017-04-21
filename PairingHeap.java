
public class PairingHeap {

	private static PairNode root;
	private static int heap_size;
	private PairNode[] arr;

	public PairingHeap(int capacity) {
		heap_size = 0;
		arr = new PairNode[capacity];
		root = null;
	}
	
    public int getSize() {
        return heap_size;
    }

	public boolean isEmpty() {
		return root == null;
	}

	public Node getMin() {
		if (!isEmpty()) {
			return arr[0];
		}
		return null;
	}
	public void insert(Node node) {
		//PairNode newNode = new PairNode(new Node);
		heap_size++;
		PairNode pairnode = new PairNode(node);
		if (root == null)
			root = pairnode;
		else
			root = compareAndLink(root, pairnode);
		//return node;
	}

	private PairNode compareAndLink(PairNode first, PairNode second) {
		if (second == null)
			return first;

		if (second.getFrequency() < first.getFrequency()) {
			/* Attach first as leftmost child of second */
			second.prev = first.prev;
			first.prev = second;
			first.sibling = second.leftChild;
			if (first.sibling != null)
				first.sibling.prev = first;
			second.leftChild = first;
			return second;
		} else {
			/* Attach second as leftmost child of first */
			second.prev = first;
			first.sibling = second.sibling;
			if (first.sibling != null)
				first.sibling.prev = first;
			second.sibling = first.leftChild;
			if (second.sibling != null)
				second.sibling.prev = second;
			first.leftChild = second;
			return first;
		}
	}
	
	private PairNode combineSiblings(PairNode firstSibling)
    {
        if( firstSibling.sibling == null )
            return firstSibling;
        /* Store the subtrees in an array */
        int numSiblings = 0;
        for ( ; firstSibling != null; numSiblings++)
        {
            arr = increaseSize( arr, numSiblings );
            arr[ numSiblings ] = firstSibling;
            /* break links */
            firstSibling.prev.sibling = null;  
            firstSibling = firstSibling.sibling;
        }
        arr = increaseSize( arr, numSiblings );
        arr[ numSiblings ] = null;
        /* Combine subtrees two at a time, going left to right */
        int i = 0;
        for ( ; i + 1 < numSiblings; i += 2)
            arr[ i ] = compareAndLink(arr[i], arr[i + 1]);
        int j = i - 2;
        /* j has the result of last compareAndLink */
        /* If an odd number of trees, get the last one */
        if (j == numSiblings - 3)
            arr[ j ] = compareAndLink( arr[ j ], arr[ j + 2 ] );
        /* Now go right to left, merging last tree with */
        /* next to last. The result becomes the new last */
        for ( ; j >= 2; j -= 2)
            arr[j - 2] = compareAndLink(arr[j-2], arr[j]);
        return arr[0];
    }

    private PairNode[] increaseSize(PairNode [ ] array, int index)
    {
        if (index == array.length)
        {
            PairNode [ ] oldArray = array;
            array = new PairNode[index * 2];
            for( int i = 0; i < index; i++ )
                array[i] = oldArray[i];
        }
        return array;
    }
	
    public PairNode extract_min(){
        if (isEmpty( ) )
            return null;
        PairNode x = root;
        if (root.leftChild == null)
            root = null;
        else
            root = combineSiblings( root.leftChild );
        heap_size--;
        return x;

    }
    
    /* inorder traversal */
    public static void inorder()
    {
        inorder(root);
    }
    
    private static void inorder(PairNode r)
    {
        if (r != null)
        {
            inorder(r.leftChild);
            System.out.print(r.getFrequency() +" ");
            inorder(r.sibling);
        }
    }
    
    public static void main(String args[]){
    	PairingHeap ph = new PairingHeap(10);
    	ph.insert(new Node("3",3));
    	ph.insert(new Node("1",1));
    	ph.insert(new Node("5",5));
    	ph.insert(new Node("7",7));
    	ph.insert(new Node("9",9));
    	inorder();
    	System.out.println();
    	ph.extract_min();
    	inorder();
    	System.out.println();
    	ph.extract_min();
    	inorder();
    	System.out.println();
    	ph.extract_min();
    	inorder();
    	System.out.println();
    	ph.extract_min();
    	inorder();
    	System.out.println();
    	ph.extract_min();
    	inorder();
    	System.out.println();
    	ph.extract_min();
    	inorder();
    	System.out.println();
    }
    

}
