import java.math.BigDecimal;
import java.math.RoundingMode;

public class Output {

    void outputResult(double result, int approximation, boolean min){
        if(result == Double.MIN_VALUE) {
            System.out.println("Something went wrong!");
            return;
        }

        if(min) {
            System.out.println("The minimum of the function is " + BigDecimal.valueOf(result).setScale(approximation, RoundingMode.HALF_DOWN));
        }else {
            System.out.println("The maximum of the function is " + BigDecimal.valueOf(result).setScale(approximation, RoundingMode.HALF_DOWN));
        }
    }
}
