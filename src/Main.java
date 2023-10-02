public class Main {
    public static void main(String[] args) {

        Input input = new Input();
        Output output = new Output();
        Solution solution = new Solution();

        double[] C = input.inputC();
        double[][] A = input.inputA();
        double[] B = input.inputB();
        int approximation = input.inputApproximation();
        boolean min = input.min();

        output.outputResult(solution.solve(C, A, B, min), min);
    }
}

