/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package statsprog;

/**
 *
 * @author Mark
 */
public class OneSampleDistribution { //creates a sampling distribution
    
    public int n;//sample size
    public double sd;//sd of data
    public double se;//se of pop from data
    public double mean;//mean of data
    
    public OneSampleDistribution(int n, double sd, double xbar){ //creates a sampling distribution from the sample stats passed
        
        this.n = n;
        this.sd = sd;
        this.mean = xbar;
        this.se = SE(sd,n);
        
    }
    
    public OneSampleDistribution(Sample s){ //creates a sampling distribution from sample
        
        this.n = s.n();
        this.sd = Math.sqrt(s.var() * n / (n - 1));
        this.mean = s.mean();
        this.se = SE(sd,n);
        
    }
    
    public static double SE(double sd, int n){//sd = sd of data, n = sample size, returns the pop SE from data
        
        return sd / Math.sqrt(n);
        
    }
    
    public double tscore (double xbar){//calculates tscore of xbar
        
        return (xbar - mean) / se;
        
    }
    
    public double tpdf (double xbar){ //calculates the tpdf of xbar in the sampling distribution
        
        StudentTModel s = new StudentTModel(n - 1);
        
        return s.tpdf(tscore(xbar));
        
    }
    
    public double tcdf (double lower, double upper){ //calculates the probability of getting a sample with xbar between upper and lower in the sampling distribution
        
        StudentTModel s = new StudentTModel(n - 1);
        
        return s.tcdf(tscore(lower), tscore(upper));
        
    }
    
}
