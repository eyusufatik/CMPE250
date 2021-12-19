
/**
 * This class is used to depart the player from a service.
 *
 */
public class DepartureEvent extends Event{
	private Personnel personnel;
	
	/**
	 * 
	 * @param player Player departing
	 * @param eventTime Time when the player leaves.
	 * @param personnel Players {@link Coach}, {@link Masseur} or {@link Physiotherapist}
	 */
	public DepartureEvent(Player player, double eventTime, Personnel personnel) {
		super(player, eventTime);
		this.personnel = personnel;
	}

	/**
	 * Make the departure happen. Lets both the player and his/her service provider free.
	 */
	@Override
	public void occur() {
		Stats.simTime = getEventTime();
		getPlayer().setFree();
		personnel.setFree();
	}

}
