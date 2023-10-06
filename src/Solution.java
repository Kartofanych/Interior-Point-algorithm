public class Solution {
    
    double solve(double[] con, double[][] a, double[] B, boolean min) {

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
                for (int j = 0; j < asz + csz; j ++ ) {
                    A[i][j] = j < csz ? a[i][j] : (j - csz != i ? 0 : 1);
                }
            }

            int[] basis = new int[asz];

            for (int i = 0 ; i < asz; i ++ ) { // forming the basis
                basis[i] = i + csz;
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
                        if (B[i] / A[i][enteringVar] > 0 && (B[i] / A[i][enteringVar] < leavingVarVal || leavingVar == -1)) {
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

            return ans;
        }catch (Exception err) {
            //error occurred
            return Double.MIN_VALUE;
        }
    }
}
