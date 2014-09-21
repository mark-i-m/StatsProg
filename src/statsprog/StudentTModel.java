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
    
    public StudentTModel(double df){
        
        this.df = df;
        
    }
    
    public double tpdf (double x){ //tpdf of x on the distribution
        
        if(df > 170){ //when df > 170 use the normal model to approximate p
            
            NormalModel nm = new NormalModel(0,1);
            
            return nm.normalpdf(x);
            
        }
        
        double t = Gamma.gamma(0.5 * (df + 1));
        
        t = t / Math.sqrt(df * Math.PI);
        
        t = t / Gamma.gamma((double) df / 2);
        
        t = t / Math.pow(1 + Math.pow(x, 2) / df, (double)(df + 1) / 2);
        
        return t;
        
    }
    
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
    
    public double tcdf (double lower, double upper) { //calculates the probability of getting a tscore between upper and lower
        
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
    
    public double invT(double area) {//default accuracy
        
        return invT(area, 0.0000001);
        
    }
    
    public double invT(double area, double accuracy) {//returns tscore for passed prob/area
        
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
