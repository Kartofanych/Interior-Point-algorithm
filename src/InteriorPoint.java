import java.util.Arrays;

public class InteriorPoint {
    void solve(Input input, Output output) {
        
        // Scanner scanner = new Scanner(System.in);
        // System.out.println("Input the initial feasible solution in format:\n" +
        //                         "x[1] ... x[2 * n]");
        // x = Arrays.stream(scanner.nextLine().split(" ")).mapToDouble(Double::parseDouble).toArray();
        // scanner.close();

        System.out.println("Interior method with alpha 0.5:");
        output.outputResult(solve(input.getC(), input.getA(), input.getB(), input.getX(), input.isMin(), 0.5), input.getApproximation(), input.isMin());
        System.out.println("\n");

        System.out.println("Interior method with alpha 0.9:");
        output.outputResult(solve(input.getC(), input.getA(), input.getB(), input.getX(), input.isMin(), 0.9), input.getApproximation(), input.isMin());
    }
    Pair<Double, double[]> solve(double[] con, double[][] a, double[] B, double[] X, boolean min, double al) {
        try {
            int csz = con.length; // number of variables
            int asz = a.length; // number of equations
            double[] oldB = Arrays.copyOf(B, asz);
            double[] C = new double[csz + asz]; // array of constraints
            double alpha = al; // alpha
            for (int i = 0; i < csz + asz; i ++ ) { // adding slack variables to the vector of constraints
                if (i < csz) {
                    C[i] = con[i] * 1.0;
                } else {
                    C[i] = 0.0;
                }
            }

            double[][] A = new double[asz][csz + asz];
            for (int i = 0; i < asz; i ++ ) { // adding slack variables to the matrix A
                double std = 1.0;

                if (B[i] < 0) {
                    std = -1.0;
                }
                B[i] *= std;
                for (int j = 0; j < asz + csz; j ++ ) {
                    A[i][j] = j < csz ? a[i][j] : (j - csz != i ? 0 : 1);
                    A[i][j] *= std;
                }
            }
            double[] x = new double[X.length * 2];
            for (int i = 0; i < X.length; i ++) {
                x[i] = X[i];
                double sum = 0.0;
                for (int j = 0; j < X.length; j ++) {
                    sum += A[i][j] * X[j];
                }
                x[i + X.length] = B[i] - sum;
            }
            while (true) {
                double[] v = Arrays.copyOf(x, x.length); // copy of vector of the feasible point
                double[][] D = new double[x.length][x.length]; // diagonal matrix with feasible solutions on diagonal

                for (int j = 0; j < x.length; j++) {
                    D[j][j] = x[j];
                }

                double[][] AA = Matrix.multiply(A, D); // matrix AD
                double[] cc = Matrix.multiply(D, C); // matrox DC
                double[][] I = new double[x.length][x.length]; // Identity matrix

                for (int j = 0; j < x.length; j++) {
                    I[j][j] = 1.0;
                }
                
                // Calculating P
                double[][] F = Matrix.multiply(AA, Matrix.transpose(AA)); 
                double[][] FI = Matrix.inverse(F);
                double[][] H = Matrix.multiply(Matrix.transpose(AA), FI);
                double[][] P = Matrix.subtract(I, Matrix.multiply(H, AA));
                
                // Calculating cp and finding the least negative element in it
                double[] cp = Matrix.multiply(P, cc);
                double nu = Math.abs(Matrix.findMin(cp));
                double[] one = new double[x.length];

                for (int j = 0; j < x.length; j++) {
                    one[j] = 1.0;
                }

                // Calculating next set of feasible solutions
                double[] y = Matrix.add(one, Matrix.multiplyScalar(cp, alpha / nu));
                double[] yy = Matrix.multiply(D, y);
                x = Arrays.copyOf(yy, yy.length);

                // If the difference between new set, and previous set is too small, then break
                if (Matrix.norm(Matrix.subtract(yy, v), 2) < 0.00001) {
                    break;
                }
            }

            double ans = 0;

            for (int i = 0; i < asz; i++) {
                int sum = 0;
                for (int j = 0; j < csz; j++) {
                    sum += A[i][j] * x[j];
                }
                if (oldB[i] != B[i]) {
                    sum *= -1;
                }
                if ((oldB[i] == B[i] && sum > B[i]) || (oldB[i] != B[i] && -sum < B[i])) {
                    throw new IndexOutOfBoundsException();
                }
            }
            System.out.println();

            for (int j = 0; j < csz; j++) {
                if (x[j] < 0 ) {
                    throw new IndexOutOfBoundsException();
                }
                ans += x[j] * C[j];
            }
            return new Pair<>(ans, Arrays.copyOf(x, csz));
        }catch (Exception err) {
            //error occurred
            return new Pair<>(Double.MIN_VALUE, B);
        }
    }
}
