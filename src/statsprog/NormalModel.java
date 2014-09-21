package statsprog;

/**
 *
 * @author Mark
 * 
 * Represents a normal model with mean=m std dev=s
 */
public class NormalModel {
    
    private double mean;
    private double sd;
    
    /**
     * Create a Normal Model with these parameters
     * @param m the mean
     * @param s the std dev.
     */
    public NormalModel(double m, double s){
        
        mean = m;
        sd = s;
        
    }
    
    /** 
     * Create a normal model for Sample s.
     * This does not check that s is Normally distributed.
     * @param s the sample
     */
    public NormalModel(Sample s){
        
        mean = s.mean();
        sd = s.sd();
        
    }
    
    /**
     * Calculates the PDF of this normal model for some datum
     * @param datum the datum
     * @return the PDF
     */
    public double normalpdf(double datum) {

        double ret;

        ret = -1 * Math.pow((datum - mean), 2) / (2 * Math.pow(sd, 2));

        ret = Math.pow(Math.E, ret);

        ret /= sd * Math.sqrt(2 * Math.PI);

        return ret;

    }

    /**
     * Calculate the probability that a datum is between
     * lower and upper in this model. Uses 0.000001 as the
     * accuracy.
     * @param lower the lower bound
     * @param upper the upper bound
     * @return the probability
     */
    public double normalcdf(double lower, double upper) { 

        return normalcdf(lower, upper, 0.000001);

    }
    
    /**
     * Calculate the probability that a datum is between
     * lower and upper in this model.
     * @param lower the lower bound
     * @param upper the upper bound
     * @param accuracy the accuracy of the result
     * @return the probability
     */
    public double normalcdf(double lower, double upper, double accuracy){
        
        double sum = normalpdf(lower);

        for (double i = lower + accuracy; i < upper; i += accuracy) {
            sum += 2 * normalpdf(i);
        }

        sum += normalpdf(upper);
        
        if(sum < 1E-6) System.out.println("P < 1E-6; exact digits may not be accurate");

        return sum * accuracy * 0.5;
        
    }
    
    /**
     * Calculates the datum that has a probability of area.
     * area should be from -inf to some datum x. Uses an accuracy
     * of 0.00001.
     * @param area the probability
     * @return the datum x
     */
    public double invNorm(double area) {
        
        return invNorm(area, 0.00001);
        
    }
    
    /**
     * Calculates the datum that has a probability of area.
     * area should be from -inf to some datum x.
     * @param area the probability
     * @param accuracy the accuracy
     * @return the datum x
     */
    public double invNorm(double area, double accuracy) {
        
        NormalModel n = new NormalModel(0,1);
        
        double x = 0;
        double ncdf = (area - n.normalcdf(-5, x));
        double npdf = 0 - n.normalpdf(x);
        
        // using Newton's method
        while(Math.abs(ncdf) > accuracy){
            x -= ncdf / npdf;
            ncdf = (area - n.normalcdf(-5, x));
            npdf = 0 - n.normalpdf(x);
        }
        x = x * sd + mean;
        
        return x;
        
    }

    /**
     * Calculate the z-score of datum in this
     * normal model
     * @param datum the datum
     * @return the z-score
     */
    public double zscore(double datum) {

        return (datum - mean) / sd;

    }
    
    /**
     * Returns the mean of this model
     * @return the mean
     */
    public double getMean(){
        
        return mean;
        
    }
    
    /**
     * Returns the std dev of this model
     * @return the std dev
     */
    public double getSD(){
        
        return sd;
        
    }
    
}
