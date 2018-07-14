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
			pw = new PrintWriter("V1_Data/Heuristic_Results5levels.csv");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		for(int i = 0; i<instances; i++) {
			StringBuilder sb = new StringBuilder();
			//creates an ExampleHyperHeuristic object with a seed for the random number generator
			HyperHeuristic learningHeuristic = new LearningHyperHeuristic(1234);
			problem.loadInstance(i);
			learningHeuristic.setTimeLimit(60000);
			learningHeuristic.loadProblemDomain(problem);
			learningHeuristic.run();
			sb.append("i" + i + "," + "learning" + "," + learningHeuristic.getBestSolutionValue() + "," + learningHeuristic.getElapsedTime()+"\n");
			learningHeuristic = null;
			
			
			HyperHeuristic randomHeuristic = new RandomHyperHeuristic(1234);
			problem.loadInstance(i);
			randomHeuristic.setTimeLimit(60000);
			randomHeuristic.loadProblemDomain(problem);
			randomHeuristic.run();
			sb.append("i" + i + "," + "random" + "," + randomHeuristic.getBestSolutionValue() + "," + randomHeuristic.getElapsedTime()+"\n");
			randomHeuristic = null;
			
			HyperHeuristic onlineHeuristic = new OnlineHyperHeuristic(1234);
			problem.loadInstance(i);
			onlineHeuristic.setTimeLimit(60000);
			onlineHeuristic.loadProblemDomain(problem);
			onlineHeuristic.run();
			sb.append("i" + i + "," + "online" + "," + onlineHeuristic.getBestSolutionValue() + "," + onlineHeuristic.getElapsedTime()+"\n");
			onlineHeuristic = null;
			
			HyperHeuristic exhaustiveHeuristic = new ExhaustiveHyperHeuristic(1234);
			problem.loadInstance(i);
			exhaustiveHeuristic.setTimeLimit(60000);
			exhaustiveHeuristic.loadProblemDomain(problem);
			exhaustiveHeuristic.run();
			sb.append("i" + i + "," + "exhaustive" + "," +exhaustiveHeuristic.getBestSolutionValue() + "," + exhaustiveHeuristic.getElapsedTime()+"\n");
			exhaustiveHeuristic = null;
			//a key step is to assign the ProblemDomain object to the HyperHeuristic object. 
			//However, this should be done after the instance has been loaded, and after the time limit has been set
			
			pw.append(sb.toString());
			
			
		}
		pw.close();
		
	}
}
