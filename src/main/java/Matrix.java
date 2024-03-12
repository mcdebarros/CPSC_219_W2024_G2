import java.util.*;
import java.io.*;

import static java.lang.StringTemplate.STR;

public class Matrix {

    private final int[] dim;
    private final Double[][] matrix;
    private final int m;
    private final int n;
    private final boolean square;
    private String type;
    private final boolean invertible;
    private final Double det;

    public Matrix(int m, int n) throws ExceptionInInitializerError {

        if ((m == 0) && (n == 0)) {
            throw new ExceptionInInitializerError("Cannot initialize 0 dimensional matrix.");
        } else if ((m == 1) && (n == 1)) {
            throw new ExceptionInInitializerError("One by one matrix is a scalar quantity.");
        }
        this.m = m;
        this.n = n;
        matrix = new Double[m][n];
        dim = new int[]{m,n};
        square = (m == n);
        if (square) {
            det = determinant(matrix);
        } else {
            det = 0.0;
        }
        invertible = (det != 0);
        type = type();
    }

    public Matrix(Double[][] aInv) {

        matrix = new Double[aInv[0].length][aInv.length];
        m = aInv.length;
        n = aInv[0].length;
        dim = new int[]{m, n};
        square = (m == n);
        det = determinant(matrix);
        invertible = (det != 0);
        type = type();
    }

    public Matrix(String data) throws ExceptionInInitializerError {

        ArrayList<String> dataLines = new ArrayList<>(); //ArrayList of lines read from the data file
        File dataFile = new File(data);
        if (dataFile.exists() && dataFile.canRead() && dataFile.isFile()) {
            try {
                FileReader readData = new FileReader(dataFile);
                BufferedReader buffedData = new BufferedReader(readData);
                String dataLine = buffedData.readLine();
                int i = 0;
                while (dataLine != null) {
                    dataLines.add(i,dataLine);
                    dataLine = buffedData.readLine();
                    i++;
                }
            } catch (FileNotFoundException e) {
                System.err.println("Whoops! Can't find the file. Check and try again.");
                System.exit(3);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.err.println("Cannot read file!");
            System.exit(4);
        }
        String[] lineContent = (dataLines.getFirst()).split("\t", 0);
        matrix = new Double[dataLines.size()][lineContent.length];
        m = matrix.length;
        n = matrix[0].length;
        if ((m == 1) && (n == 1)) {
            throw new ExceptionInInitializerError("One by one matrix is a scalar quantity.");
        }
        dim = new int[]{m,n};
        for (int i = 0; i < dataLines.size(); i++) {
            if (!(dataLines.get(i)).isEmpty()) {
                try {
                    lineContent = (dataLines.get(i)).split("\t", 0);
                    if (lineContent.length < matrix[0].length) {
                        System.err.println(STR."Inconsistent dimensions at line \{i + 1}, check n dimension.");
                    }
                    matrix[i][0] = Double.parseDouble(lineContent[0]);
                    matrix[i][1] = Double.parseDouble(lineContent[1]);
                } catch (NumberFormatException e) { //Terminates the program if the line contains data not in decimal format
                    System.err.println(STR."Improperly formatted data at line \{i + 1}, check data and try again!");
                    System.exit(6);
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.err.println(STR."Inconsistent dimensions at line \{i + 1}, check n dimension.");
                    System.exit(0);
                }
            }
        }
        square = (m == n);
        if (square) {
            det = determinant(matrix);
        } else {
            det = 0.0;
        }
        invertible = det != 0;
        type = type();
    }

    public int[] size() {
        return dim;
    }

    public boolean isSquare() {
        return square;
    }

    public boolean isInvertible() {
        return invertible;
    }

    public Double getDet() {
        return det;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return Arrays.toString(matrix);
    }

    public Matrix inverse(Matrix a) throws IllegalArgumentException {

        if (!a.invertible) {
            throw new IllegalArgumentException("Matrix must be square with nonzero determinant to be invertible!");
        }

        int n = a.n;
        Double[][] aInv = new Double[n][n]; //Initialize inverted matrix
        for (int i = 0; i < n; i++) { //Initialize the inverted matrix as the identity matrix, with 1s along the main diagonal and zeros elsewhere
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    aInv[i][j] = 1.0; //On-diagonal entries
                } else {
                    aInv[i][j] = 0.0; //Off-diagonal entries
                }
            }
        }
        for (int i = 0; i < n; i++) { //Perform Gaussian elimination w/ partial pivoting
            int maxRow = i;
            for (int j = i + 1; j < n; j++) { //Find the pivot row by locating the largest absolute value in some column
                if (Math.abs(a.matrix[j][i]) > Math.abs(a.matrix[maxRow][i])) {
                    maxRow = j;
                }
            }
            swapRows(a.matrix, i, maxRow); //Swap current row with pivot row
            swapRows(aInv, i, maxRow); //Swap current row with pivot row
            Double pivot = a.matrix[i][i]; //Scale the row such that the diagonal element is 1
            if (pivot == 0.0) { //Terminate the program if the matrix is singular
                throw new IllegalArgumentException("Matrix is singular");
            }
            for (int j = 0; j < n; j++) {
                a.matrix[i][j] /= pivot; //Scale this row in the input matrix
                aInv[i][j] /= pivot; //Update the inverted matrix
            }
            for (int k = 0; k < n; k++) { //Eliminate below the pivot element
                if (k != i) {
                    Double factor = a.matrix[k][i];
                    for (int j = 0; j < n; j++) {
                        a.matrix[k][j] -= factor * a.matrix[i][j]; //Eliminate in the input matrix
                        aInv[k][j] -= factor * aInv[i][j]; //Update the inverted matrix
                    }
                }
            }
        }
        return new Matrix(aInv);
    }

    private void swapRows(Double[][] a, int i, int j) {
        Double[] temp = a[i]; //Temporarily store a[i]
        a[i] = a[j]; //Assign a[j] to a[i]
        a[j] = temp; //Assign a[i] to a[j]
    }

    public Matrix transpose(Matrix a) {
        Double [][] transposeDub = new Double[a.n][a.m];
        for (int i = 0; i < a.m; i++) {
            for (int j = 0; j < a.n; j++) {
                transposeDub[i][j] = a.matrix[j][i];
            }
        }
        return new Matrix(transposeDub);
    }

    private static Double determinant(Double[][] matrix) {

        int n = matrix.length;
        if (n == 1) {
            return matrix[0][0];
        }
        if (n == 2) {
            return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        }
        double det = 0;
        for (int j = 0; j < n; j++) {
            // Calculate the cofactor for element (0,j)
            Double[][] minor = new Double[n - 1][n - 1];
            for (int row = 1; row < n; row++) {
                int minorCol = 0;
                for (int col = 0; col < n; col++) {
                    if (col != j) {
                        minor[row - 1][minorCol] = matrix[row][col];
                        minorCol++;
                    }
                }
            }
            // Recursive call to calculate the determinant of the minor
            det += Math.pow(-1, j) * matrix[0][j] * determinant(minor);
        }
        return det;
    }

    public Matrix matMult(Matrix a, Matrix b) throws IllegalArgumentException {

        Double[][] c = new Double[a.m][b.n]; //Initialize output matrix
        if (a.n != b.m) { //Check that passed matrices have compatible dimensions
            throw new IllegalArgumentException("Incompatible matrix dimensions for matrix multiplication!");
        } else { //Perform matrix multiplication by the general formula
            for (int i = 0; i < a.m; i++) {
                for (int j = 0; j < b.n; j++) {
                    double entry = 0.0; //Initialize the entry to store in c[i][j]
                    for (int n = 0; n < a.n; n++) {
                        entry += a.matrix[i][n] * b.matrix[n][j]; //Sum the product of matrix members based on dimension n (colsA, rowsB) for some i,j
                    }
                    c[i][j] = entry; //Assign the i,j entry of the c matrix
                }
            }
        }
        return new Matrix(c);
    }

    private String type() {

        String type;
        if ((m > 2) && (n == 2)) {
            type = "Data";
        } else if ((m == 0) && (n > 0)) {
            type = "Row Vector";
        } else if ((m > 0) && (n == 0)) {
            type = "Column Vector";
        } else if (m != n) {
            type = "System";
        } else {
            type = "Square";
        }
        return type;
        }
    }

    private boolean containsHeaders(String line) {
        String[] parts = line.split("\t"); // Create a 1D array by splitting the line at the tab
        for (String part : parts) {
            try {
                Double.parseDouble(part); //Attempt to parse each entry as a double
                return false; //Return false if parsing succeeds; this line is not a header
            } catch (NumberFormatException e) {
                //If parsing fails, it's likely that headers were encountered
            }
        }
        return true; //Data likely contains headers if not all parts were parsed as doubles
    }
}
