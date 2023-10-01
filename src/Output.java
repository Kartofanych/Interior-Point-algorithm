public class Output {

    void outputResult(double result, boolean min){
        if(result == Double.MIN_VALUE) {
            System.out.println("Something went wrong!");
            return;
        }

        if(min) {
            System.out.println("The minimum of the function is " + result);
        }else {
            System.out.println("The maximum of the function is " + result);
        }
    }
}
