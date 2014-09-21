package statsprog;

import cern.jet.stat.Gamma;

/**
 *
 * @author Mark
 */
public class ChiSquareModel { //creates a chi-square model
    
    private double df;
    
    public ChiSquareModel(double df){
        
        this.df = df;
        
    }
    
    public double chipdf(double x){
        
        double pdf = Math.pow(Math.E, 0 - x / 2);
        
        pdf *= Math.pow(x, df / 2 - 1);
        
        pdf /= Math.pow(2, df / 2);
        
        pdf /= Gamma.gamma(df / 2);
        
        return pdf;
        
    }
    
   public double chicdf (double lower, double upper, double accuracy){ //calculates the probability of getting a tscore between upper and lower
        
        double sum = chipdf(lower);

        for (double i = lower + accuracy; i < upper; i += accuracy) {
            sum += 2 * chipdf(i);
        }

        sum += chipdf(upper);
        
        if(sum < 1E-6) System.out.println("P < 1E-6; exact digits may not be accurate");

        return sum * accuracy * 0.5;
        
    }
    
     public double chicdf (double lower, double upper){
        
        return chicdf(lower, upper, 1E-5); //default accuracy
        
    }
     
     public double chicdf(double upper){
         
         return chicdf(0, upper);
         
     }

     public double invT(double area) {//default accuracy
        
        return invT(area, 0.0000001);
        
    }
    
    public double invT(double area, double accuracy) {//returns tscore for passed prob/area
        
        StudentTModel s = new StudentTModel(df);
        
        double x = 0;
        double tcdf = (area - s.tcdf(-20, x));
        double tpdf = 0 - s.tpdf(x);
        
        while(Math.abs(tcdf) > accuracy){
            x -= tcdf / tpdf;
            tcdf = (area - s.tcdf(-20, x));
            tpdf = 0 - s.tpdf(x);
        }
        
        return x;
        
    }
    
}
