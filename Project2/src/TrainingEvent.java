import java.util.PriorityQueue;


/**
 * This class contains information about training services waiting to be provided.
 *
 */
public class TrainingEvent extends SportingEvent implements Comparable<TrainingEvent>{
	
	/**
	 * 
	 * @param player The player who wants a training service.
	 * @param arrivalTime When the player arrived for the training queue.
	 * @param duration Duration of the training.
	 */
	public TrainingEvent(Player player,double arrivalTime, double duration) {
		super(player, arrivalTime, duration);
	}

	@Override
	public int compareTo(TrainingEvent o) {
		if (Math.abs(getArrivalTime() - o.getArrivalTime()) < Stats.epsilon){
			return getPlayerId() - o.getPlayerId();
		}else {
			if(getArrivalTime() < o.getArrivalTime())
				return -1;
			else
				return 1;
		}
	}
	
	@Override
	public String toString() {
		return "training event";
	}
}
