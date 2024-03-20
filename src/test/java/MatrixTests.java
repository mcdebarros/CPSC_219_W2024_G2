import org.junit.jupiter.api.Test;
import java.io.FileNotFoundException;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;
public class MatrixTests {

    @Test
    void test_constructorString() throws FileNotFoundException {
        //Arrange
        String dataFile = "src/main/java/data.txt";
        //Act
        Matrix testMat = new Matrix(dataFile);
        //Assert
        assertNotNull(testMat,"Failed to construct Matrix Object");
    }

    @Test
    void test_getEntry() throws FileNotFoundException {
        //Arrange
        String dataFile = "src/main/java/data.txt";
        Matrix testMat = new Matrix(dataFile);
        double expected = 1.0;
        //Act
        double actual = testMat.getEntry(0,0);
        //Assert
        assertEquals(expected,actual,STR."Failed to retrieve proper entry. Expected \{expected}, received \{actual}");
    }

    @Test
    void test_setEntry() throws FileNotFoundException {
        //Arrange
        String dataFile = "src/main/java/data.txt";
        Matrix testMat = new Matrix(dataFile);
        testMat.setEntry(0,0,69.0);
        double expected = 69.0;
        //Act
        double actual = testMat.getEntry(0,0);
        //Assert
        assertEquals(expected,actual,STR."Failed to set entry. Expected \{expected}, received \{actual}");
    }

    @Test
    void test_size() throws FileNotFoundException {
        //Arrange
        String dataFile = "src/main/java/data.txt";
        Matrix testMat = new Matrix(dataFile);
        int[] expected = new int[]{3,3};
        //Act
        int[] actual = testMat.size();
        System.out.println(Arrays.toString(actual));
        //Assert
        assertEquals(expected[0],actual[0], "Matrix did not initialize with proper dimensions.");
        assertEquals(expected[1],actual[1], "Matrix did not initialize with proper dimensions.");
    }

    @Test
    void test_isSquare() throws FileNotFoundException {
        //Assert
        String dataFile = "src/main/java/data.txt";
        Matrix testMat = new Matrix(dataFile);
        //Act
        boolean square = testMat.isSquare();
        //Assert
        assertTrue(square,"Nonsquare matrix indicated by test result!");
    }

    @Test
    void test_showMat() throws FileNotFoundException {
        //Arrange
        String dataFile = "src/main/java/data.txt";
        Matrix testMat = new Matrix(dataFile);
        //Act
        testMat.showMat();
        //Assert --> No assertion is needed
    }

    @Test
    void test_equals() throws FileNotFoundException {
        //Arrange
        Matrix a = new Matrix("src/main/java/data.txt");
        Matrix b = new Matrix("src/main/java/data.txt");
        //Act
        boolean equal = a.equals(b);
        //Assert
        assertTrue(equal,"Returned false for identical matrices! Expected true.");

    }

    @Test
    void test_det_2by2() throws FileNotFoundException {
        //Arrange
        Matrix testMat= new Matrix("src/main/java/data.txt");
        //Act
        double determinant = testMat.getDet();
        double expected = 0;
        //Assert
        assertEquals(expected,determinant,STR."Incorrectly calculated determinant. Expected -3.0, received \{determinant}.");
    }

    @Test
    void test_typeData() {
        //Arrange
        Matrix Matrix = new Matrix(3, 2);
        String expected = "Data";
        //Act --> No action needed
        //Assert
        assertEquals(expected, Matrix.getType());
    }

    @Test
    void test_typeRowVector() {
        //Arrange
        Matrix Matrix = new Matrix(0, 2);
        String expected = "Row Vector";
        //Act --> No action needed
        //Assert
        assertEquals(expected, Matrix.getType());
    }

    @Test
    void test_typeColumnVector() {
        //Arrange
        Matrix Matrix = new Matrix(2, 0);
        String expected = "Column Vector";
        //Act --> No action needed
        //Assert
        assertEquals(expected, Matrix.getType());
    }

    @Test
    void test_typeSystem() {
        //Assert
        Matrix Matrix = new Matrix(4, 3);
        String expected = "System";
        //Act --> No action needed
        //Assert
        assertEquals(expected, Matrix.getType());
    }

    @Test
    void test_typeSquare() throws FileNotFoundException {
        //Assert
        Matrix Matrix = new Matrix("src/main/java/data.txt");
        String expected = "Square";
        //Act --> No action needed
        //Assert
        assertEquals(expected, Matrix.getType());
    }

    @Test
    void test_transpose() throws FileNotFoundException {
        //Arrange
        Matrix testMat = new Matrix("src/main/java/data.txt");
        //Act
        Matrix testTrans = Matrix.transpose(testMat);
        //Assert --> No assertion needed
    }

    @Test
    void test_matMult1() { // Multiplying a 2x2 matrix by a 2x2 matrix
        //Arrange
        double[][] matrixA = {{1, 2}, {2, 3}};
        double[][] matrixB = {{2, 4}, {3, 6}};
        double [][] expected = {{8, 16}, {13, 26}};
        //Act
        Matrix result = Matrix.matMult(new Matrix(matrixA), new Matrix(matrixB));
        //Assert
        assertArrayEquals(expected, result.getMatrix());
    }
    @Test
    void test_matMult2() { // Multiplying a 3x2 matrix by a 2x3 matrix
        //Arrange
        double[][] matrixA = {{1, 2}, {2, 3}, {3, 4}};
        double[][] matrixB = {{2, 4, 6}, {3, 6, 9}};
        double[][] expected = {{8, 16, 24}, {13, 26, 39}, {18, 36, 54}};
        //Act
        Matrix result = Matrix.matMult(new Matrix(matrixA), new Matrix(matrixB));
        //Assert
        assertArrayEquals(expected, result.getMatrix());
    }
    @Test
    void test_matMult3() { // Multiplying a 2x3 matrix by a 3x2 matrix
        //Arrange
        double[][] matrixA = {{1, 2, 3}, {3, 4, 5}};
        double[][] matrixB = {{2, 4,}, {3, 6}, {4, 8}};
        double[][] expected = {{20, 40}, {38, 76}};
        //Act
        Matrix result = Matrix.matMult(new Matrix(matrixA), new Matrix(matrixB));
        //Assert
        assertArrayEquals(expected, result.getMatrix());
    }
    @Test
    void test_matMult4() { // Multiplying a 3x3 matrix by a 3x3 matrix
        //Arrange
        double[][] matrixA = {{1, 2, 3}, {3, 4, 5}, {5, 6, 7}};
        double[][] matrixB = {{2, 4, 6}, {3, 6, 9}, {4, 8, 12}};
        double[][] expected = {{20, 40, 60}, {38, 76, 114}, {56, 112, 168}};
        //Act
        Matrix result = Matrix.matMult(new Matrix(matrixA), new Matrix(matrixB));
        //Assert
        assertArrayEquals(expected, result.getMatrix());
    }

    @Test
    void test_invertibleMatrix() { // Testing an invertible matrix
        //Arrange
        double[][] matrixA = {{2, 0}, {0, 2}};
        Matrix matrix = new Matrix(matrixA);
        //Act
        Matrix inverseMatrix = Matrix.inverse(matrix);
        //Assert
        double[][] expectedInvertedMatrix = {{0.5, 0}, {0, 0.5}};
        Matrix expectedInverse = new Matrix(expectedInvertedMatrix);
        assertArrayEquals(expectedInverse.getMatrix(), inverseMatrix.getMatrix());
    }

    @Test
    void test_nonInvertibleMatrix() { // Testing a non-invertible matrix
        //Arrange
        double[][] matrixA = {{1, 2}, {4, 8}};
        Matrix matrix = new Matrix(matrixA);
        //Act
        try {
            Matrix.inverse(matrix);
            fail("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException illegalArgumentException) {
            //Assert
            assertEquals("Matrix must be square with nonzero determinant to be invertible!", illegalArgumentException.getMessage());
        }
    }
}
