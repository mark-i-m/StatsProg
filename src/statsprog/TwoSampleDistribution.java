/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package statsprog;

/**
 *
 * @author Mark
 */
public class TwoSampleDistribution {//creates a sampling distribution for difference of two sample means; all parameters that involve means should be in terms of difference of means
    
    public int n1;
    public int n2; //sample sizes
    public double sd1;
    public double sd2; //sd of data samples
    public double se; //se of pop from data
    public double mean1;
    public double mean2; //mean of data
    public double df; //degrees of freedom - use special formula
    public double SEp;//pooled se
    
    public TwoSampleDistribution(int n1, int n2, double sd1, double sd2, double xbar1, double xbar2){ //from stats
        
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
    
    public TwoSampleDistribution(Sample s1, Sample s2){ //from sampling
        
        this.n1 = s1.n();
        this.n2 = s2.n();
        this.sd1 = Math.sqrt(s1.var() * n1 / (n1 - 1));
        this.sd2 = Math.sqrt(s2.var() * n2 / (n2 - 1));
        this.mean1 = s1.mean();
        this.mean2 = s2.mean();
        this.se = SE(sd1,n1,sd2,n2);
        this.df = df(sd1,n1,sd2,n2);
        this.SEp = SEp(sd1,n1,sd2,n2);
        
    }
    
    public static double SE(double sd1, int n1, double sd2, int n2){ //calculates the se of pop from sampling dist
        
        return Math.sqrt(sd1 * sd1 / n1 + sd2 * sd2 / n2);
        
    }
    
    public static double SEp(double sd1, int n1, double sd2, int n2){
        
        double sep = (n1 - 1) * sd1 * sd1 + (n2 - 1) * sd2 * sd2;
        
        sep /= ((n1 - 1) + (n2 - 1));
        
        sep = Math.sqrt(sep);
        
        return SE(sep,n1,sep,n2);
        
    }
    
    public static double df(double sd1, int n1, double sd2, int n2){ //calculates the degrees of freedom
        
        double df = 0;
        
        df = Math.pow(sd1 * sd1 / n1 + sd2 * sd2 / n2, 2);
        
        df /= (Math.pow(sd1 * sd1 / n1, 2) / (n1 - 1) + Math.pow(sd2 * sd2 / n2, 2) / (n2 - 1));
        
        return df;
        
    }
    
    public double tscore (double xbar){//calculates the tscore of xbar, xbar is a difference of means
        
        return (xbar - (mean1 - mean2)) / se;
        
    }
    
    public double tpdf(double xbar){//calculates the tpdf of xbar in the sampling distribution
        
        StudentTModel s = new StudentTModel(df);
        
        return s.tpdf(tscore(xbar));
        
    }
    
    public double tcdf(double lower, double upper){//calculates the probability of getting a sample with xbar between upper and lower in the sampling distribution
        
         StudentTModel s = new StudentTModel(df);
        
        return s.tcdf(tscore(lower), tscore(upper));
        
    }
    
}
