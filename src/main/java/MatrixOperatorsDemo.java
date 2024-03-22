import java.io.*;
import java.util.Arrays;

public class MatrixOperatorsDemo {
    public static void main(String[] args) throws FileNotFoundException {
        String blueColor = "\u001B[34m";
        String yellowColor = "\u001B[33;1m";
        String resetColor = "\u001B[0m";


        System.out.print("""
                \nThis demo is here to show the matrix operations in action!
                \n""");
        Matrix data = new Matrix(args[0]);

        System.out.println(STR."\{yellowColor}Original Matrix:\{yellowColor}");
        data.showMat();
        System.out.println();

        System.out.println(STR."\{yellowColor}Important Info:\{yellowColor}");
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
        System.out.println(STR."\{yellowColor}Transposed Matrix:\{yellowColor}");
        Matrix transposedData = Matrix.transpose(data);
        transposedData.showMat();
        System.out.println();

        System.out.println(STR."\{yellowColor}Inverse Matrix:\{yellowColor}");
        Matrix invertedData = Matrix.inverse(data);
        invertedData.showMat();
        System.out.println();

        System.out.println(STR."\{yellowColor}Multiplied Matrix of Original Matrix and Inverse Matrix:\{yellowColor}");
        Matrix multipliedMatrix = Matrix.matMult(data, invertedData);
        multipliedMatrix.showMat();

    }
}
