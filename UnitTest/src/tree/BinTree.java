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

	// 节点插入
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

	// 删除节点
	public void remove(BinNode<T> node) {
		BinNode<T> parent = node.getParent();
		this.size--;
		if (parent == null) {
			node = null;
			return;
		}
		removeForParent(node);
		updateHeightAbove(parent); // 跟新祖先高度
	}

	// 更新node以及历代祖先的高度 可优化
	public void updateHeightAbove(BinNode<T> node) {
		while (node != null) {
			updateHeight(node);
			node = node.getParent();
		}
	}

	// 更新节点高度
	public int updateHeight(BinNode<T> node) {
		node.setHeight(1 + max(stature(node.getLeft()),
				stature(node.getRight())));
		return node.getHeight();
	}

	public boolean empty() {
		return (this.size == 0) ? true : false;
	}

	// 后续遍历
	public void travPost(BinNode<T> node) {
		if (node == null)
			return;
		travPost(node.getLeft());
		travPost(node.getRight());
		System.out.print(node.getData() + " ");
	}

	// 后续遍历迭代
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

	// 中序遍历
	public void intrav(BinNode<T> node) {
		if (node == null)
			return;
		intrav(node.getLeft());
		System.out.print(node.getData() + " ");
		intrav(node.getRight());
	}

	// 中序遍历迭代
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

	// 先序遍历 递归方式
	public void travPre(BinNode<T> node) {
		if (node == null)
			return;
		System.out.print(node.getData() + " ");
		travPre(node.getLeft());
		travPre(node.getRight());
	}

	// 先序遍历迭代方式1
	public void travPre2(BinNode<T> node) {
		Stack<BinNode> stack = new Stack<BinNode>();
		// 根节点入栈
		stack.push(node);
		while (!stack.isEmpty()) {
			node = stack.pop();
			System.out.print(node.getData() + " ");
			// 先右孩子入栈
			if (node.getRight() != null)
				stack.push(node.getRight());
			// 左孩子入栈
			if (node.getLeft() != null)
				stack.push(node.getLeft());
		}
	}

	// 先序遍历迭代方式2
	public void travPre3(BinNode<T> node) {
		Stack<BinNode> stack = new Stack<BinNode>();
		while (true) {
			visitAlongLeftBranch(node, stack);
			if (stack.isEmpty())
				break;
			node = stack.pop();
		}
	}

	// 层次遍历
	public void travLevel(BinNode<T> node) {
		Queue<BinNode<T>> queue = new ArrayBlockingQueue<BinNode<T>>(100);
		queue.add(node);
		int height = node.getHeight();
		while (!queue.isEmpty()) {
			node = queue.poll();
			System.out.print(node.getData() + " 颜色 : " + node.getColor().toString());
			System.out.print(" 左孩子 : "
					+ ((node.getLeft() == null) ? "无" : node.getLeft()
							.getData()));
			System.out.print(" 右孩子 : "
					+ ((node.getRight() == null) ? "无" : node.getRight()
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
		// 自顶向下反复检查栈顶节点
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

	// 来自父亲的引用
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

	// 返回节点的高度
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
