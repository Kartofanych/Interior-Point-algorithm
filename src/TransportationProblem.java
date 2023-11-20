import java.util.Arrays;
import java.util.Scanner;

public class TransportationProblem {

    public static void solve() {
        
        Scanner scanner = new Scanner(System.in);
        int n; // Number of supplies
        int m; // Number of demands

        System.out.println("Input the number of supplies:");
        n = scanner.nextInt();
        System.out.println("Input the number of demands:");
        m = scanner.nextInt();

        int[] supply = new int[n];
        int[] demand = new int[m];
        int[][] costs = new int[n][m];

        System.out.println("Input nodes price in format:\n"+
                "c[1,1] ... c[1,n]\n" +
                "       ...\n" +
                "c[m,1] ... c[m,n]\n"
            );
        for (int i = 0 ; i < n; i ++) {
            for (int j = 0; j < m; j ++) {
                costs[i][j] = scanner.nextInt();
            }
        }
        System.out.println("Input supplies vector in format:\n"+
                "  s[1] ... s[n]"
        );
        for(int i = 0; i < n; i ++) {
            supply[i] = scanner.nextInt();
        }
        System.out.println("Input demands vector in format:\n"+
                "  d[1] ... d[m]"
        );
        for(int i = 0; i < m; i ++) {
            demand[i] = scanner.nextInt();
        }

        scanner.close();

        // Check if the problem is balanced
        if (!isBalanced(supply, demand)) {
            System.out.println("The problem is not balanced!");
            return;
        }

        // Print input parameter table
        printInputTable(supply, demand, costs);

        // North-West Corner Method
        int[][] northwestSolution = northWestCornerMethod(supply.clone(), demand.clone(), costs.clone());
        System.out.println("\nNorth-West Corner Method:");
        printSolution(northwestSolution);

        System.out.println("\nVogel's Approxiamtion Method:");
        printSolution(VogelsApproximationMethod(supply.clone(), demand.clone(), costs.clone()));

        System.out.println("\nRussel's Approxiamtion Method:");
        printSolution(RussellsApproximationMethod(costs.clone(), supply.clone(), demand.clone()));

    }

    // Check if the transportation problem is balanced
    private static boolean isBalanced(int[] supply, int[] demand) {
        return Arrays.stream(supply).sum() == Arrays.stream(demand).sum();
    }

    // Print the input parameter table
    private static void printInputTable(int[] supply, int[] demand, int[][] costs) {
        System.out.println("\nInput Parameter Table:");
        System.out.print("        ");
        for (int d : demand) {
            System.out.printf("%8d", d);
        }
        System.out.println();
        for (int i = 0; i < supply.length; i++) {
            System.out.printf("%8d", supply[i]);
            for (int j = 0; j < demand.length; j++) {
                System.out.printf("%8d", costs[i][j]);
            }
            System.out.println();
        }
    }

    // Print the solution vectors
    private static void printSolution(int[][] solution) {
        for (int i = 0; i < solution.length; i++) {
            System.out.print("        ");
            for (int j = 0; j < solution[i].length; j++) {
                System.out.printf("%8d", solution[i][j]);
            }
            System.out.println();
        }
    }

    // North-West Corner Method to find initial basic feasible solution
    private static int[][] northWestCornerMethod(int[] supply, int[] demand, int[][] costs) {
        int[][] solution = new int[supply.length][demand.length];
        int i = 0, j = 0;

        while (i < supply.length && j < demand.length) {
            int quantity = Math.min(supply[i], demand[j]);
            solution[i][j] = quantity;
            supply[i] -= quantity;
            demand[j] -= quantity;

            if (supply[i] == 0) {
                i++;
            }
            if (demand[j] == 0) {
                j++;
            }
        }
        return solution;
    }

    // Vogel's Approximation method
    public static int[][] VogelsApproximationMethod(int[] supply, int[] demand, int[][] grid) {
        int INF = 1000;
        int n = grid.length;
        int m = grid[0].length;
        int[][] solution = new int[supply.length][demand.length];

        int[][] newGrid = new int[n][m];
        for (int i = 0; i < n; i++) {
            newGrid[i] = Arrays.copyOf(grid[i], m);
        }


        int[] rowDiff = new int[n];
        int[] colDiff = new int[m];
        while (Arrays.stream(supply).max().getAsInt() != 0 || Arrays.stream(demand).max().getAsInt() != 0) {
            findDiff(newGrid, rowDiff, colDiff);

            int max1 = Arrays.stream(rowDiff).max().getAsInt();
            int max2 = Arrays.stream(colDiff).max().getAsInt();

            if (max1 >= max2) {
                for (int index = 0; index < rowDiff.length; index++) {
                    if (rowDiff[index] == max1) {
                        int min1 = Arrays.stream(newGrid[index]).min().getAsInt();
                        for (int index2 = 0; index2 < newGrid[index].length; index2++) {
                            if (newGrid[index][index2] == min1) {
                                int min2 = Math.min(supply[index], demand[index2]);
                                solution[index][index2] += min2;

                                supply[index] -= min2;
                                demand[index2] -= min2;

                                if (demand[index2] == 0) {
                                    for (int r = 0; r < n; r++) {
                                        newGrid[r][index2] = INF;
                                    }
                                } else {
                                    Arrays.fill(newGrid[index], INF);
                                }
                                break;
                            }
                        }
                        break;
                    }
                }
            } else {
                for (int index = 0; index < colDiff.length; index++) {
                    if (colDiff[index] == max2) {
                        int min1 = INF;
                        for (int j = 0; j < n; j++) {
                            min1 = Math.min(min1, newGrid[j][index]);
                        }
                        for (int index2 = 0; index2 < n; index2++) {
                            int val2 = newGrid[index2][index];
                            if (val2 == min1) {
                                int min2 = Math.min(supply[index2], demand[index]);
                                solution[index2][index] += min2;

                                supply[index2] -= min2;
                                demand[index] -= min2;

                                if (demand[index] == 0) {
                                    for (int r = 0; r < n; r++) {
                                        newGrid[r][index] = INF;
                                    }
                                } else {
                                    Arrays.fill(newGrid[index2], INF);
                                }
                                break;
                            }
                        }
                        break;
                    }
                }
            }
        }
        return solution;
    }

    public static void findDiff(int[][] grid, int[] rowDiff, int[] colDiff) {
        for (int i = 0; i < grid.length; i++) {
            int[] arr = Arrays.copyOf(grid[i], grid[i].length);
            Arrays.sort(arr);
            rowDiff[i] = arr[1] - arr[0];
        }

        int col = 0;
        while (col < grid[0].length) {
            int[] arr = new int[grid.length];
            for (int i = 0; i < grid.length; i++) {
                arr[i] = grid[i][col];
            }
            Arrays.sort(arr);
            colDiff[col] = arr[1] - arr[0];
            col++;
        }
    }

    // Russell's Approximation Method
    private static int[][] RussellsApproximationMethod(int[][] costMatrix, int[] supply, int[] demand) {
        int numSources = supply.length;
        int numDestinations = demand.length;

        // Initialize the initial solution matrix
        int[][] initialSolution = new int[numSources][numDestinations];

        while (true) {
            // Find the minimum cost in each row and column
            int[] minRowCosts = new int[numSources];
            int[] minColCosts = new int[numDestinations];

            for (int i = 0; i < numSources; i++) {
                int minCost = Integer.MIN_VALUE;
                for (int j = 0; j < numDestinations; j++) {
                    if (costMatrix[i][j] > minCost) {
                        minCost = costMatrix[i][j];
                    }
                }
                minRowCosts[i] = minCost;
            }

            for (int j = 0; j < numDestinations; j++) {
                int minCost = Integer.MIN_VALUE;
                for (int i = 0; i < numSources; i++) {
                    if (costMatrix[i][j] > minCost) {
                        minCost = costMatrix[i][j];
                    }
                }
                minColCosts[j] = minCost;
            }

            // Find the maximum of the minimum row and column costs
            int maxCost = -1;
            int maxCostRow = -1;
            int maxCostCol = -1;

            for (int i = 0; i < numSources; i++) {
                for (int j = 0; j < numDestinations; j++) {
                    int cost = costMatrix[i][j] - minRowCosts[i] - minColCosts[j];
                    if (cost <= 0 && Math.abs(cost) > maxCost && supply[i] > 0  && demand[j] > 0) {
                        maxCost = Math.abs(cost);
                        maxCostCol = j;
                        maxCostRow = i;
                    }
                }
            }

            // If no such cell is found, the solution is found
            if (maxCost == -1) {
                break;
            }

            // Allocate as much as possible to the minimum of supply and demand
            int allocation = Math.min(supply[maxCostRow], demand[maxCostCol]);

            // Update supply and demand
            supply[maxCostRow] -= allocation;
            demand[maxCostCol] -= allocation;

            // Update initial solution matrix
            initialSolution[maxCostRow][maxCostCol] = allocation;
        }

        return initialSolution;
    }

}
