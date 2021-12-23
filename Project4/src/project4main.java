import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class project4main {
	public static void main(String[] args) throws FileNotFoundException {
		
		double time = System.currentTimeMillis();
		Scanner in = new Scanner(new File(args[0]));
		PrintStream out = new PrintStream(new File(args[1]));
		
		Network network = new Network();
		int totalGifts = 0;
		
		
		int noGreenRegionTrains = Integer.parseInt(in.nextLine());
		String greenRegionTrainCapacities[] = in.nextLine().split(" ");
		for(String s: greenRegionTrainCapacities) {
			if(!s.equals("")) {
				int capacity = Integer.parseInt(s);
				// add green region trains to the network
				network.addTransportation(capacity, Vehicle.TRAIN, Region.GREEN);
			}
		}
		
		int noRedRegionTrains = Integer.parseInt(in.nextLine());
		String redRegionTrainCapacities[] = in.nextLine().split(" ");
		for(String s: redRegionTrainCapacities) {
			if(!s.equals("")) {
				int capacity = Integer.parseInt(s);
				// add red region trains to the network
				network.addTransportation(capacity, Vehicle.TRAIN, Region.RED);
			}
		}
		
		int noGreenDeers = Integer.parseInt(in.nextLine());
		String greenDeerCapacities[] = in.nextLine().split(" ");
		for(String s: greenDeerCapacities) {
			if(!s.equals("")) {
				int capacity = Integer.parseInt(s);
				// add green region deers to the network
				network.addTransportation(capacity, Vehicle.DEER, Region.GREEN);
			}
		}
		
		int noRedDeers = Integer.parseInt(in.nextLine());
		String redDeerCapacities[] = in.nextLine().split(" ");
		for(String s: redDeerCapacities) {
			if(!s.equals("")) {
				int capacity = Integer.parseInt(s);
				// add red region deers to the network
				network.addTransportation(capacity, Vehicle.DEER, Region.RED);
			}
		}
		
		int noBags = Integer.parseInt(in.nextLine());
		if(noBags > 0) {
			String bagProps[] = in.nextLine().split(" ");
			for(int i = 0; i < noBags * 2; i+=2) {
				String bagType = bagProps[i];
				int noGifts = Integer.parseInt(bagProps[i+1]);
				totalGifts += noGifts;
				// add bags to the network
				network.addBag(noGifts, bagType);
			}
		}
		//network.print();
		
		out.print(totalGifts - network.findMaxFlowWithDinic());
		
		System.out.print((System.currentTimeMillis() - time)/1000 + " seconds ");
	}
}
