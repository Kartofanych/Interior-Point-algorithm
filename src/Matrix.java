public class Matrix {
    // Perform matrix subtraction
    public static double[][] subtract(double[][] matrix1, double[][] matrix2) {

        int numRows = matrix1.length;
        int numCols = matrix1[0].length;
        double[][] result = new double[numRows][numCols];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                result[i][j] = matrix1[i][j] - matrix2[i][j];
            }
        }

        return result;
    }

    public static double[] subtract(double[] a, double[] b) {
        int n = a.length;
        double[] result = new double[n];
        for (int i = 0; i < n; i++) {
            result[i] = a[i] - b[i];
        }
        return result;
    }

    // Calculate the transpose of a matrix
    public static double[][] transpose(double[][] matrix) {
        int numRows = matrix.length;
        int numCols = matrix[0].length;
        double[][] result = new double[numCols][numRows];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                result[j][i] = matrix[i][j];
            }
        }

        return result;
    }

    // Calculate the inverse of a square matrix using Gauss-Jordan elimination
    public static double[][] inverse(double[][] matrix) {
        int n = matrix.length;
        if (n != matrix[0].length) {
            throw new IllegalArgumentException("Matrix must be square for inversion.");
        }

        double[][] augmentedMatrix = new double[n][2 * n];

        // Create an augmented matrix [matrix | I]
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                augmentedMatrix[i][j] = matrix[i][j];
                augmentedMatrix[i][j + n] = (i == j) ? 1.0 : 0.0;
            }
        }

        // Perform Gauss-Jordan elimination to transform the left side into the identity matrix
        for (int col = 0; col < n; col++) {
            // Find pivot
            int pivotRow = col;
            for (int i = col + 1; i < n; i++) {
                if (Math.abs(augmentedMatrix[i][col]) > Math.abs(augmentedMatrix[pivotRow][col])) {
                    pivotRow = i;
                }
            }

            // Swap rows
            double[] temp = augmentedMatrix[col];
            augmentedMatrix[col] = augmentedMatrix[pivotRow];
            augmentedMatrix[pivotRow] = temp;

            // Scale the pivot row
            double pivotValue = augmentedMatrix[col][col];
            for (int j = 0; j < 2 * n; j++) {
                augmentedMatrix[col][j] /= pivotValue;
            }

            // Eliminate other rows
            for (int i = 0; i < n; i++) {
                if (i != col) {
                    double factor = augmentedMatrix[i][col];
                    for (int j = 0; j < 2 * n; j++) {
                        augmentedMatrix[i][j] -= factor * augmentedMatrix[col][j];
                    }
                }
            }
        }

        // Extract the right side of the augmented matrix (the inverse of the original matrix)
        double[][] inverseMatrix = new double[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(augmentedMatrix[i], n, inverseMatrix[i], 0, n);
        }

        return inverseMatrix;
    }

    // Perform matrix multiplication
    public static double[][] multiply(double[][] matrix1, double[][] matrix2) {
        int numRows1 = matrix1.length;
        int numCols1 = matrix1[0].length;
        int numRows2 = matrix2.length;
        int numCols2 = matrix2[0].length;

        if (numCols1 != numRows2) {
            throw new IllegalArgumentException("Matrix dimensions are incompatible for multiplication." + numRows1 + " " + numCols1 + " " + numRows2 + " " + numCols2);
        }

        double[][] result = new double[numRows1][numCols2];

        for (int i = 0; i < numRows1; i++) {
            for (int j = 0; j < numCols2; j++) {
                for (int k = 0; k < numCols1; k++) {
                    result[i][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }

        return result;
    }

    public static boolean matrixOptimalCompare(double[][] matrix1, double[][] matrix2, double differenceAccuracy) {

        int numRows = matrix1.length;
        int numCols = matrix1[0].length;

        // Compare each component of the matrices
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (Math.abs(matrix1[i][j] - matrix2[i][j]) > differenceAccuracy) {
                    return false;
                }
            }
        }

        // If all components are within the specified difference accuracy, return true
        return true;
    }

    public static double[] multiply(double[][] a, double[] b) {
        int m = a.length;
        int n = b.length;
        double[] result = new double[m];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                result[i] += a[i][j] * b[j];
            }
        }
        return result;
    }

    public static double[] multiplyScalar(double[] array, double scalar) {
        int n = array.length;
        double[] result = new double[n];
        for (int i = 0; i < n; i++) {
            result[i] = array[i] * scalar;
        }
        return result;
    }

    public static double[] add(double[] a, double[] b) {
        int n = a.length;
        double[] result = new double[n];
        for (int i = 0; i < n; i++) {
            result[i] = a[i] + b[i];
        }
        return result;
    }
    
    public static double findMin(double[] array) {
        double min = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] < min) {
                min = array[i];
            }
        }
        return min;
    }

    public static double norm(double[] array, int ord) {
        double sum = 0;
        for (double value : array) {
            sum += Math.pow(Math.abs(value), ord);
        }
        return Math.pow(sum, 1.0 / ord);
    }
}
