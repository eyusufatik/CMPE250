
/**
 * This class contains information about massage services waiting to be provided.
 *
 */
public class MassageEvent extends SportingEvent implements Comparable<MassageEvent>{

	/**
	 * 
	 * @param player The player who wants a massage.
	 * @param arrivalTime When the player arrived for the massage queue.
	 * @param duration Duration of the massage.
	 */
	public MassageEvent(Player player, double arrivalTime, double duration) {
		super(player, arrivalTime, duration);
	}

	@Override
	public int compareTo(MassageEvent o) {
		if(getPlayerSkillLevel() == o.getPlayerSkillLevel()) {
			if (Math.abs(getArrivalTime() - o.getArrivalTime()) < Stats.epsilon){
				return getPlayerId() - o.getPlayerId();
			}else {
				if(getArrivalTime() < o.getArrivalTime())
					return -1;
				else
					return 1;
			}
		}else {
			return o.getPlayerSkillLevel() - getPlayerSkillLevel();
		}
	}
	
	@Override
	public String toString() {
		return "massage event";
	}

}
