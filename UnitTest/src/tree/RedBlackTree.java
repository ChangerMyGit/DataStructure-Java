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
			return true; // ɾ����Ϊ������ֱ�ӷ���
		// ���ձ�ɾ�����Ǹ��ڵ㣬�����úڣ������ºڸ߶�
		if (_hot == null) {
			this.getRoot().setColor(Color.BLACK);
			updateHeight(this.getRoot());
			return true;
		}
		// ���������ȵĺ������Ȼƽ�⣬���������
		if (blackHeightUpdated(_hot))
			return true;
		if (isRed(r)) {
			r.setColor(Color.BLACK);
			r.height++;
			return true;
		}
		solveDoubleBlack(r); //��˫�ڵ����󷵻�
		return true;
	}

	/*RedBlack�߶ȸ�������*/ 
	protected boolean blackHeightUpdated(BinNode<T> node) {
		return (stature(node.getLeft()) == stature(node.getRight()) &&
				node.getHeight() == (isRed(node) ? node.getLeft().getHeight()
				: stature(node.getLeft()) + 1));
	}
	
	@Override
	public int updateHeight(BinNode<T> node) {// ����ڸ߶�
		node.setHeight(max(stature(node.getLeft()), stature(node.getRight())));
		if (isBlack(node))
			node.setHeight(node.getHeight() + 1);
		return node.getHeight();
	}

	/*******************************************************
	 * RedBlack˫������㷨������ڵ�x���丸��Ϊ��ɫ�����⡣��Ϊ�����������
	 * RR-1 : 2����ɫ��ת��2�κڸ߶ȸ��£�1~2����ת�����ٵݹ�
	 * RR-2 : 3����ɫ��ת��3�κڸ߶ȸ��£�0����ת����Ҫ�ݹ�
	 *******************************************************/
	protected void sloveDoubleRed(BinNode<T> x) {
		if (isRoot(x)) { // ����Ѿ��ݹ鵽���ڵ� ����ת�ڣ������ڸ߶�Ҳ��֮����
			// RR2 �Żᵼ�µݹ�
			this.getRoot().setColor(Color.BLACK);
			this.getRoot().setHeight(this.getRoot().getHeight() + 1);
			return;
		}
		BinNode<T> p = x.getParent();
		if (isBlack(p))
			return;// ��pΪ�ڣ������ֹ����������
		BinNode<T> g = p.getParent();
		BinNode<T> u = uncle(x);
		if(isBlack(u)){// �����սڵ�
			if(isLChild(x) == isLChild(p)) // x �� p ͬ�� ���� ���� ���� 
				p.setColor(Color.BLACK);//p�ɺ�ת�ڣ�x���ֺ�
			else 
				x.setColor(Color.BLACK); //x�ɺ�ת�ڣ�p���ֺ�
			g.setColor(Color.RED);//g�ض��ɺ�ת��
			BinNode<T> gg = g.getParent(); // ���游��great-grand parent��
			if(isRoot(g))
				this.setRoot(rotateAt(x));
			else {
				if (isLChild(g))
					gg.setLeft(rotateAt(x));
				else
					gg.setRight(rotateAt(x));
			}
		} else { // �常�ڵ�Ϊ��ڵ�
			if (!isRoot(g))
				g.setColor(Color.RED);
			p.setColor(Color.BLACK);
			p.setHeight(p.getHeight() + 1);
			u.setColor(Color.BLACK);
			u.setHeight(u.getHeight() + 1);
			sloveDoubleRed(g); //��������g��������β�ݹ飬���Ż�Ϊ������ʽ��
		}
	}
	
	
	/******************************************************
	  * RedBlack˫�ڵ����㷨������ڵ�x�뱻������Ľڵ��Ϊ��ɫ������
	  * ��Ϊ�����๲���������
	  *    BB-1  : 2����ɫ��ת��2�κڸ߶ȸ��£�1~2����ת�����ٵݹ�
	  *    BB-2R : 2����ɫ��ת��2�κڸ߶ȸ��£�0����ת�����ٵݹ�
	  *    BB-2B : 1����ɫ��ת��1�κڸ߶ȸ��£�0����ת����Ҫ�ݹ�
	  *    BB-3  : 2����ɫ��ת��2�κڸ߶ȸ��£�1����ת��תΪBB-1��BB2R
	  *****************************************************/
	private void solveDoubleBlack(BinNode<T> r) {
		BinNode<T> p = (r != null) ? r.getParent() : _hot;
		if (p == null)
			return;
		BinNode<T> s = (r == p.getLeft()) ? p.getRight() : p.getLeft();// r���ֵ�
		if (isBlack(s)) { // �ֵܽڵ�Ϊ��
			BinNode<T> t = null;// s�ĺ캢�ӣ������Һ��ӽԺ죬�������ȣ��Ժ�ʱΪNULL��
			if (isRed(s.getRight()))
				t = s.getRight();
			if (isRed(s.getLeft()))
				t = s.getLeft();
			if (t != null) { // //��s�к캢�ӣ�BB-1
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
				b.setColor(oldColor); //���������ڵ�̳�ԭ���ڵ����ɫ
			} else {// ��s�޺캢��
				s.setColor(Color.RED); // sת��
				s.height--;
				if (isRed(p)) {// BB-2R
					p.setColor(Color.BLACK); // pת�ڣ����ڸ߶Ȳ���
				} else {// BB-2B
					p.height--;// p���ֺڣ����ڸ߶��½�
					solveDoubleBlack(p);
				}
			}
		} else { // �ֵ�sΪ�죺BB-3
			// sת�ڣ�pת��
			s.setColor(Color.BLACK);
			p.setColor(Color.RED);
			BinNode<T> t = isLChild(s) ? s.getLeft() : s.getRight();
			_hot = p;
			//��t���丸�ס��游��ƽ�����
			BinNode<T> g = p.getParent();
			if (isRoot(p)) {
				this.setRoot(rotateAt(t));
			} else {
				if (isLChild(p))
					g.setLeft(rotateAt(t));
				else
					g.setRight(rotateAt(t));
			}
			solveDoubleBlack(r); // ��������r��˫�ڡ�����ʱ��p��ת�죬�ʺ���ֻ����BB-1��BB-2R
		}
	}
}
