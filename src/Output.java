import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

public class Output {

    void outputResult(Pair<Double, double[]> result, int approximation, boolean min){
        if(result.getFirst() == Double.MIN_VALUE) {
            System.out.println("The method is not applicable!");
            return;
        }

        if(min) {
            System.out.println("The minimum of the function is " + BigDecimal.valueOf(result.getFirst()).setScale(approximation, RoundingMode.HALF_DOWN));
        }else {
            System.out.println("The maximum of the function is " + BigDecimal.valueOf(result.getFirst()).setScale(approximation, RoundingMode.HALF_DOWN));
        }
        System.out.print("The vector x* is ");
        for (int i = 0; i < result.getSecond().length; i++) {
            System.out.print(BigDecimal.valueOf(result.getSecond()[i]).setScale(approximation, RoundingMode.HALF_DOWN)+" ");
        }
    }
}
