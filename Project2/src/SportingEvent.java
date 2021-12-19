
/**
 * Parent class of {@link TrainingEvent}, {@link MassgeEvent} and {@link PhysiotherapyEvent}
 * These classes are used for ordering of the queues and holding information about which player is arriving, when he/she is arriving and how long will the service take.
 *
 */
public abstract class SportingEvent{
	private Player player;
	private double arrivalTime;
	private double duration;

	public SportingEvent(Player player, double arrivalTime, double duration) {
		this.player = player;
		this.arrivalTime = arrivalTime;
		this.duration = duration;
	}
	
	/**
	 * 
	 * @return When the player arrived for the service.
	 */
	public double getArrivalTime() {
		return arrivalTime;
	}
	
	/**
	 * @return ID of the player of the service.
	 */
	public int getPlayerId() {
		return player.getId();
	}
	
	/**
	 * 
	 * @return Skill level of the player of the service.
	 */
	public int getPlayerSkillLevel() {
		return player.getSkillLevel();
	}
	
	/**
	 * 
	 * @return The player of the service.
	 */
	public Player getPlayer() {
		return player;
	}
	
	/**
	 * 
	 * @return How long will the service take (-1 for {@link PhysiotherapyEvent} because in that case duration depends on the {@link Physiotherapist}.
	 */
	public double getDuration() {
		return duration;
	}
	
	@Override
	public String toString() {
		return "sporting event";
	}
}
