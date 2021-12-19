

/**
 * This class contains information about physiotherapy services waiting to be provided.
 *
 */
public class PhysiotherapyEvent extends SportingEvent implements Comparable<PhysiotherapyEvent>{

	/**
	 * 
	 * @param player The player who wants a physiotherapy service.
	 * @param arrivalTime When the player arrived for the physiotherapy queue.
	 */
	public PhysiotherapyEvent(Player player, double arrivalTime) {
		super(player, arrivalTime, -1);
	}

	@Override
	public int compareTo(PhysiotherapyEvent o) {
		Player player = getPlayer();
		Player otherPlayer = o.getPlayer();
		if(Math.abs(player.getLastTrainingDuration() - otherPlayer.getLastTrainingDuration()) < Stats.epsilon) {
			if(Math.abs(getArrivalTime() - o.getArrivalTime()) < Stats.epsilon) {
				return player.getId() - otherPlayer.getId();
			}else {
				if(getArrivalTime() < o.getArrivalTime()) {
					return -1; 
				}else {
					return 1;
				}
			}
		}else {
			if(player.getLastTrainingDuration() > otherPlayer.getLastTrainingDuration()) {
				return -1;
			}else {
				return 1;
			}
		}
	}
}
