import matrix.ArraysMultiplier;
import matrix.Matrix;
import matrix.SequentialMatricesMultiplier;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.function.Supplier;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SequentialMatricesMultiplierTest {

    @InjectMocks
    private SequentialMatricesMultiplier sequentialMatricesMultiplier;
    @Mock
    private ArraysMultiplier arraysMultiplier;

    @Mock
    private Supplier<Byte> byteSupplier;

    @Before
    public void setUp() {
        when(byteSupplier.get())
                //1st matrix
                .thenReturn((byte) 1).thenReturn((byte) 1).thenReturn((byte) 1)
                .thenReturn((byte) 1).thenReturn((byte) 0).thenReturn((byte) 1)
                .thenReturn((byte) 0).thenReturn((byte) 1).thenReturn((byte) 0)
                //2d matrix
                .thenReturn((byte) 1).thenReturn((byte) 0).thenReturn((byte) 0)
                .thenReturn((byte) 1).thenReturn((byte) 1).thenReturn((byte) 0)
                .thenReturn((byte) 1).thenReturn((byte) 1).thenReturn((byte) 0);
        short n = 3;
        //mock 1st row of result matrix
        when(arraysMultiplier.multiply(n,
                byteArray(1, 1, 1),
                byteArray(1, 1, 1))).thenReturn((byte) 1);
        when(arraysMultiplier.multiply(n,
                byteArray(1, 1, 1),
                byteArray(0, 1, 1))).thenReturn((byte) 1);
        when(arraysMultiplier.multiply(n,
                byteArray(1, 1, 1),
                byteArray(0, 0, 0))).thenReturn((byte) 1);

        //mock 2d row of result matrix
        when(arraysMultiplier.multiply(n,
                byteArray(1, 0, 1),
                byteArray(1, 1, 1))).thenReturn((byte) 0);
        when(arraysMultiplier.multiply(n,
                byteArray(1, 0, 1),
                byteArray(0, 1, 1))).thenReturn((byte) 0);
        when(arraysMultiplier.multiply(n,
                byteArray(1, 0, 1),
                byteArray(0, 0, 0))).thenReturn((byte) 0);

        //mock 3d row of result matrix
        when(arraysMultiplier.multiply(n,
                byteArray(0, 1, 0),
                byteArray(1, 1, 1))).thenReturn((byte) 1);
        when(arraysMultiplier.multiply(n,
                byteArray(0, 1, 0),
                byteArray(0, 1, 1))).thenReturn((byte) 1);
        when(arraysMultiplier.multiply(n,
                byteArray(0, 1, 0),
                byteArray(0, 0, 0))).thenReturn((byte) 1);
    }

    @Test
    public void test() {
        short n = 3;
        Matrix matrix1 = Matrix.getSquareMatrix(n, byteSupplier);
        Matrix matrix2 = Matrix.getSquareMatrix(n, byteSupplier);

        Matrix actual = sequentialMatricesMultiplier.multiply(matrix1, matrix2);

        byte[][] expected = new byte[][]{
                byteArray(1, 1, 1),
                byteArray(0, 0, 0),
                byteArray(1, 1, 1)
        };
        assertArrayEquals(expected, actual.getData());
    }

    private byte[] byteArray(int e1, int e2, int e3) {
        return new byte[]{ (byte) e1, (byte) e2, (byte) e3 };
    }
}