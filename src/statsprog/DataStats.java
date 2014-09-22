package statsprog;

/**
 *
 * @author Mark
 */
import java.util.ArrayList;

/**
 * Contains static methods for general statistical calculations
 */
public class DataStats {
    
	/**
	 * Converts an arraylist of doubles to an array.
	 * @param data the arraylist
	 * @return the array
	 */
    public static double[] arrlisttoarr(ArrayList<Double> data) {

        double[] dataa = new double[data.size()];

        for (int i = 0; i < data.size(); i++) {
            dataa[i] = data.get(i);
        }

        return dataa;

    }

    /**
     * Calculates the median of the data.
     * @param data the data array
     * @return the median
     */
    public static double median(double[] data) {

        if (data.length % 2 != 0) {
            return data[(data.length - 1) / 2];
        } else {

            double m1 = data[(data.length - 2) / 2];
            double m2 = data[data.length / 2];

            return (mean(new double[]{m1, m2}));

        }

    }

    /**
     * Calculates the arithmetic mean of the data
     * @param data the data array
     * @return the mean
     */
    public static double mean(double[] data)  {

        return sum(data) / data.length;

    }

    /**
     * Calculates the sum of the data
     * @param data the data array
     * @return the sum
     */
    public static double sum(double[] data) {

        double sum = 0;

        for (double d : data) {
            sum += d;
        }

        return sum;

    }

    /**
     * Calculates the std dev of the data
     * @param data the data array
     * @return the std dev
     */
    public static double sd(double[] data){
        
        return Math.sqrt(var(data));

    }

    /**
     * Calculates the variance of the data
     * @param data the data array
     * @return the variance
     */
    public static double var(double[] data) {

        double[] deviations = new double[data.length];

        for (int i = 0; i < data.length; i++) {
            deviations[i] = Math.pow(deviation(data[i], data), 2);
        }

        double sum = sum(deviations);

        return sum / data.length;

    }

    /**
     * Calculates the deviation of a datum from the mean
     * of the data
     * @param datum the datum
     * @param data the data array
     * @return the deviation
     */
    public static double deviation(double datum, double[] data) {

        return datum - mean(data);

    }

    /**
     * Calculates the first quartile of the data
     * @param data the data array
     * @return the first quartile
     */
    public static double q1(double[] data) {

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

    /**
     * Calculates the third quartile of the data
     * @param data the data array
     * @return the third quartile
     */
    public static double q3(double[] data) {

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

    /**
     * Calculates the interquartile range of the data
     * @param data the data array
     * @return the IQR
     */
    public static double iqr(double[] data) {
    	
        return q3(data) - q1(data);

    }

    /**
     * Finds the maximum of the data
     * @param data the data array
     * @return the maximum
     */
    public static double max(double[] data) {

        double max = data[0];

        for (double d : data) {
            if (d > max) {
                max = d;
            }
        }

        return max;

    }

    /**
     * Finds the minimum of the data
     * @param data the data array
     * @return the minimum
     */
    public static double min(double[] data){

        double min = data[0];

        for (double d : data) {
            if (d < min) {
                min = d;
            }
        }

        return min;

    }

    /**
     * Calculates the range of the data
     * @param data the data array
     * @return the range
     */
    public static double range(double[] data) {

        return max(data) - min(data);

    }

    /**
     * Returns a list of high outliers. High outliers
     * are those at least 1.5*IQR above the third
     * quartile.
     * @param data the data array
     * @return the list of high outliers
     */
    public static double[] highOutliers(double[] data){

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

    /**
     * Returns a list of low outliers. Low outliers
     * are those at least 1.5*IQR below the first
     * quartile.
     * @param data the data array
     * @return the list of low outliers
     */
    public static double[] lowOutliers(double[] data) {

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
