public class SmallestPositiveSolution {
    public static double [] main(String[] args, double rhsNumber, int numVariables) {

        double [] solution = new double[numVariables];

            for (int i = 0; i < numVariables; i++) {
                double value = Math.min(rhsNumber - i, rhsNumber / (numVariables - i));
                solution[i] = value;
                rhsNumber -= value;
            }
                return solution;
    }
}
