import java.util.PriorityQueue;

/**
 * This class is used for making arrival events happen in the simulation at the right order.
 *
 * @param <T> Either {@link TrainingEvent}, {@link MassageEvent} or {@link PhysiotherapyEvent}
 */
public class ArrivalEvent<T extends SportingEvent> extends Event{
	
	T sportingEvent;
	PriorityQueue<T> eventPq;
	
	/**
	 * 
	 * @param player Player of the event.
	 * @param eventTime When the player arrives
	 * @param sportingEvent The service player arrives for. (e.g. training, massage)
	 * @param queue The queue the player will wait in. 
	 */
	public ArrivalEvent(Player player, double eventTime, T sportingEvent, PriorityQueue<T> queue) {
		super(player, eventTime);
		// TODO Auto-generated constructor stub
		this.sportingEvent = sportingEvent;
		this.eventPq = queue;
	}
	
	/**
	 * Makes the event happen.
	 */
	public void occur() {
		Stats.simTime = getEventTime();
		
		if(getPlayer().getMassagesTaken() == 3 && sportingEvent instanceof MassageEvent) {
			Stats.invalidAttempts++;
		}else {
			if(getPlayer().isFree() && !getPlayer().inQueue()) {
				//System.out.printf("time %f %s player: %d \n", getEventTime(), sportingEvent, getPlayer().getId());
				eventPq.add(sportingEvent);
				getPlayer().getInQueue();
				if(sportingEvent instanceof MassageEvent)
					getPlayer().takeMassage();
			}else {
				Stats.cancelledAttempts++;
				//System.out.println("id " + getPlayer().getId() + " event: " + sportingEvent.getClass());
			}
		}		
	}
	
	@Override
	public String toString() {
		return "this is an arrival for: " + sportingEvent.toString();
	}
}
