package rsn170330.sp08;
// NOTE: Please refer updated version of this DFS.java in SP10.

import rbk.Graph;
import rbk.Graph.Vertex;
import rbk.Graph.Edge;
import rbk.Graph.GraphAlgorithm;
import rbk.Graph.Factory;

import java.io.File;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * CS 5V81.001: Implementation of Data Structures and Algorithms
 * Short Project SP08: Depth First Search.
 * Team SP43:
 * @author Rahul Nalawade (rsn170330)
 * @author Arunachalam Saravanan (axs170081)
 * 
 * Date: October 28, 2018
 */

/**
 * Team task: 
 * 
 * 1. Implement topologicalOrdering1() in the starter code. 
 * This is the DFS-based algorithm for finding the topological ordering 
 * of a directed acyclic graph. 
 * 
 * Individual tasks (optional): 
 * 
 * 2. Implement topologicalOrdering2(g) in the starter code. 
 * In this algorithm discussed in class, we keep removing nodes 
 * with no incoming edges. 
 * 
 * 3. Implement connectedComponents() in the starter code. 
 * In this algorithm, use DFS to find the number of connected components 
 * of a given undirected graph. Each node gets a cno. 
 * All nodes in the same connected component receive the same cno.
 * 
 */
public class DFS extends GraphAlgorithm<DFS.DFSVertex> {
	
	static int topNum;
	List<Vertex> finishList;
	int totalComponents;
	
	public static class DFSVertex implements Factory {
		int cno; // the component number of this node
		boolean seen; // flag to check if the node is seen or not
		Color vColor; // Not used, for reference
		int top; // the order number in which we visit this node
		Vertex parent; // parent of this node
		int in; // number of inDegrees of this node
		
		// default constructor
		public DFSVertex(Vertex u) {
			seen = false;
			vColor = Color.WHITE; // Not used, for reference
			parent = null;
			top = 0;
			in = 0;
			cno = 0;
		}

		public DFSVertex make(Vertex u) {
			return new DFSVertex(u);
		}
		
		// Not used, just for reference
		enum Color {
			WHITE, GRAY, BLACK;
		}
	} 
	
	// default constructor
	public DFS(Graph g) {

		super(g, new DFSVertex(null));
		topNum = g.size();
		finishList = new LinkedList<>();
		totalComponents = 0;
	}
	
	/**
	 * Run Depth-First-Search algorithm on Graph g using method 1
	 * @param g the graph as input
	 * @return DFS object
	 * @throws Exception When graph is NOT a DAG
	 */
	public static DFS depthFirstSearch(Graph g) throws Exception {
		DFS d = new DFS(g);
		d.dfs();
		
		return d;
	}
	
	/**
	 * Method to update finishList which stores the nodes visited 
	 * in DFS manner using method 1
	 * @param g the input graph
	 * @throws Exception When graph is NOT a DAG
	 */
	private void dfs() throws Exception {
		// No of Vertices in g
		topNum = g.size();
		
		for (Vertex u: g) {
			get(u).seen = false;
			get(u).vColor = DFSVertex.Color.WHITE; // Not used, for reference
			get(u).parent = null;
		}
		
		for (Vertex u: g) {
			//if (get(u).vColor == DFSVertex.Color.WHITE) {
			if (!get(u).seen) {
				dfsVisit(u, ++totalComponents);
			}
		}	
	}
	
	/**
	 * Method to update finishList which stores the nodes visited 
	 * in increasing order of inDegrees (using method 2) FROM CLASS NOTES
	 * @param g the input graph
	 * @throws Exception When graph is NOT a DAG
	 */
	private void inDegreeOrdering(Graph g) throws Exception {
		Queue<Vertex> zeroQ = new LinkedList<>();
		
		for (Vertex u: g) {
			get(u).in = u.inDegree();
			
			if (get(u).in == 0) {
				zeroQ.add(u);
			}
		}
		// No of nodes that have been processed
		int count = 0;
		
		while (!zeroQ.isEmpty()) {
			Vertex u = zeroQ.remove();
			get(u).top = ++count;
			finishList.add(u);
			
			for (Edge e: g.incident(u)) {
				Vertex v = e.otherEnd(u);
				get(v).in--; // virtually deleting the edge e
				
				if (get(v).in == 0) 
					zeroQ.add(v);
			}
		}
		
		if (count != g.size())
			throw new Exception("Graph is not a DAG.");
		
	}
	
	/**
	 * Helper method which recursively visits the nodes in DFS manner
	 * @param u giving a visit to node 'u'
	 * @throws Exception When graph is NOT a DAG
	 */
	private void dfsVisit(Vertex u, int componentNo) throws Exception {
		get(u).vColor = DFSVertex.Color.GRAY;
		get(u).seen = true;
		get(u).cno = componentNo;
		
		//System.out.print(" -> "+u+"("+get(u).top+")"); // uncomment to trace()*
		
		for (Edge e: g.incident(u)) {
			Vertex v = e.otherEnd(u);
			
			//if (get(v).vColor == DFSVertex.Color.WHITE) {
			if (!get(v).seen) {
				// Visited the node which is 'UnVisited' (or WHITE, v.top = 0)
				get(v).parent = u;
				dfsVisit(v, componentNo);
			}
			// When get(u).vColor == DFSVertex.Color.GRAY
			else if (get(u).top > get(v).top) {
				// Visited the node which is in 'Visiting' (or GRAY) status 
				throw new Exception("Graph is not a DAG, as Edge ("+u+", "+v+") is a back edge.");
			}
			
			// When get(u).vColor == DFSVertex.Color.BLACK
			else {
				// Visited the node which was 'Visited' (or BLACK, v.top > u.top)
				//System.out.println("\nVertex "+v+" is already visited."); // uncomment to trace()*
			}
		}
		// top: the number of visited node.
		// E.g. if u.top = |V| u is the first node which was done in DFS algorithm
		get(u).top = topNum--;
		
		finishList.add(0, u); // same as finishList.addFirst(u)
		
		get(u).vColor = DFSVertex.Color.BLACK;
		//System.out.print(" <- "+u+"("+get(u).top+")"); // uncomment to trace()*
	}
	
	// Member function to find topological order using method 1
	public List<Vertex> topologicalOrder1() {
		return this.finishList;
	}
	
	// Member function to find topological order using method 2
	public List<Vertex> topologicalOrder2() {
		return this.finishList;
	}

	/**
	 * Find the number of connected components of the graph g by running dfs. 
	 * Enter the component number of each vertex u in u.cno. 
	 * Note that the graph g is available as a class field via GraphAlgorithm.
	 * @return total number of components
	 */
	public int connectedComponents() {
		return totalComponents;
	}

	/**
	 * After running the connected components algorithm, the component no of each vertex can be queried.
	 * @param u the vertex
	 * @return component number of vertex u
	 */
	public int cno(Vertex u) {
		return get(u).cno;
	}

	/**
	 * Find topological oder of a DAG using DFS method 1.
	 * @param g the input graph
	 * @return the List of vertices in order of DFS
	 * @throws Exception When graph is NOT a DAG
	 */
	public static List<Vertex> topologicalOrder1(Graph g) throws Exception {
		DFS d = depthFirstSearch(g);
		// Ran DFS and then give the order
		return d.topologicalOrder1();
	}

	/**
	 * Find topological oder of a DAG using the second algorithm.
	 * @param g the input graph
	 * @return the List of vertices in order of DFS
	 * @throws Exception When graph is NOT a DAG
	 */
	public static List<Vertex> topologicalOrder2(Graph g) throws Exception {
		DFS d = new DFS(g);
		// Calls method 2 which gives topological ordering using inDegrees
		d.inDegreeOrdering(g);
		
		return d.topologicalOrder2();
	}

	public static void main(String[] args) throws Exception {
		//String string = "7 6   1 2 2   1 3 3   2 4 5   3 4 4   4 5 1   6 7 1 0";
		String string = "10 12   1 3 1   1 8 1   6 8 1   6 10 1   3 2 1   8 2 1   8 5 1   5 10 1   2 4 1   5 4 1   4 7 1   10 9 1 0";
		
		Scanner in;
		// If there is a command line argument, use it as file from which
		// input is read, otherwise use input from string.
		in = args.length > 0 ? new Scanner(new File(args[0])) : new Scanner(string);

		// Read graph from input
		Graph g = Graph.readDirectedGraph(in);
		
		
		// ----------------------- connected components (Task 3) ----------------------
		DFS d = DFS.depthFirstSearch(g);
		int numcc = d.connectedComponents();
		
		g.printGraph(false);

		System.out.println("Number of components: " + numcc + "\nu\tcno");
		for (Vertex u: g) {
			System.out.println(u + "\t" + d.cno(u));
		}
		
		// ----------------------- topological order 1 (Task 1) -----------------------
		List<Vertex> tOrder = DFS.topologicalOrder1(g);
		
		System.out.println("\nTopological Ordering 1: ");
		
		for (Vertex u: tOrder) {
			System.out.print(u + " ");
		}
		System.out.println();
		
		
		// ----------------------- topological order 2 (Task 2) -----------------------
		tOrder = DFS.topologicalOrder2(g);
		
		System.out.println("\nTopological Ordering 2: ");
		
		for (Vertex u: tOrder) {
			System.out.print(u + " ");
		}
		System.out.println();
		
	}
}
