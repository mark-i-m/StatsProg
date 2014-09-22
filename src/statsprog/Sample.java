package statsprog;

/**
 *
 * @author Mark
 */

import java.util.ArrayList;

/*
 * Represents a sample of data
 */
public class Sample {
    
    private double[] s = null; // the data
    private int n = 0; // the sample size
    
    /**
     * Create a Sample from the data in s
     * @param s the list of data
     */
    public Sample (ArrayList<Double> s){
        
        this.s = DataStats.arrlisttoarr(s);
        n = s.size();
        
    }
    
    /**
     * Create a Sample from the data in s
     * @param s the list of data
     */
    public Sample (double [] s){
        
        this.s = s;
        n = s.length;
        
    }
    
    public Sample () {
    	this.s = new double[1];
    	this.n = 0;
    }
    
    /**
     * Add a datum to the sample
     * @param value the datum
     */
    public void add(Double value){
        
    	double newarr[] = new double[s.length * 2];
    	
        for(int i = 0; i < s.length; i++)
        	newarr[i] = s[i];
           
        newarr[s.length] = value;
        
        s = newarr;
        n++;
        
    }
    
    /**
     * Get the sample size
     * @return the sample size
     */
    public int n(){
        
        return n;
        
    }
    
    /**
     * Get the i-th datum from the sample
     * @param i the datum's index
     * @return the datum
     */
    public double get(int i){
        
    	if(i >= n)
    		throw new ArrayIndexOutOfBoundsException("Attempt to access " + i + "th element of sample of size " + n);
    	
        return s[i];
        
    }
    
    /**
     * Get an array of the data
     * @return the data
     */
    public double[] toArray(){
    	
    	double data[] = new double[n];
    	
    	for(int i = 0; i < n; i++){
    		data[i] = s[i];
    	}
    	
    	return data;
    	
    }
    
    /**
     * Returns a string representation of the Sample
     */
    public String toString(){
        
        String ret = "{";
        
        for(double d : this.s)
            ret += d + " ";
        
        return ret + "}";
        
    }
    
}
