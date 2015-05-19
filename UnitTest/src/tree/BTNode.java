package tree;

import java.util.ArrayList;
import java.util.List;

public class BTNode<T> {
	private BTNode<T> parent;
	private List<T> key; // 数值向量
	private List<BTNode<T>> child; // 孩子向量（其长度比key多一）

	public BTNode() {
		this.parent = null;
		key = new ArrayList<T>();
		child = new ArrayList<BTNode<T>>();
		//child.add(null);
	}

	public BTNode<T> getParent() {
		return parent;
	}

	public void setParent(BTNode<T> parent) {
		this.parent = parent;
	}

	public List<T> getKey() {
		return key;
	}

	public void setKey(List<T> key) {
		this.key = key;
	}

	public List<BTNode<T>> getChild() {
		return child;
	}

	public void setChild(List<BTNode<T>> child) {
		this.child = child;
	}
}
