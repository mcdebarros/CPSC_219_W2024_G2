import java.io.*;
import java.util.Arrays;

public class MatrixOperatorsDemo {
    public static void main(String[] args) throws FileNotFoundException {

        System.out.print("""
                \nThis demo is here to show the matrix operations in action!
                \n""");
        Matrix data = new Matrix(args[0]);

        System.out.println("Original Matrix:");
        data.showMat();
        System.out.println();

        System.out.println("Important Info:");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("|  Determinant\t|\t [Length, Width] \t|\tSquare Matrix?\t|\t\tType?\t\t|");
        System.out.println("---------------------------------------------------------------------------------");
        System.out.print(STR."|\t  \{data.getDet()}\t\t");
        System.out.print(STR."|\t\t \{Arrays.toString(data.size())}\t\t\t");
        if (data.isSquare()) {
            System.out.print("|\t\tTrue\t\t|");
        } else {
            System.out.print("|\t\t  False\t\t\t|");
        }
        System.out.println(STR."\t\t\{data.getType()}\t\t|");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println();

        System.out.println("Transposed Matrix:");
        Matrix transposedData = Matrix.transpose(data);
        transposedData.showMat();
        System.out.println();

        System.out.println("Inverse Matrix:");
        Matrix invertedData = Matrix.inverse(data);
        invertedData.showMat();
        System.out.println();

        System.out.println("Multiplied Matrix of Original Matrix and Inverse Matrix:");
        Matrix multipliedMatrix = Matrix.matMult(data, invertedData);
        multipliedMatrix.showMat();

    }
}
