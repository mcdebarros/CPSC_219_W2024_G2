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
}