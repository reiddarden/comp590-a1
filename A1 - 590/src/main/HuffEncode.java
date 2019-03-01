package main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import io.OutputStreamBitSink;

public class HuffEncode {

	public static void main(String[] args) throws IOException {
		String input_file_name = "/Users/ReidDarden/Documents/EclipseProjects/A1 - 590/src/data/uncompressed.txt";
		String output_file_name = "/Users/ReidDarden/Documents/EclipseProjects/A1 - 590/src/data/recompressed.txt";

		FileInputStream fis = new FileInputStream(input_file_name);

		int[] symbol_counts = new int[256];
		int num_symbols = 0;

		int next = fis.read();
		while (next != -1) {
			symbol_counts[next]++;
			num_symbols++;
			next = fis.read();
		}

		// Close input file
		fis.close();

		// Create array of symbol values

		int[] symbol_values = new int[256];
		for (int i = 0; i < 256; i++) {
			symbol_values[i] = i;
		}

		// Create encoder using symbols and their associated counts from file.

		HuffmanEncoder encoder = new HuffmanEncoder(symbol_values, symbol_counts);

		// Open output stream.
		FileOutputStream fos = new FileOutputStream(output_file_name);
		OutputStreamBitSink bit_sink = new OutputStreamBitSink(fos);

		// Write out code lengths for each symbol as 8 bit value to output file.
		for (int i = 0; i < 256; i++) {
			bit_sink.write(encoder.getCode(i).length(), 8);
		}
		// Write out total number of symbols as 32 bit value.
		bit_sink.write(num_symbols, 8);

		// Reopen input file.
		fis = new FileInputStream(input_file_name);

		next = fis.read();
		while (next != -1) {
			encoder.encode(next, bit_sink);
			next = fis.read();
		}

		// Pad output to next word.
		bit_sink.padToWord();

		// Close files.
		fis.close();
		fos.close();
		System.out.println("File has been encoded, located at: " + output_file_name);
		double compressed = 0;
		for (int i = 0; i < 256; i++) {
			double probability = ((double) symbol_counts[i] / (double) num_symbols);
			if (probability > 0) {
				compressed += ((double) probability * (double) encoder.getCode(i).length());
			}
		}
		System.out.println("My encoder's compressed entropy is: " + compressed + " bits/symbols");

	}
}