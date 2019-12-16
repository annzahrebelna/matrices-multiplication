import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ParallelMatricesMultiplierTest {

    @Test
    public void test() {
        short n = 100;
        Matrix matrix1 = Matrix.getSquareMatrix(n);
        Matrix matrix2 = Matrix.getSquareMatrix(n);

        AbstractMatricesMultiplier mmExpected = new SequentialMatricesMultiplier();
        Matrix expected = mmExpected.multiply(matrix1, matrix2);
        System.out.println("sequential: " + mmExpected.getTimeSpent());

        AbstractMatricesMultiplier mm = new ParallelMatricesMultiplier();
        Matrix actual = mm.multiply(matrix1, matrix2);
        System.out.println("parallel: " + mm.getTimeSpent());

        assertArrayEquals(expected.getData(), actual.getData());
    }
}