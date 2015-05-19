package graph;

// ±ß
public class Edge<T> {
	private T data;
	private int weight;
	private EType type;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public EType getType() {
		return type;
	}

	public void setType(EType type) {
		this.type = type;
	}
}
