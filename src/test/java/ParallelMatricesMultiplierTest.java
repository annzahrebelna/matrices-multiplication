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

        MatricesMultiplier mmExpected = new SequentialMatricesMultiplier();
        long startSeq = System.currentTimeMillis();
        Matrix expected = mmExpected.multiply(matrix1, matrix2);
        long endSeq = System.currentTimeMillis();
        System.out.println("sequential: " + (endSeq - startSeq));

        MatricesMultiplier mm = new ParallelMatricesMultiplier();
        long startPar = System.currentTimeMillis();
        Matrix actual = mm.multiply(matrix1, matrix2);
        long endPar = System.currentTimeMillis();
        System.out.println("parallel: " + (endPar - startPar));

        assertArrayEquals(expected.getData(), actual.getData());
    }
}