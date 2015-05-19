package tree;

public class BinNode<T>{
	private T data;
	protected int height = -1;
	private BinNode<T> left = null;
	private BinNode<T> right = null;;
	private BinNode<T> parent = null;
	private Color color;
	private boolean leftChild;
	private boolean rightChild;

	public BinNode() {
	}

	public BinNode(T data) {
		this.data = data;
	}

	public BinNode(T data, BinNode<T> parent) {
		this.data = data;
		this.parent = parent;
		// this.color = Color.RED;// Ä¬ÈÏÎªºì
	}

	public BinNode(T data, int height, BinNode<T> left, BinNode<T> right,
			BinNode<T> parent, Color color) {
		this.data = data;
		this.height = height;
		this.left = left;
		this.right = right;
		this.parent = parent;
		this.color = color;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public BinNode<T> getLeft() {
		return left;
	}

	public void setLeft(BinNode<T> left) {
		this.left = left;
	}

	public BinNode<T> getRight() {
		return right;
	}

	public void setRight(BinNode<T> right) {
		this.right = right;
	}

	public BinNode<T> getParent() {
		return parent;
	}

	public void setParent(BinNode<T> parent) {
		this.parent = parent;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public boolean isLeftChild() {
		return leftChild;
	}

	public void setLeftChild(boolean leftChild) {
		this.leftChild = leftChild;
	}

	public boolean isRightChild() {
		return rightChild;
	}

	public void setRightChild(boolean rightChild) {
		this.rightChild = rightChild;
	}

}
