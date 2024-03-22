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
        //Arrange
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
    void test_det_2by2() {
        //Arrange
        double [][] matrixA = {{3, 1}, {2, 4}};
        Matrix testMat = new Matrix(matrixA);
        //Act
        double determinant = testMat.getDet();
        double expected = 10;
        //Assert
        assertEquals(expected, determinant, "Incorrectly calculated determinant.");
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
            assertEquals("Matrix must be SQUARE with nonzero determinant to be invertible!", illegalArgumentException.getMessage());
        }
    }

    @Test
    void test_swapRows1() { // Swapping rows of a 2x2 matrix
        //Arrange
        double[][] matrixA = {{1, 2}, {3, 4}};
        //Act
        Matrix.swapRows(matrixA,0,1);
        //Assert
        double[][] expectedMatrix = {{3, 4}, {1, 2}};
        assertArrayEquals(expectedMatrix, matrixA);
    }

    @Test
    void test_swapRows2() { // Swapping rows of a 3x2 matrix
        //Arrange
        double[][] matrixA = {{1, 2}, {3, 4}, {5, 6}};
        //Act
        Matrix.swapRows(matrixA,0,2); //* j is equal to 2 in this case because we are swapping the 3rd row (index = 2), not the 2nd row (index = 2)
        //Assert
        double[][] expectedMatrix = {{5, 6}, {3, 4}, {1, 2}};
        assertArrayEquals(expectedMatrix, matrixA);
    }

    @Test
    void test_swapRows3() { // Swapping rows of a 2x2 matrix
        //Arrange
        double[][] matrixA = {{1, 2}, {3, 4}, {5, 6}, {7, 8}};
        //Act
        Matrix.swapRows(matrixA,0,3);
        //Assert
        double[][] expectedMatrix = {{7, 8}, {3, 4}, {5, 6}, {1, 2}};
        assertArrayEquals(expectedMatrix, matrixA);
    }

    @Test
    void test_swapRows4() { // Swapping rows of a 3x3 matrix
        //Arrange
        double[][] matrixA = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        //Act
        Matrix.swapRows(matrixA,0,2); //* j is equal to 2 in this case because we are swapping the 3rd row (index = 2), not the 2nd row (index = 2)
        //Assert
        double[][] expectedMatrix = {{7, 8, 9}, {4, 5, 6}, {1, 2, 3}};
        assertArrayEquals(expectedMatrix, matrixA);
    }

    @Test
    void test_subMatrix1() { //Getting a submatrix from a 3x3 matrix
        //Arrange
        double[][] matrixA = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        //Act
        double[][] submatrixA = Matrix.submatrix(matrixA, 0, 0);
        //Assert
        double[][] expectedSubMatrix = {{5, 6}, {8, 9}};
        assertArrayEquals(expectedSubMatrix, submatrixA);
    }

    @Test
    void test_subMatrix2() { //Getting a different submatrix from a 3x3 matrix
        //Arrange
        double[][] matrixA = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        //Act
        double[][] submatrixA = Matrix.submatrix(matrixA, 2, 2);
        //Assert
        double[][] expectedSubMatrix = {{1, 2}, {4, 5}};
        assertArrayEquals(expectedSubMatrix, submatrixA);
    }

    @Test
    void test_subMatrix3() { //Getting a different submatrix from a 3x3 matrix
        //Arrange
        double[][] matrixA = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        //Act
        double[][] submatrixA = Matrix.submatrix(matrixA, 1, 2);
        //Assert
        double[][] expectedSubMatrix = {{1, 2}, {7, 8}};
        assertArrayEquals(expectedSubMatrix, submatrixA);
    }

    @Test
    void test_getRow() { //Getting all rows from a sample matrix
        //Arrange
        double[][] matrixA = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        Matrix matrix = new Matrix(matrixA);
        //Act
        double[][] expected1stRow = {{1, 2, 3}};
        double[][] expected2ndRow = {{4, 5, 6}};
        double[][] expected3rdRow = {{7, 8, 9}};
        //Assert
        assertArrayEquals(expected1stRow, matrix.getRow(0));
        assertArrayEquals(expected2ndRow, matrix.getRow(1));
        assertArrayEquals(expected3rdRow, matrix.getRow(2));
    }

    @Test
    void test_getOOBRow() { //Getting an out-of-bounds row from a sample matrix
        //Arrange
        double[][] matrixA = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        Matrix matrix = new Matrix(matrixA);
        //Act & Assert
        try {
            matrix.getRow(3); //Attempt to get the non-existent 4th row of the above sample matrix
            fail("Expected ArrayIndexOutOfBoundsException was not thrown");
        }
        catch (ArrayIndexOutOfBoundsException _) {
        }
    }

    @Test
    void test_getCol() { //Getting all columns from a sample matrix
        //Arrange
        double[][] matrixA = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        Matrix matrix = new Matrix(matrixA);
        //Act
        double[][] expected1stColumn = {{1}, {4}, {7}};
        double[][] expected2ndColumn = {{2}, {5}, {8}};
        double[][] expected3rdColumn = {{3}, {6}, {9}};
        //Assert
        assertArrayEquals(expected1stColumn, matrix.getCol(1));
        assertArrayEquals(expected2ndColumn, matrix.getCol(2));
        assertArrayEquals(expected3rdColumn, matrix.getCol(3));
    }

    @Test
    void test_getOOBCol() { //Getting an out-of-bounds column from a sample matrix
        //Arrange
        double[][] matrixA = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        Matrix matrix = new Matrix(matrixA);
        //Act & Assert
        try {
            matrix.getRow(3); //Attempt to get the non-existent 4th row of the above sample matrix
            fail("Expected ArrayIndexOutOfBoundsException was not thrown");
        }
        catch (ArrayIndexOutOfBoundsException _) {
        }
    }
}
