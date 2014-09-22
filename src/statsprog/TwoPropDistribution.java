package statsprog;

/**
 *
 * @author Mark
 */

/**
 * Represents a 2-proportion distribution
 */
public class TwoPropDistribution {

    protected int x1;		//samp prop 1
    protected int x2;		//samp prop 2
    protected int n1;		//samp size 1
    protected int n2;		//samp size 2
    protected double pp;	//p pooled
    protected double SE;	//SE (normal)
    protected double SEp;	//SE pooled
    
    /**
     * Create a distribution with these parameters
     * @param p1 the first sample proportion
     * @param p2 the second sample proportion
     * @param n1 the first sample size
     * @param n2 the second sample size
     */
    public TwoPropDistribution (double p1, double p2, int n1, int n2){
        
        this.x1 = (int) Math.ceil(p1 * n1);
        this.x2 = (int) Math.ceil(p2 * n2);
        this.n1 = n1;
        this.n2 = n2;
        this.pp = pp(x1,x2,n1,n2);
        this.SE = SE(p1,p2,n1,n2);
        this.SEp = SEp(pp,n1,n2);
        
    }
    
    /**
     * Creates a distribution with these samples
     * @param s1 the first sample
     * @param s2 the second sample
     */
    public TwoPropDistribution (Sample s1, Sample s2){
        
        this.n1 = s1.n();
        this.n2 = s2.n();
        this.x1 = (int) Math.ceil(DataStats.mean(s1.toArray()) * n1);
        this.x2 = (int) Math.ceil(DataStats.mean(s2.toArray()) * n2);
        this.pp = pp(x1,x2,n1,n2);
        this.SE = SE(DataStats.mean(s1.toArray()), DataStats.mean(s2.toArray()),n1,n2);
        this.SEp = SEp (pp,n1,n2);
        
    }
    
    /**
     * Calculate the pooled proportion for these data
     * @param x1 the first sample mean
     * @param x2 the second sample mean
     * @param n1 the first sample size
     * @param n2 the second sample size
     * @return the pooled proportion
     */
    public static double pp(int x1, int x2, int n1, int n2){

        return ((double) x1 + x2)/(n1 + n2);
        
    }
    
    /**
     * Calculate the std error for the population difference
     * @param p1 the first proportion
     * @param p2 the second proportion
     * @param n1 the first sample/population size
     * @param n2 the second sample/population size
     * @return the std error
     */
    public static double SE(double p1, double p2, int n1, int n2){
        
        return Math.sqrt(p1*(1-p1)/(double)n1 + p2*(1-p2)/(double)n2);
        
    }
    
    /**
     * Calculate the pooled std error for the population difference
     * @param pp the pooled proportion
     * @param n1 the first sample size
     * @param n2 the second sample size
     * @return the pooled std error
     */
    public static double SEp(double pp, int n1, int n2){
        
        return SE(pp,pp,n1,n2);
        
    }
    
    /**
     * Calculate the normal PDF of datum for this distribution
     * @param datum the datum
     * @return the PDF
     */
    public double normalpdf(double datum){
        
        NormalModel nm = new NormalModel(0,SE);
        
        return nm.normalpdf(datum);
        
    }
    
    /**
     * Calculate the probability of a datum being between
     * lower and upper
     * @param lower the lower bound
     * @param upper the upper bound
     * @return the probability
     */
    public double normalcdf(double lower, double upper){
        
        NormalModel nm = new NormalModel(0, SE);
        
        return nm.normalcdf(lower, upper);
        
    }
    
}
