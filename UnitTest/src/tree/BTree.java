package tree;

import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

@SuppressWarnings("rawtypes")
public class BTree<T extends Comparable> {
	protected int size;
	protected int order;
	protected BTNode<T> root;
	protected BTNode<T> _hot;

	public BTree(int order) {
		this.size = 0;
		this.order = order;
		this.root = null;
		this._hot = null;
	}

	// ��������
	protected void sloveOverflow(BTNode<T> v) {
		if (this.order >= v.getChild().size())
			return;// ��ǰ�ڵ㲢δ����
		int s = order / 2;
		BTNode<T> u = new BTNode<T>(); // �����µĽڵ㣬��ʱ�Ѿ���һ������
		// ����t �Ҳ�Ĺؼ���ͺ��ӵ�u
		for (int j = 0; j < order - s - 1; j++) {
			u.getKey().add(j, v.getKey().remove(s + 1));
			u.getChild().add(j, v.getChild().remove(s + 1));
		}
		u.getChild().add(order - s - 1, v.getChild().remove(s + 1));
		if (u.getChild().get(0) != null) {
			// ��u�ĺ��Ӳ�Ϊ�������ǵĸ��ڵ�ͳһָ��u
			for (int i = 0; i < u.getChild().size(); i++)
				u.getChild().get(i).setParent(u);
		}
		BTNode<T> p = v.getParent();
		if (p == null) {
			// �����µĸ��ڵ�
			this.root = p = new BTNode<T>();
			u.setParent(p);
			v.setParent(p);
			p.getKey().add(v.getKey().remove(s));
			p.getChild().add(0, v);
			p.getChild().add(1, u);
			return;
		} else {
			int r = 1 + searchElem(p.getKey(), v.getKey().get(s));
			p.getKey().add(r, v.getKey().remove(s));
			p.getChild().add(r + 1, u);
			u.setParent(p);
			sloveOverflow(p);
		}
	}

	// ��������
	protected void sloveUnderflow(BTNode<T> v) {
		// �ݹ������ǰ�ڵ㲢δ����
		if ((order + 1) / 2 <= v.getChild().size())
			return;
		BTNode<T> p = v.getParent();
		if (p == null) {// �ݹ�����ѵ����ڵ㣬û�к��ӵ�����
			if (v.getKey().size() == 0 && v.getChild().get(0) != null) {
				// ��������Ϊ������v�Ѳ����ؼ��룬ȴ�У�Ψһ�ģ��ǿպ��ӣ���
				this.root = v.getChild().get(0);
				this.root.setParent(null);
			}// �����߶Ƚ���һ��
			return;
		}
		// ȷ��v��p�ĵ�r�����ӡ�����ʱv���ܲ����ؼ��룬�ʲ���ͨ���ؼ������
		int r = 0;
		while (p.getChild().get(r) != v)
			r++;
		// ���1�������ֵܽ�ؼ���
		if (r > 0) {
			BTNode<T> ls = p.getChild().get(r - 1);// ���ֵܱش���
			if (ls.getChild().size() > (order + 1) / 2) {// �����ֵ��㹻���֡�����
				v.getKey().add(0, p.getKey().get(r - 1));//p���һ���ؼ����v����Ϊ��С�ؼ��룩
				p.getKey().set(r - 1,ls.getKey().remove(ls.getKey().size() - 1)); //ls�����ؼ���ת��p
				v.getChild().add(0,ls.getChild().remove(ls.getChild().size() - 1));//ͬʱls�����Ҳຢ�ӹ��̸�v
				if (v.getChild().get(0) != null)
					v.getChild().get(0).setParent(v);
				return;// ������� ��ת����Ҫ�ݹ�
			}
		}// ���ˣ����ֵ�ҪôΪ�գ�Ҫô̫���ݡ�
		// ���2�������ֵܽ�ؼ���
		if (r < p.getChild().size() - 1) {
			BTNode<T> rs = p.getChild().get(r + 1);// ���ֵܱش���
			if (rs.getChild().size() > (order + 1) / 2) {// �����ֵ��㹻���֡�����
				v.getKey().add(p.getKey().get(r));
				p.getKey().set(r, rs.getKey().remove(0));
				v.getChild().add(rs.getChild().remove(0));
				if (v.getChild().get(v.getChild().size() - 1) != null)
					v.getChild().get(v.getChild().size() - 1).setParent(v);
				return; //���ˣ�ͨ����������ɵ�ǰ�㣨�Լ����в㣩�����紦��
			}
		}//���ˣ����ֵ�ҪôΪ�գ�Ҫô̫���ݡ�
		// ���3�������ֵ�ҪôΪ�գ���������ͬʱ����Ҫô��̫���ݡ������ϲ�
		if (r > 0) {// �����ֵܺϲ�
			BTNode<T> ls = p.getChild().get(r - 1);//���ֵܱش���
			//p�ĵ�r - 1���ؼ���ת��ls��v������p�ĵ�r������
			ls.getKey().add(p.getKey().remove(r - 1));
			p.getChild().remove(r);
			ls.getChild().add(v.getChild().remove(0));
			//v������ຢ�ӹ��̸�ls�����Ҳຢ��
			if (ls.getChild().get(ls.getChild().size() - 1) != null)
				ls.getChild().get(ls.getChild().size() - 1).setParent(ls);
			//vʣ��Ĺؼ���ͺ��ӣ�����ת��ls
			while(!v.getKey().isEmpty()){
				ls.getKey().add(v.getKey().remove(0));
				ls.getChild().add(v.getChild().remove(0));
				if (ls.getChild().get(ls.getChild().size() - 1) != null)
					ls.getChild().get(ls.getChild().size() - 1).setParent(ls);
			}
			
		} else {// �����ֵܺϲ�
			BTNode<T> rs = p.getChild().get(r + 1);// ���ֵܱ�Ȼ����
			rs.getKey().add(0, p.getKey().remove(r));
			p.getChild().remove(r);
			// p�ĵ�r���ؼ���ת��rs��v������p�ĵ�r������
			rs.getChild().add(0, v.getChild().remove(v.getChild().size() - 1));
			if (rs.getChild().get(0) != null)
				rs.getChild().get(0).setParent(rs);
			//vʣ��Ĺؼ���ͺ��ӣ�����ת��rs
			while(!v.getKey().isEmpty()){
				rs.getKey().add(0, v.getKey().remove(v.getKey().size() - 1));
				rs.getChild().add(0,v.getChild().remove(v.getChild().size() - 1));
				if (rs.getChild().get(0) != null)
					rs.getChild().get(0).setParent(rs);
			}
		}
		sloveUnderflow(p);// ����һ�㣬���б�Ҫ��������ѡ�������ݹ�O(logn)��
	}

	public BTNode<T> search(T e) {
		BTNode<T> v = this.root;// �Ӹ��ڵ����
		this._hot = null;
		while (v != null) {// ������
			int r = searchElem(v.getKey(), e);// �ڵ�ǰ�ڵ��У��ҵ�������e�����ؼ���
			if (r >= 0 && v.getKey().get(r) == e)// �ɹ����ڵ�ǰ�ڵ�������Ŀ��ؼ���
				return v;
			else {
				_hot = v; // ����ת���Ӧ������_hotָ���丸����������I/O�����ʱ��
				v = v.getChild().get(r + 1);
			}
		}
		return null;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	public boolean insert(T e) {
		BTNode<T> x = search(e);
		if (x != null)
			return false;
		if (_hot == null) {// �����Ļ��ø��ڵ�
			this.root = new BTNode();
			this.root.getKey().add(e);
			this.root.getChild().add(null);
			this.root.getChild().add(null);
			this.size++;
			return true;
		}
		int r = searchElem(_hot.getKey(), e);
		_hot.getKey().add(r + 1, e);
		_hot.getChild().add(r + 2, null);
		this.size++;
		sloveOverflow(_hot);
		return true;
	}

	public boolean remove(T e) {
		BTNode<T> v = search(e);
		if (v == null)
			return false;
		int r = searchElem(v.getKey(), e);
		// x ����Ҷ�ӽڵ�
		if (v.getChild().get(0) != null) {
			BTNode<T> u = v.getChild().get(r + 1);
			while (u.getChild().get(0) != null) {
				u = u.getChild().get(0);
			}
			// �ҵ�v�ĺ�̽ڵ�u Ȼ�󽻻�λ��
			v.getKey().set(r, u.getKey().get(0));
			v = u;
			r = 0;
		}
		// ��ʱv��Ҷ�ӽڵ� ɾ����Ϊr��Ԫ��
		v.getKey().remove(r);
		v.getChild().remove(r + 1);
		this.size--;
		sloveUnderflow(v);
		return true;
	}

	@SuppressWarnings("unchecked")
	private int searchElem(List<T> list, T e) {
		int r = -1, i;
		if (list.get(0).compareTo(e) > 0)
			return r;
		for (i = 0; i < list.size() && list.get(i).compareTo(e) <= 0; i++) {
			if (list.get(i).compareTo(e) == 0)
				return i;
		}
		if (i <= list.size() && i > -1)
			return i - 1;
		return r;
	}

	public void printBtree() {
		Queue<BTNode<T>> queue = new ArrayBlockingQueue<BTNode<T>>(100);
		queue.add(this.root);
		while (!queue.isEmpty()) {
			BTNode<T> node = queue.poll();
			if (node != null) {
				System.out.print(" ���ڵ� ��"
						+ ((node.getParent() == null) ? "��" : Arrays
								.toString(node.getParent().getKey()
										.toArray())));
				System.out.print("Ԫ�� �� "
						+ Arrays.toString(node.getKey().toArray()));
				System.out.println();
				for (int i = 0; i < node.getChild().size(); i++) {
					if (node.getChild().get(i) != null)
						queue.add(node.getChild().get(i));
				}
			}
		}
	}
}
