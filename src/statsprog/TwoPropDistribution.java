/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package statsprog;

/**
 *
 * @author Mark
 */
public class TwoPropDistribution {

    public int x1;//samp prop 1
    public int x2;//samp prop 2
    public int n1;//samp size 1
    public int n2;//samp size 2
    public double pp;//p pooled
    public double SE;//SE (normal)
    public double SEp;//SE pooled
    
    public TwoPropDistribution (double p1, double p2, int n1, int n2){
        
        this.x1 = (int) Math.ceil(p1 * n1);
        this.x2 = (int) Math.ceil(p2 * n2);
        this.n1 = n1;
        this.n2 = n2;
        this.pp = pp(x1,x2,n1,n2);
        this.SE = SE(p1,p2,n1,n2);
        this.SEp = SEp(pp,n1,n2);
        
    }
    
    public TwoPropDistribution (Sample s1, Sample s2){
        
        this.n1 = s1.n();
        this.n2 = s2.n();
        this.x1 = (int) Math.ceil(s1.mean() * n1);
        this.x2 = (int) Math.ceil(s2.mean() * n2);
        this.pp = pp(x1,x2,n1,n2);
        this.SE = SE(s1.mean(),s2.mean(),n1,n2);
        this.SEp = SEp (pp,n1,n2);
        
    }
    
    public double pp(int x1, int x2, int n1, int n2){

        return (x1 + x2 + 0.0)/(n1 + n2);
        
    }
    
    public double SE(double p1, double p2, int n1, int n2){
        
        return Math.sqrt(p1*(1-p1)/(double)n1 + p2*(1-p2)/(double)n2);
        
    }
    
    public double SEp(double pp, int n1, int n2){
        
        return SE(pp,pp,n1,n2);
        
    }
    
    public double normalpdf(double datum){
        
        NormalModel nm = new NormalModel(0,SE);
        
        return nm.normalpdf(datum);
        
    }
    
    public double normalcdf(double lower, double upper){
        
        NormalModel nm = new NormalModel(0, SE);
        
        return nm.normalcdf(lower, upper);
        
    }
    
}
