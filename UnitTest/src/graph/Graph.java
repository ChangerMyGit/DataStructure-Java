package graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class Graph<T> {
	private List<Vertex<T>> vertexs;
	private List<List<Edge<T>>> edges;
	private int vertexNum; // 顶点数
	private int edgeNum; // 边的数量

	public Graph() {
		vertexs = new ArrayList<Vertex<T>>();
		edges = new ArrayList<List<Edge<T>>>();
		this.vertexNum = 0;
		this.edgeNum = 0;
	}

	// 删除顶点
	public void removeVertex(int i) {
		// 首先删除和该顶点相关的边
		for (int j = 0; j < vertexNum; j++) {
			if (exists(i, j))
				removeEdge(i, j);
		}
		for (int j = 0; j < vertexNum; j++) {
			this.edges.get(j).remove(i);
		}
		this.edges.remove(i);
		this.vertexs.remove(i);
		this.vertexNum--;
	}

	// 插入顶点
	public void addVertex(T data) {
		Vertex<T> vertex = new Vertex<T>(data);
		vertexs.add(vertex);
		// 扩充范围
		for (int i = 0; i < vertexNum; i++) {
			edges.get(i).add(null);
		}
		this.vertexNum++;
		List<Edge<T>> newEdge = new ArrayList<Edge<T>>();
		for (int i = 0; i < vertexNum; i++) {
			newEdge.add(null);
		}
		edges.add(newEdge);
	}

	// 删除边
	public void removeEdge(int i, int j) {
		if (!exists(i, j))
			return;
		edges.get(i).set(j, null);
		this.edgeNum--;
	}

	// 增加边
	public void addEdge(int i, int j, int weight, T data) {
		if (exists(i, j))
			return;
		Edge<T> edge = new Edge<T>();
		edge.setWeight(weight);
		edge.setData(data);
		edges.get(i).set(j, edge);
		this.edgeNum++;
	}

	public void addEdge(int i, int j, int weight) {
		addEdge(i, j, weight, null);
	}

	// 获取边
	public Edge getEdge(int i, int j) {
		return edges.get(i).get(j);
	}

	// 边是否存在
	public boolean exists(int i, int j) {
		return (i >= 0 && i < vertexNum && j < vertexNum && j >= 0 && edges
				.get(i).get(j) != null);
	}

	// 返回第一个顶点
	public int firstNbr(int i) {
		return nextNbr(i, vertexNum);
	}

	// 相对于顶点j的下一邻接顶点
	public int nextNbr(int i, int j) {
		while (j > -1 && !exists(i, --j))
			;// 逆向线性试探
		return j;
	}

	// 广度优先遍历
	public void BFS(int i) {
		Queue queue = new ArrayBlockingQueue(100);
		queue.add(i);// 顶点入队
		while (!queue.isEmpty()) {
			i = (int) queue.poll();
			for (int v = firstNbr(i); v > -1; v = nextNbr(i, v)) {
				if (getVertexs().get(v).getStatus() == VStatus.UNDISCOVERED) {
					getVertexs().get(v).setStatus(VStatus.DISCOVERED);
					queue.add(v);
				}
			}
			getVertexs().get(i).setStatus(VStatus.VISITED);
		}
	}

	// 深度优先遍历
	public void DFS(int i) {
		for (int v = firstNbr(i); v > -1; v = nextNbr(i, v)) {
			if (getVertexs().get(v).getStatus() == VStatus.UNDISCOVERED) {
				getVertexs().get(v).setStatus(VStatus.DISCOVERED);
				DFS(v);
			}
		}
		getVertexs().get(i).setStatus(VStatus.VISITED);
	}
	
	public List<Vertex<T>> getVertexs() {
		return vertexs;
	}

	public void setVertexs(List<Vertex<T>> vertexs) {
		this.vertexs = vertexs;
	}

	public List<List<Edge<T>>> getEdges() {
		return edges;
	}

	public void setEdges(List<List<Edge<T>>> edges) {
		this.edges = edges;
	}

	public int getVertexNum() {
		return vertexNum;
	}

	public void setVertexNum(int vertexNum) {
		this.vertexNum = vertexNum;
	}

	public int getEdgeNum() {
		return edgeNum;
	}

	public void setEdgeNum(int edgeNum) {
		this.edgeNum = edgeNum;
	}

}
