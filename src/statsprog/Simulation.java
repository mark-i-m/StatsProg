package statsprog;

/**
 *
 * @author Mark
 */

/**
 * Represents a simulation
 */
public class Simulation {
    
    private int[] randomDigits; // array of random digits in the interval
    private int upper;
    private int lower; // the upper and lower bounds of the Simulation
    
    /**
     * Create a simulation with upper and lower bounds
     * @param upper the upper bounds
     * @param lower the lower bounds
     */
    public Simulation(int upper, int lower){
    	
    	this.upper = upper;
    	this.lower = lower;
    	
    }
    
    /**
     * Get a random integer in the interval [lower, upper)
     * @param lower the lower bound
     * @param upper the upper bound
     * @return the random integer
     */
    public static int randInt(int lower, int upper) {
    	
    	return lower + (int) (Math.random() * (upper - lower));
    	
    }
    
    /**
     * Generate trials random trials into the randomDigits array
     * @param trials the number of trials
     */
    public void generateDigits (int trials){
        
    	if(upper < lower)
    		throw new IllegalArgumentException("upper < lower");
    	
        randomDigits = new int[trials];
        
        for(int i = 0; i < randomDigits.length; i++){
            randomDigits[i] = randInt(lower, upper);
        }
        
    }
    
    /**
     * Calculate the probability of getting x in the simulation
     * @param x the datum
     * @return the probability
     */
    public double calculateProbability(int x) {
    	
        int count = 0;
        
        for (int i = 0; i < randomDigits.length; i++)
            if (randomDigits[i] == x)
                count++;
        
        return (double) count/randomDigits.length;
        
    }
    
}
