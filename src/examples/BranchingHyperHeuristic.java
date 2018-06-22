package examples;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;


/*
The goal of this class is to create a hyper heuristic which branches into 3 different paths at each level.
The heuristic chosen at each level will be completely random
After a specific number of levels, the heuristic will stop, and the sequence of heuristics will be written to a file, along with the objective score.
*/
public class BranchingHyperHeuristic extends HyperHeuristic {

	static int currentMemoryIndex = 0;
	static String filename;
	static int number_of_heuristics;
	static int depth;
	/**
	 * creates a new ExampleHyperHeuristic object with a random seed
	 */
	public BranchingHyperHeuristic(long seed, String name,int d){
		super(seed);
		filename = name;
		depth = d;
//		initialize filename to use
	}

	@SuppressWarnings("unused")
	private void ApplyHeuristicToProblem(ProblemDomain problem, int n, StringBuilder sb, PrintWriter pw, double score, int soluitionIndex) {
		int processors = Runtime.getRuntime().availableProcessors(); // figure out how to use this to create threads
		
		if(n==0) { // if we are 10 levels deep
			sb.append(score); // write the score at the end
			sb.append('\n');
			pw.write(sb.toString());
			return;
		}else {
		
		StringBuilder sb1= new StringBuilder(sb), sb2= new StringBuilder(sb), sb3 = new StringBuilder(sb);
		
		int randH1, randH2=-1, randH3=-1;
		
		double score1, score2, score3;
		randH1 = rng.nextInt(number_of_heuristics);
		while (randH1 == randH2 || randH2 == -1) {
			randH2 = rng.nextInt(number_of_heuristics);
		}
		while (randH1 == randH3 || randH2 == randH3|| randH3 == -1) {
			randH3 = rng.nextInt(number_of_heuristics);
		}
		int pos1 = currentMemoryIndex;
		currentMemoryIndex++;
		int pos2 = currentMemoryIndex;
		currentMemoryIndex++;
		int pos3 = currentMemoryIndex;
		currentMemoryIndex++;
		score1 = problem.applyHeuristic(randH1, soluitionIndex, pos1);
		score2 = problem.applyHeuristic(randH2, soluitionIndex, pos2);
		score3 = problem.applyHeuristic(randH3, soluitionIndex, pos3);
		sb1.append(randH1); sb1.append(',');
		sb2.append(randH2); sb2.append(',');
		sb3.append(randH3); sb3.append(',');
		ApplyHeuristicToProblem(problem, n-1, sb1, pw,score1,pos1);
		ApplyHeuristicToProblem(problem, n-1, sb2, pw,score2,pos2);
		ApplyHeuristicToProblem(problem, n-1, sb3, pw,score3,pos3);
		return;
		}
	}
	/**
	 * This method defines the strategy of the hyper-heuristic
	 * @param problem the problem domain to be solved
	 */
	public void solve(ProblemDomain problem) {

		//it is often a good idea to record the number of low level heuristics, as this changes depending on the problem domain
		number_of_heuristics = problem.getNumberOfHeuristics();

		//initialise the variable which keeps track of the current objective function value

		//initialise the solution at index 0 in the solution memory array
		problem.initialiseSolution(0);
		currentMemoryIndex++;
		problem.setMemorySize((int) Math.pow(3,depth+1)+1);
		//the main loop of any hyper-heuristic, which checks if the time limit has been reached
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new File(filename));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StringBuilder sb = new StringBuilder();
		ApplyHeuristicToProblem(problem,depth,sb,pw,Double.POSITIVE_INFINITY,0);
		pw.close();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
