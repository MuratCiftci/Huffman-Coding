import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

public class decoder {
	public static HashMap<String, String> codeMap = new HashMap<>();
	public static HuffmanBinaryTree binarytree = new HuffmanBinaryTree();

	private static String CODEFILE = null;
	private static final String DECODEFILE = "decoded.txt";
	private static String BINFILE = null;

	public static void readFromCodeTable() {
		FileReader in;
		try {
			in = new FileReader(CODEFILE);
			BufferedReader br = new BufferedReader(in);
			String newLine;
			while ((newLine = br.readLine()) != null) {
				newLine = newLine.trim();

				if (!newLine.equals("")) {
					String[] codeArray = newLine.split(" ");
					String key = codeArray[0];
					String binaryValue = codeArray[1];
					codeMap.put(binaryValue, key);
				}
			}
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

	public static byte[] readFromEncodedFile(String aInputFileName) {

		File file = new File(aInputFileName);
		byte[] result = new byte[(int) file.length()];
		try {
			InputStream input = null;
			try {
				int totalBytesRead = 0;
				input = new BufferedInputStream(new FileInputStream(file));
				while (totalBytesRead < result.length) {
					int bytesRemaining = result.length - totalBytesRead;
					int bytesRead = input.read(result, totalBytesRead, bytesRemaining);
					if (bytesRead > 0) {
						totalBytesRead = totalBytesRead + bytesRead;
					}
				}
			} finally {
				input.close();
			}
		} catch (FileNotFoundException ex) {
			System.out.println("File not found.");
		} catch (IOException ex) {
			System.out.println(ex);
		}

		return result;

	}

	public static StringBuilder byteToString() {
		byte[] byteArrayFromFile = readFromEncodedFile(BINFILE);
		int byteArrayLength = byteArrayFromFile.length;
		StringBuilder stringBuilder = new StringBuilder();
		for (byte newbyte : byteArrayFromFile) {
			stringBuilder.append(Integer.toBinaryString(newbyte & 255 | 256).substring(1));
			if (stringBuilder.length() >= byteArrayLength * 8 / 200) {
				int pos = decodeBinary(stringBuilder.toString());
				stringBuilder.delete(0, pos);
			}
		}
		if (stringBuilder.length() != 0) {
			decodeBinary(stringBuilder.toString());
		}
		byteArrayFromFile = null;
		return stringBuilder;

	}

	public static void generateTree() {
		for (String value : codeMap.keySet()) {
			binarytree.insert(value);
		}

	}

	public static int decodeBinary(String decoded) {
		HuffmanBinaryTree.Node root = binarytree.root;
		HuffmanBinaryTree.Node head = root;
		int lastSuccesfulPos = 0;
		for (int k = 0; k <= decoded.length(); k++) {
			if (head.leftChild == null && head.rightChild == null) {
				lastSuccesfulPos = k;
				if (k != decoded.length())
					k--;
				binarytree.stringArray.add(codeMap.get(head.value));
				head = root;
			} else if (k == decoded.length()) {
				break;
			} else {

				if (decoded.charAt(k) == '1') {
					head = head.rightChild;
				} else if (decoded.charAt(k) == '0') {
					head = head.leftChild;
				}
			}
		}
		// decoded.setLength(0);
		head = null;
		root = null;
		return lastSuccesfulPos;
	}

	public static void writetoDecodedFile(ArrayList<String> arrList) {
		try {
			FileWriter filewriter = new FileWriter(DECODEFILE);
			for (String string : arrList) {
				filewriter.write(string + "\n");
			}
			filewriter.close();
		} catch (IOException e) {
			System.out.println("Error writing decode file.");
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		if (args.length == 2) {
			BINFILE = args[0];
			CODEFILE = args[1];
		} else {
			System.out.println("Wrong number of arguments provided.");
			System.out.println("Correct usage: java decoder <encoded_file_name> <code_table_file_name>");
			System.exit(0);
		}
		double startTime = 0;
		startTime = System.currentTimeMillis();
		System.out.println("Reading from Code table.");
		readFromCodeTable();
		System.out.println("Generating tree.");
		generateTree();
		System.out.println("Writing to decoded file.");
		byteToString();
		writetoDecodedFile(binarytree.getStringArray());
		System.out.println("Time to decode: " + (System.currentTimeMillis() - startTime)/1000);

	}

}
