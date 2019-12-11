import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.function.Supplier;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SequentialMatricesMultiplierTest {

    @Mock
    private Supplier<Byte> byteSupplier;

    @Before
    public void setUp() {
        byte b1 = (byte) 1;
        byte b0 = (byte) 0;
        when(byteSupplier.get())
                //1st matrix
                .thenReturn(b1).thenReturn(b1)
                .thenReturn(b1).thenReturn(b0)
                //2d matrix
                .thenReturn(b1).thenReturn(b0)
                .thenReturn(b1).thenReturn(b1);
    }

    @Test
    public void test() {
        short n = 2;
        SquareMatrix matrix1 = new SquareMatrix(n);
        matrix1.initialize(byteSupplier);
        SquareMatrix matrix2 = new SquareMatrix(n);
        matrix2.initialize(byteSupplier);

        MatricesMultiplier mm = new SequentialMatricesMultiplier();
        byte[][] actual = mm.multiply(matrix1, matrix2);

        byte[][] expected = new byte[n][n];
        expected[0][0] = 0; //1&1=0 0^0=0
        expected[0][1] = 1;
        expected[1][0] = 1;
        expected[1][1] = 0;
        assertArrayEquals(expected, actual);
    }
}