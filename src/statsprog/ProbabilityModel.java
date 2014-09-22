package statsprog;

/**
 *
 * @author Mark
 */

/**
 * Represents a discrete finite probability model / a finite discrete random variable
 */
public class ProbabilityModel {

    private double[] X; //possible values x of X
    private double[] P; //P(X == x)

    /**
     * Create a probability model with possible values x and
     * probabilities p
     * @param x the possible values
     * @param p the probabilities corresponding to the values
     * @throws IllegalArgumentException if the lengths of x and p are not the same
     */
    public ProbabilityModel(double[] x, double[] p) throws IllegalArgumentException {

        if (x.length != p.length) {
            throw new IllegalArgumentException("X and P(X) do not match in dimensions");
        }

        X = x;
        P = p;

    }

    /**
     * Calculate the expected value of the random variable
     * @return the expected value
     */
    public double E() {

        double sum = 0;

        for (int i = 0; i < X.length; i++) {
            sum += X[i] * P[i];
        }

        return sum;

    }

    /**
     * Calculate the std dev of the random variable
     * @return the std dev
     */
    public double SD() {

        return Math.sqrt(Var());

    }

    /**
     * Calculate the variance of the random variable
     * @return the variance
     */
    public double Var() {

        double e = E();

        double sum = 0;

        for (int i = 0; i < X.length; i++) {
            sum += Math.pow(X[i] - e, 2) * P[i];
        }

        return sum;

    }

    /**
     * Add the two std devs
     * @param SD1 the first std dev
     * @param SD2 the second std dev
     * @return the sum of the std dev
     */
    public static double addSD(double SD1, double SD2) {

        return Math.sqrt(SD1 * SD1 + SD2 * SD2);

    }

    /**
     * Subtract the two std devs
     * @param SD1 the first std dev
     * @param SD2 the second std dev
     * @return the difference of the std dev
     */
    public static double subSD(double SD1, double SD2) {

        return addSD(SD1, SD2);

    }

    /**
     * Scale the std devs by a factor C
     * @param SD the std dev
     * @param C a constant scale factor
     * @return the scaled std dev
     */
    public static double multSD(double SD, double C) {

        return Math.sqrt(C * C * SD * SD);

    }
}
