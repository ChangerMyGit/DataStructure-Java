package graph;

public class GraphDemo {

	public static void main(String[] args) {
		Graph<String> graph = new Graph<String>();
		graph.addVertex("A");
		graph.addVertex("B");
		graph.addVertex("C");
		graph.addVertex("D");
		graph.addVertex("E");
		graph.addVertex("F");
		graph.addEdge(0, 1, 6, "AB");
		graph.addEdge(0, 2, 3, "AC");
		graph.addEdge(1, 2, 2, "BC");
		graph.addEdge(1, 3, 5, "BD");
		graph.addEdge(2, 1, 2, "CB");
		graph.addEdge(2, 3, 3, "CD");
		graph.addEdge(2, 4, 4, "CE");
		graph.addEdge(3, 2, 3, "DC");
		graph.addEdge(3, 4, 2, "DE");
		graph.addEdge(3, 5, 3, "DF");
		graph.addEdge(4, 5, 5, "EF");
		graph.DFS(0);
		System.out.println(graph.getVertexNum());
		System.out.println(graph.getEdgeNum());
	}
}
