package examples;

import AbstractClasses.ProblemDomain;

public abstract class ClonableProblemDomain extends AbstractClasses.ProblemDomain implements Cloneable{

	
	public ClonableProblemDomain(long seed) {
		super(seed);
	}

	public Object clone() throws CloneNotSupportedException{  
	    return super.clone();
	}
}