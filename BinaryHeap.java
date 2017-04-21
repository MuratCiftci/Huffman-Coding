//import java.util.ArrayList;

public class BinaryHeap {

	private static Node[] arr;
	private int capacity;
	private static int heap_size;

	public BinaryHeap(int capacity) {
		this.capacity = capacity;
		heap_size = 0;
		arr = new Node[capacity];
		
	}
	
    public int getSize() {
        return heap_size;
    }

	public boolean isEmpty() {
		return heap_size == 0;
	}

	public void min_heapify(int index) {
		int left = left(index);
		int right = right(index);
		int smallest;
		if (left < heap_size && arr[left].getFrequency() < arr[index].getFrequency()) {
			smallest = left;
		} else {
			smallest = index;
		}
		if (right < heap_size && arr[right].getFrequency() < arr[smallest].getFrequency()) {
			smallest = right;
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

	public int left(int index) {
		return (2 * index) + 1;
	}

	public int right(int index) {
		return (2 * index) + 2;
	}

	public int parent(int index) {
		return index / 2;
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
		int parent = (index - 1) / 2;
		Node insertedNode = arr[index];
		while (index > 0 && arr[parent].getFrequency() > insertedNode.getFrequency()) {
			arr[index] = arr[parent];
			index = parent;
			parent = (parent - 1) / 2;

		}
		arr[index] = insertedNode;
	}


	public Node extract_min() {
		if (heap_size < 1) {
			System.out.println("No elements left");
			return null;
		} else {
			Node min = arr[0];
			arr[0] = arr[--heap_size];
			min_heapify(0);
			return min;
		}
	}

	public Node getMin() {
		if (!isEmpty()) {
			return arr[0];
		}
		return null;
	}


}
