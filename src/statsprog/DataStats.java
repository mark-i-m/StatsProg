/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package statsprog;

/**
 *
 * @author Mark
 */
import java.util.Arrays;
import java.util.ArrayList;

public class DataStats {
    //this class contains methods for general calculations
    

    public static double[] arrlisttoarr(ArrayList<Double> data) {   // puts the data in the arraylist into an array

        double[] dataa = new double[data.size()];

        for (int i = 0; i < data.size(); i++) {
            dataa[i] = data.get(i);
        }

        return dataa;

    }

    public static double median(double[] data) {  // returns the median of the data in the parameter array

        if (data.length % 2 != 0) {
            return data[(data.length - 1) / 2];
        } else {

            double m1 = data[(data.length - 2) / 2];
            double m2 = data[data.length / 2];

            return (mean(new double[]{m1, m2}));

        }

    }

    public static double mean(double[] data)  { //returns the mean of the data in the parameter array

        return sum(data) / data.length;

    }

    public static double sum(double[] data) { //returns the sum of the data in the parameter array

        double sum = 0;

        for (double d : data) {
            sum += d;
        }

        return sum;

    }

    public static double sd(double[] data){ //returns the SD of the data in the parameter; calculates pop SD, to get df degrees of freedom, use Math.sqrt(var() * n / (n - 1))
        //this is a private/helper method
        
        return Math.sqrt(var(data));

    }

    public static double var(double[] data) { //returns the variance of the data passed with denom passed, similar to the standardDeviation method; also a helper method

        double[] deviations = new double[data.length];

        for (int i = 0; i < data.length; i++) {
            deviations[i] = Math.pow(deviation(data[i], data), 2);
        }

        double sum = sum(deviations);

        return sum / data.length;

    }

    public static double deviation(double datum, double[] data) { // returns the deviation of the datum passed from the mean of the data passed

        return datum - mean(data);

    }

    public static double q1(double[] data) { // returns the first quartile of data passed

        int med;
        double[] ldata;

        if (data.length % 2 != 0) {

            med = (data.length - 1) / 2;

            ldata = new double[(data.length + 1) / 2];

            for (int i = 0; i < ldata.length; i++) {
                ldata[i] = data[i];
            }

            return median(ldata);

        } else {

            med = (data.length - 2) / 2;

            ldata = new double[data.length / 2];

            for (int i = 0; i < ldata.length; i++) {
                ldata[i] = data[i];
            }

            return median(ldata);

        }

    }

    public static double q3(double[] data) { // returns third quartile of data passed

        int med;
        double[] udata;

        if (data.length % 2 != 0) {

            med = (data.length - 1) / 2;

            udata = new double[(data.length + 1) / 2];

            for (int i = med; i < data.length; i++) {
                udata[i - med] = data[i];
            }

            return median(udata);

        } else {

            med = data.length / 2;

            udata = new double[data.length / 2];

            for (int i = med; i < data.length; i++) {
                udata[i - med] = data[i];
            }

            return median(udata);

        }

    }

    public static double iqr(double[] data) { // returns the iqr of the data

        return q3(data) - q1(data);

    }

    public static double max(double[] data) { // returns max of the data

        double max = data[0];

        for (double d : data) {
            if (d > max) {
                max = d;
            }
        }

        return max;

    }

    public static double min(double[] data){ // returns min of the data

        double min = data[0];

        for (double d : data) {
            if (d < min) {
                min = d;
            }
        }

        return min;

    }

    public static double range(double[] data) { // returns the range of the data

        return max(data) - min(data);

    }

    public static double[] highOutliers(double[] data){ // returns the high outliers of the data

        double iqr = iqr(data);
        double q3 = q3(data);
        ArrayList<Double> out = new ArrayList<Double>();

        for (double d : data) {
            if (d > 1.5 * iqr + q3) {
                out.add(d);
            }
        }

        return arrlisttoarr(out);

    }

    public static double[] lowOutliers(double[] data) { //returns the low outliers of the data

        double iqr = iqr(data);
        double q1 = q1(data);
        ArrayList<Double> out = new ArrayList<Double>();

        for (double d : data) {
            if (d < q1 - 1.5 * iqr) {
                out.add(d);
            }
        }

        return arrlisttoarr(out);

    }

}
