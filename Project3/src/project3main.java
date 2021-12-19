import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.Stack;

public class project3main {

	public static void main(String[] args) throws FileNotFoundException {
		
		Scanner in = new Scanner(new File(args[0]));
		PrintStream out = new PrintStream(new File(args[1]));
		int maxTime = in.nextInt();
		int numberOfCities = in.nextInt();
		in.nextLine();
		
		String homeLine = in.nextLine();
		String homes[] = homeLine.split(" ");
		String mecnunCity = homes[0];
		String leylaCity = homes[1];
		
		
		Graph firstGraph = new Graph(false); // graph from mecnun city to leyla city
		Graph secondGraph = new Graph(true); // from leyla city to cities on the other side
		for(int i = 0; i < numberOfCities; i++) {
			// fill both graphs.
			String line = in.nextLine();
			String tokens[] = line.split(" ");
			String source = tokens[0];
			if(tokens.length == 1) {
				if(source.startsWith("c"))
					firstGraph.addVertex(source);
				else
					secondGraph.addVertex(source);
			}else {
				for(int j = 1; j<tokens.length; j+=2) {
					String destination = tokens[j];
					int weight = Integer.parseInt(tokens[j+1]);
					if(source.startsWith("c") && !destination.startsWith("d"))
						firstGraph.addEdge(source, destination, weight);
					else {
						secondGraph.addEdge(source, destination, weight);
					}
				}
			}
			
			
		}
		
		String outLine1 = "";
		String outLine2 = "";
		
		Stack<String> pathStack = firstGraph.computePath(mecnunCity, leylaCity);
		if(pathStack.isEmpty()) {
			// can't reach leyla
			outLine1 = "-1";
			outLine2 = "-1";
		}else {
			// reaches leyla 
			int timeToLeyla = firstGraph.timeToLeyla;
			while(!pathStack.isEmpty()) {
				outLine1 += pathStack.pop() + " ";
			}
			outLine1 = outLine1.strip();
			
			if(timeToLeyla <= maxTime) {
				// they can marry
				int tax = 2 * secondGraph.getMinimumSpanningTreeCost(leylaCity);
				outLine2 = Integer.toString(tax);
			}else {
				// they can't marry
				outLine2 = "-1";
			}
		}
		out.println(outLine1);
		out.print(outLine2);
	}

}
