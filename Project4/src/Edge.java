
public class Edge {
	public int from; // might be unnnecessary
	public int to;
	public Edge residual;
	
	public int flow;
	public int capacity;
	
	public Edge(int from, int to, int capacity) {
		this.from = from;
		this.to = to;
		this.capacity = capacity;
		flow = 0;
	}
	
	public int getRemainingCapacity() {
	      return capacity - flow;
	    }

    public void augment(int bottleNeck) {
      flow += bottleNeck;
      residual.flow -= bottleNeck;
    }
    
    public String toString() {
    	return from + " " + to;
    }
}
