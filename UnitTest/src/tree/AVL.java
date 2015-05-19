package tree;

/**
 * AVL 平衡树
 * 
 * @author Administrator
 * 
 * @param <T>
 */
@SuppressWarnings("rawtypes")
public class AVL<T extends Comparable> extends BST<T> {

	@SuppressWarnings("unused")
	private boolean balanced(BinNode<T> node) { // 理想平衡
		return stature(node.getLeft()) == stature(node.getRight());
	}

	private int balFac(BinNode<T> node) { // 平衡因子
		return stature(node.getLeft()) - stature(node.getRight());
	}

	@SuppressWarnings("unused")
	private boolean avlBalanced(BinNode<T> node) { // 平衡条件
		return (balFac(node) < 2 && balFac(node) > -2);
	}

	// 在左、右孩子中取更高者
	private BinNode<T> tallerChild(BinNode<T> node) {
		if (stature(node.getLeft()) > stature(node.getRight()))
			return node.getLeft();
		else if (stature(node.getLeft()) < stature(node.getRight()))
			return node.getRight();
		else if (stature(node.getLeft()) == stature(node.getRight())) {
			// 等高：与父亲node同侧者
			return (isLChild(node) ? node.getLeft() : node.getRight());
		}
		return null;
	}

	// 重写二叉搜索树算法
	@Override
	public BinNode<T> insert(T e) {
		// 首先调用父类方法插入节点
		super.insert(e);
		// 此时，x的父亲_hot若增高，则其祖父有可能失衡
		for (BinNode<T> g = this.get_hot(); g != null; g = g.getParent()) {
			// 从x之父出发向上，逐层检查各代祖先g
			if (!avlBalanced(g)) {
				// 一旦发现g失衡，则（采用“3 + 4”算法）使之复衡，并将子树
				// 重新接入原树
				if (isRoot(g)) {
					this.setRoot(rotateAt(tallerChild(tallerChild(g))));
				} else {
					BinNode<T> parent = g.getParent();
					if (isLChild(g)) { // 如果是左孩子
						parent.setLeft(rotateAt(tallerChild(tallerChild(g))));
					} else { // 右孩子
						parent.setRight(rotateAt(tallerChild(tallerChild(g))));
					}
				}
				break;
			} else {// 否则（g依然平衡），只需简单地
				updateHeight(g); // 更新其高度（注意：即便g未失衡，高度亦可能增加）
			}
		}
		return null;
	}

	@Override
	public boolean remove(T e) {
		// 先按BST规则删除之（此后，原节点之父_hot及其祖先均可能失衡）
		boolean flag = super.remove(e);
		if (flag) {
			for (BinNode<T> g = this.get_hot(); g != null; g = g.getParent()) {
				// 一旦发现g失衡，则（采用“3 + 4”算法）使之复衡，并将该子树联至原父亲
				if (!avlBalanced(g)) {
					if (isRoot(g)) {
						g = rotateAt(tallerChild(tallerChild(g)));
						this.setRoot(g);
					} else {
						BinNode<T> parent = g.getParent();
						if (isLChild(g)) { // 如果是左孩子
							parent.setLeft(rotateAt(tallerChild(tallerChild(g))));
							g = parent.getLeft();
						} else { // 右孩子
							parent.setRight(rotateAt(tallerChild(tallerChild(g))));
							g.getRight();
						}
					}
				}
				updateHeight(g);// 并更新其高度（注意：即便g未失衡，高度亦可能降低）
			}
		}
		return flag;
	}

	/**
	 * 旋转操作
	 * 
	 * @param v
	 * @return
	 */
	protected BinNode<T> rotateAt(BinNode<T> v) {
		BinNode<T> p = v.getParent(); // 父亲
		BinNode<T> g = p.getParent(); // 祖父
		// 左左 左右
		if (isLChild(p)) {
			if (isLChild(v)) {// 单旋转
				// 向上关联
				p.setParent(g.getParent());
				return connect34(v, p, g, v.getLeft(), v.getRight(),
						p.getRight(), g.getRight());
			} else {// 双旋转
				// 向上关联
				v.setParent(g.getParent());
				return connect34(p, v, g, p.getLeft(), v.getLeft(),
						v.getRight(), g.getRight());
			}
		} else { // 右右 右左
			if (isRChild(v)) {
				// 向上关联
				p.setParent(g.getParent());
				return connect34(g, p, v, g.getLeft(), p.getLeft(),
						v.getLeft(), v.getRight());
			} else {
				v.setParent(g.getParent());
				return connect34(g, v, p, g.getLeft(), v.getLeft(),
						v.getRight(), p.getRight());
			}
		}
	}

	/******************************************************************************************
	 * 按照“3 + 4”结构联接3个节点及其四棵子树，返回重组之后的局部子树根节点位置（即b） 子树根节点与上层节点之间的双向联接，均须由上层调用者完成
	 * 可用于AVL和RedBlack的局部平衡调整
	 *****************************************************************************************/
	private BinNode<T> connect34(BinNode<T> a, BinNode<T> b, BinNode<T> c,
			BinNode<T> t0, BinNode<T> t1, BinNode<T> t2, BinNode<T> t3) {
		a.setLeft(t0);
		if (t0 != null)
			t0.setParent(a);
		a.setRight(t1);
		if (t1 != null)
			t1.setParent(a);
		updateHeight(a);// 只需局部更新高度 插入操作如果局部更新成功，总体高度成功
		c.setLeft(t2);
		if (t2 != null)
			t2.setParent(c);
		c.setRight(t3);
		if (t3 != null)
			t3.setParent(c);
		updateHeight(c);
		b.setLeft(a);
		b.setRight(c);
		a.setParent(b);
		c.setParent(b);
		updateHeight(b);
		return b;
	}
}
