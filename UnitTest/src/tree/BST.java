package tree;

/**
 * ����������
 * 
 * @author Administrator
 * 
 * @param <T>
 */
@SuppressWarnings("rawtypes")
public class BST<T extends Comparable> extends BinTree<T> {
	protected BinNode<T> _hot;// �����С��ڵ�ĸ���

	public BinNode<T> search(T e) {
		return searchIn(this.getRoot(), e, _hot = null);
	}

	protected BinNode<T> searchIn(BinNode<T> node, T e, BinNode<T> _hot) {
		if (node == null || node.getData() == e)
			return node;
		this._hot = node; // ��¼��ǰ�ڵ�
		if (node.getData().compareTo(e) == 1) {
			node = node.getLeft();
		} else if (node.getData().compareTo(e) == -1) {
			node = node.getRight();
		}
		return searchIn(node, e, _hot);
	}

	public BinNode<T> insert(T e) {
		// ����ǿ���
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

	// ����ڵ��븸�ڵ�Ĺ�ϵ
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

	// ɾ���ڵ�
	protected BinNode<T> removeAt(BinNode<T> node) {
		BinNode<T> succ = null;
		if (node.getLeft() == null) {
			dealNodeRelationForRemove(node, node.getRight());
			succ = node = node.getRight();
		} else if (node.getRight() == null) {
			dealNodeRelationForRemove(node, node.getLeft());
			succ = node = node.getLeft();
		} else { // ������������
			succ = succ(node.getRight());// ��ȡ���Ԫ��
			swipNodeValue(node, succ);// ��������
			_hot = succ.getParent();// ��_hot Ϊ���Ԫ�صĸ���
			// �Ƴ����Ԫ��
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
