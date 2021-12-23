import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Network {
	private HashMap<Integer, ArrayList<Edge>> map;
	
	private HashMap<String, Integer> bagTypes;
	
	// for set intersection use retainAll with a copy of the set
	private HashSet<Integer> allTrans;
	private HashSet<Integer> greenTrans;
	private HashSet<Integer> redTrans;
	private HashSet<Integer> trains;
	private HashSet<Integer> deers;
	
	private static int id = 0;
	private int sourceId;
	private int sinkId;
	
	public Network() {
		map = new HashMap<Integer, ArrayList<Edge>>();
		
		bagTypes = new HashMap<String, Integer>();
		
		allTrans = new HashSet<Integer>();
		greenTrans = new HashSet<Integer>();
		redTrans = new HashSet<Integer>();
		trains = new HashSet<Integer>();
		deers = new HashSet<Integer>();
		
		// add virtual source
		sourceId = addVertex();
		
		// add virtual sink
		sinkId = addVertex();
		
		
	}
	
	private int addVertex() {
		map.put(id, new ArrayList<Edge>());
		int temp = id;
		id++;
		return temp;
	}
	
	private void addEdge(int from, int to, int capacity) {
		Edge e1 = new Edge(from, to, capacity);
		Edge e2 = new Edge(to, from, 0);
		
		e1.residual = e2;
		e2.residual = e1;
		
		map.get(from).add(e1);
		map.get(to).add(e2);
	}
	
	
	public void addTransportation(int capacity, Vehicle vehicle, Region destination) {
		int transId = addVertex();
		
		// connect to virtual sink, the capacity of the edge is capacity of the train
		addEdge(transId, sinkId, capacity);
		
		allTrans.add(transId);
		
		switch(vehicle) {
		case DEER:
			deers.add(transId);
			break;
		case TRAIN:
			trains.add(transId);
			break;
		}
		
		switch(destination) {
		case GREEN:
			greenTrans.add(transId);
			break;
		case RED:
			redTrans.add(transId);
		}
		
	}
	
	public void addBag(int noGifts, String bagType) {
		Integer sameTypeBag = bagTypes.get(bagType);
		// according to the bag type connect the gift to the vehicles.
		char bagProps[] = bagType.toCharArray();
		
		if(sameTypeBag != null  && bagProps[0] != 'a') {
			ArrayList<Edge> list = map.get(sourceId);
			for(int i = 0; i < list.size(); i++) {
				Edge e = list.get(i);
				if(e.to == sameTypeBag.intValue()) {
					e.capacity += noGifts;
				}
			}
		}
		else {
			int bagId = addVertex();
			
			// connect to virtual source, the capacity of the edge is number of gifts the bag holds.
			addEdge(sourceId, bagId, noGifts);
			
			bagTypes.put(bagType, bagId);
			// make a copy of all the transportation options so that we can retain according to the types.
			HashSet<Integer> suitableTrans = new HashSet<>(allTrans);
			for(char prop: bagProps) {
				if(prop == 'b')
					suitableTrans.retainAll(greenTrans);
				else if(prop == 'c')
					suitableTrans.retainAll(redTrans);
				else if(prop == 'd')
					suitableTrans.retainAll(trains);
				else if(prop == 'e')
					suitableTrans.retainAll(deers);
			}
			
			for(Integer transId: suitableTrans) {
				int edgeCapacity;
				if(bagProps[0] == 'a')
					edgeCapacity = 1;
				else
					edgeCapacity = map.get(transId).get(0).capacity;
				
				addEdge(bagId, transId, edgeCapacity);
			}
		}
		
	}
	
	/**
	 * This function implements Edmonds-Karp algorithm.
	 * @return Max flow.
	 */
	public int findMaxFlow() {
		int maxFlow = 0;
		int curFlow = -1;
		while(curFlow != 0) {
			curFlow = bfsReturnFlow();
			maxFlow += curFlow;
		}
		
		return maxFlow;
	}
	
	/**
	 * Using BFS, this function finds an augmented path from source to the sink and returns the bottleneck flow.
	 * @return Bottleneck flow.
	 */
	private int bfsReturnFlow() {
		boolean visited[] = new boolean[map.size()];
		Edge parent[] = new Edge[map.size()];
		
		ArrayDeque<Integer> queue = new ArrayDeque<Integer>();
		visited[sourceId] = true;
		queue.add(sourceId);
		
		while(!queue.isEmpty()) {
			int vertex = queue.poll();
			if(vertex == sinkId)
				break;
			
			for(Edge e: map.get(vertex)) {
				int cap = e.getRemainingCapacity();
				
				if(!visited[e.to] && cap > 0) {
					visited[e.to] = true;
					parent[e.to] = e;
					queue.add(e.to);
				}
			}
		}
		if(parent[sinkId] == null) {
			return 0;
		}
		
		// retrace the parents and find the bottleneck
		int bottleneck = Integer.MAX_VALUE;
		Edge e = parent[sinkId];
		while(e != null) {
			int cap = e.getRemainingCapacity();
			if(cap < bottleneck) {
				bottleneck = cap;
			}
			e = parent[e.from];
		}
		
		// retrace again and augment the capacities
		e = parent[sinkId];
		while(e != null) {
			e.augment(bottleneck);
			e = parent[e.from];
		}
		
		return bottleneck;
		
	}
	
	public void print() {
		System.out.println(map);
	}
}
