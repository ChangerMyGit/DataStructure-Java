package tree;

/**
 * 二叉搜索树
 * 
 * @author Administrator
 * 
 * @param <T>
 */
@SuppressWarnings("rawtypes")
public class BST<T extends Comparable> extends BinTree<T> {
	protected BinNode<T> _hot;// “命中”节点的父亲

	public BinNode<T> search(T e) {
		return searchIn(this.getRoot(), e, _hot = null);
	}

	protected BinNode<T> searchIn(BinNode<T> node, T e, BinNode<T> _hot) {
		if (node == null || node.getData() == e)
			return node;
		this._hot = node; // 记录当前节点
		if (node.getData().compareTo(e) == 1) {
			node = node.getLeft();
		} else if (node.getData().compareTo(e) == -1) {
			node = node.getRight();
		}
		return searchIn(node, e, _hot);
	}

	public BinNode<T> insert(T e) {
		// 如果是空树
		if (this.size == 0) {
			this.insertAsRoot(e);
			return this.getRoot();
		}
		BinNode<T> node = search(e);
		if (node == null) {
			node = new BinNode<T>(e, this._hot);
			dealNodeRelation(node);
			this.size++;
			updateHeightAbove(node);
		}
		return node;
	}

	// 处理节点与父节点的关系
	protected void dealNodeRelation(BinNode<T> node) {
        if (_hot.getData().compareTo(node.getData()) == 1) {
			_hot.setLeft(node);
			node.setLeftChild(true);
		} else if (_hot.getData().compareTo(node.getData()) == -1) {
			_hot.setRight(node);
			node.setRightChild(true);
		}
	}

	private void dealNodeRelationForRemove(BinNode<T> node, BinNode<T> node2) {
		if (_hot == null) {
			this.setRoot((node.getLeft() != null) ? node.getLeft() : node
					.getRight());
		} else if (_hot.getData().compareTo(node.getData()) == 1) {
			_hot.setLeft(node2);
			if (node2 != null) {
				node2.setLeftChild(true);
				node2.setParent(_hot);
			}
		} else if (_hot.getData().compareTo(node.getData()) == -1) {
			_hot.setRight(node2);
			if (node2 != null) {
				node2.setRightChild(true);
				node2.setParent(_hot);
			}
		}
	}

	public boolean remove(T e) {
		BinNode<T> node = search(e);
		if (node == null)
			return false;
		removeAt(node);
		this.size--;
		updateHeightAbove(this._hot);
		return true;
	}

	// 删除节点
	protected BinNode<T> removeAt(BinNode<T> node) {
		BinNode<T> succ = null;
		if (node.getLeft() == null) {
			dealNodeRelationForRemove(node, node.getRight());
			succ = node = node.getRight();
		} else if (node.getRight() == null) {
			dealNodeRelationForRemove(node, node.getLeft());
			succ = node = node.getLeft();
		} else { // 存在两个孩子
			succ = succ(node.getRight());// 获取后继元素
			swipNodeValue(node, succ);// 交换数据
			_hot = succ.getParent();// 置_hot 为后继元素的父亲
			// 移除后继元素
			if (succ.isLeftChild()) {
				_hot.setLeft(succ.getRight());
			} else {
				_hot.setRight(succ.getRight());
			}
			if (succ.getRight() != null)
				succ.getRight().setParent(_hot);
			return node.getRight();
		}
		return succ;
	}

	private void swipNodeValue(BinNode<T> a, BinNode<T> b) {
		T temp = a.getData();
		a.setData(b.getData());
		b.setData(temp);
	}

	private BinNode<T> succ(BinNode<T> node) {
		BinNode<T> succ, result;
		succ = node;
		result = succ;
		while (succ != null) {
			result = succ;
			succ = succ.getLeft();
		}
		return result;
	}

	public BinNode<T> get_hot() {
		return _hot;
	}

	public void set_hot(BinNode<T> _hot) {
		this._hot = _hot;
	}

}
