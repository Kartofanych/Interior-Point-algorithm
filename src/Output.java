import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

public class Output {

    void outputResult(Pair<Double, double[]> result, int approximation, boolean min){
        if(result.getFirst() == Double.MIN_VALUE) {
            System.out.println("Something went wrong!");
            return;
        }

        if(min) {
            System.out.println("The minimum of the function is " + BigDecimal.valueOf(result.getFirst()).setScale(approximation, RoundingMode.HALF_DOWN));
        }else {
            System.out.println("The maximum of the function is " + BigDecimal.valueOf(result.getFirst()).setScale(approximation, RoundingMode.HALF_DOWN));
        }
        System.out.println("The vector x* is " + Arrays.toString(result.getSecond()));
    }
}
