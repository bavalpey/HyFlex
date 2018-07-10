package examples;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;
import PersonnelScheduling.PersonnelScheduling;

/**
 * This class shows how to run a selected hyper-heuristic on a selected problem domain.
 * It shows the minimum that must be done to test a hyper heuristic on a problem domain, and it is 
 * intended to be read before the ExampleRun2 class, which provides an example of a more complex set-up
 */
public class AllHeuristicsRun {

	public static void main(String[] args) {
		
		//create a ProblemDomain object with a seed for the random number generator
		ProblemDomain problem = new PersonnelScheduling(-1);
		int instances = problem.getNumberOfInstances();
		
		PrintWriter pw = null;
		try {
			pw = new PrintWriter("V1_Data/Heuristic_Results.csv");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		for(int i = 0; i<instances; i++) {
			StringBuilder sb = new StringBuilder();
			//creates an ExampleHyperHeuristic object with a seed for the random number generator
			HyperHeuristic learningHeuristic = new LearningHyperHeuristic(1234);
			HyperHeuristic randomHeuristic = new RandomHyperHeuristic(1234);
			HyperHeuristic onlineHeuristic = new OnlineHyperHeuristic(1234);
			HyperHeuristic exhaustiveHeuristic = new ExhaustiveHyperHeuristic(1234);
			//we must load an instance within the problem domain, in this case we choose instance 2
			problem.loadInstance(i);
	
			//we must set the time limit for the hyper-heuristic in milliseconds, in this example we set the time limit to 1 minute
			learningHeuristic.setTimeLimit(60000);
			randomHeuristic.setTimeLimit(60000);
			onlineHeuristic.setTimeLimit(60000);
			exhaustiveHeuristic.setTimeLimit(60000);
	
			//a key step is to assign the ProblemDomain object to the HyperHeuristic object. 
			//However, this should be done after the instance has been loaded, and after the time limit has been set
			learningHeuristic.loadProblemDomain(problem);
			randomHeuristic.loadProblemDomain(problem);
			onlineHeuristic.loadProblemDomain(problem);
			exhaustiveHeuristic.loadProblemDomain(problem);
			
			
			//now that all of the parameters have been loaded, the run method can be called.
			//this method starts the timer, and then calls the solve() method of the hyper_heuristic_object.
			learningHeuristic.run();
			sb.append("i" + i + "," + "learning" + "," + learningHeuristic.getBestSolutionValue() + "," + learningHeuristic.getElapsedTime());
	
			//obtain the best solution found within the time limit
			pw.append(sb.toString());
		}
		
	}
}
