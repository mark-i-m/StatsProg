package statsprog;

/**
 *
 * @author Mark
 */
public class BinomModel { //creates a binomial model

    private int n;  //number of trials
    private double p;//probability of success

    public BinomModel(int trials, double prob) { //creates a new binomial model with n trials and probability of success p

        n = trials;
        p = prob;

    }

    public double binompdf(int x) throws Exception { // probability of getting x success in n trials

        NormalModel nm = new NormalModel(E(),SD());
        
        if (n > 70 || x > 70) { //if there will be an overflow, estimate using normal model
            if (n * p >= 10 && n * (1 - p) >= 10) {
                System.out.println("Must use Normal Model to estimate binomial probability");
                return nm.normalpdf(x); //estimate
            } else {
                throw new Exception("Overflow / No good estimate."); //if conditions are not met, return error
            }
        }

        double prob = nCk(x, n);

        prob *= Math.pow((1 - p), n - x) * Math.pow(p, x);

        return prob;

    }

    public double binomcdf(int x) throws Exception{ // probabilty of getting x success in n trials 

        NormalModel nm = new NormalModel(E(),SD());
        
        if (n > 70 || x > 70) {
            if (n * p >= 10 && n * (1 - p) >= 10) {
                System.out.println("Must use Normal Model to estimate binomial probability; normal probability will be calculated with 0.001 accuracy");
                return nm.normalcdf(-9999, x);
            } else {
                throw new Exception("Overflow / No good estimate.");
            }
        }
        
        double sum = 0;

        for (int i = 0; i <= x; i++) {
            sum += binompdf(i);
        }

        return sum;

    }

    public double E() { //expected value of the model

        return n * p;

    }

    public double SD() { //SD of the model

        return Math.sqrt(n * p * (1 - p));

    }

    public static double factorial(double n) { // returns n!

        if (n <= 1) {
            return 1.0;
        } else {
            return n * factorial(n - 1.0);
        }

    }

    public static double nCk(int k, int n) { //returns n choose k

        return factorial(n) / factorial(k) / factorial(n - k);

    }
}
