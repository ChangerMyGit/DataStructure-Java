package tree;

/**
 * AVL ƽ����
 * 
 * @author Administrator
 * 
 * @param <T>
 */
@SuppressWarnings("rawtypes")
public class AVL<T extends Comparable> extends BST<T> {

	@SuppressWarnings("unused")
	private boolean balanced(BinNode<T> node) { // ����ƽ��
		return stature(node.getLeft()) == stature(node.getRight());
	}

	private int balFac(BinNode<T> node) { // ƽ������
		return stature(node.getLeft()) - stature(node.getRight());
	}

	@SuppressWarnings("unused")
	private boolean avlBalanced(BinNode<T> node) { // ƽ������
		return (balFac(node) < 2 && balFac(node) > -2);
	}

	// �����Һ�����ȡ������
	private BinNode<T> tallerChild(BinNode<T> node) {
		if (stature(node.getLeft()) > stature(node.getRight()))
			return node.getLeft();
		else if (stature(node.getLeft()) < stature(node.getRight()))
			return node.getRight();
		else if (stature(node.getLeft()) == stature(node.getRight())) {
			// �ȸߣ��븸��nodeͬ����
			return (isLChild(node) ? node.getLeft() : node.getRight());
		}
		return null;
	}

	// ��д�����������㷨
	@Override
	public BinNode<T> insert(T e) {
		// ���ȵ��ø��෽������ڵ�
		super.insert(e);
		// ��ʱ��x�ĸ���_hot�����ߣ������游�п���ʧ��
		for (BinNode<T> g = this.get_hot(); g != null; g = g.getParent()) {
			// ��x֮���������ϣ�������������g
			if (!avlBalanced(g)) {
				// һ������gʧ�⣬�򣨲��á�3 + 4���㷨��ʹ֮���⣬��������
				// ���½���ԭ��
				if (isRoot(g)) {
					this.setRoot(rotateAt(tallerChild(tallerChild(g))));
				} else {
					BinNode<T> parent = g.getParent();
					if (isLChild(g)) { // ���������
						parent.setLeft(rotateAt(tallerChild(tallerChild(g))));
					} else { // �Һ���
						parent.setRight(rotateAt(tallerChild(tallerChild(g))));
					}
				}
				break;
			} else {// ����g��Ȼƽ�⣩��ֻ��򵥵�
				updateHeight(g); // ������߶ȣ�ע�⣺����gδʧ�⣬�߶���������ӣ�
			}
		}
		return null;
	}

	@Override
	public boolean remove(T e) {
		// �Ȱ�BST����ɾ��֮���˺�ԭ�ڵ�֮��_hot�������Ⱦ�����ʧ�⣩
		boolean flag = super.remove(e);
		if (flag) {
			for (BinNode<T> g = this.get_hot(); g != null; g = g.getParent()) {
				// һ������gʧ�⣬�򣨲��á�3 + 4���㷨��ʹ֮���⣬��������������ԭ����
				if (!avlBalanced(g)) {
					if (isRoot(g)) {
						g = rotateAt(tallerChild(tallerChild(g)));
						this.setRoot(g);
					} else {
						BinNode<T> parent = g.getParent();
						if (isLChild(g)) { // ���������
							parent.setLeft(rotateAt(tallerChild(tallerChild(g))));
							g = parent.getLeft();
						} else { // �Һ���
							parent.setRight(rotateAt(tallerChild(tallerChild(g))));
							g.getRight();
						}
					}
				}
				updateHeight(g);// ��������߶ȣ�ע�⣺����gδʧ�⣬�߶�����ܽ��ͣ�
			}
		}
		return flag;
	}

	/**
	 * ��ת����
	 * 
	 * @param v
	 * @return
	 */
	protected BinNode<T> rotateAt(BinNode<T> v) {
		BinNode<T> p = v.getParent(); // ����
		BinNode<T> g = p.getParent(); // �游
		// ���� ����
		if (isLChild(p)) {
			if (isLChild(v)) {// ����ת
				// ���Ϲ���
				p.setParent(g.getParent());
				return connect34(v, p, g, v.getLeft(), v.getRight(),
						p.getRight(), g.getRight());
			} else {// ˫��ת
				// ���Ϲ���
				v.setParent(g.getParent());
				return connect34(p, v, g, p.getLeft(), v.getLeft(),
						v.getRight(), g.getRight());
			}
		} else { // ���� ����
			if (isRChild(v)) {
				// ���Ϲ���
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
	 * ���ա�3 + 4���ṹ����3���ڵ㼰���Ŀ���������������֮��ľֲ��������ڵ�λ�ã���b�� �������ڵ����ϲ�ڵ�֮���˫�����ӣ��������ϲ���������
	 * ������AVL��RedBlack�ľֲ�ƽ�����
	 *****************************************************************************************/
	private BinNode<T> connect34(BinNode<T> a, BinNode<T> b, BinNode<T> c,
			BinNode<T> t0, BinNode<T> t1, BinNode<T> t2, BinNode<T> t3) {
		a.setLeft(t0);
		if (t0 != null)
			t0.setParent(a);
		a.setRight(t1);
		if (t1 != null)
			t1.setParent(a);
		updateHeight(a);// ֻ��ֲ����¸߶� �����������ֲ����³ɹ�������߶ȳɹ�
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
