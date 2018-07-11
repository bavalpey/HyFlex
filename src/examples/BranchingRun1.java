package examples;
import java.io.File;


import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;
import PersonnelScheduling.PersonnelScheduling;
import java.util.Random;

/**
 * This class shows how to run a selected hyper-heuristic on a selected problem domain.
 * It shows the minimum that must be done to test a hyper heuristic on a problem domain, and it is 
 * intended to be read before the ExampleRun2 class, which provides an example of a more complex set-up
 */
public class BranchingRun1 {
	

	public static void main(String[] args) {

		String filename = "V1_data/branch";
		int filecounter = 1;
		//create a ProblemDomain object with a seed for the random number generator
		for(int ct = 0;ct<50;ct++) {
			ProblemDomain problem = new PersonnelScheduling(1234);
			Random rand = new Random();
			while(new File(filename+filecounter+".csv").isFile()) {
				filecounter++;
			}
			filename = filename + filecounter + ".csv";
			//creates an ExampleHyperHeuristic object with a seed for the random number generator
	
	
			
			int  n = rand.nextInt(999) + 1;
			HyperHeuristic hyper_heuristic_object = new BranchingHyperHeuristic4(n,filename,5);
	
			//we must load an instance within the problem domain, in this case we choose instance 2
			problem.loadInstance(9);
	
			//we must set the time limit for the hyper-heuristic in milliseconds, in this example we set the time limit to 1 minute
			hyper_heuristic_object.setTimeLimit(60000);
	
			//a key step is to assign the ProblemDomain object to the HyperHeuristic object. 
			//However, this should be done after the instance has been loaded, and after the time limit has been set
			hyper_heuristic_object.loadProblemDomain(problem);
	
			//now that all of the parameters have been loaded, the run method can be called.
			//this method starts the timer, and then calls the solve() method of the hyper_heuristic_object.
			hyper_heuristic_object.run();
	
			//obtain the best solution found within the time limit
	//		System.out.println(hyper_heuristic_object.getBestSolutionValue());
		}
	}
}