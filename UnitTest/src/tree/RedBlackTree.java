package tree;

@SuppressWarnings("rawtypes")
public class RedBlackTree<T extends Comparable> extends AVL<T> {

	@Override
	public BinNode<T> insert(T e) {
		BinNode<T> x = search(e);
		if (x != null)
			return x;
		if (this.size == 0) {
			x = new BinNode<T>(e, -1, null, null, _hot, Color.BLACK);
			this.setRoot(x);
			this.size++;
			return x;
		}
		this.size++;
		x = new BinNode<T>(e, -1, null, null, _hot, Color.RED);
		dealNodeRelation(x);
		sloveDoubleRed(x);
		return (x != null) ? x : _hot.getParent();
	}

	@Override
	public boolean remove(T e) {
		BinNode<T> x = search(e);
		if (x == null)
			return false;
		BinNode<T> r = removeAt(x);
		if (--this.size == 0)
			return true; // 删除后为空树，直接返回
		// 若刚被删除的是根节点，则将其置黑，并更新黑高度
		if (_hot == null) {
			this.getRoot().setColor(Color.BLACK);
			updateHeight(this.getRoot());
			return true;
		}
		// 若所有祖先的黑深度依然平衡，则无需调整
		if (blackHeightUpdated(_hot))
			return true;
		if (isRed(r)) {
			r.setColor(Color.BLACK);
			r.height++;
			return true;
		}
		solveDoubleBlack(r); //经双黑调整后返回
		return true;
	}

	/*RedBlack高度更新条件*/ 
	protected boolean blackHeightUpdated(BinNode<T> node) {
		return (stature(node.getLeft()) == stature(node.getRight()) &&
				node.getHeight() == (isRed(node) ? node.getLeft().getHeight()
				: stature(node.getLeft()) + 1));
	}
	
	@Override
	public int updateHeight(BinNode<T> node) {// 计算黑高度
		node.setHeight(max(stature(node.getLeft()), stature(node.getRight())));
		if (isBlack(node))
			node.setHeight(node.getHeight() + 1);
		return node.getHeight();
	}

	/*******************************************************
	 * RedBlack双红调整算法：解决节点x与其父均为红色的问题。分为两大类情况：
	 * RR-1 : 2次颜色翻转，2次黑高度更新，1~2次旋转，不再递归
	 * RR-2 : 3次颜色翻转，3次黑高度更新，0次旋转，需要递归
	 *******************************************************/
	protected void sloveDoubleRed(BinNode<T> x) {
		if (isRoot(x)) { // 如果已经递归到根节点 则将其转黑，整树黑高度也随之递增
			// RR2 才会导致递归
			this.getRoot().setColor(Color.BLACK);
			this.getRoot().setHeight(this.getRoot().getHeight() + 1);
			return;
		}
		BinNode<T> p = x.getParent();
		if (isBlack(p))
			return;// 若p为黑，则可终止调整。否则
		BinNode<T> g = p.getParent();
		BinNode<T> u = uncle(x);
		if(isBlack(u)){// 包含空节点
			if(isLChild(x) == isLChild(p)) // x 与 p 同侧 左左 或者 右右 
				p.setColor(Color.BLACK);//p由红转黑，x保持红
			else 
				x.setColor(Color.BLACK); //x由红转黑，p保持红
			g.setColor(Color.RED);//g必定由黑转红
			BinNode<T> gg = g.getParent(); // 曾祖父（great-grand parent）
			if(isRoot(g))
				this.setRoot(rotateAt(x));
			else {
				if (isLChild(g))
					gg.setLeft(rotateAt(x));
				else
					gg.setRight(rotateAt(x));
			}
		} else { // 叔父节点为红节点
			if (!isRoot(g))
				g.setColor(Color.RED);
			p.setColor(Color.BLACK);
			p.setHeight(p.getHeight() + 1);
			u.setColor(Color.BLACK);
			u.setHeight(u.getHeight() + 1);
			sloveDoubleRed(g); //继续调整g（类似于尾递归，可优化为迭代形式）
		}
	}
	
	
	/******************************************************
	  * RedBlack双黑调整算法：解决节点x与被其替代的节点均为黑色的问题
	  * 分为三大类共四种情况：
	  *    BB-1  : 2次颜色翻转，2次黑高度更新，1~2次旋转，不再递归
	  *    BB-2R : 2次颜色翻转，2次黑高度更新，0次旋转，不再递归
	  *    BB-2B : 1次颜色翻转，1次黑高度更新，0次旋转，需要递归
	  *    BB-3  : 2次颜色翻转，2次黑高度更新，1次旋转，转为BB-1或BB2R
	  *****************************************************/
	private void solveDoubleBlack(BinNode<T> r) {
		BinNode<T> p = (r != null) ? r.getParent() : _hot;
		if (p == null)
			return;
		BinNode<T> s = (r == p.getLeft()) ? p.getRight() : p.getLeft();// r的兄弟
		if (isBlack(s)) { // 兄弟节点为黑
			BinNode<T> t = null;// s的红孩子（若左、右孩子皆红，左者优先；皆黑时为NULL）
			if (isRed(s.getRight()))
				t = s.getRight();
			if (isRed(s.getLeft()))
				t = s.getLeft();
			if (t != null) { // //黑s有红孩子：BB-1
				BinNode<T> g = p.getParent();
				Color oldColor = p.getColor();
				BinNode<T> b = null;
				if(isRoot(p)){
					b = rotateAt(t);
					this.setRoot(b);
				} else {					
					if (isLChild(p)){
						b = rotateAt(t);
						g.setLeft(b);
					}						
					else{
						b = rotateAt(t);
						g.setRight(b);	
					}										
				}
				if (b.getLeft() != null) {
					b.getLeft().setColor(Color.BLACK);
					updateHeight(b.getLeft());
				}
				if (b.getRight() != null) {
					b.getRight().setColor(Color.BLACK);
					updateHeight(b.getRight());
				}
				b.setColor(oldColor); //新子树根节点继承原根节点的颜色
			} else {// 黑s无红孩子
				s.setColor(Color.RED); // s转红
				s.height--;
				if (isRed(p)) {// BB-2R
					p.setColor(Color.BLACK); // p转黑，但黑高度不变
				} else {// BB-2B
					p.height--;// p保持黑，但黑高度下降
					solveDoubleBlack(p);
				}
			}
		} else { // 兄弟s为红：BB-3
			// s转黑，p转红
			s.setColor(Color.BLACK);
			p.setColor(Color.RED);
			BinNode<T> t = isLChild(s) ? s.getLeft() : s.getRight();
			_hot = p;
			//对t及其父亲、祖父做平衡调整
			BinNode<T> g = p.getParent();
			if (isRoot(p)) {
				this.setRoot(rotateAt(t));
			} else {
				if (isLChild(p))
					g.setLeft(rotateAt(t));
				else
					g.setRight(rotateAt(t));
			}
			solveDoubleBlack(r); // 继续修正r处双黑——此时的p已转红，故后续只能是BB-1或BB-2R
		}
	}
}
