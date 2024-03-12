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

    }

    public Matrix(Double[][] aInv) throws ExceptionInInitializerError {

        matrix = new Double[aInv[0].length][aInv.length];
        m = aInv.length;
        n = aInv[0].length;
        dim = new int[]{m, n};
        square = (m == n);
    }

    public Matrix(String data) throws ExceptionInInitializerError{

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
        square = (m == n);
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
    }

    public int[] size() {
        return dim;
    }

    public boolean isSquare() {
        return square;
    }

    @Override
    public String toString() {
        return Arrays.toString(matrix);
    }

    public Matrix inverse(Double[][] a) {

        int n = a.length; //Establish dimensions of a
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
                if (Math.abs(a[j][i]) > Math.abs(a[maxRow][i])) {
                    maxRow = j;
                }
            }
            swapRows(a, i, maxRow); //Swap current row with pivot row
            swapRows(aInv, i, maxRow); //Swap current row with pivot row
            Double pivot = a[i][i]; //Scale the row such that the diagonal element is 1
            if (pivot == 0.0) { //Terminate the program if the matrix is singular
                throw new IllegalArgumentException("Matrix is singular");
            }
            for (int j = 0; j < n; j++) {
                a[i][j] /= pivot; //Scale this row in the input matrix
                aInv[i][j] /= pivot; //Update the inverted matrix
            }
            for (int k = 0; k < n; k++) { //Eliminate below the pivot element
                if (k != i) {
                    Double factor = a[k][i];
                    for (int j = 0; j < n; j++) {
                        a[k][j] -= factor * a[i][j]; //Eliminate in the input matrix
                        aInv[k][j] -= factor * aInv[i][j]; //Update the inverted matrix
                    }
                }
            }
        }
        return aInv;
    }

    private static void swapRows(Double[][] a, int i, int j) {
        Double[] temp = a[i]; //Temporarily store a[i]
        a[i] = a[j]; //Assign a[j] to a[i]
        a[j] = temp; //Assign a[i] to a[j]
    }
}
