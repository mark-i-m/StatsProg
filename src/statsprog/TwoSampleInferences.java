package statsprog;

/**
 *
 * @author Mark
 */

/**
 * Represents a distribution for running tests with two-sample
 * student-t distributions
 */
public class TwoSampleInferences extends TwoSampleDistribution {
    
	/**
	 * Create a distribution with these statistics:
	 * @param n1 	the first sample size
	 * @param n2 	the second sample size
	 * @param sd1 	the first sample sd
	 * @param sd2	the second sample sd
	 * @param xbar1	the first sample mean	
	 * @param xbar2	the second sample mean
	 */
    public TwoSampleInferences(int n1, int n2, double sd1, double sd2, double xbar1, double xbar2){
        
        super(n1,n2,sd1,sd2,xbar1,xbar2);
        
    }
    
    /**
     * Create a distribution with these samples
     * @param s1 the first sample
     * @param s2 the second sample
     */
    public TwoSampleInferences(Sample s1, Sample s2){
        
        super(s1, s2);
        
    }
    
    /**
     * Calculates a two-sample-mean student-t interval with
     * confidence level cl
     * @param cl the confidence level
     * @return the lower and upper bounds of the confidence
     * interval, respectively as the first and second
     * elements of a two-element array
     */
    public double[] twoSampTInt (double cl){
        
        StudentTModel s = new StudentTModel(df);
        
        double tstar = s.invT(cl + (1.0 - cl) / 2);
        
        double[] CI = new double[2];
        CI[0] = (mean1 - mean2) - tstar * se;
        CI[1] = (mean1 - mean2) + tstar * se;
        
        return CI;
        
    }
    
    /**
     * Calculates a two-sample-mean student-t interval with
     * confidence level cl, using pooled std error.
     * @param cl the confidence level
     * @return the lower and upper bounds of the confidence
     * interval, respectively as the first and second
     * elements of a two-element array
     */
    public double[] twoSampPooledTInt (double cl){
        
        StudentTModel s = new StudentTModel(n1 + n2 - 2);
        
        double tstar = s.invT(cl + (1.0 - cl) / 2);
        
        double[] CI = new double[2];
        CI[0] = (mean1 - mean2) - tstar * SEp;
        CI[1] = (mean1 - mean2) + tstar * SEp;
        
        return CI;
        
    }
    
    /**
     * Calculates the p-value for the result of a hypothesis
     * test, with mnaught as the null hypothesis and altH as
     * the alternate hyp.
     * @param munaught the hypothesis
     * @param altH the alternate hypothesis
     * @return the p-value
     */
    public double twoSampTTest (double munaught, StudentTModel.AlternateHypothesis altH){
    	
        StudentTModel s = new StudentTModel(df);
        
        double t = ((mean1 - mean2) - munaught) / se;
        
        switch(altH){
            case MNOTEQ: //m != mnaught
                return 2 * s.tcdf((t < 0) ? -10 : t, (t < 0) ? t : 10);
                
            case MLESS: //m < mnaught
                return s.tcdf(-9, t);
                
            case MGREATER: //m > mnaught
                return s.tcdf(t, 9);
        }
        
        throw new IllegalArgumentException("altH invalid");
        
    }
    
    /**
     * Calculates the p-value for the result of a hypothesis
     * test, with mnaught as the null hypothesis and altH as
     * the alternate hyp, using pooled std. error.
     * @param munaught the hypothesis
     * @param altH the alternate hypothesis
     * @return the p-value
     */
    public double twoSampPooledTTest (double munaught, StudentTModel.AlternateHypothesis altH){

    	StudentTModel s = new StudentTModel(n1 + n2 - 2);
        
        double t = ((mean1 - mean2) - munaught) / SEp;
        
        switch(altH){
            case MNOTEQ: //m != mnaught
                return 2 * s.tcdf((t < 0) ? -10 : t, (t < 0) ? t : 10);
                
            case MLESS: //m < mnaught
                return s.tcdf(-9, t);
                
            case MGREATER: //m > mnaught
                return s.tcdf(t, 9);
        }
        
        throw new IllegalArgumentException("altH invalid");
        
    }
    
}
