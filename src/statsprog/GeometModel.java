/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package statsprog;

/**
 *
 * @author Mark
 */
public class GeometModel { // creates a gemotric model
    
    private double p;   // probability of success

    public GeometModel(double prob) {   //creates a geometric model with probability of success p

        p = prob;

    }

    public double geompdf(int n) { // returns the probability of getting first success on nth trial

        return Math.pow(1 - p, n - 1) * p;

    }

    public double geomcdf(int n) { //returns the probability of getting first success on nth trial or earlier

        double sum = 0;

        for (int i = 1; i <= n; i++) {
            sum += geompdf(i);
        }

        return sum;

    }
    
    public double E(){ //returns the expected value of the model
        
        return 1 / p;
        
    }
    
    public double SD(){//returns the SD of the model
        
        return Math.sqrt(p / (1 - p) / (1 - p));
    }
    
}
