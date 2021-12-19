import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.PriorityQueue;
import java.util.Scanner;

public class project2main {
	
	public static PriorityQueue<PhysiotherapyEvent> physiotherapyPq = new PriorityQueue<PhysiotherapyEvent>();
	/**
	 * Jobs are either {@link ArrivalEvent} or {@link DepartureEvent}.
	 */
	public static PriorityQueue<Event> jobs = new PriorityQueue<Event>();
	
	public static void main(String[] args) throws FileNotFoundException {
		double startTime = System.currentTimeMillis();
		
		Scanner in = new Scanner(new File(args[0]));
		PrintStream out = new PrintStream(new File(args[1]));
		
		PriorityQueue<TrainingEvent> trainingPq = new PriorityQueue<TrainingEvent>();
		PriorityQueue<MassageEvent> massagePq = new PriorityQueue<MassageEvent>();
		
		
		int N = in.nextInt(); // number of players
		in.nextLine();
		Player players[] = new Player[N];
		// Create players according to the input.
		for(int i = 0; i<N; i++) {
			String line = in.nextLine();
			String tokens[] = line.split(" ");
			int id = Integer.parseInt(tokens[0]);
			int skillLevel = Integer.parseInt(tokens[1]);
			players[id] = new Player(id, skillLevel);
		}
		
		int A = in.nextInt(); // Number of arrival events.
		in.nextLine();
		// Create arrival events according to the input.
		for(int i = 0; i<A; i++) {
			String line = in.nextLine();
			String tokens[] = line.split(" ");
			String type = tokens[0];
			int id = Integer.parseInt(tokens[1]);
			double arrivalTime = Double.parseDouble(tokens[2]);
			double duration = Double.parseDouble(tokens[3]);
			
			ArrivalEvent arrivalEvent;
			if(type.equals("t")) { // Training.
				arrivalEvent = new ArrivalEvent<TrainingEvent>(players[id], arrivalTime, 
						new TrainingEvent(players[id], arrivalTime, duration), trainingPq);
			}else { // Massage.
				arrivalEvent = new ArrivalEvent<MassageEvent>(players[id], arrivalTime,
						new MassageEvent(players[id], arrivalTime, duration), massagePq);
			}
			jobs.add(arrivalEvent);
		}
		
		String line = in.nextLine();
		String tokens[] = line.split(" ");
		int sP = Integer.parseInt(tokens[0]); // Number of physiotherapists.
		Physiotherapist physiotherapists[] = new Physiotherapist[sP];
		for (int i = 1; i <= sP; i++) {
			double serviceTime = Double.parseDouble(tokens[i]);
			physiotherapists[i-1] = new Physiotherapist(i-1, serviceTime);
		}
				
		int sC = in.nextInt(); // Number of coaches.
		int sM = in.nextInt(); // Number of masseurs.
		
		Coach coaches[] = new Coach[sC];
		Masseur masseurs[] = new Masseur[sM];
		
		// Create masseurs and coaches.
		for (int i = 0; i < sC; i++) {
			coaches[i] = new Coach(i);
		}
		for(int i = 0; i < sM; i++) {
			masseurs[i] = new Masseur(i);
		}
		//System.out.println(System.currentTimeMillis() - startTime);
		
		while(!jobs.isEmpty()) { // While there still are arrivals and departures.
			Event event = jobs.poll();
			event.occur(); // Let the event happen.
			
			if(!trainingPq.isEmpty()) {
				for (int i = 0; i < coaches.length; i++) {
					Coach c = coaches[i];
					if(c.isAvailable()) {
						TrainingEvent tEvent = trainingPq.poll();
						c.assign(tEvent);
					}
					
					if(trainingPq.isEmpty())
						break;
				}
			}
			Stats.checkTrainingQueueLength(trainingPq);
			
			if(!physiotherapyPq.isEmpty()) {
				for(int i = 0; i < physiotherapists.length; i++) {
					Physiotherapist p = physiotherapists[i];
					if(p.isAvailable()) {
						PhysiotherapyEvent pEvent = physiotherapyPq.poll();
						p.assign(pEvent);
					}
					
					if(physiotherapyPq.isEmpty())
						break;
				}
			}
			Stats.checkPhysiotherapyQueueLength(physiotherapyPq);
			
			if(!massagePq.isEmpty()) {
				for(int i = 0; i < masseurs.length; i++) {
					Masseur m = masseurs[i];
					if(m.isAvailable()) {
						MassageEvent mEvent = massagePq.poll();
						m.assign(mEvent);
					}
					
					if(massagePq.isEmpty())
						break;
				}
			}
			Stats.checkMassageQueueLength(massagePq);
			
			
			
		}
		
		out.printf("%d\n", Stats.maxLengthOfTrainingQueue);
		out.printf("%d\n", Stats.maxLengthOfPhysiotherapyQueue);
		out.printf("%d\n", Stats.maxLengthOfMassageQueue);
		
		
		Double avgWaitingTimeTrainingQueue = Stats.sumWaitingTimeTrainingQueue/Stats.numberOfWatingInTrainingQueue;
		out.printf("%.3f\n", (!avgWaitingTimeTrainingQueue.isNaN()) ? avgWaitingTimeTrainingQueue:0);
		Double avgWaitingTimePhysiotherapyQueue = Stats.sumWaitingTimePhysiotherapyQueue/Stats.numberOfWatingInPhysiotherapyQueue;
		out.printf("%.3f\n", (!avgWaitingTimePhysiotherapyQueue.isNaN()) ? avgWaitingTimePhysiotherapyQueue:0);
		Double avgWaitingTimeMassageQueue = Stats.sumWaitingTimeMassageQueue/Stats.numberOfWatingInMassageQueue;
		out.printf("%.3f\n", (!avgWaitingTimeMassageQueue.isNaN()) ? avgWaitingTimeMassageQueue:0);
		
		Double avgTrainingTime = Stats.sumTrainingTime/Stats.numberOfWatingInTrainingQueue;
		out.printf("%.3f\n", (!avgTrainingTime.isNaN()) ? avgTrainingTime:0);
		Double avgPhysiotherapyTime = Stats.sumPhysiotherapyTime/Stats.numberOfWatingInPhysiotherapyQueue;
		out.printf("%.3f\n", (!avgPhysiotherapyTime.isNaN()) ? avgPhysiotherapyTime:0);
		Double avgMassageTime = Stats.sumMassageTime/Stats.numberOfWatingInMassageQueue;
		out.printf("%.3f\n", (!avgMassageTime.isNaN()) ? avgMassageTime:0);
		
		Double avgTurnaroundTime = Stats.sumTurnaroundTime/Stats.numberOfWatingInTrainingQueue;
		out.printf("%.3f\n", (!avgTurnaroundTime.isNaN()) ? avgTurnaroundTime:0);
		
		int maxId = -1;
		double maxTime = -1;
		for (int i = 0; i < players.length; i++) {
			Player p = players[i];
			//System.out.println(p.getId() + " " + p.getTotalWaitingTimeInPhysiotherapyQueue());
			if(Math.abs(p.getTotalWaitingTimeInPhysiotherapyQueue() - maxTime) < Stats.epsilon) {
				if(p.getId() < maxId)
					maxId = p.getId();
			}else {
				if(p.getTotalWaitingTimeInPhysiotherapyQueue() > maxTime) {
					maxTime = p.getTotalWaitingTimeInPhysiotherapyQueue();
					maxId = p.getId();
				}
			}
		
		}
		out.printf("%d %.3f\n", maxId, maxTime);
		
		int minId = -1;
		double minTime = Double.MAX_VALUE;
		for (int i = 0; i < players.length; i++) {
			Player p = players[i];
			if(p.getMassagesTaken() == 3) {
				if(Math.abs(p.getTotalWaitingTimeInMassageQueue() - minTime) < Stats.epsilon) {
					if(p.getId() < minId)
						minId = p.getId();
				}else {
					if(p.getTotalWaitingTimeInMassageQueue() < minTime) {
						minTime = p.getTotalWaitingTimeInMassageQueue();
						minId = p.getId();
					}
				}
			}
		}
		minTime = (minId == -1) ? -1: minTime;
		if(minTime != -1)
			out.printf("%d %.3f\n", minId, minTime);
		else
			out.printf("%d %d\n", minId, -1);
		
		out.printf("%d\n", Stats.invalidAttempts);
		
		out.printf("%d\n", Stats.cancelledAttempts);
		
		out.printf("%.3f\n", Stats.simTime);
		
		//System.out.println((System.currentTimeMillis() - startTime)/1000.0);
	}

}
