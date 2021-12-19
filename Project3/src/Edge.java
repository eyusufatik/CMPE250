
public class Edge implements Comparable<Edge>{
	private String source;
	private String destination;
	private int weight;
	
	public Edge(String source, String destination, int weight) {
		this.destination = destination;
		this.source = source;
		this.weight = weight;
	}
	
	public String getSource() {
		return source;
	}
	
	public String getDestination() {
		return destination;
	}
	
	public int getWeight() {
		return weight;
	}
	
	@Override
	public String toString() {
		return destination + " " + weight;
	}

	@Override
	public int compareTo(Edge o) {
		// TODO Auto-generated method stub
		return this.weight - o.weight;
	}
}
