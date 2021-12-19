
/**
 * Training service provider.
 *
 */
public class Coach extends Personnel{
	private TrainingEvent lastTraining;
	
	/**
	 * 
	 * @param id Id of the coach.
	 */
	public Coach(int id) {
		super(id);
	}

	/**
	 * Assigns a training to the coach. Also schedules a departure event according to the duration of the training.
	 * @param event The training event assigned to the coach
	 */
	@Override
	public void assign(SportingEvent event) {
		lastTraining = (TrainingEvent) event;
		
		lastTraining.getPlayer().startTurnaroundTimer(lastTraining.getArrivalTime());
		
		//System.out.printf("time: %f player: %d coach: %d \n", Stats.simTime, lastTraining.getPlayerId(), getId());
		
		getBusy();
	 
		lastTraining.getPlayer().getOutOfQueue();
		lastTraining.getPlayer().getBusy();
		
		lastTraining.getPlayer().setLastTrainingDuration(lastTraining.getDuration());
		
		
		
		Stats.sumWaitingTimeTrainingQueue += Stats.simTime - lastTraining.getArrivalTime();
		Stats.numberOfWatingInTrainingQueue++;
		Stats.sumTrainingTime += lastTraining.getDuration();
		
		DepartureEvent dEvent = new DepartureEvent(lastTraining.getPlayer(), 
				Stats.simTime + lastTraining.getDuration(), this);
		project2main.jobs.add(dEvent);
		
	}
	
	@Override
	public String toString() {
		return "Coach " + getId();
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * Also creates a {@link PhysiotherapyEvent} for the player.
	 */
	@Override
	public void setFree() {
		super.setFree();
		
		PhysiotherapyEvent pEvent = new PhysiotherapyEvent(lastTraining.getPlayer(), Stats.simTime);
		
		project2main.physiotherapyPq.add(pEvent);
		
		lastTraining.getPlayer().getInQueue();
		
		//System.out.printf("time: %f phys event player: %d \n", Stats.simTime, lastTraining.getPlayer().getId());
	}
}
