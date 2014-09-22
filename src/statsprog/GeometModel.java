package statsprog;

/**
 *
 * @author Mark
 */

/*
 * Represents a geometric distribution with probability of success prob
 */
public class GeometModel {
    
    private double p;   // probability of success

    /**
     * Create a geometric model with probability of success prob
     * @param prob the probability of success
     */
    public GeometModel(double prob) {

        p = prob;

    }

    /**
     * Calculates the probability of getting the first success
     * on the n-th trial.
     * @param n the trial number
     * @return the probability
     */
    public double geompdf(int n) {

        return Math.pow(1 - p, n - 1) * p;

    }

    /**
     * Calculates the probability of success on or before the n-th
     * trial.
     * @param n the number of trials
     * @return the probability
     */
    public double geomcdf(int n) {

        return 1 - Math.pow(1-p, n);

    }
    
    /**
     * Calculates expected value of this model 
     * @return the expected value
     */
    public double E(){
 
        return 1 / p;
        
    }
    
    /**
     * Calculates the std dev of this model
     * @return the std dev
     */
    public double SD(){
        
        return Math.sqrt(p / (1 - p) / (1 - p));
    }
    
}