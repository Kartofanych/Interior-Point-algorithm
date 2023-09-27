import java.util.Arrays;
import java.util.Scanner;

public class Input {
    Scanner scanner = new Scanner(System.in);
    int n = 0;
    int[] inputC(){
        System.out.println("Print a vector of coefficients of objective function:");
        int[] C = Arrays.stream(scanner.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        n = C.length;
        return C;
    }

    int[][] inputA(){
        System.out.println("Print a matrix of coefficients of constraint function:");
        int[][] A = new int[n][n];
        for(int i = 0; i < n; i ++){
            A[i] = Arrays.stream(scanner.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }
        return A;
    }

    int[] inputB(){
        System.out.println("Print a vector of right-hand side numbers:");
        int[] B = Arrays.stream(scanner.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        return B;
    }

}
