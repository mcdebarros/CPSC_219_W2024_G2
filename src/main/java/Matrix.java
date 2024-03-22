// Import useful packages
import java.util.*;
import java.io.*;
import static java.lang.StringTemplate.STR;

//TODO
// Abstract classing for matrix subtypes?
// Inheritance?

public class Matrix {

    private final int[] dim; // Array of matrix [m,n] dimensions.
    private final double[][] matrix; // double[][] array containing matrix entries.
    private final int m; // m dimension of matrix
    private final int n; // n dimension of matrix.
    private final boolean square; // Boolean of whether m and n dimensions are equal.
    private final String type; // Classification of matrix based on dimensionality.
    private boolean invertible; // Boolean of whether matrix is invertible. Possibly redundant.
    private double det; // Determinant of square matrix.

    /**
     * Constructs null matrix object from m and m dimensions.
     * @param m number of rows
     * @param n number of columns
     * @throws ExceptionInInitializerError when initializing 0 space matrix or 1x1 scalar
     */
    public Matrix(int m, int n) throws ExceptionInInitializerError {

        if ((m == 0) && (n == 0)) { // Deny matrix construction for zero dimensional arrays
            throw new ExceptionInInitializerError("Cannot initialize 0 dimensional matrix.");
        } else if ((m == 1) && (n == 1)) { // Deny matrix construction for scalar quantities
            throw new ExceptionInInitializerError("One by one matrix is a scalar quantity.");
        }
        this.m = m; // m dimension of matrix
        this.n = n; // n dimension of matrix
        matrix = new double[m][n]; // null double[][] array of matrix
        dim = new int[]{m,n}; // integer array of matrix dimensions
        square = (m == n); // Is the matrix square?
        if (square) {
            det = determinant(matrix); // Get determinant from function if square
        } else {
            det = 0.0; // Set determinant to 0 if not square
        }
        invertible = (det != 0); // Invertible property of the matrix
        type = type(); // Distinguished between several possible types of arrays; see function
    }

    /**
     * Constructs matrix object from double[][] array.
     * @param matrix double[][] array containing matrix
     */
    public Matrix(double[][] matrix) {

        this.matrix = matrix; // Initialize matrix array directly from parameter input
        m = matrix.length; // Get m dimension from y length of matrix
        n = matrix[0].length; // Get n dimension from x length of matrix
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

    /**
     * Constructs matrix object from data file.
     * @param data String of path to datafile.
     * @throws NumberFormatException when file contains non-decimal entries.
     * @throws FileNotFoundException when file cannot be found at path destination.
     */
    public Matrix(String data) throws NumberFormatException, FileNotFoundException {

        matrix = MatReader.getData(data); // Call the matReader function yo construct the matrix array
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

    /**
     * Arranges the matrix dimensions into an int[] array.
     * @return int[] array of matrix [m,n] dimensions.
     */
    public int[] size() {
        return dim;
    }

    /**
     * Boolean of square nature of matrix
     * @return true if square; false if not
     */
    public boolean isSquare() {
        return square;
    }

    /**
     * Fetches the double[][] array containing the matrix data
     * @return matrix double[][]
     */
    public double[][] getMatrix() {
        return matrix;
    }

    /**
     * Fetches the determinant of the matrix
     * @return determinant of the matrix (double)
     */
    public double getDet() {
        return det;
    }

    /**
     * Fetches the array type, ie, data, vector, etc
     * @return string of array type
     */
    public String getType() {
        return type;
    }

    /**
     * Fetches the double in the specified entry
     * @param i m index of the entry
     * @param j n index of the entry
     * @return double of (i,j)th entry
     */
    public double getEntry(int i, int j) {
        return matrix[i][j];
    }

    /**
     * Sets the specified entry as a double
     * @param i m index of the entry
     * @param j n index of the entry
     * @param entry double to populate the entry
     */
    public void setEntry(int i, int j, double entry) {
        matrix[i][j] = entry;
        if (square) {
            det = determinant(matrix); // Update determinant as entries change
            invertible = (det != 0); // Update invertible property as determinant changes
        }
    }

    /**
     * Inverts square, non-singular matrices
     * @param a matrix to invert
     * @return inverted matrix
     * @throws IllegalArgumentException when matrix is not invertible
     */
    public static Matrix inverse(Matrix a) throws IllegalArgumentException {

        Matrix clone = new Matrix(a.m,a.n); // Clone the original matrix as the process converts the original to the identity matrix
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

    /**
     * Swap row function for gaussian elimination
     * @param a array to perform swap on
     * @param i first row to swap
     * @param j second row to swap
     */
    public static void swapRows(double[][] a, int i, int j) {
        double[] temp = a[i]; //Temporarily store a[i]
        a[i] = a[j]; //Assign a[j] to a[i]
        a[j] = temp; //Assign a[i] to a[j]
    }

    /**
     * Transpose a given array
     * @param a array to transpose
     * @return transposed array
     */
    public static Matrix transpose(Matrix a) {
        Matrix transpose = new Matrix(a.size()[1],a.size()[0]); // Create new double[][] array with opposite dimensions of parameters
        for (int i = 0; i < a.size()[0]; i++) { // Populate transpose with reversed indices
            for (int j = 0; j < a.size()[1]; j++) {
                transpose.setEntry(j,i,a.getEntry(i,j));
            }
        }
        return transpose;
    }

    /**
     * Calculate the determinant of a square matrix
     * @param matrix matrix to find determinant
     * @return double of determinant
     */
    public static double determinant(double[][] matrix) {
        int size = matrix.length;
        if (size != matrix[0].length) {
            throw new IllegalArgumentException("Matrix must be square"); // Non-square matrices do not have determinants
        }
        if (size == 1) {
            return matrix[0][0]; // Determinant of a scalar quantity is the scalar quantity
        }

        double det = 0; // Initialize the determinant double
        for (int i = 0; i < size; i++) { // Iterate through matrix columns
            det += matrix[0][i] * cofactor(matrix, 0, i); // Increment the determinant by the product of the entry and the cofactor of this column
        }
        return det;
    }

    /**
     * Organizes minor matrices for determinant calculations
     * @param matrix matrix to subdivide
     * @param row row bound of minor matrix
     * @param col column bound of minor matrix
     * @return double[][] array of minor matrix
     */
    public static double[][] submatrix(double[][] matrix, int row, int col) {
        int size = matrix.length;
        double[][] sub = new double[size - 1][size - 1]; // Initialize the minor matrix by reducing dimensionality by 1
        int r = 0; // Initialize row index of minor matrix
        for (int i = 0; i < size; i++) { // Iterate through rows of original matrix
            if (i == row) continue; // Skip blocked row
            int c = 0; // Initialize column index of minor matrix
            for (int j = 0; j < size; j++) { // Iterate through columns of the original matrix
                if (j == col) continue; // Skip blocked column
                sub[r][c] = matrix[i][j]; // Assign minor matrix entries
                c++; // Increment minor matrix column
            }
            r++; // Increment minor matrix row
        }
        return sub;
    }

    /**
     * Cofactor operation for matrix determinant
     * @param matrix host matrix
     * @param row row bound
     * @param col column bound
     * @return double of cofactor
     */
    public static double cofactor(double[][] matrix, int row, int col) {
        return Math.pow(-1, row + col) * determinant(submatrix(matrix, row, col)); // Calculate and return the cofactor for a row/col combination
    }

    /**
     * Multiplies two matrices; order matters since this operation is not commutative
     * @param a first matrix to multiply
     * @param b second matrix to multiply
     * @return Matrix c, product of a and b
     * @throws IllegalArgumentException when matrix dimensions are incompatible
     */
    public static Matrix matMult(Matrix a, Matrix b) throws IllegalArgumentException {

        int rowsA = a.size()[0]; // Assign matrix dimensions (debugging relic)
        int colsA = a.size()[1];
        int rowsB = b.size()[0];
        int colsB = b.size()[1];
        double[][] c = new double[rowsA][colsB]; //Initialize output matrix
        if (colsA != rowsB) { //Check that passed matrices have compatible dimensions
            throw new IllegalArgumentException(STR."Incompatible matrix dimensions for matrix multiplication; dimA = \{Arrays.toString(a.size())}, dimB = \{Arrays.toString(b.size())}!");
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

    /**
     * Determines matrix type
     * @return matrix type
     */
    private String type() {

        String type; // Initialize the type String
        if ((m > 2) && (n == 2)) {
            type = "Data"; // Case if matrix formatted as regressible data
        } else if ((m == 0) && (n > 1)) {
            type = "Row Vector"; // Case if matrix is 1xn
        } else if ((m > 0) && (n == 0)) {
            type = "Column Vector"; // Case if matrix is mx1
        } else if (m != n) {
            type = "System"; // Case if matrix dimensions are not identical
        } else {
            type = "Square"; // Case if m and n are the same
        }
        return type;
    }

    /**
     * Fetches specified matrix row (indexing from 1)
     * @param row row to fetch
     * @return double[] of row
     */
    public double[][] getRow(int row) {

        double[][] returnRow = new double[1][n];
        try {
            System.arraycopy(matrix[row], 0, returnRow[0], 0, n); // Copy this row to a new double[][] array
            return returnRow;
        } catch (ArrayIndexOutOfBoundsException e) { // Throw exception if array index exceeds m dimension
            throw new ArrayIndexOutOfBoundsException(STR."Row index out of bounds! Indices range from 1 to \{m}");
        }
    }

    /**
     * Fetches specified matrix column (indexing from 1)
     * @param col column to fetch
     * @return double[]
     */
    public double[][] getCol(int col) {

        double[][] column = new double[m][1];
        try {
            for (int i = 0; i < m; i++) {
                column[i][0] = matrix[i][col - 1]; // Assemble the column vector of column entries
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println(STR."Column \{col} out of range for matrix with \{n} columns."); // Throw exception if array index exceeds n dimension
        }
        return column;
    }

    /**
     * Pseudo toString method, displays the matrix in console
     */
    public void showMat() {

        String redColor = "\u001B[34m";
        String resetColor = "\u001B[0m";
        String TLC = STR."\{redColor}╔";
        StringBuilder topBar = new StringBuilder(TLC);
        String BLC = STR."\{redColor}╚";
        StringBuilder botBar = new StringBuilder(BLC);
        String LT = STR."\{redColor}╠";
        String VERT = STR."\{redColor}║\{resetColor}";
        StringBuilder BAR = new StringBuilder();
        StringBuilder midBar = new StringBuilder(LT);
        StringBuilder fancyMat = new StringBuilder();
        for (int i = 0; i < m; i++) {

            String HORI = "═════════";
            if (i < m - 1) {
                String DT = "╦";
                topBar.append(HORI).append(DT);
                String UT = "╩";
                botBar.append(HORI).append(UT);
                String CRX = "╬";
                midBar.append(HORI).append(CRX);
            } else {
                String TRC = STR."╗\{resetColor}";
                topBar.append(HORI).append(TRC);
                String BRC = STR."╝\{resetColor}";
                botBar.append(HORI).append(BRC);
                String RT = STR."╣\{resetColor}";
                midBar.append(HORI).append(STR."\{RT}\n");
            }
        }
        fancyMat.append(STR."\{topBar}\n");
        for (int i = 0; i < m; i++) {
            StringBuilder thisRow = new StringBuilder();
            for (int j = 0; j < n; j++) {
                double entry;
                double zero = 0.0;
                if (((matrix[i][j] < 1.0e-10) && (matrix[i][j] > 0)) || ((matrix[i][j] > -1.0e-10) && (matrix[i][j] < 0))) {
                    entry = zero;
                } else {
                    entry = matrix[i][j];
                }
                if (entry < 0) {
                    thisRow.append(VERT).append(String.format("%2.1e ", entry));
                } else {
                    thisRow.append(VERT).append(String.format(" %2.1e ", entry));
                }
            }
            thisRow.append(STR."\{VERT}\n");
            fancyMat.append(thisRow);
            if (i < m - 1) {
                fancyMat.append(midBar);
            }
        }
        fancyMat.append(botBar);
        System.out.println(fancyMat);
    }

    /**
     * Equality operator for Matrix objects
     * @param obj object to compare
     * @return boolean of equality
     */
    @Override
    public boolean equals(Object obj) {

        if (this == obj) { //Check if both inputs are object
            return true;
        }
        if (obj != null) { // Check that object is not null
            if (getClass() != obj.getClass()) { // Check if objects are of same class
                Matrix other = (Matrix) obj; // Cast to matrix if false above
                if ((this.m == other.m)) { // Check if arrays are of same dimensions
                    for (int i = 0; i < this.m; i++) {
                        for (int j = 0; j < this.n; j++) {
                            if ((double) this.matrix[m][n] != (double) other.matrix[m][n]) { // Check if matrix entries are identical
                                return false;
                            }
                        }
                    }
                } else return false;
            }
        }
        return true;
    }

    /**
     * Converts matrix double[][] array to string
     * @return String of matrix array
     */
    @Override
    public String toString() {

        String[] matStr = new String[m];
        for (int i = 0; i < m; i++) {
            matStr[i] = Arrays.toString(matrix[i]); // Create 2D string of matrix array
        }
        return Arrays.toString(matStr);
    }
}