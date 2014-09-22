package statsprog;

/**
 *
 * @author Mark
 */

/**
 * Represents a distribution using the student t model,
 * for running tests
 */
public class OneSampleInferences extends OneSampleDistribution {
    
	/**
	 * Creates a new distribution with these parameters
	 * @param n the sample size
	 * @param sd the data std dev
	 * @param xbar the sample mean
	 */
    public OneSampleInferences(int n, double sd, double xbar){
        
        super(n,sd,xbar);
        
    }
    
    /**
     * Create the sampling distribution from a sample
     */
    public OneSampleInferences(Sample s){
        
        super(s);
        
    }
    
    /**
     * Calculate the one-sample t-interval with 
     * confidence level cl
     * @param cl the confidence level
     * @return the lower and upper bounds of the
     * interval, respectively, in a two-element
     * array 
     */
    public double[] oneSampTInt (double cl){
        
        StudentTModel s = new StudentTModel(n - 1);
        
        double tstar = s.invT(cl + (1.0 - cl) / 2);
        
        double[] CI = new double[2];
        CI[0] = mean - tstar * se;
        CI[1] = mean + tstar * se;
        
        return CI;
        
    }
    
    /**
     * Calculates the p-value resulting from the hypothesis test
     * @param munaught the hypothesis
     * @param altH the alternate hypothesis; one of the values
     * of the AlternateHypothesis enum
     * @return the p-value
     */
    public double oneSampTTest (double munaught, StudentTModel.AlternateHypothesis altH){
        
        StudentTModel s = new StudentTModel(n - 1);
        
        double t = (mean - munaught) / se;
        
        switch(altH){
            case MNOTEQ: //p != pnaught
                return 2 * s.tcdf((t < 0) ? -10 : t, (t < 0) ? t : 10);
                
            case MLESS: //p < pnaught
                return s.tcdf(-9, t);
                
            case MGREATER: //p > pnaught
                return s.tcdf(t, 9);
        }
        
        throw new IllegalArgumentException("altH invalid");
        
    }
    
}
