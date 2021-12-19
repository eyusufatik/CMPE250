/**
 * Massage service provider.
 *
 */
public class Masseur extends Personnel{
	
	/**
	 * 
	 * @param id Id of the masseur.
	 */
	public Masseur(int id) {
		super(id);
	}
	
	/**
	 * Assigns a massage to the masseur. Also schedules a departure event according to the duration of the massage.
	 * @param event The massage event assigned to the masseur.
	 */
	@Override
	public void assign(SportingEvent event) {
		MassageEvent mEvent = (MassageEvent) event;
		
		//System.out.printf("time: %f player: %d masseur: %d \n", Stats.simTime, mEvent.getPlayerId(), getId());
		
		getBusy();

		mEvent.getPlayer().getOutOfQueue();
		mEvent.getPlayer().getBusy();
		
		mEvent.getPlayer().waitedInMassageQueueFor(Stats.simTime - mEvent.getArrivalTime());

		//mEvent.getPlayer().takeMassage();

		
		Stats.sumWaitingTimeMassageQueue += Stats.simTime - mEvent.getArrivalTime();
		Stats.numberOfWatingInMassageQueue++;
		Stats.sumMassageTime += mEvent.getDuration();
		
		DepartureEvent dEvent = new DepartureEvent(mEvent.getPlayer(),
				Stats.simTime + mEvent.getDuration(), this);
		project2main.jobs.add(dEvent);
		
		
	}
	
	@Override
	public String toString() {
		return "Masseur " + getId();
	}
	
}
