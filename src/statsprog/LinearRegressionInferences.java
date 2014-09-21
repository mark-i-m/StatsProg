/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package statsprog;

/**
 *
 * @author Mark
 */
public class LinearRegressionInferences extends LinearRegression {

    public double SEB0;
    public double SEB1;
    public double s;
    
    public LinearRegressionInferences(Sample x, Sample y){
        
        super(x,y);
        
        this.s = s();        
        this.SEB1 = SEB1();
        this.SEB0 = SEB0();      
        
    }
    
    public double s(){
        
        double s = 0;
        
        Sample e = getResid();
        
        for(int i = 0; i < e.n(); i++)
            s += Math.pow(e.get(i), 2);
        
        s /= e.n() - 2; //2df
                
        return Math.sqrt(s);
        
    }
    
    public double SEB1(){
        
        return s / Math.sqrt(x.n() - 1) / Math.sqrt(x.var() * x.n() / (x.n() - 1));
        
    }
    
    public double SEB0(){
        
        double meanx = x.mean();
        double n = x.n();
        double seb0 = 0;
        
        for(int i = 0; i < n; i++)
            seb0 += Math.pow(x.get(i) - meanx, 2);
        
        seb0 = meanx * meanx / seb0;
        
        seb0 += 1 / n;
        
        return s * Math.sqrt(seb0);
        
    }
    
    public double tscoreB0(){
        
        return getB0() / SEB0;
        
    }
    
    public double tscoreB1(){
        
        return getB1() / SEB1;
        
    }
    
    public double pB0(){
        
        StudentTModel t = new StudentTModel(x.n() - 2);
        
        double tscore = tscoreB0();
        
        return 2 * t.tcdf((tscore < 0) ? -10 : tscore, (tscore < 0) ? tscore : 10);
        
    }
    
    public double pB1(){
        
        StudentTModel t = new StudentTModel(x.n() - 2);
        
        double tscore = tscoreB1();
        
        return 2 * t.tcdf((tscore < 0) ? -10 : tscore, (tscore < 0) ? tscore : 10);
        
    }
    
    public double LinRegTTest(double bnaught, int altH){//returns p-value of b1 (slope)
        
        StudentTModel s = new StudentTModel(x.n() - 2);
        
        double t = tscoreB1();
        
        switch(altH){
            case 0: //p != pnaught
                return 2 * s.tcdf((t < 0) ? -10 : t, (t < 0) ? t : 10);
                
            case 1: //p < pnaught
                return s.tcdf(-9, t);
                
            case 2: //p > pnaught
                return s.tcdf(t, 9);
                
            default: //impossible; used for coding purposes
                return 0;
        }
        
    }
    
    public double[] LinRegInt(double cl){//returns CI for b1 (slope), cl is a decimal
        
        StudentTModel t = new StudentTModel(x.n() - 2);
        double tstar = t.invT(cl + (1.0 - cl) / 2);
        
        double[] CI = new double[2];
        CI[0] = getB1() - tstar * SEB1;
        CI[1] = getB1() + tstar * SEB1;
        
        return CI;
        
    }
    
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
