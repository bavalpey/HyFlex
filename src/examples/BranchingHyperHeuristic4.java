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
public class BranchingHyperHeuristic4 extends HyperHeuristic {
	static ProblemDomain problem;
	int currentMemoryIndex = 0;
	String filename;
	static int number_of_heuristics;
	static int depth;
	PrintWriter pw = null;
	/**
	 * creates a new ExampleHyperHeuristic object with a random seed
	 */
	public BranchingHyperHeuristic4(long seed, String name,int d){
		super(seed);
		filename = name;
		depth = d;
//		initialize filename to use
	}

	@SuppressWarnings("unused")
	private void ApplyHeuristicToProblem(int n, StringBuilder sb,double score, int solutionIndex) throws ArrayIndexOutOfBoundsException{
		
		if(n==0) { // if we are 10 levels deep
			sb.append(score); // write the score at the end
			sb.append('\n');
			pw.write(sb.toString());
			
			return;
		}else {
//		sb.append(score+','); // uncomment this to keep track of the score at each level
		StringBuilder sb1= new StringBuilder(sb), sb2= new StringBuilder(sb), sb3 = new StringBuilder(sb), sb4 = new StringBuilder(sb);
		
		int randH1, randH2=-1, randH3=-1, randH4=-1;
		
		double score1, score2, score3, score4;
		randH1 = rng.nextInt(number_of_heuristics);
		while(randH1 == 8 || randH1 == 9 || randH1 == 10) {
			randH1 = rng.nextInt(number_of_heuristics);
		}
		
		
		while (randH1 == randH2 || randH2 == -1 || randH2 == 8 || randH2 == 9 || randH2 == 10) {
			randH2 = rng.nextInt(number_of_heuristics);
		}
		while (randH1 == randH3 || randH2 == randH3|| randH3 == -1 || randH3 == 8 || randH3 == 9 || randH3 == 10) {
			randH3 = rng.nextInt(number_of_heuristics);
		}
		while (randH1 == randH4 || randH2 == randH4|| randH4 == -1 || randH3 == randH4 || randH4 == 8 || randH4 == 9 || randH4 == 10) {
			randH4 = rng.nextInt(number_of_heuristics);
		}
		int pos1 = currentMemoryIndex;
		currentMemoryIndex++;
		int pos2 = currentMemoryIndex;
		currentMemoryIndex++;
		int pos3 = currentMemoryIndex;
		currentMemoryIndex++;
		int pos4 = currentMemoryIndex;
		currentMemoryIndex++;
		score1 = problem.applyHeuristic(randH1, solutionIndex, pos1);
		score2 = problem.applyHeuristic(randH2, solutionIndex, pos2);
		score3 = problem.applyHeuristic(randH3, solutionIndex, pos3);
		score4 = problem.applyHeuristic(randH4, solutionIndex, pos4);
		sb1.append(randH1); sb1.append(',');
		sb2.append(randH2); sb2.append(',');
		sb3.append(randH3); sb3.append(',');
		sb4.append(randH4); sb4.append(',');
		try {
			ApplyHeuristicToProblem(n-1, sb1,score1,pos1);
		} catch (OutOfMemoryError e){
			System.out.println("Current memory index: " + (currentMemoryIndex-3));
			System.out.println("n is: " + n);
			}
		try {
			ApplyHeuristicToProblem(n-1, sb2,score2,pos2);
		} catch (OutOfMemoryError e){
			System.out.println("Current memory index: " + (currentMemoryIndex-2));
			System.out.println("n is: " + n);
			}
		try {
			ApplyHeuristicToProblem(n-1, sb3,score3,pos3);
		} catch (OutOfMemoryError e){
			System.out.println("Current memory index: " + (currentMemoryIndex-1));
			System.out.println("n is: " + n);
			}
		try {
			ApplyHeuristicToProblem(n-1, sb4,score4,pos4);
		} catch (OutOfMemoryError e){
			System.out.println("Current memory index: " + (currentMemoryIndex));
			System.out.println("n is: " + n);
			}
		
		
		return;
		}
	}
	/**
	 * This method defines the strategy of the hyper-heuristic
	 * @param problem1 the problem domain to be solved
	 */
	public void solve(ProblemDomain problem1) throws ArrayIndexOutOfBoundsException{
		problem = problem1;
		//it is often a good idea to record the number of low level heuristics, as this changes depending on the problem domain
		number_of_heuristics = problem.getNumberOfHeuristics();
		
		//initialise the solution at index 0 in the solution memory array
		problem.initialiseSolution(0);
		problem.setMemorySize((int) Math.pow(4, depth+1)+1);
		currentMemoryIndex++;
		
		//the main loop of any hyper-heuristic, which checks if the time limit has been reached
		try {
			pw = new PrintWriter(new File(filename));
			StringBuilder header = new StringBuilder();
			for(int i=0;i<depth;i++) {
				header.append("h"+i+",");
//				header.append('s'+i+','); // uncomment this to keep keep track of the score at each level
			}
			header.append("Final Score\n");
			pw.write(header.toString());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StringBuilder sb = new StringBuilder();
		ApplyHeuristicToProblem(depth,sb,Double.POSITIVE_INFINITY,0);
		pw.close();
		hasTimeExpired();
	}

	
	/*To get around the memory problem, only start expanding solutions after a certain point.  In other words,
	 * first choose 3-5 heuristics, and then start randomly choosing heuristics based off that.*/
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
