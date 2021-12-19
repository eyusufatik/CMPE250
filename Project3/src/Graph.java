import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.TreeSet;

public class Graph {

	private HashMap<String, HashMap<String, Integer>> map;
	private boolean isUndirected;
	
	public int timeToLeyla;
	
	public Graph(boolean isUndirected) {
		this.map = new HashMap<String, HashMap<String, Integer>>();
		this.isUndirected = isUndirected;
	}
	
	public void addVertex(String vertex) {
		if(!map.containsKey(vertex)) {
			map.put(vertex, new HashMap<String, Integer>());
		}
	}
	
	public void addEdge(String source, String destination, int weight) {
		if(!map.containsKey(source))
			addVertex(source);
		
		if(!map.containsKey(destination))
			addVertex(destination);
		
		if(source.compareTo(destination) != 0) {
			if(!map.get(source).containsKey(destination))
				map.get(source).put(destination, weight);
			else {
				if(map.get(source).get(destination) > weight) // take the minimum weight edge
					map.get(source).put(destination, weight);
			}
			
			if(isUndirected) {
				if(!map.get(destination).containsKey(source))
					map.get(destination).put(source, weight);
				else {
					if(map.get(destination).get(source) > weight) // take the minimum weight edge
						map.get(destination).put(source, weight);
				}
			}
		}
	}
	
	
	public Stack<String> computePath(String source, String destination) {
		HashSet<String> computed = new HashSet<String>();
		HashMap<String, Integer> estimates = new HashMap<String, Integer>();
		HashMap<String, String> predecessors = new HashMap<String, String>(); 
		
		PriorityQueue<String> pq = new PriorityQueue<String>(new VertexComperator(estimates));
		
		for(String vertex: map.keySet()) {
			int estimate;
			if(vertex.equals(source)) {
				estimate = 0;
			}else {
				estimate = Integer.MAX_VALUE;
			}
			estimates.put(vertex, estimate);
		}
		estimates.put(destination, Integer.MAX_VALUE);
		pq.add(source);
		
		while(!pq.isEmpty()) {
			String current = pq.poll();
			if(!computed.contains(current) && map.get(current) != null) {
				for(String sink: map.get(current).keySet()) {
					int weight = map.get(current).get(sink);
					if(estimates.get(current) + weight < estimates.get(sink) && estimates.get(current) + weight > 0) {
						if(pq.contains(sink))
							pq.remove(sink);
						estimates.put(sink, estimates.get(current) + weight);
						predecessors.put(sink, current); // current is sink's predecessor
						pq.add(sink);
					}
				}
				computed.add(current);
			}
		}
		// couldn't find a way
		if(estimates.get(destination) != Integer.MAX_VALUE) {
			String vertex = destination;
			Stack<String> stack = new Stack<String>();
			while(!vertex.equals(source)) {
				stack.add(vertex);
				vertex = predecessors.get(vertex);
			}
			stack.add(vertex);
			timeToLeyla = estimates.get(destination);
			return stack;
		}else {
			return new Stack<String>();
		}
	}
	
	private class VertexComperator implements Comparator<String>{
		HashMap<String, Integer> cost;
		public VertexComperator(HashMap<String, Integer> cost) {
			this.cost = cost;
		}
		@Override
		public int compare(String o1, String o2) {
			if(o1.equals(o2)) {
				return 0; 
			}
			int costDif = cost.get(o1) - cost.get(o2);
			if(costDif == 0) {
				return o1.compareTo(o2);
			}else {
				return costDif;
			}
		}
		
	}
	
	public int getMinimumSpanningTreeCost(String source){
		if(!map.containsKey(source)) 
			return -1;
		
		HashMap<String, Integer> cost = new HashMap<String, Integer>();
		HashMap<String, String> path = new HashMap<String, String>();
		HashMap<String, Boolean> known = new HashMap<String, Boolean>();
		
		for(String s: map.keySet()) {
			cost.put(s, Integer.MAX_VALUE);
			known.put(s, false);
			path.put(s, null);
		}

		VertexComperator vc = new VertexComperator(cost);
		TreeSet<String> pq = new TreeSet<String>(vc);
		cost.put(source, 0);
		pq.add(source);

		while(!pq.isEmpty()) {
			String v = pq.pollFirst();
			if(!known.get(v)) {
				known.put(v, true);
				for(String vSuccessor: map.get(v).keySet()) {
					if(!known.get(vSuccessor) && cost.get(vSuccessor) > map.get(v).get(vSuccessor)) {
						if(pq.contains(vSuccessor))
							pq.remove(vSuccessor);
						cost.put(vSuccessor, map.get(v).get(vSuccessor));
						path.put(vSuccessor, v);
						pq.add(vSuccessor);
					}
					
				}
			}
		}
		
		// check if there any vertices not reached
		for(boolean b: known.values()) {
			if(!b) {
				return -1;
			}
		}
		
		int sum =0;
		for(int i: cost.values()) {
			sum += i;
		}
		
		return sum;
	}

	
	public void printGraph() {
		for(String s: map.keySet()) {
			System.out.println(s + ": " + map.get(s));
		}
	}
}
