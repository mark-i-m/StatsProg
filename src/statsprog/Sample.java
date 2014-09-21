/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package statsprog;

/**
 *
 * @author Mark
 */

import java.util.ArrayList;

public class Sample {
    
    private ArrayList<Double> s = null;
    private double[] sarr = null;
    
    public Sample (){
        
        s = new ArrayList<Double>();
        sarr = arrlisttoarr(s);
        
    }
    
    public Sample (double [] d){
        
        s = new ArrayList<Double>();
        
        for(double db : d)
            s.add(db);
        
        sarr = arrlisttoarr(s);
        
    }
    
    public void add(Double value){
        
        s.add(value);
        
        sarr = arrlisttoarr(s);
        
    }
    
    public static double[] arrlisttoarr(ArrayList<Double> data) {   // puts the data in the arraylist into an array

        double[] dataa = new double[data.size()];

        for (int i = 0; i < data.size(); i++) {
            dataa[i] = data.get(i);
        }

        return dataa;

    }
    
    public double median() { //returns the median of data stored in the class data array

        return DataStats.median(sarr);

    }
    
    public double mean()  { //returns the mean of the data in the parameter array

        return DataStats.sum(sarr) / sarr.length;

    }
     
    public double sum() { //returns the sum of the data in the class data array

        return DataStats.sum(sarr);

    }
    
    public double sd() {
        
        return DataStats.sd(sarr);
        
    }
    
    public double var() {
        
        return DataStats.var(sarr);
        
    }
    
    public double deviation(double datum) { // returns the deviation of the datum passed from the mean of the class data (array)

        return DataStats.deviation(datum, sarr);

    }
    
    public double q1() {
        
        return DataStats.q1(sarr);
        
    }
    
    public double q3() {
        
        return DataStats.q3(sarr);
        
    }
    
    public double iqr(){
        
        return DataStats.iqr(sarr);
        
    }
    
    public double max() {
        
        return DataStats.max(sarr);
        
    }
    
    public double min(){
        
        return DataStats.min(sarr);
        
    }
    
    public double range(){
        
        return DataStats.range(sarr);
        
    }
    
    public double[] highOutliers(){
        
        return DataStats.highOutliers(sarr);
        
    }
    
    public double[] lowOutliers(){
        
        return DataStats.lowOutliers(sarr);
        
    }
    
    public int n(){
        
        return s.size();
        
    }
    
    public double get(int i){
        
        return s.get(i);
        
    }
    
    public String toString(){
        
        String ret = "{";
        
        for(int i = 0; i < this.sarr.length; i++)
            ret += this.sarr[i] + " ";
        
        return ret + "}";
        
    }
    
}
