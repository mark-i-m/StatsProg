package statsprog;

import cern.jet.stat.Gamma;

/**
 *
 * @author Mark
 */

/**
 * Represents a x^2 model with df degrees of freedom
 */
public class ChiSquareModel { //creates a chi-square model
    
    private double df;
    
    /**
     * Creates a x^2 model with df degrees of freedom
     * @param df the number of degrees of freedom
     */
    public ChiSquareModel(double df){
        
        this.df = df;
        
    }
    
    /**
     * Calculates the value of the PDF function for datum x
     * @param x the datum
     * @return the PDF
     */
    public double chipdf(double x){
        
        double pdf = Math.pow(Math.E, 0 - x / 2);
        
        pdf *= Math.pow(x, df / 2 - 1);
        
        pdf /= Math.pow(2, df / 2);
        
        pdf /= Gamma.gamma(df / 2);
        
        return pdf;
        
    }
    
    /**
     * Calculates the probability that a datum will be between
     * lower and upper in this model.
     * @param lower the lower bound
     * @param upper the upper bound
     * @return
     */
    public double chicdf (double lower, double upper){
        
    	if(lower < 0)
    		throw new IllegalArgumentException("chi-square model is not defined for x < 0");
    	if(upper < lower)
    		throw new IllegalArgumentException("upper < lower");
    	
        double upp = Gamma.incompleteGamma(df / 2.0, upper / 2.0) / Gamma.gamma(df / 2.0);
        double low = Gamma.incompleteGamma(df / 2.0, lower / 2.0) / Gamma.gamma(df / 2.0);
        
        //by fundamental thm of calc
        return upp - low;
        
    }
     
     /**
      * Calculates the probability that a datum will be between
      * 0 and upper in this model.
      * @param upper the upper bound
      * @return
      */
     public double chicdf(double upper){
         
         return chicdf(0, upper);
         
     }

     /**
      * Given a probability, returns the datum y
      * such that P(0 < X < y) = area. Uses 0.0000001
      * as the accuracy
      * @param area the probability
      * @return the datum y
      */
     public double invChi(double area) {//default accuracy
        
        return invChi(area, 0.0000001);
        
    }

     /**
      * Given a probability, returns the datum y
      * such that P(0 < X < y) = area.
      * @param area the probability
      * @param accuracy the accuracy
      * @return the datum y
      */
    public double invChi(double area, double accuracy) {//returns tscore for passed prob/area
        
        ChiSquareModel x = new ChiSquareModel(df);
        
        double y = 0;
        double xcdf = (area - x.chicdf(-20, y));
        double xpdf = 0 - x.chipdf(y);
        
        // Using Newton's method
        while(Math.abs(xcdf) > accuracy){
            y -= xcdf / xpdf;
            xcdf = (area - x.chicdf(-20, y));
            xpdf = 0 - x.chipdf(y);
        }
        
        return y;
        
    }
    
}
