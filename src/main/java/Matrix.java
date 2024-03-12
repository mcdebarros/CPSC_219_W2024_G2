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


}
