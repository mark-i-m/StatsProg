package statsprog;

/**
 *
 * @author Mark
 */

/**
 * Create a sampling distribution with a Student t test
 */
public class OneSampleDistribution {
    
    public int n;		//sample size
    public double sd;	//sd of data
    public double se;	//se of pop from data
    public double mean;	//mean of data
    
    /**
     * Creates a sampling distribution from these sample
     * statistics:
     * @param n the sample size
     * @param sd the sample std dev
     * @param xbar the sample mean
     */
    public OneSampleDistribution(int n, double sd, double xbar){
        
        this.n = n;
        this.sd = sd;
        this.mean = xbar;
        this.se = SE(sd,n);
        
    }
    
    /**
     * Create a sampling distribution from the sample
     * @param s the sample
     */
    public OneSampleDistribution(Sample s){
        
        this.n = s.n();
        this.sd = Math.sqrt(DataStats.var(s.toArray()) * n / (n - 1));
        this.mean = DataStats.mean(s.toArray());
        this.se = SE(sd,n);
        
    }
    
    /**
     * Calculate the std error of the population from the
     * data statistics
     * @param sd the std dev of the data
     * @param n the sample size
     * @return the population SE
     */
    public static double SE(double sd, int n){
        
        return sd / Math.sqrt(n);
        
    }
    
    /**
     * Calculates the t-score of the xbar in this distribution
     * @param xbar the datum
     * @return the t-score
     */
    public double tscore (double xbar){
        
        return (xbar - mean) / se;
        
    }
    
    /**
     * Calculate the PDF of xbar in this sampling distribution
     * @param xbar the datum
     * @return the PDF
     */
    public double tpdf (double xbar){
        
        StudentTModel s = new StudentTModel(n - 1);
        
        return s.tpdf(tscore(xbar));
        
    }
    
    /**
     * Calculate the probability of a datum being between
     * lower and upper in this sampling distribution
     * @param lower the lower bound
     * @param upper the upper bound
     * @return the probability
     */
    public double tcdf (double lower, double upper){
        
        StudentTModel s = new StudentTModel(n - 1);
        
        return s.tcdf(tscore(lower), tscore(upper));
        
    }
    
}
