package examples;
//TODO: write exhaustive branch search
import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;

public class OnlineHeuristic extends HyperHeuristic{
	private int[] sumArray(int[] array) {
		int[] to_return = {0,0,0,0,0,0,0,0,0,0,0,0};
		int sum = 0;
		for(int x = 0; x <= 11; x++) {
			sum += array[x];
			to_return[x] = sum;
		}
		return to_return;
	}
	
	private int getHeuristicFromRandomNumber(int randomNumber, int[] points) {
		int[] sumArray = sumArray(points);
		
		if(randomNumber < 10+points[0]) {
			return 0;
		}
		for(int i = 1; i<=11;i++) {
			
			if(randomNumber < (10*(i+1)+sumArray[i])) {
				return i;
			}
		}
		return -1;
	}
	
	public OnlineHeuristic(long seed){
		super(seed);
	}
	
	public void solve(ProblemDomain problem) {
		int number_of_heuristics = problem.getNumberOfHeuristics();
		int cap = 10*number_of_heuristics;
		int[] h = {0,0,0,0,0,0,0,0,0,0,0,0};
		double current_obj_function_value = Double.POSITIVE_INFINITY;
		problem.initialiseSolution(0);
		
		while (!hasTimeExpired()) {
			int heuristic_to_apply = getHeuristicFromRandomNumber(rng.nextInt(cap),h);
			double new_obj_function_value = problem.applyHeuristic(heuristic_to_apply, 0, 1);
			
			double delta = current_obj_function_value - new_obj_function_value;
			
			if (delta > 0) {
				h[heuristic_to_apply]++;
				cap++;
				problem.copySolution(1, 0);
				current_obj_function_value = new_obj_function_value;
			} else {
				if (rng.nextBoolean()) {
					problem.copySolution(1, 0);
					current_obj_function_value = new_obj_function_value;
				}
			}
		}
	}
	
	public String toString() {
		return "Online Heuristic with Probability Vector";
	}
	
}