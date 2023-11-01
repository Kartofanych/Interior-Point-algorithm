public class Solution {
    void solve(Input input, Output output) {
        System.out.println("Simplex: ");
        output.outputResult(solve(input.getC(), input.getA(), input.getB(), input.isMin()), input.getApproximation(), input.isMin());
        System.out.println("\n");
    }
    Pair<Double, double[]> solve(double[] con, double[][] a, double[] B, boolean min) {

        try {
            int csz = con.length; // number of variables
            int asz = a.length; // number of equations
            double[] C = new double[csz + asz]; // array of constraints
            for (int i = 0; i < csz + asz; i ++ ) { // adding slack variables to the vector of constraints
                if (i < csz) {
                    C[i] = con[i];
                } else {
                    C[i] = 0;
                }
            }

            double[][] A = new double[asz][csz + asz];
            for (int i = 0; i < asz; i ++ ) { // adding slack variables to the matrix A
                int std = 1;
                if (B[i] < 0) {
                    std = -1;
                    B[i] *= -1;
                }
                for (int j = 0; j < asz + csz; j ++ ) {
                    A[i][j] = j < csz ? a[i][j] : (j - csz != i ? 0 : 1);
                    A[i][j] *= std;
                }
            }

            int[] basis = new int[asz];

            for (int i = 0 ; i < asz; i ++ ) {
                basis[i] = -1;
            }

            for (int i = 0 ; i < asz; i ++ ) { // forming the basis
                for (int j = 0 ; j < asz + csz; j ++ ) {
                    if (A[i][j] == 1) {
                        boolean chk = true;
                        for (int k = 0; k < asz; k ++ ) {
                            if ( k != i && A[k][j] != 0) {
                                chk = false;
                                break;
                            }
                        }
                        if ( chk ) {
                            basis[i] = j;
                            break;
                        }
                    }
                }
            }

            for (int  i = 0; i < asz; i ++ ) {
                if ( basis[i] == -1 ) {
                    throw new IndexOutOfBoundsException();
                }
            }

            int n = A.length, m = A[0].length; // dimensions of table

            boolean optimal = false; // keeps track of optimality

            int mul = 1;

            if (min) {
                mul = -1;
            }

            while (!optimal) {

                double[] relativeProfit = new double[m]; // Cj-Zj

                optimal = true;

                double enteringVarValue = 0;
                int enteringVar = 0;

                // calculating Cj-Zj
                for (int i = 0; i < m; i++) {
                    double zj = 0;
                    for (int j = 0; j < n; j++) {
                        zj += C[basis[j]] * A[j][i];
                    }
                    relativeProfit[i] = C[i] - zj;
                    relativeProfit[i] *= mul;

                    if (relativeProfit[i] > 0) {
                        optimal = false;
                        if (enteringVarValue < relativeProfit[i]) {
                            enteringVarValue = relativeProfit[i];
                            enteringVar = i;
                        }
                    }
                }

                if (!optimal) { // if our values are not optimal, we perform the change of basis

                    double leavingVarVal = -1;
                    int leavingVar = -1;

                    for (int i = 0; i < n; i++) { // find the leaving variable
                        if (B[i] / A[i][enteringVar] >= 0 && (B[i] / A[i][enteringVar] < leavingVarVal || leavingVar == -1)) {
                            leavingVar = i;
                            leavingVarVal = B[i] / A[i][enteringVar];
                        }
                    }

                    if (leavingVar == -1) {
                        throw new IndexOutOfBoundsException();
                    }


                    basis[leavingVar] = enteringVar; // change the basis in the table

                    for (int i = 0; i < m; i++) { // divide pivot row by pivot element
                        if (i != enteringVar){
                            A[leavingVar][i] /= A[leavingVar][enteringVar];
                        }
                    }
                    B[leavingVar] /= A[leavingVar][enteringVar];
                    A[leavingVar][enteringVar] = 1;

                    for (int i = 0; i < n; i++) { // make all rows except the pivot row have zeroes in the pivot column
                        for (int j = 0; j < m; j++) {
                            if (i != leavingVar && j != enteringVar) {
                                A[i][j] -= A[i][enteringVar] * A[leavingVar][j];
                            }
                        }
                        if (i != leavingVar) {
                            B[i] -= A[i][enteringVar] * B[leavingVar];
                        }
                        if (i != leavingVar) {
                            A[i][enteringVar] = 0;
                        }
                    }
                }
            }

            double ans = 0;

            for (int i = 0; i < n; i++) { // subsitute variables into the objective function, and calculate the answer
                ans += C[basis[i]] * B[i];
            }

            double[] xs = new double[csz];
            for (int i = 0; i < n; i++) {
                if (C[basis[i]] == 0)
                    continue; // if the variable does not contribute to the answer, drop it by default
                xs[basis[i]] = B[i];
            }

            return new Pair<>(ans, xs);
        }catch (Exception err) {
            //error occurred
            return new Pair<>(Double.MIN_VALUE, B);
        }
    }
}
