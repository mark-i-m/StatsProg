package statsprog;

/**
 *
 * @author Mark
 */

/**
 * Represents a linear regression for running tests
 */
public class LinearRegressionInferences extends LinearRegression {

    public double SEB0;	//SE of B0
    public double SEB1; //SE of B1
    public double s;	//std error of regression
    
    /**
     * Create a distribution with these two samples
     * @param x the first sample
     * @param y the second sample
     */
    public LinearRegressionInferences(Sample x, Sample y){
        
        super(x,y);
        
        this.s = s();        
        this.SEB1 = SEB1();
        this.SEB0 = SEB0();      
        
    }
    
    /**
     * Calculate the std error of regression
     * @return the std error of regression
     */
    public double s(){
        
        double s = 0;
        
        Sample e = getResid();
        
        for(int i = 0; i < e.n(); i++)
            s += Math.pow(e.get(i), 2);
        
        s /= e.n() - 2; //2df
                
        return Math.sqrt(s);
        
    }
    
    /**
     * Calculate the std error of B1
     * @return the std error of B1 
     */
    public double SEB1(){
        
        return s / Math.sqrt(x.n() - 1) / Math.sqrt(DataStats.var(x.toArray()) * x.n() / (x.n() - 1));
        
    }
    
    /**
     * Calculate the std error of B0
     * @return the std error of B0
     */
    public double SEB0(){
        
        double meanx = DataStats.mean(x.toArray());
        double n = x.n();
        double seb0 = 0;
        
        for(int i = 0; i < n; i++)
            seb0 += Math.pow(x.get(i) - meanx, 2);
        
        seb0 = meanx * meanx / seb0;
        
        seb0 += 1 / n;
        
        return s * Math.sqrt(seb0);
        
    }
    
    /**
     * Calculate the t-score of B0 in this regression
     * @return the t-score
     */
    public double tscoreB0(){
        
        return getB0() / SEB0;
        
    }
    
    /**
     * Calculate the t-score of B1 in this regression
     * @return the t-score
     */
    public double tscoreB1(){
        
        return getB1() / SEB1;
        
    }
    
    /**
     * Calculate the probability of having a true B0
     * less than or equal to the calculated B0 in this
     * regression
     * @return the probability
     */
    public double pB0(){
        
        StudentTModel t = new StudentTModel(x.n() - 2);
        
        double tscore = tscoreB0();
        
        return 2 * t.tcdf((tscore < 0) ? -10 : tscore, (tscore < 0) ? tscore : 10);
        
    }
    
    /**
     * Calculate the probability of having a true B1
     * less than or equal to the calculated B1 in this
     * regression
     * @return the probability
     */
    public double pB1(){
        
        StudentTModel t = new StudentTModel(x.n() - 2);
        
        double tscore = tscoreB1();
        
        return 2 * t.tcdf((tscore < 0) ? -10 : tscore, (tscore < 0) ? tscore : 10);
        
    }
    
    /**
     * Calculate the p-value of running a linear-regression t-test,
     * with hypothesis bnaught, and alternate hypothesis altH. Here,
     * we are hypothesizing about the value of B1, which is slope.
     * @param bnaught the hypothesis
     * @param altH the alternate hypothesis
     * @return the p-value
     */
    public double LinRegTTest(double bnaught, LinearRegression.AlternateHypothesis altH){
        
        StudentTModel s = new StudentTModel(x.n() - 2);
        
        double t = tscoreB1();
        
        switch(altH){
            case BNOTEQ: //b1 != bnaught
                return 2 * s.tcdf((t < 0) ? -10 : t, (t < 0) ? t : 10);
                
            case BLESS: //b1 < bnaught
                return s.tcdf(-9, t);
                
            case BGREATER: //b1 > bnaught
                return s.tcdf(t, 9);
        }
        
        throw new IllegalArgumentException("altH invalid");
        
    }
    
    /**
     * Calculates a confidence interval for the linear regression
     * with confidence level cl.
     * @param cl the confidence level
     * @return the upper and lower bound of the confidence interval
     * as the first and second elements of a two-element array,
     * respectively.
     */
    public double[] LinRegInt(double cl){
        
        StudentTModel t = new StudentTModel(x.n() - 2);
        double tstar = t.invT(cl + (1.0 - cl) / 2);
        
        double[] CI = new double[2];
        CI[0] = getB1() - tstar * SEB1;
        CI[1] = getB1() + tstar * SEB1;
        
        return CI;
        
    }
    
    /**
     * Returns a string representation of the linear regression
     */
    public String toString(){
        
        String s = "";
        
        s += "Coeff\t\t\t\tSE(Coeff)\t\t\tt-score\t\t\t\tp-value\n";
        s += getB0() + "\t\t" + SEB0 + "\t\t" + tscoreB0() + "\t\t" + pB0() + "\n";
        s += getB1() + "\t\t" + SEB1 + "\t\t" + tscoreB1() + "\t\t" + pB1() + "\n\n";
        s += "r = " + getr() + "\t\tR-square = " + getRSquare() + "\t\ts = " + this.s + "\n\n\n";
        
        s += "0.95CI interval for B1:\n";
        double[] ci = LinRegInt(0.95);
        s += "[" + ci[0] + ", " + ci[1] + "]";
        
        return s;
        
    }
    
}
