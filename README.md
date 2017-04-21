A Huffman code is a particular type of optimal prefix code that is commonly used for lossless data compression. You can read more about it here: https://en.wikipedia.org/wiki/Huffman_coding

Encoding: 
1.	Build a frequency table for the dataset. 
2.	Build a Huffman Binary Tree from the given frequency table with the best performance priority-queue data structure(choice between Binary, 4-Way and Pairing Heap). 
3.	Generate as output an encoded file and a code table file (used to decode an encoded file). 
 
Decoding: 
1.	Take as input a code table and an encoded file and construct a binary Huffman tree from the given code table. 
2.	Use the binary tree to decode the encoded binary file to generate a decoded file. 

Command to build the java files:- make
This command should produce two binary files: encoder and decoder.

To run encoder: 
<i>java encoder <input_file_name></i>

Running encoder program must produce the output files with exact name "encoded.bin" and "code_table.txt".

On the other hand, decoder will take two input files. We will run it using following command:

<i>java decoder <encoded_file_name> <code_table_file_name></i><br>
In this case: java decoder encoded.bin code_table.txt

Running decoder program must produce output file with exact name "decoded.txt".
