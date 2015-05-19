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

	// 处理上溢
	protected void sloveOverflow(BTNode<T> v) {
		if (this.order >= v.getChild().size())
			return;// 当前节点并未上溢
		int s = order / 2;
		BTNode<T> u = new BTNode<T>(); // 创建新的节点，此时已经有一个孩子
		// 复制t 右侧的关键码和孩子到u
		for (int j = 0; j < order - s - 1; j++) {
			u.getKey().add(j, v.getKey().remove(s + 1));
			u.getChild().add(j, v.getChild().remove(s + 1));
		}
		u.getChild().add(order - s - 1, v.getChild().remove(s + 1));
		if (u.getChild().get(0) != null) {
			// 若u的孩子不为空令它们的父节点统一指向u
			for (int i = 0; i < u.getChild().size(); i++)
				u.getChild().get(i).setParent(u);
		}
		BTNode<T> p = v.getParent();
		if (p == null) {
			// 创建新的根节点
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

	// 处理下溢
	protected void sloveUnderflow(BTNode<T> v) {
		// 递归基：当前节点并未下溢
		if ((order + 1) / 2 <= v.getChild().size())
			return;
		BTNode<T> p = v.getParent();
		if (p == null) {// 递归基：已到根节点，没有孩子的下限
			if (v.getKey().size() == 0 && v.getChild().get(0) != null) {
				// 但倘若作为树根的v已不含关键码，却有（唯一的）非空孩子，则
				this.root = v.getChild().get(0);
				this.root.setParent(null);
			}// 整树高度降低一层
			return;
		}
		// 确定v是p的第r个孩子——此时v可能不含关键码，故不能通过关键码查找
		int r = 0;
		while (p.getChild().get(r) != v)
			r++;
		// 情况1：向左兄弟借关键码
		if (r > 0) {
			BTNode<T> ls = p.getChild().get(r - 1);// 左兄弟必存在
			if (ls.getChild().size() > (order + 1) / 2) {// 若该兄弟足够“胖”，则
				v.getKey().add(0, p.getKey().get(r - 1));//p借出一个关键码给v（作为最小关键码）
				p.getKey().set(r - 1,ls.getKey().remove(ls.getKey().size() - 1)); //ls的最大关键码转入p
				v.getChild().add(0,ls.getChild().remove(ls.getChild().size() - 1));//同时ls的最右侧孩子过继给v
				if (v.getChild().get(0) != null)
					v.getChild().get(0).setParent(v);
				return;// 下溢完成 旋转不需要递归
			}
		}// 至此，左兄弟要么为空，要么太“瘦”
		// 情况2：向右兄弟借关键码
		if (r < p.getChild().size() - 1) {
			BTNode<T> rs = p.getChild().get(r + 1);// 右兄弟必存在
			if (rs.getChild().size() > (order + 1) / 2) {// 若该兄弟足够“胖”，则
				v.getKey().add(p.getKey().get(r));
				p.getKey().set(r, rs.getKey().remove(0));
				v.getChild().add(rs.getChild().remove(0));
				if (v.getChild().get(v.getChild().size() - 1) != null)
					v.getChild().get(v.getChild().size() - 1).setParent(v);
				return; //至此，通过左旋已完成当前层（以及所有层）的下溢处理
			}
		}//至此，右兄弟要么为空，要么太“瘦”
		// 情况3：左、右兄弟要么为空（但不可能同时），要么都太“瘦”——合并
		if (r > 0) {// 与左兄弟合并
			BTNode<T> ls = p.getChild().get(r - 1);//左兄弟必存在
			//p的第r - 1个关键码转入ls，v不再是p的第r个孩子
			ls.getKey().add(p.getKey().remove(r - 1));
			p.getChild().remove(r);
			ls.getChild().add(v.getChild().remove(0));
			//v的最左侧孩子过继给ls做最右侧孩子
			if (ls.getChild().get(ls.getChild().size() - 1) != null)
				ls.getChild().get(ls.getChild().size() - 1).setParent(ls);
			//v剩余的关键码和孩子，依次转入ls
			while(!v.getKey().isEmpty()){
				ls.getKey().add(v.getKey().remove(0));
				ls.getChild().add(v.getChild().remove(0));
				if (ls.getChild().get(ls.getChild().size() - 1) != null)
					ls.getChild().get(ls.getChild().size() - 1).setParent(ls);
			}
			
		} else {// 与右兄弟合并
			BTNode<T> rs = p.getChild().get(r + 1);// 右兄弟必然存在
			rs.getKey().add(0, p.getKey().remove(r));
			p.getChild().remove(r);
			// p的第r个关键码转入rs，v不再是p的第r个孩子
			rs.getChild().add(0, v.getChild().remove(v.getChild().size() - 1));
			if (rs.getChild().get(0) != null)
				rs.getChild().get(0).setParent(rs);
			//v剩余的关键码和孩子，依次转入rs
			while(!v.getKey().isEmpty()){
				rs.getKey().add(0, v.getKey().remove(v.getKey().size() - 1));
				rs.getChild().add(0,v.getChild().remove(v.getChild().size() - 1));
				if (rs.getChild().get(0) != null)
					rs.getChild().get(0).setParent(rs);
			}
		}
		sloveUnderflow(p);// 上升一层，如有必要则继续分裂——至多递归O(logn)层
	}

	public BTNode<T> search(T e) {
		BTNode<T> v = this.root;// 从根节点出发
		this._hot = null;
		while (v != null) {// 逐层查找
			int r = searchElem(v.getKey(), e);// 在当前节点中，找到不大于e的最大关键码
			if (r >= 0 && v.getKey().get(r) == e)// 成功：在当前节点中命中目标关键码
				return v;
			else {
				_hot = v; // 否则，转入对应子树（_hot指向其父）——需做I/O，最费时间
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
		if (_hot == null) {// 空树的话置根节点
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
		// x 不是叶子节点
		if (v.getChild().get(0) != null) {
			BTNode<T> u = v.getChild().get(r + 1);
			while (u.getChild().get(0) != null) {
				u = u.getChild().get(0);
			}
			// 找到v的后继节点u 然后交换位置
			v.getKey().set(r, u.getKey().get(0));
			v = u;
			r = 0;
		}
		// 此时v是叶子节点 删除秩为r的元素
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
				System.out.print(" 父节点 ："
						+ ((node.getParent() == null) ? "无" : Arrays
								.toString(node.getParent().getKey()
										.toArray())));
				System.out.print("元素 ： "
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
