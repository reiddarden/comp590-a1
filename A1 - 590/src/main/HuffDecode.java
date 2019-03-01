package main;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import io.InputStreamBitSource;
import io.InsufficientBitsLeftException;

public class HuffDecode {

	public static void main(String[] args) throws IOException, InsufficientBitsLeftException {

		String input_file_name = "/Users/ReidDarden/Documents/EclipseProjects/A1 - 590/src/data/compressed.dat";
		String output_file_name = "/Users/ReidDarden/Documents/EclipseProjects/A1 - 590/src/data/uncompressed.txt";

		FileInputStream fis = new FileInputStream(input_file_name);

		InputStreamBitSource bit_source = new InputStreamBitSource(fis);

		List<SymbolWithCodeLength> symbols_with_length = new ArrayList<SymbolWithCodeLength>();

		// Read in huffman code lengths from input file and associate them with
		// each symbol as a
		// SymbolWithCodeLength object and add to the list symbols_with_length.

		for (int i = 0; i < 256; i++) {
			int length = bit_source.next(8);
			symbols_with_length.add(new SymbolWithCodeLength(i, length));
		}
		// Then sort the symbols.
		Collections.sort(symbols_with_length);
		// Now construct the canonical huffman tree

		HuffmanDecodeTree huff_tree = new HuffmanDecodeTree(symbols_with_length);

		// Read in the next 32 bits from the input file the number of symbols
		int num_symbols = bit_source.next(32);

		int[] symbol_counts = new int[256];

		try {

			// Open up output file.

			FileOutputStream fos = new FileOutputStream(output_file_name);

			for (int i = 0; i < num_symbols; i++) {
				int next_sym = huff_tree.decode(bit_source);
				symbol_counts[next_sym]++;
				fos.write(next_sym);
			}

			// Flush output and close files.

			fos.flush();
			fos.close();
			fis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("File has been decoded, located at: " + output_file_name);
		System.out.println("----------");

		double entropy = 0;
		double compressed = 0;

		for (int i = 0; i < 256; i++) {
			double probability = ((double) symbol_counts[i] / (double) num_symbols);
			System.out.println("Probability of symbol at index " + i + " is " + probability);
			if (probability > 0) {
				entropy += ((double) probability * -1 * (Math.log((double) probability)) / Math.log(2));
				compressed += ((double) probability * (double) symbols_with_length.get(i).codeLength());
			}
		}
		System.out.println("----------");
		System.out.println("Theoretical entropy is: " + entropy + " bits/symbols");
		System.out.println("----------");
		System.out.println("Compressed entropy is: " + compressed + " bits/symbol");

	}
}