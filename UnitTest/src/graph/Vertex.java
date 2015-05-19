package graph;

// 顶点
public class Vertex<T> {
	private T data;
	private int inDegree;
	private int outDegree;
	private VStatus status;
	private int parent; // 在遍历树中的父节点
	private int priority; // 优先级数
	private int clock;// 计数器 用于计算顶点被遍历的次序

	public Vertex() {
	}

	public Vertex(T data) {
		this.status = VStatus.UNDISCOVERED;
		this.data = data;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public int getInDegree() {
		return inDegree;
	}

	public void setInDegree(int inDegree) {
		this.inDegree = inDegree;
	}

	public int getOutDegree() {
		return outDegree;
	}

	public void setOutDegree(int outDegree) {
		this.outDegree = outDegree;
	}

	public VStatus getStatus() {
		return status;
	}

	public void setStatus(VStatus status) {
		this.status = status;
	}

	public int getParent() {
		return parent;
	}

	public void setParent(int parent) {
		this.parent = parent;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getClock() {
		return clock;
	}

	public void setClock(int clock) {
		this.clock = clock;
	}

}
