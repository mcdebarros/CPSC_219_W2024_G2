import java.util.*;
import java.io.*;

import static java.lang.StringTemplate.STR;

public class Regression {

    public static void main(String[] args) throws IllegalArgumentException, FileNotFoundException {

        int order;
        Scanner input = new Scanner(System.in);
        if (args.length != 2) {
            throw new IllegalArgumentException(STR."Incorrect number of arguments! Expected 2, received \{args.length}");
        } else {
            try {
                order = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Unable to interpret order argument!");
            }
        }
        Matrix data = new Matrix(args[0]);
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
