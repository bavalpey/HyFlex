package examples;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;

public class LearningHyperHeuristic extends HyperHeuristic {


	public LearningHyperHeuristic(long seed) {
		super(seed);
		// TODO Auto-generated constructor stub
		
		
		
	}

	@SuppressWarnings("unused")
	@Override
	protected void solve(ProblemDomain problem) {
		// TODO Auto-generated method stub
		int number_of_heuristics = problem.getNumberOfHeuristics();
		double current_obj_function_value = Double.POSITIVE_INFINITY;
		problem.initialiseSolution(0);
		problem.setMemorySize(5);
		PrintWriter pw;
		try {
			pw = new PrintWriter(new File("V1_data/argsFile.txt"));
		
			StringBuilder sb = new StringBuilder();
			
			
			
			int count = 0;
			while(count < 3) {
				int heuristic_to_apply = rng.nextInt(number_of_heuristics);
				double new_obj_function_value = problem.applyHeuristic(heuristic_to_apply, 0, 1);
				double delta = current_obj_function_value - new_obj_function_value;
				
				if (delta > 0) {
					problem.copySolution(1, 0);
					current_obj_function_value = new_obj_function_value;
					count++;
					sb.append(heuristic_to_apply + ",");
				} else {
					if (rng.nextBoolean()) {
						problem.copySolution(1, 0);
						current_obj_function_value = new_obj_function_value;
						count++;
						sb.append(heuristic_to_apply + ",");
						
					}
				}
			}
			
			pw.write(sb.toString());
			pw.close();
			try {
				Process p = Runtime.getRuntime()
						.exec("python \"C:\\Users\\Lab\\Documents\\Git\\HyFlex\\Scripts\\top3selector.py"); // runs the Python code located at the file path
		        p.waitFor();
		        p.destroy();
		        
			} catch (IOException e) {
				System.out.println("Python File not located");
				e.printStackTrace();
				
			} catch (InterruptedException e) {
				System.out.println("Python Process was interrupted");
				e.printStackTrace();
			}
		
			
			BufferedReader fr = new BufferedReader(new FileReader("V1_data/top3.txt"));
			String[] seq1 = fr.readLine().split(","); 
			String[] seq2 = fr.readLine().split(","); 
			String[] seq3 = fr.readLine().split(",");
			
			problem.copySolution(0, 2);
			problem.copySolution(0, 3);
			problem.copySolution(0, 4);
			
			problem.applyHeuristic(Integer.valueOf(seq1[0]), 2, 2);
			problem.applyHeuristic(Integer.valueOf(seq1[1]), 2, 2);
			
			problem.applyHeuristic(Integer.valueOf(seq2[0]), 3, 3);
			problem.applyHeuristic(Integer.valueOf(seq2[1]), 3, 3);
			
			problem.applyHeuristic(Integer.valueOf(seq3[0]), 4, 4);
			problem.applyHeuristic(Integer.valueOf(seq3[1]), 4, 4);
			fr.close();
			hasTimeExpired();
			
			
			
			/*Add in the next two heuristic choices from 7top3selector.py */
			
			
			
			
		} catch (FileNotFoundException e1) {
			System.out.println("File Read Error");
			e1.printStackTrace();
		} catch (IOException e) {
			System.out.println("File Read Error");
			e.printStackTrace();
		}
		
		
		
		
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
