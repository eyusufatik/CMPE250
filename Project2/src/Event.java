
/**
 * Parent class for {@link ArrivalEvent} and {@link DeparturerEvent}
 * <br/>
 * Only holds eventTime and player information.
 *
 */
public abstract class Event implements Comparable<Event>{
	private double eventTime;
	private Player player;
	
	public Event(Player player, double eventTime) {
		this.player = player;
		this.eventTime = eventTime;
	}
	
	/**
	 * 
	 * @return When the event occurs.
	 */
	public double getEventTime() {
		return eventTime;
	}
	
	/**
	 * 
	 * @return The player of the event.
	 */
	public Player getPlayer() {
		return player;
	}
	
	@Override
	public int compareTo(Event o) {
		if(Math.abs(getEventTime() - o.getEventTime()) < 0.0000000001) {
			return player.getId() - o.player.getId();
		}else {
			if(getEventTime() < o.getEventTime()) {
				return -1;
			}else {
				return 1;
			}
		}
	}
	
	@Override
	public String toString() {
		return "this is a normal event";
	}
	
	/**
	 * This method must be implemented by child classes.
	 * <br/>
	 * This is used inside the main class to make the event occur.
	 */
	public abstract void occur();
}
