package examples;

import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ExhaustiveHyperHeuristic extends HyperHeuristic {
	static String filename;
	/**
	 * creates a new ExampleHyperHeuristic object with a random seed
	 */
	public ExhaustiveHyperHeuristic(long seed){
		super(seed);
//		initialize filename to use
	}
	
	
	/**
	 * This method defines the strategy of the hyper-heuristic
	 * @param problem the problem domain to be solved
	 */
	public void solve(ProblemDomain problem) {
		//it is often a good idea to record the number of low level heuristics, as this changes depending on the problem domain
		int number_of_heuristics = problem.getNumberOfHeuristics();


		//initialise the variable which keeps track of the current objective function value
		double current_obj_function_value = Double.POSITIVE_INFINITY;

		//initialise the solution at index 0 in the solution memory array
		problem.initialiseSolution(0);
		problem.setMemorySize(number_of_heuristics+1);
		//the main loop of any hyper-heuristic, which checks if the time limit has been reached
		long startTime = System.currentTimeMillis();

		int previous_heuristic = -1; // this will keep track of the previous heuristic id
		
		double[] scores = new double[number_of_heuristics];
		
		for(int j=0;j<100;j++) {
			double best_delta = Double.NEGATIVE_INFINITY;
			// heuristic ids start at 0
			int modify_state;
			if(previous_heuristic == -1) modify_state = 0;
			else {
				modify_state = rng.nextInt(number_of_heuristics); // chooses the state to modify
				current_obj_function_value = scores[modify_state]; // gets the obj value of the state to modify
			}
			ArrayList<Integer> bestHeuristics = new ArrayList<Integer>();
			for(int i=0;i<number_of_heuristics;i++) { // go through and apply each heuristic
				//this hyper-heuristic chooses a random low level heuristic to apply

				//apply the chosen heuristic to the solution at index 0 in the memory
				//the new solution is then stored at index i of the solution memory while we decide whether to accept it
				
				// line below is causing an ArrayIndexOutOfBoundsException
				double new_obj_function_value = problem.applyHeuristic(i, modify_state, i);
				scores[i] = new_obj_function_value;
				//calculate the change in fitness from the current solution to the new solution
				double delta = current_obj_function_value - new_obj_function_value;
	
				//all of the problem domains are implemented as minimisation problems. A lower fitness means a better solution.
				if (delta > best_delta) {
					bestHeuristics.clear();
					best_delta = delta;
					bestHeuristics.add(i);
				}
				else if (delta == best_delta) {
					bestHeuristics.add(i);
					}
				}
		
			int bestHeuristicToWrite = rng.nextInt(bestHeuristics.size());
			previous_heuristic = bestHeuristicToWrite;
			
			//one iteration has been completed, so we return to the start of the main loop and check if the time has expired 
		}
		hasTimeExpired();
		long endTime = System.currentTimeMillis();
		System.out.println("That took " + (endTime - startTime) + " milliseconds");
	}
	
	/**
	 * this method must be implemented, to provide a different name for each hyper-heuristic
	 * @return a string representing the name of the hyper-heuristic
	 */
	public String toString() {
		return "Example Hyper Heuristic Five";
	}
}