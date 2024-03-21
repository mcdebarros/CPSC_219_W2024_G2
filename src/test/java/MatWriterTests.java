import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.util.Arrays;
import java.util.List;

public class MatWriterTests {

    @Test
    void test_writeModel() { // Testing to see if the MatWriter() class can write a sample regression model to a file
        //Arrange
        Matrix matrixA = new Matrix(new double[][] {{10, 20}, {30, 40}});
        double phiValue = 1;
        double rsqValue = 2;
        List<Object> sampleModel = Arrays.asList(matrixA, phiValue, rsqValue);
        //Act
        MatWriter.writeModel(sampleModel);
        //Assert
        assertTrue(new File("coefficients.txt").exists());
    }

    @Test
    void test_writeMat() { // Testing to see if the MatWriter() class can write a sample matrix to a file
        //Arrange
        Matrix matrixA = new Matrix(new double[][] {{10, 20}, {30, 40}});
        //Act
        MatWriter.writeMat(matrixA);
        //Assert
        assertTrue(new File("matrixData.txt").exists());
    }
}
