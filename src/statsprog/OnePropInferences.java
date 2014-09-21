/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package statsprog;

/**
 *
 * @author Mark
 */
public class OnePropInferences extends OnePropDistribution { // creates a sampling dist for inferences using phat and sample size n
    
    public OnePropInferences(double phat, int n){ // sd variable will contain the se
        
        super(phat, n);
        
    }
    
    public OnePropInferences(Sample s){
        
        super(s);
        
    }
    
    public double[] onePropZInt (double cl){// creates a 1-prop z interval with confidence level cl 
        
        NormalModel nm = new NormalModel(0,1);
        
        double zstar = nm.invNorm(cl + (1. - cl) / 2);
        
        double[] CI = new double[2];
        CI[0] = p - zstar * se;
        CI[1] = p + zstar * se;
        
        return CI;
        
    }
    
    public double onePropZTest (double pnaught, int altH){ //runs a 1-prop z-test using pnaught for the null hyp.; altH defines the alternate hyp.
        
        NormalModel nm = new NormalModel(0,1);
        
        double z = (p - pnaught) / Math.sqrt(pnaught * (1- pnaught) / n);
        
        switch(altH){
            case 0: // p != pnaught
                return 2 * nm.normalcdf((z < 0) ? -10 : z, (z < 0) ? z : 10);
         
            case 1: // p < pnaught
                return nm.normalcdf(-9, z);
            
            case 2: // p > pnaught
                return nm.normalcdf(z, 9);
            
            default: //impossible; used for coding purposes
                return 0;    
        }
        
    }
    
}
