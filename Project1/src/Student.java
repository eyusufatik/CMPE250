
public class Student implements Comparable<Student>{
	private int id;
	private String name;
	private int remainingSemesters;
	private float rating;
	/**
	 * Currently staying or stayed in a house before graduating.
	 */
	private boolean isStaying;
	
	/**
	 * If the student is graduated or not.
	 */
	private boolean graduated;
	
	public Student(int id, String name, int remainingSemesters, float rating) {
		this.id = id;
		this.name = name;
		this.remainingSemesters = remainingSemesters;
		this.rating = rating;
		this.isStaying = false;
		this.graduated = false;
	}
	
	public int getId() {
		return id;
	}
	
	/**
	 * Take the student to next semester. If the remaining semesters is 0 then the student must graduate.
	 * This is handled in main for this implementation.
	 */
	public void nextSemester() {
		if(remainingSemesters != 0)
			remainingSemesters--;
	}
	
	public int getRemainingSemesters() {
		return remainingSemesters;
	}
	
	/**
	 * 
	 * @return If the student is currently staying or stayed in a house before graduation.
	 */
	public boolean isStaying() {
		return isStaying;
	}
	
	/**
	 * Move in a house. Just makes Student.isStaying true.
	 * 
	 * @see Student#isStaying 
	 */
	public void moveIn() {
		isStaying = true;
	}
	
	public float getRating() {
		return rating;
	}
	
	/**
	 * Makes the student graduate. It will be excluded from the students collection in the implementation.
	 */
	public void graduate() {
		graduated = true;
	}
	
	/**
	 * @deprecated
	 * 
	 * @return !Student#graduate
	 */
	@Deprecated
	public boolean stillEnrolled() {
		return !graduated;
	}
	@Override
	public int compareTo(Student o) {
		// TODO Auto-generated method stub
		return id - o.getId();
	}
	
	public String toString() {
		return name;
	}
}
