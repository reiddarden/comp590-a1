package main;

public class LeafHuffmanNode implements HuffmanNode {
	private int _symbol;
	private int _count;

	public LeafHuffmanNode(int symbol, int count) {
		_symbol = symbol;
		_count = count;
	}

	@Override
	public int count() {
		return _count;
	}

	@Override
	public boolean isLeaf() {
		return true;
	}

	@Override
	public int symbol() {
		return _symbol;
	}

	@Override
	public int height() {
		return 0;
	}

	@Override
	public boolean isFull() {
		return true;
	}

	@Override
	public boolean insertSymbol(int length, int symbol) {
		throw new RuntimeException("Can't insert a symbol for the leaf");
	}

	@Override
	public HuffmanNode left() {
		throw new RuntimeException("Can't get left child of a leaf");
	}

	@Override
	public HuffmanNode right() {
		throw new RuntimeException("Can't get right child of a leaf");
	}
}