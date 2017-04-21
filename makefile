all:
	javac encoder.java decoder.java BinaryHeap.java FourWayHeap.java HuffmanBinaryTree.java Node.java PairingHeap.java PairNode.java 
	
clean:
	rm -rf *.class