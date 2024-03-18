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
    private final boolean invertible;
    private final double det;

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

    public Matrix(double[][] aInv) {

        matrix = new double[aInv[0].length][aInv.length];
        m = aInv.length;
        n = aInv[0].length;
        dim = new int[]{m, n};
        square = (m == n);
        det = determinant(matrix);
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

    public boolean isInvertible() {
        return invertible;
    }

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
    }

    @Override
    public String toString() {
        return Arrays.toString(matrix);
    }

    public static Matrix inverse(Matrix a) throws IllegalArgumentException {

        if (!a.invertible) {
            throw new IllegalArgumentException("Matrix must be square with nonzero determinant to be invertible!");
        }

        int n = a.n;
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
                if (Math.abs(a.matrix[j][i]) > Math.abs(a.matrix[maxRow][i])) {
                    maxRow = j;
                }
            }
            swapRows(a.matrix, i, maxRow); //Swap current row with pivot row
            swapRows(aInv, i, maxRow); //Swap current row with pivot row
            double pivot = a.matrix[i][i]; //Scale the row such that the diagonal element is 1
            if (pivot == 0.0) { //Terminate the program if the matrix is singular
                throw new IllegalArgumentException("Matrix is singular");
            }
            for (int j = 0; j < n; j++) {
                a.matrix[i][j] /= pivot; //Scale this row in the input matrix
                aInv[i][j] /= pivot; //Update the inverted matrix
            }
            for (int k = 0; k < n; k++) { //Eliminate below the pivot element
                if (k != i) {
                    double factor = a.matrix[k][i];
                    for (int j = 0; j < n; j++) {
                        a.matrix[k][j] -= factor * a.matrix[i][j]; //Eliminate in the input matrix
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
        int n = matrix.length;
        if (n == 1) {
            return matrix[0][0];
        } else if (n == 2) {
            return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        } else {
            double det = 0;
            for (int j = 0; j < n; j++) {
                double[][] submatrix = new double[n - 1][n - 1];
                for (int k = 1; k < n; k++) {
                    for (int l = 0, m = 0; l < n; l++) {
                        if (l != j) {
                            submatrix[k - 1][m++] = matrix[k][l];
                        }
                    }
                }
                det += Math.pow(-1, j) * matrix[0][j] * determinant(submatrix);
            }
            return det;
        }
    }

    public static Matrix matMult(Matrix a, Matrix b) throws IllegalArgumentException {

        double[][] c = new double[a.m][b.n]; //Initialize output matrix
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

        for (double[] row : matrix) {
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

