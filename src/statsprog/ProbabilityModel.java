/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package statsprog;

/**
 *
 * @author Mark
 */
public class ProbabilityModel { // creates a new probability model

    private double[] X; // this array contains possible values of X
    private double[] P; // this array contains probabilities of x

    public ProbabilityModel(double[] x, double[] p) throws Exception { //creates a new probability model with possible values x and probabilities p

        if (x.length != p.length) {
            throw new Exception("X and P(X) do not match in dimensions");
        }

        X = x;
        P = p;

    }

    public double E() { // the expected value of the model

        double sum = 0;

        for (int i = 0; i < X.length; i++) {
            sum += X[i] * P[i];
        }

        return sum;

    }

    public double SD() { // the SD of the model

        return Math.sqrt(Var());

    }

    public double Var() { // variance of the model

        double e = E();

        double sum = 0;

        for (int i = 0; i < X.length; i++) {
            sum += Math.pow(X[i] - e, 2) * P[i];
        }

        return sum;

    }

    public static double addSD(double SD1, double SD2) { // adds to SDs

        return Math.sqrt(SD1 * SD1 + SD2 * SD2);

    }

    public static double subSD(double SD1, double SD2) { // subtracts to SDs

        return addSD(SD1, SD2);

    }

    public static double multSD(double SD, double C) { // multiplies to SDs

        return Math.sqrt(C * C * SD * SD);

    }
}
