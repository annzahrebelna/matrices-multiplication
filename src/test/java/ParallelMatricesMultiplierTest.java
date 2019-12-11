import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertArrayEquals;

@RunWith(MockitoJUnitRunner.class)
public class ParallelMatricesMultiplierTest {

    @Test
    public void test() {
        short n = 1000;
        SquareMatrix matrix1 = new SquareMatrix(n);
        matrix1.initialize();
        SquareMatrix matrix2 = new SquareMatrix(n);
        matrix2.initialize();

        MatricesMultiplier mmExpected = new SequentialMatricesMultiplier();
        long startSeq = System.currentTimeMillis();
        byte[][] expected = mmExpected.multiply(matrix1, matrix2);
        long endSeq = System.currentTimeMillis();
        System.out.println("sequential: " + (endSeq - startSeq));

        MatricesMultiplier mm = new ParallelMatricesMultiplier();
        long startPar = System.currentTimeMillis();
        byte[][] actual = mm.multiply(matrix1, matrix2);
        long endPar = System.currentTimeMillis();
        System.out.println("parallel: " + (endPar - startPar));

        assertArrayEquals(expected, actual);
    }
}