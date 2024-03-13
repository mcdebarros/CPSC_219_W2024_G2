import java.io.*;
import java.util.*;
import java.lang.Math;

public class MatReader {

    public static Double[][] getData(String fileName) throws NumberFormatException, FileNotFoundException {

        ArrayList<String> dataLines = getStrings(fileName);
        Double[][] dataMatrix;
        boolean hasHeader = containsHeaders(dataLines.getFirst());
        if (!hasHeader) {
            String[] n = (dataLines.getFirst()).split("\t", 0);
            dataMatrix = new Double[dataLines.size()][n.length];
            for (int i = 0; i < dataLines.size(); i++) {
                if (!(dataLines.get(i)).isEmpty()) {
                    try {
                        String[] lineContent = (dataLines.get(i)).split("\t", 0);
                        for (int j = 0; j < n.length; j++) {
                            dataMatrix[i][j] = Double.parseDouble(lineContent[j]);
                        }
                    } catch (NumberFormatException e) {
                        throw new NumberFormatException(STR."Incorrect data format at line \{i + 1}. Check data");
                    }
                }
            }
        } else {
            String[] n = (dataLines.get(1)).split("\t", 0);
            dataMatrix = new Double[dataLines.size() - 1][n.length];
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

    public static ArrayList<String> getStrings(String fileName) throws FileNotFoundException {
        ArrayList<String> dataLines = new ArrayList<>();
        File dataFile = new File(fileName);
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
                throw new FileNotFoundException("File not found. Check filename & location.");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new RuntimeException("Cannot read the datafile. Check contents and location.");
        }
        return dataLines;
    }

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
