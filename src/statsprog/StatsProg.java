package statsprog;

import getinput.GetInput;

import java.util.ArrayList;

public class StatsProg {

    public static void main(String[] args) throws Exception {       
        
        System.out.println("Normal and Student T probabilities are not accurate past the fifth decimal digit"); //Warning
        
        double [][] data = new double[][]
        {
            {23,20,18,23,20,19,18,21,19,22,24,29}
        };
        
        double [][] data1 = new double[][]
        {
            {320,245,288},
            {98,24,17},
            {18,19,5},
            {17,2,5}
        };
        
        ChiSquareInferences m = new ChiSquareInferences(data1);
        
        System.out.println(m);
        
    }
}

class InpExp {

    public static double[] getDoubles() {

        ArrayList<Double> data = new ArrayList<Double>();

        double inp = GetInput.getDouble("Error! Try again!");

        while (inp != -1E-32) {
            data.add(inp);
            inp = GetInput.getDouble("Error! Try again!");

        }

        return DataStats.arrlisttoarr(data);

    }
  
}
