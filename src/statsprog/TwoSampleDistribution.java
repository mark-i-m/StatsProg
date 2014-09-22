package statsprog;

/**
 *
 * @author Mark
 */

/**
 * Represents a sampling distribution for difference of two sample means
 */
public class TwoSampleDistribution {
    
    public int n1;
    public int n2; 			//sample sizes
    public double sd1;
    public double sd2; 		//sd of data samples
    public double se; 		//se of pop from data
    public double mean1;
    public double mean2; 	//mean of data
    public double df; 		//degrees of freedom - use special formula
    public double SEp;		//pooled se
    
    /**
     * Creates a distribution from these statistics
     * @param n1		the first sample size
     * @param n2		the second sample size
     * @param sd1		the first sample sd
     * @param sd2		the second sample sd
     * @param xbar1		the first sample mean
     * @param xbar2		the second sample mean
     */
    public TwoSampleDistribution(int n1, int n2, double sd1, double sd2, double xbar1, double xbar2){
        
        this.n1 = n1;
        this.n2 = n2;
        this.sd1 = sd1;
        this.sd2 = sd2;
        this.mean1 = xbar1;
        this.mean2 = xbar2;
        this.se = SE(sd1,n1,sd2,n2);
        this.df = df(sd1,n1,sd2,n2);
        this.SEp = SEp(sd1,n1,sd2,n2);
        
    }
    
    /**
     * Creates a distribution from these samples
     * @param s1 the first sample
     * @param s2 the second sample
     */
    public TwoSampleDistribution(Sample s1, Sample s2){
        
        this.n1 = s1.n();
        this.n2 = s2.n();
        this.sd1 = Math.sqrt(DataStats.var(s1.toArray()) * n1 / (n1 - 1));
        this.sd2 = Math.sqrt(DataStats.var(s2.toArray()) * n2 / (n2 - 1));
        this.mean1 = DataStats.mean(s1.toArray());
        this.mean2 = DataStats.mean(s2.toArray());
        this.se = SE(sd1,n1,sd2,n2);
        this.df = df(sd1,n1,sd2,n2);
        this.SEp = SEp(sd1,n1,sd2,n2);
        
    }
    
    /**
     * Calculates the std error of the population from these
     * sample data
     * @param sd1	the first sample sd
     * @param n1	the first sample size
     * @param sd2	the second sample sd
     * @param n2	the second sample size
     * @return the std error
     */
    public static double SE(double sd1, int n1, double sd2, int n2){
        
        return Math.sqrt(sd1 * sd1 / n1 + sd2 * sd2 / n2);
        
    }
    
    /**
     * Calculates the pooled std error of the population from these
     * sample data
     * @param sd1	the first sample sd
     * @param n1	the first sample size
     * @param sd2	the second sample sd
     * @param n2	the second sample size
     * @return the pooled std error
     */
    public static double SEp(double sd1, int n1, double sd2, int n2){
        
        double sep = (n1 - 1) * sd1 * sd1 + (n2 - 1) * sd2 * sd2;
        
        sep /= ((n1 - 1) + (n2 - 1));
        
        sep = Math.sqrt(sep);
        
        return SE(sep,n1,sep,n2);
        
    }
    
    /**
     * Calculates the number of degrees of freedom to use given these
     * sample statistics
     * @param sd1	the first sample sd
     * @param n1	the first sample size
     * @param sd2	the second sample sd
     * @param n2	the second sample size
     * @return number of degrees of freedom
     */
    public static double df(double sd1, int n1, double sd2, int n2){
        
        double df = 0;
        
        df = Math.pow(sd1 * sd1 / n1 + sd2 * sd2 / n2, 2);
        
        df /= (Math.pow(sd1 * sd1 / n1, 2) / (n1 - 1) + Math.pow(sd2 * sd2 / n2, 2) / (n2 - 1));
        
        return df;
        
    }
    
    /**
     * Calculates the t-score of xbar
     * @param xbar difference of means
     * @return the t-score
     */
    public double tscore (double xbar){
        
        return (xbar - (mean1 - mean2)) / se;
        
    }
    
    /**
     * Calculates the PDF of the datum for this distribution
     * @param xbar the datum
     * @return the PDF
     */
    public double tpdf(double xbar){
        
        StudentTModel s = new StudentTModel(df);
        
        return s.tpdf(tscore(xbar));
        
    }
    
    /**
     * Calculates the probability of a datum being between
     * lower and upper for this distribution.
     * @param lower the lower bound
     * @param upper the upper bound
     * @return the probability
     */
    public double tcdf(double lower, double upper){
        
         StudentTModel s = new StudentTModel(df);
        
        return s.tcdf(tscore(lower), tscore(upper));
        
    }
    
}
