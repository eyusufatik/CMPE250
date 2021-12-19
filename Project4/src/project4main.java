import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class project4main {
	public static void main(String[] args) throws FileNotFoundException {
		
		Scanner in = new Scanner(new File(args[0]));
		PrintStream out = new PrintStream(new File(args[1]));
		
		
		int noGreenRegionTrains = Integer.parseInt(in.nextLine());
		String greenRegionTrainCapacities[] = in.nextLine().split(" ");
		for(String s: greenRegionTrainCapacities) {
			int capacity = Integer.parseInt(s);
			// add green region trains to the network
		}
		
		int noRedRegionTrains = Integer.parseInt(in.nextLine());
		String redRegionTrainCapacities[] = in.nextLine().split(" ");
		for(String s: redRegionTrainCapacities) {
			int capacity = Integer.parseInt(s);
			// add red region trains to the network
		}
		
		int noGreenDeers = Integer.parseInt(in.nextLine());
		String greenDeerCapacities[] = in.nextLine().split(" ");
		for(String s: greenDeerCapacities) {
			int capacity = Integer.parseInt(s);
			// add green region deers to the network
		}
		
		int noRedDeers = Integer.parseInt(in.nextLine());
		String redDeerCapacities[] = in.nextLine().split(" ");
		for(String s: redDeerCapacities) {
			int capacity = Integer.parseInt(s);
			// add red region deers to the network
		}
		
		int noBags = Integer.parseInt(in.nextLine());
		String bagProps[] = in.nextLine().split(" ");
		for(int i = 0; i < noBags; i+=2) {
			String bagType = bagProps[i];
			int noGifts = Integer.parseInt(bagProps[i+1]);
			// add bags to the network
		}
	}
}
