import java.util.Scanner;

public class SimplexMethod {

    public static void main(String[] args) {
        long startTime = System.nanoTime();
        Scanner scanner = new Scanner(System.in);

        // System.out.print("Enter the number of constraints: ");
        // int numConstraints = scanner.nextInt();

        // System.out.print("Enter the number of variables: ");
        // int numVariables = scanner.nextInt();

        // double[][] tableau = new double[numConstraints + 1][numVariables + 1];

        // System.out.println("Enter the coefficients of the objective function (Z):");
        // for (int j = 0; j < numVariables; j++) {
        // System.out.print("Coefficient for x" + (j + 1) + ": ");
        // tableau[0][j] = -scanner.nextDouble();
        // }

        // System.out.println("Enter the coefficients of the constraints:");
        // for (int i = 1; i <= numConstraints; i++) {
        // System.out.println("Constraint " + i + ":");
        // for (int j = 0; j < numVariables; j++) {
        // System.out.print("Coefficient for x" + (j + 1) + ": ");
        // tableau[i][j] = scanner.nextDouble();
        // }
        // System.out.print("Right-hand side value: ");
        // tableau[i][numVariables] = scanner.nextDouble();
        // }

        // PreMade Tableau
        double[][] tableau = {
                { -40, -30, 0, 0, 1, 0 },
                { 1, 1, 1, 0, 0, 12 },
                { 2, 1, 0, 1, 0, 16 },

        };

        // // print tableau
        // int numRows = tableau.length;
        // int numCols = tableau[0].length;
        // for (int i = 0; i < numRows; i++) {
        // for (int j = 0; j < numCols; j++) {
        // System.out.print(tableau[i][j] + "\t");
        // }
        // System.out.println();
        // }

        simplex(tableau);
        int n = tableau[0].length;

        // System.out.println("Optimal Solution:");
        // System.out.println("Zm = " + tableau[0][n - 1]); // Negate for maximization
        // problem
        // for (int i = 1; i < numConstraints + 1; i++) {
        // System.out.println("x" + i + " = " + tableau[i][n - 1]);
        // }

        System.out.println("Optimal Solution:");
        System.out.println("Zm = " + tableau[0][n - 1]);
        System.out.println("x1 = " + tableau[1][n - 1]);
        System.out.println("x2 = " + tableau[2][n - 1]);

        scanner.close();
        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime; // Time in nanoseconds

        // Convert nanoseconds to milliseconds for easier readability
        double milliseconds = (double) elapsedTime / 1_000_000.0;

        System.out.println("Elapsed Time: " + milliseconds + " milliseconds");
    }

    public static void simplex(double[][] tableau) {

        while (true) {
            int pivotColumn = findPivotColumn(tableau);

            // Debug
            // System.out.println("pivotColumn = " + pivotColumn);

            if (pivotColumn == -1) {
                break; // optimal solution found
            }

            int pivotRow = findPivotRow(tableau, pivotColumn);

            // Debug
            // System.out.println("pivotRow = " + pivotRow);

            if (pivotRow == -1) {
                throw new ArithmeticException("Unbounded solution");
            }

            // Perform pivot operation
            pivot(tableau, pivotRow, pivotColumn);
        }
    }

    public static int findPivotColumn(double[][] tableau) {
        int pivotColumn = -1;
        double minCoefficient = 0;

        for (int j = 0; j < tableau[0].length - 1; j++) {
            if (tableau[0][j] < minCoefficient) {
                minCoefficient = tableau[0][j];
                pivotColumn = j;
            }
        }

        return pivotColumn;
    }

    public static int findPivotRow(double[][] tableau, int pivotColumn) {
        int pivotRow = -1;
        double minRatio = Double.MAX_VALUE;

        for (int i = 1; i < tableau.length; i++) {
            if (tableau[i][pivotColumn] > 0) {
                double ratio = tableau[i][tableau[0].length - 1] / tableau[i][pivotColumn];
                // System.out.println("minRatio = " + minRatio);
                if (ratio < minRatio) {
                    minRatio = ratio;
                    pivotRow = i;
                }
            }
        }

        return pivotRow;
    }

    public static void pivot(double[][] tableau, int pivotRow, int pivotColumn) {
        int numRows = tableau.length;
        int numCols = tableau[0].length;

        // Scale pivot row to make pivot element 1
        double pivotElement = tableau[pivotRow][pivotColumn];
        for (int j = 0; j < numCols; j++) {
            tableau[pivotRow][j] /= pivotElement;
        }

        // print tableau
        // for (int i = 0; i < numRows; i++){
        // for (int j=0; j < numCols; j++){
        // System.out.print(tableau[i][j] + "\t");
        // }
        // System.out.println();
        // }

        // Make all other entries in pivot column zero
        for (int i = 0; i < numRows; i++) {
            if (i != pivotRow) {
                double factor = tableau[i][pivotColumn];
                for (int j = 0; j < numCols; j++) {
                    tableau[i][j] -= factor * tableau[pivotRow][j];
                }
            }
        }

        // print tableau
        // for (int i = 0; i < numRows; i++){
        // for (int j=0; j < numCols; j++){
        // System.out.print(tableau[i][j] + "\t");
        // }
        // System.out.println();
        // }
    }
}
