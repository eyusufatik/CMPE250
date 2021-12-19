/**
 * Players wait in queue and get services.
 *
 */
public class Player {
	private int id;
	private int skillLevel;
	private int massagesTaken;
	private double lastTrainingDuration;
	private boolean free;
	private boolean inQueue;
	private double trainingQueueEnteranceTime;
	private double sumWaitingTimeInPhysiotherapyQueue;
	private double sumWaitingTimeInMassageQueue;
	
	/**
	 * 
	 * @param id Id of the player.
	 * @param skillLevel Skill level of the player.
	 */
	public Player(int id, int skillLevel) {
		this.id = id;
		this.skillLevel = skillLevel;
		this.massagesTaken = 0;
		this.free = true;
		this.trainingQueueEnteranceTime = 0;
		this.sumWaitingTimeInPhysiotherapyQueue = 0;
		this.inQueue = false;
	}
	
	/**
	 * 
	 * @return Id of the player.
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * 
	 * @return Skill level of the player 
	 */
	public int getSkillLevel() {
		return skillLevel;
	}

	/**
	 * 
	 * @return Duration of the last training the player attended.
	 */
	public double getLastTrainingDuration() {
		return lastTrainingDuration;
	}
	
	/**
	 * Sets the duration of the training the player is leaving.
	 * @param lastTrainingTime Duration of the training.
	 */
	public void setLastTrainingDuration(double lastTrainingTime) {
		this.lastTrainingDuration = lastTrainingTime;
	}
	
	/**
	 * 
	 * @return If the player is currently in a service or not.
	 */
	public boolean isFree() {
		return free;
	}
	
	/**
	 * Makes the player not free.
	 * @see Player#isFree
	 */
	public void getBusy() {
		free = false;
	}
	
	/**
	 * Gets the player in a queue.
	 * @see Player#inQueue
	 */
	public void getInQueue() {
		inQueue = true;
	}
	
	/**
	 * Gets the player out of a queue.
	 */
	public void getOutOfQueue() {
		inQueue = false;
	}
	
	/**
	 * 
	 * @return If the player is in a queue or not.
	 */
	public boolean inQueue() {
		return inQueue;
	}
	
	/**
	 * Sets the player free.
	 * @see Player#isFree
	 */
	public void setFree() {
		free = true;
	}
	
	/**
	 * 
	 * @return The number of massages had by the player.
	 */
	public int getMassagesTaken() {
		return massagesTaken;
	}
	
	/**
	 * Increments the number of massages a player got by one.
	 */
	public void takeMassage() {
		massagesTaken++;
	}
	
	/**
	 * Starts the timer for the turnaround time.
	 * @param time Start of the turnaround.
	 */
	public void startTurnaroundTimer(double time) {
		trainingQueueEnteranceTime = time;
	}
	
	/**
	 * Stops the current turnaround time for the player.
	 * @param time Stop time of the turnaround
	 * @return Turnaround time.
	 */
	public double stopTurnaroundTime(double time) {
		return time - trainingQueueEnteranceTime;
	}
	
	/**
	 * Increments the total waiting time in the physiotherapy queue.
	 * @param duration Duration of the waiting.
	 */
	public void waitedInPhysiotherapyQueueFor(double duration) {
		sumWaitingTimeInPhysiotherapyQueue += duration;
	}
	
	/**
	 * 
	 * @return Sum of waiting times in the physiotherpy queue.
	 */
	public double getTotalWaitingTimeInPhysiotherapyQueue() {
		return sumWaitingTimeInPhysiotherapyQueue;
	}
	
	/**
	 * Increments the total waiting time in the massage queue.
	 * @param duration Duration of the waiting.
	 */
	public void waitedInMassageQueueFor(double duration) {
		sumWaitingTimeInMassageQueue += duration;
	}
	
	/**
	 * 
	 * @return Sum of waiting times in the massage queue.
	 */
	public double getTotalWaitingTimeInMassageQueue() {
		return sumWaitingTimeInMassageQueue;
	}
}
