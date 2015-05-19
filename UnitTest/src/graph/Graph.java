package graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class Graph<T> {
	private List<Vertex<T>> vertexs;
	private List<List<Edge<T>>> edges;
	private int vertexNum; // ������
	private int edgeNum; // �ߵ�����

	public Graph() {
		vertexs = new ArrayList<Vertex<T>>();
		edges = new ArrayList<List<Edge<T>>>();
		this.vertexNum = 0;
		this.edgeNum = 0;
	}

	// ɾ������
	public void removeVertex(int i) {
		// ����ɾ���͸ö�����صı�
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

	// ���붥��
	public void addVertex(T data) {
		Vertex<T> vertex = new Vertex<T>(data);
		vertexs.add(vertex);
		// ���䷶Χ
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

	// ɾ����
	public void removeEdge(int i, int j) {
		if (!exists(i, j))
			return;
		edges.get(i).set(j, null);
		this.edgeNum--;
	}

	// ���ӱ�
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

	// ��ȡ��
	public Edge getEdge(int i, int j) {
		return edges.get(i).get(j);
	}

	// ���Ƿ����
	public boolean exists(int i, int j) {
		return (i >= 0 && i < vertexNum && j < vertexNum && j >= 0 && edges
				.get(i).get(j) != null);
	}

	// ���ص�һ������
	public int firstNbr(int i) {
		return nextNbr(i, vertexNum);
	}

	// ����ڶ���j����һ�ڽӶ���
	public int nextNbr(int i, int j) {
		while (j > -1 && !exists(i, --j))
			;// ����������̽
		return j;
	}

	// ������ȱ���
	public void BFS(int i) {
		Queue queue = new ArrayBlockingQueue(100);
		queue.add(i);// �������
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

	// ������ȱ���
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
