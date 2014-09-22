package statsprog;

/**
 *
 * @author Mark
 */

/**
 * Creates a one-proportion distributions for performing
 * one-proportion z-tests
 */
public class OnePropDistribution {
    
    public double p;	//true proportion
    public int n;		//sample size
    public double se;	//stand. error or sd - usually we do not know true sd
    
    /**
     * Create a sampling distribution with sample size n
     * and true proportion p
     * @param p the true proportion
     * @param n the sample size
     */
    public OnePropDistribution(double p, int n) {
        
        this.p = p;
        this.n = n;
        this.se = SE(p,n);
        
    }
    
    /**
     * Create a sampling distribution with the sample s
     * @param s the sample
     */
    public OnePropDistribution(Sample s) {
        
        this.p = DataStats.mean(s.toArray());
        this.n = s.n();
        this.se = SE(p,n);
        
    }
    
    /**
     * Calculate the std error / std dev for a distribution
     * with a mean proportion p and sample size n
     * @param p the mean proportion
     * @param n the sample size
     * @return the std error
     */
    public static double SE(double p, int n) {
    	
        return Math.sqrt(p * (1 - p) / n);
        
    }
    
    /**
     * Calculates the z-score of p_hat
     * @param p_hat the proportion
     * @return the z-score
     */
    public double zscore (double p_hat) {
    	
        return (p_hat - p) / se;
        
    }
    
    /**
     * Calculates the normal PDF of p_hat in this distribution
     * @param p_hat the proportion p_hat
     * @return the normal PDF
     */
    public double normalpdf (double p_hat){
        
        NormalModel n = new NormalModel(p, se);
        
        return n.normalpdf(p_hat);
        
    }
    
    /**
     * Calculates the probability that a datum is between
     * lower and upper.
     * @param lower the lower bound
     * @param upper the upper bound
     * @return the probability
     */
    public double normalcdf (double lower, double upper){
        
        NormalModel n = new NormalModel(p, se);
        
        return n.normalcdf(lower, upper);
        
    }
    
}
