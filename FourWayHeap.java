
public class FourWayHeap {

	private static Node[] arr;
	private int capacity;
	private static int heap_size;
	public static int OFFSET = 3;
	

	public FourWayHeap(int capacity) {
		this.capacity = capacity + 3;
		heap_size = 3;
		arr = new Node[capacity + OFFSET];

	}

	public Node getMin() {
		if (!isEmpty()) {
			return arr[3];
		}
		return null;
	}

	public int getSize() {
		return heap_size - OFFSET;
	}

	public boolean isEmpty() {
		return heap_size == 3;
	}

	public void min_heapify(int index) {
		int smallest = index;
		for (int i = 1; i <= 4; i++) {
			int child = dthchild(index, i);
			if (child < heap_size && arr[child].getFrequency() < arr[smallest].getFrequency()) {
				smallest = child;
			}
		}
		if (smallest != index) {
			swap(index, smallest);
			min_heapify(smallest);

		}

	}

	public static void swap(int a, int b) {
		Node temp = arr[a];
		arr[a] = arr[b];
		arr[b] = temp;
	}

	public static int dthchild(int index, int d) {
		return (4 * (index-OFFSET) + d + OFFSET) ;
	}

	public static int parent(int index) {
		return (index-1-OFFSET)/4 + OFFSET ;
	}

	public boolean insert(Node node) {
		if (capacity == heap_size) {
			return false;
		}
		arr[heap_size] = node;
		checkAfterInsertion(heap_size++);
		return true;

	}

	public void checkAfterInsertion(int index) {
		int parent = parent(index);
		Node insertedNode = arr[index];
		while (index > 3 && arr[parent].getFrequency() > insertedNode.getFrequency()) {
			arr[index] = arr[parent];
			index = parent;
			parent = parent(parent);

		}
		arr[index] = insertedNode;
	}

	public Node extract_min() {
		if (heap_size <=3) {
			System.out.println("No elements left");
			return null;
		} else {
			Node min = arr[3];
			arr[3] = arr[--heap_size];
			min_heapify(3);
			return min;
		}
	}

}
