
/**
 * Parent class of {@link Coach}, {@link Masseur} and {@link Physiotherapist}
 *
 */
public abstract class Personnel {
	private boolean available = true;
	private int id;
	
	public Personnel(int id) {
		this.id = id;
	}
	
	/**
	 * @return Id of the personnel.
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * 
	 * @return If the personnel is available.
	 */
	public boolean isAvailable() {
		return available;
	}
	
	/**
	 * Makes the personnel busy.
	 */
	public void getBusy() {
		available = false;
	}
	
	/**
	 * Finishes the service and sets the personnel free.
	 */
	public void setFree() {
		available = true;
	}
	
	/**
	 * This method must be implemented by child classes.
	 * @param event The service (either {@link TrainingEvent}, {@link MassageEvent} or  {@link PhysiotherapyEvent}) assinged to the personnel.
	 */
	public abstract void assign(SportingEvent event);

}
