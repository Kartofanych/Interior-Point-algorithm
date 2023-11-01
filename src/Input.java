import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class Input {
    Scanner scanner = new Scanner(System.in);
    private double[] C;
    private double[][] A;
    private double[] b;
    private double[] x;
    private int approximation;
    private boolean isMin = false;

    void processInput() {
        System.out.println(
                "Input in format:\n" +
                        "  C[1] ... C[n]\n" +
                        "A[1,1] ... A[1,n]\n" +
                        "       ...\n" +
                        "A[m,1] ... A[m,n]\n" +
                        "  b[1] ... b[m]\n" +
                        "[Approximation]\n" +
                        "x[1] ... x[2 * n] - initial solution"
        );
//        System.out.println("Print a vector of coefficients of objective function:");
        C = Arrays.stream(scanner.nextLine().split(" ")).mapToDouble(Double::parseDouble).toArray();

//        System.out.println("Print a matrix of coefficients of constraint function, vector b and approximation:");
        LinkedList<double[]> AList = new LinkedList<>();
        while (true) {
            double[] line = Arrays.stream(scanner.nextLine().split(" ")).mapToDouble(Double::parseDouble).toArray();
            if (line.length == 1) { // found approximation line
                approximation = (int) line[0];
                b = AList.removeLast(); // means the line before was b
                break;
            }
            AList.add(line);
        }
        A = AList.toArray(new double[0][0]);
        x = Arrays.stream(scanner.nextLine().split(" ")).mapToDouble(Double::parseDouble).toArray();
//        System.out.println("Print `min` if you need to minimize and `max` if you need to maximize:");
//        String minimum = scanner.nextLine();
//        if (minimum.equals("min")) isMin = true;
    }

    public double[] getC() {
        return C;
    }

    public double[][] getA() {
        return A;
    }

    public double[] getB() {
        double[] bb = new double[b.length];
        System.arraycopy(b, 0, bb, 0, b.length);
        return bb;
    }

    public boolean isMin() {
        return isMin;
    }

    public int getApproximation() {
        return approximation;
    }

    public double[] getX() {
        return x;
    }
}
