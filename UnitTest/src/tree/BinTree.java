package tree;

import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ArrayBlockingQueue;

public class BinTree<T> {
	int size;
	private BinNode<T> root;

	public void insertAsRoot(T data) {
		this.root = new BinNode<T>(data, null);
		this.size++;
		updateHeight(root);
	}

	// �ڵ����
	public void insertAsLC(BinNode<T> node, T data) {
		BinNode<T> newNode = new BinNode<T>();
		newNode.setData(data);
		newNode.setParent(node);
		node.setLeft(newNode);
		updateHeightAbove(node);
		this.size++;
	}

	public void insertAsRC(BinNode<T> node, T data) {
		BinNode<T> newNode = new BinNode<T>();
		newNode.setData(data);
		newNode.setParent(node);
		node.setRight(newNode);
		updateHeightAbove(node);
		this.size++;
	}

	public void attenchAsLChild(BinNode<T> p, BinNode<T> lc) {
		p.setLeft(lc);
		if (lc != null)
			lc.setParent(p);
	}

	public void attenchAsRChild(BinNode<T> p, BinNode<T> rc) {
		p.setRight(rc);
		if (rc != null)
			rc.setParent(p);
	}

	// ɾ���ڵ�
	public void remove(BinNode<T> node) {
		BinNode<T> parent = node.getParent();
		this.size--;
		if (parent == null) {
			node = null;
			return;
		}
		removeForParent(node);
		updateHeightAbove(parent); // �������ȸ߶�
	}

	// ����node�Լ��������ȵĸ߶� ���Ż�
	public void updateHeightAbove(BinNode<T> node) {
		while (node != null) {
			updateHeight(node);
			node = node.getParent();
		}
	}

	// ���½ڵ�߶�
	public int updateHeight(BinNode<T> node) {
		node.setHeight(1 + max(stature(node.getLeft()),
				stature(node.getRight())));
		return node.getHeight();
	}

	public boolean empty() {
		return (this.size == 0) ? true : false;
	}

	// ��������
	public void travPost(BinNode<T> node) {
		if (node == null)
			return;
		travPost(node.getLeft());
		travPost(node.getRight());
		System.out.print(node.getData() + " ");
	}

	// ������������
	public void travPost2(BinNode<T> node) {
		Stack<BinNode<T>> stack = new Stack<BinNode<T>>();
		if (node != null)
			stack.push(node);
		while (!stack.isEmpty()) {
			if (stack.lastElement() != node.getParent())
				gotoHLVFL(stack);
			// System.out.println(stack.size());
			node = stack.pop();
			System.out.print(node.getData() + " ");
		}
	}

	// �������
	public void intrav(BinNode<T> node) {
		if (node == null)
			return;
		intrav(node.getLeft());
		System.out.print(node.getData() + " ");
		intrav(node.getRight());
	}

	// �����������
	public void intrav2(BinNode<T> node) {
		Stack<BinNode> stack = new Stack<BinNode>();
		while (true) {
			goAlongLeftBranch(node, stack);
			if (stack.isEmpty())
				break;
			node = stack.pop();
			System.out.print(node.getData() + " ");
			node = node.getRight();
		}
		System.out.println();
	}

	// ������� �ݹ鷽ʽ
	public void travPre(BinNode<T> node) {
		if (node == null)
			return;
		System.out.print(node.getData() + " ");
		travPre(node.getLeft());
		travPre(node.getRight());
	}

	// �������������ʽ1
	public void travPre2(BinNode<T> node) {
		Stack<BinNode> stack = new Stack<BinNode>();
		// ���ڵ���ջ
		stack.push(node);
		while (!stack.isEmpty()) {
			node = stack.pop();
			System.out.print(node.getData() + " ");
			// ���Һ�����ջ
			if (node.getRight() != null)
				stack.push(node.getRight());
			// ������ջ
			if (node.getLeft() != null)
				stack.push(node.getLeft());
		}
	}

	// �������������ʽ2
	public void travPre3(BinNode<T> node) {
		Stack<BinNode> stack = new Stack<BinNode>();
		while (true) {
			visitAlongLeftBranch(node, stack);
			if (stack.isEmpty())
				break;
			node = stack.pop();
		}
	}

	// ��α���
	public void travLevel(BinNode<T> node) {
		Queue<BinNode<T>> queue = new ArrayBlockingQueue<BinNode<T>>(100);
		queue.add(node);
		int height = node.getHeight();
		while (!queue.isEmpty()) {
			node = queue.poll();
			System.out.print(node.getData() + " ��ɫ : " + node.getColor().toString());
			System.out.print(" ���� : "
					+ ((node.getLeft() == null) ? "��" : node.getLeft()
							.getData()));
			System.out.print(" �Һ��� : "
					+ ((node.getRight() == null) ? "��" : node.getRight()
							.getData()));
			System.out.println("  ");
			if (node.getLeft() != null)
				queue.add(node.getLeft());
			if (node.getRight() != null)
				queue.add(node.getRight());
		}
		System.out.println();
	}

	private void goAlongLeftBranch(BinNode<T> node, Stack<BinNode> stack) {
		while (node != null) {
			stack.push(node);
			node = node.getLeft();
		}
	}

	private void visitAlongLeftBranch(BinNode<T> node, Stack<BinNode> stack) {
		while (node != null) {
			System.out.print(node.getData() + " ");
			// if (node.getRight() != null)
			stack.push(node.getRight());
			node = node.getLeft();
		}
	}

	public void gotoHLVFL(Stack<BinNode<T>> stack) {
		BinNode<T> node;
		// �Զ����·������ջ���ڵ�
		while ((node = stack.lastElement()) != null) {
			if (node.getLeft() != null) {
				if (node.getRight() != null)
					stack.push(node.getRight());
				stack.push(node.getLeft());
			} else {
				stack.push(node.getRight());
			}
		}
		if (stack.lastElement() == null)
			stack.pop();
		// System.out.println(stack.size());
	}

	protected void removeForParent(BinNode<T> node) {
		BinNode<T> parent = node.getParent();
		if (parent != null) {
			if (parent.getLeft() == node)
				parent.setLeft(null);
			else
				parent.setRight(null);
		}
	}

	// ���Ը��׵�����
	protected BinNode<T> fromParentTo(BinNode<T> node) {
		BinNode<T> parent = node.getParent();
		if (parent == null)
			return parent;
		return (parent.getLeft() == node) ? parent.getLeft() : parent
				.getRight();
	}

	protected boolean isRoot(BinNode<T> node) {
		return this.root == node;
	}

	protected boolean isLChild(BinNode<T> node) {
		return (!isRoot(node) && node == node.getParent().getLeft());
	}

	protected boolean isRChild(BinNode<T> node) {
		return (!isRoot(node) && node == node.getParent().getRight());
	}

	// ���ؽڵ�ĸ߶�
	protected int stature(BinNode<T> node) {
		if (node == null)
			return -1;
		return node.getHeight();
	}

	protected int max(int a, int b) {
		return (a > b) ? a : b;
	}

	protected boolean isBlack(BinNode<T> node) {
		return (node == null) || (node.getColor() == Color.BLACK);
	}

	protected boolean isRed(BinNode<T> node) {
		return node != null && (node.getColor() == Color.RED);
	}

	protected BinNode<T> uncle(BinNode<T> node) {
		BinNode<T> p = node.getParent();
		if (isLChild(p))
			return p.getParent().getRight();
		else
			return p.getParent().getLeft();
	}
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public BinNode<T> getRoot() {
		return root;
	}

	public void setRoot(BinNode<T> root) {
		this.root = root;
	}

}
