package tree;

@SuppressWarnings("rawtypes")
public class SplayTree<T extends Comparable> extends BST<T> {

	public BinNode<T> insertSplay(T e) {
		BinNode<T> x = insert(e);
		splay(x);
		return x;
	}

	public boolean removeSplay(T e) {
		BinNode<T> x = searchSplay(e);
		super.remove(e);
		return true;
	}

	public BinNode<T> searchSplay(T e) {
		BinNode<T> x = this.searchIn(this.getRoot(), e, _hot = null);
		return splay((x == null) ? _hot : x);
	}

	private BinNode<T> splay(BinNode<T> v) {
		BinNode<T> p, g;
		while ((p = v.getParent()) != null && (g = p.getParent()) != null) {
			BinNode<T> gg = g.getParent();
			if (isLChild(p)) {
				if (isLChild(v)) {
					attenchAsLChild(g, p.getRight());
					attenchAsLChild(p, v.getRight());
					attenchAsRChild(p, g);
					attenchAsRChild(v, p);
				} else {
					attenchAsLChild(g, v.getRight());
					attenchAsRChild(p, v.getLeft());
					attenchAsLChild(v, p);
					attenchAsRChild(v, g);
				}
			} else {
				if (isRChild(v)) {
					attenchAsRChild(g, p.getLeft());
					attenchAsRChild(p, v.getLeft());
					attenchAsLChild(p, g);
					attenchAsLChild(v, p);
				} else {
					attenchAsRChild(g, v.getLeft());
					attenchAsLChild(p, v.getRight());
					attenchAsLChild(v, g);
					attenchAsRChild(v, p);
				}
			}
			if (gg == null) {
				v.setParent(null);
				this.setRoot(v);
			} else {
				v.setParent(gg);
				if (gg.getLeft() == g) {
					gg.setLeft(v);
				} else {
					gg.setRight(v);
				}
			}
			updateHeight(p);
			updateHeight(v);
			updateHeight(g);
		}
		if ((p = v.getParent()) != null) {
			if (isLChild(v)) {
				attenchAsLChild(p, v.getRight());
				attenchAsRChild(v, p);
			} else {
				attenchAsRChild(p, v.getLeft());
				attenchAsLChild(v, p);
			}
			v.setParent(null);
			this.setRoot(v);
			updateHeight(v);
		}
		return v;
	}
}
