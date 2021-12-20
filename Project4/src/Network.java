import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;

public class Network {
	private HashMap<Integer, HashMap<Integer, Integer>> map;
	private HashMap<Integer, HashMap<Integer, Integer>> flows;
	
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
		map = new HashMap<Integer, HashMap<Integer, Integer>>();
		flows = new HashMap<Integer, HashMap<Integer, Integer>>();
		
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
		map.put(id, new HashMap<Integer, Integer>());
		flows.put(id, new HashMap<Integer, Integer>());
		
		int temp = id;
		id++;
		return temp;
	}
	
	
	public void addTransportation(int capacity, Vehicle vehicle, Region destination) {
		int transId = addVertex();
		
		// connect to virtual sink, the capacity of the edge is capacity of the train
		map.get(transId).put(sinkId, capacity);
		map.get(sinkId).put(transId, 0);
		
		flows.get(transId).put(sinkId, 0);
		flows.get(sinkId).put(transId, 0);
		
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
		int bagId = addVertex();
		
		// connect to virtual source, the capacity of the edge is number of gifts the bag holds.
		map.get(sourceId).put(bagId, noGifts);
		map.get(bagId).put(sourceId, 0);
		
		flows.get(sourceId).put(bagId, 0);
		flows.get(bagId).put(sourceId, 0);
		
		// according to the bag type connect the gift to the vehicles.
		char bagProps[] = bagType.toCharArray();
		
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
				edgeCapacity = map.get(transId).get(sinkId);
			
			map.get(bagId).put(transId, edgeCapacity);
			map.get(transId).put(bagId, 0);
			
			flows.get(bagId).put(transId, 0);
			flows.get(transId).put(bagId, 0);
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
		int parent[] = new int[map.size()];
		
		ArrayDeque<Integer> queue = new ArrayDeque<Integer>();
		visited[sourceId] = true;
		queue.add(sourceId);
		
		while(!queue.isEmpty()) {
			int vertex = queue.poll();
			if(vertex == sinkId)
				break;
			
			for(int neighbour: map.get(vertex).keySet()) {
				int cap = map.get(vertex).get(neighbour) - flows.get(vertex).get(neighbour);
				
				if(!visited[neighbour] && cap > 0) {
					visited[neighbour] = true;
					parent[neighbour] = vertex;
					queue.add(neighbour);
				}
			}
		}
		if(parent[sinkId] == 0) {
			return 0;
		}
		
		// backtrace the parents and find the bottleneck
		int bottleneck = Integer.MAX_VALUE;
		int currentVertex = sinkId;
		while(currentVertex != sourceId) {
			int cap = map.get(parent[currentVertex]).get(currentVertex) - flows.get(parent[currentVertex]).get(currentVertex);
			if(cap < bottleneck) {
				bottleneck = cap;
			}
			currentVertex = parent[currentVertex];
		}
		
		// backtrace again and augment the capacities
		currentVertex = sinkId;
		while(currentVertex != sourceId) {
			int flow = flows.get(parent[currentVertex]).get(currentVertex);
			int residualFlow = flows.get(currentVertex).get(parent[currentVertex]);
			
			flows.get(parent[currentVertex]).put(currentVertex, flow + bottleneck);
			flows.get(currentVertex).put(parent[currentVertex], residualFlow - bottleneck);
			currentVertex = parent[currentVertex];
		}
		
		return bottleneck;
		
	}
	
	public void print() {
		System.out.println(map);
	}
}
