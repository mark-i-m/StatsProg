package statsprog;

/**
 *
 * @author Mark
 */

/**
 * Represents a two-proportion distribution for running tests
 */
public class TwoPropInferences extends TwoPropDistribution {

	/**
	 * Creates the distribution with these parameters
	 * @param p1 the first proportion
	 * @param p2 the second proportion
	 * @param n1 the first sample/population size
	 * @param n2 the second sample/population size
	 */
    public TwoPropInferences(double p1, double p2, int n1, int n2) {

        super(p1, p2, n1, n2);

    }
    
    /**
     * Creates the distribution with these samples
     * @param s1 the first sample
     * @param s2 the second sample
     */
    public TwoPropInferences(Sample s1, Sample s2){
        
        super (s1,s2);
        
    }
 
    /**
     * Calculates a two-proportion z-interval with confidence
     * level cl
     * @param cl the confidence level
     * @return the lower and upper bounds of the interval --
     * a two-element array, the first element is the lower
     * bound, the second element is the upper bound
     */
    public double[] twoPropZInt(double cl) {
        
        NormalModel nm = new NormalModel(0,1);
        
        double zstar = nm.invNorm(cl + (1. - cl) / 2);
        
        double[] CI = new double[2];
        CI[0] = (1.0 * x1/n1 - 1.0 * x2/n2) - zstar * SE;
        CI[1] = (1.0 * x1/n1 - 1.0 * x2/n2) + zstar * SE;
        
        return CI;
        
    }
    
    /**
     * Calculates a two-proportion z-interval with confidence
     * level cl, using a pooled std error. This should be used
     * only if the variances are equal.
     * @param cl the confidence level
     * @return the lower and upper bounds of the interval --
     * a two-element array, the first element is the lower
     * bound, the second element is the upper bound
     */
    public double[] twoPropPooledZInt(double cl) {
        
        NormalModel nm = new NormalModel(0,1);
        
        double zstar = nm.invNorm(cl + (1. - cl) / 2);
        
        double[] CI = new double[2];
        CI[0] = (1.0 * x1/n1 - 1.0 * x2/n2) - zstar * SEp;
        CI[1] = (1.0 * x1/n1 - 1.0 * x2/n2) + zstar * SEp;
        
        return CI;
        
    }

    /**
     * Calculates the p-value for the hypothesis test.
     * @param pnaught the hypothesis
     * @param altH the alternate hypothesis
     * @return the p-value
     */
    public double twoPropZTest(double pnaught, NormalModel.AlternateHypothesis altH) {
        
        if(pnaught == 0) return twoPropPooledZTest(pnaught,altH);
        
        NormalModel nm = new NormalModel(0,1);
        
        double p = (((double)x1)/n1 - ((double)x2)/n2);
        double z = (p - pnaught)/SE;
        
        switch(altH){
            case PNOTEQ: // p != pnaught
                return 2 * nm.normalcdf((z < 0) ? -10 : z, (z < 0) ? z : 10);
         
            case PLESS: // p < pnaught
                return nm.normalcdf(-30, z);
            
            case PGREATER: // p > pnaught
                return nm.normalcdf(z, 30);
                
        }
        
        throw new IllegalArgumentException("altH invalid");
        
    }
    
    /**
     * Calculates the p-value for the hypothesis test,
     * using pooled std error.
     * @param pnaught the hypothesis
     * @param altH the alternate hypothesis
     * @return the p-value
     */
    public double twoPropPooledZTest(double pnaught, NormalModel.AlternateHypothesis altH) {
        
        NormalModel nm = new NormalModel(0,1);
        
        double p = (1.0 * x1/n1 - 1.0 * x2/n2);
        double z = (p - pnaught)/SEp;
        
        switch(altH){
            case PNOTEQ: // p != pnaught
                return 2 * nm.normalcdf((z < 0) ? -10 : z, (z < 0) ? z : 10);
         
            case PLESS: // p < pnaught
                return nm.normalcdf(-30, z);
            
            case PGREATER: // p > pnaught
                return nm.normalcdf(z, 30);   
        }
        
        throw new IllegalArgumentException("altH invalid");
        
    }
    
}
