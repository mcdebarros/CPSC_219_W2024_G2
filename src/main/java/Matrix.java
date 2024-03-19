import java.util.*;
import java.io.*;

import static java.lang.StringTemplate.STR;

//TODO
// Abstract classing for matrix subtypes?
// Inheritance?

public class Matrix {

    private final int[] dim;
    private final double[][] matrix;
    private final int m;
    private final int n;
    private final boolean square;
    private final String type;
    private boolean invertible;
    private double det;

    public Matrix(int m, int n) throws ExceptionInInitializerError {

        if ((m == 0) && (n == 0)) {
            throw new ExceptionInInitializerError("Cannot initialize 0 dimensional matrix.");
        } else if ((m == 1) && (n == 1)) {
            throw new ExceptionInInitializerError("One by one matrix is a scalar quantity.");
        }
        this.m = m;
        this.n = n;
        matrix = new double[m][n];
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

    public Matrix(double[][] matrix) {

        this.matrix = matrix;
        m = matrix.length;
        n = matrix[0].length;
        dim = new int[]{m, n};
        square = (m == n);
        if (square) {
            det = determinant(matrix);
        } else {
            det = 0;
        }
        invertible = (det != 0);
        type = type();
    }

    public Matrix(String data) throws RuntimeException, FileNotFoundException {

        matrix = MatReader.getData(data);
        m = matrix.length;
        n = matrix[0].length;
        dim = new int[]{m,n};
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
    public double[][] getMatrix() { return matrix;}

    public double getDet() {
        return det;
    }

    public String getType() {
        return type;
    }

    public double getEntry(int i, int j) {
        return matrix[i][j];
    }

    public void setEntry(int i, int j, double entry) {
        matrix[i][j] = entry;
        det = determinant(matrix);
        invertible = (det != 0);
    }

    @Override
    public String toString() {
        return Arrays.toString(matrix);
    }

    public static Matrix inverse(Matrix a) throws IllegalArgumentException {

        Matrix clone = new Matrix(a.m,a.n);
        for (int i = 0; i < a.m; i++) {
            for (int j = 0; j < a.n; j++) {
                clone.setEntry(i,j,a.matrix[i][j]);
            }
        }
        if (!clone.invertible) {
            throw new IllegalArgumentException("Matrix must be square with nonzero determinant to be invertible!");
        }

        int n = clone.n;
        double[][] aInv = new double[n][n]; //Initialize inverted matrix
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
                if (Math.abs(clone.matrix[j][i]) > Math.abs(clone.matrix[maxRow][i])) {
                    maxRow = j;
                }
            }
            swapRows(clone.matrix, i, maxRow); //Swap current row with pivot row
            swapRows(aInv, i, maxRow); //Swap current row with pivot row
            double pivot = clone.matrix[i][i]; //Scale the row such that the diagonal element is 1
            if (pivot == 0.0) { //Terminate the program if the matrix is singular
                throw new IllegalArgumentException("Matrix is singular");
            }
            for (int j = 0; j < n; j++) {
                clone.matrix[i][j] /= pivot; //Scale this row in the input matrix
                aInv[i][j] /= pivot; //Update the inverted matrix
            }
            for (int k = 0; k < n; k++) { //Eliminate below the pivot element
                if (k != i) {
                    double factor = clone.matrix[k][i];
                    for (int j = 0; j < n; j++) {
                        clone.matrix[k][j] -= factor * clone.matrix[i][j]; //Eliminate in the input matrix
                        aInv[k][j] -= factor * aInv[i][j]; //Update the inverted matrix
                    }
                }
            }
        }
        return new Matrix(aInv);
    }

    private static void swapRows(double[][] a, int i, int j) {
        double[] temp = a[i]; //Temporarily store a[i]
        a[i] = a[j]; //Assign a[j] to a[i]
        a[j] = temp; //Assign a[i] to a[j]
    }

    public static Matrix transpose(Matrix a) {
        Matrix transpose = new Matrix(a.size()[1],a.size()[0]);
        for (int i = 0; i < a.size()[0]; i++) {
            for (int j = 0; j < a.size()[1]; j++) {
                transpose.setEntry(j,i,a.getEntry(i,j));
            }
        }
        return transpose;
    }

    public static double determinant(double[][] matrix) {
        int size = matrix.length;
        if (size != matrix[0].length) {
            throw new IllegalArgumentException("Matrix must be square");
        }
        if (size == 1) {
            return matrix[0][0]; // Base case: determinant of a 1x1 matrix
        }

        double det = 0;
        for (int i = 0; i < size; i++) {
            det += matrix[0][i] * cofactor(matrix, 0, i);
        }
        return det;
    }

    public static double[][] submatrix(double[][] matrix, int row, int col) {
        int size = matrix.length;
        double[][] sub = new double[size - 1][size - 1];
        int r = 0;
        for (int i = 0; i < size; i++) {
            if (i == row) continue;
            int c = 0;
            for (int j = 0; j < size; j++) {
                if (j == col) continue;
                sub[r][c] = matrix[i][j];
                c++;
            }
            r++;
        }
        return sub;
    }

    public static double cofactor(double[][] matrix, int row, int col) {
        return Math.pow(-1, row + col) * determinant(submatrix(matrix, row, col));
    }

    public static Matrix matMult(Matrix a, Matrix b) throws IllegalArgumentException {

        int rowsA = a.size()[0];
        int colsA = a.size()[1];
        int rowsB = b.size()[0];
        int colsB = b.size()[1];
        double[][] c = new double[rowsA][colsB]; //Initialize output matrix
        if (colsA != rowsB) { //Check that passed matrices have compatible dimensions
            throw new IllegalArgumentException("Incompatible matrix dimensions for matrix multiplication!");
        } else { //Perform matrix multiplication by the general formula
            for (int i = 0; i < rowsA; i++) {
                for (int j = 0; j < colsB; j++) {
                    double entry = 0.0;
                    for (int n = 0; n < colsA; n++) {
                        entry += a.getEntry(i,n) * b.getEntry(n,j); //Sum the product of matrix members based on dimension n (colsA, rowsB) for some i,j
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

    public double[] getRow(int row) {
        try {
            return matrix[row - 1];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ArrayIndexOutOfBoundsException(STR."Row index out of bounds! Indices range from 1 to \{m}");
        }
    }

    //public double[] gerCol(int col) {

        //double[] column = new Column
        //try {
           // return matrix[0][col];
      //  }
        //return new double[0];
    //}

    public void showMat() {

        String[][] matString = new String[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                matString[i][j] = String.format("%.2f",matrix[i][j]);
            }
        }
        for (String[] row : matString) {
            System.out.println(Arrays.toString(row));
        }
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }
        if (obj != null) {
            if (getClass() != obj.getClass()) {
                Matrix other = (Matrix) obj;
                for (int i = 0; i < this.m; i++) {
                    for (int j = 0; j < this.n; j++) {
                        if ((double) this.matrix[m][n] != (double) other.matrix[m][n]) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
}

