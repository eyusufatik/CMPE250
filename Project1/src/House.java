
public class House implements Comparable<House>{
	
	private int id;
	
	/**
	 * Number of semesters the house is occupied for.
	 */
	private int occupiedSemesters;
	
	private float rating;
	
	public House(int id, int occupiedSemesters, float rating) {
		this.id = id;
		this.occupiedSemesters = occupiedSemesters;
		this.rating = rating;
	}
	
	public int getId() {
		return id;
	}
	/**
	 * Take the house to the next semester. 0 occupied semesters mean the house is free.
	 */
	public void nextSemester() {
		if(occupiedSemesters != 0)
			occupiedSemesters--;
	}
	
	public boolean isFree() {
		return occupiedSemesters == 0;
	}
	
	public float getRating() {
		return rating;
	}
	
	/**
	 * Get occupied by a student. Moves student in. See: {@link Student#moveIn}
	 * @param s Student moving in.
	 */
	public void occupy(Student s) {
		occupiedSemesters = s.getRemainingSemesters();
		s.moveIn();
	}
	
	@Override
	public int compareTo(House o) {
		// Sort by id
		return id - o.getId();
	}
	
	/**
	 * Used for debug purposes.
	 * 
	 * @return Id of the house as a String.
	 */
	public String toString() {
		return Integer.toString(id);
	}
}
