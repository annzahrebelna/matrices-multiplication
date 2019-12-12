import org.junit.Test;

import static org.junit.Assert.*;

public class Modulo2ArraysMultiplierTest {

    @Test
    public void test_result0() {
        Modulo2ArraysMultiplier m = new Modulo2ArraysMultiplier();
        byte[] array1 = byteArray(1, 1, 1);
        byte[] array2 = byteArray(1, 1, 1);

        byte actual = m.multiply((short) 3, array1, array2);

        byte expected = (byte) 1; //1&1 ^ 1&1 ^ 1&1 = 1 ^ 1 ^ 1 = 0 ^ 1 = 1
        assertEquals(expected, actual);
    }

    @Test
    public void test_result1() {
        Modulo2ArraysMultiplier m = new Modulo2ArraysMultiplier();
        byte[] array1 = byteArray(1, 1, 1);
        byte[] array2 = byteArray(0, 1, 1);

        byte actual = m.multiply((short) 3, array1, array2);

        byte expected = (byte) 0; //1&0 ^ 1&1 ^ 1&1 = 0 ^ 1 ^ 1 = 1 ^ 1 = 0
        assertEquals(expected, actual);
    }

    private byte[] byteArray(int e1, int e2, int e3) {
        return new byte[]{ (byte) e1, (byte) e2, (byte) e3 };
    }
}