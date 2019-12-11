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
        when(byteSupplier.get())
                //1st matrix
                .thenReturn((byte) 1).thenReturn((byte) 1)
                .thenReturn((byte) 1).thenReturn((byte) 0)
                //2d matrix
                .thenReturn((byte) 1).thenReturn((byte) 0)
                .thenReturn((byte) 1).thenReturn((byte) 1);
    }

    @Test
    public void test() {
        short n = 2;
        Matrix matrix1 = Matrix.getSquareMatrix(n, byteSupplier);
        Matrix matrix2 = Matrix.getSquareMatrix(n, byteSupplier);

        MatricesMultiplier mm = new SequentialMatricesMultiplier();
        Matrix actual = mm.multiply(matrix1, matrix2);

        byte[][] expected = new byte[][]{
                new byte[]{ (byte) 0, (byte) 1 },
                new byte[]{ (byte) 1, (byte) 0 }
        };
        assertArrayEquals(expected, actual.getData());
    }
}