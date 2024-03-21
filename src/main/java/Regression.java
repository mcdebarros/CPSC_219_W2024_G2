// Import packages
import java.util.*;
import java.io.*;
import static java.lang.StringTemplate.STR;

public class Regression {

    static final String INTRO = "\nWelcome to the linear regression tool!";
    static final String HELP = """
            - To use the tool, create a datafile named "data.txt" and copy the filepath.
            - Your data file should contain m rows and n columns, and be populated by decimal entries separated by tabs.
            - Once prompted, tell the program the path to your datafile, and the integer order of the model you wish to fit.
            - The program will then run the regression tool and fit coefficients for your model, as well as generate an rsq and phi.
            - You will have the option to write this data to an output file once the regression is complete.
            - If at any point you wish to see the help menu, just enter "HELP".""";
    static final String PROMPT_PATH = "Please input your file path: ";
    static final String PROMPT_ORDER = "\nPlease input your model order: ";

    /**
     * Collects inputs and directs execution of linear LSR
     * @param args String[] array of input arguments. Not used.
     * @throws IllegalArgumentException if regression produces non-square zTz matrix
     * @throws FileNotFoundException if file not found at specified path
     */
    public static void main(String[] args) throws IllegalArgumentException, FileNotFoundException {

        int order; // Initialize order integer
        String orderStr; // Initialize order string
        String path; // Initialize filepath string
        Scanner input = new Scanner(System.in); // Initialize input scanner
        System.out.println(STR."\{INTRO}\n"); // Print introduction and use instructions, then prompt for file path
        System.out.println(STR."\{HELP}\n");
        System.out.println(PROMPT_PATH);
        path = input.nextLine().toUpperCase();
        while (path.equals("HELP")) {
            System.out.println(HELP); // Show the help message and recollect input as long as the user inputs 'help'
            path = input.nextLine().toUpperCase();
        }
        System.out.println(PROMPT_ORDER); // Prompt for and collect order string
        orderStr = input.nextLine();
        while (orderStr.equals("HELP")) {
            System.out.println(HELP);
            orderStr = input.nextLine().toUpperCase();
        }
        while (!isInt(orderStr)) { // Iterate this loop until an integer order is received
            if (orderStr.equals(HELP)) {
                System.out.println(HELP);
                orderStr = input.nextLine().toUpperCase();
            } else {
                System.err.println("Unable to parse integer from input. Use only a single positive digit.");
                orderStr = input.nextLine();
            }
        }
        order = Integer.parseInt(orderStr); // Parse the integer from the input string
        Matrix data = new Matrix(path); // Initialize the data matrix object
        List<Object> model = linear(data,order); // Call the linear function to perform regression

        System.out.println("\nModel generated! Find model data below:\n");
        printResults(model);
        System.out.println("\n\nWould you like to write it to a file? (Y/N)"); // Prompt for file writing and collect selection
        String write = input.nextLine().toUpperCase();
        while (!((write.equals("Y")) || write.equals("N"))) { // Loop while not yes or no
            System.out.println("I didn't understand your selection. Please choose one of (Y/N).");
            write = input.nextLine();
        }
        if (write.equals("Y")) { // Call the file writer if input is yes
            MatWriter.writeModel(model);
        }
        System.out.println("\nSee you next time!");
    }

    /**
     * Performs linear LSR from data matrix
     * @param data Matrix object of scattered data
     * @param order Order of model to fit
     * @return Object List of model outputs
     */
    public static List<Object> linear(Matrix data, int order) {
        Matrix z = new Matrix(data.size()[0],order + 1); //Initialize the z array (parameters to multiply resultant coefficients by)
        Matrix y = new Matrix(data.size()[0],1); //Initialize the y vector (actual data)
        Matrix aveVec = new Matrix(data.size()[0],1); //Initialize "data mean" vector; populated entirely by mean of actual data
        double dataAve = 0.0; //Initialize data mean
        List<Object> model = new ArrayList<>(); //Initialize object list to be populated with model outputs
        for (int i = 0; i < data.size()[0]; i++) { //Populate the y vector with provided data
            y.setEntry(i,0,data.getEntry(i,1));

        }
        for (int i = 0; i < data.size()[0]; i++) { //Populate the z matrix with appropriate parameters
            for (int j = 0; j < order; j++) {
                z.setEntry(i,order - j, Math.pow(data.getEntry(i,0),(order - j)));
            }
        }
        for (int i = 0; i < data.size()[0]; i++) { //Populate the first column of the z array with ones; above method does not seem to be able to do this and instead populates w/ null
            z.setEntry(i,0,1.0);
        }
        Matrix zT = Matrix.transpose(z); //Create the transpose of the z matrix
        Matrix zTz = Matrix.matMult(zT,z); //Multiply the z matrix by its transpose
        Matrix zTy = Matrix.matMult(zT,y); //Multiply the zT matrix by the y vector
        Matrix zTzInv = Matrix.inverse(zTz); //Invert the zTz matrix
        Matrix a = Matrix.matMult(zTzInv,zTy); //Calculate the vector of model coefficients
        Matrix modelOut = Matrix.matMult(z,a); //Calculate model output based on fit coefficients
        Matrix model_e = new Matrix(data.size()[0],1); //Initialize vector of model residuals
        Matrix data_e = new Matrix(data.size()[0],1); //Initialize vector of data residuals
        double phiMod = 0.0; //Initialize sum of squared residuals for model
        double phiData = 0.0; //Initialize sum of squared residuals for model
        for (int i = 0; i < data.size()[0]; i++) {
            dataAve += y.getEntry(i,0); //Sum all y values contained in the data
        }
        dataAve = dataAve / data.size()[0]; //Calculate the mean of all data y values
        for (int i = 0; i < data.size()[0]; i++) {
            aveVec.setEntry(i,0, dataAve);
            data_e.setEntry(i,0, y.getEntry(i,0) - aveVec.getEntry(i,0)); //Calculate and record the residual between the data average and the y data
            model_e.setEntry(i,0,y.getEntry(i,0) - modelOut.getEntry(i,0));//Calculate and record the residual between the modelled data and the input data
            double squareMod = Math.pow(model_e.getEntry(i,0),2); //Square the model residual
            double squareData = Math.pow(data_e.getEntry(i,0),2); //Square the data residual
            phiMod += squareMod; //Increment the sum of squared residuals
            phiData += squareData; //Increment the sum of squared residuals
        }
        double rsq = (phiData - phiMod) / phiData; //Calculate the R squared value of the produced model
        model.add(a); //Populate the model list with model output
        model.add(phiMod); //Populate the model list with model output
        model.add(rsq); //Populate the model list with model output

        return model; //Return the model output list
    }

    /**
     * Checks if order String is an integer
     * @param orderStr String of model order
     * @return boolean of integer identity
     */
    public static boolean isInt(String orderStr) {

        int number;
        try {
            number = Integer.parseInt(orderStr); // Attempt to parse int from order string
            return true; // True if successful
        } catch (NumberFormatException e) {
            return false; // False if unsuccessful
        }
    }
    public static void printResults(List<Object> input) {
        String redColor = "\u001B[31m";
        String resetColor = "\u001B[0m";
        String TOP_BORDER = STR."\{redColor}╔═══RESULTS═══╗\{resetColor}";
        String MID_BORDER = STR."\{redColor}╠═════════════╣\{resetColor}";
        String BOTTOM_BORDER = STR."\{redColor}╚═════════════╝\{resetColor}";

        Matrix a = (Matrix) input.getFirst(); // Fetch and cast the coefficient matrix object
        double phi = (double) input.get(1); // Fetch and cast the phi double
        double rsq = (double) input.get(2); // Fetch and cast the rsq double
        double rsqNew = Math.floor(rsq*1000000)/1000000;

        System.out.println(TOP_BORDER); //Forms the border top.
        for (int i = 0; i < a.size()[0]; i++) {
            if (a.getEntry(i,0) >=0) {
                System.out.printf(STR."\{redColor}║\{resetColor} A%.1s: %.1f     \{redColor}║\{resetColor}\n", (float) i, a.getEntry(i, 0));
            }
            else {
                System.out.printf(STR."\{redColor}║\{resetColor} A%.1s: %.1f    \{redColor}║\{resetColor}\n", (float) i, a.getEntry(i, 0));            }
        }

        System.out.println(MID_BORDER);

        System.out.println(STR."\{redColor}║\{resetColor}     PHI     \{redColor}║\{resetColor}");
        System.out.printf(STR."\{redColor}║\{resetColor}  %.2e   \{redColor}║\{resetColor}\n", phi);

        System.out.println(MID_BORDER);

        System.out.println(STR."\{redColor}║\{resetColor}     RSQ     \{redColor}║\{resetColor}");
        System.out.printf(STR."\{redColor}║\{resetColor}   %.5f   \{redColor}║\{resetColor}\n", rsqNew);

        System.out.println(BOTTOM_BORDER);
    }
}
