import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class Network {
	private HashMap<Integer, ArrayList<Edge>> map;
	
	private HashMap<String, Integer> bagTypes;
	
	private int level[];
	
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
	
	public void print() {
		System.out.println(map);
	}

	public int findMaxFlowWithDinic() {
		int maxFlow = 0;
		int next[] = new int[map.size()];
		level = new int[map.size()];
		
		while(dinicBFS()) {
			Arrays.fill(next, 0);
			int flow = dinicDFS(sourceId, next, Integer.MAX_VALUE);
			while(flow != 0) {
				maxFlow += flow;
				flow = dinicDFS(sourceId, next, Integer.MAX_VALUE);
			}
		}
		return maxFlow;
	}
	
	private boolean dinicBFS() {
		Arrays.fill(level, -1);
		ArrayDeque<Integer> queue = new ArrayDeque<Integer>();
		queue.add(sourceId);
		level[sourceId] = 0;
		
		while(!queue.isEmpty()) {
			int vertex = queue.poll();
			for (Edge edge : map.get(vertex)) {
	          int cap = edge.getRemainingCapacity();
	          if (cap > 0 && level[edge.to] == -1) {
	            level[edge.to] = level[vertex] + 1;
	            queue.add(edge.to);
	          }
	        }
		}
		return level[sinkId] != -1;
	}
	
	private int dinicDFS(int current, int next[], int flow) {
		//System.out.println(current + " "+ flow);
		if (current == sinkId)
			return flow;
		
		int numEdges = map.get(current).size();
	
	   	for( ; next[current] < numEdges; next[current]++) {
		    Edge edge = map.get(current).get(next[current]);
		    int cap = edge.getRemainingCapacity();
		    if (cap > 0 && level[edge.to] == level[current] + 1) {
		
		      int bottleNeck = dinicDFS(edge.to, next, Math.min(flow, cap));
		      if (bottleNeck > 0) {
		        edge.augment(bottleNeck);
		        return bottleNeck;
		      }
		    }
	   	}
	    return 0;
	}
}
