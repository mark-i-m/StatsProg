package statsprog;

/**
 *
 * @author Mark
 */

/**
 * Represents a linear regression
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
    
    /**
     * Create a linear regression from two samples
     * @param x the x sample
     * @param y the y sample
     */
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
    
    /**
     * Represents the possible alternate hypotheses
     * of a hypothesis test
     */
    public enum AlternateHypothesis {
    	BLESS, BNOTEQ, BGREATER;
    }
    
    /**
     * Generates the z-score for each datum in the sample 
     * @param s the sample
     * @return the set of z-scores, in the form of a sample
     */
    private static Sample generateZScores(Sample s){
        
        double mean = DataStats.mean(s.toArray());
        double sd = Math.sqrt(DataStats.var(s.toArray()) * s.n() / (s.n() - 1));
        Sample sret = new Sample();
        
        for(int i = 0; i < s.n(); i++)
            sret.add((s.get(i) - mean) / sd);
        
        return sret;
        
    }
    
    /**
     * Calculates the correlation coefficient
     * @return the correlation coefficient
     */
    public double getr(){
        
        if(r != 0) return r;
        
        double r = 0;
        
        for(int i = 0; i < zx.n(); i++)
            r += zx.get(i) * zy.get(i);
        
        r /= n - 1;
        
        return r;
        
    }
    
    /**
     * Calculate the coefficient of determination
     * @return the coefficient of determination
     */
    public double getRSquare(){
        return Math.pow(r, 2);
    }
    
    /**
     * Calculate Beta_1, the slope
     * @return B1
     */
    public double getB1(){
        
        if(b1 != 0) return b1;
        
        return r * (Math.sqrt(DataStats.var(y.toArray()) * y.n() / (y.n() - 1)))
        		 / (Math.sqrt(DataStats.var(x.toArray()) * x.n() / (x.n() - 1)));
        
    }
    
    /**
     * Calculate Beta_0, the intercept
     * @return B0
     */
    public double getB0(){
        
        if(b0 != 0) return b0;
        
        return DataStats.mean(y.toArray()) - b1 * DataStats.mean(x.toArray());
        
    }
    
    /**
     * Calculate the residual of a point (x, y)
     * in this linear regression.
     * @param x the x value
     * @param y the y value
     * @return the residual
     */
    public double residual(double x, double y){
        
        return (y) - (x * b1 + b0);
        
    }
    
    /**
     * Generates residuals for each x value
     * @return the set of residuals, in the form
     * of a Sample
     */
    private Sample generateResid(){
        
        Sample e = new Sample();
        
        for(int i = 0; i < x.n(); i++)
            e.add(residual(x.get(i), y.get(i)));
        
        return e;
        
    }
    
    /**
     * Takes log_10 of each datum in sample s
     * @param s the sample
     * @return the set of logs, in the form
     * of a Sample
     */
    public static Sample logS(Sample s){
        
        Sample samp = new Sample();
        
        for(int i = 0; i < s.n(); i++)
            samp.add(Math.log10(s.get(i)));
        
        return samp;
        
    }
    
    /**
     * Get the set of residuals for this linear
     * regression.
     * @return the set of residuals, in the form
     * of a Sample
     */
    public Sample getResid(){
        return e;
    }
    
    /**
     * Get z scores for x values
     * @return the set of z scores, in the form
     * of a Sample
     */
    public Sample getZX(){
        return zx;
    }
    
    /**
     * Get z scores for y values
     * @return the set of z scores, in the form
     * of a Sample
     */
    public Sample getZY(){
        return zy;
    }
    
    /**
     * Recalculates this linear regression's
     * properties.
     */
    public void regenerateLinReg(){;
        this.zx = generateZScores(x);
        this.zy = generateZScores(y);
        this.r = getr();
        this.b1 = getB1();
        this.b0 = getB0();
        this.e = generateResid();
    }
    
}
