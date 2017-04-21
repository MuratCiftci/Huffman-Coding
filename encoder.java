import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class encoder {
	public static HashMap<String, Integer> hashMap = new HashMap<>();
	public static HashMap<String, String> codeMap = new HashMap<>();
	private static String FILENAME = null;
	private static final String CODEFILE = "code_table.txt";
	private static final String BINFILE = "encoded.bin";

	public String[] code;

	public static void createFrequencyTable() {
		hashMap.clear();
		FileReader in;
		try {
			in = new FileReader(FILENAME);
			BufferedReader br = new BufferedReader(in);
			String newLine;
			while ((newLine = br.readLine()) != null) {
				newLine = newLine.trim();
				if (!newLine.equals("")) {
					if (hashMap.get(newLine) == null) {
						hashMap.put(newLine, 1);
					} else {
						hashMap.put(newLine, hashMap.get(newLine) + 1);
					}
				}
			}

		} catch (FileNotFoundException e) {
			System.out.println("No file to write to.");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IO Exception.");
			e.printStackTrace();
		}

	}

	public static BinaryHeap buildBinaryHeap(HashMap freqtable) {
		BinaryHeap bh = new BinaryHeap(freqtable.size());
		for (Entry<String, Integer> entry : hashMap.entrySet()) {
			String key = entry.getKey();
			Integer value = entry.getValue();
			bh.insert(new Node(key, value));

		}
		return bh;
	}

	public static PairingHeap buildPairingHeap(HashMap freqtable) {
		PairingHeap ph = new PairingHeap(freqtable.size());
		for (Entry<String, Integer> entry : hashMap.entrySet()) {
			String key = entry.getKey();
			Integer value = entry.getValue();
			ph.insert(new Node(key, value));

		}
		return ph;
	}

	public static FourWayHeap buildFourWayHeap(HashMap freqtable) {
		FourWayHeap fwh = new FourWayHeap(freqtable.size());
		for (Entry<String, Integer> entry : hashMap.entrySet()) {
			String key = entry.getKey();
			Integer value = entry.getValue();
			fwh.insert(new Node(key, value));

		}
		return fwh;
	}

	public static Node buildHuffman(HashMap<String, Integer> freqtable, int type) {
		createFrequencyTable();
		if(freqtable.size()==0){
			return null;
		}
		BinaryHeap binaryHeap = null;
		switch (type) {
		case 0: {
			binaryHeap = buildBinaryHeap(freqtable);
			while (binaryHeap.getSize() != 1) {
				Node n1 = binaryHeap.extract_min();
				Node n2 = binaryHeap.extract_min();
				int freqSum = n1.getFrequency() + n2.getFrequency();
				Node newNode = new Node("" + System.currentTimeMillis(), freqSum);
				newNode.setLeft(n1);
				newNode.setRight(n2);
				newNode.setParent(null);
				binaryHeap.insert(newNode);

			}
			return binaryHeap.extract_min();
		}

		case 1: {
			FourWayHeap fourWayHeap = buildFourWayHeap(freqtable);
			while (fourWayHeap.getSize() != 1) {
				Node n1 = fourWayHeap.extract_min();
				Node n2 = fourWayHeap.extract_min();
				int freqSum = n1.getFrequency() + n2.getFrequency();
				Node newNode = new Node("" + System.currentTimeMillis(), freqSum);
				newNode.setLeft(n1);
				newNode.setRight(n2);
				newNode.setParent(null);
				fourWayHeap.insert(newNode);

			}
			return fourWayHeap.extract_min();
		}

		case 2: {
			PairingHeap pairingHeap = buildPairingHeap(freqtable);
			while (pairingHeap.getSize() != 1) {
				Node n1 = pairingHeap.extract_min().getNode();
				Node n2 = pairingHeap.extract_min().getNode();
				int freqSum = n1.getFrequency() + n2.getFrequency();
				Node newNode = new Node("" + System.currentTimeMillis(), freqSum);
				newNode.setLeft(n1);
				newNode.setRight(n2);
				newNode.setParent(null);
				pairingHeap.insert(newNode);

			}
			return pairingHeap.extract_min().getNode();
		}

		default: {
			System.out.println("Not a correct value");
			return null;
		}
		}
		// return binaryHeap;
	}

	public static void generateCode(Node node, String code) {
		// codeMap.clear();
		if (node != null) {
			if (node.getLeft() == null && node.getRight() == null) {
				codeMap.put(node.getValue(), code);
			} else {
				generateCode(node.getLeft(), code + "0");
				generateCode(node.getRight(), code + "1");
			}
		}
	}

	public static void writeOntoCodeTable(HashMap codemap) {
		try {
			PrintWriter writer = new PrintWriter(CODEFILE, "UTF-8");
			for (Entry<String, String> entry : codeMap.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				writer.println(key + " " + value);
			}

			writer.close();

		} catch (IOException e) {
			System.out.println("Not able to write file");
		}
	}

	public static void writeOntoEncodedFile() {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		FileReader in;
		try {
			in = new FileReader(FILENAME);
			BufferedReader br = new BufferedReader(in);
			String newLine;
			StringBuilder stringBuilder = new StringBuilder();

			while ((newLine = br.readLine()) != null) {
				newLine = newLine.trim();
				// System.out.println(newLine);
				if (!newLine.equals("")) {
					String value = codeMap.get(newLine);
					stringBuilder.append(value);
					while (stringBuilder.length() >= 8) {
						String str = stringBuilder.substring(0, 8);
						Integer intValueOfStr = null;
						try {
							intValueOfStr = Integer.parseInt(str, 2);
						} catch (Exception e) {
							System.out.println("sdasdfsf");
						}
						byte byteValueOfStr = intValueOfStr.byteValue();
						byteArrayOutputStream.write(byteValueOfStr);
						stringBuilder.delete(0, 8);

					}

				}

			}
			byte[] byteArray = byteArrayOutputStream.toByteArray();
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(BINFILE));
			bufferedOutputStream.write(byteArray);
			bufferedOutputStream.close();
			byteArrayOutputStream.close();
			br.close();
			in.close();
		} catch (FileNotFoundException e) {
			System.out.println("No file to write to.");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IO Exception.");
			e.printStackTrace();
		}

	}

	public static void main(String args[]) {

		if (args.length == 1) {
			FILENAME = args[0];
		} else {
			System.out.println("Wrong number of arguments provided.");
			System.out.println("Correct usage: java encoder <input_file_name>");
			System.exit(0);
		}
		double startTime = 0;

		startTime = System.currentTimeMillis();

		System.out.println("Starting to encode");
		Node binarynode = buildHuffman(hashMap, 1);
		System.out.println("Huffman Tree Generation Done. Proceeding to encode..");
		System.out.println("Time for tree generation: " + (System.currentTimeMillis() - startTime) / 1000 + " seconds");
		generateCode(binarynode, "");
		System.out.println("Encoding done. Printing values to code file..");
		writeOntoCodeTable(codeMap);
		System.out.println("Code table written. Writing encoded binary file..");
		writeOntoEncodedFile();
		System.out.println("Done");
		System.out.println(
				"Time to encode with Binary Heap " + (System.currentTimeMillis() - startTime) / 1000 + " seconds");

	}

}
