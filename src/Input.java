import java.util.Arrays;
import java.util.Scanner;

public class Input {
    Scanner scanner = new Scanner(System.in);
    int n = 0;
    int[] inputC(){
        System.out.println("Input the vector of coefficients of objective function:");
        int[] C = Arrays.stream(scanner.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        return C;
    }

    int[][] inputA(){
        System.out.println("Input the number of a constraint functions:");
        n = Integer.parseInt(scanner.nextLine());
        System.out.println("Input the matrix of coefficients of constraint function:");
        int[][] A = new int[n][n];
        for(int i = 0; i < n; i ++){
            A[i] = Arrays.stream(scanner.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }
        return A;
    }

    int[] inputB(){
        System.out.println("Input the vector of right-hand side numbers:");
        int[] B = Arrays.stream(scanner.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        return B;
    }

    int inputApproximation(){
        System.out.println("Input the approximation:");
        return Integer.parseInt(scanner.nextLine());
    }

    public boolean min() {
        System.out.println("Print 'min' if you want to minimize and 'max' if you want to maximize:");
        String s = scanner.nextLine();
        return s.equals("min");
    }
}
