package statsprog;

/**
 *
 * @author Mark
 */

/**
 * Represents a paired-t distribution for running tests
 */
public class PairedInferences extends PairedDistribution{
    
    /**
     * Creates a sampling distribution from the sample statistics
     * @param n		number of pairs
     * @param sd	sd of differences
     * @param xbar	mean difference
     */
    public PairedInferences(int n, double sd, double xbar){
        
        super(n,sd,xbar);
        
    }
    
    /**
     * Creates a sampling distribution from the samples
     * @param s1 the first sample
     * @param s2 the second sample 
     */
    public PairedInferences(Sample s1, Sample s2){
        
        super(s1, s2);
        
    }
    
    /**
     * Calculates a paired student-t interval with
     * confidence level cl
     * @param cl the confidence level
     * @return the lower and upper bounds of the confidence
     * interval, respectively as the first and second
     * elements of a two-element array
     */
    public double[] pairedTInt (double cl){
        
        StudentTModel s = new StudentTModel(n - 1);
        
        double tstar = s.invT(cl + (1.0 - cl) / 2);
        
        double[] CI = new double[2];
        CI[0] = mean - tstar * se;
        CI[1] = mean + tstar * se;
        
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
    public double pairedTTest (double munaught, StudentTModel.AlternateHypothesis altH){
        
        StudentTModel s = new StudentTModel(n - 1);
        
        double t = (mean - munaught) / se;
        
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
