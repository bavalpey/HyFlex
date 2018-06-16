package examples;

import AbstractClasses.ProblemDomain;
import PersonnelScheduling.PersonnelScheduling;

public class ExampleRun3 {

	public static void main(String[] args) {
		ProblemDomain schedule = new PersonnelScheduling(198);
		System.out.print("Number of heuristics is: " + schedule.getNumberOfHeuristics());
		System.out.print("Number of instances is: " + schedule.getNumberOfInstances());
		
		
	}

}
