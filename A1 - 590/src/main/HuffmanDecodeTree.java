package main;

import java.io.IOException;
import java.util.List;
import io.InputStreamBitSource;
import io.InsufficientBitsLeftException;

public class HuffmanDecodeTree {

	private HuffmanNode _root;

	public HuffmanDecodeTree(List<SymbolWithCodeLength> symbols_with_code_lengths) {

		// Create empty internal root node.

		_root = new InternalHuffmanNode();

		// Insert each symbol from list into tree
		for (int i = 0; i < 256; i++) {
			_root.insertSymbol(symbols_with_code_lengths.get(i).codeLength(), symbols_with_code_lengths.get(i).value());
		}

		// If all went well, your tree should be full
		assert _root.isFull();
	}

	public int decode(InputStreamBitSource bit_source) throws IOException, InsufficientBitsLeftException {

		// Start at the root
		HuffmanNode node = _root;

		while (!node.isLeaf()) {
			int nextBit = bit_source.next(1);
			if (nextBit == 0) {
				node = node.left();
			} else {
				node = node.right();
			}
		}
		// Return symbol at leaf
		return node.symbol();

	}
}