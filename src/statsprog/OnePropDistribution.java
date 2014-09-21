/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package statsprog;

/**
 *
 * @author Mark
 */
public class OnePropDistribution { //creates a sampling distribution
    
    public double p;//true proportion
    public int n;//sample size
    public double se;//stand. error or sd - usually we do not know true sd
    
    public OnePropDistribution(double p, int n) { //creates a sampling distribution with sample size n and probability p
        
        this.p = p;
        this.n = n;
        this.se = SE(p,n);
        
    }
    
    public OnePropDistribution(Sample s) { //creates a sampling distribution from list/sample
        
        this.p = s.mean();
        this.n = s.n();
        this.se = SE(p,n);
        
    }
    
    public static double SE(double p, int n) { //calculates the SD
        
        return Math.sqrt(p * (1 - p) / n);
        
    }
    
    public double zscore (double phat) { //calculates the zscore of phat
        
        return (phat - p) / se;
        
    }
    
    public double normalpdf (double phat){ // calculates the normalpdf of phat in the sampling dist
        
        NormalModel n = new NormalModel(p, se);
        
        return n.normalpdf(phat);
        
    }
    
    public double normalcdf (double lower, double upper){ // calculates the probability of getting sample with phat between upper and lower in the sampling distribution
        
        NormalModel n = new NormalModel(p, se);
        
        return n.normalcdf(lower, upper);
        
    }
    
}
