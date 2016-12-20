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
		
		Graph graph = new Graph();
		graph.addNode(A, B, C, D, E, F, G);

		List<List<Node>> paths = graph.findPath(E, A);
        System.out.println(paths);
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
		return "Node [" + value + "]\n";
	}
}

class Graph {
	private List<Node> nodes = new ArrayList<>();
	
	void addNode(Node... n) {
		this.nodes.addAll(Arrays.asList(n));
	}

	List<List<Node>> findPath(Node s, Node d) {
		List<List<Node>> paths = new ArrayList<>();
		path(s, d, new HashSet<>(), new ArrayList<>(), paths);
		return paths;
	}
	
	private void path(Node s, Node d, HashSet<Node> visited, List<Node> currentPath, List<List<Node>> paths) {
		visited.add(s);
		currentPath.add(s);
		
		if(s.value == d.value) {
			paths.add(currentPath);
			return;
		}
		
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
		
		for(Node neighbour : proximity) {
			ArrayList<Node> newPath = new ArrayList<>(currentPath);
			path(neighbour, d, visited, newPath, paths);
		}
	}

	public String toString() {
		return nodes.toString();
	}
}
