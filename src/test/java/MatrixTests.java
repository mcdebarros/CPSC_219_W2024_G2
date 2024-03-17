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
        Double expected = 1.0;
        Double actual = testMat.getEntry(0,0);
        assertEquals(expected,actual,STR."Failed to retrieve proper entry. Expected \{expected}, received \{actual}");
    }


    @Test
    void test_setEntry() throws FileNotFoundException {
        String dataFile = "src/main/java/data.txt";
        Matrix testMat = new Matrix(dataFile);
        testMat.setEntry(0,0,69.0);
        Double expected = 69.0;
        Double actual = testMat.getEntry(0,0);
        assertEquals(expected,actual,STR."Failed to set entry. Expected \{expected}, received \{actual}");
    }

    @Test
    void test_size() throws FileNotFoundException {
        String dataFile = "src/main/java/data.txt";
        Matrix testMat = new Matrix(dataFile);
        int[] expected = new int[]{2,2};
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
    void test_typeData() {
        Matrix Matrix = new Matrix(3, 2);
        assertEquals("Data", Matrix.getType());
    }

    @Test
    void test_typeRowVector() {
        Matrix Matrix = new Matrix(0, 2);
        assertEquals("Row Vector", Matrix.getType());
    }

    @Test
    void test_typeColumnVector() {
        Matrix Matrix = new Matrix(2, 0);
        assertEquals("Column Vector", Matrix.getType());
    }

    @Test
    void test_typeSystem() {
        Matrix Matrix = new Matrix(4, 3);
        assertEquals("System", Matrix.getType());
    }
}
