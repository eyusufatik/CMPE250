import java.util.HashMap;
import java.util.HashSet;

public class Network {
	private HashMap<Integer, HashMap<Integer, Integer>> map;
	
	
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
		int temp = id;
		id++;
		return temp;
	}
	
	public void addTransportation(int capacity, Vehicle vehicle, Region destination) {
		int transId = addVertex();
		
		// connect to virtual sink, the capacity of the edge is capacity of the train
		map.get(transId).put(sinkId, capacity);
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
		}
	}
	
	public void print() {
		System.out.println(map);
	}
}
