package matrix;

public class Modulo2ArraysMultiplier implements ArraysMultiplier {
    @Override
    public byte multiply(short arraysSize, byte[] array1, byte[] array2) {
        int result = 0;
        for (int i=0; i<arraysSize; i++) {
            result ^= array1[i] & array2[i];
        }
        return (byte) result;
    }
}
