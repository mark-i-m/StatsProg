package statsprog;

import cern.jet.stat.Gamma;

/**
 *
 * @author Mark
 */

/**
 * Models a Student t model with df degrees of freedom
 */
public class StudentTModel {
    
    private double df;
    
    /**
     * Create a student t model with df degrees of freedom
     * @param df number of degrees of freedom
     */
    public StudentTModel(double df){
        
        this.df = df;
        
    }
    
    /**
     * Calculate the PDF function for a datum x
     * @param x the datum
     * @return the PDF function's value at x
     */
    public double tpdf (double x){
    	
        //when df > 170 use the normal model to approximate p
        if(df > 170){ 
            
            NormalModel nm = new NormalModel(0,1);
            
            return nm.normalpdf(x);
            
        }
        
        double t = Gamma.gamma(0.5 * (df + 1));
        
        t = t / Math.sqrt(df * Math.PI);
        
        t = t / Gamma.gamma((double) df / 2);
        
        t = t / Math.pow(1 + Math.pow(x, 2) / df, (double)(df + 1) / 2);
        
        return t;
        
    }
    
    /**
     * Calculate the probability that a datum is between -inf
     * and bound for the model. 
     * @param bound the upper bound's t-score
     * @return the probability
     */
    public double tcdf (double bound) {
    	
    	if(df > 170){ //when df > 170 use the normal model to approximate p
            
            NormalModel nm = new NormalModel(0,1);
            
            return nm.normalcdf(-9999, bound);
            
        }
    	
    	if(bound <= 0) {
    		
    		return 0.5 * Gamma.incompleteBeta(df / (bound * bound + df), df / 2.0, 0.5)
    					/ Gamma.beta(df / 2.0, 0.5);
    		
    	} else {
    		
    		return 0.5 * ((Gamma.incompleteBeta(bound * bound / (bound * bound + df), 0.5, df / 2.0)
					/ Gamma.beta(0.5, df / 2.0)) + 1);
    		
    	}
    	
    }
    
    /**
     * Calculates the probability that a datum is between
     * lower and upper for this model.
     * @param lower the lower bound's t-score
     * @param upper the upper bound's t-score
     * @return the probability
     */
    public double tcdf (double lower, double upper) {
        
    	if(upper < lower) throw new IllegalArgumentException("upper < lower");
    	
        if(df > 170){ //when df > 170 use the normal model to approximate p
            
            NormalModel nm = new NormalModel(0,1);
            
            return nm.normalcdf(lower, upper);
            
        }
        
        // by fund thm of calc
        double upp = tcdf(upper);
        double low = tcdf(lower);
        
        return upp - low;
        
    }
    
    /**
     * Given a probability area, calculates the t-score of the datum.
     * area is taken as P(X <= x). Uses 0.0000001 as the accuracy.
     * @param area the probability
     * @return the t-score
     */
    public double invT(double area) {
        
        return invT(area, 0.0000001);
        
    }
    
    /**
     * Given a probability area, calculates the t-score of the datum.
     * area is taken as P(X <= x).
     * @param area the probability
     * @param accuracy the accuracy
     * @return the t-score
     */
    public double invT(double area, double accuracy) {
        
        StudentTModel s = new StudentTModel(df);
        
        double x = 0;
        double tcdf = (area - s.tcdf(-10, x));
        double tpdf = 0 - s.tpdf(x);
        
        // using Newton's method
        while(Math.abs(tcdf) > accuracy){
            x -= tcdf / tpdf;
            tcdf = (area - s.tcdf(-10, x));
            tpdf = 0 - s.tpdf(x);
        }
        
        return x;
        
    }
    
}
