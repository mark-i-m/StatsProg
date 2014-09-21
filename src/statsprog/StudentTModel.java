/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package statsprog;

/**
 *
 * @author Mark
 */


public class StudentTModel { //creates a t-model
    
    private double df;
    
    public StudentTModel(double df){
        
        this.df = df;
        
    }
    
    public double tpdf (double x){ //tpdf of x on the distribution
        
        if(df > 170){ //when df > 170 use the normal model to approximate p
            
            NormalModel nm = new NormalModel(0,1);
            
            return nm.normalpdf(x);
            
        }
        
        double t = gamma(0.5 * (df + 1));
        
        t = t / Math.sqrt(df * Math.PI);
        
        t = t / gamma((double) df / 2);
        
        t = t / Math.pow(1 + Math.pow(x, 2) / df, (double)(df + 1) / 2);
        
        return t;
        
    }
    
    public double tcdf (double lower, double upper, double accuracy){ //calculates the probability of getting a tscore between upper and lower
        
        if(df > 170){ //when df > 170 use the normal model to approximate p
            
            NormalModel nm = new NormalModel(0,1);
            
            return nm.normalcdf(lower, upper, accuracy);
            
        }
        
        double sum = tpdf(lower);

        for (double i = lower + accuracy; i < upper; i += accuracy) {
            sum += 2 * tpdf(i);
        }

        sum += tpdf(upper);
        
        if(sum < 1E-6) System.out.println("P < 1E-6; exact digits may not be accurate");

        return sum * accuracy * 0.5;
        
    }
    
    public double tcdf (double lower, double upper){
        
        return tcdf(lower, upper, 1E-5); //default accuracy
        
    }
    
    public static double gamma(double z){ //gamma(x) - used in t model
        
        //if z is a whole number, use (z-1)!
        if(z % 1 == 0 && z < 171)
            return BinomModel.factorial(z - 1);
        
        //otherwise, use approximation
        /*
         * This uses the Lanscoz Approximation of the Gamma function
         * This implementation is from Robert Sedgewick and Kevin Wayne
         * at Princeton University: (c) 2000 - 2011
         */
        
        double tmp = (z - 0.5) * Math.log(z + 4.5) - (z + 4.5);
        double ser = 1.0 + 76.18009173 /  z
                         - 86.50532033 / (z + 1)
                         + 24.01409822 / (z + 2)
                         - 1.231739516 / (z + 3)
                         + 0.00120858003 / (z + 4)
                         - 0.00000536382 / (z - 5);
        
        return Math.exp(tmp + Math.log(ser * Math.sqrt(2 * Math.PI)));
        
    }
    
    public double invT(double area) {//default accuracy
        
        return invT(area, 0.0000001);
        
    }
    
    public double invT(double area, double accuracy) {//returns tscore for passed prob/area
        
        StudentTModel s = new StudentTModel(df);
        
        double x = 0;
        double tcdf = (area - s.tcdf(-10, x));
        double tpdf = 0 - s.tpdf(x);
        
        while(Math.abs(tcdf) > accuracy){
            x -= tcdf / tpdf;
            tcdf = (area - s.tcdf(-10, x));
            tpdf = 0 - s.tpdf(x);
        }
        
        return x;
        
    }
    
}
