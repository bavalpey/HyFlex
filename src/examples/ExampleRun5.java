package examples;
import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;
import PersonnelScheduling.PersonnelScheduling;

/**
 * This class shows how to run a selected hyper-heuristic on a selected problem domain.
 * It shows the minimum that must be done to test a hyper heuristic on a problem domain, and it is 
 * intended to be read before the ExampleRun2 class, which provides an example of a more complex set-up
 */
public class ExampleRun5 {

	public static void main(String[] args) {

		//create a ProblemDomain object with a seed for the random number generator
		ProblemDomain problem = new PersonnelScheduling(-1);
		
		int instances = problem.getNumberOfInstances();
		for(int i=0;i<instances;i++) {
			String filename = "instance" + i + ".csv";
			//creates an ExampleHyperHeuristic object with a seed for the random number generator
			HyperHeuristic hyper_heuristic_object = new ExampleHyperHeuristic5(-1,filename);
	
			//we must load an instance within the problem domain, in this case we choose instance 2
			problem.loadInstance(i);
	
			//we must set the time limit for the hyper-heuristic in milliseconds, in this example we set the time limit to 1 minute
			hyper_heuristic_object.setTimeLimit(60000);
	
			//a key step is to assign the ProblemDomain object to the HyperHeuristic object. 
			//However, this should be done after the instance has been loaded, and after the time limit has been set
			hyper_heuristic_object.loadProblemDomain(problem);
	
			//now that all of the parameters have been loaded, the run method can be called.
			//this method starts the timer, and then calls the solve() method of the hyper_heuristic_object.
			hyper_heuristic_object.run();
	
			//obtain the best solution found within the time limit
			System.out.println(hyper_heuristic_object.getBestSolutionValue());
		}
		
	}
}
