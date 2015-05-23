package heap;

import java.util.ArrayList;

/**
 * ���ȶ���
 * 
 * @author Administrator
 * 
 * @param <T>
 */
@SuppressWarnings({ "rawtypes", "serial" })
public class PQ_ComplHeap<T extends Comparable> extends ArrayList<T> {

	// ������ǰn�������еĵ�i��ʵʩ���ˣ�i < n
	protected int percolateDown(int i) {
		int j = -1;
		while (i != (j = properParent(i))) {
			swap(i, j);
			i = j;
		}
		return i;
	}

	// ����
	protected int percolateUp(int i) {
		while (parentValid(i)) {
			if (get(i).compareTo(get(parent(i))) < 0)
				break;
			swap(i, parent(i));
			i = parent(i);
		}
		return i;
	}

	protected void heapify(int n) {
		for (Integer i = 1; i <= n; i++) {
			add((T) i);
		}
		for (int i = lastInternal(n); i >= 0; i--) {
			percolateDown(i);// ���˸��ڲ��ڵ�
		}
	}

	public void insert(T e) {
		super.add(e);
		percolateUp(size() - 1);
	}

	public T getMax() {
		return get(size() - 1);
	}

	public T delMax() {
		if (size() == 0)
			return null;
		T maxElem = get(0);
		set(0, get(size() - 1));
		remove(size() - 1);
		percolateDown(0);
		return maxElem;
	}

	private void swap(int i, int j) {
		T temp = get(i);
		set(i, get(j));
		set(j, temp);
	}

	private int parent(int i) {
		return (i - 1) >> 1;
	}

	private int lChild(int i) {
		return ((i << 1)) + 1;
	}

	private int rChild(int i) {
		return ((i + 1)) << 1;
	}

	private boolean parentValid(int i) {
		return i > 0;
	}

	/* ���ӣ����ࣩ�����еĴ��� */
	private int properParent(int i) {
		int max = i;
		int lc = lChild(i);
		int rc = rChild(i);
		if (lc > 0 && lc < size() && get(max).compareTo(get(lc)) < 0) {
			max = lc;
		}
		if (rc > 0 && rc < size() && get(max).compareTo(get(rc)) < 0) {
			max = rc;
		}
		return max;
	}

	// ���һ���ڲ��ڵ� ��ĩ�ڵ�ĸ���
	private int lastInternal(int n) {
		return parent(n - 1);
	}
}
