import java.util.Arrays;

import static utils.MyCollectors.toByteArray;

public class SequentialMatricesMultiplier extends AbstractMatricesMultiplier {

    private final ArraysMultiplier arraysMultiplier;

    public SequentialMatricesMultiplier() {
        arraysMultiplier = new Modulo2ArraysMultiplier();
    }

    public SequentialMatricesMultiplier(ArraysMultiplier arraysMultiplier) {
        this.arraysMultiplier = arraysMultiplier;
    }

    @Override
    protected Matrix multiplyMatrices(Matrix m1, Matrix m2) {
        short n = m1.getN();
        short m = m2.getM();

        byte[][] transposed = m2.getTransposedData();
        byte[][] result = Arrays.stream(m1.getData())
                .map(row -> Arrays.stream(transposed)
                        .map(row2 -> arraysMultiplier.multiply(n, row, row2))
                        .collect(toByteArray()))
                .toArray(byte[][]::new);
        return new Matrix(n, m, result);
    }
}
