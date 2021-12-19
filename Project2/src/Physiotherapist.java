public class Physiotherapist extends Personnel {
	private double serviceTime;
	
	public Physiotherapist(int id, double serviceTime) {
		super(id);
		this.serviceTime = serviceTime;
	}

	public double getServiceTime() {
		return serviceTime;
	}
	
	/**
	 * Assigns a therapy session to the physiotherapist. Also schedules a departure event according to the duration of the therapy.
	 * @param event The massage event assigned to the therapist.
	 */
	@Override
	public void assign(SportingEvent event) {
		PhysiotherapyEvent pEvent = (PhysiotherapyEvent) event;
		
		//System.out.printf("time: %f player: %d phys: %d \n", Stats.simTime, pEvent.getPlayerId(), getId());
		
		getBusy();
		
		pEvent.getPlayer().getOutOfQueue();
		pEvent.getPlayer().getBusy();

		
		double turnaroundTime = pEvent.getPlayer().stopTurnaroundTime(Stats.simTime + getServiceTime());
		double waitTime = Stats.simTime - pEvent.getArrivalTime();
		pEvent.getPlayer().waitedInPhysiotherapyQueueFor(waitTime);
		
		//if(pEvent.getPlayerId() == 461)
			//System.out.println(waitTime);
		
		Stats.sumWaitingTimePhysiotherapyQueue += Stats.simTime - pEvent.getArrivalTime();
		Stats.numberOfWatingInPhysiotherapyQueue++;
		Stats.sumPhysiotherapyTime += getServiceTime();
		Stats.sumTurnaroundTime += turnaroundTime;
		
		
		DepartureEvent dEvent = new DepartureEvent(pEvent.getPlayer(), 
				Stats.simTime + getServiceTime(), this);
		project2main.jobs.add(dEvent);
	}
	
	@Override
	public String toString() {
		return "Physiotherapist " + getId();
	}

}
