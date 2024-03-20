import java.io.*;
import java.util.*;
import java.lang.Math;

public class MatReader {

    /**
     * Read the file to extract matrix data
     * @param fileName String of path to file
     * @return double[][] of file data
     * @throws NumberFormatException when file contains non-double entries
     * @throws FileNotFoundException when file not found in specified path
     */
    public static double[][] getData(String fileName) throws NumberFormatException, FileNotFoundException {

        ArrayList<String> dataLines = getStrings(fileName); // Create and populate an array list where each entry is a string corresponding to a line in the data file
        double[][] dataMatrix; // Initialize the matrix double
        boolean hasHeader = containsHeaders(dataLines.getFirst()); //
        if (!hasHeader) { // Index from 0 if no headers found
            String[] n = (dataLines.getFirst()).split("\t", 0); // Create a string by splitting the first entry in dataLines, where the length defines the n dimension of dataMatrix
            dataMatrix = new double[dataLines.size()][n.length]; // Initialize the data matrix
            for (int i = 0; i < dataLines.size(); i++) { // Iterate through dataLines
                if (!(dataLines.get(i)).isEmpty()) { // Operate only on populated entries
                    try {
                        String[] lineContent = (dataLines.get(i)).split("\t", 0); // Split the line into component entries
                        for (int j = 0; j < n.length; j++) { // Iterate through the string vector
                            dataMatrix[i][j] = Double.parseDouble(lineContent[j]); // Parse the entries into doubles and assign them to dataMatrix
                        }
                    } catch (NumberFormatException e) {
                        throw new NumberFormatException(STR."Incorrect data format at line \{i + 1}. Check data"); // Terminate the program if data format is not integer
                    }
                }
            }
        } else { // Index from 1 if data contains headers, otherwise, same as above
            String[] n = (dataLines.get(1)).split("\t", 0);
            dataMatrix = new double[dataLines.size() - 1][n.length];
            for (int i = 1; i < dataLines.size(); i++) {
                if (!(dataLines.get(i)).isEmpty()) {
                    try {
                        String[] lineContent = (dataLines.get(i)).split("\t", 0);
                        for (int j = 0; j < n.length; j++) {
                            dataMatrix[i - 1][j] = Double.parseDouble(lineContent[j]);
                        }
                    } catch (NumberFormatException e) {
                        throw new NumberFormatException(STR."Incorrect data format at line \{i + 1}. Check data");
                    }
                }
            }
        }
        return dataMatrix;
    }

    /**
     * Reads each line in the datafile and stores it in an arraylist
     * @param fileName path to file
     * @return ArrayList of file line strings
     * @throws FileNotFoundException when the file cannot be found at the specified path
     */
    public static ArrayList<String> getStrings(String fileName) throws FileNotFoundException {
        ArrayList<String> dataLines = new ArrayList<>(); // Initialize array list of data file line strings
        File dataFile = new File(fileName); // Initialize the data file
        if (dataFile.exists() && dataFile.canRead() && dataFile.isFile()) { // Proceed only if data file exists and is readable
            try {
                FileReader readData = new FileReader(dataFile); // Initialize the file reader
                BufferedReader buffedData = new BufferedReader(readData); // Initialize the buffered reader
                String dataLine = buffedData.readLine(); // Create a string of the first data line
                int i = 0; // Index to append dataLines
                while (dataLine != null) { // Iterate while line content is not null
                    dataLines.add(i,dataLine); // Append dataLines with dataLine
                    dataLine = buffedData.readLine(); // Update dataLine with the next line
                    i++; // Iterate the index
                }
            } catch (FileNotFoundException e) {
                throw new FileNotFoundException("File not found. Check filename & location."); // Terminate if the file is not found at path location
            } catch (IOException e) {
                throw new RuntimeException(e); // Terminate if buffered reader throws exception
            }
        } else {
            throw new RuntimeException("Cannot read the datafile. Check contents and location."); // Terminate if file is not readable
        }
        return dataLines;
    }

    /**
     * Checks to see if data contains headers
     * @param line String of first line of the dataLines array
     * @return boolean of header content
     */
    public static boolean containsHeaders(String line) {
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
