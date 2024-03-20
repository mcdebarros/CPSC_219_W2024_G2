import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MatWriter {

    /**
     * Writes model outputs to file
     * @param model Object list of model parameters
     */
    public static void writeModel(List<Object> model) {

        Matrix a = (Matrix) model.getFirst();
        double phi = (double) model.get(1);
        double rsq = (double) model.get(2);

        File coefficients = new File("coefficients.txt"); //Initialize a txt file to store model outputs
        if (!coefficients.exists()) { //Create a new output file if one does not already exist
            try {
                coefficients.createNewFile();
            } catch (IOException e) { //Terminate the program if a new file cannot be created
                System.err.println("Trouble writing to file! Check location and do not interrupt.");
                System.exit(9);
            }
        }
        if (coefficients.exists() && coefficients.isFile() && coefficients.canWrite()) { //Check file existence, writeability, and file-ness
            try {
                FileWriter aWrite = new FileWriter(coefficients); //Initialize file writer
                BufferedWriter aBuffed = new BufferedWriter(aWrite); //Initialized buffered writer
                for (int i = 0; i < a.size()[0]; i++) { //For each coefficient, write a new line containing the order of the coefficient and its value, separated by a tab
                    String coefIndex = STR."a\{i}";
                    aBuffed.write(STR."\{coefIndex}\t\{a.getEntry(0,i)}\n");
                }
                aBuffed.write(STR."\nphi\t\{phi}\nRSQ\t\{rsq}"); //Write the phi and rsq values to the file on their own lines
                aBuffed.flush(); //Flush the file
                aBuffed.close(); //Close the file
                System.out.println("""
                            File written as "coefficients.txt"! See you next time!""");
            } catch (IOException e) { //Terminate the program if the file cannot be written to
                System.err.println("Oops! Couldn't write to the file.");
            }
        } else { //Terminate the program if the file to write to cannot be created or found
            System.err.println("Cannot access file to write to!");
            System.exit(11);
        }
        System.out.println("Model written to 'coefficients.txt'.");
    }

    /**
     * Writes matrices to file
     * @param mat Matrix object to write
     */
    public static void writeMat(Matrix mat) {

        File matrixData = new File("matrixData.txt"); //Initialize a txt file to store model outputs
        if (!matrixData.exists()) { //Create a new output file if one does not already exist
            try {
                matrixData.createNewFile();
            } catch (IOException e) { //Terminate the program if a new file cannot be created
                System.err.println("Trouble writing to file! Check location and do not interrupt.");
                System.exit(9);
            }
        }
        if (matrixData.exists() && matrixData.isFile() && matrixData.canWrite()) { //Check file existence, writeability, and file-ness
            try {
                FileWriter mWrite = new FileWriter(matrixData); //Initialize file writer
                BufferedWriter aBuffed = new BufferedWriter(mWrite); //Initialized buffered writer
                aBuffed.write(STR."\{mat.toString()}\n\ndet = \{mat.getDet()}\nsize = \{Arrays.toString(mat.size())}"); //Write the phi and rsq values to the file on their own lines
                aBuffed.flush(); //Flush the file
                aBuffed.close(); //Close the file
                System.out.println("""
                            File written as "coefficients.txt"! See you next time!""");
            } catch (IOException e) { //Terminate the program if the file cannot be written to
                System.err.println("Oops! Couldn't write to the file.");
            }
        } else { //Terminate the program if the file to write to cannot be created or found
            System.err.println("Cannot access file to write to!");
            System.exit(11);
        }
        System.out.println("Model written to 'coefficients.txt'.");
    }
}
