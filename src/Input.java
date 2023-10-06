import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class Input {
    Scanner scanner = new Scanner(System.in);
    private double[] C;
    private double[][] A;
    private double[] b;
    private int approximation;

    void processInput() {
        System.out.println(
                "Input in format:\n" +
                "  C[1] ... C[n]\n" +
                "A[1,1] ... A[1,n]\n" +
                "       ...\n" +
                "A[m,1] ... A[m,n]\n" +
                "  b[1] ... b[m]\n" +
                "[Number of digits after floating point]"
        );
        C = Arrays.stream(scanner.nextLine().split(" ")).mapToDouble(Double::parseDouble).toArray();

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

//        Debug
//        System.out.println("\nC:");
//        for (double i : C) {
//            System.out.print(i + " ");
//        }
//        System.out.println("\nApprox:");
//        System.out.println(approximation + "\nb:");
//        for (double i : b) {
//            System.out.print(i + " ");
//        }
//        System.out.println("\nA:");
//        for (double[] i : A) {
//            for (double j : i) {
//                System.out.print(j + " ");
//            }
//            System.out.println();
//        }
//        System.out.println();
    }

    public double[] getC() {
        return C;
    }

    public double[][] getA() {
        return A;
    }

    public double[] getB() {
        return b;
    }

    public int getApproximation() {
        return approximation;
    }
}
