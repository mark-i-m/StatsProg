/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package statsprog;

/**
 *
 * @author Mark
 */
public class TwoSampleInferences extends TwoSampleDistribution {//inferences for two sample mean t-model
    
    public TwoSampleInferences(int n1, int n2, double sd1, double sd2, double xbar1, double xbar2){
        
        super(n1,n2,sd1,sd2,xbar1,xbar2);
        
    }
    
    public TwoSampleInferences(Sample s1, Sample s2){
        
        super(s1, s2);
        
    }
    
    public double[] twoSampTInt (double cl){//create a 1-samp t interval with confidence level cl
        
        StudentTModel s = new StudentTModel(df);
        
        double tstar = s.invT(cl + (1.0 - cl) / 2);
        
        double[] CI = new double[2];
        CI[0] = (mean1 - mean2) - tstar * se;
        CI[1] = (mean1 - mean2) + tstar * se;
        
        return CI;
        
    }
    
    public double[] twoSampPooledTInt (double cl){//create a 1-samp t interval with confidence level cl
        
        StudentTModel s = new StudentTModel(n1 + n2 - 2);
        
        double tstar = s.invT(cl + (1.0 - cl) / 2);
        
        double[] CI = new double[2];
        CI[0] = (mean1 - mean2) - tstar * SEp;
        CI[1] = (mean1 - mean2) + tstar * SEp;
        
        return CI;
        
    }
    
    public double twoSampTTest (double munaught, int altH){//runs a 1-samp t test using munaught for the null hypothesis; altH defines the alternate h.
                                                           //munaught is the diffence of means
        StudentTModel s = new StudentTModel(df);
        
        double t = ((mean1 - mean2) - munaught) / se;
        
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
    
    public double twoSampPooledTTest (double munaught, int altH){//runs a 1-samp t test using munaught for the null hypothesis; altH defines the alternate h.
                                                           //munaught is the diffence of means
        StudentTModel s = new StudentTModel(n1 + n2 - 2);
        
        double t = ((mean1 - mean2) - munaught) / SEp;
        
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
