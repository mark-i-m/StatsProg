package statsprog;

/**
 *
 * @author Mark
 */

/**
 * Represents a binomial model with trials trials and prob chance of success
 */
public class BinomModel {

    private int n;  //number of trials
    private double p;//probability of success

    /**
     * Create a new Binomial model with these parameters
     * @param trials the number of trials
     * @param prob the probability of success
     */
    public BinomModel(int trials, double prob) {

        n = trials;
        p = prob;

    }

    /**
     * Calculates the probability of getting exactly x successes
     * @param x the number of success desired
     * @return the probability
     * @throws ArithmeticException Overflow
     */
    public double binompdf(int x) throws ArithmeticException {

        NormalModel nm = new NormalModel(E(),SD());
        
        if (n > 70 || x > 70) { //if there will be an overflow, estimate using normal model
            if (n * p >= 10 && n * (1 - p) >= 10) {
                System.out.println("Must use Normal Model to estimate binomial probability");
                return nm.normalpdf(x); //estimate
            } else {
            	//if conditions are not met
                throw new ArithmeticException("Overflow / No good estimate."); 
            }
        }

        double prob = nCk(x, n);

        prob *= Math.pow((1 - p), n - x) * Math.pow(p, x);

        return prob;

    }

    /**
     * Calculates the probability of getting x or fewer successes
     * @param x the number of successes desired
     * @return the probability
     * @throws ArithmeticException Overflow
     */
    public double binomcdf(int x) throws ArithmeticException{

        NormalModel nm = new NormalModel(E(),SD());
        
        if (n > 70 || x > 70) {
            if (n * p >= 10 && n * (1 - p) >= 10) {
                System.out.println("Must use Normal Model to estimate binomial probability; normal probability will be calculated with 0.001 accuracy");
                return nm.normalcdf(-9999, x);
            } else {
                throw new ArithmeticException("Overflow / No good estimate.");
            }
        }
        
        double sum = 0;

        for (int i = 0; i <= x; i++) {
            sum += binompdf(i);
        }

        return sum;

    }

    /**
     * The expected value of the distribution
     * @return expected value
     */
    public double E() { //expected value of the model

        return n * p;

    }

    /**
     * The std dev of the distribution
     * @return std dev
     */
    public double SD() { //SD of the model

        return Math.sqrt(n * p * (1 - p));

    }

    /**
     * n!
     * @param n the number to factorial-ize
     * @return the factorial
     */
    public static double factorial(double n) { // returns n!

        if (n <= 1) {
            return 1.0;
        } else {
            return n * factorial(n - 1.0);
        }

    }

    /**
     * n choose k -- the number of combinations of
     * k objects out of n objects
     * @param k the number of objects selected
     * @param n the number of objects total
     * @return the number of combinations
     */
    public static double nCk(int k, int n) { //returns n choose k

        return factorial(n) / factorial(k) / factorial(n - k);

    }
}
