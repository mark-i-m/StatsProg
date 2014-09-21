
package formatdecimal2;

import java.text.*;

public class FormatDecimal2 {
    
    public static String round(int numDec, double num){
        
        String form = "0.";
        
        for (int counter = 0; counter < numDec; counter++)
            
            form = form + "0";
           
        
        NumberFormat dec = new DecimalFormat(form);
        
        return dec.format(num);
        
    }
    
}
