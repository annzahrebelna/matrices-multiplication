public class SequentialMatricesMultiplier extends AbstractMatricesMultiplier {

    private ArraysMultiplier arraysMultiplier;

    public SequentialMatricesMultiplier() {
        arraysMultiplier = new Modulo2ArraysMultiplier();
    }

    @Override
    public byte[][] multiplyMatrices(Matrix m1, Matrix m2) {
        short n = m1.getN();
        short m = m2.getM();
        byte[][] result = new byte[n][m];
        for (short i=0; i<n; i++) {
            for (short j=0; j<m; j++) {
                byte value = arraysMultiplier.multiply(n, m1.getRow(i), m2.getColumn(j));
                result[i][j] = value;
            }
        }
        return result;
    }
}
