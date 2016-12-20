package baynet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Test {
	public static void main(String[] args) {
		Node A = new Node('A');
		Node B = new Node('B');
		Node C = new Node('C');
		Node D = new Node('D');
		Node E = new Node('E');
		Node F = new Node('F');
		Node G = new Node('G');
		
		A.addChild(D);
		B.addChild(D);
		C.addChild(E);
		D.addChild(F);
		D.addChild(G);
		E.addChild(G);
		
		BayesianNetwork net = new BayesianNetwork();
		net.addNode(A, B, C, D, E, F, G);

        net.checkIndependence(A, E, G);
        net.checkIndependence(A, E, B);
    }
}

class Node {
	char value;
	List<Node> children;
	List<Node> parents;

	Node(char value) {
		this.value = value;
	}
	
    Node addChild(Node n) {
		if(children == null) {
			this.children = new ArrayList<>();
		}
		
		this.children.add(n);
		
		if(n.parents == null) {
			n.parents = new ArrayList<>();
		}
		n.parents.add(this);
		
		return n;
	}

	@Override
	public String toString() {
		return "Node [" + value + "]";
	}
}

class Graph {
	private List<Node> nodes = new ArrayList<>();
	
	void addNode(Node... n) {
		this.nodes.addAll(Arrays.asList(n));
	}

	/*
	* A path is a list of nodes so the result is a list of lists
	* */
	List<List<Node>> findUndirectedPaths(Node s, Node d) {
		List<List<Node>> paths = new ArrayList<>();
		path(s, d, new HashSet<>(), new ArrayList<>(), paths);
        System.out.println("Computed paths: " + paths);
        return paths;
	}

	/*
	* Keeps track of visited nodes and branches new path at each recursive call
	* */
	private void path(Node s, Node d, HashSet<Node> visited, List<Node> currentPath, List<List<Node>> paths) {
		visited.add(s);
		currentPath.add(s);
		
		if(s.value == d.value) {
			paths.add(currentPath);
			return;
		}

		/*
		* A list of nodes accessible from the current node, both parents and children
		* */
		List<Node> proximity = new ArrayList<>();
		
		if(s.children != null)
			for(Node child : s.children) {
				if(!visited.contains(child))
					proximity.add(child);
			}
		
		if(s.parents != null)
			for(Node parent : s.parents) {
				if(!visited.contains(parent)) 
					proximity.add(parent);
			}

        /*
        * Explore each possible path
        * */
		for(Node neighbour : proximity) {
			ArrayList<Node> newPath = new ArrayList<>(currentPath);
			path(neighbour, d, visited, newPath, paths);
		}
	}

	public String toString() {
		return nodes.toString();
	}
}

class BayesianNetwork extends Graph {
    /*
    * Checks is a is independent of b given observed using the principle od chained causality
    * */
    public void checkIndependence(Node a, Node b, Node observed) {
        /*
        * Compute paths from a to b
        * */
        List<List<Node>> pathsFromAtoB = findUndirectedPaths(a, b);

        /*
        * Find if there is a path that contains observed
        * */
        for(List<Node> path : pathsFromAtoB) {
            for (Node node : path) {
                if (node.value == observed.value) {
                    /*
                    * At this point, we know that observed is on the path, between a and b
                    * this means that there must be an edge that enters observed and an edge that exists observed
                    * */
                    System.out.println(a.value + " _|_ " + b.value + " | " + observed.value + " - true");
                    return;
                }
            }
        }

        /*
        * The criteria isn't matched
        * */
        System.out.println(a.value + " _|_ " + b.value + " | " + observed.value + " - false");
    }
}
