package statsprog;

/**
 *
 * @author Mark
 */

/**
 * Represents a paired-t distribution
 */
public class PairedDistribution {
    
    public int n;		//number of pairs
    public double sd;	//sd of mean difference of data
    public double se;	//se of pop from data
    public double mean;	//mean diff. of data
    
    /**
     * Creates a sampling distribution from the sample statistics
     * @param n		number of pairs
     * @param sd	sd of differences
     * @param xbar	mean difference
     */
    public PairedDistribution(int n, double sd, double xbar){
        
        this.n = n;
        this.sd = sd;
        this.mean = xbar;
        this.se = SE(sd,n);
        
    }
    
    /**
     * Creates a sampling distribution from the samples
     * @param s1 the first sample
     * @param s2 the second sample 
     */
    public PairedDistribution(Sample s1, Sample s2){
        
        Sample s = new Sample();
        
        for(int i = 0; i < s1.n(); i++)           
            s.add(s1.get(i) - s2.get(i));
        
        this.n = s.n();
        this.sd = Math.sqrt(DataStats.var(s.toArray()) * n / (n - 1));
        this.mean = DataStats.mean(s.toArray());
        this.se = SE(sd,n);
        
    }
    
    /**
     * Calculate the std error of the differences of the population
     * for the given sample statistics
     * @param sd the sample sd
     * @param n the sample size
     * @return the std error
     */
    public static double SE(double sd, int n){
        
        return sd / Math.sqrt(n);
        
    }
    
    /**
     * Calculate the t-score of xbar in this distribution
     * @param xbar the datum (a difference)
     * @return the t-score
     */
    public double tscore (double xbar){
        
        return (xbar - mean) / se;
        
    }
    
    /**
     * Calculates the PDF of xbar in this sampling
     * distribution
     * @param xbar the datum
     * @return the PDF
     */
    public double tpdf (double xbar){
        
        StudentTModel s = new StudentTModel(n - 1);
        
        return s.tpdf(tscore(xbar));
        
    }
    
    /**
     * Calculates the probability of a difference being between
     * lower and upper in this distribution
     * @param lower the lower bound
     * @param upper the upper bound
     * @return the probability
     */
    public double tcdf (double lower, double upper){
        
        StudentTModel s = new StudentTModel(n - 1);
        
        return s.tcdf(tscore(lower), tscore(upper));
        
    }
    
}
