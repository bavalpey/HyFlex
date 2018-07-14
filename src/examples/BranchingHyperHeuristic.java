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
	static ProblemDomain problem;
	int currentMemoryIndex = 0;
	String filename;
	static int number_of_heuristics;
	static int depth;
	PrintWriter pw = null;
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
	private void ApplyHeuristicToProblem(int n, StringBuilder sb, double score, int solutionIndex) {
		
		if(n==0) { // if we are 10 levels deep
			sb.append(score); // write the score at the end
			sb.append('\n');
			pw.write(sb.toString());
			sb = null;
			return;
		}else {
//		sb.append(score+','); // uncomment this to keep track of the score at each level
		StringBuilder sb1= new StringBuilder(sb), sb2= new StringBuilder(sb), sb3 = new StringBuilder(sb);
		sb = null; // try to deallocate this stringBuilder to free up space for the heap
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
		score1 = problem.applyHeuristic(randH1, solutionIndex, pos1);
		score2 = problem.applyHeuristic(randH2, solutionIndex, pos2);
		score3 = problem.applyHeuristic(randH3, solutionIndex, pos3);

		sb1.append(randH1); sb1.append(',');
		sb2.append(randH2); sb2.append(',');
		sb3.append(randH3); sb3.append(',');
		try {
			ApplyHeuristicToProblem(n-1, sb1,score1,pos1);
		} catch (OutOfMemoryError e){
			System.out.println("Current memory index: " + (currentMemoryIndex-2));
			System.out.println("n is: " + n);
			
			} catch(ArrayIndexOutOfBoundsException z) {problem.setMemorySize((int) Math.pow(3+1, depth)+1);}
		try {
			ApplyHeuristicToProblem(n-1, sb2,score2,pos2);
		} catch (OutOfMemoryError e){
			System.out.println("Current memory index: " + (currentMemoryIndex-1));
			System.out.println("n is: " + n);
			} catch(ArrayIndexOutOfBoundsException z) {problem.setMemorySize((int) Math.pow(3+1, depth)+1);}
		try {
			ApplyHeuristicToProblem(n-1, sb3,score3,pos3);
		} catch (OutOfMemoryError e){
			System.out.println("Current memory index: " + (currentMemoryIndex));
			System.out.println("n is: " + n);
			} catch(ArrayIndexOutOfBoundsException z) {problem.setMemorySize((int) Math.pow(3+1, depth)+1);}
		
		
		return;
		}
	}
	/**
	 * This method defines the strategy of the hyper-heuristic
	 * @param problem1 the problem domain to be solved
	 */
	public void solve(ProblemDomain problem1) {
		problem = problem1;
		//it is often a good idea to record the number of low level heuristics, as this changes depending on the problem domain
		number_of_heuristics = problem.getNumberOfHeuristics();
		
		//Initialize the solution at index 0 in the solution memory array
		problem.initialiseSolution(0);
		problem.setMemorySize((int) Math.pow(3, depth)+8);
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
