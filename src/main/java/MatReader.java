import java.io.*;
import java.util.*;
import java.lang.Math;

public class MatReader {

    public static Double[][] getData(String fileName) throws NumberFormatException, FileNotFoundException {

        ArrayList<String> dataLines = getStrings(fileName);
        Double[][] dataMatrix;
        boolean hasHeader = containsHeaders(dataLines.getFirst());
        if (!hasHeader) {
            dataMatrix = new Double[dataLines.size()][2];
            for (int i = 0; i < dataLines.size(); i++) {
                if (!(dataLines.get(i)).isEmpty()) {
                    try {
                        String[] lineContent = (dataLines.get(i)).split("\t", 0);
                        dataMatrix[i][0] = Double.parseDouble(lineContent[0]);
                        dataMatrix[i][1] = Double.parseDouble(lineContent[1]);
                    } catch (NumberFormatException e) {
                        throw new NumberFormatException(STR."Incorrect data format at line \{i + 1}. Check data");
                    }
                }
            }
        } else {
            dataMatrix = new Double[dataLines.size() - 1][2];
            for (int i = 1; i < dataLines.size(); i++) {
                if (!(dataLines.get(i)).isEmpty()) {
                    try {
                        String[] lineContent = (dataLines.get(i)).split("\t", 0);
                        dataMatrix[i - 1][0] = Double.parseDouble(lineContent[0]);
                        dataMatrix[i - 1][1] = Double.parseDouble(lineContent[1]);
                        if (dataMatrix[i - 1].length != 2) {
                            System.err.println(STR."Too many data points on line \{i + 1}, check data and try again!");
                            System.exit(12);
                        }
                    } catch (NumberFormatException e) {
                        System.err.println(STR."Something's fishy at line \{i + 1}, check data and try again!");
                        System.exit(6);
                    }
                }
            }
        }
        return dataMatrix;
    }

    private static ArrayList<String> getStrings(String fileName) throws FileNotFoundException {
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

    private static boolean containsHeaders(String line) {
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
