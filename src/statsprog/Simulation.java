/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package statsprog;

/**
 *
 * @author Mark
 */
public class Simulation { // runs a simulations
    
    private int[] randomDigits; // array of random digits in the interval
    
    public void generateDigits (int trials, int firstDigit, int lastDigit){ //generates a list of random digits in the interval of length trials
        
        randomDigits = new int[trials];
        
        for(int i = 0; i < randomDigits.length; i++){
            
            int n = (int) (Math.random() * 100);
            
            while (n < firstDigit || n > lastDigit)
                n = (int) (Math.random() * 100);
            
            randomDigits[i] = n;
            
        }
        
    }
    
    public double calculateProbability(int x) { // calculates the probability of getting x in the simulation
        
        int count = 0;
        
        for (int i = 0; i < randomDigits.length; i++)
            if (randomDigits[i] == x)
                count++;
        
        return (double) count/randomDigits.length;
        
    }
    
}
