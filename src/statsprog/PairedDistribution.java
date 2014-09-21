/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package statsprog;

/**
 *
 * @author Mark
 */
public class PairedDistribution { //creates a sampling distribution; all parameters with means are mean differences
    
    public int n;//number of pairs
    public double sd;//sd of mean difference of data
    public double se;//se of pop from data
    public double mean;//mean diff. of data
    
    public PairedDistribution(int n, double sd, double xbar){ //creates a sampling distribution from the sample stats passed
        
        this.n = n;
        this.sd = sd;
        this.mean = xbar;
        this.se = SE(sd,n);
        
    }
    
    public PairedDistribution(Sample s1, Sample s2){ //creates a sampling distribution from sample
        
        Sample s = new Sample();
        
        for(int i = 0; i < s1.n(); i++)           
            s.add(s1.get(i) - s2.get(i));
        
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
