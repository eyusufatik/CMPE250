import java.util.PriorityQueue;

/**
 * This class holds statistics about the simulation.
 *
 */
public class Stats {
	public static double simTime;
	public static double epsilon = 0.0000000001;
	
	public static int cancelledAttempts = 0;
	public static int invalidAttempts = 0;
	
	public static int maxLengthOfTrainingQueue = 0;
	public static int maxLengthOfMassageQueue = 0;
	public static int maxLengthOfPhysiotherapyQueue;
	
	public static double sumWaitingTimeTrainingQueue = 0;
	public static int numberOfWatingInTrainingQueue = 0;
	
	public static double sumWaitingTimeMassageQueue = 0;
	public static int numberOfWatingInMassageQueue = 0;
	
	public static double sumWaitingTimePhysiotherapyQueue = 0;
	public static int numberOfWatingInPhysiotherapyQueue = 0;
	
	public static double sumTrainingTime = 0;
	public static double sumMassageTime = 0;
	public static double sumPhysiotherapyTime = 0;

	public static double sumTurnaroundTime = 0;
	
	/**
	 * Checks if the queue size is the maximum for the current simulation.
	 * @param queue Queue to be checked.
	 */
	public static void checkTrainingQueueLength(PriorityQueue<TrainingEvent> queue) {
		if(queue.size() > maxLengthOfTrainingQueue) {
			maxLengthOfTrainingQueue = queue.size();
		}
	}
	
	/**
	 * Checks if the queue size is the maximum for the current simulation.
	 * @param queue Queue to be checked.
	 */
	public static void checkMassageQueueLength(PriorityQueue<MassageEvent> queue) {
		if(queue.size() > maxLengthOfMassageQueue) {
			maxLengthOfMassageQueue = queue.size();
		}
	}
	
	/**
	 * Checks if the queue size is the maximum for the current simulation.
	 * @param queue Queue to be checked.
	 */
	public static void checkPhysiotherapyQueueLength(PriorityQueue<PhysiotherapyEvent> queue) {
		if(queue.size() > maxLengthOfPhysiotherapyQueue) {
			maxLengthOfPhysiotherapyQueue = queue.size();
		}
	}
}
