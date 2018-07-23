package examples;

import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;

public class RandomHyperHeuristic extends HyperHeuristic {
	
	/**
	 * creates a new ExampleHyperHeuristic object with a random seed
	 */
	public RandomHyperHeuristic(long seed){
		super(seed);
	}
	
	/**
	 * This method defines the strategy of the hyper-heuristic
	 * @param problem the problem domain to be solved
	 */
	public void solve(ProblemDomain problem) {

		//it is often a good idea to record the number of low level heuristics, as this changes depending on the problem domain
		int number_of_heuristics = problem.getNumberOfHeuristics();

		//initialise the variable which keeps track of the current objective function value

		//initialise the solution at index 0 in the solution memory array
		problem.initialiseSolution(0);
		int count = 0;
		//the main loop of any hyper-heuristic, which checks if the time limit has been reached
		while (count != 5) {

			//this hyper-heuristic chooses a random low level heuristic to apply
			int heuristic_to_apply = rng.nextInt(number_of_heuristics);
			while(heuristic_to_apply == 8 || heuristic_to_apply == 9 || heuristic_to_apply == 10) {
				heuristic_to_apply = rng.nextInt(number_of_heuristics);
			}
			problem.applyHeuristic(heuristic_to_apply, 0, 1);

			problem.copySolution(1, 0);
			count++;
			//one iteration has been completed, so we return to the start of the main loop and check if the time has expired 
		}
		hasTimeExpired();
	}
	
	/**
	 * this method must be implemented, to provide a different name for each hyper-heuristic
	 * @return a string representing the name of the hyper-heuristic
	 */
	public String toString() {
		return "Example Hyper Heuristic One";
	}
}
