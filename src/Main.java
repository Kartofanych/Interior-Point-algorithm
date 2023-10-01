public class Main {
    public static void main(String[] args) {

        Input input = new Input();
        Output output = new Output();
        Solution solution = new Solution();

        int[] C = input.inputC();
        int[][] A = input.inputA();
        int[] B = input.inputB();
        int approximation = input.inputApproximation();
        boolean min = input.min();

        output.outputResult(solution.solve(C, A, B, min), min);
    }
}

