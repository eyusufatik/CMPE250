import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.TreeSet;


public class project1Main {
	
	private static int graduated = 0;
	private static ArrayList<Student> graduatedWithoutStayingStudents = new ArrayList<Student>();
	
	/**
	 * Takes to houses and the students to the next semester.
	 * <br />
	 * <br />
	 * If remaining semesters of a student is 0 it is removed fromt the students set, and if {@link Student#isStaying()} is false then this student is added to the {@link project1Main#graduatedWithoutStayingStudents} set. 
	 * @param houses Full houses set.
	 * @param students Non-staying students set.
	 */
	public static void nextSemester(LinkedList<House> fullHouses, TreeSet<Student> students) {
		for(House h: fullHouses) {
			h.nextSemester();
		}
		for(Iterator<Student> it = students.iterator(); it.hasNext(); ) {
			Student s = it.next();
			s.nextSemester();
			if(s.getRemainingSemesters() == 0) {
				s.graduate();
				it.remove();
				graduatedWithoutStayingStudents.add(s);
			}	
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		Scanner in = new Scanner(new File(args[0]));
		PrintStream out = new PrintStream(new File(args[1]));
		
		TreeSet<House> houses = new TreeSet<House>(); // will hold empty houses.
		LinkedList<House> fullHouses = new LinkedList<House>(); // will hold full houses.
		
		TreeSet<Student> students = new TreeSet<Student>(); // will hold students looking for a house.
		
		/**
		 * Read the input file line by line.
		 * Split the line by the spaces.
		 * Parse the result.
		 * 
		 * If a house is empty add it to the houses set.
		 * Else add it to the fullHouses set.
		 *
		 * Add students to the students set.
		 */
		while(in.hasNextLine()) {
			String line = in.nextLine();
			String tokens[] = line.split(" ");
			if(tokens[0].equals("h")) {
				int id = Integer.parseInt(tokens[1]);
				int duration = Integer.parseInt(tokens[2]);
				float rating = Float.parseFloat(tokens[3]);
				House h = new House(id, duration, rating);
				if(duration == 0) {
					houses.add(h);
				}else {
					fullHouses.add(h);
				}
			}else if(tokens[0].equals("s")) {
				int id = Integer.parseInt(tokens[1]);
				String name = tokens[2];
				int duration = Integer.parseInt(tokens[3]);
				float rating = Float.parseFloat(tokens[4]);
				Student s = new Student(id, name, duration, rating);
				students.add(s);
			}
		}
		
		/**
		 * Until all the students graduate.
		 */
		while(!students.isEmpty()) {
			Iterator<Student> student_it = students.iterator();
			// while there are more students and there still are empty houses
			while(student_it.hasNext() && !houses.isEmpty()) {
				Student s = student_it.next();
				if(s.getRemainingSemesters() > 0) {
					Iterator<House> house_it = houses.iterator();
					while(house_it.hasNext()) {
						House h = house_it.next();
						if(h.getRating() >= s.getRating()) {
							h.occupy(s); // first fill the house.
							s.moveIn(); // get the student object to the staying state.
							student_it.remove(); // remove the students since we won't do anything about it anymore
							house_it.remove(); // remove the house from the set we are iterating until it is free again.
							fullHouses.add(h);
							break;
						}
					}
				}
			}
			
			nextSemester(fullHouses, students);
			
			// After shifting to the next semester, check if any full houses got free.
			for(Iterator<House> it = fullHouses.iterator(); it.hasNext(); ) {
				House h = it.next();

				if(h.isFree()) {
					it.remove();
					houses.add(h); // add the house to the empty house set again.
				}
			}
		}
		
		// sort the students that graduated without ever staying in a house.
		Collections.sort(graduatedWithoutStayingStudents);
		
		// print them out.
		for(Student s: graduatedWithoutStayingStudents)
			out.println(s);
		
	}
	

}
