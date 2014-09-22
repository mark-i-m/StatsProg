package statsprog;

/**
 *
 * @author Mark
 */
import java.util.Arrays;

/**
 * Represents chi-square inference tests, storing
 * data and chi-square inference information
 */
public class ChiSquareInferences {
    
    private double[][] data;//data
    private double[][] x_sq;//chi-sq for each cell
    
    /**
     * Create a new chi-square inference object with the data
     * @param data a table of data
     */
    public ChiSquareInferences(double[][] data){
        
        this.data = data;
        this.x_sq = new double[data.length][data[0].length];
        
    }
    
    /**
     * Chi-square goodness-of-fit test. Tests the hypothesis that
     * all categories are equally distributed.
     * @return the p-value
     */
    public double GOFTest(){
        
        double[] model = new double[data[0].length];
        
        for(int i = 0; i < model.length; i++)
            model[i] = 1.0 / model.length;
        
        //passes a null model in which all categories are equally distributed
        return GOFTest(model);
        
    }
    
    /**
     * Tests the hypothesis that model fits the data
     * @param model the model. The model consists of the proportions
     * for the categories
     * @return the p-value
     */
    public double GOFTest(double[] model){
        
        double[] exp = new double[data[0].length];
        double chi_sq = 0;
        ChiSquareModel c = new ChiSquareModel(data[0].length - 1);
        
        double sum = 0;
        for(double d : data[0])
            sum += d;
        
        for(int i = 0; i < data[0].length; i++)
            exp[i] = model[i] * sum;                //generate exp counts
        
        for(int i = 0; i < data[0].length; i++){
            x_sq[0][i] = Math.pow(data[0][i] - exp[i], 2) / exp[i];
            chi_sq += x_sq[0][i];
        }
        
        return 1 - c.chicdf(chi_sq);
        
    }
    
    /**
     * Tests the hypothesis that the categories are independent.
     * @return the p-value
     */
    public double Hom_IndTest(){
        
        double[][] exp = new double[data.length][data[0].length];
        double chi_sq = 0;
        ChiSquareModel m = new ChiSquareModel((data.length) * (data[0].length));
        
        double[] rowTotals = new double[data.length];
        double[] colTotals = new double[data[0].length];
        double total = 0;
        
        //calculates marginal distribution
        for(int r = 0; r < data.length; r++){
            
            double sum = 0;
            
            for(int c = 0; c < data[0].length; c++)
                sum += data[r][c];
            
            rowTotals[r] = sum;
            total += sum;                   //also calculates total
            
        }
      
        //calculates other marginal distribution
        for(int c = 0; c < data[0].length; c++){
            
            double sum = 0;
            
            for(int r = 0; r < data.length; r++)
                sum += data[r][c];
            
            colTotals[c] = sum;
        }
        
        for(int r = 0; r < data.length; r++)//generates exp
            for(int c = 0; c < data[0].length; c++)
                exp[r][c] = rowTotals[r] * colTotals[c] / total;
        
        for(int r = 0; r < data.length; r++)
            for(int c = 0; c < data[0].length; c++){
                x_sq[r][c] = Math.pow(data[r][c] - exp[r][c], 2) / exp[r][c];
                chi_sq += x_sq[r][c];
            }
        
        return 1 - m.chicdf(chi_sq);
        
    }
    
    /**
     * Return the matrix of chi-square values
     * @return the matrix
     */
    public double[][] getX_Sq(){
        
        return x_sq;
        
    }
    
    /**
     * Returns a string representation of this inference test object
     */
    public String toString(){
        
        String s = "";
        
        s += "(GOF) p: " + GOFTest() + "\n";
        s += Arrays.toString(x_sq[0]) + "\n\n";
        s += "(Hom/Ind) p: " + Hom_IndTest() + "\n";
        
        for(double[] d : x_sq)
            s += Arrays.toString(d) + "\n";
        
        return s;
        
    }
    
}
