/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package statsprog;

/**
 *
 * @author Mark
 */
public class PairedInferences extends PairedDistribution{ //inferences for paired t-model
    
    public PairedInferences(int n, double sd, double xbar){
        
        super(n,sd,xbar);
        
    }
    
    public PairedInferences(Sample s1, Sample s2){
        
        super(s1, s2);
        
    }
    
    public double[] pairedTInt (double cl){//create a 1-samp t interval with confidence level cl
        
        StudentTModel s = new StudentTModel(n - 1);
        
        double tstar = s.invT(cl + (1.0 - cl) / 2);
        
        double[] CI = new double[2];
        CI[0] = mean - tstar * se;
        CI[1] = mean + tstar * se;
        
        return CI;
        
    }
    
    public double pairedTTest (double munaught, int altH){//runs a 1-samp t test using munaught for the null hypothesis; altH defines the alternate h.
        
        StudentTModel s = new StudentTModel(n - 1);
        
        double t = (mean - munaught) / se;
        
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
    
}
