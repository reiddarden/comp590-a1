package main;

public class InternalHuffmanNode implements HuffmanNode {
	private HuffmanNode _left;
	private HuffmanNode _right;

	public InternalHuffmanNode() {
		_left = null;
		_right = null;
	}

	public InternalHuffmanNode(HuffmanNode left, HuffmanNode right) {
		_left = left;
		_right = right;
	}

	@Override
	public int count() {
		return _left.count() + _right.count();
	}

	@Override
	public boolean isLeaf() {
		return false;
	}

	@Override
	public int symbol() {
		throw new RuntimeException("There is not a symbol on internal nodes");
	}

	@Override
	public int height() {
		return Math.max(_left.height() + 1, _right.height() + 1);
	}

	@Override
	public boolean isFull() {
		if (_left == null || _right == null) {
			return false;
		} else {
			return _left.isFull() && _right.isFull();
		}
	}

	@Override
	public boolean insertSymbol(int length, int symbol) {
		if (_left != null) {
			if (!_left.isFull()) {
				return _left.insertSymbol(length - 1, symbol);
			} else if (_right != null) {
				if (!_right.isFull()) {
					return _right.insertSymbol(length - 1, symbol);
				} else {
					return false;
				}
			} else {
				if (length == 1) {
					_right = new LeafHuffmanNode(symbol, 0);
					return true;
				} else {
					_right = new InternalHuffmanNode();
					return _right.insertSymbol(length - 1, symbol);
				}
			}
		} else {
			if (length == 1) {
				_left = new LeafHuffmanNode(symbol, 0);
				return true;
			} else {
				_left = new InternalHuffmanNode();
				return _left.insertSymbol(length - 1, symbol);
			}
		}
	}

	@Override
	public HuffmanNode left() {
		return _left;
	}

	@Override
	public HuffmanNode right() {
		return _right;
	}

}