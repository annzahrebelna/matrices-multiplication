package matrix;

public class Modulo2ArraysMultiplier implements ArraysMultiplier {
    @Override
    public byte multiply(short arraysSize, byte[] array1, byte[] array2) {
        byte result = 0;
        for (short i=0; i<arraysSize; i++) {
            result ^= array1[i] & array2[i];
        }
        return result;
    }
}
