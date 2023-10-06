public class Main {
    public static void main(String[] args) {

        Input input = new Input();
        Output output = new Output();
        Solution solution = new Solution();

        input.processInput();
        output.outputResult(solution.solve(input.getC(), input.getA(), input.getB(), false), input.getApproximation(), false);
    }
}

