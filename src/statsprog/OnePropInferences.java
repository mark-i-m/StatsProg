package statsprog;

/**
 *
 * @author Mark
 */

/**
 * Creates a sampling distribution for inferences using p_hat
 * and sample size n 
 */
public class OnePropInferences extends OnePropDistribution {
    
	/**
	 * Create a distribution for inferences
	 * @param p_hat the sample mean 
	 * @param n the sample size
	 */
    public OnePropInferences(double p_hat, int n){
    	// sd variable will contain the se
        super(p_hat, n);
    }
    
    /**
     * Create a distribution for the sample
     * @param s
     */
    public OnePropInferences(Sample s){
        super(s);
    }
    
    /**
     * Calculates a confidence interval of confidence level cl
     * for this distribution 
     * @param cl the confidence level
     * @return the confidence level -- an array of size 2,
     * the first element will be the lower bound of the interval
     * and the second element will be the upper bound
     */
    public double[] onePropZInt (double cl){
        
        NormalModel nm = new NormalModel(0,1);
        
        double zstar = nm.invNorm(cl + (1.0 - cl) / 2);
        
        double[] CI = new double[2];
        CI[0] = p - zstar * se;
        CI[1] = p + zstar * se;
        
        return CI;
        
    }
    
    /**
     * Calculates the p-value resulting from a one-proportion z-test
     * with hypothesis p = pnaught, and alternate hypothesis altH.
     * altH == PLESS indicates that the alternate hypothesis is
     * 		p < p_o
     * altH == PNOTEQ indicates p != p_o
     * altH == PGREATER indicates p > p_o
     * @param pnaught the hypothesis
     * @param altH the alternate hypothesis
     * @return the p-value
     */
    public double onePropZTest (double pnaught, NormalModel.AlternateHypothesis altH){
        
        NormalModel nm = new NormalModel(0,1);
        
        double z = (p - pnaught) / Math.sqrt(pnaught * (1- pnaught) / n);
        
        switch(altH){
            case PNOTEQ: // p != pnaught
                return 2 * nm.normalcdf((z < 0) ? -10 : z, (z < 0) ? z : 10);
         
            case PLESS: // p < pnaught
                return nm.normalcdf(-9, z);
            
            case PGREATER: // p > pnaught
                return nm.normalcdf(z, 9);   
        }
        
        throw new IllegalArgumentException("altH invalid");
        
    }
    
}
