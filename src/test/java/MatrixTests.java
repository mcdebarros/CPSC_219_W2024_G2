import org.junit.jupiter.api.Test;
import java.io.FileNotFoundException;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;
public class MatrixTests {

    @Test
    void test_constructorString() throws FileNotFoundException {

        String dataFile = "src/main/java/data.txt";
        Matrix testMat = new Matrix(dataFile);
        assertNotNull(testMat,"Failed to construct Matrix Object");
    }

    @Test
    void test_getEntry() throws FileNotFoundException {
        String dataFile = "src/main/java/data.txt";
        Matrix testMat = new Matrix(dataFile);
        double expected = 1.0;
        double actual = testMat.getEntry(0,0);
        assertEquals(expected,actual,STR."Failed to retrieve proper entry. Expected \{expected}, received \{actual}");
    }


    @Test
    void test_setEntry() throws FileNotFoundException {
        String dataFile = "src/main/java/data.txt";
        Matrix testMat = new Matrix(dataFile);
        testMat.setEntry(0,0,69.0);
        double expected = 69.0;
        double actual = testMat.getEntry(0,0);
        assertEquals(expected,actual,STR."Failed to set entry. Expected \{expected}, received \{actual}");
    }

    @Test
    void test_size() throws FileNotFoundException {
        String dataFile = "src/main/java/data.txt";
        Matrix testMat = new Matrix(dataFile);
        int[] expected = new int[]{3,3};
        int[] actual = testMat.size();
        System.out.println(Arrays.toString(actual));
        assertEquals(expected[0],actual[0], "Matrix did not initialize with proper dimensions.");
        assertEquals(expected[1],actual[1], "Matrix did not initialize with proper dimensions.");
    }

    @Test
    void test_isSquare() throws FileNotFoundException {

        String dataFile = "src/main/java/data.txt";
        Matrix testMat = new Matrix(dataFile);
        boolean square = testMat.isSquare();
        assertTrue(square,"Nonsquare matrix indicated by test result!");
    }

    @Test
    void test_showMat() throws FileNotFoundException {

        String dataFile = "src/main/java/data.txt";
        Matrix testMat = new Matrix(dataFile);
        testMat.showMat();
    }

    @Test
    void test_equals() throws FileNotFoundException {

        Matrix a = new Matrix("src/main/java/data.txt");
        Matrix b = new Matrix("src/main/java/data.txt");
        boolean equal = a.equals(b);
        assertTrue(equal,"Returned false for identical matrices! Expected true.");

    }

    @Test
    void test_det_2by2() throws FileNotFoundException {

        Matrix testMat= new Matrix("src/main/java/data.txt");
        double determinant = testMat.getDet();
        double expected = 0;
        assertEquals(expected,determinant,STR."Incorrectly calculated determinant. Expected -3.0, received \{determinant}.");
    }

    @Test
    void test_typeData() {
        Matrix Matrix = new Matrix(3, 2);
        String expected = "Data";
        assertEquals(expected, Matrix.getType());
    }

    @Test
    void test_typeRowVector() {
        Matrix Matrix = new Matrix(0, 2);
        String expected = "Row Vector";
                assertEquals(expected, Matrix.getType());
    }

    @Test
    void test_typeColumnVector() {
        Matrix Matrix = new Matrix(2, 0);
        String expected = "Column Vector";
                assertEquals(expected, Matrix.getType());
    }

    @Test
    void test_typeSystem() {
        Matrix Matrix = new Matrix(4, 3);
        String expected = "System";
        assertEquals(expected, Matrix.getType());
    }

    @Test
    void test_typeSquare() throws FileNotFoundException {
        Matrix Matrix = new Matrix("src/main/java/data.txt");
        String expected = "Square";
        assertEquals(expected, Matrix.getType());
    }

    @Test
    void test_transpose() throws FileNotFoundException {

        Matrix testMat = new Matrix("src/main/java/data.txt");
        Matrix testTrans = Matrix.transpose(testMat);
    }

    @Test
    void test_matMult1() { // Multiplying a 2x2 matrix by a 2x2 matrix
        double[][] matrixA = {{1, 2}, {2, 3}};
        double[][] matrixB = {{2, 4}, {3, 6}};
        double [][] expected = {{8, 16}, {13, 26}};
        Matrix result = Matrix.matMult(new Matrix(matrixA), new Matrix(matrixB));
        assertArrayEquals(expected, result.getMatrix());
    }
    @Test
    void test_matMult2() { // Multiplying a 3x2 matrix by a 2x3 matrix
        double[][] matrixA = {{1, 2}, {2, 3}, {3, 4}};
        double[][] matrixB = {{2, 4, 6}, {3, 6, 9}};
        double[][] expected = {{8, 16, 24}, {13, 26, 39}, {18, 36, 54}};
        Matrix result = Matrix.matMult(new Matrix(matrixA), new Matrix(matrixB));
        assertArrayEquals(expected, result.getMatrix());
    }
    @Test
    void test_matMult3() { // Multiplying a 2x3 matrix by a 3x2 matrix
        double[][] matrixA = {{1, 2, 3}, {3, 4, 5}};
        double[][] matrixB = {{2, 4,}, {3, 6}, {4, 8}};
        double[][] expected = {{20, 40}, {38, 76}};
        Matrix result = Matrix.matMult(new Matrix(matrixA), new Matrix(matrixB));
        assertArrayEquals(expected, result.getMatrix());
    }
    @Test
    void test_matMult4() { // Multiplying a 3x3 matrix by a 3x3 matrix
        double[][] matrixA = {{1, 2, 3}, {3, 4, 5}, {5, 6, 7}};
        double[][] matrixB = {{2, 4, 6}, {3, 6, 9}, {4, 8, 12}};
        double[][] expected = {{20, 40, 60}, {38, 76, 114}, {56, 112, 168}};
        Matrix result = Matrix.matMult(new Matrix(matrixA), new Matrix(matrixB));
        assertArrayEquals(expected, result.getMatrix());
    }
}
