/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package statsprog;

/**
 *
 * @author Mark
 */
public class LinearRegression {
    
    public Sample x;//x
    public Sample y;//y      original data or re-expressed data
    private Sample zx;
    private Sample zy;//zscores
    private Sample e; //residuals
    private int n; //number of points
    private double b0;
    private double b1;//coeff.
    private double r;//coeff of corr.
    
    public LinearRegression(Sample x, Sample y){
        
        this.x = x;
        this.y = y;
        this.zx = generateZScores(x);
        this.zy = generateZScores(y);
        this.n = x.n();
        this.r = getr();
        this.b1 = getB1();
        this.b0 = getB0();
        this.e = generateResid();
        
    }
    
    private static Sample generateZScores(Sample s){
        
        double mean = s.mean();
        double sd = Math.sqrt(s.var() * s.n() / (s.n() - 1));
        Sample sret = new Sample();
        
        for(int i = 0; i < s.n(); i++)
            sret.add((s.get(i) - mean) / sd);
        
        return sret;
        
    }
    
    public double getr(){
        
        if(r != 0) return r;
        
        double r = 0;
        
        for(int i = 0; i < zx.n(); i++)
            r += zx.get(i) * zy.get(i);
        
        r /= n - 1;
        
        return r;
        
    }
    
    public double getRSquare(){
        return Math.pow(r, 2);
    }
    
    public double getB1(){
        
        if(b1 != 0) return b1;
        
        return r * (Math.sqrt(y.var() * y.n() / (y.n() - 1))) / (Math.sqrt(x.var() * x.n() / (x.n() - 1)));
        
    }
    
    public double getB0(){
        
        if(b0 != 0) return b0;
        
        return y.mean() - b1 * x.mean();
        
    }
    
    public double residual(double x, double y){
        
        return (y) - (x * b1 + b0);
        
    }
    
    private Sample generateResid(){
        
        Sample e = new Sample();
        
        for(int i = 0; i < x.n(); i++)
            e.add(residual(x.get(i), y.get(i)));
        
        return e;
        
    }
    
    public static Sample logS(Sample s){
        
        Sample samp = new Sample();
        
        for(int i = 0; i < s.n(); i++)
            samp.add(Math.log10(s.get(i)));
        
        return samp;
        
    }
    
    public Sample getResid(){
        return e;
    }
    
    public Sample getZX(){
        return zx;
    }
    
    public Sample getZY(){
        return zy;
    }
    
    public void regenerateLinReg(){;
        this.zx = generateZScores(x);
        this.zy = generateZScores(y);
        this.r = getr();
        this.b1 = getB1();
        this.b0 = getB0();
        this.e = generateResid();
    }
    
}
