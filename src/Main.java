public class Main {
    public static void main(String[] args) {

        Input input = new Input();
        Output output = new Output();
        InteriorPoint solution = new InteriorPoint();
        Solution simplex = new Solution();

        input.processInput();
        solution.solve(input, output);
        simplex.solve(input, output);
    }
}

