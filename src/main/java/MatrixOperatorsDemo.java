import java.io.*;

public class MatrixOperatorsDemo {
    public static void main(String[] args) throws FileNotFoundException {

        System.out.print("""
                This demo is here to show the matrix operations in action!
                """);
        Matrix data = new Matrix(args[0]);

        System.out.println("Original Matrix:");
        data.showMat();
        System.out.println();

        System.out.println("Transposed Matrix");
        Matrix transposedData = Matrix.transpose(data);
        transposedData.showMat();
        System.out.println();

        System.out.println("Inverse Matrix");
        Matrix invertedData = Matrix.inverse(data);
        invertedData.showMat();
        System.out.println("Original data");
        data.showMat();
        System.out.println();

        System.out.println("Multiplied Matrix of Original Matrix and Transposed Matrix");
        Matrix multipliedMatrix = Matrix.matMult(data, invertedData);

        multipliedMatrix.showMat();

    }
}
