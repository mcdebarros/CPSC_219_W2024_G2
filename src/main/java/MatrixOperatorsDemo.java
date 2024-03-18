import java.io.*;

public class MatrixOperatorsDemo {
    public static void main(String args[]) throws FileNotFoundException {
        System.out.print("""
                This demo is here to show the matrix operations in action!
                """);
        Matrix data = new Matrix(args[0]);
        System.out.println("Original Matrix:");
        data.showMat();

        System.out.println("Transposed Matrix");
        Matrix transposedData;
        transposedData = new Matrix(args[0]);
        transposedData.showMat();

    }
}
