package statsprog;

/**
 *
 * @author Mark
 */
public class TwoPropInferences extends TwoPropDistribution {

    public TwoPropInferences(double p1, double p2, int n1, int n2) {

        super(p1, p2, n1, n2);

    }
    
    public TwoPropInferences(Sample s1, Sample s2){
        
        super (s1,s2);
        
    }

    public double[] twoPropZInt(double cl) {
        
        NormalModel nm = new NormalModel(0,1);
        
        double zstar = nm.invNorm(cl + (1. - cl) / 2);
        
        double[] CI = new double[2];
        CI[0] = (1.0 * x1/n1 - 1.0 * x2/n2) - zstar * SE;
        CI[1] = (1.0 * x1/n1 - 1.0 * x2/n2) + zstar * SE;
        
        return CI;
        
    }
    
    public double[] twoPropPooledZInt(double cl) { //use only if the variances are equal
        
        NormalModel nm = new NormalModel(0,1);
        
        double zstar = nm.invNorm(cl + (1. - cl) / 2);
        
        double[] CI = new double[2];
        CI[0] = (1.0 * x1/n1 - 1.0 * x2/n2) - zstar * SEp;
        CI[1] = (1.0 * x1/n1 - 1.0 * x2/n2) + zstar * SEp;
        
        return CI;
        
    }

    public double twoPropZTest(double pnaught, int altH) {
        
        if(pnaught == 0) return twoPropPooledZTest(pnaught,altH);
        
        NormalModel nm = new NormalModel(0,1);
        
        double p = (1.0 * x1/n1 - 1.0 * x2/n2);
        double z = (p - pnaught)/SE;
        
        switch(altH){
            case 0: // p != pnaught
                return 2 * nm.normalcdf((z < 0) ? -10 : z, (z < 0) ? z : 10);
         
            case 1: // p < pnaught
                return nm.normalcdf(-30, z);
            
            case 2: // p > pnaught
                return nm.normalcdf(z, 30);
            
            default: //impossible; used for coding purposes
                return 0;    
        }
        
    }
    
    public double twoPropPooledZTest(double pnaught, int altH) {
        
        NormalModel nm = new NormalModel(0,1);
        
        double p = (1.0 * x1/n1 - 1.0 * x2/n2);
        double z = (p - pnaught)/SEp;
        
        switch(altH){
            case 0: // p != pnaught
                return 2 * nm.normalcdf((z < 0) ? -10 : z, (z < 0) ? z : 10);
         
            case 1: // p < pnaught
                return nm.normalcdf(-30, z);
            
            case 2: // p > pnaught
                return nm.normalcdf(z, 30);
            
            default: //impossible; used for coding purposes
                return 0;    
        }
        
    }
    
}
