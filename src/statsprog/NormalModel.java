/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package statsprog;

/**
 *
 * @author Mark
 */
public class NormalModel {
    
    private double mean;
    private double sd;
    
    public NormalModel(double m, double s){
        
        mean = m;
        sd = s;
        
    }
    
    public NormalModel(Sample s){
        
        mean = s.mean();
        sd = s.sd();
        
    }
    
    public double normalpdf(double datum) { // returns the normal probability density frequency of the datum on a normal curve with mean and sd

        double ret;

        ret = -1 * Math.pow((datum - mean), 2) / (2 * Math.pow(sd, 2));

        ret = Math.pow(Math.E, ret);

        ret /= sd * Math.sqrt(2 * Math.PI);

        return ret;

    }

    public double normalcdf(double lower, double upper) { // returns the normal probability between upper and lower bounds of normal model with mean and sd

        return normalcdf(lower, upper, 0.000001); // default accuracy

    }
    
    public double normalcdf(double lower, double upper, double accuracy){
        
        double sum = normalpdf(lower);

        for (double i = lower + accuracy; i < upper; i += accuracy) {
            sum += 2 * normalpdf(i);
        }

        sum += normalpdf(upper);
        
        if(sum < 1E-6) System.out.println("P < 1E-6; exact digits may not be accurate");

        return sum * accuracy * 0.5;
        
    }
    
    public double invNorm(double area) {
        
        return invNorm(area, 0.00001);
        
    }
    
    public double invNorm(double area, double accuracy) {
        
        NormalModel n = new NormalModel(0,1);
        
        double x = 0;
        double ncdf = (area - n.normalcdf(-5, x));
        double npdf = 0 - n.normalpdf(x);
        
        while(Math.abs(ncdf) > accuracy){
            x -= ncdf / npdf;
            ncdf = (area - n.normalcdf(-5, x));
            npdf = 0 - n.normalpdf(x);
        }
        x = x * sd + mean;
        
        return x;
        
    }

    public double zscore(double datum) { // finds z score of datum, among data with mean and sd

        return (datum - mean) / sd;

    }
    
    public double getMean(){
        
        return mean;
        
    }
    
    public double getSD(){
        
        return sd;
        
    }
    
}
