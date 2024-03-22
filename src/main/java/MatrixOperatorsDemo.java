import java.io.*;
import java.util.Arrays;

public class MatrixOperatorsDemo {
    public static void main(String[] args) throws FileNotFoundException {
        String blueColor = "\u001B[34m";
        String cyanColor = "\u001B[36;1m";
        String resetColor = "\u001B[0m";

        System.out.print("""
                \nThis demo is here to show the matrix operations in action!
                \n""");
        Matrix data = new Matrix(args[0]);

        // Shows Original Matrix
        System.out.println(STR."\{cyanColor}Original Matrix:\{cyanColor}");
        data.showMat();
        System.out.println();

        // Shows all the important info
        // Determinant, Length and Width of Matrix, whether it is a square matrix or not, and the type of data inputted
        System.out.println(STR."\{cyanColor}Important Info:\{cyanColor}");
        System.out.println(STR."\{blueColor}~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\{resetColor}");
        System.out.println(STR."\{blueColor}|\{resetColor}  Determinant\t\{blueColor}|\{resetColor}\t [Length, Width] \t\{blueColor}|\{resetColor}\tSquare Matrix?\t\{blueColor}|\{resetColor}\t\tType?\t\t\{blueColor}|\{resetColor}");
        System.out.println(STR."\{blueColor}|---------------|-----------------------|-------------------|-------------------|\{resetColor}");
        System.out.print(STR."\{blueColor}|\{resetColor}\t  \{data.getDet()}\t\t");
        System.out.print(STR."\{blueColor}|\{resetColor}\t\t \{Arrays.toString(data.size())}\t\t\t");
        if (data.isSquare()) {
            System.out.print(STR."\{blueColor}|\{resetColor}\t\tTrue\t\t\{blueColor}|\{resetColor}");
        } else {
            System.out.print(STR."\{blueColor}|\{resetColor}\t\t  False\t\t\t\{blueColor}|\{resetColor}");
        }
        System.out.println(STR."\t\t\{data.getType()}\t\t\{blueColor}|\{resetColor}");
        System.out.println(STR."\{blueColor}~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\{resetColor}");
        System.out.println();

        // Shows Transposed Matrix
        System.out.println(STR."\{cyanColor}Transposed Matrix:\{cyanColor}");
        Matrix transposedData = Matrix.transpose(data);
        transposedData.showMat();
        System.out.println();

        // Shows Inverted Matrix
        System.out.println(STR."\{cyanColor}Inverse Matrix:\{cyanColor}");
        Matrix invertedData = Matrix.inverse(data);
        invertedData.showMat();
        System.out.println();

        // Shows Multiplied Matrix of Original Matrix First and Inverse Matrix
        // **Should show the Identity Matrix**
        System.out.println(STR."\{cyanColor}Multiplied Matrix of Original Matrix and Inverse Matrix:\{cyanColor}");
        Matrix multipliedMatrix = Matrix.matMult(data, invertedData);
        multipliedMatrix.showMat();

    }
}
